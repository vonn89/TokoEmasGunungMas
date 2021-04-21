/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasGunungMas.View.Report;

import com.excellentsystem.TokoEmasGunungMas.DAO.BarangDAO;
import com.excellentsystem.TokoEmasGunungMas.DAO.HancurDetailDAO;
import com.excellentsystem.TokoEmasGunungMas.DAO.HancurHeadDAO;
import com.excellentsystem.TokoEmasGunungMas.Function;
import static com.excellentsystem.TokoEmasGunungMas.Function.getTreeTableCell;
import com.excellentsystem.TokoEmasGunungMas.Koneksi;
import com.excellentsystem.TokoEmasGunungMas.Main;
import static com.excellentsystem.TokoEmasGunungMas.Main.gr;
import static com.excellentsystem.TokoEmasGunungMas.Main.rp;
import static com.excellentsystem.TokoEmasGunungMas.Main.tglLengkap;
import static com.excellentsystem.TokoEmasGunungMas.Main.tglSql;
import com.excellentsystem.TokoEmasGunungMas.Model.Barang;
import com.excellentsystem.TokoEmasGunungMas.Model.HancurDetail;
import com.excellentsystem.TokoEmasGunungMas.Model.HancurHead;
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
public class LaporanHancurBarangController {

    @FXML
    private TreeTableView<HancurDetail> hancurHeadTable;
    @FXML
    private TreeTableColumn<HancurDetail, String> noHancurColumn;
    @FXML
    private TreeTableColumn<HancurDetail, String> tglHancurColumn;
    @FXML
    private TreeTableColumn<HancurDetail, String> kodeUserColumn;
    @FXML
    private TreeTableColumn<HancurDetail, String> kodeBarcodeColumn;
    @FXML
    private TreeTableColumn<HancurDetail, String> namaBarangColumn;
    @FXML
    private TreeTableColumn<HancurDetail, String> keteranganColumn;
    @FXML
    private TreeTableColumn<HancurDetail, String> kodeKategoriColumn;
    @FXML
    private TreeTableColumn<HancurDetail, String> kodeJenisColumn;
    @FXML
    private TreeTableColumn<HancurDetail, String> kodeGudangColumn;
    @FXML
    private TreeTableColumn<HancurDetail, String> kodeInternColumn;
    @FXML
    private TreeTableColumn<HancurDetail, String> kadarColumn;
    @FXML
    private TreeTableColumn<HancurDetail, Number> beratColumn;
    @FXML
    private TreeTableColumn<HancurDetail, Number> beratAsliColumn;
    @FXML
    private TreeTableColumn<HancurDetail, Number> beratKemasanColumn;

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
    final TreeItem<HancurDetail> root = new TreeItem<>();
    private ObservableList<HancurDetail> filterData = FXCollections.observableArrayList();
    private ObservableList<HancurDetail> allDetail = FXCollections.observableArrayList();

    public void initialize() {
        noHancurColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().noHancurProperty());
        tglHancurColumn.setCellValueFactory(cellData -> {
            try {
                return new SimpleStringProperty(tglLengkap.format(
                        tglSql.parse(cellData.getValue().getValue().getHancurHead().getTglHancur())));
            } catch (Exception ex) {
                return null;
            }
        });
        kodeUserColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().getHancurHead().kodeUserProperty());

        kodeBarcodeColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().kodeBarcodeProperty());

        namaBarangColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().getBarang().namaBarangProperty());

        keteranganColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().getBarang().keteranganProperty());

        kodeKategoriColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().getBarang().kodeKategoriProperty());

        kodeJenisColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().getBarang().kodeJenisProperty());

        kodeGudangColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().getBarang().kodeGudangProperty());

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
            getHancurBarang();
        });
        rowMenu.getItems().addAll(cetak, refresh);
        hancurHeadTable.setContextMenu(rowMenu);
        hancurHeadTable.setRowFactory(table -> {
            TreeTableRow<HancurDetail> row = new TreeTableRow<HancurDetail>() {
                @Override
                public void updateItem(HancurDetail item, boolean empty) {
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
                            getHancurBarang();
                        });
                        rowMenu.getItems().addAll(cetak, refresh);
                        setContextMenu(rowMenu);
                    }
                }
            };
            return row;
        });

        allDetail.addListener((ListChangeListener.Change<? extends HancurDetail> change) -> {
            searchHancur();
        });
        searchField.textProperty().addListener(
                (ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
                    searchHancur();
                });
        filterData.addAll(allDetail);
    }

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
        getHancurBarang();
    }

    @FXML
    private void getHancurBarang() {
        try (Connection con = Koneksi.getConnection()) {
            allDetail.clear();
            List<HancurHead> listHancurHead = HancurHeadDAO.getAllByTglHancur(con,
                    tglStartPicker.getValue().toString(), tglAkhirPicker.getValue().toString());
            List<HancurDetail> listHancurDetail = HancurDetailDAO.getAllByTglHancur(con,
                    tglStartPicker.getValue().toString(), tglAkhirPicker.getValue().toString());
            for (HancurDetail d : listHancurDetail) {
                for (HancurHead h : listHancurHead) {
                    if (d.getNoHancur().equals(h.getNoHancur())) {
                        d.setHancurHead(h);
                    }
                }
                Barang b = BarangDAO.get(con, d.getKodeBarcode());
                d.setBarang(b);
            }
            allDetail.addAll(listHancurDetail);
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

    private void searchHancur() {
        try {
            filterData.clear();
            for (HancurDetail temp : allDetail) {
                if (searchField.getText() == null || searchField.getText().equals("")) {
                    filterData.add(temp);
                } else {
                    if (checkColumn(temp.getNoHancur())
                            || checkColumn(tglLengkap.format(tglSql.parse(temp.getHancurHead().getTglHancur())))
                            || checkColumn(gr.format(temp.getHancurHead().getTotalQty()))
                            || checkColumn(gr.format(temp.getHancurHead().getTotalBerat()))
                            || checkColumn(gr.format(temp.getHancurHead().getTotalBeratAsli()))
                            || checkColumn(temp.getHancurHead().getKodeUser())
                            || checkColumn(temp.getKodeBarcode())
                            || checkColumn(tglLengkap.format(tglSql.parse(temp.getBarang().getBarcodeDate())))
                            || checkColumn(temp.getBarang().getNamaBarang())
                            || checkColumn(temp.getBarang().getKeterangan())
                            || checkColumn(temp.getBarang().getKodeKategori())
                            || checkColumn(temp.getBarang().getKodeJenis())
                            || checkColumn(temp.getBarang().getKodeGudang())
                            || checkColumn(temp.getBarang().getKodeIntern())
                            || checkColumn(temp.getBarang().getKadar())
                            || checkColumn(temp.getBarang().getStatusBarang())
                            || checkColumn(gr.format(temp.getBarang().getBerat()))
                            || checkColumn(gr.format(temp.getBarang().getBeratAsli()))
                            || checkColumn(rp.format(temp.getBarang().getNilaiPokok()))
                            || checkColumn(rp.format(temp.getBarang().getHargaJual()))
                            || checkColumn(temp.getBarang().getBarcodeBy())) {
                        filterData.add(temp);
                    }
                }
            }setTable();
            hitungTotal();
        } catch (Exception e) {
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }


    private void setTable() throws Exception {
        if (hancurHeadTable.getRoot() != null) {
            hancurHeadTable.getRoot().getChildren().clear();
        }
        List<String> groupBy = new ArrayList<>();
        for (HancurDetail temp : filterData) {
            if (!groupBy.contains(temp.getNoHancur())) {
                groupBy.add(temp.getNoHancur());
            }
        }
        for (String temp : groupBy) {
            HancurDetail head = new HancurDetail();
            head.setNoHancur(temp);
            HancurHead p = new HancurHead();
            head.setHancurHead(p);
            Barang j = new Barang();
            head.setBarang(j);
            TreeItem<HancurDetail> parent = new TreeItem<>(head);
            double berat = 0;
            double beratAsli = 0;
            double beratKemasan = 0;
            for (HancurDetail detail : filterData) {
                if (temp.equals(detail.getNoHancur())) {
                    TreeItem<HancurDetail> child = new TreeItem<>(detail);
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
        hancurHeadTable.setRoot(root);
    }

    private void hitungTotal() {
        int qty = 0;
        double berat = 0;
        double beratAsli = 0;
        for (HancurDetail h : filterData) {
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
            report.printLaporanHancurBarang(filterData, tglStartPicker.getValue().toString(),
                    tglAkhirPicker.getValue().toString(), searchField.getText());
        } catch (Exception e) {
            e.printStackTrace();
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }
}
