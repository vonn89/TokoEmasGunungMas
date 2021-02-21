/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasGunungMas.DAO;

import com.excellentsystem.TokoEmasGunungMas.Model.PembelianDetail;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Yunaz
 */
public  class PembelianDetailDAO {

    
    public static List<PembelianDetail> getAllByTglBatalAndStatus(Connection con, String tglMulai,String tglAkhir,String status)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tt_pembelian_detail a,tt_pembelian_head b "
                + " where a.no_pembelian = b.no_pembelian and left(b.tgl_batal,10) between ? and ? and b.status = ?");
        ps.setString(1, tglMulai);
        ps.setString(2, tglAkhir);
        ps.setString(3, status);
        ResultSet rs = ps.executeQuery();
        List<PembelianDetail> allDetail = new ArrayList<>();
        while(rs.next()){
            PembelianDetail d = new PembelianDetail();
            d.setNoPembelian(rs.getString(1));
            d.setNoUrut(rs.getInt(2));
            d.setKodeKategori(rs.getString(3));
            d.setKodeJenis(rs.getString(4));
            d.setNamaBarang(rs.getString(5));
            d.setBeratKotor(rs.getDouble(6));
            d.setBeratBersih(rs.getDouble(7));
            d.setHargaKomp(rs.getDouble(8));
            d.setHargaBeli(rs.getDouble(9));
            allDetail.add(d);
        }
        return allDetail;
    }
    public static List<PembelianDetail> getAllByTglBeliAndStatus(Connection con, String tglMulai,String tglAkhir,String status)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tt_pembelian_detail a,tt_pembelian_head b "
                + " where a.no_pembelian = b.no_pembelian and left(b.tgl_pembelian,10) between ? and ? and b.status = ?");
        ps.setString(1, tglMulai);
        ps.setString(2, tglAkhir);
        ps.setString(3, status);
        ResultSet rs = ps.executeQuery();
        List<PembelianDetail> allDetail = new ArrayList<>();
        while(rs.next()){
            PembelianDetail d = new PembelianDetail();
            d.setNoPembelian(rs.getString(1));
            d.setNoUrut(rs.getInt(2));
            d.setKodeKategori(rs.getString(3));
            d.setKodeJenis(rs.getString(4));
            d.setNamaBarang(rs.getString(5));
            d.setBeratKotor(rs.getDouble(6));
            d.setBeratBersih(rs.getDouble(7));
            d.setHargaKomp(rs.getDouble(8));
            d.setHargaBeli(rs.getDouble(9));
            allDetail.add(d);
        }
        return allDetail;
    }
    public static void insert(Connection con, PembelianDetail detail)throws Exception{
        PreparedStatement ps = con.prepareStatement("insert into tt_pembelian_detail values (?,?,?,?,?,?,?,?,?)");
        ps.setString(1, detail.getNoPembelian());
        ps.setInt(2, detail.getNoUrut());
        ps.setString(3, detail.getKodeKategori());
        ps.setString(4, detail.getKodeJenis());
        ps.setString(5, detail.getNamaBarang());
        ps.setDouble(6, detail.getBeratKotor());
        ps.setDouble(7, detail.getBeratBersih());
        ps.setDouble(8, detail.getHargaKomp());
        ps.setDouble(9, detail.getHargaBeli());
        ps.executeUpdate();
    }
}
