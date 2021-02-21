/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasGunungMas.View.Report;

import com.excellentsystem.TokoEmasGunungMas.DAO.KeuanganDAO;
import com.excellentsystem.TokoEmasGunungMas.Koneksi;
import com.excellentsystem.TokoEmasGunungMas.Main;
import static com.excellentsystem.TokoEmasGunungMas.Main.rp;
import com.excellentsystem.TokoEmasGunungMas.Model.Keuangan;
import com.excellentsystem.TokoEmasGunungMas.Omzet;
import java.sql.Connection;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.util.StringConverter;

/**
 * FXML Controller class
 *
 * @author Yunaz
 */
public class LaporanOmzetController {

    @FXML
    private TableView<Omzet> keuanganTable;
    @FXML
    private TableColumn<Omzet, String> tanggalColumn;
    @FXML
    private TableColumn<Omzet, Number> penjualanColumn;
    @FXML
    private TableColumn<Omzet, Number> pembelianColumn;
    @FXML
    private TableColumn<Omzet, Number> terimaGadaiColumn;
    @FXML
    private TableColumn<Omzet, Number> gadaiLunasColumn;
    @FXML
    private TableColumn<Omzet, Number> bungaGadaiColumn;
    @FXML
    private DatePicker tglMulaiPicker;
    @FXML
    private DatePicker tglAkhirPicker;
    @FXML
    private Label totalPenjualanLabel;
    @FXML
    private Label totalPembelianLabel;
    @FXML
    private Label totalTerimaGadaiLabel;
    @FXML
    private Label totalGadaiLunasLabel;
    @FXML
    private Label totalBungaGadaiLabel;
    private ObservableList<Omzet> allKeuangan = FXCollections.observableArrayList();
    private Main mainApp;

    public void initialize() {
        tanggalColumn.setCellValueFactory(cellData -> cellData.getValue().tanggalProperty());
        penjualanColumn.setCellValueFactory(cellData -> cellData.getValue().penjualanProperty());
        penjualanColumn.setCellFactory(col -> new TableCell<Omzet, Number>() {
            @Override
            public void updateItem(Number value, boolean empty) {
                super.updateItem(value, empty);
                if (empty) {
                    setText(null);
                } else {
                    setText(rp.format(value.doubleValue()));
                }
            }
        });
        pembelianColumn.setCellValueFactory(cellData -> cellData.getValue().pembelianProperty());
        pembelianColumn.setCellFactory(col -> new TableCell<Omzet, Number>() {
            @Override
            public void updateItem(Number value, boolean empty) {
                super.updateItem(value, empty);
                if (empty) {
                    setText(null);
                } else {
                    setText(rp.format(value.doubleValue()));
                }
            }
        });
        terimaGadaiColumn.setCellValueFactory(cellData -> cellData.getValue().terimaGadaiProperty());
        terimaGadaiColumn.setCellFactory(col -> new TableCell<Omzet, Number>() {
            @Override
            public void updateItem(Number value, boolean empty) {
                super.updateItem(value, empty);
                if (empty) {
                    setText(null);
                } else {
                    setText(rp.format(value.doubleValue()));
                }
            }
        });
        gadaiLunasColumn.setCellValueFactory(cellData -> cellData.getValue().gadaiLunasProperty());
        gadaiLunasColumn.setCellFactory(col -> new TableCell<Omzet, Number>() {
            @Override
            public void updateItem(Number value, boolean empty) {
                super.updateItem(value, empty);
                if (empty) {
                    setText(null);
                } else {
                    setText(rp.format(value.doubleValue()));
                }
            }
        });
        bungaGadaiColumn.setCellValueFactory(cellData -> cellData.getValue().bungaGadaiProperty());
        bungaGadaiColumn.setCellFactory(col -> new TableCell<Omzet, Number>() {
            @Override
            public void updateItem(Number value, boolean empty) {
                super.updateItem(value, empty);
                if (empty) {
                    setText(null);
                } else {
                    setText(rp.format(value.doubleValue()));
                }
            }
        });
        tglMulaiPicker.setConverter(new StringConverter<LocalDate>() {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy");

            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return dateFormatter.format(date);
                } else {
                    return "";
                }
            }

            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);
                } else {
                    return null;
                }
            }
        });
        tglMulaiPicker.setValue(LocalDate.parse(Main.sistem.getTglSystem(), DateTimeFormatter.ISO_DATE));
        tglMulaiPicker.setDayCellFactory((final DatePicker datePicker) -> new DateCell() {
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
        tglAkhirPicker.setConverter(new StringConverter<LocalDate>() {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy");

            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return dateFormatter.format(date);
                } else {
                    return "";
                }
            }

            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);
                } else {
                    return null;
                }
            }
        });
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
                if (item.isBefore(tglMulaiPicker.getValue())) {
                    this.setDisable(true);
                }
            }
        });
    }

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
        keuanganTable.setItems(allKeuangan);
        getKeuangan();
    }

    @FXML
    private void getKeuangan() {
        try (Connection con = Koneksi.getConnection()) {
            allKeuangan.clear();
            List<Keuangan> keuangan = KeuanganDAO.getOmzetByDate(con,
                    tglMulaiPicker.getValue().toString(),
                    tglAkhirPicker.getValue().toString());
            List<Omzet> omzet = new ArrayList<>();
            Omzet totalOmzet = new Omzet();
            for (Keuangan k : keuangan) {
                Omzet o = null;
                for (Omzet temp : omzet) {
                    if (temp.getTanggal().equals(k.getTglKeuangan())) {
                        o = temp;
                    }
                }
                if (o == null) {
                    o = new Omzet();
                    o.setTanggal(k.getTglKeuangan());
                    if (k.getKategori().equals("Penjualan")) {
                        o.setPenjualan(o.getPenjualan() + k.getJumlahRp());
                        totalOmzet.setPenjualan(totalOmzet.getPenjualan() + k.getJumlahRp());
                    } else if (k.getKategori().equals("Batal Penjualan")) {
                        o.setPenjualan(o.getPenjualan() - k.getJumlahRp());
                        totalOmzet.setPenjualan(totalOmzet.getPenjualan() - k.getJumlahRp());
                    } else if (k.getKategori().equals("Pembelian")) {
                        o.setPembelian(o.getPembelian() + k.getJumlahRp());
                        totalOmzet.setPembelian(totalOmzet.getPembelian() + k.getJumlahRp());
                    } else if (k.getKategori().equals("Batal Pembelian")) {
                        o.setPembelian(o.getPembelian() - k.getJumlahRp());
                        totalOmzet.setPembelian(totalOmzet.getPembelian() - k.getJumlahRp());
                    } else if (k.getKategori().equals("Terima Gadai")) {
                        o.setTerimaGadai(o.getTerimaGadai() + k.getJumlahRp());
                        totalOmzet.setTerimaGadai(totalOmzet.getTerimaGadai() + k.getJumlahRp());
                    } else if (k.getKategori().equals("Batal Terima Gadai")) {
                        o.setTerimaGadai(o.getTerimaGadai() - k.getJumlahRp());
                        totalOmzet.setTerimaGadai(totalOmzet.getTerimaGadai() - k.getJumlahRp());
                    } else if (k.getKategori().equals("Pelunasan Gadai")) {
                        o.setGadaiLunas(o.getGadaiLunas() + k.getJumlahRp());
                        totalOmzet.setGadaiLunas(totalOmzet.getGadaiLunas() + k.getJumlahRp());
                    } else if (k.getKategori().equals("Batal Pelunasan Gadai")) {
                        o.setGadaiLunas(o.getGadaiLunas() - k.getJumlahRp());
                        totalOmzet.setGadaiLunas(totalOmzet.getGadaiLunas() - k.getJumlahRp());
                    } else if (k.getKategori().equals("Bunga Gadai")) {
                        o.setBungaGadai(o.getBungaGadai() + k.getJumlahRp());
                        totalOmzet.setBungaGadai(totalOmzet.getBungaGadai() + k.getJumlahRp());
                    } else if (k.getKategori().equals("Batal Bunga Gadai")) {
                        o.setBungaGadai(o.getBungaGadai() - k.getJumlahRp());
                        totalOmzet.setBungaGadai(totalOmzet.getBungaGadai() - k.getJumlahRp());
                    }
                    omzet.add(o);
                } else {
                    if (k.getKategori().equals("Penjualan")) {
                        o.setPenjualan(o.getPenjualan() + k.getJumlahRp());
                        totalOmzet.setPenjualan(totalOmzet.getPenjualan() + k.getJumlahRp());
                    } else if (k.getKategori().equals("Batal Penjualan")) {
                        o.setPenjualan(o.getPenjualan() - k.getJumlahRp());
                        totalOmzet.setPenjualan(totalOmzet.getPenjualan() - k.getJumlahRp());
                    } else if (k.getKategori().equals("Pembelian")) {
                        o.setPembelian(o.getPembelian() + k.getJumlahRp());
                        totalOmzet.setPembelian(totalOmzet.getPembelian() + k.getJumlahRp());
                    } else if (k.getKategori().equals("Batal Pembelian")) {
                        o.setPembelian(o.getPembelian() - k.getJumlahRp());
                        totalOmzet.setPembelian(totalOmzet.getPembelian() - k.getJumlahRp());
                    } else if (k.getKategori().equals("Terima Gadai")) {
                        o.setTerimaGadai(o.getTerimaGadai() + k.getJumlahRp());
                        totalOmzet.setTerimaGadai(totalOmzet.getTerimaGadai() + k.getJumlahRp());
                    } else if (k.getKategori().equals("Batal Terima Gadai")) {
                        o.setTerimaGadai(o.getTerimaGadai() - k.getJumlahRp());
                        totalOmzet.setTerimaGadai(totalOmzet.getTerimaGadai() - k.getJumlahRp());
                    } else if (k.getKategori().equals("Pelunasan Gadai")) {
                        o.setGadaiLunas(o.getGadaiLunas() + k.getJumlahRp());
                        totalOmzet.setGadaiLunas(totalOmzet.getGadaiLunas() + k.getJumlahRp());
                    } else if (k.getKategori().equals("Batal Pelunasan Gadai")) {
                        o.setGadaiLunas(o.getGadaiLunas() - k.getJumlahRp());
                        totalOmzet.setGadaiLunas(totalOmzet.getGadaiLunas() - k.getJumlahRp());
                    } else if (k.getKategori().equals("Bunga Gadai")) {
                        o.setBungaGadai(o.getBungaGadai() + k.getJumlahRp());
                        totalOmzet.setBungaGadai(totalOmzet.getBungaGadai() + k.getJumlahRp());
                    } else if (k.getKategori().equals("Batal Bunga Gadai")) {
                        o.setBungaGadai(o.getBungaGadai() - k.getJumlahRp());
                        totalOmzet.setBungaGadai(totalOmzet.getBungaGadai() - k.getJumlahRp());
                    }
                }
            }
            allKeuangan.addAll(omzet);
            totalPenjualanLabel.setText(rp.format(totalOmzet.getPenjualan()));
            totalPembelianLabel.setText(rp.format(totalOmzet.getPembelian()));
            totalTerimaGadaiLabel.setText(rp.format(totalOmzet.getTerimaGadai()));
            totalGadaiLunasLabel.setText(rp.format(totalOmzet.getGadaiLunas()));
            totalBungaGadaiLabel.setText(rp.format(totalOmzet.getBungaGadai()));
        } catch (Exception e) {
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }

}
