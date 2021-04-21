/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasGunungMas.View.Report;

import com.excellentsystem.TokoEmasGunungMas.DAO.BarangDAO;
import com.excellentsystem.TokoEmasGunungMas.DAO.PenjualanDetailDAO;
import com.excellentsystem.TokoEmasGunungMas.DAO.PenjualanHeadDAO;
import com.excellentsystem.TokoEmasGunungMas.Function;
import static com.excellentsystem.TokoEmasGunungMas.Function.getTreeTableCell;
import com.excellentsystem.TokoEmasGunungMas.Koneksi;
import com.excellentsystem.TokoEmasGunungMas.Main;
import static com.excellentsystem.TokoEmasGunungMas.Main.gr;
import static com.excellentsystem.TokoEmasGunungMas.Main.rp;
import static com.excellentsystem.TokoEmasGunungMas.Main.tglLengkap;
import static com.excellentsystem.TokoEmasGunungMas.Main.tglSql;
import com.excellentsystem.TokoEmasGunungMas.Model.Barang;
import com.excellentsystem.TokoEmasGunungMas.Model.PenjualanDetail;
import com.excellentsystem.TokoEmasGunungMas.Model.PenjualanHead;
import com.excellentsystem.TokoEmasGunungMas.PrintOut.PrintOut;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableRow;
import javafx.scene.control.TreeTableView;
import javafx.stage.Modality;

/**
 * FXML Controller class
 *
 * @author Yunaz
 */
public class LaporanBatalJualController {

    @FXML
    private TreeTableView<PenjualanDetail> penjualanTable;
    @FXML
    private TreeTableColumn<PenjualanDetail, String> noPenjualanColumn;
    @FXML
    private TreeTableColumn<PenjualanDetail, String> tglPenjualanColumn;
    @FXML
    private TreeTableColumn<PenjualanDetail, String> tglBatalColumn;
    @FXML
    private TreeTableColumn<PenjualanDetail, String> userBatalColumn;
    @FXML
    private TreeTableColumn<PenjualanDetail, String> kodeSalesColumn;
    @FXML
    private TreeTableColumn<PenjualanDetail, String> kodePelangganColumn;
    @FXML
    private TreeTableColumn<PenjualanDetail, String> namaColumn;
    @FXML
    private TreeTableColumn<PenjualanDetail, String> alamatColumn;
    @FXML
    private TreeTableColumn<PenjualanDetail, String> noTelpColumn;
    @FXML
    private TreeTableColumn<PenjualanDetail, Number> totalBeratColumn;
    @FXML
    private TreeTableColumn<PenjualanDetail, Number> grandtotalColumn;
    @FXML
    private TreeTableColumn<PenjualanDetail, String> catatanColumn;

    @FXML
    private TreeTableColumn<PenjualanDetail, String> kodeBarcodeColumn;
    @FXML
    private TreeTableColumn<PenjualanDetail, String> namaBarangColumn;
    @FXML
    private TreeTableColumn<PenjualanDetail, String> keteranganColumn;
    @FXML
    private TreeTableColumn<PenjualanDetail, String> kodeKategoriColumn;
    @FXML
    private TreeTableColumn<PenjualanDetail, String> kodeJenisColumn;
    @FXML
    private TreeTableColumn<PenjualanDetail, String> kodeGudangColumn;
    @FXML
    private TreeTableColumn<PenjualanDetail, String> kodeInternColumn;
    @FXML
    private TreeTableColumn<PenjualanDetail, String> kadarColumn;
    @FXML
    private TreeTableColumn<PenjualanDetail, Number> beratColumn;
    @FXML
    private TreeTableColumn<PenjualanDetail, Number> beratAsliColumn;
    @FXML
    private TreeTableColumn<PenjualanDetail, Number> nilaiPokokColumn;
    @FXML
    private TreeTableColumn<PenjualanDetail, Number> hargaKompColumn;
    @FXML
    private TreeTableColumn<PenjualanDetail, Number> hargaJualColumn;
    @FXML
    private TreeTableColumn<PenjualanDetail, String> userBarcodeColumn;
    @FXML
    private TreeTableColumn<PenjualanDetail, String> tglBarcodeColumn;
    @FXML
    private TextField searchField;
    @FXML
    private Label totalBeratField;
    @FXML
    private Label totalNilaiPokokField;
    @FXML
    private Label totalPenjualanField;
    @FXML
    private DatePicker mulaiTglPicker;
    @FXML
    private DatePicker akhirTglPicker;
    final TreeItem<PenjualanDetail> root = new TreeItem<>();
    private ObservableList<PenjualanDetail> allPenjualan = FXCollections.observableArrayList();
    private ObservableList<PenjualanDetail> filterData = FXCollections.observableArrayList();
    private Main mainApp;

    public void initialize() {
        noPenjualanColumn.setCellValueFactory(param -> param.getValue().getValue().noPenjualanProperty());
        
        tglPenjualanColumn.setCellValueFactory(cellData -> {
            try {
                return new SimpleStringProperty(
                        tglLengkap.format(
                                tglSql.parse(cellData.getValue().getValue().getPenjualan().getTglPenjualan())));
            } catch (Exception ex) {
                return null;
            }
        });
        tglPenjualanColumn.setComparator(Function.sortDate(tglLengkap));
        
        tglBatalColumn.setCellValueFactory(cellData -> {
            try {
                return new SimpleStringProperty(
                        tglLengkap.format(
                                tglSql.parse(cellData.getValue().getValue().getPenjualan().getTglBatal())));
            } catch (Exception ex) {
                return null;
            }
        });
        tglBatalColumn.setComparator(Function.sortDate(tglLengkap));
        
        userBatalColumn.setCellValueFactory(param -> param.getValue().getValue().getPenjualan().userBatalProperty());
        
        kodeSalesColumn.setCellValueFactory(param -> param.getValue().getValue().getPenjualan().kodeSalesProperty());
        
        kodePelangganColumn.setCellValueFactory(param -> param.getValue().getValue().getPenjualan().kodePelangganProperty());
        
        namaColumn.setCellValueFactory(param -> param.getValue().getValue().getPenjualan().namaProperty());
        
        alamatColumn.setCellValueFactory(param -> param.getValue().getValue().getPenjualan().alamatProperty());
        
        noTelpColumn.setCellValueFactory(param -> param.getValue().getValue().getPenjualan().noTelpProperty());
        
        totalBeratColumn.setCellValueFactory(param -> param.getValue().getValue().getPenjualan().totalBeratProperty());
        totalBeratColumn.setCellFactory(col -> getTreeTableCell(gr));
        
        grandtotalColumn.setCellValueFactory(param -> param.getValue().getValue().getPenjualan().grandtotalProperty());
        grandtotalColumn.setCellFactory(col -> getTreeTableCell(rp));
        
        catatanColumn.setCellValueFactory(param -> param.getValue().getValue().getPenjualan().catatanProperty());

        kodeBarcodeColumn.setCellValueFactory(param -> param.getValue().getValue().getBarang().kodeBarcodeProperty());
        
        namaBarangColumn.setCellValueFactory(param -> param.getValue().getValue().getBarang().namaBarangProperty());
        
        keteranganColumn.setCellValueFactory(param -> param.getValue().getValue().getBarang().keteranganProperty());
        
        kodeKategoriColumn.setCellValueFactory(param -> param.getValue().getValue().getBarang().kodeKategoriProperty());
        
        kodeJenisColumn.setCellValueFactory(param -> param.getValue().getValue().getBarang().kodeJenisProperty());
        
        kodeGudangColumn.setCellValueFactory(param -> param.getValue().getValue().getBarang().kodeGudangProperty());
        
        kodeInternColumn.setCellValueFactory(param -> param.getValue().getValue().getBarang().kodeInternProperty());
        
        kadarColumn.setCellValueFactory(param -> param.getValue().getValue().getBarang().kadarProperty());
        
        beratColumn.setCellValueFactory(param -> param.getValue().getValue().getBarang().beratProperty());
        beratColumn.setCellFactory(col -> getTreeTableCell(gr));
        
        beratAsliColumn.setCellValueFactory(param -> param.getValue().getValue().getBarang().beratAsliProperty());
        beratAsliColumn.setCellFactory(col -> getTreeTableCell(gr));
        
        nilaiPokokColumn.setCellValueFactory(param -> param.getValue().getValue().getBarang().nilaiPokokProperty());
        nilaiPokokColumn.setCellFactory(col -> getTreeTableCell(rp));
        
        hargaKompColumn.setCellValueFactory(param -> param.getValue().getValue().hargaKompProperty());
        hargaKompColumn.setCellFactory(col -> getTreeTableCell(rp));
        
        hargaJualColumn.setCellValueFactory(param -> param.getValue().getValue().hargaJualProperty());
        hargaJualColumn.setCellFactory(col -> getTreeTableCell(rp));
        
        userBarcodeColumn.setCellValueFactory(param -> param.getValue().getValue().getBarang().barcodeByProperty());
        
        tglBarcodeColumn.setCellValueFactory(cellData -> {
            try {
                return new SimpleStringProperty(
                        tglLengkap.format(
                                tglSql.parse(cellData.getValue().getValue().getBarang().getBarcodeDate())));
            } catch (Exception ex) {
                return null;
            }
        });
        tglBarcodeColumn.setComparator(Function.sortDate(tglLengkap));
        
        mulaiTglPicker.setConverter(Function.getTglConverter());
        mulaiTglPicker.setValue(LocalDate.now());
        mulaiTglPicker.setDayCellFactory((final DatePicker datePicker) -> Function.getDateCellMulai(akhirTglPicker));
        akhirTglPicker.setConverter(Function.getTglConverter());
        akhirTglPicker.setValue(LocalDate.now());
        akhirTglPicker.setDayCellFactory((final DatePicker datePicker) -> Function.getDateCellAkhir(mulaiTglPicker));
        
        final ContextMenu rowMenu = new ContextMenu();
        MenuItem cetak = new MenuItem("Print Laporan");
        cetak.setOnAction((ActionEvent e) -> {
            printLaporan();
        });
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent event) -> {
            getPenjualan();
        });
        rowMenu.getItems().addAll(cetak, refresh);
        penjualanTable.setContextMenu(rowMenu);
        penjualanTable.setRowFactory(table -> {
            TreeTableRow<PenjualanDetail> row = new TreeTableRow<PenjualanDetail>() {
                @Override
                public void updateItem(PenjualanDetail item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(rowMenu);
                    } else {
                        final ContextMenu rowMenu = new ContextMenu();
                        MenuItem cetak = new MenuItem("Print Laporan");
                        cetak.setOnAction((ActionEvent e) -> {
                            printLaporan();
                        });
                        MenuItem refresh = new MenuItem("Refresh");
                        refresh.setOnAction((ActionEvent event) -> {
                            getPenjualan();
                        });
                        rowMenu.getItems().addAll(cetak, refresh);
                        setContextMenu(rowMenu);
                    }
                }
            };
            return row;
        });
        
        allPenjualan.addListener((ListChangeListener.Change<? extends PenjualanDetail> change) -> {
            searchPenjualanDetail();
        });
        searchField.textProperty().addListener(
                (ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
                    searchPenjualanDetail();
                });
        filterData.addAll(allPenjualan);
    }

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
        getPenjualan();
    }

    @FXML
    private void getPenjualan() {
        try (Connection con = Koneksi.getConnection()) {
            allPenjualan.clear();
            List<PenjualanDetail> listPenjualanDetail = PenjualanDetailDAO.getAllByTglBatalAndStatus(con,
                    mulaiTglPicker.getValue().toString(), akhirTglPicker.getValue().toString(), "false");
            List<PenjualanHead> listPenjualanHead = PenjualanHeadDAO.getAllByTglBatalAndStatus(con,
                    mulaiTglPicker.getValue().toString(), akhirTglPicker.getValue().toString(), "false");
            for (PenjualanDetail d : listPenjualanDetail) {
                for (PenjualanHead h : listPenjualanHead) {
                    if (d.getNoPenjualan().equals(h.getNoPenjualan())) {
                        d.setPenjualan(h);
                    }
                }
                Barang b = BarangDAO.get(con, d.getKodeBarcode());
                d.setBarang(b);
            }
            allPenjualan.addAll(listPenjualanDetail);
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

    private void searchPenjualanDetail() {
        try {
            filterData.clear();
            for (PenjualanDetail temp : allPenjualan) {
                if (searchField.getText() == null || searchField.getText().equals("")) {
                    filterData.add(temp);
                } else {
                    if (checkColumn(temp.getNoPenjualan())
                            || checkColumn(tglLengkap.format(tglSql.parse(temp.getPenjualan().getTglPenjualan())))
                            || checkColumn(temp.getPenjualan().getKodeSales())
                            || checkColumn(temp.getPenjualan().getKodePelanggan())
                            || checkColumn(temp.getPenjualan().getNama())
                            || checkColumn(temp.getPenjualan().getAlamat())
                            || checkColumn(temp.getPenjualan().getNoTelp())
                            || checkColumn(gr.format(temp.getPenjualan().getTotalBerat()))
                            || checkColumn(gr.format(temp.getPenjualan().getGrandtotal()))
                            || checkColumn(temp.getPenjualan().getCatatan())
                            || checkColumn(temp.getKodeBarcode())
                            || checkColumn(temp.getBarang().getNamaBarang())
                            || checkColumn(temp.getBarang().getKeterangan())
                            || checkColumn(temp.getBarang().getKodeKategori())
                            || checkColumn(temp.getBarang().getKodeJenis())
                            || checkColumn(temp.getBarang().getKodeGudang())
                            || checkColumn(temp.getBarang().getKodeIntern())
                            || checkColumn(temp.getBarang().getKadar())
                            || checkColumn(gr.format(temp.getBarang().getBerat()))
                            || checkColumn(gr.format(temp.getBarang().getBeratAsli()))
                            || checkColumn(gr.format(temp.getBarang().getNilaiPokok()))
                            || checkColumn(gr.format(temp.getHargaJual()))
                            || checkColumn(gr.format(temp.getHargaKomp()))
                            || checkColumn(tglLengkap.format(tglSql.parse(
                                    temp.getBarang().getBarcodeDate())))) {
                        filterData.add(temp);
                    }
                }
            }
            setTable();
        } catch (Exception e) {
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }

    private void setTable() throws Exception {
        if (penjualanTable.getRoot() != null) {
            penjualanTable.getRoot().getChildren().clear();
        }
        List<String> groupBy = new ArrayList<>();
        for (PenjualanDetail temp : filterData) {
            if (!groupBy.contains(temp.getNoPenjualan())) {
                groupBy.add(temp.getNoPenjualan());
            }
        }
        double totalBerat = 0;
        double totalNilai = 0;
        double totalJual = 0;
        for (String temp : groupBy) {
            PenjualanDetail head = new PenjualanDetail();
            head.setNoPenjualan(temp);
            PenjualanHead p = new PenjualanHead();
            p.setKodeSales("");
            head.setPenjualan(p);
            TreeItem<PenjualanDetail> parent = new TreeItem<>(head);
            double berat = 0;
            double beratAsli = 0;
            double nilaiPokok = 0;
            double hargaKomp = 0;
            double hargaJual = 0;
            for (PenjualanDetail detail : filterData) {
                if (temp.equals(detail.getNoPenjualan())) {
                    TreeItem<PenjualanDetail> child = new TreeItem<>(detail);
                    parent.getChildren().addAll(child);
                    berat = berat + detail.getBerat();
                    beratAsli = beratAsli + detail.getBarang().getBeratAsli();
                    nilaiPokok = nilaiPokok + detail.getNilaiPokok();
                    hargaKomp = hargaKomp + detail.getHargaKomp();
                    hargaJual = hargaJual + detail.getHargaJual();
                    totalBerat = totalBerat + detail.getBerat();
                    totalNilai = totalNilai + detail.getNilaiPokok();
                    totalJual = totalJual + detail.getHargaJual();
                }
            }
            Barang b = new Barang();
            b.setBeratAsli(beratAsli);
            b.setBerat(berat);
            b.setNilaiPokok(nilaiPokok);
            parent.getValue().setBarang(b);
            parent.getValue().setHargaKomp(hargaKomp);
            parent.getValue().setHargaJual(hargaJual);
            root.getChildren().add(parent);
        }
        penjualanTable.setRoot(root);
        totalBeratField.setText(gr.format(totalBerat));
        totalNilaiPokokField.setText(rp.format(totalNilai));
        totalPenjualanField.setText(rp.format(totalJual));
    }

    private void printLaporan() {
        try {
            PrintOut report = new PrintOut();
            report.printLaporanBatalPenjualan(filterData, mulaiTglPicker.getValue().toString(),
                    akhirTglPicker.getValue().toString(), searchField.getText());
        } catch (Exception e) {
            e.printStackTrace();
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }
}
