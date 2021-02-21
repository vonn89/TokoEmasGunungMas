/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasGunungMas.DAO;

import static com.excellentsystem.TokoEmasGunungMas.Main.sistem;
import static com.excellentsystem.TokoEmasGunungMas.Main.tglBarang;
import static com.excellentsystem.TokoEmasGunungMas.Main.tglSystem;
import com.excellentsystem.TokoEmasGunungMas.Model.CetakBarcodeHead;
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
public class CetakBarcodeHeadDAO {
    public static List<CetakBarcodeHead> getAllByTglCetak(Connection con, String tglMulai,String tglAkhir)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tt_cetak_barcode_head "
                + " where left(tgl_cetak,10) between ? and ? ");
        ps.setString(1, tglMulai);
        ps.setString(2, tglAkhir);
        ResultSet rs = ps.executeQuery();
        List<CetakBarcodeHead> allCetakBarcodeHead = new ArrayList<>();
        while(rs.next()){
            CetakBarcodeHead cb = new CetakBarcodeHead();
            cb.setNoCetak(rs.getString(1));
            cb.setTglCetak(rs.getString(2));
            cb.setTotalQty(rs.getInt(3));
            cb.setTotalBerat(rs.getDouble(4));
            cb.setTotalBeratAsli(rs.getDouble(5));
            cb.setKodeUser(rs.getString(6));
            allCetakBarcodeHead.add(cb);
        }
        return allCetakBarcodeHead;
    }
    public static void insert(Connection con, CetakBarcodeHead cb)throws Exception{
        PreparedStatement ps = con.prepareStatement("insert into tt_cetak_barcode_head values (?,?,?,?,?,?)");
        ps.setString(1, cb.getNoCetak());
        ps.setString(2, cb.getTglCetak());
        ps.setInt(3, cb.getTotalQty());
        ps.setDouble(4, cb.getTotalBerat());
        ps.setDouble(5, cb.getTotalBeratAsli());
        ps.setString(6, cb.getKodeUser());
        ps.executeUpdate();
    }
    public static String getId(Connection con)throws Exception{
        PreparedStatement ps =  con.prepareStatement("select max(right(no_cetak,4)) from tt_cetak_barcode_head "
                + " where mid(no_cetak,4,6)=? ");
        String no_cetakBarcode = "CB-"+tglSystem.format(tglBarang.parse(sistem.getTglSystem()))+"-"+new DecimalFormat("0000").format(1);
        ps.setString(1, tglSystem.format(tglBarang.parse(sistem.getTglSystem())));
        ResultSet rs = ps.executeQuery();
        if(rs.next())
            no_cetakBarcode = "CB-"+tglSystem.format(tglBarang.parse(sistem.getTglSystem()))+"-"+new DecimalFormat("0000").format(rs.getInt(1)+1);
        return no_cetakBarcode;
    }
}
