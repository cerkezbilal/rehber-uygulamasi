package com.bilal.karademir.rehberuygulamasi;

public class Kisiler {

    private int kisi_id;
    private String Kisi_adi;
    private String kisi_tel;

    public Kisiler() {
    }

    public Kisiler(int kisi_id, String kisi_adi, String kisi_tel) {
        this.kisi_id = kisi_id;
        Kisi_adi = kisi_adi;
        this.kisi_tel = kisi_tel;
    }

    public int getKisi_id() {
        return kisi_id;
    }

    public void setKisi_id(int kisi_id) {
        this.kisi_id = kisi_id;
    }

    public String getKisi_adi() {
        return Kisi_adi;
    }

    public void setKisi_adi(String kisi_adi) {
        Kisi_adi = kisi_adi;
    }

    public String getKisi_tel() {
        return kisi_tel;
    }

    public void setKisi_tel(String kisi_tel) {
        this.kisi_tel = kisi_tel;
    }
}



