package com.simpus.srikandi.activity.objects;

public class Balita {
    private String No_Index_Balita_Dinas;
    private String Nama_Bayi;
    private String Tanggal_Lahir;
    private String Nama_Ibu;
    private String Nama_Ayah;
    private String Kode_Wilayah;

    public Balita(String no_Index_Balita_Dinas, String nama_Bayi, String tanggal_Lahir, String nama_Ibu, String nama_Ayah, String kode_Wilayah) {
        No_Index_Balita_Dinas = no_Index_Balita_Dinas;
        Nama_Bayi = nama_Bayi;
        Tanggal_Lahir = tanggal_Lahir;
        Nama_Ibu = nama_Ibu;
        Nama_Ayah = nama_Ayah;
        Kode_Wilayah = kode_Wilayah;
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

    public String getKode_Wilayah() {
        return Kode_Wilayah;
    }

    public void setKode_Wilayah(String kode_Wilayah) {
        Kode_Wilayah = kode_Wilayah;
    }
}
