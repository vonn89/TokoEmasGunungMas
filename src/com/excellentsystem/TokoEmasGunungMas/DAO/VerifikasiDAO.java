/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasGunungMas.DAO;

import com.excellentsystem.TokoEmasGunungMas.Model.User;
import com.excellentsystem.TokoEmasGunungMas.Model.Verifikasi;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Xtreme
 */
public class VerifikasiDAO {
    public static List<Verifikasi> getAll(Connection con)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tm_verifikasi");
        ResultSet rs = ps.executeQuery();
        List<Verifikasi> allVerifikasi = new ArrayList<>();
        while(rs.next()){
            Verifikasi verifikasi = new Verifikasi();
            verifikasi.setUsername(rs.getString(1));
            verifikasi.setJenis(rs.getString(2));
            verifikasi.setStatus(Boolean.parseBoolean(rs.getString(3)));
            allVerifikasi.add(verifikasi);
        }
        return allVerifikasi;
    }
    public static void update(Connection con, Verifikasi verifikasi)throws Exception{
        PreparedStatement ps = con.prepareStatement("update tm_verifikasi set status=? where username=? and jenis=?");
        ps.setString(1, String.valueOf(verifikasi.isStatus()));
        ps.setString(2, verifikasi.getUsername());
        ps.setString(3, verifikasi.getJenis());
        ps.executeUpdate();
    }
    public static void insert(Connection con, Verifikasi verifikasi)throws Exception{
        PreparedStatement ps = con.prepareStatement("insert into tm_verifikasi values(?,?,?)");
        ps.setString(1, verifikasi.getUsername());
        ps.setString(2, verifikasi.getJenis());
        ps.setString(3, String.valueOf(verifikasi.isStatus()));
        ps.executeUpdate();
    }
    public static void delete(Connection con, User user)throws Exception{
        PreparedStatement ps = con.prepareStatement("delete from tm_verifikasi where username=?");
        ps.setString(1, user.getUsername());
        ps.executeUpdate();
    }
}
