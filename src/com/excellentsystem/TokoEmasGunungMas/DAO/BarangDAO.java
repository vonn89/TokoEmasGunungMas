/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasGunungMas.DAO;

import static com.excellentsystem.TokoEmasGunungMas.Main.sistem;
import com.excellentsystem.TokoEmasGunungMas.Model.Barang;
import com.excellentsystem.TokoEmasGunungMas.Model.Jenis;
import com.excellentsystem.TokoEmasGunungMas.Model.Kategori;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Yunaz
 */
public class BarangDAO {

    public static List<Barang> getAllByTglBarcode(Connection con, String mulaiDate, String akhirDate) throws Exception {
        PreparedStatement ps = con.prepareStatement("select * from tm_barang "
                + "where left(barcode_date,10) between ? and ? ");
        ps.setString(1, mulaiDate);
        ps.setString(2, akhirDate);
        ResultSet rs = ps.executeQuery();
        List<Barang> allBarang = new ArrayList<>();
        while (rs.next()) {
            Barang b = new Barang();
            b.setKodeBarcode(rs.getString(1));
            b.setNamaBarang(rs.getString(2));
            b.setKeterangan(rs.getString(3));
            b.setKodeKategori(rs.getString(4));
            b.setKodeJenis(rs.getString(5));
            b.setKodeGudang(rs.getString(6));
            b.setKodeIntern(rs.getString(7));
            b.setKadar(rs.getString(8));
            b.setBerat(rs.getDouble(9));
            b.setBeratAsli(rs.getDouble(10));
            b.setBeratKemasan(rs.getDouble(11));
            b.setNilaiPokok(rs.getDouble(12));
            b.setHargaJual(rs.getDouble(13));
            b.setStatusBarang(rs.getString(14));
            b.setBarcodeDate(rs.getString(15));
            b.setBarcodeBy(rs.getString(16));
            b.setDeletedDate(rs.getString(17));
            b.setDeletedBy(rs.getString(18));
            b.setSoldDate(rs.getString(19));
            b.setSoldBy(rs.getString(20));
            allBarang.add(b);
        }
        return allBarang;
    }

    public static List<Barang> getAll(Connection con, String status, String kodeBarcode, String kodeGudang,
            String kodeKategori, String kodeJenis) throws Exception {
        String sql = "select * from tm_barang where kode_barcode !='' ";
        if (!"Semua".equals(status)) {
            sql = sql + " and status_barang = '" + status + "' ";
        }
        if (!"Semua".equals(kodeBarcode)) {
            sql = sql + " and kode_barcode = '" + kodeBarcode + "' ";
        }
        if (!"Semua".equals(kodeGudang)) {
            sql = sql + " and kode_gudang = '" + kodeGudang + "' ";
        }
        if (!"Semua".equals(kodeKategori)) {
            sql = sql + " and kode_kategori = '" + kodeKategori + "' ";
        }
        if (!"Semua".equals(kodeJenis)) {
            sql = sql + " and kode_jenis = '" + kodeJenis + "' ";
        }
        PreparedStatement ps = con.prepareStatement(sql);
        List<Barang> allBarang = new ArrayList<>();
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Barang b = new Barang();
            b.setKodeBarcode(rs.getString(1));
            b.setNamaBarang(rs.getString(2));
            b.setKeterangan(rs.getString(3));
            b.setKodeKategori(rs.getString(4));
            b.setKodeJenis(rs.getString(5));
            b.setKodeGudang(rs.getString(6));
            b.setKodeIntern(rs.getString(7));
            b.setKadar(rs.getString(8));
            b.setBerat(rs.getDouble(9));
            b.setBeratAsli(rs.getDouble(10));
            b.setBeratKemasan(rs.getDouble(11));
            b.setNilaiPokok(rs.getDouble(12));
            b.setHargaJual(rs.getDouble(13));
            b.setStatusBarang(rs.getString(14));
            b.setBarcodeDate(rs.getString(15));
            b.setBarcodeBy(rs.getString(16));
            b.setDeletedDate(rs.getString(17));
            b.setDeletedBy(rs.getString(18));
            b.setSoldDate(rs.getString(19));
            b.setSoldBy(rs.getString(20));
            allBarang.add(b);
        }
        return allBarang;
    }

    public static Boolean checkKodeKemasan(Connection con, String kodeKemasan) throws Exception {
        PreparedStatement ps = con.prepareStatement("select * from tm_barang "
                + "where kode_kemasan = ?  and status_barang = 'Tersedia'");
        ps.setString(1, kodeKemasan);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return true;
        } else {
            return false;
        }
    }

    public static Barang get(Connection con, String kodeBarcode) throws Exception {
        PreparedStatement ps = con.prepareStatement("select * from tm_barang where kode_barcode= ?");
        ps.setString(1, kodeBarcode);
        Barang b = null;
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            b = new Barang();
            b.setKodeBarcode(rs.getString(1));
            b.setNamaBarang(rs.getString(2));
            b.setKeterangan(rs.getString(3));
            b.setKodeKategori(rs.getString(4));
            b.setKodeJenis(rs.getString(5));
            b.setKodeGudang(rs.getString(6));
            b.setKodeIntern(rs.getString(7));
            b.setKadar(rs.getString(8));
            b.setBerat(rs.getDouble(9));
            b.setBeratAsli(rs.getDouble(10));
            b.setBeratKemasan(rs.getDouble(11));
            b.setNilaiPokok(rs.getDouble(12));
            b.setHargaJual(rs.getDouble(13));
            b.setStatusBarang(rs.getString(14));
            b.setBarcodeDate(rs.getString(15));
            b.setBarcodeBy(rs.getString(16));
            b.setDeletedDate(rs.getString(17));
            b.setDeletedBy(rs.getString(18));
            b.setSoldDate(rs.getString(19));
            b.setSoldBy(rs.getString(20));
        }
        return b;
    }

    public static String getId(Connection con) throws Exception {
        PreparedStatement ps = con.prepareStatement("select max(right(kode_barcode,7)) from tm_barang where left(kode_barcode,1) like ? ");
        ps.setString(1, sistem.getPrefixBarcode());
        ResultSet rs = ps.executeQuery();
        String kodeBarcode = sistem.getPrefixBarcode() + "0000000";
        if (rs.next()) {
            kodeBarcode = sistem.getPrefixBarcode() + new DecimalFormat("0000000").format(rs.getInt(1) + 1);
        }
        return kodeBarcode;
    }

    public static void updateHarga(Connection con, Kategori k) throws Exception {
        PreparedStatement ps = con.prepareStatement("update tm_barang set harga_jual=ceil(?*berat/500)*500 where kode_kategori=? and status_barang='Tersedia'");
        ps.setDouble(1, k.getHargaJual());
        ps.setString(2, k.getKodeKategori());
        ps.executeUpdate();
    }

    public static void updateKategori(Connection con, Jenis j) throws Exception {
        PreparedStatement ps = con.prepareStatement("update tm_barang set kode_kategori=? where kode_jenis=? ");
        ps.setString(1, j.getKodeKategori());
        ps.setString(2, j.getKodeJenis());
        ps.executeUpdate();
    }

    public static void update(Connection con, Barang b) throws Exception {
        PreparedStatement ps = con.prepareStatement("update tm_barang set "
                + " nama_barang=?, keterangan=?, kode_kategori=?, kode_jenis=?, kode_gudang=?, "
                + " kode_intern=?, kadar=?, berat=?, berat_asli=?, berat_kemasan=?, nilai_pokok=?, harga_jual=?,"
                + " status_barang=?, barcode_date=?, barcode_by=?,deleted_date=?, deleted_by=?,"
                + " sold_date=?, sold_by=? where kode_barcode=?");
        ps.setString(1, b.getNamaBarang());
        ps.setString(2, b.getKeterangan());
        ps.setString(3, b.getKodeKategori());
        ps.setString(4, b.getKodeJenis());
        ps.setString(5, b.getKodeGudang());
        ps.setString(6, b.getKodeIntern());
        ps.setString(7, b.getKadar());
        ps.setDouble(8, b.getBerat());
        ps.setDouble(9, b.getBeratAsli());
        ps.setDouble(10, b.getBeratKemasan());
        ps.setDouble(11, b.getNilaiPokok());
        ps.setDouble(12, b.getHargaJual());
        ps.setString(13, b.getStatusBarang());
        ps.setString(14, b.getBarcodeDate());
        ps.setString(15, b.getBarcodeBy());
        ps.setString(16, b.getDeletedDate());
        ps.setString(17, b.getDeletedBy());
        ps.setString(18, b.getSoldDate());
        ps.setString(19, b.getSoldBy());
        ps.setString(20, b.getKodeBarcode());
        ps.executeUpdate();
    }

    public static void insert(Connection con, Barang b) throws Exception {
        PreparedStatement ps = con.prepareStatement("insert into tm_barang values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
        ps.setString(1, b.getKodeBarcode());
        ps.setString(2, b.getNamaBarang());
        ps.setString(3, b.getKeterangan());
        ps.setString(4, b.getKodeKategori());
        ps.setString(5, b.getKodeJenis());
        ps.setString(6, b.getKodeGudang());
        ps.setString(7, b.getKodeIntern());
        ps.setString(8, b.getKadar());
        ps.setDouble(9, b.getBerat());
        ps.setDouble(10, b.getBeratAsli());
        ps.setDouble(11, b.getBeratKemasan());
        ps.setDouble(12, b.getNilaiPokok());
        ps.setDouble(13, b.getHargaJual());
        ps.setString(14, b.getStatusBarang());
        ps.setString(15, b.getBarcodeDate());
        ps.setString(16, b.getBarcodeBy());
        ps.setString(17, b.getDeletedDate());
        ps.setString(18, b.getDeletedBy());
        ps.setString(19, b.getSoldDate());
        ps.setString(20, b.getSoldBy());
        ps.executeUpdate();
    }
}
