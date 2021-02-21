/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasGunungMas.DAO;

import static com.excellentsystem.TokoEmasGunungMas.Main.sistem;
import static com.excellentsystem.TokoEmasGunungMas.Main.tglBarang;
import static com.excellentsystem.TokoEmasGunungMas.Main.tglSystem;
import com.excellentsystem.TokoEmasGunungMas.Model.PembelianHead;
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
public class PembelianHeadDAO {

    public static double getTotalByTglBeliAndStatus(Connection con, String mulai,String akhir,String status)throws Exception{
        PreparedStatement ps = con.prepareStatement("select sum(total_pembelian) from tt_pembelian_head "
                + " where left(tgl_pembelian,10) between ? and ? and status = ?");
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
    public static List<PembelianHead> getAllByTglBatalAndStatus(Connection con, String mulai,String akhir,String status)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tt_pembelian_head "
                + " where left(tgl_batal,10) between ? and ? and status = ?");
        ps.setString(1, mulai);
        ps.setString(2, akhir);
        ps.setString(3, status);
        ResultSet rs = ps.executeQuery();
        List<PembelianHead> allPembelian = new ArrayList<>();
        while(rs.next()){
            PembelianHead p = new PembelianHead();
            p.setNoPembelian(rs.getString(1));
            p.setTglPembelian(rs.getString(2));
            p.setKodeSales(rs.getString(3));
            p.setKodePelanggan(rs.getString(4));
            p.setNama(rs.getString(5));
            p.setAlamat(rs.getString(6));
            p.setNoTelp(rs.getString(7));
            p.setTotalberatKotor(rs.getDouble(8));
            p.setTotalBeratBersih(rs.getDouble(9));
            p.setTotalPembelian(rs.getDouble(10));
            p.setCatatan(rs.getString(11));
            p.setKodeUser(rs.getString(12));
            p.setStatus(rs.getString(13));
            p.setTglBatal(rs.getString(14));
            p.setUserBatal(rs.getString(15));
            allPembelian.add(p);
        }
        return allPembelian;
    }
    public static List<PembelianHead> getAllByTglBeliAndStatus(Connection con, String mulai,String akhir,String status)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tt_pembelian_head "
                + " where left(tgl_pembelian,10) between ? and ? and status = ?");
        ps.setString(1, mulai);
        ps.setString(2, akhir);
        ps.setString(3, status);
        ResultSet rs = ps.executeQuery();
        List<PembelianHead> allPembelian = new ArrayList<>();
        while(rs.next()){
            PembelianHead p = new PembelianHead();
            p.setNoPembelian(rs.getString(1));
            p.setTglPembelian(rs.getString(2));
            p.setKodeSales(rs.getString(3));
            p.setKodePelanggan(rs.getString(4));
            p.setNama(rs.getString(5));
            p.setAlamat(rs.getString(6));
            p.setNoTelp(rs.getString(7));
            p.setTotalberatKotor(rs.getDouble(8));
            p.setTotalBeratBersih(rs.getDouble(9));
            p.setTotalPembelian(rs.getDouble(10));
            p.setCatatan(rs.getString(11));
            p.setKodeUser(rs.getString(12));
            p.setStatus(rs.getString(13));
            p.setTglBatal(rs.getString(14));
            p.setUserBatal(rs.getString(15));
            allPembelian.add(p);
        }
        return allPembelian;
    }
    public static String getId(Connection con)throws Exception{
        PreparedStatement ps =  con.prepareStatement("select max(right(no_pembelian,4)) from tt_pembelian_head "
                + " where mid(no_pembelian,4,6)=? ");
        String noPembelian = "PB-"+tglSystem.format(tglBarang.parse(sistem.getTglSystem()))+"-"+new DecimalFormat("0000").format(1);
        ps.setString(1, tglSystem.format(tglBarang.parse(sistem.getTglSystem())));
        ResultSet rs = ps.executeQuery();
        if(rs.next())
            noPembelian = "PB-"+tglSystem.format(tglBarang.parse(sistem.getTglSystem()))+"-"+new DecimalFormat("0000").format(rs.getInt(1)+1);
        return noPembelian;
    }
    public static void insert(Connection con, PembelianHead p)throws Exception{
        PreparedStatement ps = con.prepareStatement("insert into tt_pembelian_head values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
        ps.setString(1, p.getNoPembelian());
        ps.setString(2, p.getTglPembelian());
        ps.setString(3, p.getKodeSales());
        ps.setString(4, p.getKodePelanggan());
        ps.setString(5, p.getNama());
        ps.setString(6, p.getAlamat());
        ps.setString(7, p.getNoTelp());
        ps.setDouble(8, p.getTotalberatKotor());
        ps.setDouble(9, p.getTotalBeratBersih());
        ps.setDouble(10, p.getTotalPembelian());
        ps.setString(11, p.getCatatan());
        ps.setString(12, p.getKodeUser());
        ps.setString(13, p.getStatus());
        ps.setString(14, p.getTglBatal());
        ps.setString(15, p.getUserBatal());
        ps.executeUpdate();
    }
    public static void update(Connection con, PembelianHead p)throws Exception{
        PreparedStatement ps = con.prepareStatement("update tt_pembelian_head set status=?,tgl_batal=?,user_batal=? where no_pembelian=?");
        ps.setString(1, p.getStatus());
        ps.setString(2, p.getTglBatal());
        ps.setString(3, p.getUserBatal());
        ps.setString(4, p.getNoPembelian());
        ps.executeUpdate();
    }
}
