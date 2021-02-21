/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasGunungMas.DAO;

import static com.excellentsystem.TokoEmasGunungMas.Main.sistem;
import static com.excellentsystem.TokoEmasGunungMas.Main.tglBarang;
import static com.excellentsystem.TokoEmasGunungMas.Main.tglSystem;
import com.excellentsystem.TokoEmasGunungMas.Model.AmbilBarangHead;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Xtreme
 */
public class AmbilBarangHeadDAO {

    public static List<AmbilBarangHead> getAllByTglAmbil(Connection con, String tglMulai,String tglAkhir)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tt_ambil_barang_head "
                + " where left(tgl_ambil,10) between ? and ?");
        ps.setString(1, tglMulai);
        ps.setString(2, tglAkhir);
        ResultSet rs = ps.executeQuery();
        List<AmbilBarangHead> allAmbilBarangHead = new ArrayList<>();
        while(rs.next()){
            AmbilBarangHead ab = new AmbilBarangHead();
            ab.setNoAmbil(rs.getString(1));
            ab.setTglAmbil(rs.getString(2));
            ab.setKeterangan(rs.getString(3));
            ab.setTotalQty(rs.getInt(4));
            ab.setTotalBerat(rs.getDouble(5));
            ab.setKodeUser(rs.getString(6));
            allAmbilBarangHead.add(ab);
        }
        return allAmbilBarangHead;
    }
    public static void insert(Connection con, AmbilBarangHead ab)throws Exception{
        PreparedStatement ps = con.prepareStatement("insert into tt_ambil_barang_head values (?,?,?,?,?,?)");
        ps.setString(1, ab.getNoAmbil());
        ps.setString(2, ab.getTglAmbil());
        ps.setString(3, ab.getKeterangan());
        ps.setInt(4, ab.getTotalQty());
        ps.setDouble(5, ab.getTotalBerat());
        ps.setString(6, ab.getKodeUser());
        ps.executeUpdate();
    }
    public static String getId(Connection con)throws Exception{
        PreparedStatement ps =  con.prepareStatement("select max(right(no_ambil,4)) from tt_ambil_barang_head "
                + " where mid(no_ambil,4,6)=? ");
        String no_ambilBarang;
        DecimalFormat df = new DecimalFormat("0000");
        ps.setString(1, tglSystem.format(tglBarang.parse(sistem.getTglSystem())));
        ResultSet rs = ps.executeQuery();
        if(rs.next())
            no_ambilBarang = "AB-"+tglSystem.format(tglBarang.parse(sistem.getTglSystem()))+"-"+df.format(rs.getInt(1)+1);
        else
            no_ambilBarang= "AB-"+tglSystem.format(tglBarang.parse(sistem.getTglSystem()))+"-"+df.format(1);
        return no_ambilBarang;
    }
}
