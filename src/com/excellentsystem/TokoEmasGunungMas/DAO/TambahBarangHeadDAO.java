/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasGunungMas.DAO;

import static com.excellentsystem.TokoEmasGunungMas.Main.sistem;
import static com.excellentsystem.TokoEmasGunungMas.Main.tglBarang;
import static com.excellentsystem.TokoEmasGunungMas.Main.tglSystem;
import com.excellentsystem.TokoEmasGunungMas.Model.TambahBarangHead;
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
public class TambahBarangHeadDAO {
    public static List<TambahBarangHead> getAllByTglTambah(Connection con, String tglMulai,String tglAkhir )throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tt_tambah_barang_head "
                + " where left(tgl_tambah,10) between ? and ?  ");
        ps.setString(1, tglMulai);
        ps.setString(2, tglAkhir);
        ResultSet rs = ps.executeQuery();
        List<TambahBarangHead> allTambahBarangHead = new ArrayList<>();
        while(rs.next()){
            TambahBarangHead t = new TambahBarangHead();
            t.setNoTambah(rs.getString(1));
            t.setTglTambah(rs.getString(2));
            t.setKeterangan(rs.getString(3));
            t.setTotalQty(rs.getInt(4));
            t.setTotalBerat(rs.getDouble(5));
            t.setKodeUser(rs.getString(6));
            allTambahBarangHead.add(t);
        }
        return allTambahBarangHead;
    }
    public static void insert(Connection con, TambahBarangHead t)throws Exception{
        PreparedStatement ps = con.prepareStatement("insert into tt_tambah_barang_head values (?,?,?,?,?,?)");
        ps.setString(1, t.getNoTambah());
        ps.setString(2, t.getTglTambah());
        ps.setString(3, t.getKeterangan());
        ps.setInt(4, t.getTotalQty());
        ps.setDouble(5, t.getTotalBerat());
        ps.setString(6, t.getKodeUser());
        ps.executeUpdate();
    }
    public static String getId(Connection con)throws Exception{
        PreparedStatement ps =  con.prepareStatement("select max(right(no_tambah,4)) from tt_tambah_barang_head where mid(no_tambah,4,6)=? ");
        String noTambah = "TB-"+tglSystem.format(tglBarang.parse(sistem.getTglSystem()))+"-"+new DecimalFormat("0000").format(1);
        ps.setString(1, tglSystem.format(tglBarang.parse(sistem.getTglSystem())));
        ResultSet rs = ps.executeQuery();
        if(rs.next())
            noTambah = "TB-"+tglSystem.format(tglBarang.parse(sistem.getTglSystem()))+"-"+new DecimalFormat("0000").format(rs.getInt(1)+1);
        return noTambah;
    }
}
