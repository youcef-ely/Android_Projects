package Model;

public class Chantier {

    private int id;
    private String avenue;
    private String ville;
    private double latitude;
    private double longitude;
    private String dateDebut;
    private String dateFin;
    private String observation;


    public Chantier() {

    }

    public Chantier(String avenue, String ville, double latitude, double longitude, String dateDebut, String dateFin, String observation) {

        this.avenue = avenue;
        this.ville = ville;
        this.latitude = latitude;
        this.longitude = longitude;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.observation = observation;
    }



    public String getAvenue() {
        return avenue;
    }

    public void setAvenue(String avenue) { this.avenue = avenue; }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(String dateDebut) {
        this.dateDebut = dateDebut;
    }

    public String getDateFin() {
        return dateFin;
    }

    public void setDateFin(String dateFin) {
        this.dateFin = dateFin;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }



}
