/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasGunungMas.View;

import com.excellentsystem.TokoEmasGunungMas.DAO.BarangDAO;
import com.excellentsystem.TokoEmasGunungMas.DAO.StokOpnameDetailDAO;
import com.excellentsystem.TokoEmasGunungMas.DAO.StokOpnameHeadDAO;
import static com.excellentsystem.TokoEmasGunungMas.Function.getTableCell;
import com.excellentsystem.TokoEmasGunungMas.Koneksi;
import com.excellentsystem.TokoEmasGunungMas.Main;
import static com.excellentsystem.TokoEmasGunungMas.Main.gr;
import static com.excellentsystem.TokoEmasGunungMas.Main.rp;
import static com.excellentsystem.TokoEmasGunungMas.Main.tglLengkap;
import static com.excellentsystem.TokoEmasGunungMas.Main.tglSql;
import static com.excellentsystem.TokoEmasGunungMas.Main.user;
import com.excellentsystem.TokoEmasGunungMas.Model.Barang;
import com.excellentsystem.TokoEmasGunungMas.Model.StokOpnameDetail;
import com.excellentsystem.TokoEmasGunungMas.Model.StokOpnameHead;
import com.excellentsystem.TokoEmasGunungMas.View.Dialog.DetailBarangController;
import com.excellentsystem.TokoEmasGunungMas.View.Dialog.LanjutStokOpnameController;
import com.excellentsystem.TokoEmasGunungMas.View.Dialog.NewStokOpnameController;
import java.sql.Connection;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
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
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Yunaz
 */
public class StokOpnameController {

    @FXML
    private TableView<StokOpnameDetail> barangTable;
    @FXML
    private TableColumn<StokOpnameDetail, Boolean> checkColumn;
    @FXML
    private TableColumn<StokOpnameDetail, String> kodeBarcodeColumn;
    @FXML
    private TableColumn<StokOpnameDetail, String> namaBarangColumn;
    @FXML
    private TableColumn<StokOpnameDetail, String> keteranganColumn;
    @FXML
    private TableColumn<StokOpnameDetail, String> kodeKategoriColumn;
    @FXML
    private TableColumn<StokOpnameDetail, String> kodeJenisColumn;
    @FXML
    private TableColumn<StokOpnameDetail, String> kodeGudangColumn;
    @FXML
    private TableColumn<StokOpnameDetail, String> kodeInternColumn;
    @FXML
    private TableColumn<StokOpnameDetail, String> kadarColumn;
    @FXML
    private TableColumn<StokOpnameDetail, Number> beratColumn;
    @FXML
    private TableColumn<StokOpnameDetail, Number> beratAsliColumn;
    @FXML
    private TableColumn<StokOpnameDetail, Number> nilaiPokokColumn;
    @FXML
    private TableColumn<StokOpnameDetail, Number> hargaJualColumn;
    @FXML
    private TableColumn<StokOpnameDetail, String> tglBarcodeColumn;
    @FXML
    private TableColumn<StokOpnameDetail, String> userBarcodeColumn;
    @FXML
    private TableColumn<StokOpnameDetail, String> salesStokColumn;

    @FXML
    private Label noStokOpnameLabel;
    @FXML
    private Button newStokOpnameButton;
    @FXML
    private Button lanjutStokOpnameButton;
    @FXML
    private TextField kodeBarcodeField;
    @FXML
    private Label totalQtyLabel;
    @FXML
    private Label totalBeratLabel;
    @FXML
    private Label totalBeratAsliLabel;
    @FXML
    private Label totalBeratLabelLabel;
    private Main mainApp;
    private final ObservableList<StokOpnameDetail> allBarang = FXCollections.observableArrayList();

    public void initialize() {
        kodeBarcodeColumn.setCellValueFactory(cellData -> cellData.getValue().kodeBarcodeProperty());
        namaBarangColumn.setCellValueFactory(cellData -> cellData.getValue().getBarang().namaBarangProperty());
        keteranganColumn.setCellValueFactory(cellData -> cellData.getValue().getBarang().keteranganProperty());
        kodeKategoriColumn.setCellValueFactory(cellData -> cellData.getValue().getBarang().kodeKategoriProperty());
        kodeJenisColumn.setCellValueFactory(cellData -> cellData.getValue().getBarang().kodeJenisProperty());
        kodeGudangColumn.setCellValueFactory(cellData -> cellData.getValue().getBarang().kodeGudangProperty());
        kodeInternColumn.setCellValueFactory(cellData -> cellData.getValue().getBarang().kodeInternProperty());
        kadarColumn.setCellValueFactory(cellData -> cellData.getValue().getBarang().kadarProperty());
        beratColumn.setCellValueFactory(cellData -> cellData.getValue().getBarang().beratProperty());
        beratColumn.setCellFactory(col -> getTableCell(gr));
        beratAsliColumn.setCellValueFactory(cellData -> cellData.getValue().getBarang().beratAsliProperty());
        beratAsliColumn.setCellFactory(col -> getTableCell(gr));
        nilaiPokokColumn.setCellValueFactory(cellData -> cellData.getValue().getBarang().nilaiPokokProperty());
        nilaiPokokColumn.setCellFactory(col -> getTableCell(rp));
        hargaJualColumn.setCellValueFactory(cellData -> cellData.getValue().getBarang().hargaJualProperty());
        hargaJualColumn.setCellFactory(col -> getTableCell(gr));
        tglBarcodeColumn.setCellValueFactory(cellData -> {
            try {
                return new SimpleStringProperty(tglLengkap.format(
                        tglSql.parse(cellData.getValue().getBarang().getBarcodeDate())));
            } catch (ParseException ex) {
                return null;
            }
        });
        userBarcodeColumn.setCellValueFactory(cellData -> cellData.getValue().getBarang().barcodeByProperty());
        salesStokColumn.setCellValueFactory(cellData -> cellData.getValue().kodeSalesProperty());

        checkColumn.setCellValueFactory(cellData -> cellData.getValue().statusProperty());
        checkColumn.setCellFactory(CheckBoxTableCell.forTableColumn((Integer param) -> {
            return barangTable.getItems().get(param).statusProperty();
        }));
        kodeBarcodeField.setOnKeyPressed((KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                stokBarcode();
            }
        });
        final ContextMenu rowMenu = new ContextMenu();
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent event) -> {
            getStokOpname();
        });
        rowMenu.getItems().addAll(refresh);
        barangTable.setContextMenu(rowMenu);
        barangTable.setRowFactory(table -> {
            TableRow<StokOpnameDetail> row = new TableRow<StokOpnameDetail>() {
                @Override
                public void updateItem(StokOpnameDetail item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(rowMenu);
                    } else {
                        final ContextMenu rowMenu = new ContextMenu();
                        MenuItem lihat = new MenuItem("Lihat Detail Barang");
                        lihat.setOnAction((ActionEvent e) -> {
                            lihatDetailBarang(item);
                        });
                        MenuItem refresh = new MenuItem("Refresh");
                        refresh.setOnAction((ActionEvent e) -> {
                            getStokOpname();
                        });
                        rowMenu.getItems().addAll(lihat, refresh);
                        setContextMenu(rowMenu);
                    }
                }
            };
//            row.setOnMouseClicked((MouseEvent mouseEvent) -> {
//                if(mouseEvent.getButton().equals(MouseButton.PRIMARY)&&
//                        mouseEvent.getClickCount() == 2){
//                    if(row.getItem().isStatus()){
//                        try {
//                            if(service.con.isClosed()) service.setService();
//                            row.getItem().setStatus(false);
//                            row.getItem().setKodeSales("");
//                            service.stokOpnameDetailDao.updateStokOpnameDetail(row.getItem());
//                            getStokOpname();
//                        } catch (Exception ex) {
//                            mainApp.showMessage(Modality.NONE, "Warning", ex.toString());
//                        }
//                    }else{
//                        try {
//                            if(service.con.isClosed()) service.setService();
//                            row.getItem().setStatus(true);
//                            row.getItem().setKodeSales(salesCombo.getSelectionModel().getSelectedItem());
//                            service.stokOpnameDetailDao.updateStokOpnameDetail(row.getItem());
//                            getStokOpname();
//                        } catch (Exception ex) {
//                            mainApp.showMessage(Modality.NONE, "Warning", ex.toString());
//                        }
//                    }
//                }
//            });
            return row;
        });
    }

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
        barangTable.setItems(allBarang);
    }

    private void getBarang(String kodeGudang, String kodeKategori, String kodeJenis) {
        try (Connection con = Koneksi.getConnection()) {
            allBarang.clear();
            StokOpnameHead sHead = new StokOpnameHead();
            sHead.setNoStokOpname(StokOpnameHeadDAO.getId(con));
            sHead.setTglStokOpname(tglSql.format(new Date()));
            sHead.setKodeGudang(kodeGudang);
            sHead.setKodeKategori(kodeKategori);
            sHead.setKodeJenis(kodeJenis);
            double totalberat = 0;
            int totalqty = 0;
            List<Barang> x = BarangDAO.getAll(con,
                    "Tersedia", "Semua", kodeGudang, kodeKategori, kodeJenis);
            for (Barang b : x) {
                StokOpnameDetail s = new StokOpnameDetail();
                s.setNoStokOpname(sHead.getNoStokOpname());
                s.setKodeBarcode(b.getKodeBarcode());
                s.setStatus(false);
                s.setKodeSales("");
                s.setBarang(b);
                allBarang.add(s);
                totalqty = totalqty + 1;
                totalberat = totalberat + b.getBerat();
                StokOpnameDetailDAO.insert(con, s);
            }
            sHead.setTotalQty(0);
            sHead.setTotalBerat(0);
            StokOpnameHeadDAO.insert(con, sHead);
            noStokOpnameLabel.setText(sHead.getNoStokOpname());
            noStokOpnameLabel.setVisible(true);
            newStokOpnameButton.setVisible(false);
            lanjutStokOpnameButton.setVisible(false);
            hitungTotal();
        } catch (Exception e) {
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }

    private void getStokOpname() {
        try (Connection con = Koneksi.getConnection()) {
            allBarang.clear();
            if (noStokOpnameLabel.getText().equals("")) {
                mainApp.showMessage(Modality.NONE, "Warning", "No Stok Opname belum dipilih");
            } else {
                StokOpnameHead sHead = StokOpnameHeadDAO.get(con, noStokOpnameLabel.getText());
                if (sHead == null) {
                    mainApp.showMessage(Modality.NONE, "Warning", "No Stok Opname tidak ditemukan");
                    noStokOpnameLabel.setText("");
                } else {
                    List<StokOpnameDetail> stok = StokOpnameDetailDAO.getAll(con,
                            noStokOpnameLabel.getText());
                    for (StokOpnameDetail s : stok) {
                        s.setBarang(BarangDAO.get(con, s.getKodeBarcode()));
                    }
                    allBarang.addAll(stok);
                    noStokOpnameLabel.setVisible(true);
                    newStokOpnameButton.setVisible(false);
                    lanjutStokOpnameButton.setVisible(false);
                    hitungTotal();
                }
            }
        } catch (Exception e) {
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }

    @FXML
    private void newStokOpname() {
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/NewStokOpname.fxml");
        NewStokOpnameController c = loader.getController();
        c.setMainApp(mainApp, mainApp.MainStage, stage);
        c.OkButton.setOnAction((event) -> {
            getBarang(c.kodeGudangCombo.getSelectionModel().getSelectedItem(),
                    c.kodeKategoriCombo.getSelectionModel().getSelectedItem(),
                    c.kodeJenisCombo.getSelectionModel().getSelectedItem());
            mainApp.closeDialog(mainApp.MainStage, stage);
        });
    }

    @FXML
    private void lanjutStokOpname() {
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/LanjutStokOpname.fxml");
        LanjutStokOpnameController c = loader.getController();
        c.setMainApp(mainApp, mainApp.MainStage, stage);
        c.okButton.setOnAction((event) -> {
            if (c.noStokOpnameField.getText().equals("")) {
                mainApp.showMessage(Modality.NONE, "Warning", "No Stok Opname masih kosong");
            } else {
                noStokOpnameLabel.setText(c.noStokOpnameField.getText());
                getStokOpname();
                mainApp.closeDialog(mainApp.MainStage, stage);
            }
        });
    }

    private void hitungTotal() {
        int totalQty = 0;
        double totalBerat = 0;
        double totalBeratAsli = 0;
        for (StokOpnameDetail b : allBarang) {
            if (b.isStatus()) {
                totalQty = totalQty + 1;
                totalBerat = totalBerat + b.getBarang().getBerat();
                totalBeratAsli = totalBeratAsli + b.getBarang().getBeratAsli();
            }
        }
        totalQtyLabel.setText(gr.format(totalQty));
        totalBeratLabel.setText(gr.format(totalBerat));
        totalBeratAsliLabel.setText(gr.format(totalBeratAsli));
        totalBeratLabelLabel.setText(gr.format(totalBeratAsli + (totalQty * Main.sistem.getBeratLabel())));
    }

    private void lihatDetailBarang(StokOpnameDetail b) {
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/DetailBarang.fxml");
        DetailBarangController controller = loader.getController();
        controller.setMainApp(mainApp, mainApp.MainStage, stage);
        controller.setBarang(b.getBarang());
        controller.setViewOnly();
    }

    @FXML
    private void stokBarcode() {
        if (kodeBarcodeField.getText().equals("")) {
            mainApp.showMessage(Modality.NONE, "Warning", "Kode Barcode masih kosong");
        } else {
            Boolean status = false;
            for (StokOpnameDetail s : allBarang) {
                if (s.getKodeBarcode().equals(kodeBarcodeField.getText())) {
                    status = true;
                    if (s.isStatus()) {
                        mainApp.showMessage(Modality.NONE, "Warning", "Barang sudah pernah di scan");
                        kodeBarcodeField.selectAll();
                    } else {
                        try (Connection con = Koneksi.getConnection()) {
                            s.setStatus(true);
                            s.setKodeSales(user.getUsername());
                            StokOpnameDetailDAO.update(con, s);
                            getStokOpname();
                            kodeBarcodeField.setText("");
                        } catch (Exception e) {
                            mainApp.showMessage(Modality.NONE, "Error", e.toString());
                        }
                    }
                }
            }
            if (!status) {
                mainApp.showMessage(Modality.NONE, "Warning", "Kode Barcode tidak ditemukan");
            }
        }
    }

}
