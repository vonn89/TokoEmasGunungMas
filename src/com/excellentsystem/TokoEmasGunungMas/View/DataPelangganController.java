/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasGunungMas.View;

import com.excellentsystem.TokoEmasGunungMas.DAO.PelangganDAO;
import com.excellentsystem.TokoEmasGunungMas.Koneksi;
import com.excellentsystem.TokoEmasGunungMas.Main;
import com.excellentsystem.TokoEmasGunungMas.Model.Pelanggan;
import com.excellentsystem.TokoEmasGunungMas.View.Dialog.DetailPelangganController;
import com.excellentsystem.TokoEmasGunungMas.View.Dialog.MessageController;
import java.sql.Connection;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Xtreme
 */
public class DataPelangganController {

    @FXML
    private TableView<Pelanggan> customerTable;
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
    private Main mainApp;
    private final ObservableList<Pelanggan> allPelanggan = FXCollections.observableArrayList();
    private final ObservableList<Pelanggan> filterData = FXCollections.observableArrayList();

    public void initialize() {
        kodePelangganColumn.setCellValueFactory(cellData -> cellData.getValue().kodePelangganProperty());
        namaColumn.setCellValueFactory(cellData -> cellData.getValue().namaProperty());
        alamatColumn.setCellValueFactory(cellData -> cellData.getValue().alamatProperty());
        noTelpColumn.setCellValueFactory(cellData -> cellData.getValue().noTelpProperty());
        noHandphoneColumn.setCellValueFactory(cellData -> cellData.getValue().noHandphoneProperty());
        keteranganColumn.setCellValueFactory(cellData -> cellData.getValue().keteranganProperty());
        identitasColumn.setCellValueFactory(cellData -> cellData.getValue().identitasProperty());
        noIdentitasColumn.setCellValueFactory(cellData -> cellData.getValue().noIdentitasProperty());
        final ContextMenu rowMenu = new ContextMenu();
        MenuItem addNew = new MenuItem("Tambah Pelanggan Baru");
        addNew.setOnAction((ActionEvent e) -> {
            newPelanggan();
        });
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent e) -> {
            getPelanggan();
        });
        rowMenu.getItems().addAll(addNew, refresh);
        customerTable.setContextMenu(rowMenu);
        customerTable.setRowFactory(table -> {
            TableRow<Pelanggan> row = new TableRow<Pelanggan>() {
                @Override
                public void updateItem(Pelanggan item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(rowMenu);
                    } else {
                        final ContextMenu rowMenu = new ContextMenu();
                        MenuItem addNew = new MenuItem("Tambah Pelanggan Baru");
                        addNew.setOnAction((ActionEvent e) -> {
                            newPelanggan();
                        });
                        MenuItem edit = new MenuItem("Ubah Pelanggan");
                        edit.setOnAction((ActionEvent e) -> {
                            updatePelanggan(item);
                        });
                        MenuItem delete = new MenuItem("Hapus Pelanggan");
                        delete.setOnAction((ActionEvent e) -> {
                            deletePelanggan(item);
                        });
                        MenuItem refresh = new MenuItem("Refresh");
                        refresh.setOnAction((ActionEvent e) -> {
                            getPelanggan();
                        });
                        rowMenu.getItems().addAll(addNew, edit, delete, refresh);
                        setContextMenu(rowMenu);
                    }
                }
            };
            return row;
        });
        allPelanggan.addListener((ListChangeListener.Change<? extends Pelanggan> change) -> {
            searchPelanggan();
        });
        searchField.textProperty().addListener(
                (ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
                    searchPelanggan();
                });
        filterData.addAll(allPelanggan);
    }

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
        getPelanggan();
        customerTable.setItems(filterData);
    }

    private void getPelanggan() {
        try (Connection con = Koneksi.getConnection()) {
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
                        || checkColumn(temp.getNoTelp())
                        || checkColumn(temp.getNoHandphone())
                        || checkColumn(temp.getKeterangan())
                        || checkColumn(temp.getIdentitas())
                        || checkColumn(temp.getNoIdentitas())) {
                    filterData.add(temp);
                }
            }
        }
    }

    @FXML
    private void newPelanggan() {
        Pelanggan cust = new Pelanggan();
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/DetailPelanggan.fxml");
        DetailPelangganController x = loader.getController();
        x.setMainApp(mainApp, mainApp.MainStage, stage);
        x.setPelanggan(cust);
        x.saveButton.setOnAction((ActionEvent ev) -> {
            try (Connection con = Koneksi.getConnection()) {
                if (x.namaField.getText().equals("") || x.namaField.getText() == null) {
                    mainApp.showMessage(Modality.NONE, "Warning", "nama masih kosong");
                } else {
                    cust.setKodePelanggan(PelangganDAO.getId(con));
                    cust.setNama(x.namaField.getText());
                    cust.setAlamat(x.alamatField.getText());
                    cust.setNoTelp(x.noTelpField.getText());
                    cust.setNoHandphone(x.noHandphoneField.getText());
                    cust.setKeterangan(x.keteranganField.getText());
                    cust.setIdentitas(x.identitasCombo.getSelectionModel().getSelectedItem());
                    cust.setNoIdentitas(x.noIdentitasField.getText());
                    cust.setStatus("true");
                    PelangganDAO.insert(con, cust);
                    mainApp.closeDialog(mainApp.MainStage, stage);
                    getPelanggan();
                    mainApp.showMessage(Modality.NONE, "Success", "Data pelanggan berhasil disimpan");
                }
            } catch (Exception e) {
                mainApp.showMessage(Modality.NONE, "Error", e.toString());
            }
        });
    }

    private void updatePelanggan(Pelanggan cust) {
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/DetailPelanggan.fxml");
        DetailPelangganController x = loader.getController();
        x.setMainApp(mainApp, mainApp.MainStage, stage);
        x.setPelanggan(cust);
        x.saveButton.setOnAction((ActionEvent ev) -> {
            try (Connection con = Koneksi.getConnection()) {
                if (x.namaField.getText().equals("") || x.namaField.getText() == null) {
                    mainApp.showMessage(Modality.NONE, "Warning", "nama masih kosong");
                } else {
                    cust.setNama(x.namaField.getText());
                    cust.setAlamat(x.alamatField.getText());
                    cust.setNoTelp(x.noTelpField.getText());
                    cust.setNoHandphone(x.noHandphoneField.getText());
                    cust.setKeterangan(x.keteranganField.getText());
                    cust.setIdentitas(x.identitasCombo.getSelectionModel().getSelectedItem());
                    cust.setNoIdentitas(x.noIdentitasField.getText());
                    cust.setStatus("true");
                    PelangganDAO.update(con, cust);
                    mainApp.closeDialog(mainApp.MainStage, stage);
                    getPelanggan();
                    mainApp.showMessage(Modality.NONE, "Success", "Data pelanggan berhasil disimpan");
                }
            } catch (Exception e) {
                mainApp.showMessage(Modality.NONE, "Error", e.toString());
            }
        });
    }

    private void deletePelanggan(Pelanggan cust) {
        MessageController controller = mainApp.showMessage(Modality.WINDOW_MODAL, "Confirmation",
                "Delete pelanggan " + cust.getKodePelanggan() + "-" + cust.getNama() + " ?");
        controller.OK.setOnAction((ActionEvent ev) -> {
            try (Connection con = Koneksi.getConnection()) {
                PelangganDAO.delete(con, cust.getKodePelanggan());
                mainApp.showMessage(Modality.NONE, "Success", "Data pelanggan berhasil dihapus");
                getPelanggan();
            } catch (Exception e) {
                mainApp.showMessage(Modality.NONE, "Error", e.toString());
            }
        });
    }
}
