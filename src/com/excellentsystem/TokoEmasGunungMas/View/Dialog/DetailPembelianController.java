/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasGunungMas.View.Dialog;

import com.excellentsystem.TokoEmasGunungMas.DAO.JenisDAO;
import com.excellentsystem.TokoEmasGunungMas.DAO.KategoriDAO;
import com.excellentsystem.TokoEmasGunungMas.DAO.PelangganDAO;
import com.excellentsystem.TokoEmasGunungMas.DAO.SalesDAO;
import com.excellentsystem.TokoEmasGunungMas.Function;
import static com.excellentsystem.TokoEmasGunungMas.Function.getTableCell;
import com.excellentsystem.TokoEmasGunungMas.Koneksi;
import com.excellentsystem.TokoEmasGunungMas.Main;
import static com.excellentsystem.TokoEmasGunungMas.Main.gr;
import static com.excellentsystem.TokoEmasGunungMas.Main.rp;
import static com.excellentsystem.TokoEmasGunungMas.Main.tglLengkap;
import static com.excellentsystem.TokoEmasGunungMas.Main.tglSql;
import static com.excellentsystem.TokoEmasGunungMas.Main.user;
import com.excellentsystem.TokoEmasGunungMas.Model.Jenis;
import com.excellentsystem.TokoEmasGunungMas.Model.Kategori;
import com.excellentsystem.TokoEmasGunungMas.Model.Pelanggan;
import com.excellentsystem.TokoEmasGunungMas.Model.PembelianDetail;
import com.excellentsystem.TokoEmasGunungMas.Model.PembelianHead;
import com.excellentsystem.TokoEmasGunungMas.Model.PenjualanDetail;
import com.excellentsystem.TokoEmasGunungMas.Model.Sales;
import com.excellentsystem.TokoEmasGunungMas.PrintOut.PrintOut;
import com.excellentsystem.TokoEmasGunungMas.Service;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javafx.beans.property.SimpleDoubleProperty;
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
import javafx.util.StringConverter;

/**
 * FXML Controller class
 *
 * @author Yunaz
 */
public class DetailPembelianController {

    @FXML
    private TableView<PembelianDetail> detailTable;
    @FXML
    private TableColumn<PembelianDetail, String> kodeJenisColumn;
    @FXML
    private TableColumn<PembelianDetail, String> kodeKategoriColumn;
    @FXML
    private TableColumn<PembelianDetail, String> namaBarangColumn;
    @FXML
    private TableColumn<PembelianDetail, Number> beratKotorColumn;
    @FXML
    private TableColumn<PembelianDetail, Number> beratBersihColumn;
    @FXML
    private TableColumn<PembelianDetail, Number> hargaBeliPerGramColumn;
    @FXML
    private TableColumn<PembelianDetail, Number> totalColumn;

    @FXML
    private Label noPembelianLabel;
    @FXML
    private Label tglPembelianLabel;
    @FXML
    private Label totalBeratKotorLabel;
    @FXML
    private Label totalBeratBersihLabel;
    @FXML
    private Label totalPembelianLabel;
    @FXML
    private TextField namaField;
    @FXML
    private TextField alamatField;
    @FXML
    private TextField noTelpField;
    @FXML
    private ComboBox<Jenis> kodeJenisCombo;
    @FXML
    private TextField namaBarangField;
    @FXML
    private TextField beratKotorField;
    @FXML
    private TextField beratBersihField;
    @FXML
    private TextField hargaBeliField;
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
    private List<Kategori> kategori;
    private ObservableList<PembelianDetail> allDetail = FXCollections.observableArrayList();
    private Main mainApp;
    private Stage stage;
    private Stage owner;
    private final ObservableList<Jenis> allJenis = FXCollections.observableArrayList();

    public void initialize() {
        kodeJenisColumn.setCellValueFactory(cellData -> cellData.getValue().kodeJenisProperty());
        kodeKategoriColumn.setCellValueFactory(cellData -> cellData.getValue().kodeKategoriProperty());
        namaBarangColumn.setCellValueFactory(cellData -> cellData.getValue().namaBarangProperty());
        beratKotorColumn.setCellValueFactory(cellData -> cellData.getValue().beratKotorProperty());
        beratKotorColumn.setCellFactory(col -> getTableCell(gr));
        beratBersihColumn.setCellValueFactory(cellData -> cellData.getValue().beratBersihProperty());
        beratBersihColumn.setCellFactory(col -> getTableCell(gr));
        hargaBeliPerGramColumn.setCellValueFactory(cellData -> {
            return new SimpleDoubleProperty(cellData.getValue().getHargaBeli() / cellData.getValue().getBeratBersih());
        });
        hargaBeliPerGramColumn.setCellFactory(col -> getTableCell(gr));
        totalColumn.setCellValueFactory(cellData -> cellData.getValue().hargaBeliProperty());
        totalColumn.setCellFactory(col -> getTableCell(gr));
        Function.setBeratField(beratKotorField);
        beratBersihField.setOnKeyReleased((event) -> {
            try {
                String string = beratBersihField.getText();
                if (string.indexOf(".") > 0) {
                    String string2 = string.substring(string.indexOf(".") + 1, string.length());
                    if (string2.contains(".")) {
                        beratBersihField.undo();
                    } else if (!string2.equals("") && Double.parseDouble(string2) != 0) {
                        beratBersihField.setText(gr.format(Double.parseDouble(string.replaceAll(",", ""))));
                    }
                } else {
                    beratBersihField.setText(gr.format(Double.parseDouble(string.replaceAll(",", ""))));
                }
                beratBersihField.end();
            } catch (Exception e) {
                beratBersihField.undo();
            }
            getHargaBeli();
        });
        Function.setRpField(hargaBeliField);
        kodeJenisCombo.setConverter(new StringConverter<Jenis>() {
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
                    for (Jenis j : kodeJenisCombo.getItems()) {
                        if (j.getKodeJenis().toUpperCase().equals(string.toUpperCase())) {
                            p = j;
                        }
                    }
                } else {
                    String kodeJenis = string.substring(0, commaIndex);
                    for (Jenis j : kodeJenisCombo.getItems()) {
                        if (j.getKodeJenis().toUpperCase().equals(kodeJenis.toUpperCase())) {
                            p = j;
                        }
                    }
                }
                return p;
            }
        });
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
        noTelpField.setOnKeyPressed((KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                kodeJenisCombo.requestFocus();
            }
        });
        kodeJenisCombo.setOnKeyPressed((KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                getBarang();
            }
        });
        namaBarangField.setOnKeyPressed((KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                beratKotorField.selectAll();
                beratKotorField.requestFocus();
            }
        });
        beratKotorField.setOnKeyPressed((KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                beratBersihField.selectAll();
                beratBersihField.requestFocus();
            }
        });
        beratBersihField.setOnKeyPressed((KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                hargaBeliField.selectAll();
                hargaBeliField.requestFocus();
            }
        });
        hargaBeliField.setOnKeyPressed((KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                addItemButton.fire();
            }
        });
        final ContextMenu rowMenu = new ContextMenu();
        MenuItem add = new MenuItem("Add Barang Terjual");
        add.setOnAction((ActionEvent event) -> {
            showAddBarang();
        });
        if (cancelButton.getText().equals("Cancel")) {
            rowMenu.getItems().addAll(add);
        }
        detailTable.setContextMenu(rowMenu);
        detailTable.setRowFactory(table -> {
            TableRow<PembelianDetail> row = new TableRow<PembelianDetail>() {
                @Override
                public void updateItem(PembelianDetail item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(rowMenu);
                    } else {
                        final ContextMenu rowMenu = new ContextMenu();
                        MenuItem add = new MenuItem("Add Barang Terjual");
                        add.setOnAction((ActionEvent event) -> {
                            showAddBarang();
                        });
                        MenuItem hapus = new MenuItem("Hapus Barang");
                        hapus.setOnAction((ActionEvent event) -> {
                            allDetail.remove(item);
                            hitungTotal();
                            detailTable.refresh();
                        });
                        if (cancelButton.getText().equals("Cancel")) {
                            rowMenu.getItems().addAll(add, hapus);
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
            List<Sales> temp = SalesDAO.getAll(con);
            ObservableList<String> sales = FXCollections.observableArrayList();
            for (Sales s : temp) {
                sales.add(s.getNama());
            }
            salesCombo.setItems(sales);
            allJenis.addAll(JenisDAO.getAll(con));
            kodeJenisCombo.setItems(allJenis);
            kategori = KategoriDAO.getAll(con);
        } catch (Exception e) {
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }

    @FXML
    private void close() {
        mainApp.closeDialog(owner, stage);
    }

    private void hitungTotal() {
        double totalBeratKotor = 0;
        double totalBeratBersih = 0;
        double totalHarga = 0;
        for (PembelianDetail d : allDetail) {
            totalBeratKotor = totalBeratKotor + d.getBeratKotor();
            totalBeratBersih = totalBeratBersih + d.getBeratBersih();
            totalHarga = totalHarga + d.getHargaBeli();
        }
        totalBeratKotorLabel.setText(gr.format(totalBeratKotor));
        totalBeratBersihLabel.setText(gr.format(totalBeratBersih));
        totalPembelianLabel.setText(rp.format(totalHarga));
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

    @FXML
    private void getBarang() {
        if (kodeJenisCombo.getSelectionModel().getSelectedItem() != null) {
            namaBarangField.setText(kodeJenisCombo.getSelectionModel().getSelectedItem().getNamaJenis());
            getHargaBeli();
            namaBarangField.selectAll();
            namaBarangField.requestFocus();
        }
    }

    @FXML
    private void getHargaBeli() {
        if (kodeJenisCombo.getSelectionModel().getSelectedItem() != null && !"".equals(beratBersihField.getText())) {
            double hargaBeli = 0;
            for (Kategori k : kategori) {
                if (k.getKodeKategori().equals(kodeJenisCombo.getSelectionModel().getSelectedItem().getKodeKategori())) {
                    hargaBeli = Double.parseDouble(beratBersihField.getText().replaceAll(",", "")) * k.getHargaBeli();
                }
            }
            hargaBeliField.setText(rp.format(hargaBeli));
        }
    }

    private void addDetail() {
        double hb = 0;
        for (Kategori k : kategori) {
            if (k.getKodeKategori().equals(kodeJenisCombo.getSelectionModel().getSelectedItem().getKodeKategori())) {
                hb = Double.parseDouble(beratBersihField.getText().replaceAll(",", "")) * k.getHargaBeli();
            }
        }
        PembelianDetail d = new PembelianDetail();
        d.setKodeJenis(kodeJenisCombo.getSelectionModel().getSelectedItem().getKodeJenis());
        d.setKodeKategori(kodeJenisCombo.getSelectionModel().getSelectedItem().getKodeKategori());
        d.setNamaBarang(namaBarangField.getText());
        d.setBeratKotor(Double.parseDouble(beratKotorField.getText().replaceAll(",", "")));
        d.setBeratBersih(Double.parseDouble(beratBersihField.getText().replaceAll(",", "")));
        d.setHargaKomp(hb);
        d.setHargaBeli(Double.parseDouble(hargaBeliField.getText().replaceAll(",", "")));
        allDetail.add(d);
        detailTable.refresh();
        hitungTotal();
        kodeJenisCombo.getSelectionModel().select(null);
        namaBarangField.setText("");
        beratKotorField.setText("0");
        beratBersihField.setText("0");
        hargaBeliField.setText("0");
        kodeJenisCombo.requestFocus();
    }

    @FXML
    private void addBarang() {
        if (kodeJenisCombo.getSelectionModel().getSelectedItem() == null) {
            mainApp.showMessage(Modality.NONE, "Warning", "Kode jenis belum dipilih");
        } else if ("".equals(namaBarangField.getText())) {
            mainApp.showMessage(Modality.NONE, "Warning", "Nama barang masih kosong");
        } else if ("".equals(beratKotorField.getText())) {
            mainApp.showMessage(Modality.NONE, "Warning", "Berat kotor masih kosong");
        } else if (Double.parseDouble(beratKotorField.getText().replaceAll(",", "")) <= 0) {
            mainApp.showMessage(Modality.NONE, "Warning", "Berat kotor masih kosong");
        } else if ("".equals(beratBersihField.getText())) {
            mainApp.showMessage(Modality.NONE, "Warning", "Berat bersih masih kosong");
        } else if (Double.parseDouble(beratBersihField.getText().replaceAll(",", "")) <= 0) {
            mainApp.showMessage(Modality.NONE, "Warning", "Berat bersih masih kosong");
        } else if ("".equals(hargaBeliField.getText())) {
            mainApp.showMessage(Modality.NONE, "Warning", "Harga beli masih kosong");
        } else if (Double.parseDouble(hargaBeliField.getText().replaceAll(",", "")) <= 0) {
            mainApp.showMessage(Modality.NONE, "Warning", "Harga beli masih kosong");
        } else {
            try {
                double hargaBeli = 0;
                for (Kategori k : kategori) {
                    if (k.getKodeKategori().equals(kodeJenisCombo.getSelectionModel().getSelectedItem().getKodeKategori())) {
                        hargaBeli = Double.parseDouble(beratBersihField.getText().replaceAll(",", "")) * k.getHargaBeli();
                    }
                }
                if (Double.parseDouble(hargaBeliField.getText().replaceAll(",", "")) > hargaBeli) {
                    Stage child = new Stage();
                    FXMLLoader loader = mainApp.showDialog(stage, child, "View/Dialog/Verifikasi.fxml");
                    VerifikasiController controller = loader.getController();
                    controller.setMainApp(mainApp, stage, child);
                    controller.password.setOnKeyPressed((KeyEvent keyEvent) -> {
                        if (keyEvent.getCode() == KeyCode.ENTER) {
                            mainApp.closeDialog(stage, child);
                            if (Function.verifikasi(controller.username.getText(), controller.password.getText(), "Pembelian")) {
                                addDetail();
                            } else {
                                mainApp.showMessage(Modality.NONE, "Warning", "Verifikasi salah");
                                kodeJenisCombo.getSelectionModel().select(null);
                                namaBarangField.setText("");
                                beratKotorField.setText("0");
                                beratBersihField.setText("0");
                                hargaBeliField.setText("0");
                                kodeJenisCombo.requestFocus();
                            }
                        }
                    });
                } else {
                    addDetail();
                }
            } catch (Exception e) {
                mainApp.showMessage(Modality.NONE, "Error", e.toString());
            }
        }
    }

    @FXML
    private GridPane gridPane;
    @FXML
    private AnchorPane anchorPane;

    public void setPembelian(PembelianHead p) {
        try {
            noPembelianLabel.setText("");
            tglPembelianLabel.setText("");
            namaField.setText("");
            alamatField.setText("");
            noTelpField.setText("");
            salesCombo.getSelectionModel().select("");
            totalBeratKotorLabel.setText("0");
            totalBeratBersihLabel.setText("0");
            totalPembelianLabel.setText("0");
            catatanField.setText("");
            allDetail.clear();
            namaField.setDisable(true);
            alamatField.setDisable(true);
            noTelpField.setDisable(true);
            kodeJenisCombo.setDisable(true);
            namaBarangField.setDisable(true);
            beratKotorField.setDisable(true);
            beratBersihField.setDisable(true);
            hargaBeliField.setDisable(true);
            addItemButton.setDisable(true);
            saveCustomerButton.setVisible(false);
            addCustomerButton.setVisible(false);
            printCheckBox.setVisible(false);
            catatanField.setEditable(false);
            salesCombo.setDisable(true);
            saveButton.setVisible(false);
            cancelButton.setText("Close");
            List<MenuItem> removeMenu = new ArrayList<>();
            for (MenuItem m : detailTable.getContextMenu().getItems()) {
                if (m.getText().equals("Add Barang Terjual")) {
                    removeMenu.add(m);
                }
            }
            detailTable.getContextMenu().getItems().removeAll(removeMenu);

            gridPane.getRowConstraints().get(7).setMinHeight(0);
            gridPane.getRowConstraints().get(7).setPrefHeight(0);
            gridPane.getRowConstraints().get(7).setMaxHeight(0);

            anchorPane.setVisible(false);

            if (p != null) {
                noPembelianLabel.setText(p.getNoPembelian());
                tglPembelianLabel.setText(tglLengkap.format(tglSql.parse(p.getTglPembelian())));
                namaField.setText(p.getNama());
                alamatField.setText(p.getAlamat());
                noTelpField.setText(p.getNoTelp());
                salesCombo.getSelectionModel().select(p.getKodeSales());
                totalBeratKotorLabel.setText(gr.format(p.getTotalberatKotor()));
                totalBeratBersihLabel.setText(gr.format(p.getTotalBeratBersih()));
                totalPembelianLabel.setText(rp.format(p.getTotalPembelian()));
                catatanField.setText(p.getCatatan());
                allDetail.addAll(p.getAllDetail());
            }
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
    private void showAddBarang() {
        Stage child = new Stage();
        FXMLLoader loader = mainApp.showDialog(stage, child, "View/Dialog/AddBarangTerjual.fxml");
        AddBarangTerjualController c = loader.getController();
        c.setMainApp(mainApp, stage, child);
        c.penjualanTable.setRowFactory(table -> {
            TableRow<PenjualanDetail> row = new TableRow<PenjualanDetail>() {
                @Override
                public void updateItem(PenjualanDetail item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(null);
                    } else {
                        final ContextMenu rowMenu = new ContextMenu();
                        MenuItem pilih = new MenuItem("Pilih Barang");
                        pilih.setOnAction((ActionEvent e) -> {
                            addBarangTerjual(item);
                            mainApp.closeDialog(stage, child);
                        });
                        MenuItem refresh = new MenuItem("Refresh");
                        refresh.setOnAction((ActionEvent e) -> {
                            c.getPenjualanDetail();
                        });
                        rowMenu.getItems().addAll(pilih, refresh);
                        setContextMenu(rowMenu);
                    }
                }
            };
            row.setOnMouseClicked((MouseEvent mouseEvent) -> {
                if (mouseEvent.getButton().equals(MouseButton.PRIMARY) && mouseEvent.getClickCount() == 2) {
                    if (row.getItem() != null) {
                        addBarangTerjual(row.getItem());
                        mainApp.closeDialog(stage, child);
                    }
                }
            });
            return row;
        });
    }

    private void addBarangTerjual(PenjualanDetail d) {
        kodeJenisCombo.getSelectionModel().select(null);
        namaBarangField.setText("");
        beratKotorField.setText("0");
        beratBersihField.setText("0");
        hargaBeliField.setText("0");
        if (d != null) {
            for (Jenis j : allJenis) {
                if (j.getKodeJenis().equals(d.getKodeJenis())) {
                    kodeJenisCombo.getSelectionModel().select(j);
                }
            }
            namaBarangField.setText(d.getNamaBarang());
            beratKotorField.setText(gr.format(d.getBerat()));
            beratBersihField.setText(gr.format(d.getBerat()));
            getHargaBeli();
        }
    }

    @FXML
    private void savePembelian() {
        if (allDetail.isEmpty()) {
            mainApp.showMessage(Modality.NONE, "Warning", "Detail pembelian masih kosong");
        } else if (salesCombo.getSelectionModel().getSelectedItem() == null) {
            mainApp.showMessage(Modality.NONE, "Warning", "Sales belum dipilih");
        } else {
            try (Connection con = Koneksi.getConnection()) {
                PembelianHead p = new PembelianHead();
                p.setTglPembelian(tglSql.format(new Date()));
                p.setKodeSales(salesCombo.getSelectionModel().getSelectedItem());
                if (plg != null) {
                    p.setKodePelanggan(plg.getKodePelanggan());
                } else {
                    p.setKodePelanggan("");
                }
                p.setNama(namaField.getText());
                p.setAlamat(alamatField.getText());
                p.setNoTelp(noTelpField.getText());
                p.setTotalberatKotor(Double.parseDouble(totalBeratKotorLabel.getText().replaceAll(",", "")));
                p.setTotalBeratBersih(Double.parseDouble(totalBeratBersihLabel.getText().replaceAll(",", "")));
                p.setTotalPembelian(Double.parseDouble(totalPembelianLabel.getText().replaceAll(",", "")));
                p.setCatatan(catatanField.getText());
                p.setStatus("true");
                p.setKodeUser(user.getUsername());
                p.setTglBatal("2000-01-01 00:00:00");
                p.setUserBatal("");
                for (PembelianDetail d : allDetail) {
                    d.setPembelian(p);
                }
                p.setAllDetail(allDetail);
                String status = Service.savePembelian(con, p);
                if (status.equals("true")) {
                    if (printCheckBox.isSelected()) {
                        try {
                            PrintOut printOut = new PrintOut();
                            printOut.printBuktiPembelian(allDetail);
                        } catch (Exception e) {
                            mainApp.showMessage(Modality.NONE, "Error", e.toString());
                        }
                    }
                    mainApp.closeDialog(owner, stage);
                    mainApp.showPembelian();
                    mainApp.showMessage(Modality.NONE, "Success", "Pembelian berhasil disimpan");
                } else {
                    mainApp.showMessage(Modality.NONE, "Error", status);
                }
            } catch (Exception e) {
                mainApp.showMessage(Modality.NONE, "Warning", e.toString());
            }
        }
    }

}
