/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasGunungMas.View.Dialog;

import com.excellentsystem.TokoEmasGunungMas.DAO.GudangDAO;
import com.excellentsystem.TokoEmasGunungMas.DAO.JenisDAO;
import com.excellentsystem.TokoEmasGunungMas.DAO.KategoriDAO;
import com.excellentsystem.TokoEmasGunungMas.Koneksi;
import com.excellentsystem.TokoEmasGunungMas.Main;
import com.excellentsystem.TokoEmasGunungMas.Model.Gudang;
import com.excellentsystem.TokoEmasGunungMas.Model.Jenis;
import com.excellentsystem.TokoEmasGunungMas.Model.Kategori;
import java.sql.Connection;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Yunaz
 */
public class NewStokOpnameController {

    public ComboBox<String> kodeGudangCombo;
    public ComboBox<String> kodeKategoriCombo;
    public ComboBox<String> kodeJenisCombo;
    public Button OkButton;
    private Main mainApp;
    private Stage stage;
    private Stage owner;
    private final ObservableList<String> allKategori = FXCollections.observableArrayList();
    private final ObservableList<String> allJenis = FXCollections.observableArrayList();
    private final ObservableList<String> allGudang = FXCollections.observableArrayList();
    public List<Jenis> jenis;

    public void setMainApp(Main mainApp, Stage owner, Stage stage) {
        try (Connection con = Koneksi.getConnection()){
            this.mainApp = mainApp;
            this.stage = stage;
            this.owner = owner;
            allGudang.clear();
            allGudang.add("Semua");
            for (Gudang g : GudangDAO.getAll(con)) {
                allGudang.add(g.getKodeGudang());
            }
            kodeGudangCombo.setItems(allGudang);
            kodeGudangCombo.getSelectionModel().select("Semua");
            allKategori.clear();
            allKategori.add("Semua");
            List<Kategori> kategori = KategoriDAO.getAll(con);
            for (Kategori k : kategori) {
                allKategori.add(k.getKodeKategori());
            }
            kodeKategoriCombo.setItems(allKategori);
            kodeKategoriCombo.getSelectionModel().select("Semua");

            jenis = JenisDAO.getAll(con);
            getJenis();
            stage.setOnCloseRequest((event) -> {
                mainApp.closeDialog(owner, stage);
            });
        } catch (Exception e) {
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }

    @FXML
    private void getJenis() {
        if (jenis != null) {
            allJenis.clear();
            allJenis.add("Semua");
            for (Jenis j : jenis) {
                if (j.getKodeKategori().equals(kodeKategoriCombo.getSelectionModel().getSelectedItem())
                        || kodeKategoriCombo.getSelectionModel().getSelectedItem().equals("Semua")) {
                    allJenis.add(j.getKodeJenis());
                }
            }
            kodeJenisCombo.setItems(allJenis);
            kodeJenisCombo.getSelectionModel().select("Semua");
        }
    }

    @FXML
    private void close() {
        mainApp.closeDialog(owner, stage);
    }
}
