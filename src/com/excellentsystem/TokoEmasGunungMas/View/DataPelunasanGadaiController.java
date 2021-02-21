/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasGunungMas.View;

import com.excellentsystem.TokoEmasGunungMas.DAO.GadaiDetailDAO;
import com.excellentsystem.TokoEmasGunungMas.DAO.GadaiHeadDAO;
import com.excellentsystem.TokoEmasGunungMas.Function;
import static com.excellentsystem.TokoEmasGunungMas.Function.getTableCell;
import com.excellentsystem.TokoEmasGunungMas.Koneksi;
import com.excellentsystem.TokoEmasGunungMas.Main;
import static com.excellentsystem.TokoEmasGunungMas.Main.gr;
import static com.excellentsystem.TokoEmasGunungMas.Main.rp;
import static com.excellentsystem.TokoEmasGunungMas.Main.sistem;
import static com.excellentsystem.TokoEmasGunungMas.Main.tglLengkap;
import static com.excellentsystem.TokoEmasGunungMas.Main.tglSql;
import com.excellentsystem.TokoEmasGunungMas.Model.GadaiDetail;
import com.excellentsystem.TokoEmasGunungMas.Model.GadaiHead;
import com.excellentsystem.TokoEmasGunungMas.PrintOut.PrintOut;
import com.excellentsystem.TokoEmasGunungMas.Service;
import com.excellentsystem.TokoEmasGunungMas.View.Dialog.DetailPelunasanGadaiController;
import com.excellentsystem.TokoEmasGunungMas.View.Dialog.VerifikasiController;
import java.sql.Connection;
import java.text.ParseException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;
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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Yunaz
 */
public class DataPelunasanGadaiController {

    @FXML
    private TableView<GadaiHead> gadaiTable;
    @FXML
    private TableColumn<GadaiHead, String> noGadaiColumn;
    @FXML
    private TableColumn<GadaiHead, String> tglLunasColumn;
    @FXML
    private TableColumn<GadaiHead, String> kodePelangganColumn;
    @FXML
    private TableColumn<GadaiHead, String> namaPelangganColumn;
    @FXML
    private TableColumn<GadaiHead, String> alamatPelangganColumn;
    @FXML
    private TableColumn<GadaiHead, String> noTelpPelangganColumn;
    @FXML
    private TableColumn<GadaiHead, Number> totalBeratColumn;
    @FXML
    private TableColumn<GadaiHead, Number> totalPinjamanColumn;
    @FXML
    private TableColumn<GadaiHead, Number> lamaPinjamColumn;
    @FXML
    private TableColumn<GadaiHead, Number> bungaPersenColumn;
    @FXML
    private TableColumn<GadaiHead, Number> bungaKompColumn;
    @FXML
    private TableColumn<GadaiHead, Number> bungaRpColumn;
    @FXML
    private TableColumn<GadaiHead, String> keteranganColumn;
    @FXML
    private TableColumn<GadaiHead, String> salesLunasColumn;
    @FXML
    private TableColumn<GadaiHead, String> userLunasColumn;

    @FXML
    private TextField searchField;
    @FXML
    private DatePicker tglStartPicker;
    @FXML
    private DatePicker tglAkhirPicker;
    @FXML
    private Label totalGadaiField;
    @FXML
    private Label totalBungaField;
    private ObservableList<GadaiHead> allGadai = FXCollections.observableArrayList();
    private ObservableList<GadaiHead> filterData = FXCollections.observableArrayList();
    private Main mainApp;

    public void initialize() {
        noGadaiColumn.setCellValueFactory(cellData -> cellData.getValue().noGadaiProperty());
        kodePelangganColumn.setCellValueFactory(cellData -> cellData.getValue().kodePelangganProperty());
        namaPelangganColumn.setCellValueFactory(cellData -> cellData.getValue().namaProperty());
        alamatPelangganColumn.setCellValueFactory(cellData -> cellData.getValue().alamatProperty());
        noTelpPelangganColumn.setCellValueFactory(cellData -> cellData.getValue().noTelpProperty());
        keteranganColumn.setCellValueFactory(cellData -> cellData.getValue().keteranganProperty());
        salesLunasColumn.setCellValueFactory(cellData -> cellData.getValue().salesLunasProperty());
        userLunasColumn.setCellValueFactory(cellData -> cellData.getValue().userLunasProperty());
        tglLunasColumn.setCellValueFactory(cellData -> {
            try {
                return new SimpleStringProperty(tglLengkap.format(
                        tglSql.parse(cellData.getValue().getTglLunas())));
            } catch (ParseException ex) {
                return null;
            }
        });
        totalBeratColumn.setCellValueFactory(celldata -> celldata.getValue().totalBeratProperty());
        totalBeratColumn.setCellFactory(col -> getTableCell(gr));
        totalPinjamanColumn.setCellValueFactory(celldata -> celldata.getValue().totalPinjamanProperty());
        totalPinjamanColumn.setCellFactory(col -> getTableCell(rp));
        lamaPinjamColumn.setCellValueFactory(celldata -> celldata.getValue().lamaPinjamProperty());
        lamaPinjamColumn.setCellFactory(col -> getTableCell(gr));
        bungaPersenColumn.setCellValueFactory(celldata -> celldata.getValue().bungaPersenProperty());
        bungaPersenColumn.setCellFactory(col -> getTableCell(gr));
        bungaKompColumn.setCellValueFactory(celldata -> celldata.getValue().bungaKompProperty());
        bungaKompColumn.setCellFactory(col -> getTableCell(rp));
        bungaRpColumn.setCellValueFactory(celldata -> celldata.getValue().bungaRpProperty());
        bungaRpColumn.setCellFactory(col -> getTableCell(rp));

        tglStartPicker.setConverter(Function.getTglConverter());
        tglStartPicker.setValue(LocalDate.parse(Main.sistem.getTglSystem(), DateTimeFormatter.ISO_DATE));
        tglStartPicker.setDayCellFactory((final DatePicker datePicker) -> new DateCell() {
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
                if (item.isAfter(tglAkhirPicker.getValue())) {
                    this.setDisable(true);
                }
            }
        });
        tglAkhirPicker.setConverter(Function.getTglConverter());
        tglAkhirPicker.setValue(LocalDate.parse(Main.sistem.getTglSystem(), DateTimeFormatter.ISO_DATE));
        tglAkhirPicker.setDayCellFactory((final DatePicker datePicker) -> new DateCell() {
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
                if (item.isBefore(tglStartPicker.getValue())) {
                    this.setDisable(true);
                }
            }
        });
        final ContextMenu rowMenu = new ContextMenu();
        MenuItem addNew = new MenuItem("Pelunasan Gadai");
        addNew.setOnAction((ActionEvent e) -> {
            pelunasanGadai();
        });
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent e) -> {
            getGadai();
        });
        rowMenu.getItems().addAll(addNew, refresh);
        gadaiTable.setContextMenu(rowMenu);
        gadaiTable.setRowFactory(table -> {
            TableRow<GadaiHead> row = new TableRow<GadaiHead>() {
                @Override
                public void updateItem(GadaiHead item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(rowMenu);
                    } else {
                        final ContextMenu rowMenu = new ContextMenu();
                        MenuItem addNew = new MenuItem("Pelunasan Gadai");
                        addNew.setOnAction((ActionEvent e) -> {
                            pelunasanGadai();
                        });
                        MenuItem print = new MenuItem("Print Surat Pelunasan Gadai");
                        print.setOnAction((ActionEvent e) -> {
                            printPelunasanGadai(item);
                        });
                        MenuItem batal = new MenuItem("Batal Pelunasan Gadai");
                        batal.setOnAction((ActionEvent event) -> {
                            deletePelunasanGadai(item);
                        });
                        MenuItem view = new MenuItem("Lihat Detail Pelunasan Gadai");
                        view.setOnAction((ActionEvent event) -> {
                            viewPelunasanGadai(item);
                        });
                        MenuItem refresh = new MenuItem("Refresh");
                        refresh.setOnAction((ActionEvent e) -> {
                            getGadai();
                        });
                        rowMenu.getItems().addAll(addNew, view, batal, print, refresh);
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
        getGadai();
        gadaiTable.setItems(filterData);
    }

    public void getGadai() {
        try (Connection con = Koneksi.getConnection()) {
            allGadai.clear();
            List<GadaiHead> listGadaiHead = GadaiHeadDAO.getAllByTglLunasAndStatus(con,
                    tglStartPicker.getValue().toString(), tglAkhirPicker.getValue().toString(), "Lunas");
            List<GadaiDetail> listGadaiDetail = GadaiDetailDAO.getAllByTglLunasAndStatus(con,
                    tglStartPicker.getValue().toString(), tglAkhirPicker.getValue().toString(), "Lunas");
            for (GadaiHead h : listGadaiHead) {
                List<GadaiDetail> listDetail = new ArrayList<>();
                for (GadaiDetail d : listGadaiDetail) {
                    if (h.getNoGadai().equals(d.getNoGadai())) {
                        listDetail.add(d);
                    }
                }
                h.setAllDetail(listDetail);
            }
            allGadai.addAll(listGadaiHead);
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

    public void searchGadai() {
        try {
            filterData.clear();
            for (GadaiHead temp : allGadai) {
                if (searchField.getText() == null || searchField.getText().equals("")) {
                    filterData.add(temp);
                } else {
                    if (checkColumn(temp.getNoGadai())
                            || checkColumn(tglLengkap.format(tglSql.parse(temp.getTglLunas())))
                            || checkColumn(temp.getKodePelanggan())
                            || checkColumn(temp.getNama())
                            || checkColumn(temp.getAlamat())
                            || checkColumn(temp.getNoTelp())
                            || checkColumn(gr.format(temp.getTotalBerat()))
                            || checkColumn(rp.format(temp.getTotalPinjaman()))
                            || checkColumn(gr.format(temp.getLamaPinjam()))
                            || checkColumn(gr.format(temp.getBungaPersen()))
                            || checkColumn(rp.format(temp.getBungaRp()))
                            || checkColumn(temp.getKeterangan())
                            || checkColumn(temp.getKodeSales())) {
                        filterData.add(temp);
                    }
                }
            }
            hitungTotal();
        } catch (Exception e) {
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }

    public void hitungTotal() {
        double total = 0;
        double bunga = 0;
        for (GadaiHead temp : filterData) {
            total = total + temp.getTotalPinjaman();
            bunga = bunga + temp.getBungaRp();
        }
        totalGadaiField.setText(rp.format(total));
        totalBungaField.setText(rp.format(bunga));
    }

    private void pelunasanGadai() {
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/DetailPelunasanGadai.fxml");
        DetailPelunasanGadaiController controller = loader.getController();
        controller.setMainApp(mainApp, mainApp.MainStage, stage);
    }

    private void viewPelunasanGadai(GadaiHead p) {
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/DetailPelunasanGadai.fxml");
        DetailPelunasanGadaiController controller = loader.getController();
        controller.setMainApp(mainApp, mainApp.MainStage, stage);
        controller.viewGadai(p);
    }

    private void printPelunasanGadai(GadaiHead p) {
        try {
            PrintOut printOut = new PrintOut();
            printOut.printBuktiPelunasanGadai(p.getAllDetail());
        } catch (Exception e) {
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }

    private void deletePelunasanGadai(GadaiHead p) {
        try {
            if (!p.getTglLunas().substring(0, 10).equals(sistem.getTglSystem())) {
                mainApp.showMessage(Modality.NONE, "Warning", "Tanggal pelunasan gadai sudah berbeda dengan tanggal sekarang");
            } else {
                Stage stage = new Stage();
                FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/Verifikasi.fxml");
                VerifikasiController controller = loader.getController();
                controller.setMainApp(mainApp, mainApp.MainStage, stage);
                controller.password.setOnKeyPressed((KeyEvent keyEvent) -> {
                    if (keyEvent.getCode() == KeyCode.ENTER) {
                        mainApp.closeDialog(mainApp.MainStage, stage);
                        if (Function.verifikasi(controller.username.getText(), controller.password.getText(), "Batal Pelunasan Gadai")) {
                            try (Connection con = Koneksi.getConnection()) {
                                String status = Service.batalPelunasanGadai(con, p, controller.username.getText());
                                if (status.equals("true")) {
                                    mainApp.showMessage(Modality.NONE, "Success", "Pelunasan gadai berhasil dibatal");
                                    getGadai();
                                } else {
                                    mainApp.showMessage(Modality.NONE, "Error", status);
                                }
                            } catch (Exception ex) {
                                mainApp.showMessage(Modality.NONE, "Error", ex.toString());
                            }
                        } else {
                            mainApp.showMessage(Modality.NONE, "Warning", "Verifikasi Salah");
                        }
                    }
                });
            }
        } catch (Exception ex) {
            mainApp.showMessage(Modality.NONE, "Error", ex.toString());
        }
    }

}
