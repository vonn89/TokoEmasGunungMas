/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasGunungMas.View;

import com.excellentsystem.TokoEmasGunungMas.DAO.BarangDAO;
import com.excellentsystem.TokoEmasGunungMas.DAO.GudangDAO;
import com.excellentsystem.TokoEmasGunungMas.DAO.JenisDAO;
import com.excellentsystem.TokoEmasGunungMas.DAO.KategoriDAO;
import com.excellentsystem.TokoEmasGunungMas.Function;
import static com.excellentsystem.TokoEmasGunungMas.Function.getTableCell;
import com.excellentsystem.TokoEmasGunungMas.Koneksi;
import com.excellentsystem.TokoEmasGunungMas.Main;
import static com.excellentsystem.TokoEmasGunungMas.Main.gr;
import static com.excellentsystem.TokoEmasGunungMas.Main.rp;
import static com.excellentsystem.TokoEmasGunungMas.Main.tglLengkap;
import static com.excellentsystem.TokoEmasGunungMas.Main.tglSql;
import static com.excellentsystem.TokoEmasGunungMas.Main.user;
import com.excellentsystem.TokoEmasGunungMas.Model.Barang;
import com.excellentsystem.TokoEmasGunungMas.Model.CetakBarcodeDetail;
import com.excellentsystem.TokoEmasGunungMas.Model.CetakBarcodeHead;
import com.excellentsystem.TokoEmasGunungMas.Model.Gudang;
import com.excellentsystem.TokoEmasGunungMas.Model.HancurDetail;
import com.excellentsystem.TokoEmasGunungMas.Model.HancurHead;
import com.excellentsystem.TokoEmasGunungMas.Model.Jenis;
import com.excellentsystem.TokoEmasGunungMas.Model.Kategori;
import com.excellentsystem.TokoEmasGunungMas.Model.PindahDetail;
import com.excellentsystem.TokoEmasGunungMas.Model.PindahHead;
import com.excellentsystem.TokoEmasGunungMas.PrintOut.PrintOut;
import com.excellentsystem.TokoEmasGunungMas.Service;
import com.excellentsystem.TokoEmasGunungMas.View.Dialog.CetakBarcodeController;
import com.excellentsystem.TokoEmasGunungMas.View.Dialog.DetailBarangController;
import com.excellentsystem.TokoEmasGunungMas.View.Dialog.HancurBarangController;
import com.excellentsystem.TokoEmasGunungMas.View.Dialog.PindahGudangController;
import com.excellentsystem.TokoEmasGunungMas.View.Dialog.VerifikasiController;
import java.sql.Connection;
import java.text.ParseException;
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
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Xtreme
 */
public class DataBarangBarcodeController {

    @FXML
    private TableView<Barang> barangTable;
    @FXML
    private TableColumn<Barang, Boolean> checkColumn;
    @FXML
    private TableColumn<Barang, String> kodeBarcodeColumn;
    @FXML
    private TableColumn<Barang, String> namaBarangColumn;
    @FXML
    private TableColumn<Barang, String> keteranganColumn;
    @FXML
    private TableColumn<Barang, String> kodeKategoriColumn;
    @FXML
    private TableColumn<Barang, String> kodeJenisColumn;
    @FXML
    private TableColumn<Barang, String> kodeGudangColumn;
    @FXML
    private TableColumn<Barang, String> kodeInternColumn;
    @FXML
    private TableColumn<Barang, String> kadarColumn;
    @FXML
    private TableColumn<Barang, Number> beratColumn;
    @FXML
    private TableColumn<Barang, Number> beratAsliColumn;
    @FXML
    private TableColumn<Barang, Number> beratKemasanColumn;
    @FXML
    private TableColumn<Barang, Number> nilaiPokokColumn;
    @FXML
    private TableColumn<Barang, Number> hargaJualColumn;
    @FXML
    private TableColumn<Barang, String> statusBarangColumn;
    @FXML
    private TableColumn<Barang, String> tglBarcodeColumn;
    @FXML
    private TableColumn<Barang, String> userBarcodeColumn;
    @FXML
    private TableColumn<Barang, String> tglHancurColumn;
    @FXML
    private TableColumn<Barang, String> userHancurColumn;
    @FXML
    private TableColumn<Barang, String> tglJualColumn;
    @FXML
    private TableColumn<Barang, String> userJualColumn;

    @FXML
    private CheckBox checkAll;
    @FXML
    private TextField searchField;
    @FXML
    private ComboBox<String> kodeKategoriCombo;
    @FXML
    private ComboBox<String> kodeJenisCombo;
    @FXML
    private ComboBox<String> kodeGudangCombo;
    @FXML
    private ComboBox<String> statusCombo;
    @FXML
    private Label totalQtyLabel;
    @FXML
    private Label totalBeratLabel;
    @FXML
    private Label totalBeratAsliLabel;
    @FXML
    private Label totalBeratLabelLabel;
    @FXML
    private Label totalNilaiPokokLabel;
    @FXML
    private Label totalHargaJualLabel;
    private Main mainApp;
    private final ObservableList<String> allKategori = FXCollections.observableArrayList();
    private final ObservableList<String> allJenis = FXCollections.observableArrayList();
    private final ObservableList<String> allGudang = FXCollections.observableArrayList();
    private final ObservableList<String> allStatus = FXCollections.observableArrayList();
    private final ObservableList<Barang> allBarang = FXCollections.observableArrayList();
    private final ObservableList<Barang> filterData = FXCollections.observableArrayList();

    public void initialize() {
        kodeBarcodeColumn.setCellValueFactory(cellData -> cellData.getValue().kodeBarcodeProperty());
        namaBarangColumn.setCellValueFactory(cellData -> cellData.getValue().namaBarangProperty());
        keteranganColumn.setCellValueFactory(cellData -> cellData.getValue().keteranganProperty());
        kodeKategoriColumn.setCellValueFactory(cellData -> cellData.getValue().kodeKategoriProperty());
        kodeJenisColumn.setCellValueFactory(cellData -> cellData.getValue().kodeJenisProperty());
        kodeGudangColumn.setCellValueFactory(cellData -> cellData.getValue().kodeGudangProperty());
        kodeInternColumn.setCellValueFactory(cellData -> cellData.getValue().kodeInternProperty());
        kadarColumn.setCellValueFactory(cellData -> cellData.getValue().kadarProperty());
        beratColumn.setCellValueFactory(cellData -> cellData.getValue().beratProperty());
        beratColumn.setCellFactory(col -> getTableCell(gr));
        beratAsliColumn.setCellValueFactory(cellData -> cellData.getValue().beratAsliProperty());
        beratAsliColumn.setCellFactory(col -> getTableCell(gr));
        beratKemasanColumn.setCellValueFactory(cellData -> cellData.getValue().beratKemasanProperty());
        beratKemasanColumn.setCellFactory(col -> getTableCell(gr));
        nilaiPokokColumn.setCellValueFactory(cellData -> cellData.getValue().nilaiPokokProperty());
        nilaiPokokColumn.setCellFactory(col -> getTableCell(rp));
        hargaJualColumn.setCellValueFactory(cellData -> cellData.getValue().hargaJualProperty());
        hargaJualColumn.setCellFactory(col -> getTableCell(rp));
        statusBarangColumn.setCellValueFactory(cellData -> cellData.getValue().statusBarangProperty());
        tglBarcodeColumn.setCellValueFactory(cellData -> {
            try {
                return new SimpleStringProperty(tglLengkap.format(
                        tglSql.parse(cellData.getValue().getBarcodeDate())));
            } catch (ParseException ex) {
                return null;
            }
        });
        userBarcodeColumn.setCellValueFactory(cellData -> cellData.getValue().barcodeByProperty());
        tglHancurColumn.setCellValueFactory(cellData -> {
            try {
                return new SimpleStringProperty(tglLengkap.format(
                        tglSql.parse(cellData.getValue().getDeletedDate())));
            } catch (ParseException ex) {
                return null;
            }
        });
        userHancurColumn.setCellValueFactory(cellData -> cellData.getValue().deletedByProperty());
        tglJualColumn.setCellValueFactory(cellData -> {
            try {
                return new SimpleStringProperty(tglLengkap.format(
                        tglSql.parse(cellData.getValue().getSoldDate())));
            } catch (ParseException ex) {
                return null;
            }
        });
        userJualColumn.setCellValueFactory(cellData -> cellData.getValue().soldByProperty());

        checkColumn.setCellValueFactory(cellData -> cellData.getValue().statusProperty());
        checkColumn.setCellFactory(CheckBoxTableCell.forTableColumn((Integer v) -> {
            return barangTable.getItems().get(v).statusProperty();
        }));
        final ContextMenu rowMenu = new ContextMenu();
        MenuItem cetak = new MenuItem("Cetak Barcode");
        cetak.setOnAction((ActionEvent e) -> {
            cetakBarcode();
        });
        MenuItem pindah = new MenuItem("Pindah Gudang");
        pindah.setOnAction((ActionEvent event) -> {
            pindahBarang();
        });
        MenuItem hancur = new MenuItem("Hancur Barang");
        hancur.setOnAction((ActionEvent event) -> {
            hancurBarang();
        });
        MenuItem laporan = new MenuItem("Print Laporan");
        laporan.setOnAction((ActionEvent e) -> {
            printLaporan();
        });
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent event) -> {
            getBarang();
        });
        rowMenu.getItems().addAll(cetak, pindah, hancur, laporan, refresh);
        barangTable.setContextMenu(rowMenu);
        barangTable.setRowFactory(table -> {
            TableRow<Barang> row = new TableRow<Barang>() {
                @Override
                public void updateItem(Barang item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(rowMenu);
                    } else {
                        final ContextMenu rowMenu = new ContextMenu();
                        MenuItem edit = new MenuItem("Edit Barang");
                        edit.setOnAction((ActionEvent e) -> {
                            editBarang(item);
                        });
                        MenuItem cetak = new MenuItem("Cetak Barcode");
                        cetak.setOnAction((ActionEvent e) -> {
                            cetakBarcode();
                        });
                        MenuItem pindah = new MenuItem("Pindah Gudang");
                        pindah.setOnAction((ActionEvent event) -> {
                            pindahBarang();
                        });
                        MenuItem hancur = new MenuItem("Hancur Barang");
                        hancur.setOnAction((ActionEvent event) -> {
                            hancurBarang();
                        });
                        MenuItem laporan = new MenuItem("Print Laporan");
                        laporan.setOnAction((ActionEvent e) -> {
                            printLaporan();
                        });
                        MenuItem refresh = new MenuItem("Refresh");
                        refresh.setOnAction((ActionEvent e) -> {
                            getBarang();
                        });
                        rowMenu.getItems().addAll(edit, cetak, pindah, hancur, laporan, refresh);
                        setContextMenu(rowMenu);
                    }
                }
            };
            row.setOnMouseClicked((MouseEvent mouseEvent) -> {
                if (mouseEvent.getButton().equals(MouseButton.PRIMARY)
                        && mouseEvent.getClickCount() == 2) {
                    if (row.getItem() != null) {
                        if (row.getItem().isStatus()) {
                            row.getItem().setStatus(false);
                        } else {
                            row.getItem().setStatus(true);
                        }
                    }
                }
            });
            return row;
        });
        allBarang.addListener((ListChangeListener.Change<? extends Barang> change) -> {
            searchBarang();
        });
        searchField.textProperty().addListener(
                (ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
                    searchBarang();
                });
        filterData.addAll(allBarang);
    }

    public void setMainApp(Main mainApp) {
        try (Connection con = Koneksi.getConnection()) {
            this.mainApp = mainApp;

            allKategori.add("Semua");
            List<Kategori> kategori = KategoriDAO.getAll(con);
            for (Kategori k : kategori) {
                allKategori.add(k.getKodeKategori());
            }
            kodeKategoriCombo.setItems(allKategori);
            kodeKategoriCombo.getSelectionModel().select("Semua");

            allJenis.add("Semua");
            List<Jenis> jenis = JenisDAO.getAll(con);
            for (Jenis j : jenis) {
                allJenis.add(j.getKodeJenis());
            }
            kodeJenisCombo.setItems(allJenis);
            kodeJenisCombo.getSelectionModel().select("Semua");

            allGudang.add("Semua");
            for (Gudang g : GudangDAO.getAll(con)) {
                allGudang.add(g.getKodeGudang());
            }
            kodeGudangCombo.setItems(allGudang);
            kodeGudangCombo.getSelectionModel().select("Semua");

            allStatus.add("Tersedia");
            allStatus.add("Terjual");
            allStatus.add("Hancur");
            statusCombo.setItems(allStatus);
            statusCombo.getSelectionModel().select("Tersedia");

            getBarang();
            barangTable.setItems(filterData);
        } catch (Exception e) {
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }

    @FXML
    private void getBarang() {
        try (Connection con = Koneksi.getConnection()) {
            allBarang.clear();
            List<Barang> x = BarangDAO.getAll(con,
                    statusCombo.getSelectionModel().getSelectedItem(),
                    "Semua", kodeGudangCombo.getSelectionModel().getSelectedItem(),
                    kodeKategoriCombo.getSelectionModel().getSelectedItem(),
                    kodeJenisCombo.getSelectionModel().getSelectedItem());
            for (Barang b : x) {
                b.setStatus(checkAll.isSelected());
            }
            allBarang.addAll(x);
        } catch (Exception e) {
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }

    @FXML
    private void checkAllHandle() {
        for (Barang b : allBarang) {
            b.setStatus(checkAll.isSelected());
        }
        barangTable.refresh();
    }

    private Boolean checkColumn(String column) {
        if (column != null) {
            if (column.toLowerCase().contains(searchField.getText().toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    private void searchBarang() {
        try {
            filterData.clear();
            for (Barang temp : allBarang) {
                if (searchField.getText() == null || searchField.getText().equals("")) {
                    filterData.add(temp);
                } else {
                    if (checkColumn(temp.getKodeKategori())
                            || checkColumn(temp.getKodeBarcode())
                            || checkColumn(temp.getNamaBarang())
                            || checkColumn(temp.getKeterangan())
                            || checkColumn(temp.getKodeJenis())
                            || checkColumn(temp.getKodeGudang())
                            || checkColumn(temp.getKodeIntern())
                            || checkColumn(temp.getKadar())
                            || checkColumn(temp.getStatusBarang())
                            || checkColumn(temp.getBarcodeBy())
                            || checkColumn(tglLengkap.format(tglSql.parse(temp.getBarcodeDate())))
                            || checkColumn(tglLengkap.format(tglSql.parse(temp.getDeletedDate())))
                            || checkColumn(temp.getDeletedBy())
                            || checkColumn(tglLengkap.format(tglSql.parse(temp.getSoldDate())))
                            || checkColumn(temp.getSoldBy())
                            || checkColumn(gr.format(temp.getBerat()))
                            || checkColumn(gr.format(temp.getBeratAsli()))
                            || checkColumn(gr.format(temp.getBeratKemasan()))
                            || checkColumn(gr.format(temp.getNilaiPokok()))
                            || checkColumn(gr.format(temp.getHargaJual()))) {
                        filterData.add(temp);
                    }
                }
            }
            for (Barang b : filterData) {
                b.statusProperty().addListener((obs, oldValue, newValue) -> {
                    hitungTotal();
                });
            }
            hitungTotal();
        } catch (Exception e) {
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }

    private void hitungTotal() {
        int totalQty = 0;
        double totalBerat = 0;
        double totalBeratAsli = 0;
        double totalBeratAll = 0;
        double totalNilai = 0;
        double totalHarga = 0;
        for (Barang b : filterData) {
            if (b.isStatus()) {
                totalQty = totalQty + 1;
                totalBerat = totalBerat + b.getBerat();
                totalBeratAsli = totalBeratAsli + b.getBeratAsli();
                totalBeratAll = totalBeratAll + b.getBeratAsli() + b.getBeratKemasan();
                totalNilai = totalNilai + b.getNilaiPokok();
                totalHarga = totalHarga + b.getHargaJual();
            }
        }
        totalQtyLabel.setText(gr.format(totalQty));
        totalBeratLabel.setText(gr.format(totalBerat));
        totalBeratAsliLabel.setText(gr.format(totalBeratAsli));
        totalBeratLabelLabel.setText(gr.format(totalBeratAll + (totalQty * Main.sistem.getBeratLabel())));
        totalNilaiPokokLabel.setText(rp.format(totalNilai));
        totalHargaJualLabel.setText(rp.format(totalHarga));
    }

    private void pindahBarang() {
        List<Barang> barang = new ArrayList<>();
        boolean tersedia = false;
        for (Barang b : filterData) {
            if (!"Tersedia".equals(b.getStatusBarang())) {
                tersedia = true;
            }
            if (b.isStatus()) {
                barang.add(b);
            }
        }
        if (barang.isEmpty()) {
            mainApp.showMessage(Modality.NONE, "Warning", "Barang belum dipilih");
        } else if (tersedia) {
            mainApp.showMessage(Modality.NONE, "Warning", "Barang tidak tersedia");
        } else {
            Stage stage = new Stage();
            FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/PindahGudang.fxml");
            PindahGudangController x = loader.getController();
            x.setMainApp(mainApp, mainApp.MainStage, stage);
            x.setBarang(barang);
            x.saveButton.setOnAction((event) -> {
                if (x.kodeGudangCombo.getSelectionModel().getSelectedItem() == null) {
                    mainApp.showMessage(Modality.NONE, "Warning", "Gudang belum dipilih");
                } else {
                    try (Connection con = Koneksi.getConnection()) {
                        PindahHead head = new PindahHead();
                        head.setTglPindah(tglSql.format(new Date()));
                        head.setGudangTujuan(x.kodeGudangCombo.getSelectionModel().getSelectedItem());
                        head.setTotalQty(Integer.parseInt(x.totalQty.getText().replaceAll(",", "")));
                        head.setTotalBerat(Double.parseDouble(x.totalBerat.getText().replaceAll(",", "")));
                        head.setTotalBeratAsli(Double.parseDouble(x.totalBeratAsli.getText().replaceAll(",", "")));
                        head.setKodeUser(user.getUsername());
                        List<PindahDetail> allDetail = new ArrayList<>();
                        for (Barang b : x.allBarang) {
                            PindahDetail detail = new PindahDetail();
                            detail.setKodeBarcode(b.getKodeBarcode());
                            detail.setGudangAsal(b.getKodeGudang());
                            allDetail.add(detail);
                        }
                        head.setAllDetail(allDetail);
                        String status = Service.savePindahGudang(con, head);
                        if (status.equals("true")) {
                            mainApp.closeDialog(mainApp.MainStage, stage);
                            mainApp.showMessage(Modality.NONE, "Success", "Pindah gudang berhasil disimpan");
                            getBarang();
                        } else {
                            mainApp.showMessage(Modality.NONE, "Error", status);
                        }
                    } catch (Exception e) {
                        mainApp.showMessage(Modality.NONE, "Error", e.toString());
                    }
                }
            });
        }
    }

    private void editBarang(Barang b) {
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/DetailBarang.fxml");
        DetailBarangController controller = loader.getController();
        controller.setMainApp(mainApp, mainApp.MainStage, stage);
        controller.setBarang(b);
        if (!"Tersedia".equals(b.getStatusBarang())) {
            controller.setViewOnly();
        }
        controller.saveButton.setOnAction((event) -> {
            Stage stageVerifikasi = new Stage();
            FXMLLoader loaderVerifikasi = mainApp.showDialog(stage, stageVerifikasi, "View/Dialog/Verifikasi.fxml");
            VerifikasiController v = loaderVerifikasi.getController();
            v.setMainApp(mainApp, stage, stageVerifikasi);
            v.password.setOnKeyPressed((KeyEvent) -> {
                if (KeyEvent.getCode() == KeyCode.ENTER) {
                    mainApp.closeDialog(stage, stageVerifikasi);
                    if (Function.verifikasi(v.username.getText(), v.password.getText(), "Edit Barang")) {
                        try (Connection con = Koneksi.getConnection()) {
                            b.setNamaBarang(controller.namaBarangField.getText());
                            b.setKeterangan(controller.keteranganField.getText());
                            b.setKodeIntern(controller.kodeInternField.getText());
                            b.setKadar(controller.kadarField.getText());
                            b.setBeratKemasan(Double.parseDouble(controller.beratKemasanField.getText().replaceAll(",", "")));
                            b.setNilaiPokok(Double.parseDouble(controller.nilaiPokokField.getText().replaceAll(",", "")));
                            b.setHargaJual(Double.parseDouble(controller.hargaJualField.getText().replaceAll(",", "")));
                            BarangDAO.update(con, b);
                            mainApp.closeDialog(mainApp.MainStage, stage);
                            mainApp.showMessage(Modality.NONE, "Success", "Barang berhasil diupdate");
                            getBarang();
                        } catch (Exception e) {
                            mainApp.showMessage(Modality.NONE, "Error", e.toString());
                        }
                    } else {
                        mainApp.showMessage(Modality.NONE, "Warning", "Verifikasi salah");
                    }
                }
            });
        });
    }

    private void hancurBarang() {
        List<Barang> barang = new ArrayList<>();
        boolean tersedia = false;
        for (Barang b : filterData) {
            if (!"Tersedia".equals(b.getStatusBarang())) {
                tersedia = true;
            }
            if (b.isStatus()) {
                barang.add(b);
            }
        }
        if (barang.isEmpty()) {
            mainApp.showMessage(Modality.NONE, "Warning", "Barang belum dipilih");
        } else if (tersedia) {
            mainApp.showMessage(Modality.NONE, "Warning", "Barang tidak tersedia");
        } else {
            Stage stage = new Stage();
            FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/HancurBarang.fxml");
            HancurBarangController controller = loader.getController();
            controller.setMainApp(mainApp, mainApp.MainStage, stage);
            controller.setBarang(barang);
            controller.saveButton.setOnAction((event) -> {
                Stage stageVerifikasi = new Stage();
                FXMLLoader loaderVerifikasi = mainApp.showDialog(stage, stageVerifikasi, "View/Dialog/Verifikasi.fxml");
                VerifikasiController v = loaderVerifikasi.getController();
                v.setMainApp(mainApp, stage, stageVerifikasi);
                v.password.setOnKeyPressed((KeyEvent) -> {
                    if (KeyEvent.getCode() == KeyCode.ENTER) {
                        mainApp.closeDialog(stage, stageVerifikasi);
                        if (Function.verifikasi(v.username.getText(), v.password.getText(), "Hancur Barang")) {
                            try (Connection con = Koneksi.getConnection()) {
                                HancurHead head = new HancurHead();
                                head.setTglHancur(tglSql.format(new Date()));
                                head.setTotalQty(Integer.parseInt(controller.totalQty.getText().replaceAll(",", "")));
                                head.setTotalBerat(Double.parseDouble(controller.totalBerat.getText().replaceAll(",", "")));
                                head.setTotalBeratAsli(Double.parseDouble(controller.totalBeratAsli.getText().replaceAll(",", "")));
                                head.setKodeUser(v.username.getText());
                                List<HancurDetail> allDetail = new ArrayList<>();
                                for (Barang b : controller.allBarang) {
                                    HancurDetail detail = new HancurDetail();
                                    detail.setKodeBarcode(b.getKodeBarcode());
                                    allDetail.add(detail);
                                }
                                head.setAllDetail(allDetail);
                                String status = Service.saveHancurBarang(con, head);
                                if (status.equals("true")) {
                                    mainApp.closeDialog(mainApp.MainStage, stage);
                                    mainApp.showMessage(Modality.NONE, "Success", "Hancur Barang berhasil disimpan");
                                    getBarang();
                                } else {
                                    mainApp.showMessage(Modality.NONE, "Error", status);
                                }
                            } catch (Exception e) {
                                mainApp.showMessage(Modality.NONE, "Error", e.toString());
                            }
                        } else {
                            mainApp.showMessage(Modality.NONE, "Warning", "Verifikasi salah");
                        }
                    }
                });
            });
        }
    }

    private void cetakBarcode() {
        List<Barang> barang = new ArrayList<>();
        boolean tersedia = false;
        for (Barang b : filterData) {
            if (!"Tersedia".equals(b.getStatusBarang())) {
                tersedia = true;
            }
            if (b.isStatus()) {
                barang.add(b);
            }
        }
        if (barang.isEmpty()) {
            mainApp.showMessage(Modality.NONE, "Warning", "Barang belum dipilih");
        } else if (tersedia) {
            mainApp.showMessage(Modality.NONE, "Waning", "Barang tidak tersedia");
        } else {
            Stage stage = new Stage();
            FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/CetakBarcode.fxml");
            CetakBarcodeController x = loader.getController();
            x.setMainApp(mainApp, mainApp.MainStage, stage);
            x.setBarang(barang);
            x.saveButton.setOnAction((event) -> {
                try (Connection con = Koneksi.getConnection()) {
                    CetakBarcodeHead head = new CetakBarcodeHead();
                    head.setTglCetak(tglSql.format(new Date()));
                    head.setTotalQty(Integer.parseInt(x.totalQty.getText().replaceAll(",", "")));
                    head.setTotalBerat(Double.parseDouble(x.totalBerat.getText().replaceAll(",", "")));
                    head.setTotalBeratAsli(Double.parseDouble(x.totalBeratAsli.getText().replaceAll(",", "")));
                    head.setKodeUser(user.getUsername());
                    List<CetakBarcodeDetail> allDetail = new ArrayList<>();
                    for (Barang b : x.allBarang) {
                        CetakBarcodeDetail detail = new CetakBarcodeDetail();
                        detail.setKodeBarcode(b.getKodeBarcode());
                        allDetail.add(detail);
                    }
                    head.setAllDetail(allDetail);
                    String status = Service.saveCetakBarcode(con, head);
                    if (status.equals("true")) {
                        PrintOut.printBarcode(x.allBarang);
                        mainApp.closeDialog(mainApp.MainStage, stage);
                    } else {
                        mainApp.showMessage(Modality.NONE, "Error", status);
                    }
                } catch (Exception e) {
                    mainApp.showMessage(Modality.NONE, "Error", e.toString());
                }
            });
        }
    }

    private void printLaporan() {
        try {
            PrintOut report = new PrintOut();
            report.printLaporanBarang(filterData, searchField.getText());
        } catch (Exception e) {
            e.printStackTrace();
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }
}
