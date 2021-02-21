/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasGunungMas.DAO;

import com.excellentsystem.TokoEmasGunungMas.Model.CetakBarcodeDetail;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Yunaz
 */
public class CetakBarcodeDetailDAO {

    public static List<CetakBarcodeDetail> getAllByTglCetak(Connection con, String tglMulai, String tglAkhir) throws Exception {
        PreparedStatement ps = con.prepareStatement("select * from tt_cetak_barcode_detail "
                + "where no_cetak in (select no_cetak from tt_cetak_barcode_head where left(tgl_cetak,10) between ? and ?)");
        ps.setString(1, tglMulai);
        ps.setString(2, tglAkhir);
        ResultSet rs = ps.executeQuery();
        List<CetakBarcodeDetail> allCetakDetail = new ArrayList<>();
        while (rs.next()) {
            CetakBarcodeDetail cetak = new CetakBarcodeDetail();
            cetak.setNoCetak(rs.getString(1));
            cetak.setKodeBarcode(rs.getString(2));
            allCetakDetail.add(cetak);
        }
        return allCetakDetail;
    }

    public static void insert(Connection con, CetakBarcodeDetail cetak) throws Exception {
        PreparedStatement ps = con.prepareStatement("insert into tt_cetak_barcode_detail values (?,?)");
        ps.setString(1, cetak.getNoCetak());
        ps.setString(2, cetak.getKodeBarcode());
        ps.executeUpdate();
    }
}
