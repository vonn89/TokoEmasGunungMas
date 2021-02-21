/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasGunungMas.View;

import com.excellentsystem.TokoEmasGunungMas.DAO.StokBarangDAO;
import com.excellentsystem.TokoEmasGunungMas.Function;
import static com.excellentsystem.TokoEmasGunungMas.Function.getTableCell;
import com.excellentsystem.TokoEmasGunungMas.Koneksi;
import com.excellentsystem.TokoEmasGunungMas.Main;
import static com.excellentsystem.TokoEmasGunungMas.Main.gr;
import com.excellentsystem.TokoEmasGunungMas.Model.StokBarang;
import com.excellentsystem.TokoEmasGunungMas.View.Dialog.AmbilBarangController;
import com.excellentsystem.TokoEmasGunungMas.View.Dialog.KartuStokBarangDalamController;
import com.excellentsystem.TokoEmasGunungMas.View.Dialog.TambahBarangController;
import java.sql.Connection;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
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
public class DataBarangNonBarcodeController {

    @FXML
    private TableView<StokBarang> stokBarangTable;
    @FXML
    private TableColumn<StokBarang, String> kodeKategoriColumn;
    @FXML
    private TableColumn<StokBarang, String> kodeJenisColumn;

    @FXML
    private TableColumn<StokBarang, Number> beratAwalColumn;
    @FXML
    private TableColumn<StokBarang, Number> stokAwalColumn;
    @FXML
    private TableColumn<StokBarang, Number> beratMasukColumn;
    @FXML
    private TableColumn<StokBarang, Number> stokMasukColumn;
    @FXML
    private TableColumn<StokBarang, Number> beratKeluarColumn;
    @FXML
    private TableColumn<StokBarang, Number> stokKeluarColumn;
    @FXML
    private TableColumn<StokBarang, Number> beratAkhirColumn;
    @FXML
    private TableColumn<StokBarang, Number> stokAkhirColumn;
    @FXML
    private TextField searchField;
    @FXML
    private DatePicker tglPicker;
    @FXML
    private Label stokAwal;
    @FXML
    private Label beratAwal;
    @FXML
    private Label stokMasuk;
    @FXML
    private Label beratMasuk;
    @FXML
    private Label stokKeluar;
    @FXML
    private Label beratKeluar;
    @FXML
    private Label stokAkhir;
    @FXML
    private Label beratAkhir;
    private Main mainApp;
    private ObservableList<StokBarang> allStokBarang = FXCollections.observableArrayList();
    private ObservableList<StokBarang> filterData = FXCollections.observableArrayList();

    public void initialize() {
        kodeKategoriColumn.setCellValueFactory(cellData -> cellData.getValue().kodeKategoriProperty());
        kodeJenisColumn.setCellValueFactory(cellData -> cellData.getValue().kodeJenisProperty());
        beratAwalColumn.setCellValueFactory(cellData -> cellData.getValue().beratAwalProperty());
        beratAwalColumn.setCellFactory(col -> getTableCell(gr));
        stokAwalColumn.setCellValueFactory(cellData -> cellData.getValue().stokAwalProperty());
        stokAwalColumn.setCellFactory(col -> getTableCell(gr));
        beratMasukColumn.setCellValueFactory(cellData -> cellData.getValue().beratMasukProperty());
        beratMasukColumn.setCellFactory(col -> getTableCell(gr));
        stokMasukColumn.setCellValueFactory(cellData -> cellData.getValue().stokMasukProperty());
        stokMasukColumn.setCellFactory(col -> getTableCell(gr));
        beratKeluarColumn.setCellValueFactory(cellData -> cellData.getValue().beratKeluarProperty());
        beratKeluarColumn.setCellFactory(col -> getTableCell(gr));
        stokKeluarColumn.setCellValueFactory(cellData -> cellData.getValue().stokKeluarProperty());
        stokKeluarColumn.setCellFactory(col -> getTableCell(gr));
        beratAkhirColumn.setCellValueFactory(cellData -> cellData.getValue().beratAkhirProperty());
        beratAkhirColumn.setCellFactory(col -> getTableCell(gr));
        stokAkhirColumn.setCellValueFactory(cellData -> cellData.getValue().stokAkhirProperty());
        stokAkhirColumn.setCellFactory(col -> getTableCell(gr));

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
        MenuItem tambah = new MenuItem("Tambah Barang");
        tambah.setOnAction((ActionEvent e) -> {
            showTambahBarang();
        });
        MenuItem ambil = new MenuItem("Ambil Barang");
        ambil.setOnAction((ActionEvent event) -> {
            showAmbilBarang();
        });
        MenuItem barcode = new MenuItem("Barcode Barang");
        barcode.setOnAction((ActionEvent event) -> {
            showBarcodeBarang();
        });
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent event) -> {
            getStokBarang();
        });
        final ContextMenu rowMenu = new ContextMenu();
        rowMenu.getItems().addAll(tambah, ambil, barcode, refresh);
        stokBarangTable.setContextMenu(rowMenu);
        stokBarangTable.setRowFactory(ttv -> {
            TableRow<StokBarang> row = new TableRow<StokBarang>() {
                @Override
                public void updateItem(StokBarang item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(rowMenu);
                    } else {
                        final ContextMenu rowMenu = new ContextMenu();
                        MenuItem cekKartuStok = new MenuItem("Cek Kartu Stok");
                        cekKartuStok.setOnAction((ActionEvent e) -> {
                            showKartuStok(item);
                        });
                        MenuItem tambah = new MenuItem("Tambah Barang");
                        tambah.setOnAction((ActionEvent e) -> {
                            showTambahBarang();
                        });
                        MenuItem ambil = new MenuItem("Ambil Barang");
                        ambil.setOnAction((ActionEvent event) -> {
                            showAmbilBarang();
                        });
                        MenuItem barcode = new MenuItem("Barcode Barang");
                        barcode.setOnAction((ActionEvent event) -> {
                            showBarcodeBarang();
                        });
                        MenuItem refresh = new MenuItem("Refresh");
                        refresh.setOnAction((ActionEvent event) -> {
                            getStokBarang();
                        });
                        rowMenu.getItems().addAll(cekKartuStok, tambah, ambil, barcode, refresh);
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
        getStokBarang();
        stokBarangTable.setItems(filterData);
    }

    @FXML
    private void getStokBarang() {
        try (Connection con = Koneksi.getConnection()) {
            allStokBarang.clear();
            allStokBarang.addAll(StokBarangDAO.getStokNonBarcodeByDate(con,
                    tglPicker.getValue().toString()));
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
                            || checkColumn(temp.getKodeJenis())) {
                        filterData.add(temp);
                    }
                }
            }
            hitungTotal();
        } catch (Exception e) {
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }

    private void showKartuStok(StokBarang s) {
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/KartuStokBarangDalam.fxml");
        KartuStokBarangDalamController controller = loader.getController();
        controller.setMainApp(mainApp, mainApp.MainStage, stage);
        controller.setBarang(s.getKodeJenis());
    }

    private void hitungTotal() {
        double b0 = 0;
        int s0 = 0;
        double bIn = 0;
        int sIn = 0;
        double bOut = 0;
        int sOut = 0;
        double b1 = 0;
        int s1 = 0;
        for (StokBarang s : filterData) {
            b0 = b0 + s.getBeratAwal();
            s0 = s0 + s.getStokAwal();
            bIn = bIn + s.getBeratMasuk();
            sIn = sIn + s.getStokMasuk();
            bOut = bOut + s.getBeratKeluar();
            sOut = sOut + s.getStokKeluar();
            b1 = b1 + s.getBeratAkhir();
            s1 = s1 + s.getStokAkhir();
        }
        beratAwal.setText(gr.format(b0));
        stokAwal.setText(gr.format(s0));
        beratMasuk.setText(gr.format(bIn));
        stokMasuk.setText(gr.format(sIn));
        beratKeluar.setText(gr.format(bOut));
        stokKeluar.setText(gr.format(sOut));
        beratAkhir.setText(gr.format(b1));
        stokAkhir.setText(gr.format(s1));
    }

    @FXML
    private void showBarcodeBarang() {
        mainApp.showBarcodeBarang();
    }

    private void showTambahBarang() {
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/TambahBarang.fxml");
        TambahBarangController controller = loader.getController();
        controller.setMainApp(mainApp, mainApp.MainStage, stage);
    }

    private void showAmbilBarang() {
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/AmbilBarang.fxml");
        AmbilBarangController controller = loader.getController();
        controller.setMainApp(mainApp, mainApp.MainStage, stage);
    }
}
