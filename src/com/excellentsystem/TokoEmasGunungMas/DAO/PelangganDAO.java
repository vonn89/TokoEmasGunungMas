/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasGunungMas.DAO;

import com.excellentsystem.TokoEmasGunungMas.Model.Pelanggan;
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
public class PelangganDAO {

    public static List<Pelanggan> getAll(Connection con) throws Exception {
        PreparedStatement ps = con.prepareStatement("select * from tm_pelanggan where status='true'");
        ResultSet rs = ps.executeQuery();
        List<Pelanggan> allPelanggan = new ArrayList<>();
        while (rs.next()) {
            Pelanggan p = new Pelanggan();
            p.setKodePelanggan(rs.getString(1));
            p.setNama(rs.getString(2));
            p.setAlamat(rs.getString(3));
            p.setNoTelp(rs.getString(4));
            p.setNoHandphone(rs.getString(5));;
            p.setKeterangan(rs.getString(6));
            p.setIdentitas(rs.getString(7));
            p.setNoIdentitas(rs.getString(8));
            p.setStatus(rs.getString(9));
            allPelanggan.add(p);
        }
        return allPelanggan;
    }

    public static String getId(Connection con) throws Exception {
        PreparedStatement ps = con.prepareStatement("select max(right(kode_pelanggan,5)) from tm_pelanggan");
        String kodePelanggan = "CS-" + new DecimalFormat("00000").format(1);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            kodePelanggan = "CS-" + new DecimalFormat("00000").format(rs.getInt(1) + 1);
        }
        return kodePelanggan;
    }

    public static void update(Connection con, Pelanggan p) throws Exception {
        PreparedStatement ps = con.prepareStatement("update tm_pelanggan set nama=?, alamat=?, no_telp=?, "
                + " no_handphone=?, keterangan=?, identitas=?, no_identitas=?, status=?"
                + " where kode_pelanggan=?");
        ps.setString(1, p.getNama());
        ps.setString(2, p.getAlamat());
        ps.setString(3, p.getNoTelp());
        ps.setString(4, p.getNoHandphone());
        ps.setString(5, p.getKeterangan());
        ps.setString(6, p.getIdentitas());
        ps.setString(7, p.getNoIdentitas());
        ps.setString(8, p.getStatus());
        ps.setString(9, p.getKodePelanggan());
        ps.executeUpdate();
    }

    public static void insert(Connection con, Pelanggan p) throws Exception {
        PreparedStatement ps = con.prepareStatement("insert into tm_pelanggan values(?,?,?,?,?,?,?,?,?)");
        ps.setString(1, p.getKodePelanggan());
        ps.setString(2, p.getNama());
        ps.setString(3, p.getAlamat());
        ps.setString(4, p.getNoTelp());
        ps.setString(5, p.getNoHandphone());
        ps.setString(6, p.getKeterangan());
        ps.setString(7, p.getIdentitas());
        ps.setString(8, p.getNoIdentitas());
        ps.setString(9, p.getStatus());
        ps.executeUpdate();
    }

    public static void delete(Connection con, String p) throws Exception {
        PreparedStatement ps = con.prepareStatement("update tm_pelanggan set status='false' where kode_pelanggan=?");
        ps.setString(1, p);
        ps.executeUpdate();
    }
}
