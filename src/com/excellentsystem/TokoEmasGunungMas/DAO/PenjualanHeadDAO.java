/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasGunungMas.DAO;

import static com.excellentsystem.TokoEmasGunungMas.Main.sistem;
import static com.excellentsystem.TokoEmasGunungMas.Main.tglBarang;
import static com.excellentsystem.TokoEmasGunungMas.Main.tglSystem;
import com.excellentsystem.TokoEmasGunungMas.Model.PenjualanHead;
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
public class PenjualanHeadDAO {
    public static double getTotalByTglPenjualanAndStatus(Connection con, String mulai,String akhir,String status)throws Exception{
        PreparedStatement ps = con.prepareStatement("select sum(grandtotal) from tt_penjualan_head "
                + " where left(tgl_penjualan,10) between ? and ? and status = ?");
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
    public static List<PenjualanHead> getAllByTglPenjualanAndStatus(Connection con, String mulai,String akhir,String status)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tt_penjualan_head "
                + " where left(tgl_penjualan,10) between ? and ? and status = ?");
        ps.setString(1, mulai);
        ps.setString(2, akhir);
        ps.setString(3, status);
        ResultSet rs = ps.executeQuery();
        List<PenjualanHead> allPenjualan = new ArrayList<>();
        while(rs.next()){
            PenjualanHead p = new PenjualanHead();
            p.setNoPenjualan(rs.getString(1));
            p.setTglPenjualan(rs.getString(2));
            p.setKodeSales(rs.getString(3));
            p.setKodePelanggan(rs.getString(4));
            p.setNama(rs.getString(5));
            p.setAlamat(rs.getString(6));
            p.setNoTelp(rs.getString(7));
            p.setTotalBerat(rs.getDouble(8));
            p.setGrandtotal(rs.getDouble(9));
            p.setCatatan(rs.getString(10));
            p.setKodeUser(rs.getString(11));
            p.setStatus(rs.getString(12));
            p.setTglBatal(rs.getString(13));
            p.setUserBatal(rs.getString(14));
            allPenjualan.add(p);
        }
        return allPenjualan;
    }
    public static List<PenjualanHead> getAllByTglBatalAndStatus(Connection con, String mulai,String akhir,String status)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tt_penjualan_head "
                + " where left(tgl_batal,10) between ? and ? and status = ?");
        ps.setString(1, mulai);
        ps.setString(2, akhir);
        ps.setString(3, status);
        ResultSet rs = ps.executeQuery();
        List<PenjualanHead> allPenjualan = new ArrayList<>();
        while(rs.next()){
            PenjualanHead p = new PenjualanHead();
            p.setNoPenjualan(rs.getString(1));
            p.setTglPenjualan(rs.getString(2));
            p.setKodeSales(rs.getString(3));
            p.setKodePelanggan(rs.getString(4));
            p.setNama(rs.getString(5));
            p.setAlamat(rs.getString(6));
            p.setNoTelp(rs.getString(7));
            p.setTotalBerat(rs.getDouble(8));
            p.setGrandtotal(rs.getDouble(9));
            p.setCatatan(rs.getString(10));
            p.setKodeUser(rs.getString(11));
            p.setStatus(rs.getString(12));
            p.setTglBatal(rs.getString(13));
            p.setUserBatal(rs.getString(14));
            allPenjualan.add(p);
        }
        return allPenjualan;
    }
    public static String getId(Connection con)throws Exception{
        PreparedStatement ps =  con.prepareStatement("select max(right(no_penjualan,4)) from tt_penjualan_head "
                + " where mid(no_penjualan,4,6)=? ");
        String noPenjualan = "PJ-"+tglSystem.format(tglBarang.parse(sistem.getTglSystem()))+"-"+new DecimalFormat("0000").format(1);
        ps.setString(1, tglSystem.format(tglBarang.parse(sistem.getTglSystem())));
        ResultSet rs = ps.executeQuery();
        if(rs.next())
            noPenjualan = "PJ-"+tglSystem.format(tglBarang.parse(sistem.getTglSystem()))+"-"+new DecimalFormat("0000").format(rs.getInt(1)+1);
        return noPenjualan;
    }
    public static void insert(Connection con, PenjualanHead p)throws Exception{
        PreparedStatement ps = con.prepareStatement("insert into tt_penjualan_head values (?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
        ps.setString(1, p.getNoPenjualan());
        ps.setString(2, p.getTglPenjualan());
        ps.setString(3, p.getKodeSales());
        ps.setString(4, p.getKodePelanggan());
        ps.setString(5, p.getNama());
        ps.setString(6, p.getAlamat());
        ps.setString(7, p.getNoTelp());
        ps.setDouble(8, p.getTotalBerat());
        ps.setDouble(9, p.getGrandtotal());
        ps.setString(10, p.getCatatan());
        ps.setString(11, p.getKodeUser());
        ps.setString(12, p.getStatus());
        ps.setString(13, p.getTglBatal());
        ps.setString(14, p.getUserBatal());
        ps.executeUpdate();
    }
    public static void update(Connection con, PenjualanHead p)throws Exception{
        PreparedStatement ps = con.prepareStatement("update tt_penjualan_head set status=?,tgl_batal=?,user_batal=? where no_penjualan=?");
        ps.setString(1, p.getStatus());
        ps.setString(2, p.getTglBatal());
        ps.setString(3, p.getUserBatal());
        ps.setString(4, p.getNoPenjualan());
        ps.executeUpdate();
    }
}
