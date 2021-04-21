/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasGunungMas.View.Report;

import com.excellentsystem.TokoEmasGunungMas.DAO.JenisDAO;
import com.excellentsystem.TokoEmasGunungMas.DAO.TambahBarangDetailDAO;
import com.excellentsystem.TokoEmasGunungMas.DAO.TambahBarangHeadDAO;
import com.excellentsystem.TokoEmasGunungMas.Function;
import static com.excellentsystem.TokoEmasGunungMas.Function.getTreeTableCell;
import com.excellentsystem.TokoEmasGunungMas.Koneksi;
import com.excellentsystem.TokoEmasGunungMas.Main;
import static com.excellentsystem.TokoEmasGunungMas.Main.gr;
import static com.excellentsystem.TokoEmasGunungMas.Main.tglLengkap;
import static com.excellentsystem.TokoEmasGunungMas.Main.tglSql;
import com.excellentsystem.TokoEmasGunungMas.Model.Jenis;
import com.excellentsystem.TokoEmasGunungMas.Model.TambahBarangDetail;
import com.excellentsystem.TokoEmasGunungMas.Model.TambahBarangHead;
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
public class LaporanTambahBarangController {

    @FXML
    private TreeTableView<TambahBarangDetail> tambahBarangTable;
    @FXML
    private TreeTableColumn<TambahBarangDetail, String> noTambahBarangColumn;
    @FXML
    private TreeTableColumn<TambahBarangDetail, String> tglTambahBarangColumn;
    @FXML
    private TreeTableColumn<TambahBarangDetail, String> keteranganColumn;
    @FXML
    private TreeTableColumn<TambahBarangDetail, Number> totalQtyColumn;
    @FXML
    private TreeTableColumn<TambahBarangDetail, Number> totalBeratColumn;
    @FXML
    private TreeTableColumn<TambahBarangDetail, String> kodeUserColumn;

    @FXML
    private TreeTableColumn<TambahBarangDetail, String> kodeKategoriColumn;
    @FXML
    private TreeTableColumn<TambahBarangDetail, String> kodeJenisColumn;
    @FXML
    private TreeTableColumn<TambahBarangDetail, String> namaJenisColumn;
    @FXML
    private TreeTableColumn<TambahBarangDetail, Number> qtyColumn;
    @FXML
    private TreeTableColumn<TambahBarangDetail, Number> beratColumn;
    @FXML
    private TextField searchField;
    @FXML
    private ComboBox<String> groupByCombo;
    @FXML
    private Label totalBeratField;
    @FXML
    private Label totalQtyField;
    @FXML
    private DatePicker mulaiTglPicker;
    @FXML
    private DatePicker akhirTglPicker;
    final TreeItem<TambahBarangDetail> root = new TreeItem<>();
    private ObservableList<TambahBarangDetail> allTambahBarang = FXCollections.observableArrayList();
    private ObservableList<TambahBarangDetail> filterData = FXCollections.observableArrayList();
    private Main mainApp;

    public void initialize() {
        noTambahBarangColumn.setCellValueFactory(param -> param.getValue().getValue().noTambahProperty());
        
        tglTambahBarangColumn.setCellValueFactory(cellData -> {
            try {
                return new SimpleStringProperty(tglLengkap.format(tglSql.parse(cellData.getValue().getValue().getTambahBarang().getTglTambah())));
            } catch (Exception ex) {
                return null;
            }
        });
        tglTambahBarangColumn.setComparator(Function.sortDate(tglLengkap));
        
        keteranganColumn.setCellValueFactory(param -> param.getValue().getValue().getTambahBarang().keteranganProperty());
        
        kodeUserColumn.setCellValueFactory(param -> param.getValue().getValue().getTambahBarang().kodeUserProperty());
        
        totalQtyColumn.setCellValueFactory(param -> param.getValue().getValue().getTambahBarang().totalBeratProperty());
        totalQtyColumn.setCellFactory(col -> getTreeTableCell(gr));
        
        totalBeratColumn.setCellValueFactory(param -> param.getValue().getValue().getTambahBarang().totalBeratProperty());
        totalBeratColumn.setCellFactory(col -> getTreeTableCell(gr));
        
        namaJenisColumn.setCellValueFactory(param -> param.getValue().getValue().getJenis().namaJenisProperty());
        
        kodeKategoriColumn.setCellValueFactory(param -> param.getValue().getValue().kodeKategoriProperty());
        
        kodeJenisColumn.setCellValueFactory(param -> param.getValue().getValue().kodeJenisProperty());
        
        beratColumn.setCellValueFactory(param -> param.getValue().getValue().beratProperty());
        beratColumn.setCellFactory(col -> getTreeTableCell(gr));
        
        qtyColumn.setCellValueFactory(param -> param.getValue().getValue().qtyProperty());
        qtyColumn.setCellFactory(col -> getTreeTableCell(gr));
        
        mulaiTglPicker.setConverter(Function.getTglConverter());
        mulaiTglPicker.setValue(LocalDate.now());
        mulaiTglPicker.setDayCellFactory((final DatePicker datePicker) -> Function.getDateCellMulai(akhirTglPicker));
        akhirTglPicker.setConverter(Function.getTglConverter());
        akhirTglPicker.setValue(LocalDate.now());
        akhirTglPicker.setDayCellFactory((final DatePicker datePicker) -> Function.getDateCellAkhir(mulaiTglPicker));
        
        final ContextMenu rowMenu = new ContextMenu();
        MenuItem cetak = new MenuItem("Print Laporan");
        cetak.setOnAction((ActionEvent e) -> {
            printLaporan();
        });
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent event) -> {
            getTambahBarang();
        });
        rowMenu.getItems().addAll(cetak, refresh);
        tambahBarangTable.setContextMenu(rowMenu);
        tambahBarangTable.setRowFactory(table -> {
            TreeTableRow<TambahBarangDetail> row = new TreeTableRow<TambahBarangDetail>() {
                @Override
                public void updateItem(TambahBarangDetail item, boolean empty) {
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
                            getTambahBarang();
                        });
                        rowMenu.getItems().addAll(cetak, refresh);
                        setContextMenu(rowMenu);
                    }
                }
            };
            return row;
        });
        allTambahBarang.addListener((ListChangeListener.Change<? extends TambahBarangDetail> change) -> {
            searchTambahBarangDetail();
        });
        searchField.textProperty().addListener(
                (ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
                    searchTambahBarangDetail();
                });
        filterData.addAll(allTambahBarang);
    }

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
        ObservableList<String> groupBy = FXCollections.observableArrayList();
        groupBy.add("No Tambah");
        groupBy.add("Tanggal");
        groupBy.add("User");
        groupBy.add("Kategori Barang");
        groupBy.add("Jenis Barang");
        groupByCombo.setItems(groupBy);
        groupByCombo.getSelectionModel().select("No Tambah");
        getTambahBarang();
    }

    @FXML
    private void getTambahBarang() {
        try (Connection con = Koneksi.getConnection()) {
            allTambahBarang.clear();
            List<TambahBarangDetail> listTambahBarangDetail = TambahBarangDetailDAO.getAllByTglTambah(con, 
                    mulaiTglPicker.getValue().toString(), akhirTglPicker.getValue().toString());
            List<TambahBarangHead> listTambahBarangHead = TambahBarangHeadDAO.getAllByTglTambah(con, 
                    mulaiTglPicker.getValue().toString(), akhirTglPicker.getValue().toString());
            List<Jenis> listJenis = JenisDAO.getAll(con);
            for (TambahBarangDetail d : listTambahBarangDetail) {
                for (TambahBarangHead h : listTambahBarangHead) {
                    if (d.getNoTambah().equals(h.getNoTambah())) {
                        d.setTambahBarang(h);
                    }
                }
                for (Jenis j : listJenis) {
                    if (d.getKodeJenis().equals(j.getKodeJenis())) {
                        d.setJenis(j);
                    }
                }
            }
            allTambahBarang.addAll(listTambahBarangDetail);
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

    private void searchTambahBarangDetail() {
        try {
            filterData.clear();
            for (TambahBarangDetail temp : allTambahBarang) {
                if (searchField.getText() == null || searchField.getText().equals("")) {
                    filterData.add(temp);
                } else {
                    if (checkColumn(temp.getNoTambah())
                            || checkColumn(tglLengkap.format(tglSql.parse(temp.getTambahBarang().getTglTambah())))
                            || checkColumn(temp.getTambahBarang().getKeterangan())
                            || checkColumn(temp.getTambahBarang().getKodeUser())
                            || checkColumn(gr.format(temp.getTambahBarang().getTotalBerat()))
                            || checkColumn(gr.format(temp.getTambahBarang().getTotalQty()))
                            || checkColumn(temp.getKodeKategori())
                            || checkColumn(temp.getKodeJenis())
                            || checkColumn(temp.getJenis().getNamaJenis())
                            || checkColumn(gr.format(temp.getBerat()))
                            || checkColumn(gr.format(temp.getQty()))) {
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
        if (tambahBarangTable.getRoot() != null) {
            tambahBarangTable.getRoot().getChildren().clear();
        }
        List<String> groupBy = new ArrayList<>();
        for (TambahBarangDetail temp : filterData) {
            if (groupByCombo.getSelectionModel().getSelectedItem().equals("No Tambah")) {
                if (!groupBy.contains(temp.getNoTambah())) {
                    groupBy.add(temp.getNoTambah());
                }
            } else if (groupByCombo.getSelectionModel().getSelectedItem().equals("Tanggal")) {
                if (!groupBy.contains(temp.getTambahBarang().getTglTambah().substring(0, 10))) {
                    groupBy.add(temp.getTambahBarang().getTglTambah().substring(0, 10));
                }
            } else if (groupByCombo.getSelectionModel().getSelectedItem().equals("User")) {
                if (!groupBy.contains(temp.getTambahBarang().getKodeUser())) {
                    groupBy.add(temp.getTambahBarang().getKodeUser());
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
        int totalQty = 0;
        for (String temp : groupBy) {
            TambahBarangDetail head = new TambahBarangDetail();
            head.setNoTambah(temp);
            TambahBarangHead p = new TambahBarangHead();
            head.setTambahBarang(p);
            Jenis j = new Jenis();
            head.setJenis(j);
            TreeItem<TambahBarangDetail> parent = new TreeItem<>(head);
            double berat = 0;
            int qty = 0;
            for (TambahBarangDetail detail : filterData) {
                if (groupByCombo.getSelectionModel().getSelectedItem().equals("No Tambah")) {
                    if (temp.equals(detail.getNoTambah())) {
                        TreeItem<TambahBarangDetail> child = new TreeItem<>(detail);
                        parent.getChildren().addAll(child);
                        berat = berat + detail.getBerat();
                        qty = qty + detail.getQty();
                        totalBerat = totalBerat + detail.getBerat();
                        totalQty = totalQty + detail.getQty();
                    }
                } else if (groupByCombo.getSelectionModel().getSelectedItem().equals("Tanggal")) {
                    if (temp.equals(detail.getTambahBarang().getTglTambah().substring(0, 10))) {
                        TreeItem<TambahBarangDetail> child = new TreeItem<>(detail);
                        parent.getChildren().addAll(child);
                        berat = berat + detail.getBerat();
                        qty = qty + detail.getQty();
                        totalBerat = totalBerat + detail.getBerat();
                        totalQty = totalQty + detail.getQty();
                    }
                } else if (groupByCombo.getSelectionModel().getSelectedItem().equals("User")) {
                    if (temp.equals(detail.getTambahBarang().getKodeUser())) {
                        TreeItem<TambahBarangDetail> child = new TreeItem<>(detail);
                        parent.getChildren().addAll(child);
                        berat = berat + detail.getBerat();
                        qty = qty + detail.getQty();
                        totalBerat = totalBerat + detail.getBerat();
                        totalQty = totalQty + detail.getQty();
                    }
                } else if (groupByCombo.getSelectionModel().getSelectedItem().equals("Kategori Barang")) {
                    if (temp.equals(detail.getKodeKategori())) {
                        TreeItem<TambahBarangDetail> child = new TreeItem<>(detail);
                        parent.getChildren().addAll(child);
                        berat = berat + detail.getBerat();
                        qty = qty + detail.getQty();
                        totalBerat = totalBerat + detail.getBerat();
                        totalQty = totalQty + detail.getQty();
                    }
                } else if (groupByCombo.getSelectionModel().getSelectedItem().equals("Jenis Barang")) {
                    if (temp.equals(detail.getKodeJenis())) {
                        TreeItem<TambahBarangDetail> child = new TreeItem<>(detail);
                        parent.getChildren().addAll(child);
                        berat = berat + detail.getBerat();
                        qty = qty + detail.getQty();
                        totalBerat = totalBerat + detail.getBerat();
                        totalQty = totalQty + detail.getQty();
                    }
                }
            }
            parent.getValue().setQty(qty);
            parent.getValue().setBerat(berat);
            root.getChildren().add(parent);
        }
        tambahBarangTable.setRoot(root);
        totalBeratField.setText(gr.format(totalBerat));
        totalQtyField.setText(gr.format(totalQty));
    }

    private void printLaporan() {
        try {
            PrintOut report = new PrintOut();
            report.printLaporanTambahBarang(filterData, mulaiTglPicker.getValue().toString(),
                    akhirTglPicker.getValue().toString(), groupByCombo.getSelectionModel().getSelectedItem(), searchField.getText());
        } catch (Exception e) {
            e.printStackTrace();
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }
}
