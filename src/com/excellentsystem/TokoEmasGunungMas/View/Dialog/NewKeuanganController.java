/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasGunungMas.View.Dialog;

import com.excellentsystem.TokoEmasGunungMas.DAO.KategoriTransaksiDAO;
import com.excellentsystem.TokoEmasGunungMas.DAO.SalesDAO;
import com.excellentsystem.TokoEmasGunungMas.Function;
import com.excellentsystem.TokoEmasGunungMas.Koneksi;
import com.excellentsystem.TokoEmasGunungMas.Main;
import com.excellentsystem.TokoEmasGunungMas.Model.KategoriTransaksi;
import com.excellentsystem.TokoEmasGunungMas.Model.Sales;
import java.sql.Connection;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Xtreme
 */
public class NewKeuanganController {

    public ComboBox<String> kategoriCombo;
    public TextArea keteranganField;
    public TextField jumlahRpField;
    public ComboBox<String> salesCombo;
    public Button saveButton;
    public Button cancelButton;
    private Main mainApp;
    private Stage stage;
    private Stage owner;
    public List<KategoriTransaksi> kategoriTransaksi;

    public void setMainApp(Main mainApp, Stage owner, Stage stage) {
        try (Connection con = Koneksi.getConnection()){
            this.mainApp = mainApp;
            this.stage = stage;
            this.owner = owner;
            ObservableList<String> allKategori = FXCollections.observableArrayList();
            kategoriTransaksi = KategoriTransaksiDAO.getAll(con);
            for (KategoriTransaksi k : kategoriTransaksi) {
                allKategori.add(k.getKodeKategori());
            }
            kategoriCombo.setItems(allKategori);
            ObservableList<String> allSales = FXCollections.observableArrayList();
            List<Sales> sales = SalesDAO.getAll(con);
            for (Sales s : sales) {
                allSales.add(s.getNama());
            }
            salesCombo.setItems(allSales);
            Function.setRpField(jumlahRpField);
            stage.setOnCloseRequest((event) -> {
                mainApp.closeDialog(owner, stage);
            });
        } catch (Exception e) {
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }

    public void closeDialog() {
        mainApp.closeDialog(owner, stage);
    }

}
