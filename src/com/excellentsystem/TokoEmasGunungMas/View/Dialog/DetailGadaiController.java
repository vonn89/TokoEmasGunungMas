/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasGunungMas.View.Dialog;

import com.excellentsystem.TokoEmasGunungMas.DAO.BungaGadaiDAO;
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
import com.excellentsystem.TokoEmasGunungMas.Model.BungaGadai;
import com.excellentsystem.TokoEmasGunungMas.Model.GadaiDetail;
import com.excellentsystem.TokoEmasGunungMas.Model.GadaiHead;
import com.excellentsystem.TokoEmasGunungMas.Model.Kategori;
import com.excellentsystem.TokoEmasGunungMas.Model.Pelanggan;
import com.excellentsystem.TokoEmasGunungMas.Model.Sales;
import com.excellentsystem.TokoEmasGunungMas.PrintOut.PrintOut;
import com.excellentsystem.TokoEmasGunungMas.Service;
import java.sql.Connection;
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
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Yunaz
 */
public class DetailGadaiController {

    @FXML
    private TableView<GadaiDetail> detailTable;
    @FXML
    private TableColumn<GadaiDetail, String> kodeKategoriColumn;
    @FXML
    private TableColumn<GadaiDetail, String> namaBarangColumn;
    @FXML
    private TableColumn<GadaiDetail, Number> beratColumn;
    @FXML
    private TableColumn<GadaiDetail, Number> nilaiJualColumn;

    @FXML
    private Label noGadaiLabel;
    @FXML
    private Label tglGadaiLabel;
    @FXML
    private Label totalBeratLabel;
    @FXML
    private Label totalNilaiJualLabel;
    @FXML
    private TextField namaField;
    @FXML
    private TextField alamatField;
    @FXML
    private TextField noTelpField;
    @FXML
    private ComboBox<String> kodeKategoriCombo;
    @FXML
    private TextField namaBarangField;
    @FXML
    private TextField beratField;
    @FXML
    private TextField nilaiJualField;
    @FXML
    private TextArea catatanField;
    @FXML
    private TextField totalpinjamanField;
    @FXML
    private TextField bungaPersenField;
    @FXML
    private TextField bungaRpField;

    @FXML
    private TextField noGadaiField;
    @FXML
    private Button addCustomerButton;
    @FXML
    private Button saveCustomerButton;
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
    private ObservableList<GadaiDetail> allDetail = FXCollections.observableArrayList();
    private Main mainApp;
    private Stage stage;
    private Stage owner;
    private List<BungaGadai> bungaGadai;
    private List<Kategori> kategori;
    private final ObservableList<String> allKategori = FXCollections.observableArrayList();

    public void initialize() {
        kodeKategoriColumn.setCellValueFactory(cellData -> cellData.getValue().kodeKategoriProperty());
        namaBarangColumn.setCellValueFactory(cellData -> cellData.getValue().namaBarangProperty());
        beratColumn.setCellValueFactory(cellData -> cellData.getValue().beratProperty());
        beratColumn.setCellFactory(col -> getTableCell(gr));
        nilaiJualColumn.setCellValueFactory(cellData -> cellData.getValue().nilaiJualProperty());
        nilaiJualColumn.setCellFactory(col -> getTableCell(rp));
        beratField.setOnKeyReleased((event) -> {
            try {
                String string = beratField.getText();
                if (string.indexOf(".") > 0) {
                    String string2 = string.substring(string.indexOf(".") + 1, string.length());
                    if (string2.contains(".")) {
                        beratField.undo();
                    } else if (!string2.equals("") && Double.parseDouble(string2) != 0) {
                        beratField.setText(gr.format(Double.parseDouble(string.replaceAll(",", ""))));
                    }
                } else {
                    beratField.setText(gr.format(Double.parseDouble(string.replaceAll(",", ""))));
                }
                beratField.end();
            } catch (Exception e) {
                beratField.undo();
            }
            getNilaiJual();
        });
        totalpinjamanField.setOnKeyReleased((event) -> {
            try {
                String string = totalpinjamanField.getText();
                if (string.indexOf(".") > 0) {
                    String string2 = string.substring(string.indexOf(".") + 1, string.length());
                    if (string2.contains(".")) {
                        totalpinjamanField.undo();
                    } else if (!string2.equals("") && Double.parseDouble(string2) != 0) {
                        totalpinjamanField.setText(gr.format(Double.parseDouble(string.replaceAll(",", ""))));
                    }
                } else {
                    totalpinjamanField.setText(gr.format(Double.parseDouble(string.replaceAll(",", ""))));
                }
                totalpinjamanField.end();
            } catch (Exception e) {
                totalpinjamanField.undo();
            }
            getBungaPersen();
        });
        bungaPersenField.setOnKeyReleased((event) -> {
            try {
                String string = bungaPersenField.getText();
                if (string.indexOf(".") > 0) {
                    String string2 = string.substring(string.indexOf(".") + 1, string.length());
                    if (string2.contains(".")) {
                        bungaPersenField.undo();
                    } else if (!string2.equals("") && Double.parseDouble(string2) != 0) {
                        bungaPersenField.setText(gr.format(Double.parseDouble(string.replaceAll(",", ""))));
                    }
                } else {
                    bungaPersenField.setText(gr.format(Double.parseDouble(string.replaceAll(",", ""))));
                }
                bungaPersenField.end();
            } catch (Exception e) {
                bungaPersenField.undo();
            }
            getBungaRp();
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
                kodeKategoriCombo.requestFocus();
            }
        });
        kodeKategoriCombo.setOnKeyPressed((KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                getBarang();
            }
        });
        namaBarangField.setOnKeyPressed((KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                beratField.selectAll();
                beratField.requestFocus();
            }
        });
        beratField.setOnKeyPressed((KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                addItemButton.fire();
            }
        });
        totalpinjamanField.setOnKeyPressed((KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                bungaPersenField.selectAll();
                bungaPersenField.requestFocus();
            }
        });
        bungaPersenField.setOnKeyPressed((KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                saveButton.fire();
            }
        });
        detailTable.setRowFactory(table -> {
            TableRow<GadaiDetail> row = new TableRow<GadaiDetail>() {
                @Override
                public void updateItem(GadaiDetail item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(null);
                    } else {
                        final ContextMenu rowMenu = new ContextMenu();
                        MenuItem hapus = new MenuItem("Hapus Barang");
                        hapus.setOnAction((ActionEvent event) -> {
                            allDetail.remove(item);
                            hitungTotal();
                            getBungaPersen();
                            detailTable.refresh();
                        });
                        rowMenu.getItems().addAll(hapus);
                        if (cancelButton.getText().equals("Cancel")) {
                            setContextMenu(rowMenu);
                        } else {
                            setContextMenu(null);
                        }
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
            kategori = KategoriDAO.getAll(con);
            for (Kategori k : kategori) {
                allKategori.add(k.getKodeKategori());
            }
            kodeKategoriCombo.setItems(allKategori);
            bungaGadai = BungaGadaiDAO.getAll(con);
        } catch (Exception e) {
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
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
    private void close() {
        mainApp.closeDialog(owner, stage);
    }

    @FXML
    private void getBungaPersen() {
        double pinjaman = Double.parseDouble(totalpinjamanField.getText().replaceAll(",", ""));
        double bunga = 0;
        for (BungaGadai b : bungaGadai) {
            if (b.getMin() <= pinjaman && pinjaman <= b.getMax()) {
                bunga = b.getBunga();
            }
        }
        bungaPersenField.setText(gr.format(bunga));
        getBungaRp();
    }

    @FXML
    private void getBungaRp() {
        double pinjaman = Double.parseDouble(totalpinjamanField.getText().replaceAll(",", ""));
        double bunga = Double.parseDouble(bungaPersenField.getText().replaceAll(",", ""));
        double bungaRp = pinjaman * bunga / 100;
        bungaRpField.setText(rp.format(Math.ceil(bungaRp / 500) * 500));
    }

    private void hitungTotal() {
        double totalBerat = 0;
        double totalNilaiJual = 0;
        for (GadaiDetail d : allDetail) {
            totalBerat = totalBerat + d.getBerat();
            totalNilaiJual = totalNilaiJual + d.getNilaiJual();
        }
        totalBeratLabel.setText(gr.format(totalBerat));
        totalNilaiJualLabel.setText(rp.format(totalNilaiJual));
        totalpinjamanField.setText(rp.format(
                Double.parseDouble(totalNilaiJualLabel.getText().replaceAll(",", "")) * 70 / 100));
    }

    @FXML
    private void getBarang() {
        if (kodeKategoriCombo.getSelectionModel().getSelectedItem() != null) {
            getNilaiJual();
            namaBarangField.selectAll();
            namaBarangField.requestFocus();
        }
    }

    @FXML
    private void getNilaiJual() {
        if (kodeKategoriCombo.getSelectionModel().getSelectedItem() != null
                && Double.parseDouble(beratField.getText().replaceAll(",", "")) >= 0) {
            double hargaJual = 0;
            Boolean status = false;
            for (Kategori k : kategori) {
                if (k.getKodeKategori().toUpperCase().equals(
                        kodeKategoriCombo.getSelectionModel().getSelectedItem().toUpperCase())) {
                    hargaJual = Double.parseDouble(beratField.getText().replaceAll(",", "")) * k.getHargaJual();
                    kodeKategoriCombo.getSelectionModel().select(
                            kodeKategoriCombo.getSelectionModel().getSelectedItem().toUpperCase());
                    status = true;
                }
            }
            if (!status) {
                kodeKategoriCombo.getSelectionModel().select(null);
            }
            nilaiJualField.setText(rp.format(hargaJual));
        }
    }

    @FXML
    private void addBarang() {
        getNilaiJual();
        if (kodeKategoriCombo.getSelectionModel().getSelectedItem() == null) {
            mainApp.showMessage(Modality.NONE, "Warning", "Kode kategori belum dipilih");
        } else if ("".equals(namaBarangField.getText())) {
            mainApp.showMessage(Modality.NONE, "Warning", "Nama barang masih kosong");
        } else if ("".equals(beratField.getText())) {
            mainApp.showMessage(Modality.NONE, "Warning", "berat masih kosong");
        } else if (Double.parseDouble(beratField.getText().replaceAll(",", "")) <= 0) {
            mainApp.showMessage(Modality.NONE, "Warning", "berat masih salah");
        } else {
            try {
                GadaiDetail d = new GadaiDetail();
                d.setKodeKategori(kodeKategoriCombo.getSelectionModel().getSelectedItem());
                d.setNamaBarang(namaBarangField.getText());
                d.setBerat(Double.parseDouble(beratField.getText().replaceAll(",", "")));
                d.setNilaiJual(Double.parseDouble(nilaiJualField.getText().replaceAll(",", "")));
                d.setNilaiJualSekarang(Double.parseDouble(nilaiJualField.getText().replaceAll(",", "")));
                allDetail.add(d);
                detailTable.refresh();
                hitungTotal();
                getBungaPersen();
                kodeKategoriCombo.getSelectionModel().select(null);
                namaBarangField.setText("");
                beratField.setText("0");
                nilaiJualField.setText("0");
                kodeKategoriCombo.requestFocus();
            } catch (Exception e) {
                mainApp.showMessage(Modality.NONE, "Error", e.toString());
            }
        }
    }

    @FXML
    private GridPane gridPane;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private HBox hBox;

    public void setGadai(GadaiHead p) {
        try {
            noGadaiLabel.setText("");
            tglGadaiLabel.setText("");
            namaField.setText("");
            alamatField.setText("");
            noTelpField.setText("");
            salesCombo.getSelectionModel().select("");
            totalBeratLabel.setText("0");
            totalNilaiJualLabel.setText("0");
            catatanField.setText("");
            totalpinjamanField.setText("0");
            bungaPersenField.setText("0");
            bungaRpField.setText("0");
            allDetail.clear();
            namaField.setDisable(true);
            alamatField.setDisable(true);
            noTelpField.setDisable(true);
            kodeKategoriCombo.setDisable(true);
            namaBarangField.setDisable(true);
            beratField.setDisable(true);
            nilaiJualField.setDisable(true);
            totalpinjamanField.setDisable(true);
            bungaPersenField.setDisable(true);
            addItemButton.setDisable(true);
            saveCustomerButton.setVisible(false);
            addCustomerButton.setVisible(false);
            printCheckBox.setVisible(false);
            catatanField.setEditable(false);
            salesCombo.setDisable(true);
            saveButton.setVisible(false);
            cancelButton.setText("Close");

            gridPane.getRowConstraints().get(6).setMinHeight(0);
            gridPane.getRowConstraints().get(6).setPrefHeight(0);
            gridPane.getRowConstraints().get(6).setMaxHeight(0);
            anchorPane.setVisible(false);

            gridPane.getRowConstraints().get(12).setMinHeight(0);
            gridPane.getRowConstraints().get(12).setPrefHeight(0);
            gridPane.getRowConstraints().get(12).setMaxHeight(0);
            hBox.setVisible(false);
            if (p != null) {
                noGadaiLabel.setText(p.getNoGadai());
                tglGadaiLabel.setText(tglLengkap.format(tglSql.parse(p.getTglGadai())));
                namaField.setText(p.getNama());
                alamatField.setText(p.getAlamat());
                noTelpField.setText(p.getNoTelp());
                allDetail.addAll(p.getAllDetail());
                hitungTotal();
                salesCombo.getSelectionModel().select(p.getKodeSales());
                totalpinjamanField.setText(rp.format(p.getTotalPinjaman()));
                bungaPersenField.setText(gr.format(p.getBungaPersen()));
                bungaRpField.setText(gr.format(p.getBungaRp()));
                catatanField.setText(p.getKeterangan());
            }
        } catch (Exception e) {
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }

    private void save() {
        try (Connection con = Koneksi.getConnection()) {
            GadaiHead p = new GadaiHead();
            p.setNoGadai(noGadaiField.getText());
            p.setTglGadai(tglSql.format(new Date()));
            p.setKodeSales(salesCombo.getSelectionModel().getSelectedItem());
            if (plg != null) {
                p.setKodePelanggan(plg.getKodePelanggan());
            } else {
                p.setKodePelanggan("");
            }
            p.setNama(namaField.getText());
            p.setAlamat(alamatField.getText());
            p.setNoTelp(noTelpField.getText());
            p.setKeterangan(catatanField.getText());
            p.setTotalBerat(Double.parseDouble(totalBeratLabel.getText().replaceAll(",", "")));
            p.setTotalPinjaman(Double.parseDouble(totalpinjamanField.getText().replaceAll(",", "")));
            p.setLamaPinjam(0);
            p.setBungaPersen(Double.parseDouble(bungaPersenField.getText().replaceAll(",", "")));
            p.setBungaKomp(0);
            p.setBungaRp(0);
            p.setKodeUser(user.getUsername());
            p.setStatus("Belum Lunas");
            p.setTglLunas("2000-01-01 00:00:00");
            p.setSalesLunas("");
            p.setUserLunas("");
            p.setTglBatal("2000-01-01 00:00:00");
            p.setSalesBatal("");
            for (GadaiDetail d : allDetail) {
                d.setGadai(p);
                d.setNoGadai(p.getNoGadai());
            }
            p.setAllDetail(allDetail);
            String status = Service.saveGadai(con, p);
            if (status.equals("true")) {
                if (printCheckBox.isSelected()) {
                    try {
                        PrintOut printOut = new PrintOut();
                        printOut.printSuratHutang(allDetail);
                        printOut.printSuratHutangInternal(allDetail);
                    } catch (Exception e) {
                        mainApp.showMessage(Modality.NONE, "Error", e.toString());
                    }
                }
                mainApp.closeDialog(owner, stage);
                mainApp.showMessage(Modality.NONE, "Success", "Terima Gadai berhasil disimpan");
            } else {
                mainApp.showMessage(Modality.NONE, "Error", status);
            }
        } catch (Exception e) {
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }

    @FXML
    private void saveGadai() {
        if (allDetail.isEmpty()) {
            mainApp.showMessage(Modality.NONE, "Warning", "Detail gadai masih kosong");
        } else if (salesCombo.getSelectionModel().getSelectedItem() == null) {
            mainApp.showMessage(Modality.NONE, "Warning", "Sales belum dipilih");
        } else if (totalpinjamanField.getText().equals("")) {
            mainApp.showMessage(Modality.NONE, "Warning", "Total pinjaman masih kosong");
        } else if (bungaPersenField.getText().equals("")) {
            mainApp.showMessage(Modality.NONE, "Warning", "Bunga persen masih kosong");
        } else {
            double maxPinjam = Double.parseDouble(totalNilaiJualLabel.getText().replaceAll(",", ""));
            double pinjaman = Double.parseDouble(totalpinjamanField.getText().replaceAll(",", ""));
            double bunga = 0;
            for (BungaGadai b : bungaGadai) {
                if (b.getMin() <= pinjaman && pinjaman <= b.getMax()) {
                    bunga = b.getBunga();
                }
            }
            if (pinjaman > maxPinjam || bunga != Double.parseDouble(bungaPersenField.getText().replaceAll(",", ""))) {
                Stage child = new Stage();
                FXMLLoader loader = mainApp.showDialog(stage, child, "View/Dialog/Verifikasi.fxml");
                VerifikasiController controller = loader.getController();
                controller.setMainApp(mainApp, stage, child);
                controller.password.setOnKeyPressed((KeyEvent keyEvent) -> {
                    if (keyEvent.getCode() == KeyCode.ENTER) {
                        mainApp.closeDialog(stage, child);
                        if (Function.verifikasi(controller.username.getText(), controller.password.getText(), "Terima Gadai")) {
                            save();
                        } else {
                            mainApp.showMessage(Modality.NONE, "Warning", "Verifikasi salah");
                        }
                    }
                });
            } else {
                save();
            }
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

}
