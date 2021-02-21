/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasGunungMas.DAO;

import com.excellentsystem.TokoEmasGunungMas.Model.KategoriTransaksi;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Xtreme
 */
public class KategoriTransaksiDAO {

    
    public static List<KategoriTransaksi> getAll(Connection con)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tm_kategori_transaksi ");
        ResultSet rs = ps.executeQuery();
        List<KategoriTransaksi> allKategoriTransaksi = new ArrayList<>();
        while(rs.next()){
            KategoriTransaksi kt = new KategoriTransaksi();
            kt.setKodeKategori(rs.getString(1));
            kt.setJenisTransaksi(rs.getString(2));
            allKategoriTransaksi.add(kt);
        }
        return allKategoriTransaksi;
    }
    public static void update(Connection con, KategoriTransaksi kt)throws Exception{
        PreparedStatement ps = con.prepareStatement("update tm_kategori_transaksi set jenis_transaksi=? where kode_kategori=?");
        ps.setString(1, kt.getJenisTransaksi());
        ps.setString(2, kt.getKodeKategori());
        ps.executeUpdate();
    }
    public static void insert(Connection con, KategoriTransaksi kt)throws Exception{
        PreparedStatement ps = con.prepareStatement("insert into tm_kategori_transaksi values(?,?)");
        ps.setString(1, kt.getKodeKategori());
        ps.setString(2, kt.getJenisTransaksi());
        ps.executeUpdate();
    }
    public static void delete(Connection con, String kodeKategori)throws Exception{
        PreparedStatement ps = con.prepareStatement("delete from tm_kategori_transaksi where kode_kategori=?");
        ps.setString(1, kodeKategori);
        ps.executeUpdate();
    }
}
