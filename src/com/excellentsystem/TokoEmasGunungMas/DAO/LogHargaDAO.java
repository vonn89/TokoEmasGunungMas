/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasGunungMas.DAO;

import com.excellentsystem.TokoEmasGunungMas.Model.LogHarga;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Yunaz
 */
public class LogHargaDAO {

    public static void insert(Connection con, LogHarga log)throws Exception{
        PreparedStatement ps = con.prepareStatement("insert into tm_log_harga values(?,?,?,?,?)");
        ps.setString(1, log.getTanggal());
        ps.setString(2, log.getKodeKategori());
        ps.setDouble(3, log.getHargaBeli());
        ps.setDouble(4, log.getHargaJual());
        ps.setString(5, log.getUser());
        ps.executeUpdate();
    }
    public static List<LogHarga> getAllByDate(Connection con, String tglMulai,String tglAkhir)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tm_log_harga where tanggal between ? and ? ");
        ps.setString(1, tglMulai);
        ps.setString(2, tglAkhir);
        ResultSet rs = ps.executeQuery();
        List<LogHarga> allLogHarga = new ArrayList<>();
        while(rs.next()){
            LogHarga l = new LogHarga();
            l.setTanggal(rs.getString(1));
            l.setKodeKategori(rs.getString(2));
            l.setHargaBeli(rs.getDouble(3));
            l.setHargaJual(rs.getDouble(4));
            l.setUser(rs.getString(5));
            allLogHarga.add(l);
        }
        return allLogHarga;
    }
}
