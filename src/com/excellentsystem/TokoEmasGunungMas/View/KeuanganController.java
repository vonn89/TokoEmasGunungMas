/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasGunungMas.View;

import com.excellentsystem.TokoEmasGunungMas.DAO.KategoriTransaksiDAO;
import com.excellentsystem.TokoEmasGunungMas.DAO.KeuanganDAO;
import com.excellentsystem.TokoEmasGunungMas.Function;
import static com.excellentsystem.TokoEmasGunungMas.Function.getTreeTableCell;
import com.excellentsystem.TokoEmasGunungMas.Koneksi;
import com.excellentsystem.TokoEmasGunungMas.Main;
import static com.excellentsystem.TokoEmasGunungMas.Main.gr;
import static com.excellentsystem.TokoEmasGunungMas.Main.rp;
import static com.excellentsystem.TokoEmasGunungMas.Main.sistem;
import static com.excellentsystem.TokoEmasGunungMas.Main.tglBarang;
import static com.excellentsystem.TokoEmasGunungMas.Main.tglLengkap;
import static com.excellentsystem.TokoEmasGunungMas.Main.tglSql;
import static com.excellentsystem.TokoEmasGunungMas.Main.tglSystem;
import com.excellentsystem.TokoEmasGunungMas.Model.KategoriTransaksi;
import com.excellentsystem.TokoEmasGunungMas.Model.Keuangan;
import com.excellentsystem.TokoEmasGunungMas.PrintOut.PrintOut;
import com.excellentsystem.TokoEmasGunungMas.View.Dialog.NewKeuanganController;
import com.excellentsystem.TokoEmasGunungMas.View.Dialog.VerifikasiController;
import java.sql.Connection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableRow;
import javafx.scene.control.TreeTableView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Xtreme
 */
public class KeuanganController {
    
    @FXML
    private TreeTableView<Keuangan> keuanganTable;
    @FXML
    private TreeTableColumn<Keuangan, String> noKeuanganColumn;
    @FXML
    private TreeTableColumn<Keuangan, String> tglKeuanganColumn;
    @FXML
    private TreeTableColumn<Keuangan, String> kategoriColumn;
    @FXML
    private TreeTableColumn<Keuangan, String> deskripsiColumn;
    @FXML
    private TreeTableColumn<Keuangan, Number> jumlahRpColumn;
    @FXML
    private TreeTableColumn<Keuangan, String> kodeUserColumn;
    @FXML
    private TextField searchField;
    @FXML
    private Label saldoAwalField;
    @FXML
    private Label saldoAkhirField;
    @FXML
    private DatePicker tglPicker;
    final TreeItem<Keuangan> root = new TreeItem<>();
    private List<KategoriTransaksi> listKategori = new ArrayList<>();
    private ObservableList<Keuangan> allKeuangan = FXCollections.observableArrayList();
    private ObservableList<Keuangan> filterData = FXCollections.observableArrayList();
    private Main mainApp;
    
    public void initialize() {
        noKeuanganColumn.setCellValueFactory(param -> param.getValue().getValue().noKeuanganProperty());
        kategoriColumn.setCellValueFactory(param -> param.getValue().getValue().kategoriProperty());
        deskripsiColumn.setCellValueFactory(param -> param.getValue().getValue().deskripsiProperty());
        kodeUserColumn.setCellValueFactory(param -> param.getValue().getValue().kodeUserProperty());
        tglKeuanganColumn.setCellValueFactory(cellData -> {
            try {
                DateFormat df = new SimpleDateFormat("hh:mm");
                return new SimpleStringProperty(df.format(tglSql.parse(cellData.getValue().getValue().getTglKeuangan())));
            } catch (Exception ex) {
                return null;
            }
        });
        jumlahRpColumn.setCellValueFactory(param -> param.getValue().getValue().jumlahRpProperty());
        jumlahRpColumn.setCellFactory(col -> getTreeTableCell(rp));
        tglPicker.setConverter(Function.getTglConverter());
        tglPicker.setValue(LocalDate.parse(Main.sistem.getTglSystem(), DateTimeFormatter.ISO_DATE));
        tglPicker.setDayCellFactory((final DatePicker datePicker) -> new DateCell() {
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
            }
        });
        final ContextMenu rowMenu = new ContextMenu();
        MenuItem addNew = new MenuItem("Transaksi Keuangan Baru");
        addNew.setOnAction((ActionEvent event) -> {
            showNewKeuangan();
        });
        MenuItem print = new MenuItem("Print Keuangan Harian");
        print.setOnAction((ActionEvent event) -> {
            printKeuangan();
        });
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent event) -> {
            getKeuangan();
        });
        rowMenu.getItems().addAll(addNew, print, refresh);
        keuanganTable.setContextMenu(rowMenu);
        keuanganTable.setRowFactory(ttv -> {
            TreeTableRow<Keuangan> row = new TreeTableRow<Keuangan>() {
                @Override
                public void updateItem(Keuangan item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(rowMenu);
                    } else if (item.getNoKeuangan().startsWith("KK")) {
                        ContextMenu rowMenu = new ContextMenu();
                        MenuItem addNew = new MenuItem("Transaksi Keuangan Baru");
                        addNew.setOnAction((ActionEvent event) -> {
                            showNewKeuangan();
                        });
                        MenuItem print = new MenuItem("Print Keuangan Harian");
                        print.setOnAction((ActionEvent event) -> {
                            printKeuangan();
                        });
                        MenuItem refresh = new MenuItem("Refresh");
                        refresh.setOnAction((ActionEvent event) -> {
                            getKeuangan();
                        });
                        rowMenu.getItems().addAll(addNew, print, refresh);
                        
                        Boolean status = false;
                        for (KategoriTransaksi k : listKategori) {
                            if (item.getKategori().equals(k.getKodeKategori())) {
                                status = true;
                            }
                        }
                        if (status) {
                            if (item.getTglKeuangan().startsWith(tglBarang.format(new Date()))) {
                                MenuItem batal = new MenuItem("Batal Transaksi");
                                batal.setOnAction(e -> {
                                    batal(item);
                                });
                                rowMenu.getItems().add(batal);
                            }
                        }
                        setContextMenu(rowMenu);
                    }
                }
            };
            return row;
        });
        allKeuangan.addListener((ListChangeListener.Change<? extends Keuangan> change) -> {
            searchKeuangan();
        });
        searchField.textProperty().addListener(
                (ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
                    searchKeuangan();
                });
        filterData.addAll(allKeuangan);
    }
    
    public void setMainApp(Main mainApp) {
        try (Connection con = Koneksi.getConnection()) {
            this.mainApp = mainApp;
            getKeuangan();
            listKategori = KategoriTransaksiDAO.getAll(con);
        } catch (Exception e) {
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }
    
    @FXML
    private void getKeuangan() {
        try (Connection con = Koneksi.getConnection()) {
            allKeuangan.clear();
            allKeuangan.addAll(KeuanganDAO.getAllByDate(con,
                    tglPicker.getValue().toString(),
                    tglPicker.getValue().toString()));
            saldoAwalField.setText(rp.format(KeuanganDAO.getSaldoAwal(con,
                    tglPicker.getValue().toString())));
            saldoAkhirField.setText(rp.format(KeuanganDAO.getSaldoAkhir(con,
                    tglPicker.getValue().toString())));
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
    
    private void searchKeuangan() {
        try {
            filterData.clear();
            for (Keuangan temp : allKeuangan) {
                if (searchField.getText() == null || searchField.getText().equals("")) {
                    filterData.add(temp);
                } else {
                    if (checkColumn(temp.getNoKeuangan())
                            || checkColumn(tglLengkap.format(tglSql.parse(temp.getTglKeuangan())))
                            || checkColumn(temp.getKategori())
                            || checkColumn(temp.getDeskripsi())
                            || checkColumn(gr.format(temp.getJumlahRp()))
                            || checkColumn(temp.getKodeUser())) {
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
        if (keuanganTable.getRoot() != null) {
            keuanganTable.getRoot().getChildren().clear();
        }
        List<String> kategori = new ArrayList<>();
        for (Keuangan temp : filterData) {
            if (!kategori.contains(temp.getKategori())) {
                kategori.add(temp.getKategori());
            }
        }
        for (String temp : kategori) {
            Keuangan katKeu = new Keuangan();
            katKeu.setNoKeuangan(temp);
            TreeItem<Keuangan> parent = new TreeItem<>(katKeu);
            double total = 0;
            for (Keuangan temp2 : filterData) {
                if (temp.equals(temp2.getKategori())) {
                    TreeItem<Keuangan> child = new TreeItem<>(temp2);
                    total = total + temp2.getJumlahRp();
                    parent.getChildren().addAll(child);
                }
            }
            parent.getValue().setJumlahRp(total);
            root.getChildren().add(parent);
        }
        keuanganTable.setRoot(root);
    }
    
    private void showNewKeuangan() {
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/NewKeuangan.fxml");
        NewKeuanganController x = loader.getController();
        x.setMainApp(mainApp, mainApp.MainStage, stage);
        x.saveButton.setOnAction((ActionEvent event) -> {
            if ("0".equals(x.jumlahRpField.getText().replaceAll(",", ""))
                    || "".equals(x.jumlahRpField.getText().replaceAll(",", ""))) {
                mainApp.showMessage(Modality.NONE, "Warning", "Jumlah Rp masih kosong");
            } else if (x.kategoriCombo.getSelectionModel().getSelectedItem() == null) {
                mainApp.showMessage(Modality.NONE, "Warning", "Kategori belum dipilih");
            } else if (x.salesCombo.getSelectionModel().getSelectedItem() == null) {
                mainApp.showMessage(Modality.NONE, "Warning", "Sales belum dipilih");
            } else {
                try (Connection con = Koneksi.getConnection()) {
                    String tipe = "";
                    for (KategoriTransaksi k : listKategori) {
                        if (k.getKodeKategori().equals(x.kategoriCombo.getSelectionModel().getSelectedItem())) {
                            tipe = k.getJenisTransaksi();
                        }
                    }
                    Keuangan k = new Keuangan();
                    k.setNoKeuangan(KeuanganDAO.getId(con));
                    k.setTglKeuangan(tglSql.format(new Date()));
                    k.setKategori(x.kategoriCombo.getSelectionModel().getSelectedItem());
                    k.setDeskripsi(x.keteranganField.getText());
                    if (tipe.equals("D")) {
                        k.setJumlahRp(Double.parseDouble(x.jumlahRpField.getText().replaceAll(",", "")));
                    } else if (tipe.equals("K")) {
                        k.setJumlahRp(-Double.parseDouble(x.jumlahRpField.getText().replaceAll(",", "")));
                    }
                    k.setKodeUser(x.salesCombo.getSelectionModel().getSelectedItem());
                    KeuanganDAO.insert(con, k);
                    mainApp.closeDialog(mainApp.MainStage, stage);
                    mainApp.showMessage(Modality.NONE, "Success", "Transaksi Keuangan berhasil disimpan");
                    getKeuangan();
                } catch (Exception e) {
                    mainApp.showMessage(Modality.NONE, "Error", e.toString());
                }
            }
        });
    }
    
    private void batal(Keuangan keu) {
        try {
            if (!keu.getNoKeuangan().substring(3, 9).equals(tglSystem.format(tglBarang.parse(sistem.getTglSystem())))) {
                mainApp.showMessage(Modality.NONE, "Warning", "Transaksi keuangan sudah berbeda tanggal");
            } else {
                Stage stage = new Stage();
                FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/Verifikasi.fxml");
                VerifikasiController controller = loader.getController();
                controller.setMainApp(mainApp, mainApp.MainStage, stage);
                controller.password.setOnKeyPressed((KeyEvent keyEvent) -> {
                    if (keyEvent.getCode() == KeyCode.ENTER) {
                        mainApp.closeDialog(mainApp.MainStage, stage);
                        if (Function.verifikasi(controller.username.getText(), controller.password.getText(), "Batal Transaksi")) {
                            try (Connection con = Koneksi.getConnection()) {
                                KeuanganDAO.delete(con, keu.getNoKeuangan());
                                mainApp.showMessage(Modality.NONE, "Success", "Transaksi Keuangan berhasil dibatal");
                                getKeuangan();
                            } catch (Exception ex) {
                                mainApp.showMessage(Modality.NONE, "Error", ex.toString());
                            }
                        } else {
                            mainApp.showMessage(Modality.NONE, "Warning", "Verifikasi Salah");
                        }
                    }
                });
            }
        } catch (Exception e) {
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }
    
    private void printKeuangan() {
        try {
            PrintOut printOut = new PrintOut();
            printOut.printKeuanganHarian(tglPicker.getValue().toString(),
                    Double.parseDouble(saldoAwalField.getText().replaceAll(",", "")));
        } catch (Exception e) {
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }
    
}
