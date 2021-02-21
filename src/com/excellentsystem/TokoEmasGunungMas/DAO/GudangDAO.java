/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasGunungMas.DAO;

import com.excellentsystem.TokoEmasGunungMas.Model.Gudang;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Xtreme
 */
public class GudangDAO {

    
    public static List<Gudang> getAll(Connection con)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tm_gudang ");
        ResultSet rs = ps.executeQuery();
        List<Gudang> allGudang = new ArrayList<>();
        while(rs.next()){
            Gudang g = new Gudang();
            g.setKodeGudang(rs.getString(1));
            g.setBeratBaki(rs.getDouble(2));
            allGudang.add(g);
        }
        return allGudang;
    }
    public static Gudang get(Connection con, String kodeGudang)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tm_gudang where kode_gudang=?");
        ps.setString(1, kodeGudang);
        ResultSet rs = ps.executeQuery();
        Gudang g = null;
        while(rs.next()){
            g = new Gudang();
            g.setKodeGudang(rs.getString(1));
            g.setBeratBaki(rs.getDouble(2));
        }
        return g;
    }
    public static void update(Connection con, Gudang g)throws Exception{
        PreparedStatement ps = con.prepareStatement("update tm_gudang set berat_baki=? where kode_gudang=?");
        ps.setDouble(1, g.getBeratBaki());
        ps.setString(2, g.getKodeGudang());
        ps.executeUpdate();
    }
    public static void insert(Connection con, Gudang g)throws Exception{
        PreparedStatement ps = con.prepareStatement("insert into tm_gudang values(?,?)");
        ps.setString(1, g.getKodeGudang());
        ps.setDouble(2, g.getBeratBaki());
        ps.executeUpdate();
    }
    public static void delete(Connection con, String kodeGudang)throws Exception{
        PreparedStatement ps = con.prepareStatement("delete from tm_gudang where kode_gudang=?");
        ps.setString(1, kodeGudang);
        ps.executeUpdate();
    }
}
