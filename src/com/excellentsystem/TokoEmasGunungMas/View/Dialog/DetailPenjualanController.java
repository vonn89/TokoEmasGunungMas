/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasGunungMas.View.Dialog;

import com.excellentsystem.TokoEmasGunungMas.DAO.BarangDAO;
import com.excellentsystem.TokoEmasGunungMas.DAO.PelangganDAO;
import com.excellentsystem.TokoEmasGunungMas.DAO.SalesDAO;
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
import com.excellentsystem.TokoEmasGunungMas.Model.Pelanggan;
import com.excellentsystem.TokoEmasGunungMas.Model.PenjualanDetail;
import com.excellentsystem.TokoEmasGunungMas.Model.PenjualanHead;
import com.excellentsystem.TokoEmasGunungMas.Model.Sales;
import com.excellentsystem.TokoEmasGunungMas.Model.StokBarang;
import com.excellentsystem.TokoEmasGunungMas.PrintOut.PrintOut;
import com.excellentsystem.TokoEmasGunungMas.Service;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Yunaz
 */
public class DetailPenjualanController {

    @FXML
    private TableView<PenjualanDetail> detailTable;
    @FXML
    private TableColumn<PenjualanDetail, String> kodeBarcodeColumn;
    @FXML
    private TableColumn<PenjualanDetail, String> namaBarangColumn;
    @FXML
    private TableColumn<PenjualanDetail, Number> beratColumn;
    @FXML
    private TableColumn<PenjualanDetail, Number> hargaJualColumn;

    @FXML
    private Label noPenjualanLabel;
    @FXML
    private Label tglPenjualanLabel;
    @FXML
    private Label totalBeratLabel;
    @FXML
    private Label grandtotalLabel;
    @FXML
    private TextField namaField;
    @FXML
    private TextField alamatField;
    @FXML
    private TextField noTelpField;
    @FXML
    public TextField kodeBarcodeField;
    @FXML
    private TextField namaBarangField;
    @FXML
    private TextField beratField;
    @FXML
    private TextField hargaField;
    @FXML
    private TextArea catatanField;
    @FXML
    private Button saveCustomerButton;
    @FXML
    private Button addCustomerButton;
    @FXML
    private Button addItemButton;
    @FXML
    private Button saveButton;
    @FXML
    private Button cancelButton;
    @FXML
    private ComboBox<String> salesCombo;
    @FXML
    private CheckBox printCheckBox;
    private Pelanggan plg;
    private ObservableList<PenjualanDetail> allDetail = FXCollections.observableArrayList();
    private Main mainApp;
    private Stage stage;
    private Stage owner;

    public void initialize() {
        kodeBarcodeColumn.setCellValueFactory(cellData -> cellData.getValue().kodeBarcodeProperty());
        namaBarangColumn.setCellValueFactory(cellData -> cellData.getValue().namaBarangProperty());
        beratColumn.setCellValueFactory(cellData -> cellData.getValue().beratProperty());
        beratColumn.setCellFactory(col -> getTableCell(gr));
        hargaJualColumn.setCellValueFactory(cellData -> cellData.getValue().hargaJualProperty());
        hargaJualColumn.setCellFactory(col -> getTableCell(rp));
        Function.setRpField(hargaField);
        namaField.setOnKeyPressed((KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                alamatField.selectAll();
                alamatField.requestFocus();
            }
        });
        alamatField.setOnKeyPressed((KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                noTelpField.selectAll();
                noTelpField.requestFocus();
            }
        });
        kodeBarcodeField.setOnKeyPressed((KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                getBarang();
            }
        });
        hargaField.setOnKeyPressed((KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                addItemButton.fire();
            }
        });
        final ContextMenu rowMenu = new ContextMenu();
        MenuItem cari = new MenuItem("Cari Barang");
        cari.setOnAction((ActionEvent event) -> {
            searchBarang();
        });
        if (cancelButton.getText().equals("Cancel")) {
            rowMenu.getItems().addAll(cari);
        }
        detailTable.setContextMenu(rowMenu);
        detailTable.setRowFactory(table -> {
            TableRow<PenjualanDetail> row = new TableRow<PenjualanDetail>() {
                @Override
                public void updateItem(PenjualanDetail item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(rowMenu);
                    } else {
                        final ContextMenu rowMenu = new ContextMenu();
                        MenuItem cari = new MenuItem("Cari Barang");
                        cari.setOnAction((ActionEvent event) -> {
                            searchBarang();
                        });
                        MenuItem lihat = new MenuItem("Lihat Barang");
                        lihat.setOnAction((ActionEvent event) -> {
                            lihatBarang(item);
                        });
                        MenuItem hapus = new MenuItem("Hapus Barang");
                        hapus.setOnAction((ActionEvent event) -> {
                            allDetail.remove(item);
                            hitungTotal();
                            detailTable.refresh();
                        });
                        if (cancelButton.getText().equals("Cancel")) {
                            rowMenu.getItems().addAll(cari, lihat, hapus);
                        } else {
                            rowMenu.getItems().addAll(lihat);
                        }
                        setContextMenu(rowMenu);

                    }
                }
            };
            return row;
        });
    }

    public void setMainApp(Main mainApp, Stage owner, Stage stage) {
        try (Connection con = Koneksi.getConnection()) {
            this.mainApp = mainApp;
            this.stage = stage;
            this.owner = owner;
            stage.setOnCloseRequest((event) -> {
                mainApp.closeDialog(owner, stage);
            });
            stage.setHeight(mainApp.screenSize.getHeight() - 80);
            stage.setWidth(mainApp.screenSize.getWidth() - 80);
            stage.setX((mainApp.screenSize.getWidth() - stage.getWidth()) / 2);
            stage.setY((mainApp.screenSize.getHeight() - stage.getHeight()) / 2);
            detailTable.setItems(allDetail);
            ObservableList<String> sales = FXCollections.observableArrayList();
            List<Sales> temp = SalesDAO.getAll(con);
            for (Sales s : temp) {
                sales.add(s.getNama());
            }
            salesCombo.setItems(sales);
        } catch (Exception e) {
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }

    @FXML
    private void close() {
        mainApp.closeDialog(owner, stage);
    }

    private void hitungTotal() {
        double totalBerat = 0;
        double totalHarga = 0;
        for (PenjualanDetail d : allDetail) {
            totalBerat = totalBerat + d.getBerat();
            totalHarga = totalHarga + d.getHargaJual();
        }
        totalBeratLabel.setText(gr.format(totalBerat));
        grandtotalLabel.setText(rp.format(totalHarga));
    }

    @FXML
    private void savePelanggan() {
        try (Connection con = Koneksi.getConnection()) {
            Pelanggan p = new Pelanggan();
            p.setKodePelanggan(PelangganDAO.getId(con));
            p.setNama(namaField.getText());
            p.setAlamat(alamatField.getText());
            p.setNoTelp(noTelpField.getText());
            p.setNoHandphone("");
            p.setKeterangan("");
            p.setIdentitas("");
            p.setNoIdentitas("");
            p.setStatus("true");
            PelangganDAO.insert(con, p);
            mainApp.showMessage(Modality.NONE, "Success", "Pelanggan berhasil disimpan");
        } catch (Exception e) {
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }

    public void getBarang() {
        try (Connection con = Koneksi.getConnection()) {
            namaBarangField.setText("");
            beratField.setText("0");
            hargaField.setText("0");
            if ("".equals(kodeBarcodeField.getText())) {
                mainApp.showMessage(Modality.NONE, "Warning", "Kode Barcode masih kosong");
            } else {
                Barang b = BarangDAO.get(con, kodeBarcodeField.getText());
                if (b == null) {
                    mainApp.showMessage(Modality.NONE, "Warning", "Kode Barcode tidak ditemukan");
                } else {
                    if (b.getStatusBarang().equals("Tersedia")) {
                        StokBarang s = StokBarangDAO.getStokBarcodeTodayByKodeBarcodeAndKodeGudang(con,
                                kodeBarcodeField.getText(), b.getKodeGudang());
                        if (s.getStokAkhir() == 1) {
                            namaBarangField.setText(b.getNamaBarang());
                            beratField.setText(gr.format(b.getBerat()));
                            hargaField.setText(rp.format(b.getHargaJual()));
                            hargaField.selectAll();
                            hargaField.requestFocus();
                        } else {
                            mainApp.showMessage(Modality.NONE, "Warning", "Stok barang sudah tidak ada");
                        }
                    } else {
                        mainApp.showMessage(Modality.NONE, "Warning", "Barang sudah tidak ada");
                    }
                }
            }
        } catch (Exception e) {
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }

    private void searchBarang() {
        try {
            Stage child = new Stage();
            FXMLLoader loader = mainApp.showDialog(stage, child, "View/Dialog/DetailGroupBarang.fxml");
            DetailGroupBarangController c = loader.getController();
            c.setMainApp(mainApp, stage, child);
            c.setBarang("", "", "", sistem.getTglSystem());
            c.barangTable.setRowFactory(table -> {
                TableRow<StokBarang> row = new TableRow<StokBarang>() {
                };
                row.setOnMouseClicked((MouseEvent mouseEvent) -> {
                    if (mouseEvent.getButton().equals(MouseButton.PRIMARY)
                            && mouseEvent.getClickCount() == 2) {
                        if (row.getItem() != null) {
                            kodeBarcodeField.setText(row.getItem().getKodeBarcode());
                            getBarang();
                        }
                        mainApp.closeDialog(stage, child);
                    }
                });
                return row;
            });
        } catch (Exception e) {
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }

    private void addDetail(Barang barang) {
        PenjualanDetail d = new PenjualanDetail();
        d.setKodeBarcode(barang.getKodeBarcode());
        d.setNamaBarang(barang.getNamaBarang());
        d.setKodeKategori(barang.getKodeKategori());
        d.setKodeJenis(barang.getKodeJenis());
        d.setBerat(barang.getBerat());
        d.setNilaiPokok(barang.getNilaiPokok());
        d.setHargaKomp(barang.getHargaJual());
        d.setHargaJual(Double.parseDouble(hargaField.getText().replaceAll(",", "")));
        d.setNoPembelian("");
        d.setBarang(barang);
        allDetail.add(d);
        detailTable.refresh();
        hitungTotal();
        kodeBarcodeField.setText("");
        namaBarangField.setText("");
        beratField.setText("0");
        hargaField.setText("0");
        kodeBarcodeField.selectAll();
        kodeBarcodeField.requestFocus();
    }

    @FXML
    private void addBarang() {
        if ("".equals(kodeBarcodeField.getText())) {
            mainApp.showMessage(Modality.NONE, "Warning", "Kode barcode masih kosong");
        } else if ("".equals(namaBarangField.getText())) {
            mainApp.showMessage(Modality.NONE, "Warning", "nama barang masih kosong");
        } else if ("".equals(beratField.getText())) {
            mainApp.showMessage(Modality.NONE, "Warning", "berat masih kosong");
        } else if ("".equals(hargaField.getText())) {
            mainApp.showMessage(Modality.NONE, "Warning", "harga masih kosong");
        } else {
            try (Connection con = Koneksi.getConnection()) {
                Barang barang = BarangDAO.get(con, kodeBarcodeField.getText());
                if (barang != null) {
                    boolean input = true;
                    for (PenjualanDetail d : allDetail) {
                        if (d.getKodeBarcode().equals(barang.getKodeBarcode())) {
                            input = false;
                            mainApp.showMessage(Modality.NONE, "Warning", "Barang sudah diinput");
                        }
                    }
                    if (allDetail.size() >= 3) {
                        input = false;
                        mainApp.showMessage(Modality.NONE, "Warning", "1 Surat penjualan maksimal 3 barang");
                    }
                    if (input) {
                        if (Double.parseDouble(hargaField.getText().replaceAll(",", "")) < barang.getHargaJual()) {
                            Stage child = new Stage();
                            FXMLLoader loader = mainApp.showDialog(stage, child, "View/Dialog/Verifikasi.fxml");
                            VerifikasiController controller = loader.getController();
                            controller.setMainApp(mainApp, stage, child);
                            controller.password.setOnKeyPressed((KeyEvent keyEvent) -> {
                                if (keyEvent.getCode() == KeyCode.ENTER) {
                                    mainApp.closeDialog(stage, child);
                                    if (Function.verifikasi(controller.username.getText(), controller.password.getText(), "Penjualan")) {
                                        addDetail(barang);
                                    } else {
                                        mainApp.showMessage(Modality.NONE, "Warning", "Verifikasi salah");
                                        kodeBarcodeField.setText("");
                                        namaBarangField.setText("");
                                        beratField.setText("0");
                                        hargaField.setText("0");
                                        kodeBarcodeField.selectAll();
                                        kodeBarcodeField.requestFocus();
                                    }
                                }
                            });
                        } else {
                            addDetail(barang);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                mainApp.showMessage(Modality.NONE, "Error", e.toString());
            }
        }
    }
    @FXML
    private GridPane gridPane;
    @FXML
    private AnchorPane anchorPane;

    public void setPenjualan(PenjualanHead p) {
        try {
            noPenjualanLabel.setText("");
            tglPenjualanLabel.setText("");
            plg = null;
            namaField.setText("");
            alamatField.setText("");
            noTelpField.setText("");
            salesCombo.getSelectionModel().select("");
            totalBeratLabel.setText("0");
            grandtotalLabel.setText("0");
            catatanField.setText("");
            allDetail.clear();
            namaField.setDisable(true);
            alamatField.setDisable(true);
            noTelpField.setDisable(true);
            kodeBarcodeField.setDisable(true);
            hargaField.setDisable(true);
            addItemButton.setDisable(true);
            addCustomerButton.setVisible(false);
            saveCustomerButton.setVisible(false);
            printCheckBox.setVisible(false);
            catatanField.setEditable(false);
            salesCombo.setDisable(true);
            saveButton.setVisible(false);
            cancelButton.setText("Close");
            
            List<MenuItem> removeMenu = new ArrayList<>();
            for (MenuItem m : detailTable.getContextMenu().getItems()) {
                if (m.getText().equals("Cari Barang")) {
                    removeMenu.add(m);
                }
            }
            detailTable.getContextMenu().getItems().removeAll(removeMenu);

            gridPane.getRowConstraints().get(7).setMinHeight(0);
            gridPane.getRowConstraints().get(7).setPrefHeight(0);
            gridPane.getRowConstraints().get(7).setMaxHeight(0);

            anchorPane.setVisible(false);
            if (p != null) {
                noPenjualanLabel.setText(p.getNoPenjualan());
                tglPenjualanLabel.setText(tglLengkap.format(tglSql.parse(p.getTglPenjualan())));
                plg = p.getPelanggan();
                namaField.setText(p.getNama());
                alamatField.setText(p.getAlamat());
                noTelpField.setText(p.getNoTelp());
                salesCombo.getSelectionModel().select(p.getKodeSales());
                totalBeratLabel.setText(rp.format(p.getTotalBerat()));
                grandtotalLabel.setText(rp.format(p.getGrandtotal()));
                catatanField.setText(p.getCatatan());
                allDetail.addAll(p.getAllDetail());
            }
        } catch (Exception e) {
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }

    private void lihatBarang(PenjualanDetail d) {
        try (Connection con = Koneksi.getConnection()) {
            Barang b = BarangDAO.get(con, d.getKodeBarcode());
            Stage child = new Stage();
            FXMLLoader loader = mainApp.showDialog(stage, child, "View/Dialog/DetailBarang.fxml");
            DetailBarangController controller = loader.getController();
            controller.setMainApp(mainApp, stage, child);
            controller.setBarang(b);
            controller.setViewOnly();
        } catch (Exception e) {
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }

    @FXML
    private void showAddCustomer() {
        Stage child = new Stage();
        FXMLLoader loader = mainApp.showDialog(stage, child, "View/Dialog/AddPelanggan.fxml");
        AddPelangganController c = loader.getController();
        c.setMainApp(mainApp, stage, child);
        c.pelangganTable.setRowFactory(table -> {
            TableRow<Pelanggan> row = new TableRow<Pelanggan>() {
                @Override
                public void updateItem(Pelanggan item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(null);
                    } else {
                        final ContextMenu rowMenu = new ContextMenu();
                        MenuItem pilih = new MenuItem("Pilih Pelanggan");
                        pilih.setOnAction((ActionEvent e) -> {
                            addPelanggan(item);
                            mainApp.closeDialog(stage, child);
                        });
                        MenuItem refresh = new MenuItem("Refresh");
                        refresh.setOnAction((ActionEvent e) -> {
                            c.getPelanggan();
                        });
                        rowMenu.getItems().addAll(pilih, refresh);
                        setContextMenu(rowMenu);
                    }
                }
            };
            row.setOnMouseClicked((MouseEvent mouseEvent) -> {
                if (mouseEvent.getButton().equals(MouseButton.PRIMARY) && mouseEvent.getClickCount() == 2) {
                    if (row.getItem() != null) {
                        addPelanggan(row.getItem());
                        mainApp.closeDialog(stage, child);
                    }
                }
            });
            return row;
        });
    }

    private void addPelanggan(Pelanggan p) {
        plg = null;
        namaField.setText("");
        alamatField.setText("");
        noTelpField.setText("");
        if (p != null) {
            plg = p;
            namaField.setText(p.getNama());
            alamatField.setText(p.getAlamat());
            noTelpField.setText(p.getNoTelp());
        }
    }

    @FXML
    private void savePenjualan() {
        if (allDetail.isEmpty()) {
            mainApp.showMessage(Modality.NONE, "Warning", "Detail penjualan masih kosong");
        } else if (salesCombo.getSelectionModel().getSelectedItem() == null) {
            mainApp.showMessage(Modality.NONE, "Warning", "Sales belum dipilih");
        } else {
            try (Connection con = Koneksi.getConnection()) {
                PenjualanHead p = new PenjualanHead();
                p.setTglPenjualan(tglSql.format(new Date()));
                p.setKodeSales(salesCombo.getSelectionModel().getSelectedItem());
                if (plg != null) {
                    p.setKodePelanggan(plg.getKodePelanggan());
                } else {
                    p.setKodePelanggan("");
                }
                p.setNama(namaField.getText());
                p.setAlamat(alamatField.getText());
                p.setNoTelp(noTelpField.getText());
                p.setTotalBerat(Double.parseDouble(totalBeratLabel.getText().replaceAll(",", "")));
                p.setGrandtotal(Double.parseDouble(grandtotalLabel.getText().replaceAll(",", "")));
                p.setCatatan(catatanField.getText());
                p.setKodeUser(user.getUsername());
                p.setStatus("true");
                p.setTglBatal("2000-01-01 00:00:00");
                p.setUserBatal("");
                p.setAllDetail(allDetail);
                String status = Service.savePenjualan(con, p);
                if (status.equals("true")) {
                    if (printCheckBox.isSelected()) {
                        try {
                            PrintOut printOut = new PrintOut();
                            printOut.printSuratPenjualan(p);
                        } catch (Exception e) {
                            mainApp.showMessage(Modality.NONE, "Error", e.toString());
                        }
                    }
                    mainApp.closeDialog(owner, stage);
                    mainApp.showMessage(Modality.NONE, "Success", "Penjualan berhasil disimpan");
                } else {
                    mainApp.showMessage(Modality.NONE, "Error", status);
                }
            } catch (Exception e) {
                mainApp.showMessage(Modality.NONE, "Error", e.toString());
            }
        }
    }
}
