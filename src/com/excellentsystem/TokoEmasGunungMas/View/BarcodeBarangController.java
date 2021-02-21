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
import com.excellentsystem.TokoEmasGunungMas.DAO.StokBarangDAO;
import com.excellentsystem.TokoEmasGunungMas.Function;
import static com.excellentsystem.TokoEmasGunungMas.Function.getTableCell;
import com.excellentsystem.TokoEmasGunungMas.Koneksi;
import com.excellentsystem.TokoEmasGunungMas.Main;
import static com.excellentsystem.TokoEmasGunungMas.Main.gr;
import static com.excellentsystem.TokoEmasGunungMas.Main.rp;
import static com.excellentsystem.TokoEmasGunungMas.Main.sistem;
import static com.excellentsystem.TokoEmasGunungMas.Main.tglLengkap;
import static com.excellentsystem.TokoEmasGunungMas.Main.tglSql;
import static com.excellentsystem.TokoEmasGunungMas.Main.user;
import com.excellentsystem.TokoEmasGunungMas.Model.Barang;
import com.excellentsystem.TokoEmasGunungMas.Model.CetakBarcodeDetail;
import com.excellentsystem.TokoEmasGunungMas.Model.CetakBarcodeHead;
import com.excellentsystem.TokoEmasGunungMas.Model.Gudang;
import com.excellentsystem.TokoEmasGunungMas.Model.Jenis;
import com.excellentsystem.TokoEmasGunungMas.Model.Kategori;
import com.excellentsystem.TokoEmasGunungMas.Model.PindahDetail;
import com.excellentsystem.TokoEmasGunungMas.Model.PindahHead;
import com.excellentsystem.TokoEmasGunungMas.Model.StokBarang;
import com.excellentsystem.TokoEmasGunungMas.PrintOut.PrintOut;
import com.excellentsystem.TokoEmasGunungMas.Service;
import com.excellentsystem.TokoEmasGunungMas.View.Dialog.CetakBarcodeController;
import com.excellentsystem.TokoEmasGunungMas.View.Dialog.PindahGudangController;
import java.sql.Connection;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
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
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.StringConverter;

/**
 * FXML Controller class
 *
 * @author Xtreme
 */
public class BarcodeBarangController {

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
    private TableColumn<Barang, String> kodeInternColumn;
    @FXML
    private TableColumn<Barang, String> kadarColumn;
    @FXML
    private TableColumn<Barang, Number> beratColumn;
    @FXML
    private TableColumn<Barang, Number> beratAsliColumn;
    @FXML
    private TableColumn<Barang, Number> nilaiPokokColumn;
    @FXML
    private TableColumn<Barang, String> userBarcodeColumn;
    @FXML
    private TableColumn<Barang, String> tglBarcodeColumn;

    @FXML
    private CheckBox checkAll;
    @FXML
    private ComboBox<String> kodeGudangCombo;
    @FXML
    private ComboBox<Jenis> jenisCombo;
    @FXML
    private TextField qtyJenisField;
    @FXML
    private TextField beratJenisField;
    @FXML
    private TextField namaBarangField;
    @FXML
    private TextField keteranganField;
    @FXML
    private TextField beratField;
    @FXML
    private TextField beratAsliField;
    @FXML
    private TextField hargaPokokField;
    @FXML
    private TextField kadarField;
    @FXML
    private TextField kodeInternField;
    @FXML
    private Button saveButton;
    @FXML
    private Label totalQty;
    @FXML
    private Label totalBerat;
    @FXML
    private Label totalBeratAsli;
    @FXML
    private Label totalBeratLabel;
    @FXML
    private Label warningNamaBarang;
    private Main mainApp;
    private final ObservableList<Barang> allBarang = FXCollections.observableArrayList();
    private final ObservableList<Jenis> allJenis = FXCollections.observableArrayList();
    private final ObservableList<String> allGudang = FXCollections.observableArrayList();

    public void initialize() {
        kodeBarcodeColumn.setCellValueFactory(cellData -> cellData.getValue().kodeBarcodeProperty());
        namaBarangColumn.setCellValueFactory(cellData -> cellData.getValue().namaBarangProperty());
        keteranganColumn.setCellValueFactory(cellData -> cellData.getValue().keteranganProperty());
        kodeKategoriColumn.setCellValueFactory(cellData -> cellData.getValue().kodeKategoriProperty());
        kodeJenisColumn.setCellValueFactory(cellData -> cellData.getValue().kodeJenisProperty());
        kadarColumn.setCellValueFactory(cellData -> cellData.getValue().kadarProperty());
        kodeInternColumn.setCellValueFactory(cellData -> cellData.getValue().kodeInternProperty());
        beratColumn.setCellValueFactory(cellData -> cellData.getValue().beratProperty());
        beratColumn.setCellFactory(col -> getTableCell(gr));
        beratAsliColumn.setCellValueFactory(cellData -> cellData.getValue().beratAsliProperty());
        beratAsliColumn.setCellFactory(col -> getTableCell(gr));
        nilaiPokokColumn.setCellValueFactory(cellData -> cellData.getValue().nilaiPokokProperty());
        nilaiPokokColumn.setCellFactory(col -> getTableCell(rp));
        userBarcodeColumn.setCellValueFactory(cellData -> cellData.getValue().barcodeByProperty());
        tglBarcodeColumn.setCellValueFactory(cellData -> {
            try {
                return new SimpleStringProperty(tglLengkap.format(tglSql.parse(
                        cellData.getValue().getBarcodeDate())));
            } catch (ParseException ex) {
                return null;
            }
        });
        checkColumn.setCellValueFactory(cellData -> cellData.getValue().statusProperty());
        checkColumn.setCellFactory(CheckBoxTableCell.forTableColumn((Integer param) -> {
            return barangTable.getItems().get(param).statusProperty();
        }));
        jenisCombo.setConverter(new StringConverter<Jenis>() {
            @Override
            public String toString(Jenis p) {
                return p == null ? null : p.getKodeJenis() + "-" + p.getNamaJenis();
            }

            @Override
            public Jenis fromString(String string) {
                Jenis p = null;
                if (string == null) {
                    return p;
                }
                int commaIndex = string.indexOf("-");
                if (commaIndex == -1) {
                    for (Jenis j : jenisCombo.getItems()) {
                        if (j.getKodeJenis().toUpperCase().equals(string.toUpperCase())) {
                            p = j;
                        }
                    }
                } else {
                    String kodeJenis = string.substring(0, commaIndex);
                    for (Jenis j : jenisCombo.getItems()) {
                        if (j.getKodeJenis().toUpperCase().equals(kodeJenis.toUpperCase())) {
                            p = j;
                        }
                    }
                }
                return p;
            }
        });
        final ContextMenu rowMenu = new ContextMenu();
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent e) -> {
            getBarang();
        });
        rowMenu.getItems().addAll(refresh);
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
                        MenuItem cetak = new MenuItem("Cetak Barcode");
                        cetak.setOnAction((ActionEvent e) -> {
                            cetakBarcode();
                        });
                        MenuItem pindah = new MenuItem("Pindah Gudang");
                        pindah.setOnAction((ActionEvent event) -> {
                            pindahBarang();
                        });
                        MenuItem refresh = new MenuItem("Refresh");
                        refresh.setOnAction((ActionEvent event) -> {
                            getBarang();
                        });
                        rowMenu.getItems().addAll(cetak, pindah, refresh);
                        setContextMenu(rowMenu);
                    }
                }
            };
            row.setOnMouseClicked((MouseEvent mouseEvent) -> {
                if (mouseEvent.getButton().equals(MouseButton.PRIMARY) && mouseEvent.getClickCount() == 2) {
                    if (row.getItem().isStatus()) {
                        row.getItem().setStatus(false);
                    } else {
                        row.getItem().setStatus(true);
                    }
                }
            });
            return row;
        });
        Function.setRpField(hargaPokokField);
        Function.setBeratField(beratField);
        beratAsliField.setOnKeyReleased((event) -> {
            hitungHargaPokok();
        });
        jenisCombo.setOnKeyPressed((KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                namaBarangField.selectAll();
                namaBarangField.requestFocus();
            }
        });
        namaBarangField.setOnKeyPressed((KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                keteranganField.selectAll();
                keteranganField.requestFocus();
            }
        });
        keteranganField.setOnKeyPressed((KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                beratField.selectAll();
                beratField.requestFocus();
            }
        });
        beratField.setOnKeyPressed((KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                beratAsliField.selectAll();
                beratAsliField.requestFocus();
            }
        });
        beratAsliField.setOnKeyPressed((KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                hargaPokokField.selectAll();
                hargaPokokField.requestFocus();
            }
        });
        hargaPokokField.setOnKeyPressed((KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                kadarField.selectAll();
                kadarField.requestFocus();
            }
        });
        kadarField.setOnKeyPressed((KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                kodeInternField.selectAll();
                kodeInternField.requestFocus();
            }
        });
        kodeInternField.setOnKeyPressed((KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                saveButton.fire();
            }
        });
        saveButton.setOnKeyPressed((KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                saveButton.fire();
            }
        });
    }

    private void setBarcodeBarang() {
        try (Connection con = Koneksi.getConnection()) {
            allGudang.clear();
            allJenis.clear();
            List<Gudang> listGudang = GudangDAO.getAll(con);
            List<Jenis> listJenis = JenisDAO.getAll(con);
            List<Kategori> listKategori = KategoriDAO.getAll(con);
            List<StokBarang> listStokDalam = StokBarangDAO.getStokNonBarcodeByDate(con, sistem.getTglSystem());
            for (Gudang g : listGudang) {
                allGudang.add(g.getKodeGudang());
            }
            for (StokBarang sb : listStokDalam) {
                if (sb.getStokAkhir() != 0) {
                    for (Jenis j : listJenis) {
                        if (sb.getKodeJenis().equals(j.getKodeJenis())) {
                            for (Kategori k : listKategori) {
                                if (sb.getKodeKategori().equals(k.getKodeKategori())) {
                                    j.setKategori(k);
                                }
                            }
                            allJenis.add(j);
                        }
                    }
                }
            }
        } catch (Exception e) {
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
        barangTable.setItems(allBarang);
        kodeGudangCombo.setItems(allGudang);
        jenisCombo.setItems(allJenis);
        setBarcodeBarang();
    }

    @FXML
    private void getBarang() {
        try (Connection con = Koneksi.getConnection()) {
            allBarang.clear();
            List<Barang> all = BarangDAO.getAll(con, "Tersedia", "Semua",
                    kodeGudangCombo.getSelectionModel().getSelectedItem(), "Semua", "Semua");
            for (Barang b : all) {
                b.setStatus(checkAll.isSelected());
            }
            allBarang.addAll(all);
            for (Barang b : allBarang) {
                b.statusProperty().addListener((obs, oldValue, newValue) -> {
                    hitungTotal();
                });
            }
            hitungTotal();
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

    @FXML
    private void warningNama() {
        if (namaBarangField.getText().length() > 12) {
            warningNamaBarang.setVisible(true);
        } else {
            warningNamaBarang.setVisible(false);
        }
    }

    private void hitungTotal() {
        double beratAsli = 0;
        double berat = 0;
        int qty = 0;
        for (Barang b : allBarang) {
            if (b.isStatus()) {
                beratAsli = beratAsli + b.getBeratAsli();
                berat = berat + b.getBerat();
                qty = qty + 1;
            }
        }
        totalBerat.setText(gr.format(berat));
        totalQty.setText(gr.format(qty));
        totalBeratAsli.setText(gr.format(beratAsli));
        totalBeratLabel.setText(gr.format(beratAsli + (qty * Main.sistem.getBeratLabel())));
    }

    @FXML
    private void setJenis() {
        try (Connection con = Koneksi.getConnection()) {
            if (jenisCombo.getSelectionModel().getSelectedItem() != null) {
                StokBarang s = StokBarangDAO.getStokNonBarcodeTodayByKodeJenis(con,
                        jenisCombo.getSelectionModel().getSelectedItem().getKodeJenis());
                if (s != null) {
                    qtyJenisField.setText(gr.format(s.getStokAkhir()));
                    beratJenisField.setText(gr.format(s.getBeratAkhir()));
                } else {
                    qtyJenisField.setText("0");
                    beratJenisField.setText("0");
                }
                cancel();
            }
        } catch (Exception e) {
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }

    @FXML
    private void hitungHargaPokok() {
        double hargaBeli = 0;
        if (jenisCombo.getSelectionModel().getSelectedItem() != null) {
            hargaBeli = jenisCombo.getSelectionModel().getSelectedItem().getKategori().getHargaBeli();
        }
        try {
            String string = beratAsliField.getText();
            if (string.indexOf(".") > 0) {
                String string2 = string.substring(string.indexOf(".") + 1, string.length());
                if (string2.contains(".")) {
                    beratAsliField.undo();
                } else if (!string2.equals("") && Double.parseDouble(string2) != 0) {
                    beratAsliField.setText(gr.format(Double.parseDouble(beratAsliField.getText().replaceAll(",", ""))));
                }
            } else {
                beratAsliField.setText(gr.format(Double.parseDouble(beratAsliField.getText().replaceAll(",", ""))));
            }
            hargaPokokField.setText(rp.format(hargaBeli
                    * Double.parseDouble(beratAsliField.getText().replaceAll(",", ""))));
            beratAsliField.end();
        } catch (NumberFormatException e) {
            beratAsliField.undo();
        }
    }

    @FXML
    private void cancel() {
        namaBarangField.setText("");
        keteranganField.setText("");
        beratField.setText("0");
        beratAsliField.setText("0");
        hargaPokokField.setText("0");
        kadarField.setText("");
        kodeInternField.setText("");
    }

    @FXML
    private void save() {
        if (kodeGudangCombo.getSelectionModel().getSelectedItem() == null) {
            mainApp.showMessage(Modality.NONE, "Warning", "Gudang belum dipilih");
        } else if (namaBarangField.getText().equals("")) {
            mainApp.showMessage(Modality.NONE, "Warning", "Nama barang masih kosong");
        } else if (kadarField.getText().equals("")) {
            mainApp.showMessage(Modality.NONE, "Warning", "Kadar masih kosong");
        } else if (kodeInternField.getText().equals("")) {
            mainApp.showMessage(Modality.NONE, "Warning", "Kode intern masih kosong");
        } else if (beratField.getText().equals("") || beratField.getText().equals("0")) {
            mainApp.showMessage(Modality.NONE, "Warning", "Berat masih kosong");
        } else if (beratAsliField.getText().equals("") || beratAsliField.getText().equals("0")) {
            mainApp.showMessage(Modality.NONE, "Warning", "Berat asli masih kosong");
        } else if (Integer.parseInt(qtyJenisField.getText()) < 1) {
            mainApp.showMessage(Modality.NONE, "Warning", "Qty barang sudah habis / tidak ada");
        } else if (Double.parseDouble(beratField.getText().replaceAll(",", ""))
                < Double.parseDouble(beratAsliField.getText().replaceAll(",", ""))) {
            mainApp.showMessage(Modality.NONE, "Warning", "Berat asli tidak boleh melebihi berat jual");
        } else if (Double.parseDouble(beratJenisField.getText().replaceAll(",", ""))
                < Double.parseDouble(beratAsliField.getText().replaceAll(",", ""))) {
            mainApp.showMessage(Modality.NONE, "Warning", "Berat asli melebihi stok berat yang tersisa");
        } else {
            try (Connection con = Koneksi.getConnection()) {
                Barang b = new Barang();
                b.setNamaBarang(namaBarangField.getText());
                b.setKeterangan(keteranganField.getText());
                b.setKodeKategori(jenisCombo.getSelectionModel().getSelectedItem().getKodeKategori());
                b.setKodeJenis(jenisCombo.getSelectionModel().getSelectedItem().getKodeJenis());
                b.setKodeGudang(kodeGudangCombo.getSelectionModel().getSelectedItem());
                b.setKodeIntern(kodeInternField.getText());
                b.setKadar(kadarField.getText());
                b.setBerat(Double.parseDouble(beratField.getText().replaceAll(",", "")));
                b.setBeratAsli(Double.parseDouble(beratAsliField.getText().replaceAll(",", "")));
                b.setNilaiPokok(Double.parseDouble(hargaPokokField.getText().replaceAll(",", "")));
                b.setHargaJual(Math.ceil(jenisCombo.getSelectionModel().getSelectedItem().getKategori().getHargaJual() * b.getBerat() * 500) / 500);
                b.setStatusBarang("Tersedia");
                b.setBarcodeBy(user.getUsername());
                b.setBarcodeDate(tglSql.format(new Date()));
                b.setDeletedBy("-");
                b.setDeletedDate("2000-01-01 00:00:00");
                b.setSoldBy("-");
                b.setSoldDate("2000-01-01 00:00:00");
                String status = Service.saveNewBarang(con, b);
                if (status.equals("true")) {
                    getBarang();
                    setJenis();
                    namaBarangField.selectAll();
                    namaBarangField.requestFocus();
                } else {
                    mainApp.showMessage(Modality.NONE, "Error", status);
                }
            } catch (Exception e) {
                mainApp.showMessage(Modality.NONE, "Error", e.toString());
            }
        }
    }

    private void cetakBarcode() {
        List<Barang> barang = new ArrayList<>();
        for (Barang b : allBarang) {
            if (b.isStatus()) {
                barang.add(b);
            }
        }
        if (barang.isEmpty()) {
            mainApp.showMessage(Modality.NONE, "Warning", "Barang belum dipilih");
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

    private void pindahBarang() {
        List<Barang> barang = new ArrayList<>();
        for (Barang b : allBarang) {
            if (b.isStatus()) {
                barang.add(b);
            }
        }
        if (barang.isEmpty()) {
            mainApp.showMessage(Modality.NONE, "Warning", "Barang belum dipilih");
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
                            mainApp.showMessage(Modality.NONE, "Success", "Pindah Gudang berhasil disimpan");
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
}
