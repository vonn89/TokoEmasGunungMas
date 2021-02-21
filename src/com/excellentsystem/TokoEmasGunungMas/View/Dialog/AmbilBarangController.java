/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasGunungMas.View.Dialog;

import com.excellentsystem.TokoEmasGunungMas.DAO.JenisDAO;
import com.excellentsystem.TokoEmasGunungMas.Function;
import static com.excellentsystem.TokoEmasGunungMas.Function.getTableCell;
import com.excellentsystem.TokoEmasGunungMas.Koneksi;
import com.excellentsystem.TokoEmasGunungMas.Main;
import static com.excellentsystem.TokoEmasGunungMas.Main.gr;
import static com.excellentsystem.TokoEmasGunungMas.Main.tglSql;
import com.excellentsystem.TokoEmasGunungMas.Model.AmbilBarangDetail;
import com.excellentsystem.TokoEmasGunungMas.Model.AmbilBarangHead;
import com.excellentsystem.TokoEmasGunungMas.Model.Jenis;
import com.excellentsystem.TokoEmasGunungMas.Service;
import java.sql.Connection;
import java.util.Date;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.StringConverter;

/**
 * FXML Controller class
 *
 * @author Xtreme
 */
public class AmbilBarangController {

    @FXML
    private TableView<AmbilBarangDetail> ambilBarangDetailTable;
    @FXML
    private TableColumn<AmbilBarangDetail, String> kodeKategoriColumn;
    @FXML
    private TableColumn<AmbilBarangDetail, String> kodeJenisColumn;
    @FXML
    private TableColumn<AmbilBarangDetail, String> namaJenisColumn;
    @FXML
    private TableColumn<AmbilBarangDetail, Number> beratColumn;
    @FXML
    private TableColumn<AmbilBarangDetail, Number> qtyColumn;

    @FXML
    private TextArea keteranganField;
    @FXML
    private ComboBox<Jenis> kodeJenisCombo;
    @FXML
    private TextField qtyField;
    @FXML
    private TextField beratField;
    @FXML
    private Label totalQty;
    @FXML
    private Label totalBerat;
    private Main mainApp;
    private Stage stage;
    private Stage owner;
    private ObservableList<Jenis> allJenis = FXCollections.observableArrayList();
    private ObservableList<AmbilBarangDetail> allDetail = FXCollections.observableArrayList();

    public void initialize() {
        kodeKategoriColumn.setCellValueFactory(cellData -> cellData.getValue().kodeKategoriProperty());
        kodeJenisColumn.setCellValueFactory(cellData -> cellData.getValue().kodeJenisProperty());
        namaJenisColumn.setCellValueFactory(cellData -> cellData.getValue().getJenis().namaJenisProperty());
        beratColumn.setCellValueFactory(cellData -> cellData.getValue().beratProperty());
        beratColumn.setCellFactory(col -> getTableCell(gr));
        qtyColumn.setCellValueFactory(cellData -> cellData.getValue().qtyProperty());
        qtyColumn.setCellFactory(col -> getTableCell(gr));
        Function.setRpField(qtyField);
        Function.setBeratField(beratField);
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
        kodeJenisCombo.setOnKeyPressed((KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                qtyField.selectAll();
            }
        });
        qtyField.setOnKeyPressed((KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                beratField.selectAll();
            }
        });
        beratField.setOnKeyPressed((KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                addBarang();
            }
        });
        final ContextMenu rowMenu = new ContextMenu();
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent e) -> {
            ambilBarangDetailTable.refresh();
        });
        rowMenu.getItems().addAll(refresh);
        ambilBarangDetailTable.setContextMenu(rowMenu);
        ambilBarangDetailTable.setRowFactory(table -> {
            TableRow<AmbilBarangDetail> row = new TableRow<AmbilBarangDetail>() {
                @Override
                public void updateItem(AmbilBarangDetail item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(rowMenu);
                    } else {
                        final ContextMenu rowMenu = new ContextMenu();
                        MenuItem delete = new MenuItem("Hapus Barang");
                        delete.setOnAction((ActionEvent e) -> {
                            allDetail.remove(item);
                            hitungTotal();
                            ambilBarangDetailTable.refresh();
                        });
                        MenuItem refresh = new MenuItem("Refresh");
                        refresh.setOnAction((ActionEvent e) -> {
                            ambilBarangDetailTable.refresh();
                        });

                        rowMenu.getItems().addAll(delete, refresh);
                        setContextMenu(rowMenu);
                    }
                }
            };
            return row;
        });
        kodeJenisCombo.setItems(allJenis);
        ambilBarangDetailTable.setItems(allDetail);
    }

    public void setMainApp(Main mainApp, Stage owner, Stage stage) {
        try (Connection con = Koneksi.getConnection()) {
            this.mainApp = mainApp;
            this.stage = stage;
            this.owner = owner;
            allJenis.addAll(JenisDAO.getAll(con));
            stage.setOnCloseRequest((event) -> {
                mainApp.closeDialog(owner, stage);
            });
            stage.setHeight(mainApp.screenSize.getHeight() - 80);
            stage.setWidth(mainApp.screenSize.getWidth() - 80);
            stage.setX((mainApp.screenSize.getWidth() - stage.getWidth()) / 2);
            stage.setY((mainApp.screenSize.getHeight() - stage.getHeight()) / 2);
        } catch (Exception e) {
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }

    @FXML
    private void addBarang() {
        if (kodeJenisCombo.getSelectionModel().getSelectedItem() == null) {
            mainApp.showMessage(Modality.NONE, "Warning", "Kode Jenis belum dipilih");
        } else if (kodeJenisCombo.getSelectionModel().getSelectedItem().getKodeJenis() == null) {
            mainApp.showMessage(Modality.NONE, "Warning", "Kode jenis yang dipilih masih salah");
        } else if (qtyField.getText().equals("")) {
            mainApp.showMessage(Modality.NONE, "Warning", "Qty masih kosong");
        } else if (beratField.getText().equals("")) {
            mainApp.showMessage(Modality.NONE, "Warning", "Berat masih kosong");
        } else {
            AmbilBarangDetail d = new AmbilBarangDetail();
            d.setKodeKategori(kodeJenisCombo.getSelectionModel().getSelectedItem().getKodeKategori());
            d.setKodeJenis(kodeJenisCombo.getSelectionModel().getSelectedItem().getKodeJenis());
            d.setJenis(kodeJenisCombo.getSelectionModel().getSelectedItem());
            d.setQty(Integer.parseInt(qtyField.getText().replaceAll(",", "")));
            d.setBerat(Double.parseDouble(beratField.getText().replaceAll(",", "")));
            allDetail.add(d);
            hitungTotal();
            kodeJenisCombo.getSelectionModel().select(null);
            qtyField.setText("");
            beratField.setText("");
            kodeJenisCombo.requestFocus();
        }
    }

    private void hitungTotal() {
        int qty = 0;
        double berat = 0;
        for (AmbilBarangDetail d : allDetail) {
            qty = qty + d.getQty();
            berat = berat + d.getBerat();
        }
        totalQty.setText(gr.format(qty));
        totalBerat.setText(gr.format(berat));
    }

    @FXML
    private void cancel() {
        mainApp.closeDialog(owner, stage);
    }

    @FXML
    private void save() {
        try {
            if (allDetail.isEmpty()) {
                mainApp.showMessage(Modality.NONE, "Warning", "Detail barang masih kosong");
            } else {
                Stage child = new Stage();
                FXMLLoader loader = mainApp.showDialog(stage, child, "View/Dialog/Verifikasi.fxml");
                VerifikasiController c = loader.getController();
                c.setMainApp(mainApp, stage, child);
                c.password.setOnKeyPressed((KeyEvent keyEvent) -> {
                    if (keyEvent.getCode() == KeyCode.ENTER) {
                        mainApp.closeDialog(stage, child);
                        if (Function.verifikasi(c.username.getText(), c.password.getText(), "Ambil Barang")) {
                            try (Connection con = Koneksi.getConnection()) {
                                AmbilBarangHead ab = new AmbilBarangHead();
                                ab.setTglAmbil(tglSql.format(new Date()));
                                ab.setKeterangan(keteranganField.getText());
                                ab.setTotalQty(Integer.parseInt(totalQty.getText().replaceAll(",", "")));
                                ab.setTotalBerat(Double.parseDouble(totalBerat.getText().replaceAll(",", "")));
                                ab.setKodeUser(c.username.getText());
                                ab.setAllDetail(allDetail);
                                String status = Service.saveAmbilBarang(con, ab);
                                if (status.equals("true")) {
                                    mainApp.showMessage(Modality.NONE, "Success", "Pengambilan Stok Barang berhasil disimpan");
                                    mainApp.closeDialog(owner, stage);
                                    mainApp.showDataBarangNonBarcode();
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

            }
        } catch (Exception e) {
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }
}
