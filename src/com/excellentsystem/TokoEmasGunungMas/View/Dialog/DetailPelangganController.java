/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasGunungMas.View.Dialog;

import com.excellentsystem.TokoEmasGunungMas.Main;
import com.excellentsystem.TokoEmasGunungMas.Model.Pelanggan;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Xtreme
 */
public class DetailPelangganController {

    public TextField kodePelangganField;
    public TextField namaField;
    public TextArea alamatField;
    public TextField noTelpField;
    public TextField noHandphoneField;
    public TextField keteranganField;
    public ComboBox<String> identitasCombo;
    public TextField noIdentitasField;
    public Button saveButton;
    public Button cancelButton;
    private Main mainApp;
    private Stage stage;
    private Stage owner;

    public void setMainApp(Main mainApp, Stage owner, Stage stage) {
        this.mainApp = mainApp;
        this.stage = stage;
        this.owner = owner;
        ObservableList<String> allstatus = FXCollections.observableArrayList();
        allstatus.add("KTP");
        allstatus.add("SIM");
        allstatus.add("Lainnya..");
        identitasCombo.setItems(allstatus);
        stage.setOnCloseRequest((event) -> {
            mainApp.closeDialog(owner, stage);
        });
    }

    public void setPelanggan(Pelanggan cust) {
        namaField.setText("");
        alamatField.setText("");
        noTelpField.setText("");
        noHandphoneField.setText("");
        identitasCombo.getSelectionModel().select("");
        keteranganField.setText("");
        noIdentitasField.setText("");
        if (cust.getNama() != null) {
            kodePelangganField.setText(cust.getKodePelanggan());
            namaField.setText(cust.getNama());
            alamatField.setText(cust.getAlamat());
            noTelpField.setText(cust.getNoTelp());
            noHandphoneField.setText(cust.getNoHandphone());
            identitasCombo.getSelectionModel().select(cust.getIdentitas());
            keteranganField.setText(cust.getKeterangan());
            noIdentitasField.setText(cust.getNoIdentitas());
        }
    }

    public void closeDialog() {
        mainApp.closeDialog(owner, stage);
    }

}
