/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasGunungMas.View;

import com.excellentsystem.TokoEmasGunungMas.DAO.SalesDAO;
import com.excellentsystem.TokoEmasGunungMas.Koneksi;
import com.excellentsystem.TokoEmasGunungMas.Main;
import com.excellentsystem.TokoEmasGunungMas.Model.Sales;
import com.excellentsystem.TokoEmasGunungMas.View.Dialog.DetailSalesController;
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
public class DataSalesController {

    @FXML
    private TableView<Sales> salesTable;
    @FXML
    private TableColumn<Sales, String> namaColumn;
    @FXML
    private TableColumn<Sales, String> alamatColumn;
    @FXML
    private TableColumn<Sales, String> noTelpColumn;
    @FXML
    private TableColumn<Sales, String> noHandphoneColumn;
    @FXML
    private TableColumn<Sales, String> keteranganColumn;
    @FXML
    private TableColumn<Sales, String> identitasColumn;
    @FXML
    private TableColumn<Sales, String> noIdentitasColumn;
    @FXML
    private TextField searchField;
    private Main mainApp;
    private ObservableList<Sales> allSales = FXCollections.observableArrayList();
    private ObservableList<Sales> filterData = FXCollections.observableArrayList();

    public void initialize() {
        namaColumn.setCellValueFactory(cellData -> cellData.getValue().namaProperty());
        alamatColumn.setCellValueFactory(cellData -> cellData.getValue().alamatProperty());
        noTelpColumn.setCellValueFactory(cellData -> cellData.getValue().noTelpProperty());
        noHandphoneColumn.setCellValueFactory(cellData -> cellData.getValue().noHandphoneProperty());
        keteranganColumn.setCellValueFactory(cellData -> cellData.getValue().keteranganProperty());
        identitasColumn.setCellValueFactory(cellData -> cellData.getValue().identitasProperty());
        noIdentitasColumn.setCellValueFactory(cellData -> cellData.getValue().noIdentitasProperty());
        allSales.addListener((ListChangeListener.Change<? extends Sales> change) -> {
            searchSales();
        });
        final ContextMenu rowMenu = new ContextMenu();
        MenuItem addNew = new MenuItem("Tambah Sales Baru");
        addNew.setOnAction((ActionEvent e) -> {
            newSales();
        });
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent e) -> {
            getSales();
        });
        rowMenu.getItems().addAll(addNew, refresh);
        salesTable.setContextMenu(rowMenu);
        salesTable.setRowFactory(table -> {
            TableRow<Sales> row = new TableRow<Sales>() {
                @Override
                public void updateItem(Sales item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(rowMenu);
                    } else {
                        final ContextMenu rowMenu = new ContextMenu();
                        MenuItem addNew = new MenuItem("Tambah Sales Baru");
                        addNew.setOnAction((ActionEvent e) -> {
                            newSales();
                        });
                        MenuItem edit = new MenuItem("Ubah Sales");
                        edit.setOnAction((ActionEvent e) -> {
                            updateSales(item);
                        });
                        MenuItem delete = new MenuItem("Hapus Sales");
                        delete.setOnAction((ActionEvent e) -> {
                            deleteSales(item);
                        });
                        MenuItem refresh = new MenuItem("Refresh");
                        refresh.setOnAction((ActionEvent e) -> {
                            getSales();
                        });
                        rowMenu.getItems().addAll(addNew, edit, delete, refresh);
                        setContextMenu(rowMenu);
                    }
                }
            };
            return row;
        });
        searchField.textProperty().addListener(
                (ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
                    searchSales();
                });
        filterData.addAll(allSales);
    }

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
        getSales();
        salesTable.setItems(filterData);
    }

    private void getSales() {
        try (Connection con = Koneksi.getConnection()) {
            allSales.clear();
            allSales.addAll(SalesDAO.getAll(con));
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

    private void searchSales() {
        filterData.clear();
        for (Sales temp : allSales) {
            if (searchField.getText() == null || searchField.getText().equals("")) {
                filterData.add(temp);
            } else {
                if (checkColumn(temp.getNama())
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
    private void newSales() {
        Sales s = new Sales();
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/DetailSales.fxml");
        DetailSalesController x = loader.getController();
        x.setMainApp(mainApp, mainApp.MainStage, stage);
        x.setSales(s);
        x.saveButton.setOnAction((ActionEvent ev) -> {
            try (Connection con = Koneksi.getConnection()) {
                if (x.namaField.getText().equals("") || x.namaField.getText() == null) {
                    mainApp.showMessage(Modality.NONE, "Warning", "nama masih kosong");
                } else {
                    s.setNama(x.namaField.getText());
                    s.setAlamat(x.alamatField.getText());
                    s.setNoTelp(x.noTelpField.getText());
                    s.setNoHandphone(x.noHandphoneField.getText());
                    s.setIdentitas(x.identitasCombo.getSelectionModel().getSelectedItem());
                    s.setKeterangan(x.keteranganField.getText());
                    s.setNoIdentitas(x.noIdentitasField.getText());
                    s.setStatus("true");
                    SalesDAO.insert(con, s);
                    mainApp.closeDialog(mainApp.MainStage, stage);
                    getSales();
                    mainApp.showMessage(Modality.NONE, "Success", "Data sales berhasil disimpan");
                }
            } catch (Exception e) {
                mainApp.showMessage(Modality.NONE, "Error", e.toString());
            }
        });
    }

    private void updateSales(Sales s) {
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/DetailSales.fxml");
        DetailSalesController x = loader.getController();
        x.setMainApp(mainApp, mainApp.MainStage, stage);
        x.setSales(s);
        x.namaField.setDisable(true);
        x.saveButton.setOnAction((ActionEvent ev) -> {
            try (Connection con = Koneksi.getConnection()) {
                s.setNama(x.namaField.getText());
                s.setAlamat(x.alamatField.getText());
                s.setNoTelp(x.noTelpField.getText());
                s.setNoHandphone(x.noHandphoneField.getText());
                s.setIdentitas(x.identitasCombo.getSelectionModel().getSelectedItem());
                s.setKeterangan(x.keteranganField.getText());
                s.setNoIdentitas(x.noIdentitasField.getText());
                s.setStatus("true");
                SalesDAO.update(con, s);
                mainApp.closeDialog(mainApp.MainStage, stage);
                getSales();
                mainApp.showMessage(Modality.NONE, "Success", "Data sales berhasil disimpan");
            } catch (Exception e) {
                mainApp.showMessage(Modality.NONE, "Error", e.toString());
            }
        });
    }

    private void deleteSales(Sales s) {
        MessageController controller = mainApp.showMessage(Modality.WINDOW_MODAL, "Confirmation",
                "Delete sales " + s.getNama() + " ?");
        controller.OK.setOnAction((ActionEvent ev) -> {
            try (Connection con = Koneksi.getConnection()) {
                SalesDAO.delete(con, s.getNama());
                mainApp.showMessage(Modality.NONE, "Success", "Data sales berhasil dihapus");
                getSales();
            } catch (Exception e) {
                mainApp.showMessage(Modality.NONE, "Error", e.toString());
            }
        });
    }
}
