/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasGunungMas.View;

import com.excellentsystem.TokoEmasGunungMas.DAO.PembelianDetailDAO;
import com.excellentsystem.TokoEmasGunungMas.DAO.PembelianHeadDAO;
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
import com.excellentsystem.TokoEmasGunungMas.Model.PembelianDetail;
import com.excellentsystem.TokoEmasGunungMas.Model.PembelianHead;
import com.excellentsystem.TokoEmasGunungMas.PrintOut.PrintOut;
import com.excellentsystem.TokoEmasGunungMas.Service;
import com.excellentsystem.TokoEmasGunungMas.View.Dialog.DetailPembelianController;
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
public class PembelianController {

    @FXML
    private TableView<PembelianHead> pembelianTable;
    @FXML
    private TableColumn<PembelianHead, String> noPembelianColumn;
    @FXML
    private TableColumn<PembelianHead, String> tglPembelianColumn;
    @FXML
    private TableColumn<PembelianHead, String> kodePelangganColumn;
    @FXML
    private TableColumn<PembelianHead, String> namaPelangganColumn;
    @FXML
    private TableColumn<PembelianHead, String> alamatPelangganColumn;
    @FXML
    private TableColumn<PembelianHead, String> noTelpPelangganColumn;
    @FXML
    private TableColumn<PembelianHead, Number> totalBeratKotorColumn;
    @FXML
    private TableColumn<PembelianHead, Number> totalBeratBersihColumn;
    @FXML
    private TableColumn<PembelianHead, Number> totalPembelianColumn;
    @FXML
    private TableColumn<PembelianHead, String> catatanColumn;
    @FXML
    private TableColumn<PembelianHead, String> SalesColumn;
    @FXML
    private TableColumn<PembelianHead, String> kodeUserColumn;

    @FXML
    private TextField searchField;
    @FXML
    private DatePicker tglStartPicker;
    @FXML
    private DatePicker tglAkhirPicker;
    @FXML
    private Label totalPembelianField;
    private ObservableList<PembelianHead> allPembelian = FXCollections.observableArrayList();
    private ObservableList<PembelianHead> filterData = FXCollections.observableArrayList();
    private Main mainApp;

    public void initialize() {
        noPembelianColumn.setCellValueFactory(cellData -> cellData.getValue().noPembelianProperty());
        kodePelangganColumn.setCellValueFactory(cellData -> cellData.getValue().kodePelangganProperty());
        namaPelangganColumn.setCellValueFactory(cellData -> cellData.getValue().namaProperty());
        alamatPelangganColumn.setCellValueFactory(cellData -> cellData.getValue().alamatProperty());
        noTelpPelangganColumn.setCellValueFactory(cellData -> cellData.getValue().noTelpProperty());
        catatanColumn.setCellValueFactory(cellData -> cellData.getValue().catatanProperty());
        SalesColumn.setCellValueFactory(cellData -> cellData.getValue().kodeSalesProperty());
        kodeUserColumn.setCellValueFactory(cellData -> cellData.getValue().kodeUserProperty());
        tglPembelianColumn.setCellValueFactory(cellData -> {
            try {
                return new SimpleStringProperty(tglLengkap.format(
                        tglSql.parse(cellData.getValue().getTglPembelian())));
            } catch (ParseException ex) {
                return null;
            }
        });
        totalBeratKotorColumn.setCellValueFactory(celldata -> celldata.getValue().totalberatKotorProperty());
        totalBeratKotorColumn.setCellFactory(col -> getTableCell(gr));
        totalBeratBersihColumn.setCellValueFactory(celldata -> celldata.getValue().totalBeratBersihProperty());
        totalBeratBersihColumn.setCellFactory(col -> getTableCell(gr));
        totalPembelianColumn.setCellValueFactory(celldata -> celldata.getValue().totalPembelianProperty());
        totalPembelianColumn.setCellFactory(col -> getTableCell(rp));
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
        MenuItem addNew = new MenuItem("Pembelian Baru");
        addNew.setOnAction((ActionEvent e) -> {
            newPembelian();
        });
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent e) -> {
            getPembelian();
        });
        rowMenu.getItems().addAll(addNew, refresh);
        pembelianTable.setContextMenu(rowMenu);
        pembelianTable.setRowFactory(table -> {
            TableRow<PembelianHead> row = new TableRow<PembelianHead>() {
                @Override
                public void updateItem(PembelianHead item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(rowMenu);
                    } else {
                        final ContextMenu rowMenu = new ContextMenu();
                        MenuItem addNew = new MenuItem("Pembelian Baru");
                        addNew.setOnAction((ActionEvent e) -> {
                            newPembelian();
                        });
                        MenuItem print = new MenuItem("Print Bukti Pembelian");
                        print.setOnAction((ActionEvent e) -> {
                            printPembelian(item);
                        });
                        MenuItem batal = new MenuItem("Batal Pembelian");
                        batal.setOnAction((ActionEvent event) -> {
                            deletePembelian(item);
                        });
                        MenuItem view = new MenuItem("Lihat Detail Pembelian");
                        view.setOnAction((ActionEvent event) -> {
                            viewPembelian(item);
                        });
                        MenuItem printBarang = new MenuItem("Print Barang Dibeli");
                        printBarang.setOnAction((ActionEvent e) -> {
                            printPembelian();
                        });
                        MenuItem refresh = new MenuItem("Refresh");
                        refresh.setOnAction((ActionEvent e) -> {
                            getPembelian();
                        });
                        rowMenu.getItems().addAll(addNew, view, batal, print,  printBarang, refresh);
                        setContextMenu(rowMenu);
                    }
                }
            };
            return row;
        });
        allPembelian.addListener((ListChangeListener.Change<? extends PembelianHead> change) -> {
            searchPembelian();
        });
        searchField.textProperty().addListener(
                (ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
                    searchPembelian();
                });
        filterData.addAll(allPembelian);
    }

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
        getPembelian();
        pembelianTable.setItems(filterData);
    }

    public void getPembelian() {
        try (Connection con = Koneksi.getConnection()) {
            allPembelian.clear();
            List<PembelianHead> listPembelianHead = PembelianHeadDAO.getAllByTglBeliAndStatus(con, 
                    tglStartPicker.getValue().toString(), tglAkhirPicker.getValue().toString(), "true");
            List<PembelianDetail> listPembelianDetail = PembelianDetailDAO.getAllByTglBeliAndStatus(con, 
                    tglStartPicker.getValue().toString(), tglAkhirPicker.getValue().toString(), "true");
            for (PembelianHead h : listPembelianHead) {
                List<PembelianDetail> listDetail = new ArrayList<>();
                for (PembelianDetail d : listPembelianDetail) {
                    if (h.getNoPembelian().equals(d.getNoPembelian())) {
                        listDetail.add(d);
                    }
                }
                h.setAllDetail(listDetail);
            }
            allPembelian.addAll(listPembelianHead);
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

    public void searchPembelian() {
        try {
            filterData.clear();
            for (PembelianHead temp : allPembelian) {
                if (searchField.getText() == null || searchField.getText().equals("")) {
                    filterData.add(temp);
                } else {
                    if (checkColumn(temp.getNoPembelian())
                            || checkColumn(tglLengkap.format(tglSql.parse(temp.getTglPembelian())))
                            || checkColumn(temp.getKodePelanggan())
                            || checkColumn(temp.getNama())
                            || checkColumn(temp.getAlamat())
                            || checkColumn(temp.getNoTelp())
                            || checkColumn(gr.format(temp.getTotalberatKotor()))
                            || checkColumn(gr.format(temp.getTotalBeratBersih()))
                            || checkColumn(rp.format(temp.getTotalPembelian()))
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
        for (PembelianHead temp : filterData) {
            total = total + temp.getTotalPembelian();
        }
        totalPembelianField.setText(rp.format(total));
    }

    @FXML
    private void newPembelian() {
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/DetailPembelian.fxml");
        DetailPembelianController controller = loader.getController();
        controller.setMainApp(mainApp, mainApp.MainStage, stage);
    }

    private void viewPembelian(PembelianHead p) {
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/DetailPembelian.fxml");
        DetailPembelianController controller = loader.getController();
        controller.setMainApp(mainApp, mainApp.MainStage, stage);
        controller.setPembelian(p);
    }

    private void printPembelian(PembelianHead p) {
        try {
            for (PembelianDetail d : p.getAllDetail()) {
                d.setPembelian(p);
            }
            PrintOut printOut = new PrintOut();
            printOut.printBuktiPembelian(p.getAllDetail());
        } catch (Exception e) {
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }

    private void deletePembelian(PembelianHead p) {
        try {
            if (!p.getNoPembelian().substring(3, 9).equals(tglSystem.format(tglBarang.parse(sistem.getTglSystem())))) {
                mainApp.showMessage(Modality.NONE, "Warning", "Pembelian sudah berbeda tanggal");
            } else {
                Stage stage = new Stage();
                FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/Verifikasi.fxml");
                VerifikasiController controller = loader.getController();
                controller.setMainApp(mainApp, mainApp.MainStage, stage);
                controller.password.setOnKeyPressed((KeyEvent keyEvent) -> {
                    if (keyEvent.getCode() == KeyCode.ENTER) {
                        mainApp.closeDialog(mainApp.MainStage, stage);
                        if (Function.verifikasi(controller.username.getText(), controller.password.getText(), "Batal Pembelian")) {
                            try (Connection con = Koneksi.getConnection()){
                                p.setTglBatal(tglSql.format(new Date()));
                                p.setUserBatal(controller.username.getText());
                                p.setStatus("false");
                                String status = Service.batalPembelian(con,p);
                                if (status.equals("true")) {
                                    mainApp.showMessage(Modality.NONE, "Success", "Data pembelian berhasil dibatal");
                                    getPembelian();
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

    private void printPembelian() {
        try {
            PrintOut printOut = new PrintOut();
            printOut.printPembelian(tglStartPicker.getValue().toString(), tglAkhirPicker.getValue().toString());
        } catch (Exception e) {
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }
}
