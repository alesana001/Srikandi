package   com.simpus.srikandi.activity.objects;

/**
 * Created by alesana on 02/03/2018.
 */

public class IbuHml {
    private String No_Index;
    private String Tgl_Informasi;
    private String Nama;
    private String Suami;
    private String Alamat;
    private String Kode_Kecamatan;
    private String Kode_Desa;
    private String User_id_pelapor;
    private String Verifikasi;
    private String Lat;
    private String Lng;
    private String No_Telp;


    public IbuHml(String no_Index, String tgl_Informasi, String nama, String suami, String alamat, String kode_Kecamatan, String kode_Desa, String user_id_pelapor, String verifikasi, String lat, String lng, String no_Telp) {
        No_Index = no_Index;
        Tgl_Informasi = tgl_Informasi;
        Nama = nama;
        Suami = suami;
        Alamat = alamat;
        Kode_Kecamatan = kode_Kecamatan;
        Kode_Desa = kode_Desa;
        User_id_pelapor = user_id_pelapor;
        Verifikasi = verifikasi;
        Lat = lat;
        Lng = lng;
        No_Telp = no_Telp;
    }

    public String getNo_Index() {
        return No_Index;
    }

    public void setNo_Index(String no_Index) {
        No_Index = no_Index;
    }

    public String getTgl_Informasi() {
        return Tgl_Informasi;
    }

    public void setTgl_Informasi(String tgl_Informasi) {
        Tgl_Informasi = tgl_Informasi;
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

    public String getAlamat() {
        return Alamat;
    }

    public void setAlamat(String alamat) {
        Alamat = alamat;
    }

    public String getKode_Kecamatan() {
        return Kode_Kecamatan;
    }

    public void setKode_kecamatan(String kode_kecamatan) {
        Kode_Kecamatan = kode_kecamatan;
    }

    public String getKode_Desa() {
        return Kode_Desa;
    }

    public void setKode_Desa(String kode_Desa) {
        Kode_Desa = kode_Desa;
    }

    public String getUser_id_pelapor() {
        return User_id_pelapor;
    }

    public void setUser_id_pelapor(String user_id_pelapor) {
        User_id_pelapor = user_id_pelapor;
    }

    public String getVerifikasi() {
        return Verifikasi;
    }

    public void setVerifikasi(String verifikasi) {
        Verifikasi = verifikasi;
    }

    public String getLat() {
        return Lat;
    }

    public void setLat(String lat) {
        Lat = lat;
    }

    public String getLng() {
        return Lng;
    }

    public void setLng(String lng) {
        Lng = lng;
    }
    public String getNo_Telp() {
        return No_Telp;
    }

    public void setNo_Telp(String no_Telp) {
        No_Telp = no_Telp;
    }
}