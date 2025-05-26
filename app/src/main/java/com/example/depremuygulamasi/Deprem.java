package com.example.depremuygulamasi;

public class Deprem {

    private String siddet;
    private String konum;
    private String tarih;

    public Deprem(String siddet, String konum, String tarih) {
        this.siddet = siddet;
        this.konum = konum;
        this.tarih = tarih;
    }

    public String getSiddet() {
        return siddet;
    }

    public void setSiddet(String siddet) {
        this.siddet = siddet;
    }

    public String getKonum() {
        return konum;
    }

    public void setKonum(String konum) {
        this.konum = konum;
    }

    public String getTarih() {
        return tarih;
    }

    public void setTarih(String tarih) {
        this.tarih = tarih;
    }
}
