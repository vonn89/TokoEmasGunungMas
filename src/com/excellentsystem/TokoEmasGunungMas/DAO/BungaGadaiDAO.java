/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasGunungMas.DAO;

import com.excellentsystem.TokoEmasGunungMas.Model.BungaGadai;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Yunaz
 */
public class BungaGadaiDAO {
    
    public static List<BungaGadai> getAll(Connection con)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tm_bunga_gadai ");
        ResultSet rs = ps.executeQuery();
        List<BungaGadai> allBungaGadai = new ArrayList<>();
        while(rs.next()){
            BungaGadai bungaGadai = new BungaGadai();
            bungaGadai.setMin(rs.getDouble(1));
            bungaGadai.setMax(rs.getDouble(2));
            bungaGadai.setBunga(rs.getDouble(3));
            allBungaGadai.add(bungaGadai);
        }
        return allBungaGadai;
    }
    public static void insert(Connection con, BungaGadai bungaGadai)throws Exception{
        PreparedStatement ps = con.prepareStatement("insert into tm_bunga_gadai values(?,?,?)");
        ps.setDouble(1, bungaGadai.getMin());
        ps.setDouble(2, bungaGadai.getMax());
        ps.setDouble(3, bungaGadai.getBunga());
        ps.executeUpdate();
    }
    public static void delete(Connection con, BungaGadai bunga)throws Exception{
        PreparedStatement ps = con.prepareStatement("delete from tm_bunga_gadai where min=? and max=? and bunga=?");
        ps.setDouble(1, bunga.getMin());
        ps.setDouble(2, bunga.getMax());
        ps.setDouble(3, bunga.getBunga());
        ps.executeUpdate();
    }
}
