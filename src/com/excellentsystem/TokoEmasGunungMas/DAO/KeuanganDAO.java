/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasGunungMas.DAO;

import static com.excellentsystem.TokoEmasGunungMas.Main.sistem;
import static com.excellentsystem.TokoEmasGunungMas.Main.tglBarang;
import static com.excellentsystem.TokoEmasGunungMas.Main.tglSystem;
import com.excellentsystem.TokoEmasGunungMas.Model.Keuangan;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Yunaz
 */
public class KeuanganDAO {

    public static List<Keuangan> getOmzetByDate(Connection con, String tglMulai, String tglAkhir) throws Exception {
        PreparedStatement ps = con.prepareStatement("select '',left(tgl_keuangan,10),kategori,'',sum(jumlah_rp),'' "
                + " from tt_keuangan where left(tgl_keuangan,10) between ? and ? "
                + " group by left(tgl_keuangan,10), kategori ");
        ps.setString(1, tglMulai);
        ps.setString(2, tglAkhir);
        ResultSet rs = ps.executeQuery();
        List<Keuangan> allKeuangan = new ArrayList<>();
        while (rs.next()) {
            Keuangan keu = new Keuangan();
            keu.setNoKeuangan(rs.getString(1));
            keu.setTglKeuangan(rs.getString(2));
            keu.setKategori(rs.getString(3));
            keu.setDeskripsi(rs.getString(4));
            keu.setJumlahRp(rs.getDouble(5));
            keu.setKodeUser(rs.getString(6));
            allKeuangan.add(keu);
        }
        return allKeuangan;
    }

    public static List<Keuangan> getAllByDate(Connection con, String tglMulai, String tglAkhir) throws Exception {
        PreparedStatement ps = con.prepareStatement("select * from tt_keuangan "
                + " where left(tgl_keuangan,10) between ? and ? ");
        ps.setString(1, tglMulai);
        ps.setString(2, tglAkhir);
        ResultSet rs = ps.executeQuery();
        List<Keuangan> allKeuangan = new ArrayList<>();
        while (rs.next()) {
            Keuangan keu = new Keuangan();
            keu.setNoKeuangan(rs.getString(1));
            keu.setTglKeuangan(rs.getString(2));
            keu.setKategori(rs.getString(3));
            keu.setDeskripsi(rs.getString(4));
            keu.setJumlahRp(rs.getDouble(5));
            keu.setKodeUser(rs.getString(6));
            allKeuangan.add(keu);
        }
        return allKeuangan;
    }

    public static Double getSaldoAwal(Connection con, String tanggal) throws Exception {
        PreparedStatement ps = con.prepareStatement("select sum(jumlah_rp) "
                + " from tt_keuangan where left(tgl_keuangan,10)  < ? ");
        ps.setString(1, tanggal);
        ResultSet rs = ps.executeQuery();
        double saldoAwal = 0;
        if (rs.next()) {
            saldoAwal = rs.getDouble(1);
        }
        return saldoAwal;
    }

    public static Double getSaldoAkhir(Connection con, String tanggal) throws Exception {
        PreparedStatement ps = con.prepareStatement("select sum(jumlah_rp) "
                + " from tt_keuangan where left(tgl_keuangan,10)  <= ? ");
        ps.setString(1, tanggal);
        ResultSet rs = ps.executeQuery();
        double saldoAkhir = 0;
        if (rs.next()) {
            saldoAkhir = rs.getDouble(1);
        }
        return saldoAkhir;
    }
    public static Keuangan get(Connection con, String kategori, String deskripsi, double jumlahRp) throws Exception {
        PreparedStatement ps = con.prepareStatement("select * from tt_keuangan "
                + " where kategori = ? and deskripsi = ? and jumlah_rp = ? ");
        ps.setString(1, kategori);
        ps.setString(2, deskripsi);
        ps.setDouble(3, jumlahRp);
        ResultSet rs = ps.executeQuery();
        Keuangan keu = null;
        while (rs.next()) {
            keu = new Keuangan();
            keu.setNoKeuangan(rs.getString(1));
            keu.setTglKeuangan(rs.getString(2));
            keu.setKategori(rs.getString(3));
            keu.setDeskripsi(rs.getString(4));
            keu.setJumlahRp(rs.getDouble(5));
            keu.setKodeUser(rs.getString(6));
        }
        return keu;
    }


    public static void insert(Connection con, Keuangan k) throws Exception {
        PreparedStatement ps = con.prepareStatement("insert into tt_keuangan values (?,?,?,?,?,?)");
        ps.setString(1, k.getNoKeuangan());
        ps.setString(2, k.getTglKeuangan());
        ps.setString(3, k.getKategori());
        ps.setString(4, k.getDeskripsi());
        ps.setDouble(5, k.getJumlahRp());
        ps.setString(6, k.getKodeUser());
        ps.executeUpdate();
    }

    public static String getId(Connection con) throws Exception {
        PreparedStatement ps = con.prepareStatement("select max(right(no_keuangan,4)) from tt_keuangan where mid(no_keuangan,4,6)=?");
        ps.setString(1, tglSystem.format(tglBarang.parse(sistem.getTglSystem())));
        ResultSet rs = ps.executeQuery();
        String no_keuangan = "KK-" + tglSystem.format(tglBarang.parse(sistem.getTglSystem())) + "-" + new DecimalFormat("0000").format(1);
        if (rs.next()) {
            no_keuangan = "KK-" + tglSystem.format(tglBarang.parse(sistem.getTglSystem())) + "-" + new DecimalFormat("0000").format(rs.getInt(1) + 1);
        }
        return no_keuangan;
    }

    public static void delete(Connection con, String noKeuangan) throws Exception {
        PreparedStatement ps = con.prepareStatement("delete from tt_keuangan where no_keuangan=?");
        ps.setString(1, noKeuangan);
        ps.executeUpdate();
    }
}
