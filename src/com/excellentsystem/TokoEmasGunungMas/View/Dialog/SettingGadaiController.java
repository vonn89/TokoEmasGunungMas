/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasGunungMas.View.Dialog;

import com.excellentsystem.TokoEmasGunungMas.DAO.BungaGadaiDAO;
import com.excellentsystem.TokoEmasGunungMas.Koneksi;
import com.excellentsystem.TokoEmasGunungMas.Main;
import static com.excellentsystem.TokoEmasGunungMas.Main.gr;
import static com.excellentsystem.TokoEmasGunungMas.Main.rp;
import com.excellentsystem.TokoEmasGunungMas.Model.BungaGadai;
import java.sql.Connection;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableCell;
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
public class SettingGadaiController {

    @FXML
    private TableView<BungaGadai> bungaGadaiTable;
    @FXML
    private TableColumn<BungaGadai, Number> jumlahPinjamanMinColumn;
    @FXML
    private TableColumn<BungaGadai, Number> jumlahPinjamanMaxColumn;
    @FXML
    private TableColumn<BungaGadai, Number> bungaGadaiColumn;

    @FXML
    private TextField jumlahPinjamanMinField;
    @FXML
    private TextField jumlahPinjamanMaxField;
    @FXML
    private TextField bungaGadaiField;
    private ObservableList<BungaGadai> allBungaGadai = FXCollections.observableArrayList();
    private Main mainApp;
    private Stage stage;
    private Stage owner;

    public void initialize() {
        jumlahPinjamanMinColumn.setCellValueFactory(cellData -> cellData.getValue().minProperty());
        jumlahPinjamanMinColumn.setCellFactory(col -> new TableCell<BungaGadai, Number>() {
            @Override
            public void updateItem(Number value, boolean empty) {
                super.updateItem(value, empty);
                if (empty) {
                    setText(null);
                } else {
                    setText(rp.format(value.doubleValue()));
                }
            }
        });
        jumlahPinjamanMaxColumn.setCellValueFactory(cellData -> cellData.getValue().maxProperty());
        jumlahPinjamanMaxColumn.setCellFactory(col -> new TableCell<BungaGadai, Number>() {
            @Override
            public void updateItem(Number value, boolean empty) {
                super.updateItem(value, empty);
                if (empty) {
                    setText(null);
                } else {
                    setText(rp.format(value.doubleValue()));
                }
            }
        });
        bungaGadaiColumn.setCellValueFactory(cellData -> cellData.getValue().bungaProperty());
        bungaGadaiColumn.setCellFactory(col -> new TableCell<BungaGadai, Number>() {
            @Override
            public void updateItem(Number value, boolean empty) {
                super.updateItem(value, empty);
                if (empty) {
                    setText(null);
                } else {
                    setText(gr.format(value.doubleValue()));
                }
            }
        });

        jumlahPinjamanMinField.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            try {
                double value = Double.parseDouble(jumlahPinjamanMinField.getText().replaceAll(",", ""));
                jumlahPinjamanMinField.setText(rp.format(value));
            } catch (NumberFormatException e) {
                jumlahPinjamanMinField.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
        jumlahPinjamanMaxField.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            try {
                double value = Double.parseDouble(jumlahPinjamanMaxField.getText().replaceAll(",", ""));
                jumlahPinjamanMaxField.setText(rp.format(value));
            } catch (NumberFormatException e) {
                jumlahPinjamanMaxField.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
        bungaGadaiField.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            try {
                if (!bungaGadaiField.getText().endsWith(".")
                        && !bungaGadaiField.getText().endsWith(".0")
                        && !bungaGadaiField.getText().endsWith(".00")) {
                    bungaGadaiField.setText(gr.format(Double.parseDouble(bungaGadaiField.getText().replaceAll(",", ""))));
                }
            } catch (Exception e) {
                bungaGadaiField.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });

        jumlahPinjamanMinField.setOnKeyPressed((KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                jumlahPinjamanMaxField.selectAll();
                jumlahPinjamanMaxField.requestFocus();
            }
        });
        jumlahPinjamanMaxField.setOnKeyPressed((KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                bungaGadaiField.selectAll();
                bungaGadaiField.requestFocus();
            }
        });
        final ContextMenu menu = new ContextMenu();
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent event) -> {
            bungaGadaiTable.refresh();
        });

        menu.getItems().addAll(refresh);
        bungaGadaiTable.setContextMenu(menu);
        bungaGadaiTable.setRowFactory(table -> {
            TableRow<BungaGadai> row = new TableRow<BungaGadai>() {
                @Override
                public void updateItem(BungaGadai item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        final ContextMenu rowMenu = new ContextMenu();
                        MenuItem refresh = new MenuItem("Refresh");
                        refresh.setOnAction((ActionEvent event) -> {
                            bungaGadaiTable.refresh();
                        });

                        rowMenu.getItems().addAll(refresh);
                        setContextMenu(rowMenu);
                    } else {
                        final ContextMenu rowMenu = new ContextMenu();
                        MenuItem hapus = new MenuItem("Hapus");
                        hapus.setOnAction((ActionEvent event) -> {
                            deleteBungaGadai(item);
                        });

                        MenuItem refresh = new MenuItem("Refresh");
                        refresh.setOnAction((ActionEvent event) -> {
                            bungaGadaiTable.refresh();
                        });
                        rowMenu.getItems().addAll(hapus, refresh);
                        setContextMenu(rowMenu);
                    }
                }
            };
            return row;
        });
    }

    public void setMainApp(Main mainApp, Stage owner, Stage stage) {
        try (Connection con = Koneksi.getConnection()) {
            this.mainApp = mainApp;
            this.stage = stage;
            this.owner = owner;
            allBungaGadai.addAll(BungaGadaiDAO.getAll(con));
            bungaGadaiTable.setItems(allBungaGadai);
            stage.setOnCloseRequest((event) -> {
                mainApp.closeDialog(owner, stage);
            });
        } catch (Exception e) {
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }

    @FXML
    private void close() {
        mainApp.closeDialog(owner, stage);
    }

    private void deleteBungaGadai(BungaGadai temp) {
        try (Connection con = Koneksi.getConnection()) {
            allBungaGadai.remove(temp);
            BungaGadaiDAO.delete(con, temp);
            bungaGadaiTable.refresh();
            jumlahPinjamanMinField.setText("0");
            jumlahPinjamanMaxField.setText("0");
            bungaGadaiField.setText("0");
        } catch (Exception e) {
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }

    @FXML
    private void saveBungaGadai() {
        if (jumlahPinjamanMinField.getText().equals("")) {
            mainApp.showMessage(Modality.NONE, "Warning", "Jumlah pinjaman minimal masih kosong");
        } else if (jumlahPinjamanMaxField.getText().equals("")) {
            mainApp.showMessage(Modality.NONE, "Warning", "Jumlah pinjaman maksimal masih kosong");
        } else if (bungaGadaiField.getText().equals("")) {
            mainApp.showMessage(Modality.NONE, "Warning", "Bunga gadai masih kosong");
        } else {
            try (Connection con = Koneksi.getConnection()) {
                BungaGadai bunga = new BungaGadai();
                bunga.setMin(Double.parseDouble(jumlahPinjamanMinField.getText().replaceAll(",", "")));
                bunga.setMax(Double.parseDouble(jumlahPinjamanMaxField.getText().replaceAll(",", "")));
                bunga.setBunga(Double.parseDouble(bungaGadaiField.getText().replaceAll(",", "")));
                BungaGadaiDAO.insert(con, bunga);
                allBungaGadai.add(bunga);
                bungaGadaiTable.refresh();
                jumlahPinjamanMinField.setText("0");
                jumlahPinjamanMaxField.setText("0");
                bungaGadaiField.setText("0");
            } catch (Exception e) {
                mainApp.showMessage(Modality.NONE, "Error", e.toString());
            }
        }
    }
}
