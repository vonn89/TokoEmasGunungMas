/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.excellentsystem.TokoEmasGunungMas.DAO;

import com.excellentsystem.TokoEmasGunungMas.Model.Sistem;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author Xtreme
 */
public class SistemDAO {

    public static Sistem get(Connection con)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tm_system");
        ResultSet rs = ps.executeQuery();
        Sistem sistem = null ;
        while(rs.next()){
            sistem = new Sistem();
            sistem.setNamaToko(rs.getString(1));
            sistem.setAlamatToko(rs.getString(2));
            sistem.setNoTelpToko(rs.getString(3));
            sistem.setPrefixBarcode(rs.getString(4));
            sistem.setBeratLabel(rs.getDouble(5));
            sistem.setTglSystem(rs.getString(6));
        }
        return sistem;
    }
    public static void update(Connection con, Sistem sistem)throws Exception{
        PreparedStatement ps = con.prepareStatement("update tm_system set nama_toko = ?, alamat_toko=?, no_telp_toko=?, prefix_barcode=?, berat_label = ?, tgl_system = ?");
        ps.setString(1, sistem.getNamaToko());
        ps.setString(2, sistem.getAlamatToko());
        ps.setString(3, sistem.getNoTelpToko());
        ps.setString(4, sistem.getPrefixBarcode());
        ps.setDouble(5, sistem.getBeratLabel());
        ps.setString(6, sistem.getTglSystem());
        ps.executeUpdate();
    }
}
