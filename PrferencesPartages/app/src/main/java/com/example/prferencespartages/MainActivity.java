package com.example.prferencespartages;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {



    private EditText nom, prenom;
    private Button effacer, enregistrer, charger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nom = findViewById(R.id.nom);
        prenom = findViewById(R.id.prenom);
        effacer = findViewById(R.id.effacer);
        enregistrer = findViewById(R.id.enregistrer);
        charger = findViewById(R.id.charger);

        SharedPreferences pref = getSharedPreferences("data", Context.MODE_PRIVATE);

        enregistrer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nomText = nom.getText().toString();
                String prenomText = prenom.getText().toString();

                if(nomText.equals("") && prenomText.equals("")){
                    Toast.makeText(MainActivity.this, "Il faut entrer le nom et le prénom pour pouvoir les enregistrer !", Toast.LENGTH_LONG).show();
                }else{
                    SharedPreferences.Editor edit = pref.edit();
                    edit.putString("Nom", nomText);
                    edit.putString("Prénom", prenomText);
                    edit.commit();


                    nom.setText("");
                    prenom.setText("");
                }
            }
        });

        charger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nomText = pref.getString("Nom", "Not found");
                String prenomText = pref.getString("Prénom", "Not found");
                if(nomText.equals("Not found") || prenomText.equals("Not found")){
                    Toast.makeText(MainActivity.this, "Les données ne sont pas encore enregistrées !", Toast.LENGTH_LONG).show();
                }
                else{
                    nom.setText(nomText);
                    prenom.setText(prenomText);
                }
            }
        });

        effacer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nomText = pref.getString("Nom", "Not found");
                String prenomText = pref.getString("Prénom", "Not found");
                if(nomText.equals("Not found") || prenomText.equals("Not found")){
                    Toast.makeText(MainActivity.this, "Les données ne sont pas encore enregistrées, Rien à supprimer !!", Toast.LENGTH_LONG).show();
                }
                else{
                    SharedPreferences.Editor edit = pref.edit();
                    edit.clear();
                    edit.commit();
                    nom.setText("");
                    prenom.setText("");
                }
            }
        });
    }

}