package com.simpus.srikandi.activity.objects;

public class BumilblmPeta {
    private String No_Index_Bumil_Dinas;
    private String Nama;
    private String Suami;
    private String Tgl_Lahir;
    private String HTP;
    private String Kode_Desa;

    public BumilblmPeta(String no_Index_Bumil_Dinas, String nama, String suami, String tgl_Lahir, String HTP, String kode_Desa) {
        No_Index_Bumil_Dinas = no_Index_Bumil_Dinas;
        Nama = nama;
        Suami = suami;
        Tgl_Lahir = tgl_Lahir;
        this.HTP = HTP;
        Kode_Desa = kode_Desa;
    }

    public String getNo_Index_Bumil_Dinas() {
        return No_Index_Bumil_Dinas;
    }

    public void setNo_Index_Bumil_Dinas(String no_Index_Bumil_Dinas) {
        No_Index_Bumil_Dinas = no_Index_Bumil_Dinas;
    }

    public String getNama() {
        return Nama;
    }

    public void setNama(String nama) {
        Nama = nama;
    }

    public String getSuami() {
        return Suami;
    }

    public void setSuami(String suami) {
        Suami = suami;
    }

    public String getTgl_Lahir() {
        return Tgl_Lahir;
    }

    public void setTgl_Lahir(String tgl_Lahir) {
        Tgl_Lahir = tgl_Lahir;
    }

    public String getHTP() {
        return HTP;
    }

    public void setHTP(String HTP) {
        this.HTP = HTP;
    }

    public String getKode_Desa() {
        return Kode_Desa;
    }

    public void setKode_Desa(String kode_Desa) {
        Kode_Desa = kode_Desa;
    }
}
