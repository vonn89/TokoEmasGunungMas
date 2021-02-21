/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasGunungMas.View.Dialog;

import com.excellentsystem.TokoEmasGunungMas.DAO.GudangDAO;
import com.excellentsystem.TokoEmasGunungMas.DAO.StokBarangDAO;
import com.excellentsystem.TokoEmasGunungMas.Function;
import static com.excellentsystem.TokoEmasGunungMas.Function.getTableCell;
import com.excellentsystem.TokoEmasGunungMas.Koneksi;
import com.excellentsystem.TokoEmasGunungMas.Main;
import static com.excellentsystem.TokoEmasGunungMas.Main.gr;
import com.excellentsystem.TokoEmasGunungMas.Model.Gudang;
import java.sql.Connection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Yunaz
 */
public class DataGudangController {

    @FXML
    private TableView<Gudang> gudangTable;
    @FXML
    private TableColumn<Gudang, String> kodeGudangColumn;
    @FXML
    private TableColumn<Gudang, Number> beratBakiColumn;

    @FXML
    private Button cancelButton;
    @FXML
    private TextField kodeGudangField;
    @FXML
    private TextField beratBakiField;
    private String status = "";
    private ObservableList<Gudang> allGudang = FXCollections.observableArrayList();
    private Main mainApp;
    private Stage stage;
    private Stage owner;

    public void initialize() {
        kodeGudangColumn.setCellValueFactory(cellData -> cellData.getValue().kodeGudangProperty());
        beratBakiColumn.setCellValueFactory(cellData -> cellData.getValue().beratBakiProperty());
        beratBakiColumn.setCellFactory(col -> getTableCell(gr));
        Function.setBeratField(beratBakiField);
        kodeGudangField.setOnKeyPressed((KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                beratBakiField.selectAll();
                beratBakiField.requestFocus();
            }
        });
        final ContextMenu menu = new ContextMenu();
        MenuItem addNew = new MenuItem("Tambah Gudang Baru");
        addNew.setOnAction((ActionEvent event) -> {
            newGudang();
        });
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent event) -> {
            getGudang();
        });
        menu.getItems().addAll(addNew, refresh);
        gudangTable.setContextMenu(menu);
        gudangTable.setRowFactory(table -> {
            TableRow<Gudang> row = new TableRow<Gudang>() {
                @Override
                public void updateItem(Gudang item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(menu);
                    } else {
                        final ContextMenu rowMenu = new ContextMenu();
                        MenuItem addNew = new MenuItem("Tambah Gudang Baru");
                        addNew.setOnAction((ActionEvent event) -> {
                            newGudang();
                        });
                        MenuItem hapus = new MenuItem("Hapus Gudang");
                        hapus.setOnAction((ActionEvent event) -> {
                            deleteGudang(item);
                        });
                        MenuItem refresh = new MenuItem("Refresh");
                        refresh.setOnAction((ActionEvent event) -> {
                            getGudang();
                        });
                        rowMenu.getItems().addAll(addNew, hapus, refresh);
                        setContextMenu(rowMenu);
                    }
                }
            };
            return row;
        });
        gudangTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> selectGudang(newValue));
        gudangTable.setItems(allGudang);
    }

    public void setMainApp(Main mainApp, Stage owner, Stage stage) {
        this.mainApp = mainApp;
        this.stage = stage;
        this.owner = owner;
        getGudang();
        stage.setOnCloseRequest((event) -> {
            mainApp.closeDialog(owner, stage);
        });
    }

    private void getGudang() {
        try (Connection con = Koneksi.getConnection()) {
            allGudang.clear();
            allGudang.addAll(GudangDAO.getAll(con));
            gudangTable.refresh();
        } catch (Exception e) {
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }

    @FXML
    private void close() {
        mainApp.closeDialog(owner, stage);
    }

    private void newGudang() {
        kodeGudangField.setDisable(false);
        kodeGudangField.setText("");
        beratBakiField.setText("");
        kodeGudangField.requestFocus();
        status = "New";
    }

    @FXML
    private void cancel() {
        kodeGudangField.setDisable(true);
        kodeGudangField.setText("");
        beratBakiField.setText("");
        status = "";
    }

    private void deleteGudang(Gudang temp) {
        try (Connection con = Koneksi.getConnection()) {
            if (StokBarangDAO.checkKodeGudang(con, temp.getKodeGudang())) {
                mainApp.showMessage(Modality.NONE, "Warning", "Gudang tidak dapat dihapus,"
                        + "\nkarena masih ada stok barang di gudang " + temp.getKodeGudang());
            } else {
                GudangDAO.delete(con, temp.getKodeGudang());
                getGudang();
                mainApp.showMessage(Modality.NONE, "Success", "Gudang berhasil dihapus");
                cancelButton.fire();
            }
        } catch (Exception e) {
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }

    private void selectGudang(Gudang temp) {
        if (temp != null) {
            kodeGudangField.setDisable(true);
            kodeGudangField.setText(temp.getKodeGudang());
            beratBakiField.setText(gr.format(temp.getBeratBaki()));
            beratBakiField.requestFocus();
            status = "Update";
        }
    }

    @FXML
    private void saveGudang() {
        try (Connection con = Koneksi.getConnection()) {
            if (kodeGudangField.getText().equals("")) {
                mainApp.showMessage(Modality.NONE, "Warning", "Kode gudang masih kosong");
            } else if (beratBakiField.getText().equals("")) {
                mainApp.showMessage(Modality.NONE, "Warning", "Berat baki masih kosong");
            } else {
                if (status.equals("New")) {
                    Boolean s = true;
                    for (Gudang k : allGudang) {
                        if (k.getKodeGudang().toUpperCase().equals(kodeGudangField.getText().toUpperCase())) {
                            s = false;
                        }
                    }
                    if (s) {
                        Gudang k = new Gudang();
                        k.setKodeGudang(kodeGudangField.getText().toUpperCase());
                        k.setBeratBaki(Double.parseDouble(beratBakiField.getText().replaceAll(",", "")));
                        GudangDAO.insert(con, k);
                        getGudang();
                        mainApp.showMessage(Modality.NONE, "Success", "Gudang Barang berhasil disimpan");
                        cancelButton.fire();
                    } else {
                        mainApp.showMessage(Modality.NONE, "Warning", "Kode Gudang sudah terdaftar");
                    }
                } else if (status.equals("Update")) {
                    Gudang gudang = null;
                    for (Gudang k : allGudang) {
                        if (k.getKodeGudang().toUpperCase().equals(kodeGudangField.getText().toUpperCase())) {
                            gudang = k;
                        }
                    }
                    if (gudang == null) {
                        mainApp.showMessage(Modality.NONE, "Warning", "Gudang barang tidak ditemukan");
                    } else {
                        gudang.setBeratBaki(Double.parseDouble(beratBakiField.getText().replaceAll(",", "")));
                        GudangDAO.update(con, gudang);
                        for (Gudang k : allGudang) {
                            if (k.getKodeGudang().toUpperCase().equals(kodeGudangField.getText().toUpperCase())) {
                                k.setBeratBaki(Double.parseDouble(beratBakiField.getText().replaceAll(",", "")));
                            }
                        }
                        getGudang();
                        mainApp.showMessage(Modality.NONE, "Success", "Gudang Barang berhasil disimpan");
                        cancelButton.fire();
                    }
                }
            }
        } catch (Exception e) {
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }
}
