package com.simpus.srikandi.activity.objects;

public class Petabalita {
    private String No_Index_Balita_Dinas;
    private String Nama_Bayi;
    private String Tanggal_Lahir;
    private String Nama_Ibu;
    private String Nama_Ayah;
    private String Lat;
    private String lng;
    private String Ket_Stunting;
    private String Ket_Gibur;
    private String Nip;
    private String Kode_Desa;

    public Petabalita(String no_Index_Balita_Dinas, String nama_Bayi, String tanggal_Lahir, String nama_Ibu, String nama_Ayah, String lat, String lng, String ket_Stunting, String ket_Gibur, String nip, String kode_Desa) {
        No_Index_Balita_Dinas = no_Index_Balita_Dinas;
        Nama_Bayi = nama_Bayi;
        Tanggal_Lahir = tanggal_Lahir;
        Nama_Ibu = nama_Ibu;
        Nama_Ayah = nama_Ayah;
        Lat = lat;
        this.lng = lng;
        Ket_Stunting = ket_Stunting;
        Ket_Gibur = ket_Gibur;
        Nip = nip;
        Kode_Desa = kode_Desa;
    }

    public String getNo_Index_Balita_Dinas() {
        return No_Index_Balita_Dinas;
    }

    public void setNo_Index_Balita_Dinas(String no_Index_Balita_Dinas) {
        No_Index_Balita_Dinas = no_Index_Balita_Dinas;
    }

    public String getNama_Bayi() {
        return Nama_Bayi;
    }

    public void setNama_Bayi(String nama_Bayi) {
        Nama_Bayi = nama_Bayi;
    }

    public String getTanggal_Lahir() {
        return Tanggal_Lahir;
    }

    public void setTanggal_Lahir(String tanggal_Lahir) {
        Tanggal_Lahir = tanggal_Lahir;
    }

    public String getNama_Ibu() {
        return Nama_Ibu;
    }

    public void setNama_Ibu(String nama_Ibu) {
        Nama_Ibu = nama_Ibu;
    }

    public String getNama_Ayah() {
        return Nama_Ayah;
    }

    public void setNama_Ayah(String nama_Ayah) {
        Nama_Ayah = nama_Ayah;
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

    public String getKet_Stunting() {
        return Ket_Stunting;
    }

    public void setKet_Stunting(String ket_Stunting) {
        Ket_Stunting = ket_Stunting;
    }

    public String getKet_Gibur() {
        return Ket_Gibur;
    }

    public void setKet_Gibur(String ket_Gibur) {
        Ket_Gibur = ket_Gibur;
    }

    public String getNip() {
        return Nip;
    }

    public void setNip(String nip) {
        Nip = nip;
    }

    public String getKode_Desa() {
        return Kode_Desa;
    }

    public void setKode_Desa(String kode_Desa) {
        Kode_Desa = kode_Desa;
    }
}
