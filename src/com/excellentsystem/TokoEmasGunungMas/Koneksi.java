package com.excellentsystem.TokoEmasGunungMas;

import static com.excellentsystem.TokoEmasGunungMas.Main.ipServer;
import java.sql.Connection;
import java.sql.DriverManager;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Xtreme
 */
public class Koneksi {

    public static Connection getConnection() throws Exception {
        String DbName = "jdbc:mysql://" + ipServer + ":3306/tokoemas?"
                + "connectTimeout=0&socketTimeout=0&autoReconnect=true";
        Class.forName("com.mysql.jdbc.Driver");
        return DriverManager.getConnection(DbName, "jago_admin", "jagopusat");
    }
}
