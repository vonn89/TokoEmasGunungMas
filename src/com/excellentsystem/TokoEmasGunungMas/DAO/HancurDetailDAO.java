/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasGunungMas.DAO;

import com.excellentsystem.TokoEmasGunungMas.Model.HancurDetail;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Yunaz
 */
public class HancurDetailDAO {

    public static List<HancurDetail> getAllByTglHancur(Connection con, String tglMulai, String tglAkhir) throws Exception {
        PreparedStatement ps = con.prepareStatement("select * from tt_hancur_detail "
                + " where no_hancur in (select no_hancur from tt_hancur_head where left(tgl_hancur,10) between ? and ? )");
        ps.setString(1, tglMulai);
        ps.setString(2, tglAkhir);
        ResultSet rs = ps.executeQuery();
        List<HancurDetail> allHancurDetail = new ArrayList<>();
        while (rs.next()) {
            HancurDetail hancur = new HancurDetail();
            hancur.setNoHancur(rs.getString(1));
            hancur.setKodeBarcode(rs.getString(2));
            allHancurDetail.add(hancur);
        }
        return allHancurDetail;
    }

    public static void insert(Connection con, HancurDetail hancur) throws Exception {
        PreparedStatement ps = con.prepareStatement("insert into tt_hancur_detail values (?,?)");
        ps.setString(1, hancur.getNoHancur());
        ps.setString(2, hancur.getKodeBarcode());
        ps.executeUpdate();
    }
}
