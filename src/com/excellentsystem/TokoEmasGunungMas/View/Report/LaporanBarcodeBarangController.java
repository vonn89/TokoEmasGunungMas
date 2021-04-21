/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasGunungMas.View.Report;

import com.excellentsystem.TokoEmasGunungMas.DAO.BarangDAO;
import com.excellentsystem.TokoEmasGunungMas.Function;
import static com.excellentsystem.TokoEmasGunungMas.Function.getTreeTableCell;
import com.excellentsystem.TokoEmasGunungMas.Koneksi;
import com.excellentsystem.TokoEmasGunungMas.Main;
import static com.excellentsystem.TokoEmasGunungMas.Main.gr;
import static com.excellentsystem.TokoEmasGunungMas.Main.rp;
import static com.excellentsystem.TokoEmasGunungMas.Main.tglLengkap;
import static com.excellentsystem.TokoEmasGunungMas.Main.tglSql;
import com.excellentsystem.TokoEmasGunungMas.Model.Barang;
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
public class LaporanBarcodeBarangController {

    @FXML
    private TreeTableView<Barang> barcodeBarangHeadTable;
    @FXML
    private TreeTableColumn<Barang, String> kodeBarcodeColumn;
    @FXML
    private TreeTableColumn<Barang, String> tglBarcodeColumn;
    @FXML
    private TreeTableColumn<Barang, String> namaBarangColumn;
    @FXML
    private TreeTableColumn<Barang, String> keteranganColumn;
    @FXML
    private TreeTableColumn<Barang, String> kodeKategoriColumn;
    @FXML
    private TreeTableColumn<Barang, String> kodeJenisColumn;
    @FXML
    private TreeTableColumn<Barang, String> kodeGudangColumn;
    @FXML
    private TreeTableColumn<Barang, String> kodeInternColumn;
    @FXML
    private TreeTableColumn<Barang, String> kadarColumn;
    @FXML
    private TreeTableColumn<Barang, Number> beratColumn;
    @FXML
    private TreeTableColumn<Barang, Number> beratAsliColumn;
    @FXML
    private TreeTableColumn<Barang, Number> beratKemasanColumn;
    @FXML
    private TreeTableColumn<Barang, Number> nilaiPokokColumn;
    @FXML
    private TreeTableColumn<Barang, Number> hargaJualColumn;
    @FXML
    private TreeTableColumn<Barang, String> userBarcodeColumn;
    @FXML
    private TreeTableColumn<Barang, String> statusBarangColumn;

    @FXML
    private ComboBox<String> groupByCombo;
    @FXML
    private Label totalQtyField;
    @FXML
    private Label totalBeratField;
    @FXML
    private Label totalBeratAsliField;
    @FXML
    private Label totalBeratKemasanField;
    @FXML
    private DatePicker tglStartPicker;
    @FXML
    private DatePicker tglAkhirPicker;
    @FXML
    private TextField searchField;
    private Main mainApp;
    final TreeItem<Barang> root = new TreeItem<>();
    private ObservableList<Barang> allBarcodeBarang = FXCollections.observableArrayList();
    private ObservableList<Barang> filterData = FXCollections.observableArrayList();

    public void initialize() {
        kodeBarcodeColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().kodeBarcodeProperty());
        
        tglBarcodeColumn.setCellValueFactory(cellData -> {
            try {
                return new SimpleStringProperty(tglLengkap.format(tglSql.parse(cellData.getValue().getValue().getBarcodeDate())));
            } catch (Exception ex) {
                return null;
            }
        });
        tglBarcodeColumn.setComparator(Function.sortDate(tglLengkap));
        
        namaBarangColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().namaBarangProperty());
        
        keteranganColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().keteranganProperty());
        
        kodeKategoriColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().kodeKategoriProperty());
        
        kodeJenisColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().kodeJenisProperty());
        
        kodeGudangColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().kodeGudangProperty());
        
        kodeInternColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().kodeInternProperty());
        
        kadarColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().kadarProperty());
        
        beratColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().beratProperty());
        beratColumn.setCellFactory(col -> getTreeTableCell(gr));
        
        beratAsliColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().beratAsliProperty());
        beratAsliColumn.setCellFactory(col -> getTreeTableCell(gr));
        
        beratKemasanColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().beratKemasanProperty());
        beratKemasanColumn.setCellFactory(col -> getTreeTableCell(gr));
        
        nilaiPokokColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().nilaiPokokProperty());
        nilaiPokokColumn.setCellFactory(col -> getTreeTableCell(rp));
        
        hargaJualColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().hargaJualProperty());
        hargaJualColumn.setCellFactory(col -> getTreeTableCell(rp));
        
        statusBarangColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().statusBarangProperty());
        
        userBarcodeColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().barcodeByProperty());

        tglStartPicker.setConverter(Function.getTglConverter());
        tglStartPicker.setValue(LocalDate.now());
        tglStartPicker.setDayCellFactory((final DatePicker datePicker) -> Function.getDateCellMulai(tglAkhirPicker));
        tglAkhirPicker.setConverter(Function.getTglConverter());
        tglAkhirPicker.setValue(LocalDate.now());
        tglAkhirPicker.setDayCellFactory((final DatePicker datePicker) -> Function.getDateCellAkhir(tglStartPicker));
        
        final ContextMenu rowMenu = new ContextMenu();
        MenuItem cetak = new MenuItem("Print Laporan");
        cetak.setOnAction((ActionEvent e) -> {
            printLaporan();
        });
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent event) -> {
            getBarcodeBarang();
        });
        rowMenu.getItems().addAll(cetak, refresh);
        barcodeBarangHeadTable.setContextMenu(rowMenu);
        barcodeBarangHeadTable.setRowFactory(table -> {
            TreeTableRow<Barang> row = new TreeTableRow<Barang>() {
                @Override
                public void updateItem(Barang item, boolean empty) {
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
                            getBarcodeBarang();
                        });
                        rowMenu.getItems().addAll(cetak, refresh);
                        setContextMenu(rowMenu);
                    }
                }
            };
            return row;
        });
        allBarcodeBarang.addListener((ListChangeListener.Change<? extends Barang> change) -> {
            searchBarcodeBarang();
        });
        searchField.textProperty().addListener(
                (ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
                    searchBarcodeBarang();
                });
        filterData.addAll(allBarcodeBarang);
    }

    public void setMainApp(Main mainApp) {
        try {
            this.mainApp = mainApp;
            ObservableList<String> groupBy = FXCollections.observableArrayList();
            groupBy.add("Tanggal");
            groupBy.add("User");
            groupBy.add("Kategori Barang");
            groupBy.add("Jenis Barang");
            groupByCombo.setItems(groupBy);
            groupByCombo.getSelectionModel().select("Tanggal");
            getBarcodeBarang();
        } catch (Exception e) {
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }

    @FXML
    private void getBarcodeBarang() {
        try(Connection con = Koneksi.getConnection()){
            allBarcodeBarang.clear();
            allBarcodeBarang.addAll(BarangDAO.getAllByTglBarcode(con, 
                    tglStartPicker.getValue().toString(), tglAkhirPicker.getValue().toString()));
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

    private void searchBarcodeBarang() {
        try {
            filterData.clear();
            for (Barang temp : allBarcodeBarang) {
                if (searchField.getText() == null || searchField.getText().equals("")) {
                    filterData.add(temp);
                } else {
                    if (checkColumn(temp.getKodeBarcode())
                            || checkColumn(tglLengkap.format(tglSql.parse(temp.getBarcodeDate())))
                            || checkColumn(temp.getNamaBarang())
                            || checkColumn(temp.getKeterangan())
                            || checkColumn(temp.getKodeKategori())
                            || checkColumn(temp.getKodeJenis())
                            || checkColumn(temp.getKodeGudang())
                            || checkColumn(temp.getKodeIntern())
                            || checkColumn(temp.getKadar())
                            || checkColumn(temp.getStatusBarang())
                            || checkColumn(gr.format(temp.getBerat()))
                            || checkColumn(gr.format(temp.getBeratAsli()))
                            || checkColumn(rp.format(temp.getNilaiPokok()))
                            || checkColumn(rp.format(temp.getHargaJual()))
                            || checkColumn(temp.getBarcodeBy())) {
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
        if (barcodeBarangHeadTable.getRoot() != null) {
            barcodeBarangHeadTable.getRoot().getChildren().clear();
        }
        List<String> groupBy = new ArrayList<>();
        for (Barang temp : filterData) {
            if (groupByCombo.getSelectionModel().getSelectedItem().equals("Tanggal")) {
                if (!groupBy.contains(temp.getBarcodeDate().substring(0, 10))) {
                    groupBy.add(temp.getBarcodeDate().substring(0, 10));
                }
            } else if (groupByCombo.getSelectionModel().getSelectedItem().equals("User")) {
                if (!groupBy.contains(temp.getBarcodeBy())) {
                    groupBy.add(temp.getBarcodeBy());
                }
            } else if (groupByCombo.getSelectionModel().getSelectedItem().equals("Kategori Barang")) {
                if (!groupBy.contains(temp.getKodeKategori())) {
                    groupBy.add(temp.getKodeKategori());
                }
            } else if (groupByCombo.getSelectionModel().getSelectedItem().equals("Jenis Barang")) {
                if (!groupBy.contains(temp.getKodeJenis())) {
                    groupBy.add(temp.getKodeJenis());
                }
            }
        }
        double totalBerat = 0;
        double totalBeratAsli = 0;
        double totalBeratKemasan = 0;
        int totalQty = 0;
        for (String temp : groupBy) {
            Barang head = new Barang();
            head.setKodeBarcode(temp);
            TreeItem<Barang> parent = new TreeItem<>(head);
            double berat = 0;
            double beratAsli = 0;
            double beratKemasan = 0;
            for (Barang detail : filterData) {
                if (groupByCombo.getSelectionModel().getSelectedItem().equals("Tanggal")) {
                    if (temp.equals(detail.getBarcodeDate().substring(0, 10))) {
                        TreeItem<Barang> child = new TreeItem<>(detail);
                        parent.getChildren().addAll(child);
                        berat = berat + detail.getBerat();
                        beratAsli = beratAsli + detail.getBeratAsli();
                        beratKemasan = beratKemasan + detail.getBeratKemasan();
                        totalBerat = totalBerat + detail.getBerat();
                        totalBeratAsli = totalBeratAsli + detail.getBeratAsli();
                        totalBeratKemasan = totalBeratKemasan + detail.getBeratKemasan();
                        totalQty = totalQty + 1;
                    }
                } else if (groupByCombo.getSelectionModel().getSelectedItem().equals("User")) {
                    if (temp.equals(detail.getBarcodeBy())) {
                        TreeItem<Barang> child = new TreeItem<>(detail);
                        parent.getChildren().addAll(child);
                        berat = berat + detail.getBerat();
                        beratAsli = beratAsli + detail.getBeratAsli();
                        beratKemasan = beratKemasan + detail.getBeratKemasan();
                        totalBerat = totalBerat + detail.getBerat();
                        totalBeratAsli = totalBeratAsli + detail.getBeratAsli();
                        totalBeratKemasan = totalBeratKemasan + detail.getBeratKemasan();
                        totalQty = totalQty + 1;
                    }
                } else if (groupByCombo.getSelectionModel().getSelectedItem().equals("Kategori Barang")) {
                    if (temp.equals(detail.getKodeKategori())) {
                        TreeItem<Barang> child = new TreeItem<>(detail);
                        parent.getChildren().addAll(child);
                        berat = berat + detail.getBerat();
                        beratAsli = beratAsli + detail.getBeratAsli();
                        beratKemasan = beratKemasan + detail.getBeratKemasan();
                        totalBerat = totalBerat + detail.getBerat();
                        totalBeratAsli = totalBeratAsli + detail.getBeratAsli();
                        totalBeratKemasan = totalBeratKemasan + detail.getBeratKemasan();
                        totalQty = totalQty + 1;
                    }
                } else if (groupByCombo.getSelectionModel().getSelectedItem().equals("Jenis Barang")) {
                    if (temp.equals(detail.getKodeJenis())) {
                        TreeItem<Barang> child = new TreeItem<>(detail);
                        parent.getChildren().addAll(child);
                        berat = berat + detail.getBerat();
                        beratAsli = beratAsli + detail.getBeratAsli();
                        beratKemasan = beratKemasan + detail.getBeratKemasan();
                        totalBerat = totalBerat + detail.getBerat();
                        totalBeratAsli = totalBeratAsli + detail.getBeratAsli();
                        totalBeratKemasan = totalBeratKemasan + detail.getBeratKemasan();
                        totalQty = totalQty + 1;
                    }
                }
            }
            parent.getValue().setBerat(berat);
            parent.getValue().setBeratAsli(beratAsli);
            parent.getValue().setBeratAsli(beratKemasan);
            root.getChildren().add(parent);
        }
        barcodeBarangHeadTable.setRoot(root);
        totalBeratAsliField.setText(gr.format(totalBeratAsli));
        totalBeratKemasanField.setText(gr.format(totalBeratKemasan));
        totalBeratField.setText(gr.format(totalBerat));
        totalQtyField.setText(gr.format(totalQty));
    }
    private void printLaporan() {
        try {
            PrintOut report = new PrintOut();
            report.printLaporanBarcodeBarang(filterData, tglStartPicker.getValue().toString(),
                    tglAkhirPicker.getValue().toString(), groupByCombo.getSelectionModel().getSelectedItem(), searchField.getText());
        } catch (Exception e) {
            e.printStackTrace();
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }
}
