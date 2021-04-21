/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasGunungMas.View.Report;

import com.excellentsystem.TokoEmasGunungMas.DAO.KeuanganDAO;
import com.excellentsystem.TokoEmasGunungMas.Function;
import static com.excellentsystem.TokoEmasGunungMas.Function.getTreeTableCell;
import com.excellentsystem.TokoEmasGunungMas.Koneksi;
import com.excellentsystem.TokoEmasGunungMas.Main;
import static com.excellentsystem.TokoEmasGunungMas.Main.gr;
import static com.excellentsystem.TokoEmasGunungMas.Main.rp;
import static com.excellentsystem.TokoEmasGunungMas.Main.tglLengkap;
import static com.excellentsystem.TokoEmasGunungMas.Main.tglSql;
import com.excellentsystem.TokoEmasGunungMas.Model.Keuangan;
import com.excellentsystem.TokoEmasGunungMas.PrintOut.PrintOut;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableRow;
import javafx.scene.control.TreeTableView;
import javafx.stage.Modality;

/**
 * FXML Controller class
 *
 * @author Yunaz
 */
public class LaporanKeuanganController {

    @FXML
    private TreeTableView<Keuangan> keuanganTable;
    @FXML
    private TreeTableColumn<Keuangan, String> noKeuanganColumn;
    @FXML
    private TreeTableColumn<Keuangan, String> tglKeuanganColumn;
    @FXML
    private TreeTableColumn<Keuangan, String> kategoriColumn;
    @FXML
    private TreeTableColumn<Keuangan, String> deskripsiColumn;
    @FXML
    private TreeTableColumn<Keuangan, Number> jumlahRpColumn;
    @FXML
    private TreeTableColumn<Keuangan, String> kodeUserColumn;
    @FXML
    private ComboBox<String> groupByCombo;
    @FXML
    private TextField searchField;
    @FXML
    private Label saldoAwalField;
    @FXML
    private Label saldoAkhirField;
    @FXML
    private DatePicker tglMulaiPicker;
    @FXML
    private DatePicker tglAkhirPicker;
    final TreeItem<Keuangan> root = new TreeItem<>();
    private ObservableList<Keuangan> allKeuangan = FXCollections.observableArrayList();
    private ObservableList<Keuangan> filterData = FXCollections.observableArrayList();
    private Main mainApp;

    public void initialize() {
        noKeuanganColumn.setCellValueFactory(param -> param.getValue().getValue().noKeuanganProperty());

        kategoriColumn.setCellValueFactory(param -> param.getValue().getValue().kategoriProperty());

        deskripsiColumn.setCellValueFactory(param -> param.getValue().getValue().deskripsiProperty());

        kodeUserColumn.setCellValueFactory(param -> param.getValue().getValue().kodeUserProperty());

        tglKeuanganColumn.setCellValueFactory(cellData -> {
            try {
                return new SimpleStringProperty(tglLengkap.format(tglSql.parse(cellData.getValue().getValue().getTglKeuangan())));
            } catch (Exception ex) {
                return null;
            }
        });
        tglKeuanganColumn.setComparator(Function.sortDate(tglLengkap));

        jumlahRpColumn.setCellValueFactory(param -> param.getValue().getValue().jumlahRpProperty());
        jumlahRpColumn.setCellFactory(col -> getTreeTableCell(rp));

        tglMulaiPicker.setConverter(Function.getTglConverter());
        tglMulaiPicker.setValue(LocalDate.now());
        tglMulaiPicker.setDayCellFactory((final DatePicker datePicker) -> Function.getDateCellMulai(tglAkhirPicker));
        tglAkhirPicker.setConverter(Function.getTglConverter());
        tglAkhirPicker.setValue(LocalDate.now());
        tglAkhirPicker.setDayCellFactory((final DatePicker datePicker) -> Function.getDateCellAkhir(tglMulaiPicker));

        final ContextMenu rowMenu = new ContextMenu();
        MenuItem cetak = new MenuItem("Print Laporan");
        cetak.setOnAction((ActionEvent e) -> {
            printLaporan();
        });
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent event) -> {
            getKeuangan();
        });
        rowMenu.getItems().addAll(cetak, refresh);
        keuanganTable.setContextMenu(rowMenu);
        keuanganTable.setRowFactory(table -> {
            TreeTableRow<Keuangan> row = new TreeTableRow<Keuangan>() {
                @Override
                public void updateItem(Keuangan item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(rowMenu);
                    } else {
                        final ContextMenu rowMenu = new ContextMenu();
                        MenuItem cetak = new MenuItem("Print Laporan");
                        cetak.setOnAction((ActionEvent e) -> {
                            printLaporan();
                        });
                        MenuItem refresh = new MenuItem("Refresh");
                        refresh.setOnAction((ActionEvent event) -> {
                            getKeuangan();
                        });
                        rowMenu.getItems().addAll(cetak, refresh);
                        setContextMenu(rowMenu);
                    }
                }
            };
            return row;
        });

        allKeuangan.addListener((ListChangeListener.Change<? extends Keuangan> change) -> {
            searchKeuangan();
        });
        searchField.textProperty().addListener(
                (ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
                    searchKeuangan();
                });
        filterData.addAll(allKeuangan);
    }

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
        ObservableList<String> groupBy = FXCollections.observableArrayList();
        groupBy.add("Tanggal");
        groupBy.add("Sales");
        groupBy.add("Kategori");
        groupByCombo.setItems(groupBy);
        groupByCombo.getSelectionModel().select("Tanggal");
        getKeuangan();
    }

    @FXML
    private void getKeuangan() {
        try (Connection con = Koneksi.getConnection()) {
            allKeuangan.clear();
            allKeuangan.addAll(KeuanganDAO.getAllByDate(con,
                    tglMulaiPicker.getValue().toString(),
                    tglAkhirPicker.getValue().toString()));
            saldoAwalField.setText(rp.format(KeuanganDAO.getSaldoAwal(con,
                    tglMulaiPicker.getValue().toString())));
            saldoAkhirField.setText(rp.format(KeuanganDAO.getSaldoAkhir(con,
                    tglAkhirPicker.getValue().toString())));
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

    private void searchKeuangan() {
        try {
            filterData.clear();
            for (Keuangan temp : allKeuangan) {
                if (searchField.getText() == null || searchField.getText().equals("")) {
                    filterData.add(temp);
                } else {
                    if (checkColumn(temp.getNoKeuangan())
                            || checkColumn(tglLengkap.format(tglSql.parse(temp.getTglKeuangan())))
                            || checkColumn(temp.getKategori())
                            || checkColumn(temp.getDeskripsi())
                            || checkColumn(gr.format(temp.getJumlahRp()))
                            || checkColumn(temp.getKodeUser())) {
                        filterData.add(temp);
                    }
                }
            }
            setTable();
        } catch (Exception e) {
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }

    private void setTable() throws Exception {
        if (keuanganTable.getRoot() != null) {
            keuanganTable.getRoot().getChildren().clear();
        }
        List<String> groupBy = new ArrayList<>();
        for (Keuangan temp : filterData) {
            if (groupByCombo.getSelectionModel().getSelectedItem().equals("Tanggal")) {
                if (!groupBy.contains(temp.getTglKeuangan().substring(0, 10))) {
                    groupBy.add(temp.getTglKeuangan().substring(0, 10));
                }
            } else if (groupByCombo.getSelectionModel().getSelectedItem().equals("Sales")) {
                if (!groupBy.contains(temp.getKodeUser())) {
                    groupBy.add(temp.getKodeUser());
                }
            } else if (groupByCombo.getSelectionModel().getSelectedItem().equals("Kategori")) {
                if (!groupBy.contains(temp.getKategori())) {
                    groupBy.add(temp.getKategori());
                }
            }
        }
        for (String temp : groupBy) {
            Keuangan katKeu = new Keuangan();
            katKeu.setNoKeuangan(temp);
            TreeItem<Keuangan> parent = new TreeItem<>(katKeu);
            double total = 0;
            for (Keuangan detail : filterData) {
                if (groupByCombo.getSelectionModel().getSelectedItem().equals("Tanggal")) {
                    if (temp.equals(detail.getTglKeuangan().substring(0, 10))) {
                        TreeItem<Keuangan> child = new TreeItem<>(detail);
                        total = total + detail.getJumlahRp();
                        parent.getChildren().addAll(child);
                    }
                } else if (groupByCombo.getSelectionModel().getSelectedItem().equals("Kategori")) {
                    if (temp.equals(detail.getKategori())) {
                        TreeItem<Keuangan> child = new TreeItem<>(detail);
                        total = total + detail.getJumlahRp();
                        parent.getChildren().addAll(child);
                    }
                } else if (groupByCombo.getSelectionModel().getSelectedItem().equals("Sales")) {
                    if (temp.equals(detail.getKodeUser())) {
                        TreeItem<Keuangan> child = new TreeItem<>(detail);
                        total = total + detail.getJumlahRp();
                        parent.getChildren().addAll(child);
                    }
                } 
            }
            parent.getValue().setJumlahRp(total);
            root.getChildren().add(parent);
        }
        keuanganTable.setRoot(root);
    }
    private void printLaporan() {
        try {
            PrintOut report = new PrintOut();
            report.printLaporanKeuangan(filterData, tglMulaiPicker.getValue().toString(),
                    tglAkhirPicker.getValue().toString(), groupByCombo.getSelectionModel().getSelectedItem(), searchField.getText());
        } catch (Exception e) {
            e.printStackTrace();
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }
}
