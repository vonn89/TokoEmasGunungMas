/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasGunungMas.DAO;

import com.excellentsystem.TokoEmasGunungMas.Main;
import static com.excellentsystem.TokoEmasGunungMas.Main.sistem;
import com.excellentsystem.TokoEmasGunungMas.Model.Jenis;
import com.excellentsystem.TokoEmasGunungMas.Model.StokBarang;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Xtreme
 */
public class StokBarangDAO {

    public static List<StokBarang> getAllStokBarcode(Connection con, String date, String kodeGudang, String kodeKategori, String kodeJenis) throws Exception {
        PreparedStatement ps = con.prepareStatement("select * from tt_stok_barang where tanggal = ? and kode_barcode!='' "
                + "and kode_gudang like ? and kode_kategori like ? and kode_jenis like ? ");
        if ("".equals(kodeGudang)) {
            kodeGudang = "%";
        }
        if ("".equals(kodeKategori)) {
            kodeKategori = "%";
        }
        if ("".equals(kodeJenis)) {
            kodeJenis = "%";
        }
        ps.setString(1, date);
        ps.setString(2, kodeGudang);
        ps.setString(3, kodeKategori);
        ps.setString(4, kodeJenis);
        ResultSet rs = ps.executeQuery();
        List<StokBarang> allStokBarang = new ArrayList<>();
        while (rs.next()) {
            StokBarang stok = new StokBarang();
            stok.setTanggal(rs.getString(1));
            stok.setKodeBarcode(rs.getString(2));
            stok.setKodeKategori(rs.getString(3));
            stok.setKodeJenis(rs.getString(4));
            stok.setKodeGudang(rs.getString(5));
            stok.setBeratAwal(rs.getDouble(6));
            stok.setBeratAsliAwal(rs.getDouble(7));
            stok.setStokAwal(rs.getInt(8));
            stok.setBeratMasuk(rs.getDouble(9));
            stok.setBeratAsliMasuk(rs.getDouble(10));
            stok.setStokMasuk(rs.getInt(11));
            stok.setBeratKeluar(rs.getDouble(12));
            stok.setBeratAsliKeluar(rs.getDouble(13));
            stok.setStokKeluar(rs.getInt(14));
            stok.setBeratAkhir(rs.getDouble(15));
            stok.setBeratAsliAkhir(rs.getDouble(16));
            stok.setStokAkhir(rs.getInt(17));
            allStokBarang.add(stok);
        }
        return allStokBarang;
    }

    public static StokBarang getStokBarcodeTodayByKodeBarcodeAndKodeGudang(Connection con, String kodeBarcode, String kodeGudang) throws Exception {
        PreparedStatement ps = con.prepareStatement("select * from tt_stok_barang "
                + "where tanggal=? and kode_barcode=? and kode_gudang=?");
        ps.setString(1, sistem.getTglSystem());
        ps.setString(2, kodeBarcode);
        ps.setString(3, kodeGudang);
        StokBarang stok = null;
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            stok = new StokBarang();
            stok.setTanggal(rs.getString(1));
            stok.setKodeBarcode(rs.getString(2));
            stok.setKodeKategori(rs.getString(3));
            stok.setKodeJenis(rs.getString(4));
            stok.setKodeGudang(rs.getString(5));
            stok.setBeratAwal(rs.getDouble(6));
            stok.setBeratAsliAwal(rs.getDouble(7));
            stok.setStokAwal(rs.getInt(8));
            stok.setBeratMasuk(rs.getDouble(9));
            stok.setBeratAsliMasuk(rs.getDouble(10));
            stok.setStokMasuk(rs.getInt(11));
            stok.setBeratKeluar(rs.getDouble(12));
            stok.setBeratAsliKeluar(rs.getDouble(13));
            stok.setStokKeluar(rs.getInt(14));
            stok.setBeratAkhir(rs.getDouble(15));
            stok.setBeratAsliAkhir(rs.getDouble(16));
            stok.setStokAkhir(rs.getInt(17));
        }
        return stok;
    }

    public static List<StokBarang> getStokBarcodeGroupByGudangDate(Connection con, String date) throws Exception {
        PreparedStatement ps = con.prepareStatement("select tanggal,kode_barcode,kode_kategori,kode_jenis,kode_gudang,"
                + "sum(berat_awal),sum(berat_asli_awal),sum(stok_awal),"
                + "sum(berat_masuk),sum(berat_asli_masuk),sum(stok_masuk),"
                + "sum(berat_keluar),sum(berat_asli_keluar),sum(stok_keluar),"
                + "sum(berat_akhir),sum(berat_asli_akhir),sum(stok_akhir)"
                + "from tt_stok_barang where kode_barcode!='' and kode_gudang!='' and tanggal = ? group by kode_gudang,kode_jenis");
        ps.setString(1, date);
        ResultSet rs = ps.executeQuery();
        List<StokBarang> allStokBarang = new ArrayList<>();
        while (rs.next()) {
            StokBarang stok = new StokBarang();
            stok.setTanggal(rs.getString(1));
            stok.setKodeBarcode(rs.getString(2));
            stok.setKodeKategori(rs.getString(3));
            stok.setKodeJenis(rs.getString(4));
            stok.setKodeGudang(rs.getString(5));
            stok.setBeratAwal(rs.getDouble(6));
            stok.setBeratAsliAwal(rs.getDouble(7));
            stok.setStokAwal(rs.getInt(8));
            stok.setBeratMasuk(rs.getDouble(9));
            stok.setBeratAsliMasuk(rs.getDouble(10));
            stok.setStokMasuk(rs.getInt(11));
            stok.setBeratKeluar(rs.getDouble(12));
            stok.setBeratAsliKeluar(rs.getDouble(13));
            stok.setStokKeluar(rs.getInt(14));
            stok.setBeratAkhir(rs.getDouble(15));
            stok.setBeratAsliAkhir(rs.getDouble(16));
            stok.setStokAkhir(rs.getInt(17));
            allStokBarang.add(stok);
        }
        return allStokBarang;
    }

    public static List<StokBarang> getStokBarcodeGroupByKategoriDate(Connection con, String date) throws Exception {
        PreparedStatement ps = con.prepareStatement("select tanggal,kode_barcode,kode_kategori,kode_jenis,kode_gudang,"
                + "sum(berat_awal),sum(berat_asli_awal),sum(stok_awal),"
                + "sum(berat_masuk),sum(berat_asli_masuk),sum(stok_masuk),"
                + "sum(berat_keluar),sum(berat_asli_keluar),sum(stok_keluar),"
                + "sum(berat_akhir),sum(berat_asli_akhir),sum(stok_akhir)"
                + "from tt_stok_barang where kode_barcode!='' and kode_gudang!='' and tanggal = ? group by kode_kategori,kode_jenis");
        ps.setString(1, date);
        ResultSet rs = ps.executeQuery();
        List<StokBarang> allStokBarang = new ArrayList<>();
        while (rs.next()) {
            StokBarang stok = new StokBarang();
            stok.setTanggal(rs.getString(1));
            stok.setKodeBarcode(rs.getString(2));
            stok.setKodeKategori(rs.getString(3));
            stok.setKodeJenis(rs.getString(4));
            stok.setKodeGudang(rs.getString(5));
            stok.setBeratAwal(rs.getDouble(6));
            stok.setBeratAsliAwal(rs.getDouble(7));
            stok.setStokAwal(rs.getInt(8));
            stok.setBeratMasuk(rs.getDouble(9));
            stok.setBeratAsliMasuk(rs.getDouble(10));
            stok.setStokMasuk(rs.getInt(11));
            stok.setBeratKeluar(rs.getDouble(12));
            stok.setBeratAsliKeluar(rs.getDouble(13));
            stok.setStokKeluar(rs.getInt(14));
            stok.setBeratAkhir(rs.getDouble(15));
            stok.setBeratAsliAkhir(rs.getDouble(16));
            stok.setStokAkhir(rs.getInt(17));
            allStokBarang.add(stok);
        }
        return allStokBarang;
    }

    public static List<StokBarang> getKartuStokBarangBarcode(Connection con, String periode, String kodeGudang, String kodeKategori, String kodeJenis) throws Exception {
        PreparedStatement ps = con.prepareStatement("select tanggal,kode_barcode,kode_kategori,kode_jenis,kode_gudang,"
                + "sum(berat_awal),sum(berat_asli_awal),sum(stok_awal),"
                + "sum(berat_masuk),sum(berat_asli_masuk),sum(stok_masuk),"
                + "sum(berat_keluar),sum(berat_asli_keluar),sum(stok_keluar),"
                + "sum(berat_akhir),sum(berat_asli_akhir),sum(stok_akhir)"
                + "from tt_stok_barang where kode_barcode!='' and kode_kategori like ? and kode_jenis like ? and kode_gudang like ? and tanggal like ? group by tanggal");
        if ("".equals(kodeKategori)) {
            kodeKategori = "%";
        }
        if ("".equals(kodeJenis)) {
            kodeJenis = "%";
        }
        if ("".equals(kodeGudang)) {
            kodeGudang = "%";
        }
        if (periode.equals("Today")) {
            periode = Main.sistem.getTglSystem()+ "%";
        } else if (periode.equals("This Month")) {
            periode = Main.sistem.getTglSystem().substring(0, 7) + "%";
        } else if (periode.equals("This Year")) {
            periode = Main.sistem.getTglSystem().substring(0, 4) + "%";
        } else {
            periode = "%";
        }
        ps.setString(1, kodeKategori);
        ps.setString(2, kodeJenis);
        ps.setString(3, kodeGudang);
        ps.setString(4, periode);
        ResultSet rs = ps.executeQuery();
        List<StokBarang> allStokBarang = new ArrayList<>();
        while (rs.next()) {
            StokBarang stok = new StokBarang();
            stok.setTanggal(rs.getString(1));
            stok.setKodeBarcode(rs.getString(2));
            stok.setKodeKategori(rs.getString(3));
            stok.setKodeJenis(rs.getString(4));
            stok.setKodeGudang(rs.getString(5));
            stok.setBeratAwal(rs.getDouble(6));
            stok.setBeratAsliAwal(rs.getDouble(7));
            stok.setStokAwal(rs.getInt(8));
            stok.setBeratMasuk(rs.getDouble(9));
            stok.setBeratAsliMasuk(rs.getDouble(10));
            stok.setStokMasuk(rs.getInt(11));
            stok.setBeratKeluar(rs.getDouble(12));
            stok.setBeratAsliKeluar(rs.getDouble(13));
            stok.setStokKeluar(rs.getInt(14));
            stok.setBeratAkhir(rs.getDouble(15));
            stok.setBeratAsliAkhir(rs.getDouble(16));
            stok.setStokAkhir(rs.getInt(17));
            allStokBarang.add(stok);
        }
        return allStokBarang;
    }

    public static List<StokBarang> getKartuStokBarangDalam(Connection con, String periode, String kodeJenis) throws Exception {
        PreparedStatement ps = con.prepareStatement("select tanggal,kode_barcode,kode_kategori,kode_jenis,kode_gudang,"
                + "sum(berat_awal),sum(berat_asli_awal),sum(stok_awal),"
                + "sum(berat_masuk),sum(berat_asli_masuk),sum(stok_masuk),"
                + "sum(berat_keluar),sum(berat_asli_keluar),sum(stok_keluar),"
                + "sum(berat_akhir),sum(berat_asli_akhir),sum(stok_akhir)"
                + "from tt_stok_barang where kode_jenis like ? and tanggal like ? and kode_barcode='' group by tanggal ");
        if ("".equals(kodeJenis)) {
            kodeJenis = "%";
        }
        if (periode.equals("Today")) {
            periode = sistem.getTglSystem() + "%";
        } else if (periode.equals("This Month")) {
            periode = sistem.getTglSystem().substring(0, 7) + "%";
        } else if (periode.equals("This Year")) {
            periode = sistem.getTglSystem().substring(0, 4) + "%";
        } else {
            periode = "%";
        }
        ps.setString(1, kodeJenis);
        ps.setString(2, periode);
        ResultSet rs = ps.executeQuery();
        List<StokBarang> allStokBarang = new ArrayList<>();
        while (rs.next()) {
            StokBarang stok = new StokBarang();
            stok.setTanggal(rs.getString(1));
            stok.setKodeBarcode(rs.getString(2));
            stok.setKodeKategori(rs.getString(3));
            stok.setKodeJenis(rs.getString(4));
            stok.setKodeGudang(rs.getString(5));
            stok.setBeratAwal(rs.getDouble(6));
            stok.setBeratAsliAwal(rs.getDouble(7));
            stok.setStokAwal(rs.getInt(8));
            stok.setBeratMasuk(rs.getDouble(9));
            stok.setBeratAsliMasuk(rs.getDouble(10));
            stok.setStokMasuk(rs.getInt(11));
            stok.setBeratKeluar(rs.getDouble(12));
            stok.setBeratAsliKeluar(rs.getDouble(13));
            stok.setStokKeluar(rs.getInt(14));
            stok.setBeratAkhir(rs.getDouble(15));
            stok.setBeratAsliAkhir(rs.getDouble(16));
            stok.setStokAkhir(rs.getInt(17));
            allStokBarang.add(stok);
        }
        return allStokBarang;
    }

    public static StokBarang getStokNonBarcodeTodayByKodeJenis(Connection con, String kodeJenis) throws Exception {
        PreparedStatement ps = con.prepareStatement("select * from tt_stok_barang where kode_barcode='' "
                + " and kode_gudang='' and tanggal = ? and kode_jenis like ?");
        ps.setString(1, sistem.getTglSystem());
        ps.setString(2, kodeJenis);
        ResultSet rs = ps.executeQuery();
        StokBarang stok = null;
        while (rs.next()) {
            stok = new StokBarang();
            stok.setTanggal(rs.getString(1));
            stok.setKodeBarcode(rs.getString(2));
            stok.setKodeKategori(rs.getString(3));
            stok.setKodeJenis(rs.getString(4));
            stok.setKodeGudang(rs.getString(5));
            stok.setBeratAwal(rs.getDouble(6));
            stok.setBeratAsliAwal(rs.getDouble(7));
            stok.setStokAwal(rs.getInt(8));
            stok.setBeratMasuk(rs.getDouble(9));
            stok.setBeratAsliMasuk(rs.getDouble(10));
            stok.setStokMasuk(rs.getInt(11));
            stok.setBeratKeluar(rs.getDouble(12));
            stok.setBeratAsliKeluar(rs.getDouble(13));
            stok.setStokKeluar(rs.getInt(14));
            stok.setBeratAkhir(rs.getDouble(15));
            stok.setBeratAsliAkhir(rs.getDouble(16));
            stok.setStokAkhir(rs.getInt(17));
        }
        return stok;
    }

    public static List<StokBarang> getStokNonBarcodeByDate(Connection con, String date) throws Exception {
        PreparedStatement ps = con.prepareStatement("select * from tt_stok_barang where kode_barcode='' "
                + "and kode_gudang='' and tanggal = ? ");
        ps.setString(1, date);
        ResultSet rs = ps.executeQuery();
        List<StokBarang> allStokBarang = new ArrayList<>();
        while (rs.next()) {
            StokBarang stok = new StokBarang();
            stok.setTanggal(rs.getString(1));
            stok.setKodeBarcode(rs.getString(2));
            stok.setKodeKategori(rs.getString(3));
            stok.setKodeJenis(rs.getString(4));
            stok.setKodeGudang(rs.getString(5));
            stok.setBeratAwal(rs.getDouble(6));
            stok.setBeratAsliAwal(rs.getDouble(7));
            stok.setStokAwal(rs.getInt(8));
            stok.setBeratMasuk(rs.getDouble(9));
            stok.setBeratAsliMasuk(rs.getDouble(10));
            stok.setStokMasuk(rs.getInt(11));
            stok.setBeratKeluar(rs.getDouble(12));
            stok.setBeratAsliKeluar(rs.getDouble(13));
            stok.setStokKeluar(rs.getInt(14));
            stok.setBeratAkhir(rs.getDouble(15));
            stok.setBeratAsliAkhir(rs.getDouble(16));
            stok.setStokAkhir(rs.getInt(17));
            allStokBarang.add(stok);
        }
        return allStokBarang;
    }

    public static void update(Connection con, StokBarang stok) throws Exception {
        PreparedStatement ps = con.prepareStatement("update tt_stok_barang set "
                + " berat_awal=?, berat_asli_awal=?, stok_awal=?,"
                + " berat_masuk=?, berat_asli_masuk=?, stok_masuk=?,"
                + " berat_keluar=?, berat_asli_keluar=?, stok_keluar=?,"
                + " berat_akhir=?, berat_asli_akhir=?, stok_akhir=? "
                + " where tanggal=? and kode_barcode=? and kode_kategori=? and kode_jenis=? and kode_gudang=?");
        ps.setDouble(1, stok.getBeratAwal());
        ps.setDouble(2, stok.getBeratAsliAwal());
        ps.setInt(3, stok.getStokAwal());
        ps.setDouble(4, stok.getBeratMasuk());
        ps.setDouble(5, stok.getBeratAsliMasuk());
        ps.setInt(6, stok.getStokMasuk());
        ps.setDouble(7, stok.getBeratKeluar());
        ps.setDouble(8, stok.getBeratAsliKeluar());
        ps.setInt(9, stok.getStokKeluar());
        ps.setDouble(10, stok.getBeratAkhir());
        ps.setDouble(11, stok.getBeratAsliAkhir());
        ps.setInt(12, stok.getStokAkhir());
        ps.setString(13, stok.getTanggal());
        ps.setString(14, stok.getKodeBarcode());
        ps.setString(15, stok.getKodeKategori());
        ps.setString(16, stok.getKodeJenis());
        ps.setString(17, stok.getKodeGudang());
        ps.executeUpdate();
    }

    public static void insert(Connection con, StokBarang stok) throws Exception {
        PreparedStatement ps = con.prepareStatement("insert into tt_stok_barang values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
        ps.setString(1, stok.getTanggal());
        ps.setString(2, stok.getKodeBarcode());
        ps.setString(3, stok.getKodeKategori());
        ps.setString(4, stok.getKodeJenis());
        ps.setString(5, stok.getKodeGudang());
        ps.setDouble(6, stok.getBeratAwal());
        ps.setDouble(7, stok.getBeratAsliAwal());
        ps.setInt(8, stok.getStokAwal());
        ps.setDouble(9, stok.getBeratMasuk());
        ps.setDouble(10, stok.getBeratAsliMasuk());
        ps.setInt(11, stok.getStokMasuk());
        ps.setDouble(12, stok.getBeratKeluar());
        ps.setDouble(13, stok.getBeratAsliKeluar());
        ps.setInt(14, stok.getStokKeluar());
        ps.setDouble(15, stok.getBeratAkhir());
        ps.setDouble(16, stok.getBeratAsliAkhir());
        ps.setInt(17, stok.getStokAkhir());
        ps.executeUpdate();
    }

    public static void insertNewStokBarang(Connection con) throws Exception {
        PreparedStatement ps = con.prepareStatement("insert into tt_stok_barang "
                + " select ?,kode_barcode,kode_kategori,kode_jenis,kode_gudang,"
                + " berat_akhir,berat_asli_akhir,stok_akhir,"
                + " 0,0,0,"
                + " 0,0,0,"
                + " berat_akhir,berat_asli_akhir,stok_akhir "
                + " from tt_stok_barang "
                + " where tanggal=? and berat_akhir!=0 and stok_akhir!=0");
        LocalDate yesterday = LocalDate.parse(sistem.getTglSystem(), DateTimeFormatter.ISO_DATE);
        LocalDate today = yesterday.plusDays(1);
        ps.setString(1, today.toString());
        ps.setString(2, yesterday.toString());
        ps.executeUpdate();
    }

    public static Boolean checkKodeJenis(Connection con, String kodeJenis) throws Exception {
        PreparedStatement ps = con.prepareStatement("select kode_jenis from tt_stok_barang where tanggal = ? and kode_jenis=?");
        ps.setString(1, sistem.getTglSystem());
        ps.setString(2, kodeJenis);
        ResultSet rs = ps.executeQuery();
        return rs.next();
    }

    public static Boolean checkKodeGudang(Connection con, String kodeGudang) throws Exception {
        PreparedStatement ps = con.prepareStatement("select kode_gudang from tt_stok_barang where tanggal = ? and kode_gudang=?");
        ps.setString(1, sistem.getTglSystem());
        ps.setString(2, kodeGudang);
        ResultSet rs = ps.executeQuery();
        return rs.next();
    }

    public static void updateKategori(Connection con, Jenis jenis) throws Exception {
        PreparedStatement ps = con.prepareStatement("update tt_stok_barang set kode_kategori=? where kode_jenis=?");
        ps.setString(1, jenis.getKodeKategori());
        ps.setString(2, jenis.getKodeJenis());
        ps.executeUpdate();
    }
}
