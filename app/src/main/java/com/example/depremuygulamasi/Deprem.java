package com.example.depremuygulamasi;

public class Deprem {

    private double siddetDouble;
    private String siddet;
    private String konum;
    private String tarih;
    private long zamanMillis;

    public Deprem(String siddet, String konum, String tarih, long zaman) {
        this.siddet = siddet;
        this.konum = konum;
        this.tarih = tarih;
        this.zamanMillis = zaman;

        try {
            this.siddetDouble = Double.parseDouble(siddet);
        } catch (NumberFormatException e) {
            this.siddetDouble = 0;
        }
    }

    public String getTimeAgo() {
        long diffMillis = System.currentTimeMillis() - zamanMillis;

        long diffSeconds = diffMillis / 1000;
        long diffMinutes = diffSeconds / 60;
        long diffHours = diffMinutes / 60;
        long diffDays = diffHours / 24;

        if (diffDays > 0) {
            return diffDays + " gün önce";
        } else if (diffHours > 0) {
            return diffHours + " saat önce";
        } else if (diffMinutes > 0) {
            return diffMinutes + " dakika önce";
        } else {
            return "Şimdi";
        }
    }

    // Getters and setters
    public String getSiddet() {
        return siddet;
    }

    public long getZamanMillis() {
        return zamanMillis;
    }

    public double getSiddetDouble() {
        return siddetDouble;
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
