package com.example.zadatak;

public class Rezervacija {
    private int PIN;
    private String restoran;
    private String datum;
    private String vrijeme;
    private String br_osoba;
    private String ime;

    public Rezervacija(String restoran, String datum, String vrijeme, String br_osoba, String ime) {
        this.restoran = restoran;
        this.datum = datum;
        this.vrijeme = vrijeme;
        this.br_osoba = br_osoba;
        this.ime = ime;
    }

    
    public int getPIN() {
        return PIN;
    }

    public void setPIN() {
        this.PIN = (int)(Math.random()*9000+1000);
    }

    public String getRestoran() {
        return restoran;
    }

    public void setRestoran(String restoran) {
        this.restoran = restoran;
    }

    public String getDatum() {
        return datum;
    }

    public void setDatum(String datum) {
        this.datum = datum;
    }

    public String getVrijeme() {
        return vrijeme;
    }

    public void setVrijeme(String vrijeme) {
        this.vrijeme = vrijeme;
    }
    public String getBr_osoba() {
        return br_osoba;
    }

    public void setBr_osoba(String br_osoba) {
        this.br_osoba = br_osoba;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }


}
