/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasGunungMas.DAO;

import com.excellentsystem.TokoEmasGunungMas.Model.AmbilBarangDetail;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Xtreme
 */
public class AmbilBarangDetailDAO {

    public static List<AmbilBarangDetail> getAllByTglAmbil(Connection con, String tglMulai, String tglAkhir) throws Exception {
        PreparedStatement ps = con.prepareStatement("select * from tt_ambil_barang_detail "
                + " where no_ambil in ( select no_ambil from tt_ambil_barang_head left(tgl_ambil,10) between ? and ? )");
        ps.setString(1, tglMulai);
        ps.setString(2, tglAkhir);
        ResultSet rs = ps.executeQuery();
        List<AmbilBarangDetail> allAmbilBarangDetail = new ArrayList<>();
        while (rs.next()) {
            AmbilBarangDetail ab = new AmbilBarangDetail();
            ab.setNoAmbil(rs.getString(1));
            ab.setNoUrut(rs.getInt(2));
            ab.setKodeKategori(rs.getString(3));
            ab.setKodeJenis(rs.getString(4));
            ab.setQty(rs.getInt(5));
            ab.setBerat(rs.getDouble(6));
            allAmbilBarangDetail.add(ab);
        }
        return allAmbilBarangDetail;
    }

    public static void insert(Connection con, AmbilBarangDetail ab) throws Exception {
        PreparedStatement ps = con.prepareStatement("insert into tt_ambil_barang_detail values (?,?,?,?,?,?)");
        ps.setString(1, ab.getNoAmbil());
        ps.setInt(2, ab.getNoUrut());
        ps.setString(3, ab.getKodeKategori());
        ps.setString(4, ab.getKodeJenis());
        ps.setInt(5, ab.getQty());
        ps.setDouble(6, ab.getBerat());
        ps.executeUpdate();
    }
}
