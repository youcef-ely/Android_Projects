package com.example.projet;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import android.Manifest;
import android.location.Address;
import android.location.Geocoder;


import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.Toast;

import com.directions.route.AbstractRouting;
import com.directions.route.Route;
import com.directions.route.RouteException;
import com.directions.route.Routing;
import com.directions.route.RoutingListener;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;
import java.util.List;

import Model.Chantier;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback
        , GoogleApiClient.OnConnectionFailedListener
        , RoutingListener {

    private GoogleMap mMap;
    private Marker destinationMarker;
    protected LatLng currentLatLng;
    protected LatLng destinationLatLng;
    LatLng start;
    LatLng destination;
    private ImageButton imageButton;
    protected List<Chantier> chantiers;


    private List<Polyline> polylines;
    private SearchView searchView;

    FusedLocationProviderClient fusedLocationProviderClient;
    private DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.databaseReference =  FirebaseDatabase
                .getInstance()
                .getReference("Chantier");

        setContentView(R.layout.activity_maps);

        imageButton = findViewById(R.id.drawRoute);

        chantiers = new ArrayList<>();

        searchView = (SearchView) findViewById(R.id.idSearchView);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {
                start = currentLatLng;
                destination = destinationLatLng;

                drawRoute(currentLatLng, destinationLatLng);
            }
        });
    }












    //Avoir la position actuelle
    private void getCurrentLocation(){
        if(PermissionPackage.Permissions.checkPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)){
            mMap.setMyLocationEnabled(true);
            fusedLocationProviderClient.getLastLocation().addOnSuccessListener(this, location -> {
                if (location != null) {
                    currentLatLng = new LatLng(location.getLatitude(), location.getLongitude());
                    }

                });

        }else{
            PermissionPackage.Permissions.askForPermission(this, Manifest.permission.ACCESS_FINE_LOCATION, 44);
        }

    }









    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        getCurrentLocation();
        mMap.getUiSettings().setZoomControlsEnabled(true);



        //Charger les position des chantiers
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Chantier chantier = dataSnapshot.getValue(Chantier.class);
                    chantiers.add(chantier);
                    mMap.addMarker(new MarkerOptions().position(new LatLng(chantier.getLatitude(),
                            chantier.getLongitude())).title(chantier.getAvenue()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)).snippet(chantier.getObservation()));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(chantier.getLatitude(),
                            chantier.getLongitude())));
                    
                }

            }


            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("dd", "Failed to read value.", databaseError.toException());
            }
        });

        searchLocation();
        
    }


















    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        // granResults[i] equals -1 or 0, mean respectively access denied or access granted
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(grantResults[0] == 0){
            getCurrentLocation();
            Toast.makeText(getApplicationContext(), "Accès permis", Toast.LENGTH_LONG).show();
        }else
            Toast.makeText(getApplicationContext(), "Accès refusé", Toast.LENGTH_LONG).show();
    }











    //Chercher une localisation
    public void searchLocation(){
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                if(destinationMarker != null){
                    destinationMarker.remove();
                }
                String location = searchView.getQuery().toString();
                List<Address> addressList;
                if(!location.equals("")) {
                    Geocoder geocoder = new Geocoder(MapsActivity.this);
                    try{
                        addressList = geocoder.getFromLocationName(location, 1);
                        LatLng latLng = new LatLng(addressList.get(0).getLatitude(), addressList.get(0).getLongitude());

                        destinationLatLng = latLng;
                        destinationMarker = mMap.addMarker(new MarkerOptions().position(latLng).title(location));
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
                        Toast.makeText(MapsActivity.this, "Destination trouvée", Toast.LENGTH_SHORT
                        ).show();
                    }catch(Exception e) {
                        Toast.makeText(MapsActivity.this, "Destination non trouvée", Toast.LENGTH_SHORT).show();
                    }
                }else
                    Toast.makeText(MapsActivity.this, "Champs vide", Toast.LENGTH_LONG).show();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

    }










    public void drawRoute(LatLng Start, LatLng End)

    {
        if(Start==null || End==null)
            Toast.makeText(getApplicationContext(),"Locations empty",Toast.LENGTH_LONG).show();
        else {
            Routing routing = new Routing.Builder()
                    //Spécifier un chemin pour un piéton, sinon on peut la changer
                    .travelMode(AbstractRouting.TravelMode.WALKING)
                    .withListener(this)
                    .alternativeRoutes(true)
                    .waypoints(Start, End)
                    .key("AIzaSyDHCY5a7ZKFXnzL4ON-EcCbteejHffwP_o")
                    .build();
            routing.execute();
        }
    }

    @Override
    public void onRoutingFailure(RouteException e) {
        Toast.makeText(getApplicationContext()
                , e.toString(),
                Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRoutingStart() {
        Toast.makeText(getApplicationContext(),"Traçage du chemin",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRoutingCancelled() {
        drawRoute(currentLatLng,destinationLatLng);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        drawRoute(currentLatLng,destinationLatLng);
    }

    @Override
    public void onRoutingSuccess(ArrayList<Route> route, int shortestRouteIndex) {
        CameraUpdate center = CameraUpdateFactory.newLatLng(currentLatLng);
        CameraUpdate zoom = CameraUpdateFactory.zoomTo(6);
        if (polylines != null) {
            polylines.clear();
        }
        PolylineOptions polyOptions = new PolylineOptions();
        LatLng polylineStartLatLng = null;
        LatLng polylineEndLatLng = null;

        polylines = new ArrayList<>();
        boolean travauxExist;
        for (int i = 0; i <route.size(); i++) {
            travauxExist = false;
            for(int j = 0; j<route.get(i).getPoints().size(); j++)
            {
                for(Chantier c : chantiers)
                {
                    if((route.get(i).getPoints().get(j).latitude <= c.getLatitude() + 0.002 && route.get(i).getPoints().get(j).latitude >= c.getLatitude() - 0.002) &&
                            (route.get(i).getPoints().get(j).longitude <=  c.getLongitude() + 0.002 && route.get(i).getPoints().get(j).longitude >=  c.getLongitude() -0.002))
                    {
                        travauxExist = true;
                        break;
                    }
                }
            }
            if(!travauxExist)
            {

                polyOptions = new PolylineOptions();
                polyOptions.color(getResources().getColor(R.color.teal_200));
                polyOptions.width(7);
                polyOptions.addAll(route.get(i).getPoints());

                Polyline polyline = mMap.addPolyline(polyOptions);
                int k = polyline.getPoints().size();
                polylines.add(polyline);

                break;

            }
            else {

            }
        }

        MarkerOptions startMarker = new MarkerOptions();
        startMarker.position(start);
        startMarker.title("My Location");
        mMap.addMarker(startMarker);


        MarkerOptions endMarker = new MarkerOptions();
        endMarker.position(destination);
        endMarker.title("Destination");
        mMap.addMarker(endMarker);
    }







}











