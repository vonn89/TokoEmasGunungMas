/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasGunungMas.View.Dialog;

import com.excellentsystem.TokoEmasGunungMas.DAO.PelangganDAO;
import com.excellentsystem.TokoEmasGunungMas.Koneksi;
import com.excellentsystem.TokoEmasGunungMas.Main;
import com.excellentsystem.TokoEmasGunungMas.Model.Pelanggan;
import java.sql.Connection;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Yunaz
 */
public class AddPelangganController {

    public TableView<Pelanggan> pelangganTable;
    @FXML
    private TableColumn<Pelanggan, String> kodePelangganColumn;
    @FXML
    private TableColumn<Pelanggan, String> namaColumn;
    @FXML
    private TableColumn<Pelanggan, String> alamatColumn;
    @FXML
    private TableColumn<Pelanggan, String> noTelpColumn;
    @FXML
    private TableColumn<Pelanggan, String> noHandphoneColumn;
    @FXML
    private TableColumn<Pelanggan, String> keteranganColumn;
    @FXML
    private TableColumn<Pelanggan, String> identitasColumn;
    @FXML
    private TableColumn<Pelanggan, String> noIdentitasColumn;

    @FXML
    private TextField searchField;
    private ObservableList<Pelanggan> allPelanggan = FXCollections.observableArrayList();
    private ObservableList<Pelanggan> filterData = FXCollections.observableArrayList();
    private Main mainApp;
    private Stage stage;
    private Stage owner;

    public void initialize() {
        kodePelangganColumn.setCellValueFactory(cellData -> cellData.getValue().kodePelangganProperty());
        namaColumn.setCellValueFactory(cellData -> cellData.getValue().namaProperty());
        alamatColumn.setCellValueFactory(cellData -> cellData.getValue().alamatProperty());
        noHandphoneColumn.setCellValueFactory(cellData -> cellData.getValue().noHandphoneProperty());
        keteranganColumn.setCellValueFactory(cellData -> cellData.getValue().keteranganProperty());
        noTelpColumn.setCellValueFactory(cellData -> cellData.getValue().noTelpProperty());
        identitasColumn.setCellValueFactory(cellData -> cellData.getValue().identitasProperty());
        noIdentitasColumn.setCellValueFactory(cellData -> cellData.getValue().noIdentitasProperty());
        allPelanggan.addListener((ListChangeListener.Change<? extends Pelanggan> change) -> {
            searchPelanggan();
        });
        searchField.textProperty().addListener(
                (ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
                    searchPelanggan();
                });
        filterData.addAll(allPelanggan);
        pelangganTable.setItems(filterData);
    }

    public void setMainApp(Main mainApp, Stage owner, Stage stage) {
        this.mainApp = mainApp;
        this.stage = stage;
        this.owner = owner;
        getPelanggan();
        stage.setOnCloseRequest((event) -> {
            mainApp.closeDialog(owner, stage);
        });
        stage.setHeight(mainApp.screenSize.getHeight() - 80);
        stage.setWidth(mainApp.screenSize.getWidth() - 80);
        stage.setX((mainApp.screenSize.getWidth() - stage.getWidth()) / 2);
        stage.setY((mainApp.screenSize.getHeight() - stage.getHeight()) / 2);
    }

    public void getPelanggan() {
        try(Connection con = Koneksi.getConnection()){
            allPelanggan.clear();
            allPelanggan.addAll(PelangganDAO.getAll(con));
        } catch (Exception e) {
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }

    private Boolean checkColumn(String column) {
        if (column != null) {
            if (column.toLowerCase().contains(searchField.getText().toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    private void searchPelanggan() {
        filterData.clear();
        for (Pelanggan temp : allPelanggan) {
            if (searchField.getText() == null || searchField.getText().equals("")) {
                filterData.add(temp);
            } else {
                if (checkColumn(temp.getKodePelanggan())
                        || checkColumn(temp.getNama())
                        || checkColumn(temp.getAlamat())
                        || checkColumn(temp.getNoHandphone())
                        || checkColumn(temp.getKeterangan())
                        || checkColumn(temp.getNoTelp())
                        || checkColumn(temp.getNoIdentitas())
                        || checkColumn(temp.getIdentitas())) {
                    filterData.add(temp);
                }
            }
        }
    }

    @FXML
    private void close() {
        mainApp.closeDialog(owner, stage);
    }

}
