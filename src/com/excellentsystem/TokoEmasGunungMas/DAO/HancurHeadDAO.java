/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasGunungMas.DAO;

import static com.excellentsystem.TokoEmasGunungMas.Main.sistem;
import static com.excellentsystem.TokoEmasGunungMas.Main.tglBarang;
import static com.excellentsystem.TokoEmasGunungMas.Main.tglSystem;
import com.excellentsystem.TokoEmasGunungMas.Model.HancurHead;
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
public class HancurHeadDAO {

    public static List<HancurHead> getAllByTglHancur(Connection con, String tglMulai,String tglAkhir)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tt_hancur_head "
                + " where left(tgl_hancur,10) between ? and ? ");
        ps.setString(1, tglMulai);
        ps.setString(2, tglAkhir);
        ResultSet rs = ps.executeQuery();
        List<HancurHead> allHancurHead = new ArrayList<>();
        while(rs.next()){
            HancurHead hancur = new HancurHead();
            hancur.setNoHancur(rs.getString(1));
            hancur.setTglHancur(rs.getString(2));
            hancur.setTotalQty(rs.getInt(3));
            hancur.setTotalBerat(rs.getDouble(4));
            hancur.setTotalBeratAsli(rs.getDouble(5));
            hancur.setKodeUser(rs.getString(6));
            allHancurHead.add(hancur);
        }
        return allHancurHead;
    }
    public static void insert(Connection con, HancurHead hancur)throws Exception{
        PreparedStatement ps = con.prepareStatement("insert into tt_hancur_head values (?,?,?,?,?,?)");
        ps.setString(1, hancur.getNoHancur());
        ps.setString(2, hancur.getTglHancur());
        ps.setInt(3, hancur.getTotalQty());
        ps.setDouble(4, hancur.getTotalBerat());
        ps.setDouble(5, hancur.getTotalBeratAsli());
        ps.setString(6, hancur.getKodeUser());
        ps.executeUpdate();
    }
    public static String getId(Connection con)throws Exception{
        PreparedStatement ps =  con.prepareStatement("select max(right(no_hancur,4)) from tt_hancur_head "
                + " where mid(no_hancur,4,6)=? ");
        String no_hancur = "HB-"+tglSystem.format(tglBarang.parse(sistem.getTglSystem()))+"-"+new DecimalFormat("0000").format(1);
        ps.setString(1, tglSystem.format(tglBarang.parse(sistem.getTglSystem())));
        ResultSet rs = ps.executeQuery();
        if(rs.next())
            no_hancur = "HB-"+tglSystem.format(tglBarang.parse(sistem.getTglSystem()))+"-"+new DecimalFormat("0000").format(rs.getInt(1)+1);
        return no_hancur;
    }
}
