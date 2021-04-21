/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasGunungMas.View.Report;

import com.excellentsystem.TokoEmasGunungMas.DAO.GadaiHeadDAO;
import com.excellentsystem.TokoEmasGunungMas.Function;
import static com.excellentsystem.TokoEmasGunungMas.Function.getTreeTableCell;
import com.excellentsystem.TokoEmasGunungMas.Koneksi;
import com.excellentsystem.TokoEmasGunungMas.Main;
import static com.excellentsystem.TokoEmasGunungMas.Main.gr;
import static com.excellentsystem.TokoEmasGunungMas.Main.rp;
import static com.excellentsystem.TokoEmasGunungMas.Main.tglLengkap;
import static com.excellentsystem.TokoEmasGunungMas.Main.tglSql;
import com.excellentsystem.TokoEmasGunungMas.Model.GadaiHead;
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
public class LaporanPelunasanGadaiController {

    @FXML
    private TreeTableView<GadaiHead> gadaiTable;
    @FXML
    private TreeTableColumn<GadaiHead, String> noGadaiColumn;
    @FXML
    private TreeTableColumn<GadaiHead, String> tglGadaiColumn;
    @FXML
    private TreeTableColumn<GadaiHead, String> tglLunasColumn;
    @FXML
    private TreeTableColumn<GadaiHead, String> salesTerimaColumn;
    @FXML
    private TreeTableColumn<GadaiHead, String> salesLunasColumn;
    @FXML
    private TreeTableColumn<GadaiHead, String> kodePelangganColumn;
    @FXML
    private TreeTableColumn<GadaiHead, String> namaColumn;
    @FXML
    private TreeTableColumn<GadaiHead, String> alamatColumn;
    @FXML
    private TreeTableColumn<GadaiHead, String> noTelpColumn;
    @FXML
    private TreeTableColumn<GadaiHead, String> keteranganColumn;
    @FXML
    private TreeTableColumn<GadaiHead, Number> totalBeratColumn;
    @FXML
    private TreeTableColumn<GadaiHead, Number> totalPinjamanColumn;
    @FXML
    private TreeTableColumn<GadaiHead, Number> lamaPinjamColumn;
    @FXML
    private TreeTableColumn<GadaiHead, Number> bungaPersenColumn;
    @FXML
    private TreeTableColumn<GadaiHead, Number> bungaKompColumn;
    @FXML
    private TreeTableColumn<GadaiHead, Number> bungaRpColumn;

    @FXML
    private TextField searchField;
    @FXML
    private ComboBox<String> groupByCombo;
    @FXML
    private Label totalBeratField;
    @FXML
    private Label totalPinjamanField;
    @FXML
    private Label totalBungaKompField;
    @FXML
    private Label totalBungaRpField;
    @FXML
    private DatePicker mulaiTglPicker;
    @FXML
    private DatePicker akhirTglPicker;
    final TreeItem<GadaiHead> root = new TreeItem<>();
    private ObservableList<GadaiHead> allGadai = FXCollections.observableArrayList();
    private ObservableList<GadaiHead> filterData = FXCollections.observableArrayList();
    private Main mainApp;

    public void initialize() {
        noGadaiColumn.setCellValueFactory(param -> param.getValue().getValue().noGadaiProperty());
        
        tglGadaiColumn.setCellValueFactory(cellData -> {
            try {
                return new SimpleStringProperty(
                        tglLengkap.format(
                                tglSql.parse(cellData.getValue().getValue().getTglGadai())));
            } catch (Exception ex) {
                return null;
            }
        });
        tglGadaiColumn.setComparator(Function.sortDate(tglLengkap));
        
        tglLunasColumn.setCellValueFactory(cellData -> {
            try {
                return new SimpleStringProperty(
                        tglLengkap.format(
                                tglSql.parse(cellData.getValue().getValue().getTglLunas())));
            } catch (Exception ex) {
                return null;
            }
        });
        tglLunasColumn.setComparator(Function.sortDate(tglLengkap));
        
        salesTerimaColumn.setCellValueFactory(param -> param.getValue().getValue().kodeSalesProperty());
        
        salesLunasColumn.setCellValueFactory(param -> param.getValue().getValue().salesLunasProperty());
        
        kodePelangganColumn.setCellValueFactory(param -> param.getValue().getValue().kodePelangganProperty());
        
        namaColumn.setCellValueFactory(param -> param.getValue().getValue().namaProperty());
        
        alamatColumn.setCellValueFactory(param -> param.getValue().getValue().alamatProperty());
        
        noTelpColumn.setCellValueFactory(param -> param.getValue().getValue().noTelpProperty());
        
        keteranganColumn.setCellValueFactory(param -> param.getValue().getValue().keteranganProperty());
        
        totalBeratColumn.setCellValueFactory(param -> param.getValue().getValue().totalBeratProperty());
        totalBeratColumn.setCellFactory(col -> getTreeTableCell(gr));
        
        totalPinjamanColumn.setCellValueFactory(param -> param.getValue().getValue().totalPinjamanProperty());
        totalPinjamanColumn.setCellFactory(col -> getTreeTableCell(gr));
        
        lamaPinjamColumn.setCellValueFactory(param -> param.getValue().getValue().lamaPinjamProperty());
        lamaPinjamColumn.setCellFactory(col -> getTreeTableCell(gr));
        
        bungaPersenColumn.setCellValueFactory(param -> param.getValue().getValue().bungaPersenProperty());
        bungaPersenColumn.setCellFactory(col -> getTreeTableCell(gr));
        
        bungaKompColumn.setCellValueFactory(param -> param.getValue().getValue().bungaKompProperty());
        bungaKompColumn.setCellFactory(col -> getTreeTableCell(gr));
        
        bungaRpColumn.setCellValueFactory(param -> param.getValue().getValue().bungaRpProperty());
        bungaRpColumn.setCellFactory(col -> getTreeTableCell(gr));
        

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
            getGadai();
        });
        rowMenu.getItems().addAll(cetak, refresh);
        gadaiTable.setContextMenu(rowMenu);
        gadaiTable.setRowFactory(table -> {
            TreeTableRow<GadaiHead> row = new TreeTableRow<GadaiHead>() {
                @Override
                public void updateItem(GadaiHead item, boolean empty) {
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
                            getGadai();
                        });
                        rowMenu.getItems().addAll(cetak, refresh);
                        setContextMenu(rowMenu);
                    }
                }
            };
            return row;
        });
        allGadai.addListener((ListChangeListener.Change<? extends GadaiHead> change) -> {
            searchGadai();
        });
        searchField.textProperty().addListener(
                (ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
                    searchGadai();
                });
        filterData.addAll(allGadai);
    }

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
        ObservableList<String> groupBy = FXCollections.observableArrayList();
        groupBy.add("Tanggal");
        groupBy.add("Sales");
        groupByCombo.setItems(groupBy);
        groupByCombo.getSelectionModel().select("Tanggal");
        getGadai();
    }

    @FXML
    private void getGadai() {
        try(Connection con = Koneksi.getConnection()){
            allGadai.clear();
            List<GadaiHead> gadai = GadaiHeadDAO.getAllByTglLunasAndStatus(con, 
                    mulaiTglPicker.getValue().toString(), akhirTglPicker.getValue().toString(), "Lunas");
            allGadai.addAll(gadai);
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

    private void searchGadai() {
        try {
            filterData.clear();
            for (GadaiHead temp : allGadai) {
                if (searchField.getText() == null || searchField.getText().equals("")) {
                    filterData.add(temp);
                } else {
                    if (checkColumn(temp.getNoGadai())
                            || checkColumn(tglLengkap.format(tglSql.parse(temp.getTglGadai())))
                            || checkColumn(tglLengkap.format(tglSql.parse(temp.getTglLunas())))
                            || checkColumn(temp.getKodeSales())
                            || checkColumn(temp.getSalesLunas())
                            || checkColumn(temp.getKodePelanggan())
                            || checkColumn(temp.getNama())
                            || checkColumn(temp.getAlamat())
                            || checkColumn(temp.getNoTelp())
                            || checkColumn(gr.format(temp.getTotalBerat()))
                            || checkColumn(gr.format(temp.getTotalPinjaman()))
                            || checkColumn(gr.format(temp.getLamaPinjam()))
                            || checkColumn(gr.format(temp.getBungaPersen()))
                            || checkColumn(gr.format(temp.getBungaKomp()))
                            || checkColumn(gr.format(temp.getBungaRp()))
                            || checkColumn(temp.getKeterangan())
                            || checkColumn(temp.getStatus())) {
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
        if (gadaiTable.getRoot() != null) {
            gadaiTable.getRoot().getChildren().clear();
        }
        List<String> groupBy = new ArrayList<>();
        for (GadaiHead temp : filterData) {
            if (groupByCombo.getSelectionModel().getSelectedItem().equals("Tanggal")) {
                if (!groupBy.contains(temp.getTglLunas().substring(0, 10))) {
                    groupBy.add(temp.getTglLunas().substring(0, 10));
                }
            } else if (groupByCombo.getSelectionModel().getSelectedItem().equals("Sales")) {
                if (!groupBy.contains(temp.getSalesLunas())) {
                    groupBy.add(temp.getSalesLunas());
                }
            }
        }
        double totalBerat = 0;
        double totalPinjaman = 0;
        double totalBungaKomp = 0;
        double totalBungaRp = 0;
        for (String temp : groupBy) {
            GadaiHead head = new GadaiHead();
            head.setNoGadai(temp);
            TreeItem<GadaiHead> parent = new TreeItem<>(head);
            double berat = 0;
            double pinjaman = 0;
            double bungaKomp = 0;
            double bungaRp = 0;
            for (GadaiHead detail : filterData) {
                if (groupByCombo.getSelectionModel().getSelectedItem().equals("Tanggal")) {
                    if (temp.equals(detail.getTglLunas().substring(0, 10))) {
                        TreeItem<GadaiHead> child = new TreeItem<>(detail);
                        parent.getChildren().addAll(child);
                        berat = berat + detail.getTotalBerat();
                        pinjaman = pinjaman + detail.getTotalPinjaman();
                        bungaKomp = bungaKomp + detail.getBungaKomp();
                        bungaRp = bungaRp + detail.getBungaRp();
                        totalBerat = totalBerat + detail.getTotalBerat();
                        totalPinjaman = totalPinjaman + detail.getTotalPinjaman();
                        totalBungaKomp = totalBungaKomp + detail.getBungaKomp();
                        totalBungaRp = totalBungaRp + detail.getBungaRp();
                    }
                } if (groupByCombo.getSelectionModel().getSelectedItem().equals("Sales")) {
                    if (temp.equals(detail.getSalesLunas())) {
                        TreeItem<GadaiHead> child = new TreeItem<>(detail);
                        parent.getChildren().addAll(child);
                        berat = berat + detail.getTotalBerat();
                        pinjaman = pinjaman + detail.getTotalPinjaman();
                        bungaKomp = bungaKomp + detail.getBungaKomp();
                        bungaRp = bungaRp + detail.getBungaRp();
                        totalBerat = totalBerat + detail.getTotalBerat();
                        totalPinjaman = totalPinjaman + detail.getTotalPinjaman();
                        totalBungaKomp = totalBungaKomp + detail.getBungaKomp();
                        totalBungaRp = totalBungaRp + detail.getBungaRp();
                    }
                }
            }
            parent.getValue().setTotalBerat(berat);
            parent.getValue().setTotalPinjaman(pinjaman);
            parent.getValue().setBungaKomp(bungaKomp);
            parent.getValue().setBungaRp(bungaRp);
            root.getChildren().add(parent);
        }
        gadaiTable.setRoot(root);
        totalBeratField.setText(gr.format(totalBerat));
        totalPinjamanField.setText(rp.format(totalPinjaman));
        totalBungaKompField.setText(rp.format(totalBungaKomp));
        totalBungaRpField.setText(rp.format(totalBungaRp));
    }

    private void printLaporan() {
        try {
            PrintOut report = new PrintOut();
            report.printLaporanPelunasanGadai(filterData, mulaiTglPicker.getValue().toString(),
                    akhirTglPicker.getValue().toString(), groupByCombo.getSelectionModel().getSelectedItem(), searchField.getText());
        } catch (Exception e) {
            e.printStackTrace();
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }

}
