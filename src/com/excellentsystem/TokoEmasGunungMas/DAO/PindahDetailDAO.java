/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasGunungMas.DAO;

import com.excellentsystem.TokoEmasGunungMas.Model.PindahDetail;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Yunaz
 */
public class PindahDetailDAO {

    public static List<PindahDetail> getAllByTglPindah(Connection con, String tglMulai, String tglAkhir) throws Exception {
        PreparedStatement ps = con.prepareStatement("select * from tt_pindah_detail "
                + "where no_pindah in (select no_pindah from tt_pindah_head where left(tgl_pindah,10) between ? and ? )");
        ps.setString(1, tglMulai);
        ps.setString(2, tglAkhir);
        ResultSet rs = ps.executeQuery();
        List<PindahDetail> allPindahDetail = new ArrayList<>();
        while (rs.next()) {
            PindahDetail pindah = new PindahDetail();
            pindah.setNoPindah(rs.getString(1));
            pindah.setKodeBarcode(rs.getString(2));
            pindah.setGudangAsal(rs.getString(3));
            allPindahDetail.add(pindah);
        }
        return allPindahDetail;
    }

    public static void insert(Connection con, PindahDetail pindah) throws Exception {
        PreparedStatement ps = con.prepareStatement("insert into tt_pindah_detail values (?,?,?)");
        ps.setString(1, pindah.getNoPindah());
        ps.setString(2, pindah.getKodeBarcode());
        ps.setString(3, pindah.getGudangAsal());
        ps.executeUpdate();
    }
}
