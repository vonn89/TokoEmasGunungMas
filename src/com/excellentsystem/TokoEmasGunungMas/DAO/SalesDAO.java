/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasGunungMas.DAO;

import com.excellentsystem.TokoEmasGunungMas.Model.Sales;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Xtreme
 */
public class SalesDAO {

    public static List<Sales> getAll(Connection con)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tm_sales where status='true'");
        ResultSet rs = ps.executeQuery();
        List<Sales> allSales = new ArrayList<>();
        while(rs.next()){
            Sales s = new Sales();
            s.setNama(rs.getString(1));
            s.setAlamat(rs.getString(2));
            s.setNoTelp(rs.getString(3));
            s.setNoHandphone(rs.getString(4));;
            s.setKeterangan(rs.getString(5));
            s.setIdentitas(rs.getString(6));
            s.setNoIdentitas(rs.getString(7));
            s.setStatus(rs.getString(8));
            allSales.add(s);
        }
        return allSales;
    }
    public static void update(Connection con, Sales s)throws Exception{
        PreparedStatement ps = con.prepareStatement("update tm_sales set alamat=?, no_telp=?, "
            + " no_handphone=?, keterangan=?, identitas=?, no_identitas=?, status=?"
            + " where nama=?");
        ps.setString(1, s.getAlamat());
        ps.setString(2, s.getNoTelp());
        ps.setString(3, s.getNoHandphone());
        ps.setString(4, s.getKeterangan());
        ps.setString(5, s.getIdentitas());
        ps.setString(6, s.getNoIdentitas());
        ps.setString(7, s.getStatus());
        ps.setString(8, s.getNama());
        ps.executeUpdate();
    }
    public static void insert(Connection con, Sales s)throws Exception{
        PreparedStatement ps = con.prepareStatement("insert into tm_sales values(?,?,?,?,?,?,?,?)");
        ps.setString(1, s.getNama());
        ps.setString(2, s.getAlamat());
        ps.setString(3, s.getNoTelp());
        ps.setString(4, s.getNoHandphone());
        ps.setString(5, s.getKeterangan());
        ps.setString(6, s.getIdentitas());
        ps.setString(7, s.getNoIdentitas());
        ps.setString(8, s.getStatus());
        ps.executeUpdate();
    }
    public static void delete(Connection con, String s)throws Exception{
        PreparedStatement ps = con.prepareStatement("update tm_sales set status='false' where nama=?");
        ps.setString(1, s);
        ps.executeUpdate();
    }
}
