/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasGunungMas.DAO;

import static com.excellentsystem.TokoEmasGunungMas.Main.sistem;
import static com.excellentsystem.TokoEmasGunungMas.Main.tglBarang;
import static com.excellentsystem.TokoEmasGunungMas.Main.tglSystem;
import com.excellentsystem.TokoEmasGunungMas.Model.PindahHead;
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
public class PindahHeadDAO {
    
    public static List<PindahHead> getAllByTglPindah(Connection con, String tglMulai,String tglAkhir)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tt_pindah_head "
                + " where left(tgl_pindah,10) between ? and ? ");
        ps.setString(1, tglMulai);
        ps.setString(2, tglAkhir);
        ResultSet rs = ps.executeQuery();
        List<PindahHead> allPindahHead = new ArrayList<>();
        while(rs.next()){
            PindahHead p = new PindahHead();
            p.setNoPindah(rs.getString(1));
            p.setTglPindah(rs.getString(2));
            p.setGudangTujuan(rs.getString(3));
            p.setTotalQty(rs.getInt(4));
            p.setTotalBerat(rs.getDouble(5));
            p.setTotalBeratAsli(rs.getDouble(6));
            p.setKodeUser(rs.getString(7));
            allPindahHead.add(p);
        }
        return allPindahHead;
    }
    public static void insert(Connection con, PindahHead p)throws Exception{
        PreparedStatement ps = con.prepareStatement("insert into tt_pindah_head values (?,?,?,?,?,?,?)");
        ps.setString(1, p.getNoPindah());
        ps.setString(2, p.getTglPindah());
        ps.setString(3, p.getGudangTujuan());
        ps.setInt(4, p.getTotalQty());
        ps.setDouble(5, p.getTotalBerat());
        ps.setDouble(6, p.getTotalBeratAsli());
        ps.setString(7, p.getKodeUser());
        ps.executeUpdate();
    }
    public static String getId(Connection con)throws Exception{
        PreparedStatement ps =  con.prepareStatement("select max(right(no_pindah,4)) from tt_pindah_head "
                + " where mid(no_pindah,4,6)=? ");
        String noPindah = "PB-"+tglSystem.format(tglBarang.parse(sistem.getTglSystem()))+"-"+new DecimalFormat("0000").format(1);
        ps.setString(1, tglSystem.format(tglBarang.parse(sistem.getTglSystem())));
        ResultSet rs = ps.executeQuery();
        if(rs.next())
            noPindah = "PB-"+tglSystem.format(tglBarang.parse(sistem.getTglSystem()))+"-"+new DecimalFormat("0000").format(rs.getInt(1)+1);
        return noPindah;
    }
}
