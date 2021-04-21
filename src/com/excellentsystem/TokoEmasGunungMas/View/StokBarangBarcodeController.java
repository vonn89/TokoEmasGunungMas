/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasGunungMas.View;

import com.excellentsystem.TokoEmasGunungMas.DAO.StokBarangDAO;
import com.excellentsystem.TokoEmasGunungMas.Function;
import static com.excellentsystem.TokoEmasGunungMas.Function.getTreeTableCell;
import com.excellentsystem.TokoEmasGunungMas.Koneksi;
import com.excellentsystem.TokoEmasGunungMas.Main;
import static com.excellentsystem.TokoEmasGunungMas.Main.gr;
import com.excellentsystem.TokoEmasGunungMas.Model.StokBarang;
import com.excellentsystem.TokoEmasGunungMas.PrintOut.PrintOut;
import com.excellentsystem.TokoEmasGunungMas.View.Dialog.DetailGroupBarangController;
import com.excellentsystem.TokoEmasGunungMas.View.Dialog.KartuStokController;
import java.sql.Connection;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableRow;
import javafx.scene.control.TreeTableView;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Yunaz
 */
public class StokBarangBarcodeController {

    @FXML
    private TreeTableView<StokBarang> stokBarangTable;
    @FXML
    private TreeTableColumn<StokBarang, String> kodeJenisColumn;
    @FXML
    private TreeTableColumn<StokBarang, Number> stokAwalColumn;
    @FXML
    private TreeTableColumn<StokBarang, Number> beratAwalColumn;
    @FXML
    private TreeTableColumn<StokBarang, Number> beratAsliAwalColumn;
    @FXML
    private TreeTableColumn<StokBarang, Number> beratLabelAwalColumn;
    @FXML
    private TreeTableColumn<StokBarang, Number> stokMasukColumn;
    @FXML
    private TreeTableColumn<StokBarang, Number> beratMasukColumn;
    @FXML
    private TreeTableColumn<StokBarang, Number> beratAsliMasukColumn;
    @FXML
    private TreeTableColumn<StokBarang, Number> beratLabelMasukColumn;
    @FXML
    private TreeTableColumn<StokBarang, Number> stokKeluarColumn;
    @FXML
    private TreeTableColumn<StokBarang, Number> beratKeluarColumn;
    @FXML
    private TreeTableColumn<StokBarang, Number> beratAsliKeluarColumn;
    @FXML
    private TreeTableColumn<StokBarang, Number> beratLabelKeluarColumn;
    @FXML
    private TreeTableColumn<StokBarang, Number> stokAkhirColumn;
    @FXML
    private TreeTableColumn<StokBarang, Number> beratAkhirColumn;
    @FXML
    private TreeTableColumn<StokBarang, Number> beratAsliAkhirColumn;
    @FXML
    private TreeTableColumn<StokBarang, Number> beratLabelAkhirColumn;

    @FXML
    private TextField searchField;
    @FXML
    private ComboBox<String> groupByCombo;
    @FXML
    private DatePicker tglPicker;
    @FXML
    private Label totalQtyLabel;
    @FXML
    private Label totalBeratLabel;

    private Main mainApp;
    final TreeItem<StokBarang> root = new TreeItem<>();
    private final ObservableList<String> allStatus = FXCollections.observableArrayList();
    private final ObservableList<StokBarang> allStokBarang = FXCollections.observableArrayList();
    private final ObservableList<StokBarang> filterData = FXCollections.observableArrayList();

    public void initialize() {
        kodeJenisColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().kodeJenisProperty());
        stokAwalColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().stokAwalProperty());
        stokAwalColumn.setCellFactory(col -> getTreeTableCell(gr));
        beratAwalColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().beratAwalProperty());
        beratAwalColumn.setCellFactory(col -> getTreeTableCell(gr));
        beratAsliAwalColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().beratAsliAwalProperty());
        beratAsliAwalColumn.setCellFactory(col -> getTreeTableCell(gr));
        beratLabelAwalColumn.setCellValueFactory(cellData -> {
            double beratLabel = (cellData.getValue().getValue().getStokAwal() * Main.sistem.getBeratLabel())
                    + cellData.getValue().getValue().getBeratAsliAwal();
            return new SimpleDoubleProperty(beratLabel);
        });
        beratLabelAwalColumn.setCellFactory(col -> getTreeTableCell(gr));
        stokMasukColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().stokMasukProperty());
        stokMasukColumn.setCellFactory(col -> getTreeTableCell(gr));
        beratMasukColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().beratMasukProperty());
        beratMasukColumn.setCellFactory(col -> getTreeTableCell(gr));
        beratAsliMasukColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().beratAsliMasukProperty());
        beratAsliMasukColumn.setCellFactory(col -> getTreeTableCell(gr));
        beratLabelMasukColumn.setCellValueFactory(cellData -> {
            double beratLabel = (cellData.getValue().getValue().getStokMasuk() * Main.sistem.getBeratLabel())
                    + cellData.getValue().getValue().getBeratAsliMasuk();
            return new SimpleDoubleProperty(beratLabel);
        });
        beratLabelMasukColumn.setCellFactory(col -> getTreeTableCell(gr));
        stokKeluarColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().stokKeluarProperty());
        stokKeluarColumn.setCellFactory(col -> getTreeTableCell(gr));
        beratKeluarColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().beratKeluarProperty());
        beratKeluarColumn.setCellFactory(col -> getTreeTableCell(gr));
        beratAsliKeluarColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().beratAsliKeluarProperty());
        beratAsliKeluarColumn.setCellFactory(col -> getTreeTableCell(gr));
        beratLabelKeluarColumn.setCellValueFactory(cellData -> {
            double beratLabel = (cellData.getValue().getValue().getStokKeluar() * Main.sistem.getBeratLabel())
                    + cellData.getValue().getValue().getBeratAsliKeluar();
            return new SimpleDoubleProperty(beratLabel);
        });
        beratLabelKeluarColumn.setCellFactory(col -> getTreeTableCell(gr));
        stokAkhirColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().stokAkhirProperty());
        stokAkhirColumn.setCellFactory(col -> getTreeTableCell(gr));
        beratAkhirColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().beratAkhirProperty());
        beratAkhirColumn.setCellFactory(col -> getTreeTableCell(gr));
        beratAsliAkhirColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().beratAsliAkhirProperty());
        beratAsliAkhirColumn.setCellFactory(col -> getTreeTableCell(gr));
        beratLabelAkhirColumn.setCellValueFactory(cellData -> {
            double beratLabel = (cellData.getValue().getValue().getStokAkhir() * Main.sistem.getBeratLabel())
                    + cellData.getValue().getValue().getBeratAsliAkhir();
            return new SimpleDoubleProperty(beratLabel);
        });
        beratLabelAkhirColumn.setCellFactory(col -> getTreeTableCell(gr));

        tglPicker.setConverter(Function.getTglConverter());
        tglPicker.setValue(LocalDate.parse(Main.sistem.getTglSystem(), DateTimeFormatter.ISO_DATE));
        tglPicker.setDayCellFactory((final DatePicker datePicker) -> new DateCell() {
            @Override
            public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                DayOfWeek day = DayOfWeek.from(item);
                if (day == DayOfWeek.SUNDAY) {
                    this.setStyle("-fx-background-color: derive(RED, 150%);");
                }
                if (item.equals(LocalDate.now())) {
                    this.setStyle(" -fx-font-weight:bold;");
                }
                if (item.isAfter(LocalDate.now())) {
                    this.setDisable(true);
                }
            }
        });

        final ContextMenu rowMenu = new ContextMenu();
        MenuItem laporan = new MenuItem("Print Laporan");
        laporan.setOnAction((ActionEvent event) -> {
            printLaporan();
        });
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent event) -> {
            getStokBarang();
        });
        rowMenu.getItems().addAll(laporan, refresh);

        stokBarangTable.setContextMenu(rowMenu);
        stokBarangTable.setRowFactory(ttv -> {
            TreeTableRow<StokBarang> row = new TreeTableRow<StokBarang>() {
                @Override
                public void updateItem(StokBarang item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(rowMenu);
                    } else {
                        final ContextMenu rowMenu = new ContextMenu();
                        MenuItem cek = new MenuItem("Cek Detail");
                        cek.setOnAction((ActionEvent e) -> {
                            showDetail(item);
                        });
                        MenuItem cekKartuStok = new MenuItem("Cek Kartu Stok");
                        cekKartuStok.setOnAction((ActionEvent e) -> {
                            showKartuStok(item);
                        });
                        MenuItem laporan = new MenuItem("Print Laporan");
                        laporan.setOnAction((ActionEvent event) -> {
                            printLaporan();
                        });
                        MenuItem refresh = new MenuItem("Refresh");
                        refresh.setOnAction((ActionEvent event) -> {
                            getStokBarang();
                        });
                        rowMenu.getItems().addAll(cek, cekKartuStok, laporan, refresh);

                        setContextMenu(rowMenu);
                    }
                }
            };
            return row;

        });
        allStokBarang.addListener((ListChangeListener.Change<? extends StokBarang> change) -> {
            searchStokBarang();
        });
        searchField.textProperty().addListener(
                (ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
                    searchStokBarang();
                });
        filterData.addAll(allStokBarang);
    }

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;

        allStatus.add("Gudang");
        allStatus.add("Kategori");
        groupByCombo.setItems(allStatus);
        groupByCombo.getSelectionModel().select("Gudang");

        getStokBarang();
    }

    @FXML
    private void getStokBarang() {
        try (Connection con = Koneksi.getConnection()) {
            allStokBarang.clear();
            if (groupByCombo.getSelectionModel().getSelectedItem().equals("Gudang")) {
                List<StokBarang> listStok = StokBarangDAO.getStokBarcodeGroupByGudangDate(con,
                        tglPicker.getValue().toString());
                allStokBarang.addAll(listStok);
            } else if (groupByCombo.getSelectionModel().getSelectedItem().equals("Kategori")) {
                List<StokBarang> listStok = StokBarangDAO.getStokBarcodeGroupByKategoriDate(con,
                        tglPicker.getValue().toString());
                allStokBarang.addAll(listStok);
            }

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

    private void searchStokBarang() {
        try {
            filterData.clear();
            for (StokBarang temp : allStokBarang) {
                if (searchField.getText() == null || searchField.getText().equals("")) {
                    filterData.add(temp);
                } else {
                    if (checkColumn(temp.getKodeKategori())
                            || checkColumn(temp.getKodeJenis())
                            || checkColumn(temp.getKodeGudang())) {
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
        if (stokBarangTable.getRoot() != null) {
            stokBarangTable.getRoot().getChildren().clear();
        }
        List<String> group = new ArrayList<>();
        for (StokBarang temp : filterData) {
            if (groupByCombo.getSelectionModel().getSelectedItem().equals("Gudang")
                    && !group.contains(temp.getKodeGudang())) {
                group.add(temp.getKodeGudang());
            } else if (groupByCombo.getSelectionModel().getSelectedItem().equals("Kategori")
                    && !group.contains(temp.getKodeKategori())) {
                group.add(temp.getKodeKategori());
            }
        }
        for (String temp : group) {
            StokBarang head = new StokBarang();
            head.setKodeJenis(temp);
            TreeItem<StokBarang> parent = new TreeItem<>(head);
            double b0 = 0;
            double ba0 = 0;
            int s0 = 0;
            double bIn = 0;
            double baIn = 0;
            int sIn = 0;
            double bOut = 0;
            double baOut = 0;
            int sOut = 0;
            double b1 = 0;
            double ba1 = 0;
            int s1 = 0;
            for (StokBarang temp2 : filterData) {
                if (groupByCombo.getSelectionModel().getSelectedItem().equals("Gudang")
                        && temp.equals(temp2.getKodeGudang())) {
                    TreeItem<StokBarang> child = new TreeItem<>(temp2);
                    parent.getChildren().add(child);
                    b0 = b0 + temp2.getBeratAwal();
                    ba0 = ba0 + temp2.getBeratAsliAwal();
                    s0 = s0 + temp2.getStokAwal();
                    bIn = bIn + temp2.getBeratMasuk();
                    baIn = baIn + temp2.getBeratAsliMasuk();
                    sIn = sIn + temp2.getStokMasuk();
                    bOut = bOut + temp2.getBeratKeluar();
                    baOut = baOut + temp2.getBeratAsliKeluar();
                    sOut = sOut + temp2.getStokKeluar();
                    b1 = b1 + temp2.getBeratAkhir();
                    ba1 = ba1 + temp2.getBeratAsliAkhir();
                    s1 = s1 + temp2.getStokAkhir();
                } else if (groupByCombo.getSelectionModel().getSelectedItem().equals("Kategori")
                        && temp.equals(temp2.getKodeKategori())) {
                    TreeItem<StokBarang> child = new TreeItem<>(temp2);
                    parent.getChildren().add(child);
                    b0 = b0 + temp2.getBeratAwal();
                    ba0 = ba0 + temp2.getBeratAsliAwal();
                    s0 = s0 + temp2.getStokAwal();
                    bIn = bIn + temp2.getBeratMasuk();
                    baIn = baIn + temp2.getBeratAsliMasuk();
                    sIn = sIn + temp2.getStokMasuk();
                    bOut = bOut + temp2.getBeratKeluar();
                    baOut = baOut + temp2.getBeratAsliKeluar();
                    sOut = sOut + temp2.getStokKeluar();
                    b1 = b1 + temp2.getBeratAkhir();
                    ba1 = ba1 + temp2.getBeratAsliAkhir();
                    s1 = s1 + temp2.getStokAkhir();
                }
            }
            parent.getValue().setBeratAwal(b0);
            parent.getValue().setBeratAsliAwal(ba0);
            parent.getValue().setStokAwal(s0);
            parent.getValue().setBeratMasuk(bIn);
            parent.getValue().setBeratAsliMasuk(baIn);
            parent.getValue().setStokMasuk(sIn);
            parent.getValue().setBeratKeluar(bOut);
            parent.getValue().setBeratAsliKeluar(baOut);
            parent.getValue().setStokKeluar(sOut);
            parent.getValue().setBeratAkhir(b1);
            parent.getValue().setBeratAsliAkhir(ba1);
            parent.getValue().setStokAkhir(s1);
            root.getChildren().add(parent);
        }
        stokBarangTable.setRoot(root);
        hitungTotal();
    }

    private void hitungTotal() {
        int totalQty = 0;
        double totalBerat = 0;
        for (StokBarang b : filterData) {
            totalQty = totalQty + b.getStokAkhir();
            totalBerat = totalBerat + b.getBeratAkhir();
        }
        totalQtyLabel.setText(gr.format(totalQty));
        totalBeratLabel.setText(gr.format(totalBerat));
    }

    private void showDetail(StokBarang s) {
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/DetailGroupBarang.fxml");
        DetailGroupBarangController x = loader.getController();
        x.setMainApp(mainApp, mainApp.MainStage, stage);
        if (groupByCombo.getSelectionModel().getSelectedItem().equals("Gudang")) {
            if (s.getKodeGudang() != null) {
                x.setBarang(s.getKodeGudang(), "", s.getKodeJenis(), tglPicker.getValue().toString());
            } else {
                x.setBarang(s.getKodeJenis(), "", "", tglPicker.getValue().toString());
            }
        } else if (groupByCombo.getSelectionModel().getSelectedItem().equals("Kategori")) {
            if (s.getKodeKategori() != null) {
                x.setBarang("", s.getKodeKategori(), s.getKodeJenis(), tglPicker.getValue().toString());
            } else {
                x.setBarang("", s.getKodeJenis(), "", tglPicker.getValue().toString());
            }
        }
    }

    private void showKartuStok(StokBarang s) {
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/KartuStok.fxml");
        KartuStokController x = loader.getController();
        x.setMainApp(mainApp, mainApp.MainStage, stage);
        x.setBarang(s.getKodeGudang(), s.getKodeKategori(), s.getKodeJenis());
        if (groupByCombo.getSelectionModel().getSelectedItem().equals("Gudang")) {
            if (s.getKodeGudang() != null) {
                x.setBarang(s.getKodeGudang(), "", s.getKodeJenis());
            } else {
                x.setBarang(s.getKodeJenis(), "", "");
            }
        } else if (groupByCombo.getSelectionModel().getSelectedItem().equals("Kategori")) {
            if (s.getKodeKategori() != null) {
                x.setBarang("", s.getKodeKategori(), s.getKodeJenis());
            } else {
                x.setBarang("", s.getKodeJenis(), "");
            }
        }
    }

    private void printLaporan() {
        try {
            PrintOut report = new PrintOut();
            report.printLaporanStokBarang(filterData, tglPicker.getValue().toString(), groupByCombo.getSelectionModel().getSelectedItem(), searchField.getText());
        } catch (Exception e) {
            e.printStackTrace();
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }
}
