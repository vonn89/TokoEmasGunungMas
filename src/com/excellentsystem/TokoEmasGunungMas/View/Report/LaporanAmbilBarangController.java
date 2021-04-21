/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasGunungMas.View.Report;

import com.excellentsystem.TokoEmasGunungMas.DAO.AmbilBarangDetailDAO;
import com.excellentsystem.TokoEmasGunungMas.DAO.AmbilBarangHeadDAO;
import com.excellentsystem.TokoEmasGunungMas.DAO.JenisDAO;
import com.excellentsystem.TokoEmasGunungMas.Function;
import static com.excellentsystem.TokoEmasGunungMas.Function.getTreeTableCell;
import com.excellentsystem.TokoEmasGunungMas.Koneksi;
import com.excellentsystem.TokoEmasGunungMas.Main;
import static com.excellentsystem.TokoEmasGunungMas.Main.gr;
import static com.excellentsystem.TokoEmasGunungMas.Main.tglLengkap;
import static com.excellentsystem.TokoEmasGunungMas.Main.tglSql;
import com.excellentsystem.TokoEmasGunungMas.Model.AmbilBarangDetail;
import com.excellentsystem.TokoEmasGunungMas.Model.AmbilBarangHead;
import com.excellentsystem.TokoEmasGunungMas.Model.Jenis;
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
public class LaporanAmbilBarangController {

    @FXML
    private TreeTableView<AmbilBarangDetail> ambilBarangTable;
    @FXML
    private TreeTableColumn<AmbilBarangDetail, String> noAmbilBarangColumn;
    @FXML
    private TreeTableColumn<AmbilBarangDetail, String> tglAmbilBarangColumn;
    @FXML
    private TreeTableColumn<AmbilBarangDetail, String> keteranganColumn;
    @FXML
    private TreeTableColumn<AmbilBarangDetail, Number> totalQtyColumn;
    @FXML
    private TreeTableColumn<AmbilBarangDetail, Number> totalBeratColumn;
    @FXML
    private TreeTableColumn<AmbilBarangDetail, String> kodeUserColumn;

    @FXML
    private TreeTableColumn<AmbilBarangDetail, String> kodeKategoriColumn;
    @FXML
    private TreeTableColumn<AmbilBarangDetail, String> kodeJenisColumn;
    @FXML
    private TreeTableColumn<AmbilBarangDetail, String> namaJenisColumn;
    @FXML
    private TreeTableColumn<AmbilBarangDetail, Number> qtyColumn;
    @FXML
    private TreeTableColumn<AmbilBarangDetail, Number> beratColumn;
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
    final TreeItem<AmbilBarangDetail> root = new TreeItem<>();
    private ObservableList<AmbilBarangDetail> allAmbilBarang = FXCollections.observableArrayList();
    private ObservableList<AmbilBarangDetail> filterData = FXCollections.observableArrayList();
    private Main mainApp;

    public void initialize() {
        noAmbilBarangColumn.setCellValueFactory(param -> param.getValue().getValue().noAmbilProperty());
        
        tglAmbilBarangColumn.setCellValueFactory(cellData -> {
            try {
                return new SimpleStringProperty(tglLengkap.format(tglSql.parse(cellData.getValue().getValue().getAmbilBarangHead().getTglAmbil())));
            } catch (Exception ex) {
                return null;
            }
        });
        tglAmbilBarangColumn.setComparator(Function.sortDate(tglLengkap));
        
        keteranganColumn.setCellValueFactory(param -> param.getValue().getValue().getAmbilBarangHead().keteranganProperty());
        
        kodeUserColumn.setCellValueFactory(param -> param.getValue().getValue().getAmbilBarangHead().kodeUserProperty());
        
        totalQtyColumn.setCellValueFactory(param -> param.getValue().getValue().getAmbilBarangHead().totalBeratProperty());
        totalQtyColumn.setCellFactory(col -> getTreeTableCell(gr));
        
        totalBeratColumn.setCellValueFactory(param -> param.getValue().getValue().getAmbilBarangHead().totalBeratProperty());
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
            getAmbilBarang();
        });
        rowMenu.getItems().addAll(cetak, refresh);
        ambilBarangTable.setContextMenu(rowMenu);
        ambilBarangTable.setRowFactory(table -> {
            TreeTableRow<AmbilBarangDetail> row = new TreeTableRow<AmbilBarangDetail>() {
                @Override
                public void updateItem(AmbilBarangDetail item, boolean empty) {
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
                            getAmbilBarang();
                        });
                        rowMenu.getItems().addAll(cetak, refresh);
                        setContextMenu(rowMenu);
                    }
                }
            };
            return row;
        });
        allAmbilBarang.addListener((ListChangeListener.Change<? extends AmbilBarangDetail> change) -> {
            searchAmbilBarangDetail();
        });
        searchField.textProperty().addListener(
                (ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
                    searchAmbilBarangDetail();
                });
        filterData.addAll(allAmbilBarang);
    }

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
        ObservableList<String> groupBy = FXCollections.observableArrayList();
        groupBy.add("No Ambil");
        groupBy.add("Tanggal");
        groupBy.add("User");
        groupBy.add("Kategori Barang");
        groupBy.add("Jenis Barang");
        groupByCombo.setItems(groupBy);
        groupByCombo.getSelectionModel().select("No Ambil");
        getAmbilBarang();
    }

    @FXML
    private void getAmbilBarang() {
        try (Connection con = Koneksi.getConnection()) {
            allAmbilBarang.clear();
            List<AmbilBarangDetail> listAmbilBarangDetail = AmbilBarangDetailDAO.getAllByTglAmbil(con,
                    mulaiTglPicker.getValue().toString(), akhirTglPicker.getValue().toString());
            List<AmbilBarangHead> listAmbilBarangHead = AmbilBarangHeadDAO.getAllByTglAmbil(con,
                    mulaiTglPicker.getValue().toString(), akhirTglPicker.getValue().toString());
            List<Jenis> listJenis = JenisDAO.getAll(con);
            for (AmbilBarangDetail d : listAmbilBarangDetail) {
                for (AmbilBarangHead h : listAmbilBarangHead) {
                    if (d.getNoAmbil().equals(h.getNoAmbil())) {
                        d.setAmbilBarangHead(h);
                    }
                }
                for (Jenis j : listJenis) {
                    if (d.getKodeJenis().equals(j.getKodeJenis())) {
                        d.setJenis(j);
                    }
                }
            }
            allAmbilBarang.addAll(listAmbilBarangDetail);
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

    private void searchAmbilBarangDetail() {
        try {
            filterData.clear();
            for (AmbilBarangDetail temp : allAmbilBarang) {
                if (searchField.getText() == null || searchField.getText().equals("")) {
                    filterData.add(temp);
                } else {
                    if (checkColumn(temp.getNoAmbil())
                            || checkColumn(tglLengkap.format(tglSql.parse(temp.getAmbilBarangHead().getTglAmbil())))
                            || checkColumn(temp.getAmbilBarangHead().getKeterangan())
                            || checkColumn(temp.getAmbilBarangHead().getKodeUser())
                            || checkColumn(gr.format(temp.getAmbilBarangHead().getTotalBerat()))
                            || checkColumn(gr.format(temp.getAmbilBarangHead().getTotalQty()))
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
        if (ambilBarangTable.getRoot() != null) {
            ambilBarangTable.getRoot().getChildren().clear();
        }
        List<String> groupBy = new ArrayList<>();
        for (AmbilBarangDetail temp : filterData) {
            if (groupByCombo.getSelectionModel().getSelectedItem().equals("No Ambil")) {
                if (!groupBy.contains(temp.getNoAmbil())) {
                    groupBy.add(temp.getNoAmbil());
                }
            } else if (groupByCombo.getSelectionModel().getSelectedItem().equals("Tanggal")) {
                if (!groupBy.contains(temp.getAmbilBarangHead().getTglAmbil().substring(0, 10))) {
                    groupBy.add(temp.getAmbilBarangHead().getTglAmbil().substring(0, 10));
                }
            } else if (groupByCombo.getSelectionModel().getSelectedItem().equals("User")) {
                if (!groupBy.contains(temp.getAmbilBarangHead().getKodeUser())) {
                    groupBy.add(temp.getAmbilBarangHead().getKodeUser());
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
            AmbilBarangDetail head = new AmbilBarangDetail();
            head.setNoAmbil(temp);
            AmbilBarangHead p = new AmbilBarangHead();
            head.setAmbilBarangHead(p);
            Jenis j = new Jenis();
            head.setJenis(j);
            TreeItem<AmbilBarangDetail> parent = new TreeItem<>(head);
            double berat = 0;
            int qty = 0;
            for (AmbilBarangDetail detail : filterData) {
                if (groupByCombo.getSelectionModel().getSelectedItem().equals("No Ambil")) {
                    if (temp.equals(detail.getNoAmbil())) {
                        TreeItem<AmbilBarangDetail> child = new TreeItem<>(detail);
                        parent.getChildren().addAll(child);
                        berat = berat + detail.getBerat();
                        qty = qty + detail.getQty();
                        totalBerat = totalBerat + detail.getBerat();
                        totalQty = totalQty + detail.getQty();
                    }
                } else if (groupByCombo.getSelectionModel().getSelectedItem().equals("Tanggal")) {
                    if (temp.equals(detail.getAmbilBarangHead().getTglAmbil().substring(0, 10))) {
                        TreeItem<AmbilBarangDetail> child = new TreeItem<>(detail);
                        parent.getChildren().addAll(child);
                        berat = berat + detail.getBerat();
                        qty = qty + detail.getQty();
                        totalBerat = totalBerat + detail.getBerat();
                        totalQty = totalQty + detail.getQty();
                    }
                } else if (groupByCombo.getSelectionModel().getSelectedItem().equals("User")) {
                    if (temp.equals(detail.getAmbilBarangHead().getKodeUser())) {
                        TreeItem<AmbilBarangDetail> child = new TreeItem<>(detail);
                        parent.getChildren().addAll(child);
                        berat = berat + detail.getBerat();
                        qty = qty + detail.getQty();
                        totalBerat = totalBerat + detail.getBerat();
                        totalQty = totalQty + detail.getQty();
                    }
                } else if (groupByCombo.getSelectionModel().getSelectedItem().equals("Kategori Barang")) {
                    if (temp.equals(detail.getKodeKategori())) {
                        TreeItem<AmbilBarangDetail> child = new TreeItem<>(detail);
                        parent.getChildren().addAll(child);
                        berat = berat + detail.getBerat();
                        qty = qty + detail.getQty();
                        totalBerat = totalBerat + detail.getBerat();
                        totalQty = totalQty + detail.getQty();
                    }
                } else if (groupByCombo.getSelectionModel().getSelectedItem().equals("Jenis Barang")) {
                    if (temp.equals(detail.getKodeJenis())) {
                        TreeItem<AmbilBarangDetail> child = new TreeItem<>(detail);
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
        ambilBarangTable.setRoot(root);
        totalBeratField.setText(gr.format(totalBerat));
        totalQtyField.setText(gr.format(totalQty));
    }

    private void printLaporan() {
        try {
            PrintOut report = new PrintOut();
            report.printLaporanAmbilBarang(filterData, mulaiTglPicker.getValue().toString(),
                    akhirTglPicker.getValue().toString(), groupByCombo.getSelectionModel().getSelectedItem(), searchField.getText());
        } catch (Exception e) {
            e.printStackTrace();
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }
}
