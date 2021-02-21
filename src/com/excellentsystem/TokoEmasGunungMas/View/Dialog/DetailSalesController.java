/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasGunungMas.View.Dialog;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import com.excellentsystem.TokoEmasGunungMas.Main;
import com.excellentsystem.TokoEmasGunungMas.Model.Sales;

/**
 * FXML Controller class
 *
 * @author Xtreme
 */
public class DetailSalesController {

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
        ObservableList<String> allIdentitas = FXCollections.observableArrayList();
        allIdentitas.add("KTP");
        allIdentitas.add("SIM");
        allIdentitas.add("Lainnya..");
        identitasCombo.setItems(allIdentitas);
        stage.setOnCloseRequest((event) -> {
            mainApp.closeDialog(owner, stage);
        });
    }
    public void setSales(Sales value){
        namaField.setText("");
        alamatField.setText("");
        noTelpField.setText("");
        noHandphoneField.setText("");
        identitasCombo.getSelectionModel().select("");
        keteranganField.setText("");
        noIdentitasField.setText("");
        if(value.getNama()!=null){
            namaField.setText(value.getNama());
            alamatField.setText(value.getAlamat());
            noTelpField.setText(value.getNoTelp());
            noHandphoneField.setText(value.getNoHandphone());
            identitasCombo.getSelectionModel().select(value.getIdentitas());
            keteranganField.setText(value.getKeterangan());
            noIdentitasField.setText(value.getNoIdentitas());
        }
    }
    @FXML
    private void closeDialog(){
        mainApp.closeDialog(owner, stage);
    }
}
