/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasGunungMas.View.Report;

import com.excellentsystem.TokoEmasGunungMas.DAO.BarangDAO;
import com.excellentsystem.TokoEmasGunungMas.DAO.PindahDetailDAO;
import com.excellentsystem.TokoEmasGunungMas.DAO.PindahHeadDAO;
import com.excellentsystem.TokoEmasGunungMas.Function;
import static com.excellentsystem.TokoEmasGunungMas.Function.getTreeTableCell;
import com.excellentsystem.TokoEmasGunungMas.Koneksi;
import com.excellentsystem.TokoEmasGunungMas.Main;
import static com.excellentsystem.TokoEmasGunungMas.Main.gr;
import static com.excellentsystem.TokoEmasGunungMas.Main.rp;
import static com.excellentsystem.TokoEmasGunungMas.Main.tglLengkap;
import static com.excellentsystem.TokoEmasGunungMas.Main.tglSql;
import com.excellentsystem.TokoEmasGunungMas.Model.Barang;
import com.excellentsystem.TokoEmasGunungMas.Model.PindahDetail;
import com.excellentsystem.TokoEmasGunungMas.Model.PindahHead;
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
public class LaporanPindahGudangController {

    @FXML
    private TreeTableView<PindahDetail> pindahGudangHeadTable;
    @FXML
    private TreeTableColumn<PindahDetail, String> noPindahColumn;
    @FXML
    private TreeTableColumn<PindahDetail, String> tglPindahColumn;
    @FXML
    private TreeTableColumn<PindahDetail, String> kodeUserColumn;
    @FXML
    private TreeTableColumn<PindahDetail, String> gudangAsalColumn;
    @FXML
    private TreeTableColumn<PindahDetail, String> gudangTujuanColumn;
    @FXML
    private TreeTableColumn<PindahDetail, String> kodeBarcodeColumn;
    @FXML
    private TreeTableColumn<PindahDetail, String> namaBarangColumn;
    @FXML
    private TreeTableColumn<PindahDetail, String> keteranganColumn;
    @FXML
    private TreeTableColumn<PindahDetail, String> kodeKategoriColumn;
    @FXML
    private TreeTableColumn<PindahDetail, String> kodeJenisColumn;
    @FXML
    private TreeTableColumn<PindahDetail, String> kodeInternColumn;
    @FXML
    private TreeTableColumn<PindahDetail, String> kadarColumn;
    @FXML
    private TreeTableColumn<PindahDetail, Number> beratColumn;
    @FXML
    private TreeTableColumn<PindahDetail, Number> beratAsliColumn;
    @FXML
    private TreeTableColumn<PindahDetail, Number> beratKemasanColumn;

    @FXML
    private Label totalQty;
    @FXML
    private Label totalBerat;
    @FXML
    private Label totalBeratAsli;
    @FXML
    private DatePicker tglStartPicker;
    @FXML
    private DatePicker tglAkhirPicker;
    @FXML
    private TextField searchField;
    private Main mainApp;
    final TreeItem<PindahDetail> root = new TreeItem<>();
    private ObservableList<PindahDetail> allPindah = FXCollections.observableArrayList();
    private ObservableList<PindahDetail> filterData = FXCollections.observableArrayList();
    private ObservableList<PindahDetail> allDetail = FXCollections.observableArrayList();

    public void initialize() {
        noPindahColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().noPindahProperty());

        tglPindahColumn.setCellValueFactory(cellData -> {
            try {
                return new SimpleStringProperty(tglLengkap.format(tglSql.parse(cellData.getValue().getValue().getPindahHead().getTglPindah())));
            } catch (Exception ex) {
                return null;
            }
        });
        tglPindahColumn.setComparator(Function.sortDate(tglLengkap));

        gudangAsalColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().gudangAsalProperty());

        gudangTujuanColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().getPindahHead().gudangTujuanProperty());

        kodeUserColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().getPindahHead().kodeUserProperty());

        kodeBarcodeColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().kodeBarcodeProperty());

        namaBarangColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().getBarang().namaBarangProperty());

        keteranganColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().getBarang().keteranganProperty());

        kodeKategoriColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().getBarang().kodeKategoriProperty());

        kodeJenisColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().getBarang().kodeJenisProperty());

        kodeInternColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().getBarang().kodeInternProperty());

        kadarColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().getBarang().kadarProperty());

        beratColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().getBarang().beratProperty());
        beratColumn.setCellFactory(col -> getTreeTableCell(gr));

        beratAsliColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().getBarang().beratAsliProperty());
        beratAsliColumn.setCellFactory(col -> getTreeTableCell(gr));

        beratKemasanColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().getBarang().beratKemasanProperty());
        beratKemasanColumn.setCellFactory(col -> getTreeTableCell(gr));

        tglStartPicker.setConverter(Function.getTglConverter());
        tglStartPicker.setValue(LocalDate.now());
        tglStartPicker.setDayCellFactory((final DatePicker datePicker) -> Function.getDateCellMulai(tglAkhirPicker));
        tglAkhirPicker.setConverter(Function.getTglConverter());
        tglAkhirPicker.setValue(LocalDate.now());
        tglAkhirPicker.setDayCellFactory((final DatePicker datePicker) -> Function.getDateCellAkhir(tglStartPicker));

        final ContextMenu rowMenu = new ContextMenu();
        MenuItem cetak = new MenuItem("Print Laporan");
        cetak.setOnAction((ActionEvent e) -> {
            printLaporan();
        });
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent event) -> {
            getPindahGudang();
        });
        rowMenu.getItems().addAll(cetak, refresh);
        pindahGudangHeadTable.setContextMenu(rowMenu);
        pindahGudangHeadTable.setRowFactory(table -> {
            TreeTableRow<PindahDetail> row = new TreeTableRow<PindahDetail>() {
                @Override
                public void updateItem(PindahDetail item, boolean empty) {
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
                            getPindahGudang();
                        });
                        rowMenu.getItems().addAll(cetak, refresh);
                        setContextMenu(rowMenu);
                    }
                }
            };
            return row;
        });

        allPindah.addListener((ListChangeListener.Change<? extends PindahDetail> change) -> {
            searchPindah();
        });
        searchField.textProperty().addListener(
                (ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
                    searchPindah();
                });
        filterData.addAll(allPindah);
    }

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
        getPindahGudang();
    }

    @FXML
    private void getPindahGudang() {
        try (Connection con = Koneksi.getConnection()) {
            allPindah.clear();
            List<PindahHead> listPindahHead = PindahHeadDAO.getAllByTglPindah(con,
                    tglStartPicker.getValue().toString(), tglAkhirPicker.getValue().toString());
            List<PindahDetail> listPindahDetail = PindahDetailDAO.getAllByTglPindah(con,
                    tglStartPicker.getValue().toString(), tglAkhirPicker.getValue().toString());
            for (PindahDetail d : listPindahDetail) {
                for (PindahHead h : listPindahHead) {
                    if (d.getNoPindah().equals(h.getNoPindah())) {
                        d.setPindahHead(h);
                    }
                }
                Barang b = BarangDAO.get(con, d.getKodeBarcode());
                d.setBarang(b);
            }
            allPindah.addAll(listPindahDetail);
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

    private void searchPindah() {
        try {
            filterData.clear();
            for (PindahDetail temp : allPindah) {
                if (searchField.getText() == null || searchField.getText().equals("")) {
                    filterData.add(temp);
                } else {
                    if (checkColumn(temp.getNoPindah())
                            || checkColumn(temp.getPindahHead().getGudangTujuan())
                            || checkColumn(tglLengkap.format(tglSql.parse(temp.getPindahHead().getTglPindah())))
                            || checkColumn(gr.format(temp.getPindahHead().getTotalQty()))
                            || checkColumn(gr.format(temp.getPindahHead().getTotalBerat()))
                            || checkColumn(gr.format(temp.getPindahHead().getTotalBeratAsli()))
                            || checkColumn(temp.getPindahHead().getKodeUser())
                            || checkColumn(temp.getKodeBarcode())
                            || checkColumn(tglLengkap.format(tglSql.parse(temp.getBarang().getBarcodeDate())))
                            || checkColumn(temp.getBarang().getNamaBarang())
                            || checkColumn(temp.getBarang().getKeterangan())
                            || checkColumn(temp.getBarang().getKodeKategori())
                            || checkColumn(temp.getBarang().getKodeJenis())
                            || checkColumn(temp.getBarang().getKodeIntern())
                            || checkColumn(temp.getBarang().getKadar())
                            || checkColumn(temp.getBarang().getStatusBarang())
                            || checkColumn(gr.format(temp.getBarang().getBerat()))
                            || checkColumn(gr.format(temp.getBarang().getBeratAsli()))
                            || checkColumn(rp.format(temp.getBarang().getNilaiPokok()))
                            || checkColumn(rp.format(temp.getBarang().getHargaJual()))
                            || checkColumn(temp.getGudangAsal())
                            || checkColumn(temp.getBarang().getBarcodeBy())) {
                        filterData.add(temp);
                    }
                }
            }
            setTable();
            hitungTotal();
        } catch (Exception e) {
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }

    private void setTable() throws Exception {
        if (pindahGudangHeadTable.getRoot() != null) {
            pindahGudangHeadTable.getRoot().getChildren().clear();
        }
        List<String> groupBy = new ArrayList<>();
        for (PindahDetail temp : filterData) {
            if (!groupBy.contains(temp.getNoPindah())) {
                groupBy.add(temp.getNoPindah());
            }
        }
        for (String temp : groupBy) {
            PindahDetail head = new PindahDetail();
            head.setNoPindah(temp);
            PindahHead p = new PindahHead();
            head.setPindahHead(p);
            Barang j = new Barang();
            head.setBarang(j);
            TreeItem<PindahDetail> parent = new TreeItem<>(head);
            double berat = 0;
            double beratAsli = 0;
            double beratKemasan = 0;
            for (PindahDetail detail : filterData) {
                if (temp.equals(detail.getNoPindah())) {
                    TreeItem<PindahDetail> child = new TreeItem<>(detail);
                    parent.getChildren().addAll(child);
                    berat = berat + detail.getBarang().getBerat();
                    beratAsli = beratAsli + detail.getBarang().getBeratAsli();
                    beratKemasan = beratKemasan + detail.getBarang().getBeratKemasan();
                }
            }
            parent.getValue().getBarang().setBerat(berat);
            parent.getValue().getBarang().setBeratAsli(beratAsli);
            parent.getValue().getBarang().setBeratKemasan(beratKemasan);
            root.getChildren().add(parent);
        }
        pindahGudangHeadTable.setRoot(root);
    }

    private void hitungTotal() {
        int qty = 0;
        double berat = 0;
        double beratAsli = 0;
        for (PindahDetail h : filterData) {
            qty = qty + 1;
            berat = berat + h.getBarang().getBerat();
            beratAsli = beratAsli + h.getBarang().getBeratAsli();
        }
        totalQty.setText(gr.format(qty));
        totalBerat.setText(gr.format(berat));
        totalBeratAsli.setText(gr.format(beratAsli));
    }

    private void printLaporan() {
        try {
            PrintOut report = new PrintOut();
            report.printLaporanPindahGudang(filterData, tglStartPicker.getValue().toString(),
                    tglAkhirPicker.getValue().toString(), searchField.getText());
        } catch (Exception e) {
            e.printStackTrace();
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }
}
