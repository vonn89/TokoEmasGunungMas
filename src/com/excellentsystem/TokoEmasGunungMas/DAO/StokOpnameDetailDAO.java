/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasGunungMas.DAO;

import com.excellentsystem.TokoEmasGunungMas.Model.StokOpnameDetail;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Yunaz
 */
public class StokOpnameDetailDAO {
    
    public static List<StokOpnameDetail> getAll(Connection con, String noStokOpname)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tt_stok_opname_detail where no_stok_opname = ?");
        ps.setString(1, noStokOpname);
        ResultSet rs = ps.executeQuery();
        List<StokOpnameDetail> allStokOpnameDetail = new ArrayList<>();
        while(rs.next()){
            StokOpnameDetail s = new StokOpnameDetail();
            s.setNoStokOpname(rs.getString(1));
            s.setKodeBarcode(rs.getString(2));
            s.setStatus(Boolean.parseBoolean(rs.getString(3)));
            s.setKodeSales(rs.getString(4));
            allStokOpnameDetail.add(s);
        }
        return allStokOpnameDetail;
    }
    public static void insert(Connection con, StokOpnameDetail s)throws Exception{
        PreparedStatement ps = con.prepareStatement("insert into tt_stok_opname_detail values (?,?,?,?)");
        ps.setString(1, s.getNoStokOpname());
        ps.setString(2, s.getKodeBarcode());
        ps.setString(3, String.valueOf(s.isStatus()));
        ps.setString(4, s.getKodeSales());
        ps.executeUpdate();
    }
    public static void update(Connection con, StokOpnameDetail s)throws Exception{
        PreparedStatement ps = con.prepareStatement("update tt_stok_opname_detail set status=?, kode_sales=? "
                + " where kode_barcode=? and no_stok_opname=?");
        ps.setString(1, String.valueOf(s.isStatus()));
        ps.setString(2, s.getKodeSales());
        ps.setString(3, s.getKodeBarcode());
        ps.setString(4, s.getNoStokOpname());
        ps.executeUpdate();
    }
}
