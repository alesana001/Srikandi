package com.simpus.srikandi.activity.objects;

public class PetaBumil {
    private String No_Index_Bumil;
    private String Nama;
    private String NamaSuami;
    private String TanggalLahir;
    private String Lat;
    private String lng;
    private String StatusRisti;
    private String Hpl;
    private String Nip;

    public String getNo_Index_Bumil() {
        return No_Index_Bumil;
    }

    public void setNo_Index_Bumil(String no_Index_Bumil) {
        No_Index_Bumil = no_Index_Bumil;
    }

    public String getNama() {
        return Nama;
    }

    public void setNama(String nama) {
        Nama = nama;
    }

    public String getNamaSuami() {
        return NamaSuami;
    }

    public void setNamaSuami(String namaSuami) {
        NamaSuami = namaSuami;
    }

    public String getTanggalLahir() {
        return TanggalLahir;
    }

    public void setTanggalLahir(String tanggalLahir) {
        TanggalLahir = tanggalLahir;
    }

    public String getLat() {
        return Lat;
    }

    public void setLat(String lat) {
        Lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getStatusRisti() {
        return StatusRisti;
    }

    public void setStatusRisti(String statusRisti) {
        StatusRisti = statusRisti;
    }

    public String getHpl() {
        return Hpl;
    }

    public void setHpl(String hpl) {
        Hpl = hpl;
    }

    public String getNip() {
        return Nip;
    }

    public void setNip(String nip) {
        Nip = nip;
    }

    public PetaBumil(String no_Index_Bumil, String nama, String namaSuami, String tanggalLahir, String lat, String lng, String statusRisti, String hpl, String nip) {
        No_Index_Bumil = no_Index_Bumil;
        Nama = nama;
        NamaSuami = namaSuami;
        TanggalLahir = tanggalLahir;
        Lat = lat;
        this.lng = lng;
        StatusRisti = statusRisti;
        Hpl = hpl;
        Nip = nip;

    }
}
