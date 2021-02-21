/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasGunungMas.DAO;

import com.excellentsystem.TokoEmasGunungMas.Model.TambahBarangDetail;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Xtreme
 */
public class TambahBarangDetailDAO {

    public static List<TambahBarangDetail> getAllByTglTambah(Connection con, String tglMulai, String tglAkhir) throws Exception {
        PreparedStatement ps = con.prepareStatement("select * from tt_tambah_barang_detail where no_tambah in "
                + "( select no_tambah from tt_tambah_barang_head where left(tgl_tambah,10) between ? and ?) ");
        ps.setString(1, tglMulai);
        ps.setString(2, tglAkhir);
        ResultSet rs = ps.executeQuery();
        List<TambahBarangDetail> allTambahBarangDetail = new ArrayList<>();
        while (rs.next()) {
            TambahBarangDetail t = new TambahBarangDetail();
            t.setNoTambah(rs.getString(1));
            t.setNoUrut(rs.getInt(2));
            t.setKodeKategori(rs.getString(3));
            t.setKodeJenis(rs.getString(4));
            t.setQty(rs.getInt(5));
            t.setBerat(rs.getDouble(6));
            allTambahBarangDetail.add(t);
        }
        return allTambahBarangDetail;
    }

    public static void insert(Connection con, TambahBarangDetail t) throws Exception {
        PreparedStatement ps = con.prepareStatement("insert into tt_tambah_barang_detail values (?,?,?,?,?,?)");
        ps.setString(1, t.getNoTambah());
        ps.setInt(2, t.getNoUrut());
        ps.setString(3, t.getKodeKategori());
        ps.setString(4, t.getKodeJenis());
        ps.setInt(5, t.getQty());
        ps.setDouble(6, t.getBerat());
        ps.executeUpdate();
    }
}
