/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasGunungMas.View.Dialog;

import com.excellentsystem.TokoEmasGunungMas.DAO.BungaGadaiDAO;
import com.excellentsystem.TokoEmasGunungMas.DAO.GadaiDetailDAO;
import com.excellentsystem.TokoEmasGunungMas.DAO.GadaiHeadDAO;
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
import com.excellentsystem.TokoEmasGunungMas.Model.Sales;
import com.excellentsystem.TokoEmasGunungMas.PrintOut.PrintOut;
import com.excellentsystem.TokoEmasGunungMas.Service;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author Yunaz
 */
public class DetailPelunasanGadaiController {

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
    private TableColumn<GadaiDetail, Number> nilaiJualSekarangColumn;

    @FXML
    public TextField noGadaiField;
    @FXML
    private TextField tglGadaiField;
    @FXML
    private TextField tglLunasField;
    @FXML
    private TextField namaField;
    @FXML
    private TextField alamatField;
    @FXML
    private TextField noTelpField;

    @FXML
    private TextField totalpinjamanField;
    @FXML
    private TextField bungaPersenField;
    @FXML
    private TextField bungaRpField;
    @FXML
    private ComboBox<String> salesCombo;
    @FXML
    private CheckBox printCheckBox;

    @FXML
    private TextField pembayaranPinjamanField;
    @FXML
    private TextField pembayaranBungaField;
    @FXML
    private TextField sisaPinjamanField;
    @FXML
    private TextField bungaPersenBaruField;
    @FXML
    private TextField bungaRpBaruField;
    @FXML
    private TextField totalJumlahDiterimaField;
    @FXML
    private TextField noGadaiBaruField;

    @FXML
    private Button searchButton;
    @FXML
    private Button saveButton;
    @FXML
    private Button cancelButton;

    private GadaiHead g = null;
    private ObservableList<GadaiDetail> allDetail = FXCollections.observableArrayList();
    private Main mainApp;
    private Stage stage;
    private Stage owner;
    private List<BungaGadai> bungaGadai;

    public void initialize() {
        kodeKategoriColumn.setCellValueFactory(cellData -> cellData.getValue().kodeKategoriProperty());
        namaBarangColumn.setCellValueFactory(cellData -> cellData.getValue().namaBarangProperty());
        beratColumn.setCellValueFactory(cellData -> cellData.getValue().beratProperty());
        beratColumn.setCellFactory(col -> getTableCell(gr));
        nilaiJualColumn.setCellValueFactory(cellData -> cellData.getValue().nilaiJualProperty());
        nilaiJualColumn.setCellFactory(col -> getTableCell(gr));
        nilaiJualSekarangColumn.setCellValueFactory(cellData -> cellData.getValue().nilaiJualSekarangProperty());
        nilaiJualSekarangColumn.setCellFactory(col -> getTableCell(gr));
        pembayaranPinjamanField.setOnKeyReleased((event) -> {
            try {
                String string = pembayaranPinjamanField.getText();
                if (string.indexOf(".") > 0) {
                    String string2 = string.substring(string.indexOf(".") + 1, string.length());
                    if (string2.contains(".")) {
                        pembayaranPinjamanField.undo();
                    } else if (!string2.equals("") && Double.parseDouble(string2) != 0) {
                        pembayaranPinjamanField.setText(gr.format(Double.parseDouble(string.replaceAll(",", ""))));
                    }
                } else {
                    pembayaranPinjamanField.setText(gr.format(Double.parseDouble(string.replaceAll(",", ""))));
                }
                pembayaranPinjamanField.end();
            } catch (Exception e) {
                pembayaranPinjamanField.undo();
            }
            hitung();
        });
        pembayaranBungaField.setOnKeyReleased((event) -> {
            try {
                String string = pembayaranBungaField.getText();
                if (string.indexOf(".") > 0) {
                    String string2 = string.substring(string.indexOf(".") + 1, string.length());
                    if (string2.contains(".")) {
                        pembayaranBungaField.undo();
                    } else if (!string2.equals("") && Double.parseDouble(string2) != 0) {
                        pembayaranBungaField.setText(gr.format(Double.parseDouble(string.replaceAll(",", ""))));
                    }
                } else {
                    pembayaranBungaField.setText(gr.format(Double.parseDouble(string.replaceAll(",", ""))));
                }
                pembayaranBungaField.end();
            } catch (Exception e) {
                pembayaranBungaField.undo();
            }
            hitung();
        });
        bungaPersenBaruField.setOnKeyReleased((event) -> {
            try {
                String string = bungaPersenBaruField.getText();
                if (string.indexOf(".") > 0) {
                    String string2 = string.substring(string.indexOf(".") + 1, string.length());
                    if (string2.contains(".")) {
                        bungaPersenBaruField.undo();
                    } else if (!string2.equals("") && Double.parseDouble(string2) != 0) {
                        bungaPersenBaruField.setText(gr.format(Double.parseDouble(string.replaceAll(",", ""))));
                    }
                } else {
                    bungaPersenBaruField.setText(gr.format(Double.parseDouble(string.replaceAll(",", ""))));
                }
                bungaPersenBaruField.end();
            } catch (Exception e) {
                bungaPersenBaruField.undo();
            }
            getBungaRp();
        });
        pembayaranPinjamanField.setOnKeyPressed((KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                pembayaranBungaField.selectAll();
                pembayaranBungaField.requestFocus();
            }
        });
        pembayaranBungaField.setOnKeyPressed((KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                bungaPersenBaruField.selectAll();
                bungaPersenBaruField.requestFocus();
            }
        });
        bungaPersenBaruField.setOnKeyPressed((KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                salesCombo.requestFocus();
            }
        });
        salesCombo.setOnKeyPressed((KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                saveButton.fire();
            }
        });
        noGadaiField.setOnKeyPressed((KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                searchButton.fire();
            }
        });
        searchButton.setOnAction((event) -> {
            getGadai();
            setPembayaran();
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
            bungaGadai = BungaGadaiDAO.getAll(con);
        } catch (Exception e) {
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }

    private void bindToTime() {
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(1), (ActionEvent e) -> {
                    tglLunasField.setText(new SimpleDateFormat("dd MMMMM yyyy  hh:mm:ss").format(new Date()));
                })
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    public void getGadai() {
        try (Connection con = Koneksi.getConnection()) {
            g = null;
            allDetail.clear();
            tglGadaiField.setText("");
            namaField.setText("");
            alamatField.setText("");
            noTelpField.setText("");
            totalpinjamanField.setText("0");
            bungaPersenField.setText("0");
            bungaRpField.setText("0");
            g = GadaiHeadDAO.get(con, noGadaiField.getText());
            if (g == null) {
                mainApp.showMessage(Modality.NONE, "Warning", "No Gadai tidak ditemukan");
            } else if (g.getStatus().equals("Lunas")) {
                mainApp.showMessage(Modality.NONE, "Warning", "Gadai sudah pernah dilunasi");
            } else if (g.getStatus().equals("Batal Gadai")) {
                mainApp.showMessage(Modality.NONE, "Warning", "Gadai sudah pernah dibatalkan");
            } else {
                allDetail.addAll(GadaiDetailDAO.getAllByNoGadai(con, g.getNoGadai()));
                noGadaiField.setText(g.getNoGadai());
                tglGadaiField.setText(g.getTglGadai());
                bindToTime();
                namaField.setText(g.getNama());
                alamatField.setText(g.getAlamat());
                noTelpField.setText(g.getNoTelp());
                totalpinjamanField.setText(rp.format(g.getTotalPinjaman()));
                bungaPersenField.setText(rp.format(g.getBungaPersen()));
                bungaRpField.setText(rp.format(g.getBungaKomp()));
            }
        } catch (Exception e) {
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }

    public void setPembayaran() {
        if (!noGadaiField.getText().equals("")) {
            pembayaranPinjamanField.setText("0");
            pembayaranBungaField.setText("0");
            sisaPinjamanField.setText("0");
            bungaPersenBaruField.setText("0");
            bungaRpBaruField.setText("0");
            totalJumlahDiterimaField.setText("0");
            pembayaranPinjamanField.setText(rp.format(g.getTotalPinjaman()));
            pembayaranBungaField.setText(rp.format(g.getBungaKomp()));
            totalJumlahDiterimaField.setText(rp.format(g.getTotalPinjaman() + g.getBungaKomp()));
        }
    }

    @FXML
    private void close() {
        mainApp.closeDialog(owner, stage);
    }

    private void getBungaPersen() {
        double pinjaman = Double.parseDouble(sisaPinjamanField.getText().replaceAll(",", ""));
        double bunga = 0;
        for (BungaGadai b : bungaGadai) {
            if (b.getMin() <= pinjaman && pinjaman <= b.getMax()) {
                bunga = b.getBunga();
            }
        }
        bungaPersenBaruField.setText(gr.format(bunga));
        getBungaRp();
    }

    @FXML
    private void getBungaRp() {
        double pinjaman = Double.parseDouble(sisaPinjamanField.getText().replaceAll(",", ""));
        double bunga = Double.parseDouble(bungaPersenBaruField.getText().replaceAll(",", ""));
        double bungaRp = pinjaman * bunga / 100;
        bungaRpBaruField.setText(rp.format(Math.ceil(bungaRp / 500) * 500));
    }

    @FXML
    private void hitung() {
        double totalPinjaman = Double.parseDouble(totalpinjamanField.getText().replaceAll(",", ""));
        double bayarPinjaman = Double.parseDouble(pembayaranPinjamanField.getText().replaceAll(",", ""));
        double bayarBunga = Double.parseDouble(pembayaranBungaField.getText().replaceAll(",", ""));
        sisaPinjamanField.setText(rp.format(totalPinjaman - bayarPinjaman));
        totalJumlahDiterimaField.setText(rp.format(bayarPinjaman + bayarBunga));
        getBungaPersen();
    }
    @FXML private GridPane gridPane;
    @FXML private HBox hBox;

    public void viewGadai(GadaiHead p) {
        try {
            g = null;
            allDetail.clear();
            noGadaiField.setText("");
            tglGadaiField.setText("");
            tglLunasField.setText("");
            namaField.setText("");
            alamatField.setText("");
            noTelpField.setText("");
            totalpinjamanField.setText("0");
            bungaPersenField.setText("0");
            bungaRpField.setText("0");
            salesCombo.getSelectionModel().select(null);
            pembayaranPinjamanField.setText("0");
            pembayaranBungaField.setText("0");
            sisaPinjamanField.setText("0");
            bungaPersenBaruField.setText("0");
            bungaRpBaruField.setText("0");
            totalJumlahDiterimaField.setText("0");
            noGadaiField.setDisable(true);
            searchButton.setVisible(false);
            pembayaranBungaField.setDisable(true);
            pembayaranPinjamanField.setDisable(true);
            bungaPersenBaruField.setDisable(true);
            salesCombo.setDisable(true);
            printCheckBox.setVisible(false);
            saveButton.setVisible(false);
            cancelButton.setText("Close");
            
            gridPane.getRowConstraints().get(11).setMinHeight(0);
            gridPane.getRowConstraints().get(11).setPrefHeight(0);
            gridPane.getRowConstraints().get(11).setMaxHeight(0);
            hBox.setVisible(false);
            
            if (p != null) {
                noGadaiField.setText(p.getNoGadai());
                tglGadaiField.setText(tglLengkap.format(tglSql.parse(p.getTglGadai())));
                tglLunasField.setText(tglLengkap.format(tglSql.parse(p.getTglLunas())));
                namaField.setText(p.getNama());
                alamatField.setText(p.getAlamat());
                noTelpField.setText(p.getNoTelp());
                allDetail.addAll(p.getAllDetail());
                totalpinjamanField.setText(rp.format(p.getTotalPinjaman()));
                bungaPersenField.setText(gr.format(p.getBungaPersen()));
                bungaRpField.setText(gr.format(p.getBungaKomp()));
                salesCombo.getSelectionModel().select(p.getSalesLunas());
                pembayaranBungaField.setText(rp.format(p.getBungaRp()));
                pembayaranPinjamanField.setText(rp.format(p.getTotalPinjaman()));
                sisaPinjamanField.setText(rp.format(0));
                bungaPersenBaruField.setText(gr.format(0));
                bungaRpBaruField.setText(rp.format(0));
                totalJumlahDiterimaField.setText(rp.format(p.getTotalPinjaman() + p.getBungaRp()));
            }
        } catch (Exception e) {
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }

    private void save(double bayarPinjaman, double bayarBunga, double sisaPinjaman, double bungaBaru) {
        try (Connection con = Koneksi.getConnection()) {
            g.setTglLunas(tglSql.format(new Date()));
            g.setSalesLunas(salesCombo.getSelectionModel().getSelectedItem());
            g.setUserLunas(user.getUsername());
            g.setBungaRp(bayarBunga);
            g.setStatus("Lunas");
            g.setAllDetail(allDetail);

            GadaiHead gadaiBaru = null;
            if (sisaPinjaman > 0) {
                gadaiBaru = new GadaiHead();
                gadaiBaru.setNoGadai(noGadaiBaruField.getText());
                gadaiBaru.setTglGadai(g.getTglLunas());
                gadaiBaru.setKodeSales(g.getSalesLunas());
                gadaiBaru.setKodePelanggan(g.getKodePelanggan());
                gadaiBaru.setNama(g.getNama());
                gadaiBaru.setAlamat(g.getAlamat());
                gadaiBaru.setNoTelp(g.getNoTelp());
                gadaiBaru.setKeterangan("No Hutang Lama : " + g.getNoGadai() + "\n"
                        + "Pinjaman : " + rp.format(g.getTotalPinjaman()) + "\n"
                        + "Cicil Pinjaman : " + rp.format(bayarPinjaman) + "\n"
                        + "Bunga Dibayar : " + rp.format(bayarBunga));
                gadaiBaru.setTotalBerat(g.getTotalBerat());
                gadaiBaru.setTotalPinjaman(sisaPinjaman);
                gadaiBaru.setLamaPinjam(0);
                gadaiBaru.setBungaPersen(bungaBaru);
                gadaiBaru.setBungaKomp(0);
                gadaiBaru.setBungaRp(0);
                gadaiBaru.setStatus("Belum Lunas");
                gadaiBaru.setKodeUser(user.getUsername());
                gadaiBaru.setTglLunas("2000-01-01 00:00:00");
                gadaiBaru.setSalesLunas("");
                gadaiBaru.setUserLunas("");
                gadaiBaru.setTglBatal("2000-01-01 00:00:00");
                gadaiBaru.setSalesBatal("");
            }

            String status = Service.savePelunasanGadai(con, g, gadaiBaru);
            if (status.equals("true")) {
                if (gadaiBaru != null) {
                    if (printCheckBox.isSelected()) {
                        try {
                            for (GadaiDetail d : allDetail) {
                                d.setNoGadai(gadaiBaru.getNoGadai());
                                d.setGadai(gadaiBaru);
                            }
                            PrintOut printOut = new PrintOut();
                            printOut.printSuratHutang(allDetail);
                            printOut.printSuratHutangInternal(allDetail);
                        } catch (Exception e) {
                            mainApp.showMessage(Modality.NONE, "Error", e.toString());
                        }
                    }
                    mainApp.closeDialog(owner, stage);
                    mainApp.showDataTerimaGadai();
                    mainApp.showMessage(Modality.NONE, "Success", "Bayar bunga/cicil gadai berhasil disimpan");
                } else {
                    mainApp.closeDialog(owner, stage);
                    mainApp.showDataPelunasanGadai();
                    mainApp.showMessage(Modality.NONE, "Success", "Pelunasan gadai berhasil disimpan");
                }
            } else {
                mainApp.showMessage(Modality.NONE, "Error", status);
            }
        } catch (Exception e) {
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }

    @FXML
    private void saveGadai() {
        getGadai();
        if (g == null) {
            mainApp.showMessage(Modality.NONE, "Warning", "Gadai belum dipilih");
        } else if (salesCombo.getSelectionModel().getSelectedItem() == null) {
            mainApp.showMessage(Modality.NONE, "Warning", "Sales belum dipilih");
        } else if (pembayaranPinjamanField.getText().equals("")) {
            mainApp.showMessage(Modality.NONE, "Warning", "Pembayaran pinjaman masih kosong");
        } else if (pembayaranBungaField.getText().equals("")) {
            mainApp.showMessage(Modality.NONE, "Warning", "Pembayaran bunga masih kosong");
        } else if (bungaPersenBaruField.getText().equals("")) {
            mainApp.showMessage(Modality.NONE, "Warning", "Bunga persen masih kosong");
        } else if (Double.parseDouble(pembayaranPinjamanField.getText().replaceAll(",", ""))
                > Double.parseDouble(totalpinjamanField.getText().replaceAll(",", ""))) {
            mainApp.showMessage(Modality.NONE, "Warning", "Pembayaran pinjaman melebihi total pinjaman");
        } else {
            double bungaKomp = Double.parseDouble(bungaRpField.getText().replaceAll(",", ""));
            double bayarBunga = Double.parseDouble(pembayaranBungaField.getText().replaceAll(",", ""));
            double bayarPinjaman = Double.parseDouble(pembayaranPinjamanField.getText().replaceAll(",", ""));
            double sisaPinjaman = Double.parseDouble(sisaPinjamanField.getText().replaceAll(",", ""));
            double bungaBaru = Double.parseDouble(bungaPersenBaruField.getText().replaceAll(",", ""));
            double bunga = 0;
            for (BungaGadai b : bungaGadai) {
                if (b.getMin() <= sisaPinjaman && sisaPinjaman <= b.getMax()) {
                    bunga = b.getBunga();
                }
            }
            if (bayarBunga < bungaKomp || bunga != bungaBaru) {
                Stage child = new Stage();
                FXMLLoader loader = mainApp.showDialog(stage, child, "View/Dialog/Verifikasi.fxml");
                VerifikasiController controller = loader.getController();
                controller.setMainApp(mainApp, stage, child);
                controller.password.setOnKeyPressed((KeyEvent keyEvent) -> {
                    if (keyEvent.getCode() == KeyCode.ENTER) {
                        mainApp.closeDialog(stage, child);
                        if (Function.verifikasi(controller.username.getText(), controller.password.getText(), "Pelunasan Gadai")) {
                            save(bayarPinjaman, bayarBunga, sisaPinjaman, bungaBaru);
                        } else {
                            mainApp.showMessage(Modality.NONE, "Warning", "Verifikasi salah");
                        }
                    }
                });
            } else {
                save(bayarPinjaman, bayarBunga, sisaPinjaman, bungaBaru);
            }
        }
    }

}
