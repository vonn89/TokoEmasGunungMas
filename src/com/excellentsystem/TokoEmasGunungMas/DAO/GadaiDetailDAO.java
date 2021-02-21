/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasGunungMas.DAO;

import com.excellentsystem.TokoEmasGunungMas.Model.GadaiDetail;
import com.excellentsystem.TokoEmasGunungMas.Model.Kategori;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Yunaz
 */
public class GadaiDetailDAO {

    
    public static List<GadaiDetail> getAllByTglGadaiAndStatus(Connection con, String tglMulai,String tglAkhir,String status)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tm_gadai_detail a,tm_gadai_head b "
                + " where a.no_gadai=b.no_gadai and left(b.tgl_gadai,10) between ? and ? and b.status = ? ");
        ps.setString(1, tglMulai);
        ps.setString(2, tglAkhir);
        ps.setString(3, status);
        ResultSet rs = ps.executeQuery();
        List<GadaiDetail> allDetail = new ArrayList<>();
        while(rs.next()){
            GadaiDetail d = new GadaiDetail();
            d.setNoGadai(rs.getString(1));
            d.setNoUrut(rs.getInt(2));
            d.setKodeKategori(rs.getString(3));
            d.setNamaBarang(rs.getString(4));
            d.setBerat(rs.getDouble(5));
            d.setNilaiJual(rs.getDouble(6));
            d.setNilaiJualSekarang(rs.getDouble(7));
            allDetail.add(d);
        }
        return allDetail;
    }
    public static List<GadaiDetail> getAllByTglLunasAndStatus(Connection con, String tglMulai,String tglAkhir,String status)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tm_gadai_detail a,tm_gadai_head b "
                + " where a.no_gadai=b.no_gadai and left(b.tgl_lunas,10) between ? and ? and b.status = ? ");
        ps.setString(1, tglMulai);
        ps.setString(2, tglAkhir);
        ps.setString(3, status);
        ResultSet rs = ps.executeQuery();
        List<GadaiDetail> allDetail = new ArrayList<>();
        while(rs.next()){
            GadaiDetail d = new GadaiDetail();
            d.setNoGadai(rs.getString(1));
            d.setNoUrut(rs.getInt(2));
            d.setKodeKategori(rs.getString(3));
            d.setNamaBarang(rs.getString(4));
            d.setBerat(rs.getDouble(5));
            d.setNilaiJual(rs.getDouble(6));
            d.setNilaiJualSekarang(rs.getDouble(7));
            allDetail.add(d);
        }
        return allDetail;
    }
    public static List<GadaiDetail> getAllByTglGadaiAndStatusNotLike(Connection con, String tglMulai,String tglAkhir,String status)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tm_gadai_detail a,tm_gadai_head b "
                + " where a.no_gadai=b.no_gadai and left(b.tgl_gadai,10) between ? and ? and b.status != ? ");
        ps.setString(1, tglMulai);
        ps.setString(2, tglAkhir);
        ps.setString(3, status);
        ResultSet rs = ps.executeQuery();
        List<GadaiDetail> allDetail = new ArrayList<>();
        while(rs.next()){
            GadaiDetail d = new GadaiDetail();
            d.setNoGadai(rs.getString(1));
            d.setNoUrut(rs.getInt(2));
            d.setKodeKategori(rs.getString(3));
            d.setNamaBarang(rs.getString(4));
            d.setBerat(rs.getDouble(5));
            d.setNilaiJual(rs.getDouble(6));
            d.setNilaiJualSekarang(rs.getDouble(7));
            allDetail.add(d);
        }
        return allDetail;
    }
    public static List<GadaiDetail> getAllByNoGadai(Connection con, String noGadai)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tm_gadai_detail where no_gadai =?");
        ps.setString(1, noGadai);
        ResultSet rs = ps.executeQuery();
        List<GadaiDetail> allDetail = new ArrayList<>();
        while(rs.next()){
            GadaiDetail d = new GadaiDetail();
            d.setNoGadai(rs.getString(1));
            d.setNoUrut(rs.getInt(2));
            d.setKodeKategori(rs.getString(3));
            d.setNamaBarang(rs.getString(4));
            d.setBerat(rs.getDouble(5));
            d.setNilaiJual(rs.getDouble(6));
            d.setNilaiJualSekarang(rs.getDouble(7));
            allDetail.add(d);
        }
        return allDetail;
    }
    public static void insert(Connection con, GadaiDetail detail)throws Exception{
        PreparedStatement ps = con.prepareStatement("insert into tm_gadai_detail values (?,?,?,?,?,?,?)");
        ps.setString(1, detail.getNoGadai());
        ps.setInt(2, detail.getNoUrut());
        ps.setString(3, detail.getKodeKategori());
        ps.setString(4, detail.getNamaBarang());
        ps.setDouble(5, detail.getBerat());
        ps.setDouble(6, detail.getNilaiJual());
        ps.setDouble(7, detail.getNilaiJualSekarang());
        ps.executeUpdate();
    }
    public static void updateHargaSekarangByKategori(Connection con, Kategori k) throws Exception{
        PreparedStatement ps = con.prepareStatement("update tm_gadai_detail set "
                + " nilai_jual_sekarang=ceil(?*berat/500)*500 where kode_kategori=? ");
        ps.setDouble(1, k.getHargaJual());
        ps.setString(2, k.getKodeKategori());
        ps.executeUpdate();
        
    }
}
