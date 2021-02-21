/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasGunungMas.DAO;

import static com.excellentsystem.TokoEmasGunungMas.Main.sistem;
import static com.excellentsystem.TokoEmasGunungMas.Main.tglBarang;
import static com.excellentsystem.TokoEmasGunungMas.Main.tglSystem;
import com.excellentsystem.TokoEmasGunungMas.Model.StokOpnameHead;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;

/**
 *
 * @author Yunaz
 */
public class StokOpnameHeadDAO {
    public static void insert(Connection con, StokOpnameHead s)throws Exception{
        PreparedStatement ps = con.prepareStatement("insert into tt_stok_opname_head values (?,?,?,?,?,?,?)");
        ps.setString(1, s.getNoStokOpname());
        ps.setString(2, s.getTglStokOpname());
        ps.setString(3, s.getKodeGudang());
        ps.setString(4, s.getKodeKategori());
        ps.setString(5, s.getKodeJenis());
        ps.setInt(6, s.getTotalQty());
        ps.setDouble(7, s.getTotalBerat());
        ps.executeUpdate();
    }
    public static StokOpnameHead get(Connection con, String noStokOpnameHead)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tt_stok_opname_head where no_stok_opname=?");
        ps.setString(1, noStokOpnameHead);
        ResultSet rs = ps.executeQuery();
        StokOpnameHead s =null;
        while(rs.next()){
            s = new StokOpnameHead();
            s.setNoStokOpname(rs.getString(1));
            s.setTglStokOpname(rs.getString(2));
            s.setKodeGudang(rs.getString(3));
            s.setKodeKategori(rs.getString(4));
            s.setKodeJenis(rs.getString(5));
            s.setTotalQty(rs.getInt(6));
            s.setTotalBerat(rs.getDouble(7));
        }
        return s;
    }
    public static String getId(Connection con)throws Exception{
        PreparedStatement ps =  con.prepareStatement("select max(right(no_stok_opname,4)) from tt_stok_opname_head where mid(no_stok_opname,4,6)=? ");
        String noStok = "SO-"+tglSystem.format(tglBarang.parse(sistem.getTglSystem()))+"-"+new DecimalFormat("0000").format(1);
        ps.setString(1, tglSystem.format(tglBarang.parse(sistem.getTglSystem())));
        ResultSet rs = ps.executeQuery();
        if(rs.next())
            noStok = "SO-"+tglSystem.format(tglBarang.parse(sistem.getTglSystem()))+"-"+new DecimalFormat("0000").format(rs.getInt(1)+1);
        return noStok;
    }
}
