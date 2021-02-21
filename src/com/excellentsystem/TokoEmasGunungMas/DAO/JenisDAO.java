/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasGunungMas.DAO;

import com.excellentsystem.TokoEmasGunungMas.Model.Jenis;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Xtreme
 */
public class JenisDAO {

    public static List<Jenis> getAll(Connection con)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tm_jenis ");
        ResultSet rs = ps.executeQuery();
        List<Jenis> allJenis = new ArrayList<>();
        while(rs.next()){
            Jenis jenis = new Jenis();
            jenis.setKodeJenis(rs.getString(1));
            jenis.setNamaJenis(rs.getString(2));
            jenis.setKodeKategori(rs.getString(3));
            allJenis.add(jenis);
        }
        return allJenis;
    }
    public static void insert(Connection con, Jenis jenis)throws Exception{
        PreparedStatement ps = con.prepareStatement("insert into tm_jenis values(?,?,?)");
        ps.setString(1, jenis.getKodeJenis());
        ps.setString(2, jenis.getNamaJenis());
        ps.setString(3, jenis.getKodeKategori());
        ps.executeUpdate();
    }
    public static void update(Connection con, Jenis jenis)throws Exception{
        PreparedStatement ps = con.prepareStatement("update tm_jenis set nama_jenis=?,kode_kategori=? where kode_jenis=?");
        ps.setString(1, jenis.getNamaJenis());
        ps.setString(2, jenis.getKodeKategori());
        ps.setString(3, jenis.getKodeJenis());
        ps.executeUpdate();
    }
    public static void delete(Connection con, String kodeJenis)throws Exception{
        PreparedStatement ps = con.prepareStatement("delete from tm_jenis where kode_jenis=?");
        ps.setString(1, kodeJenis);
        ps.executeUpdate();
    }
}
