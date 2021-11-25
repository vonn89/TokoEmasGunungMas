/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasGunungMas.DAO;

import com.excellentsystem.TokoEmasGunungMas.Model.PenjualanDetail;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Yunaz
 */
public class PenjualanDetailDAO {

    public static List<PenjualanDetail> getAllByTglBatalAndStatus(Connection con, String tglMulai, String tglAkhir, String status) throws Exception {
        PreparedStatement ps = con.prepareStatement("select * from tt_penjualan_detail a, tt_penjualan_head b "
                + "where a.no_penjualan=b.no_penjualan and left(b.tgl_batal,10) between ? and ?  and b.status=?");
        ps.setString(1, tglMulai);
        ps.setString(2, tglAkhir);
        ps.setString(3, status);
        ResultSet rs = ps.executeQuery();
        List<PenjualanDetail> allDetail = new ArrayList<>();
        while (rs.next()) {
            PenjualanDetail d = new PenjualanDetail();
            d.setNoPenjualan(rs.getString(1));
            d.setKodeBarcode(rs.getString(2));
            d.setKodeKategori(rs.getString(3));
            d.setKodeJenis(rs.getString(4));
            d.setNamaBarang(rs.getString(5));
            d.setBerat(rs.getDouble(6));
            d.setNilaiPokok(rs.getDouble(7));
            d.setHargaKomp(rs.getDouble(8));
            d.setHargaJual(rs.getDouble(9));
            d.setNoPembelian(rs.getString(10));
            allDetail.add(d);
        }
        return allDetail;
    }

    public static List<PenjualanDetail> getAllByTglPenjualanAndStatus(Connection con, String tglMulai, String tglAkhir, String status) throws Exception {
        PreparedStatement ps = con.prepareStatement("select * from tt_penjualan_detail a, tt_penjualan_head b "
                + "where a.no_penjualan=b.no_penjualan and left(b.tgl_penjualan,10) between ? and ?  and b.status=?");
        ps.setString(1, tglMulai);
        ps.setString(2, tglAkhir);
        ps.setString(3, status);
        ResultSet rs = ps.executeQuery();
        List<PenjualanDetail> allDetail = new ArrayList<>();
        while (rs.next()) {
            PenjualanDetail d = new PenjualanDetail();
            d.setNoPenjualan(rs.getString(1));
            d.setKodeBarcode(rs.getString(2));
            d.setKodeKategori(rs.getString(3));
            d.setKodeJenis(rs.getString(4));
            d.setNamaBarang(rs.getString(5));
            d.setBerat(rs.getDouble(6));
            d.setNilaiPokok(rs.getDouble(7));
            d.setHargaKomp(rs.getDouble(8));
            d.setHargaJual(rs.getDouble(9));
            d.setNoPembelian(rs.getString(10));
            allDetail.add(d);
        }
        return allDetail;
    }
    public static List<PenjualanDetail> getAllByTglPenjualanAndStatusAndNoBuyBack(Connection con, String tglMulai, String tglAkhir, String status) throws Exception {
        PreparedStatement ps = con.prepareStatement("select * from tt_penjualan_detail a, tt_penjualan_head b "
                + "where a.no_penjualan=b.no_penjualan and left(b.tgl_penjualan,10) between ? and ?  and b.status=? and a.no_pembelian=''");
        ps.setString(1, tglMulai);
        ps.setString(2, tglAkhir);
        ps.setString(3, status);
        ResultSet rs = ps.executeQuery();
        List<PenjualanDetail> allDetail = new ArrayList<>();
        while (rs.next()) {
            PenjualanDetail d = new PenjualanDetail();
            d.setNoPenjualan(rs.getString(1));
            d.setKodeBarcode(rs.getString(2));
            d.setKodeKategori(rs.getString(3));
            d.setKodeJenis(rs.getString(4));
            d.setNamaBarang(rs.getString(5));
            d.setBerat(rs.getDouble(6));
            d.setNilaiPokok(rs.getDouble(7));
            d.setHargaKomp(rs.getDouble(8));
            d.setHargaJual(rs.getDouble(9));
            d.setNoPembelian(rs.getString(10));
            allDetail.add(d);
        }
        return allDetail;
    }
    public static PenjualanDetail getByKodeBarcode(Connection con, String kodeBarcode) throws Exception {
        PreparedStatement ps = con.prepareStatement("select * from tt_penjualan_detail a, tt_penjualan_head b "
                + "where a.no_penjualan=b.no_penjualan and a.kode_barcode=?  and b.status='true'");
        ps.setString(1, kodeBarcode);
        ResultSet rs = ps.executeQuery();
        PenjualanDetail d = null;
        while (rs.next()) {
            d = new PenjualanDetail();
            d.setNoPenjualan(rs.getString(1));
            d.setKodeBarcode(rs.getString(2));
            d.setKodeKategori(rs.getString(3));
            d.setKodeJenis(rs.getString(4));
            d.setNamaBarang(rs.getString(5));
            d.setBerat(rs.getDouble(6));
            d.setNilaiPokok(rs.getDouble(7));
            d.setHargaKomp(rs.getDouble(8));
            d.setHargaJual(rs.getDouble(9));
            d.setNoPembelian(rs.getString(10));
        }
        return d;
    }

    public static void update(Connection con, PenjualanDetail detail) throws Exception {
        PreparedStatement ps = con.prepareStatement("update tt_penjualan_detail set "
                + " kode_kategori = ?, kode_jenis = ?, nama_barang = ?, berat = ?, "
                + " nilai_pokok = ?, harga_komp = ?, harga_jual = ?, no_pembelian = ? "
                + " where no_penjualan = ? and kode_barcode = ?");
        ps.setString(1, detail.getKodeKategori());
        ps.setString(2, detail.getKodeJenis());
        ps.setString(3, detail.getNamaBarang());
        ps.setDouble(4, detail.getBerat());
        ps.setDouble(5, detail.getNilaiPokok());
        ps.setDouble(6, detail.getHargaKomp());
        ps.setDouble(7, detail.getHargaJual());
        ps.setString(8, detail.getNoPembelian());
        ps.setString(9, detail.getNoPenjualan());
        ps.setString(10, detail.getKodeBarcode());
        ps.executeUpdate();
    }
    public static void insert(Connection con, PenjualanDetail detail) throws Exception {
        PreparedStatement ps = con.prepareStatement("insert into tt_penjualan_detail values (?,?,?,?,?,?,?,?,?,?)");
        ps.setString(1, detail.getNoPenjualan());
        ps.setString(2, detail.getKodeBarcode());
        ps.setString(3, detail.getKodeKategori());
        ps.setString(4, detail.getKodeJenis());
        ps.setString(5, detail.getNamaBarang());
        ps.setDouble(6, detail.getBerat());
        ps.setDouble(7, detail.getNilaiPokok());
        ps.setDouble(8, detail.getHargaKomp());
        ps.setDouble(9, detail.getHargaJual());
        ps.setString(10, detail.getNoPembelian());
        ps.executeUpdate();
    }
}
