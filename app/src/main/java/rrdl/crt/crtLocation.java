package rrdl.crt;



class crtLocation {
    private double x;
    private double y;
    private String adresse;
    crtLocation(double x, double y, String adresse){
        this.x=x;
        this.y=y;
        this.adresse = adresse;
    }

    double getX() {
        return x;
    }

    double getY() {
        return y;
    }

    String getAdresse() {
        return adresse;
    }
}
