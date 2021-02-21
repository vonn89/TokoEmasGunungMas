/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasGunungMas.DAO;

import static com.excellentsystem.TokoEmasGunungMas.Main.sistem;
import com.excellentsystem.TokoEmasGunungMas.Model.GadaiHead;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Yunaz
 */
public class GadaiHeadDAO {

    public static double getTotalByTglLunasAndStatus(Connection con, String mulai,String akhir,String status)throws Exception{
        PreparedStatement ps = con.prepareStatement("select sum(total_pinjaman) from tm_gadai_head "
                + " where left(tgl_lunas,10) between ? and ? and status = ?");
        ps.setString(1, mulai);
        ps.setString(2, akhir);
        ps.setString(3, status);
        ResultSet rs = ps.executeQuery();
        double total = 0;
        while(rs.next()){
            total = rs.getDouble(1);
        }
        return total;
    }
    public static double getTotalByTglGadaiAndStatus(Connection con, String mulai,String akhir,String status)throws Exception{
        PreparedStatement ps = con.prepareStatement("select sum(total_pinjaman) from tm_gadai_head "
                + " where left(tgl_gadai,10) between ? and ? and status = ?");
        ps.setString(1, mulai);
        ps.setString(2, akhir);
        ps.setString(3, status);
        ResultSet rs = ps.executeQuery();
        double total = 0;
        while(rs.next()){
            total = rs.getDouble(1);
        }
        return total;
    }
    public static List<GadaiHead> getAllByTglLunasAndStatus(Connection con, String mulai,String akhir,String status)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tm_gadai_head "
                + " where left(tgl_lunas,10) between ? and ? and status = ?");
        ps.setString(1, mulai);
        ps.setString(2, akhir);
        ps.setString(3, status);
        ResultSet rs = ps.executeQuery();
        List<GadaiHead> allGadai = new ArrayList<>();
        while(rs.next()){
            GadaiHead h = new GadaiHead();
            h.setNoGadai(rs.getString(1));
            h.setTglGadai(rs.getString(2));
            h.setKodeSales(rs.getString(3));
            h.setKodePelanggan(rs.getString(4));
            h.setNama(rs.getString(5));
            h.setAlamat(rs.getString(6));
            h.setNoTelp(rs.getString(7));
            h.setKeterangan(rs.getString(8));
            h.setTotalBerat(rs.getDouble(9));
            h.setTotalPinjaman(rs.getDouble(10));
            h.setLamaPinjam(rs.getInt(11));
            h.setBungaPersen(rs.getDouble(12));
            h.setBungaKomp(rs.getDouble(13));
            h.setBungaRp(rs.getDouble(14));
            h.setKodeUser(rs.getString(15));
            h.setStatus(rs.getString(16));
            h.setTglLunas(rs.getString(17));
            h.setSalesLunas(rs.getString(18));
            h.setUserLunas(rs.getString(19));
            h.setTglBatal(rs.getString(20));
            h.setSalesBatal(rs.getString(21));
            allGadai.add(h);
        }
        return allGadai;
    }
    public static List<GadaiHead> getAllByTglGadaiAndStatus(Connection con, String mulai,String akhir,String status)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tm_gadai_head "
                + " where left(tgl_gadai,10) between ? and ? and status like ?");
        ps.setString(1, mulai);
        ps.setString(2, akhir);
        ps.setString(3, status);
        ResultSet rs = ps.executeQuery();
        List<GadaiHead> allGadai = new ArrayList<>();
        while(rs.next()){
            GadaiHead h = new GadaiHead();
            h.setNoGadai(rs.getString(1));
            h.setTglGadai(rs.getString(2));
            h.setKodeSales(rs.getString(3));
            h.setKodePelanggan(rs.getString(4));
            h.setNama(rs.getString(5));
            h.setAlamat(rs.getString(6));
            h.setNoTelp(rs.getString(7));
            h.setKeterangan(rs.getString(8));
            h.setTotalBerat(rs.getDouble(9));
            h.setTotalPinjaman(rs.getDouble(10));
            h.setLamaPinjam(rs.getInt(11));
            h.setBungaPersen(rs.getDouble(12));
            h.setBungaKomp(rs.getDouble(13));
            h.setBungaRp(rs.getDouble(14));
            h.setKodeUser(rs.getString(15));
            h.setStatus(rs.getString(16));
            h.setTglLunas(rs.getString(17));
            h.setSalesLunas(rs.getString(18));
            h.setUserLunas(rs.getString(19));
            h.setTglBatal(rs.getString(20));
            h.setSalesBatal(rs.getString(21));
            allGadai.add(h);
        }
        return allGadai;
    }
    public static List<GadaiHead> getAllByTglGadaiAndStatusNotLike(Connection con, String mulai,String akhir,String status)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tm_gadai_head "
                + " where left(tgl_gadai,10) between ? and ? and status != ?");
        ps.setString(1, mulai);
        ps.setString(2, akhir);
        ps.setString(3, status);
        ResultSet rs = ps.executeQuery();
        List<GadaiHead> allGadai = new ArrayList<>();
        while(rs.next()){
            GadaiHead h = new GadaiHead();
            h.setNoGadai(rs.getString(1));
            h.setTglGadai(rs.getString(2));
            h.setKodeSales(rs.getString(3));
            h.setKodePelanggan(rs.getString(4));
            h.setNama(rs.getString(5));
            h.setAlamat(rs.getString(6));
            h.setNoTelp(rs.getString(7));
            h.setKeterangan(rs.getString(8));
            h.setTotalBerat(rs.getDouble(9));
            h.setTotalPinjaman(rs.getDouble(10));
            h.setLamaPinjam(rs.getInt(11));
            h.setBungaPersen(rs.getDouble(12));
            h.setBungaKomp(rs.getDouble(13));
            h.setBungaRp(rs.getDouble(14));
            h.setKodeUser(rs.getString(15));
            h.setStatus(rs.getString(16));
            h.setTglLunas(rs.getString(17));
            h.setSalesLunas(rs.getString(18));
            h.setUserLunas(rs.getString(19));
            h.setTglBatal(rs.getString(20));
            h.setSalesBatal(rs.getString(21));
            allGadai.add(h);
        }
        return allGadai;
    }
    public static GadaiHead get(Connection con, String noGadai)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tm_gadai_head where no_gadai=? ");
        ps.setString(1, noGadai);
        ResultSet rs = ps.executeQuery();
        GadaiHead h = null;
        while(rs.next()){
            h = new GadaiHead();
            h.setNoGadai(rs.getString(1));
            h.setTglGadai(rs.getString(2));
            h.setKodeSales(rs.getString(3));
            h.setKodePelanggan(rs.getString(4));
            h.setNama(rs.getString(5));
            h.setAlamat(rs.getString(6));
            h.setNoTelp(rs.getString(7));
            h.setKeterangan(rs.getString(8));
            h.setTotalBerat(rs.getDouble(9));
            h.setTotalPinjaman(rs.getDouble(10));
            h.setLamaPinjam(rs.getInt(11));
            h.setBungaPersen(rs.getDouble(12));
            h.setBungaKomp(rs.getDouble(13));
            h.setBungaRp(rs.getDouble(14));
            h.setKodeUser(rs.getString(15));
            h.setStatus(rs.getString(16));
            h.setTglLunas(rs.getString(17));
            h.setSalesLunas(rs.getString(18));
            h.setUserLunas(rs.getString(19));
            h.setTglBatal(rs.getString(20));
            h.setSalesBatal(rs.getString(21));
        }
        return h;
    }
    
    public static void insert(Connection con, GadaiHead gadai)throws Exception{
        PreparedStatement ps = con.prepareStatement("insert into tm_gadai_head values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
        ps.setString(1, gadai.getNoGadai());
        ps.setString(2, gadai.getTglGadai());
        ps.setString(3, gadai.getKodeSales());
        ps.setString(4, gadai.getKodePelanggan());
        ps.setString(5, gadai.getNama());
        ps.setString(6, gadai.getAlamat());
        ps.setString(7, gadai.getNoTelp());
        ps.setString(8, gadai.getKeterangan());
        ps.setDouble(9, gadai.getTotalBerat());
        ps.setDouble(10, gadai.getTotalPinjaman());
        ps.setDouble(11, gadai.getLamaPinjam());
        ps.setDouble(12, gadai.getBungaPersen());
        ps.setDouble(13, gadai.getBungaKomp());
        ps.setDouble(14, gadai.getBungaRp());
        ps.setString(15, gadai.getKodeUser());
        ps.setString(16, gadai.getStatus());
        ps.setString(17, gadai.getTglLunas());
        ps.setString(18, gadai.getSalesLunas());
        ps.setString(19, gadai.getUserLunas());
        ps.setString(20, gadai.getTglBatal());
        ps.setString(21, gadai.getSalesBatal());
        ps.executeUpdate();
    }
    public static void update(Connection con, GadaiHead gadai)throws Exception{
        PreparedStatement ps = con.prepareStatement("update tm_gadai_head "
                + " set total_berat=?, lama_pinjam=?, bunga_komp=?, bunga_rp=?, "
                + " status=?, tgl_lunas=?, sales_lunas=?, user_lunas=?,  tgl_batal=?, sales_batal=? "
                + " where no_gadai=?");
        ps.setDouble(1, gadai.getTotalBerat());
        ps.setInt(2, gadai.getLamaPinjam());
        ps.setDouble(3, gadai.getBungaKomp());
        ps.setDouble(4, gadai.getBungaRp());
        ps.setString(5, gadai.getStatus());
        ps.setString(6, gadai.getTglLunas());
        ps.setString(7, gadai.getSalesLunas());
        ps.setString(8, gadai.getUserLunas());
        ps.setString(9, gadai.getTglBatal());
        ps.setString(10, gadai.getSalesBatal());
        ps.setString(11, gadai.getNoGadai());
        ps.executeUpdate();
    }
    public static void updateBungaGadai(Connection con)throws Exception{
        PreparedStatement ps = con.prepareStatement("update tm_gadai_head "
                + " set lama_pinjam=datediff(?,concat('20',SUBSTRING(no_gadai,4,2),'-',SUBSTRING(no_gadai,06,2),'-',SUBSTRING(no_gadai,08,2))),"
                + " bunga_komp=ceil(total_pinjaman*bunga_persen/100/30*lama_pinjam/500)*500 , "
                + " bunga_rp=ceil(total_pinjaman*bunga_persen/100/30*lama_pinjam/500)*500 "
                + " where status='Belum Lunas'");
        ps.setString(1, sistem.getTglSystem());
        ps.executeUpdate();
    }
}
