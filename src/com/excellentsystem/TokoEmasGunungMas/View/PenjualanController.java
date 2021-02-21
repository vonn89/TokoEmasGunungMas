/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasGunungMas.View;

import com.excellentsystem.TokoEmasGunungMas.DAO.BarangDAO;
import com.excellentsystem.TokoEmasGunungMas.DAO.PenjualanDetailDAO;
import com.excellentsystem.TokoEmasGunungMas.DAO.PenjualanHeadDAO;
import com.excellentsystem.TokoEmasGunungMas.Function;
import static com.excellentsystem.TokoEmasGunungMas.Function.getTableCell;
import com.excellentsystem.TokoEmasGunungMas.Koneksi;
import com.excellentsystem.TokoEmasGunungMas.Main;
import static com.excellentsystem.TokoEmasGunungMas.Main.gr;
import static com.excellentsystem.TokoEmasGunungMas.Main.rp;
import static com.excellentsystem.TokoEmasGunungMas.Main.sistem;
import static com.excellentsystem.TokoEmasGunungMas.Main.tglBarang;
import static com.excellentsystem.TokoEmasGunungMas.Main.tglLengkap;
import static com.excellentsystem.TokoEmasGunungMas.Main.tglSql;
import static com.excellentsystem.TokoEmasGunungMas.Main.tglSystem;
import com.excellentsystem.TokoEmasGunungMas.Model.PenjualanDetail;
import com.excellentsystem.TokoEmasGunungMas.Model.PenjualanHead;
import com.excellentsystem.TokoEmasGunungMas.PrintOut.PrintOut;
import com.excellentsystem.TokoEmasGunungMas.Service;
import com.excellentsystem.TokoEmasGunungMas.View.Dialog.DetailPenjualanController;
import com.excellentsystem.TokoEmasGunungMas.View.Dialog.VerifikasiController;
import java.sql.Connection;
import java.text.ParseException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
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
public class PenjualanController {

    @FXML
    private TableView<PenjualanHead> penjualanTable;
    @FXML
    private TableColumn<PenjualanHead, String> noPenjualanColumn;
    @FXML
    private TableColumn<PenjualanHead, String> tglPenjualanColumn;
    @FXML
    private TableColumn<PenjualanHead, String> kodePelangganColumn;
    @FXML
    private TableColumn<PenjualanHead, String> namaPelangganColumn;
    @FXML
    private TableColumn<PenjualanHead, String> alamatPelangganColumn;
    @FXML
    private TableColumn<PenjualanHead, String> noTelpPelangganColumn;
    @FXML
    private TableColumn<PenjualanHead, Number> totalBeratColumn;
    @FXML
    private TableColumn<PenjualanHead, Number> grandtotalColumn;
    @FXML
    private TableColumn<PenjualanHead, String> catatanColumn;
    @FXML
    private TableColumn<PenjualanHead, String> SalesColumn;
    @FXML
    private TableColumn<PenjualanHead, String> kodeUserColumn;

    @FXML
    private TextField searchField;
    @FXML
    private DatePicker tglStartPicker;
    @FXML
    private DatePicker tglAkhirPicker;
    @FXML
    private Label totalPenjualanField;
    private ObservableList<PenjualanHead> allPenjualan = FXCollections.observableArrayList();
    private ObservableList<PenjualanHead> filterData = FXCollections.observableArrayList();
    private Main mainApp;

    public void initialize() {
        noPenjualanColumn.setCellValueFactory(cellData -> cellData.getValue().noPenjualanProperty());
        kodePelangganColumn.setCellValueFactory(cellData -> cellData.getValue().kodePelangganProperty());
        namaPelangganColumn.setCellValueFactory(cellData -> cellData.getValue().namaProperty());
        alamatPelangganColumn.setCellValueFactory(cellData -> cellData.getValue().alamatProperty());
        noTelpPelangganColumn.setCellValueFactory(cellData -> cellData.getValue().noTelpProperty());
        catatanColumn.setCellValueFactory(cellData -> cellData.getValue().catatanProperty());
        SalesColumn.setCellValueFactory(cellData -> cellData.getValue().kodeSalesProperty());
        kodeUserColumn.setCellValueFactory(cellData -> cellData.getValue().kodeUserProperty());
        tglPenjualanColumn.setCellValueFactory(cellData -> {
            try {
                return new SimpleStringProperty(tglLengkap.format(
                        tglSql.parse(cellData.getValue().getTglPenjualan())));
            } catch (ParseException ex) {
                return null;
            }
        });
        totalBeratColumn.setCellValueFactory(celldata -> celldata.getValue().totalBeratProperty());
        totalBeratColumn.setCellFactory(col -> getTableCell(gr));
        grandtotalColumn.setCellValueFactory(celldata -> celldata.getValue().grandtotalProperty());
        grandtotalColumn.setCellFactory(col -> getTableCell(rp));

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
        MenuItem addNew = new MenuItem("Penjualan Baru");
        addNew.setOnAction((ActionEvent e) -> {
            newPenjualan();
        });
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent e) -> {
            getPenjualan();
        });
        rowMenu.getItems().addAll(addNew, refresh);
        penjualanTable.setContextMenu(rowMenu);
        penjualanTable.setRowFactory(table -> {
            TableRow<PenjualanHead> row = new TableRow<PenjualanHead>() {
                @Override
                public void updateItem(PenjualanHead item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(rowMenu);
                    } else {
                        final ContextMenu rowMenu = new ContextMenu();
                        MenuItem addNew = new MenuItem("Penjualan Baru");
                        addNew.setOnAction((ActionEvent e) -> {
                            newPenjualan();
                        });
                        MenuItem view = new MenuItem("Lihat Detail Penjualan");
                        view.setOnAction((ActionEvent event) -> {
                            viewPenjualan(item);
                        });
                        MenuItem batal = new MenuItem("Batal Penjualan");
                        batal.setOnAction((ActionEvent event) -> {
                            deletePenjualan(item);
                        });
                        MenuItem print = new MenuItem("Print Faktur Penjualan");
                        print.setOnAction((ActionEvent e) -> {
                            printPenjualan(item);
                        });
                        MenuItem refresh = new MenuItem("Refresh");
                        refresh.setOnAction((ActionEvent e) -> {
                            getPenjualan();
                        });
                        rowMenu.getItems().addAll(addNew,  view, batal, print,refresh);
                        setContextMenu(rowMenu);
                    }
                }
            };
            return row;
        });
        allPenjualan.addListener((ListChangeListener.Change<? extends PenjualanHead> change) -> {
            searchPenjualan();
        });
        searchField.textProperty().addListener(
                (ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
                    searchPenjualan();
                });
        filterData.addAll(allPenjualan);
    }

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
        getPenjualan();
        penjualanTable.setItems(filterData);
    }

    public void getPenjualan() {
        try (Connection con = Koneksi.getConnection()) {
            allPenjualan.clear();
            List<PenjualanHead> listPenjualanHead = PenjualanHeadDAO.getAllByTglPenjualanAndStatus(con,
                    tglStartPicker.getValue().toString(), tglAkhirPicker.getValue().toString(), "true");
            List<PenjualanDetail> listPenjualanDetail = PenjualanDetailDAO.getAllByTglPenjualanAndStatus(con,
                    tglStartPicker.getValue().toString(), tglAkhirPicker.getValue().toString(), "true");
            for (PenjualanHead h : listPenjualanHead) {
                List<PenjualanDetail> listDetail = new ArrayList<>();
                for (PenjualanDetail d : listPenjualanDetail) {
                    if (h.getNoPenjualan().equals(d.getNoPenjualan())) {
                        listDetail.add(d);
                    }
                }
                h.setAllDetail(listDetail);
            }
            allPenjualan.addAll(listPenjualanHead);
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

    public void searchPenjualan() {
        try {
            filterData.clear();
            for (PenjualanHead temp : allPenjualan) {
                if (searchField.getText() == null || searchField.getText().equals("")) {
                    filterData.add(temp);
                } else {
                    if (checkColumn(temp.getNoPenjualan())
                            || checkColumn(tglLengkap.format(tglSql.parse(temp.getTglPenjualan())))
                            || checkColumn(temp.getKodePelanggan())
                            || checkColumn(temp.getNama())
                            || checkColumn(temp.getAlamat())
                            || checkColumn(temp.getNoTelp())
                            || checkColumn(gr.format(temp.getTotalBerat()))
                            || checkColumn(rp.format(temp.getGrandtotal()))
                            || checkColumn(temp.getCatatan())
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
        for (PenjualanHead temp : filterData) {
            total = total + temp.getGrandtotal();
        }
        totalPenjualanField.setText(rp.format(total));
    }

    @FXML
    private void newPenjualan() {
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/DetailPenjualan.fxml");
        DetailPenjualanController controller = loader.getController();
        controller.setMainApp(mainApp, mainApp.MainStage, stage);
    }

    private void viewPenjualan(PenjualanHead p) {
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/DetailPenjualan.fxml");
        DetailPenjualanController controller = loader.getController();
        controller.setMainApp(mainApp, mainApp.MainStage, stage);
        controller.setPenjualan(p);
    }

    private void printPenjualan(PenjualanHead p) {
        try (Connection con = Koneksi.getConnection()) {
            for (PenjualanDetail d : p.getAllDetail()) {
                d.setBarang(BarangDAO.get(con, d.getKodeBarcode()));
            }
            PrintOut printout = new PrintOut();
            printout.printSuratPenjualan(p);
        } catch (Exception e) {
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }

    private void deletePenjualan(PenjualanHead p) {
        try {
            if (!p.getNoPenjualan().substring(3, 9).equals(tglSystem.format(tglBarang.parse(sistem.getTglSystem())))) {
                mainApp.showMessage(Modality.NONE, "Warning", "Penjualan sudah berbeda tanggal");
            } else {
                Stage stage = new Stage();
                FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/Verifikasi.fxml");
                VerifikasiController controller = loader.getController();
                controller.setMainApp(mainApp, mainApp.MainStage, stage);
                controller.password.setOnKeyPressed((KeyEvent keyEvent) -> {
                    if (keyEvent.getCode() == KeyCode.ENTER) {
                        mainApp.closeDialog(mainApp.MainStage, stage);
                        if (Function.verifikasi(controller.username.getText(), controller.password.getText(), "Batal Penjualan")) {
                            try (Connection con = Koneksi.getConnection()) {
                                p.setTglBatal(tglSql.format(new Date()));
                                p.setUserBatal(controller.username.getText());
                                p.setStatus("false");
                                String status = Service.batalPenjualan(con, p);
                                if (status.equals("true")) {
                                    mainApp.showMessage(Modality.NONE, "Success", "Data penjualan berhasil dibatal");
                                    getPenjualan();
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
