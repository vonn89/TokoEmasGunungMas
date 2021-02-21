/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasGunungMas.View;

import com.excellentsystem.TokoEmasGunungMas.DAO.KategoriDAO;
import com.excellentsystem.TokoEmasGunungMas.DAO.KeuanganDAO;
import com.excellentsystem.TokoEmasGunungMas.DAO.PenjualanDetailDAO;
import static com.excellentsystem.TokoEmasGunungMas.Function.getTableCell;
import com.excellentsystem.TokoEmasGunungMas.Koneksi;
import com.excellentsystem.TokoEmasGunungMas.Main;
import static com.excellentsystem.TokoEmasGunungMas.Main.rp;
import static com.excellentsystem.TokoEmasGunungMas.Main.user;
import com.excellentsystem.TokoEmasGunungMas.Model.Kategori;
import com.excellentsystem.TokoEmasGunungMas.Model.Keuangan;
import com.excellentsystem.TokoEmasGunungMas.Model.Otoritas;
import com.excellentsystem.TokoEmasGunungMas.Model.PenjualanDetail;
import static java.lang.Math.abs;
import java.sql.Connection;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Modality;

/**
 * FXML Controller class
 *
 * @author excellent
 */
public class DashboardController {

    @FXML
    private Label totalPenjualanLabel;
    @FXML
    private Label totalPembelianLabel;
    @FXML
    private Label totalTerimaGadaiLabel;
    @FXML
    private Label totalPelunasanGadaiLabel;
    @FXML
    private Label totalBungaGadaiLabel;

    @FXML
    private LineChart<String, Double> omzetPenjualanChart;
    @FXML
    private CategoryAxis periodeOmzetAxis;

    @FXML
    private PieChart bestSellingItemChart;

    @FXML
    private StackedBarChart<String, Double> salesPerformanceChart;
    @FXML
    private CategoryAxis salesAxis;

    @FXML
    private TableView<Kategori> kategoriTable;
    @FXML
    private TableColumn<Kategori, String> kodeKategoriColumn;
    @FXML
    private TableColumn<Kategori, Number> hargaBeliColumn;
    @FXML
    private TableColumn<Kategori, Number> hargaJualColumn;

    @FXML
    private ComboBox<String> periodeCombo;
    private ObservableList<String> periode = FXCollections.observableArrayList();

    private ObservableList<Kategori> allKategori = FXCollections.observableArrayList();
    private Main mainApp;

    public void initialize() {
        kodeKategoriColumn.setCellValueFactory(cellData -> cellData.getValue().kodeKategoriProperty());
        hargaBeliColumn.setCellValueFactory(cellData -> cellData.getValue().hargaBeliProperty());
        hargaBeliColumn.setCellFactory(col -> getTableCell(rp));
        hargaJualColumn.setCellValueFactory(cellData -> cellData.getValue().hargaJualProperty());
        hargaJualColumn.setCellFactory(col -> getTableCell(rp));

        ContextMenu menu = new ContextMenu();
        MenuItem update = new MenuItem("Update Harga");
        update.setOnAction((ActionEvent) -> {
            updateHarga();
        });
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent) -> {
            getKategori();
        });
        for (Otoritas o : user.getOtoritas()) {
            if (o.getJenis().equals("Pengaturan Umum")) {
                menu.getItems().add(update);
            }
        }
        menu.getItems().add(refresh);
        kategoriTable.setContextMenu(menu);
        kategoriTable.setItems(allKategori);
    }

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
        periode.clear();
        periode.add("This Month");
        periode.add("Last 3 Months");
        periode.add("Last 6 Months");
        periode.add("Last 12 Months");
        periode.add("This Year");
        periode.add("All");
        periodeCombo.setItems(periode);
        periodeCombo.getSelectionModel().select("This Month");
        getData();
    }

    @FXML
    private void getData() {
        try (Connection con = Koneksi.getConnection()) {
            String tglMulai = "";
            String tglAkhir = "";
            if (periodeCombo.getSelectionModel().getSelectedItem().equals("This Month")) {
                LocalDate startdate = LocalDate.now();
                DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM");
                tglMulai = startdate.format(format) + "-01";
                LocalDate enddate = LocalDate.now();
                tglAkhir = enddate.toString();
            } else if (periodeCombo.getSelectionModel().getSelectedItem().equals("Last 3 Months")) {
                LocalDate startdate = LocalDate.now().minusMonths(2);
                DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM");
                tglMulai = startdate.format(format) + "-01";
                LocalDate enddate = LocalDate.now();
                tglAkhir = enddate.toString();
            } else if (periodeCombo.getSelectionModel().getSelectedItem().equals("Last 6 Months")) {
                LocalDate startdate = LocalDate.now().minusMonths(5);
                DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM");
                tglMulai = startdate.format(format) + "-01";
                LocalDate enddate = LocalDate.now();
                tglAkhir = enddate.toString();
            } else if (periodeCombo.getSelectionModel().getSelectedItem().equals("Last 12 Months")) {
                LocalDate startdate = LocalDate.now().minusMonths(11);
                DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM");
                tglMulai = startdate.format(format) + "-01";
                LocalDate enddate = LocalDate.now();
                tglAkhir = enddate.toString();
            } else if (periodeCombo.getSelectionModel().getSelectedItem().equals("This Year")) {
                LocalDate startdate = LocalDate.now();
                DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy");
                tglMulai = startdate.format(format) + "-01-01";
                LocalDate enddate = LocalDate.now();
                tglAkhir = enddate.toString();
            } else if (periodeCombo.getSelectionModel().getSelectedItem().equals("All")) {
                tglMulai = "2000-01-01";
                LocalDate enddate = LocalDate.now();
                tglAkhir = enddate.toString();
            }

            List<PenjualanDetail> listPenjualanDetail = PenjualanDetailDAO.getAllByTglPenjualanAndStatus(con, tglMulai, tglAkhir, "true");
            double totalPenjualan = 0;
            double totalPembelian = 0;
            double totalTerimaGadai = 0;
            double totalPelunasanGadai = 0;
            double totalBungaGadai = 0;
            List<Keuangan> listKeuangan = KeuanganDAO.getAllByDate(con, tglMulai, tglAkhir);
            for (Keuangan k : listKeuangan) {
                if (k.getKategori().equals("Penjualan")) {
                    totalPenjualan = totalPenjualan + k.getJumlahRp();
                }
                if (k.getKategori().equals("Batal Penjualan")) {
                    totalPenjualan = totalPenjualan + k.getJumlahRp();
                }

                if (k.getKategori().equals("Pembelian")) {
                    totalPembelian = totalPembelian + k.getJumlahRp();
                }
                if (k.getKategori().equals("Batal Pembelian")) {
                    totalPembelian = totalPembelian + k.getJumlahRp();
                }

                if (k.getKategori().equals("Terima Gadai")) {
                    totalTerimaGadai = totalTerimaGadai + k.getJumlahRp();
                }
                if (k.getKategori().equals("Batal Terima Gadai")) {
                    totalTerimaGadai = totalTerimaGadai + k.getJumlahRp();
                }

                if (k.getKategori().equals("Pelunasan Gadai")) {
                    totalPelunasanGadai = totalPelunasanGadai + k.getJumlahRp();
                }
                if (k.getKategori().equals("Batal Pelunasan Gadai")) {
                    totalPelunasanGadai = totalPelunasanGadai + k.getJumlahRp();
                }

                if (k.getKategori().equals("Bunga Gadai")) {
                    totalBungaGadai = totalBungaGadai + k.getJumlahRp();
                }
                if (k.getKategori().equals("Batal Bunga Gadai")) {
                    totalBungaGadai = totalBungaGadai + k.getJumlahRp();
                }
            }
            setLabelHeader(totalPenjualan, totalPembelian, totalTerimaGadai, totalPelunasanGadai, totalBungaGadai);
            setOmzetPenjualan(listKeuangan);
            setSalesPerformance(listKeuangan);
            setBestSellingItems(listPenjualanDetail);
            getKategori();
        } catch (Exception ex) {
            mainApp.showMessage(Modality.NONE, "Error", ex.toString());
        }
    }

    private void setLabelHeader(double totalPenjualan, double totalPembelian, double totalTerimaGadai, double totalPelunasanGadai, double totalBungaGadai) {
        DecimalFormat df = new DecimalFormat("###,##0.##");
        String a = new DecimalFormat("###,##0").format(totalPenjualan);
        String b = new DecimalFormat("###,##0").format(totalPembelian);
        String c = new DecimalFormat("###,##0").format(totalTerimaGadai);
        String d = new DecimalFormat("###,##0").format(totalPelunasanGadai);
        String e = new DecimalFormat("###,##0").format(totalBungaGadai);
        if (totalPenjualan / 1000000000 >= 1 || totalPenjualan / 1000000000 <= -1) {
            a = df.format(totalPenjualan / 1000000000) + " M";
        }
        if (totalPembelian / 1000000000 >= 1 || totalPembelian / 1000000000 <= -1) {
            b = df.format(totalPembelian / 1000000000) + " M";
        }
        if (totalTerimaGadai / 1000000000 >= 1 || totalTerimaGadai / 1000000000 <= -1) {
            c = df.format(totalTerimaGadai / 1000000000) + " M";
        }
        if (totalPelunasanGadai / 1000000000 >= 1 || totalPelunasanGadai / 1000000000 <= -1) {
            d = df.format(totalPelunasanGadai / 1000000000) + " M";
        }
        if (totalBungaGadai / 1000000000 >= 1 || totalBungaGadai / 1000000000 <= -1) {
            e = df.format(totalBungaGadai / 1000000000) + " M";
        }

        totalPenjualanLabel.setText(a);
        totalPembelianLabel.setText(b);
        totalTerimaGadaiLabel.setText(c);
        totalPelunasanGadaiLabel.setText(d);
        totalBungaGadaiLabel.setText(e);
    }

    private void setOmzetPenjualan(List<Keuangan> listKeuangan) throws Exception {
        omzetPenjualanChart.getData().clear();
        periodeOmzetAxis.getCategories().clear();
        for (Keuangan k : listKeuangan) {
            if (!periodeOmzetAxis.getCategories().contains(new SimpleDateFormat("MMM yyyy").format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(k.getTglKeuangan())))) {
                periodeOmzetAxis.getCategories().add(new SimpleDateFormat("MMM yyyy").format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(k.getTglKeuangan())));
            }
        }
        XYChart.Series series1 = new XYChart.Series<>();
        series1.setName("Penjualan");
        for (String s : periodeOmzetAxis.getCategories()) {
            double totalPenjualan = 0;
            for (Keuangan p : listKeuangan) {
                if (p.getKategori().equals("Penjualan")) {
                    if (s.equals(new SimpleDateFormat("MMM yyyy").format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(p.getTglKeuangan())))) {
                        totalPenjualan = totalPenjualan + p.getJumlahRp();
                    }
                }
                if (p.getKategori().equals("Batal Penjualan")) {
                    if (s.equals(new SimpleDateFormat("MMM yyyy").format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(p.getTglKeuangan())))) {
                        totalPenjualan = totalPenjualan + p.getJumlahRp();
                    }
                }
            }
            if (totalPenjualan != 0) {
                series1.getData().add(new XYChart.Data<>(s, totalPenjualan));
            }
        }
        if (!series1.getData().isEmpty()) {
            omzetPenjualanChart.getData().add(series1);
        }

        XYChart.Series series2 = new XYChart.Series<>();
        series2.setName("Pembelian");
        for (String s : periodeOmzetAxis.getCategories()) {
            double totalPembelian = 0;
            for (Keuangan p : listKeuangan) {
                if (p.getKategori().equals("Pembelian")) {
                    if (s.equals(new SimpleDateFormat("MMM yyyy").format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(p.getTglKeuangan())))) {
                        totalPembelian = totalPembelian + p.getJumlahRp();
                    }
                }
                if (p.getKategori().equals("Batal Pembelian")) {
                    if (s.equals(new SimpleDateFormat("MMM yyyy").format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(p.getTglKeuangan())))) {
                        totalPembelian = totalPembelian + p.getJumlahRp();
                    }
                }
            }
            if (totalPembelian != 0) {
                series2.getData().add(new XYChart.Data<>(s, abs(totalPembelian)));
            }
        }
        if (!series2.getData().isEmpty()) {
            omzetPenjualanChart.getData().add(series2);
        }

        XYChart.Series series3 = new XYChart.Series<>();
        series3.setName("Terima Gadai");
        for (String s : periodeOmzetAxis.getCategories()) {
            double totalTerimaGadai = 0;
            for (Keuangan p : listKeuangan) {
                if (p.getKategori().equals("Terima Gadai")) {
                    if (s.equals(new SimpleDateFormat("MMM yyyy").format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(p.getTglKeuangan())))) {
                        totalTerimaGadai = totalTerimaGadai + p.getJumlahRp();
                    }
                }
                if (p.getKategori().equals("Batal Terima Gadai")) {
                    if (s.equals(new SimpleDateFormat("MMM yyyy").format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(p.getTglKeuangan())))) {
                        totalTerimaGadai = totalTerimaGadai + p.getJumlahRp();
                    }
                }
            }
            if (totalTerimaGadai != 0) {
                series3.getData().add(new XYChart.Data<>(s, abs(totalTerimaGadai)));
            }
        }
        if (!series3.getData().isEmpty()) {
            omzetPenjualanChart.getData().add(series3);
        }

        XYChart.Series series4 = new XYChart.Series<>();
        series4.setName("Pelunasan Gadai");
        for (String s : periodeOmzetAxis.getCategories()) {
            double totalPelunasanGadai = 0;
            for (Keuangan p : listKeuangan) {
                if (p.getKategori().equals("Pelunasan Gadai")) {
                    if (s.equals(new SimpleDateFormat("MMM yyyy").format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(p.getTglKeuangan())))) {
                        totalPelunasanGadai = totalPelunasanGadai + p.getJumlahRp();
                    }
                }
                if (p.getKategori().equals("Batal Pelunasan Gadai")) {
                    if (s.equals(new SimpleDateFormat("MMM yyyy").format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(p.getTglKeuangan())))) {
                        totalPelunasanGadai = totalPelunasanGadai + p.getJumlahRp();
                    }
                }
            }
            if (totalPelunasanGadai != 0) {
                series4.getData().add(new XYChart.Data<>(s, totalPelunasanGadai));
            }
        }
        if (!series4.getData().isEmpty()) {
            omzetPenjualanChart.getData().add(series4);
        }

        XYChart.Series series5 = new XYChart.Series<>();
        series5.setName("Bunga Gadai");
        for (String s : periodeOmzetAxis.getCategories()) {
            double totalPenjualan = 0;
            for (Keuangan p : listKeuangan) {
                if (p.getKategori().equals("Bunga Gadai")) {
                    if (s.equals(new SimpleDateFormat("MMM yyyy").format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(p.getTglKeuangan())))) {
                        totalPenjualan = totalPenjualan + p.getJumlahRp();
                    }
                }
                if (p.getKategori().equals("Batal Bunga Gadai")) {
                    if (s.equals(new SimpleDateFormat("MMM yyyy").format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(p.getTglKeuangan())))) {
                        totalPenjualan = totalPenjualan + p.getJumlahRp();
                    }
                }
            }
            if (totalPenjualan != 0) {
                series5.getData().add(new XYChart.Data<>(s, totalPenjualan));
            }
        }
        if (!series5.getData().isEmpty()) {
            omzetPenjualanChart.getData().add(series5);
        }
    }

    private void setSalesPerformance(List<Keuangan> listPenjualan) throws Exception {
        salesPerformanceChart.getData().clear();
        salesAxis.getCategories().clear();
        for (Keuangan p : listPenjualan) {
            if (!salesAxis.getCategories().contains(p.getKodeUser())) {
                salesAxis.getCategories().add(p.getKodeUser());
            }
        }
        XYChart.Series series1 = new XYChart.Series<>();
        series1.setName("Penjualan");
        for (String s : salesAxis.getCategories()) {
            double totalPenjualan = 0;
            for (Keuangan p : listPenjualan) {
                if (p.getKategori().equals("Penjualan")) {
                    if (s.equals(p.getKodeUser())) {
                        totalPenjualan = totalPenjualan + p.getJumlahRp();
                    }
                }
                if (p.getKategori().equals("Batal Penjualan")) {
                    if (s.equals(p.getKodeUser())) {
                        totalPenjualan = totalPenjualan + p.getJumlahRp();
                    }
                }
            }
            if (totalPenjualan != 0) {
                series1.getData().add(new XYChart.Data<>(s, totalPenjualan));
            }
        }
        if (!series1.getData().isEmpty()) {
            salesPerformanceChart.getData().add(series1);
        }

        XYChart.Series series2 = new XYChart.Series<>();
        series2.setName("Pembelian");
        for (String s : salesAxis.getCategories()) {
            double totalPenjualan = 0;
            for (Keuangan p : listPenjualan) {
                if (p.getKategori().equals("Pembelian")) {
                    if (s.equals(p.getKodeUser())) {
                        totalPenjualan = totalPenjualan + p.getJumlahRp();
                    }
                }
                if (p.getKategori().equals("Batal Pembelian")) {
                    if (s.equals(p.getKodeUser())) {
                        totalPenjualan = totalPenjualan + p.getJumlahRp();
                    }
                }
            }
            if (totalPenjualan != 0) {
                series2.getData().add(new XYChart.Data<>(s, abs(totalPenjualan)));
            }
        }
        if (!series2.getData().isEmpty()) {
            salesPerformanceChart.getData().add(series2);
        }

        XYChart.Series series3 = new XYChart.Series<>();
        series3.setName("Terima Gadai");
        for (String s : salesAxis.getCategories()) {
            double totalPenjualan = 0;
            for (Keuangan p : listPenjualan) {
                if (p.getKategori().equals("Terima Gadai")) {
                    if (s.equals(p.getKodeUser())) {
                        totalPenjualan = totalPenjualan + p.getJumlahRp();
                    }
                }
                if (p.getKategori().equals("Batal Terima Gadai")) {
                    if (s.equals(p.getKodeUser())) {
                        totalPenjualan = totalPenjualan + p.getJumlahRp();
                    }
                }
            }
            if (totalPenjualan != 0) {
                series3.getData().add(new XYChart.Data<>(s, abs(totalPenjualan)));
            }
        }
        if (!series3.getData().isEmpty()) {
            salesPerformanceChart.getData().add(series3);
        }

        XYChart.Series series4 = new XYChart.Series<>();
        series4.setName("Pelunasan Gadai");
        for (String s : salesAxis.getCategories()) {
            double totalPenjualan = 0;
            for (Keuangan p : listPenjualan) {
                if (p.getKategori().equals("Pelunasan Gadai")) {
                    if (s.equals(p.getKodeUser())) {
                        totalPenjualan = totalPenjualan + p.getJumlahRp();
                    }
                }
                if (p.getKategori().equals("Batal Pelunasan Gadai")) {
                    if (s.equals(p.getKodeUser())) {
                        totalPenjualan = totalPenjualan + p.getJumlahRp();
                    }
                }
            }
            if (totalPenjualan != 0) {
                series4.getData().add(new XYChart.Data<>(s, totalPenjualan));
            }
        }
        if (!series4.getData().isEmpty()) {
            salesPerformanceChart.getData().add(series4);
        }

        XYChart.Series series5 = new XYChart.Series<>();
        series5.setName("Bunga Gadai");
        for (String s : salesAxis.getCategories()) {
            double totalPenjualan = 0;
            for (Keuangan p : listPenjualan) {
                if (p.getKategori().equals("Bunga Gadai")) {
                    if (s.equals(p.getKodeUser())) {
                        totalPenjualan = totalPenjualan + p.getJumlahRp();
                    }
                }
                if (p.getKategori().equals("Batal Bunga Gadai")) {
                    if (s.equals(p.getKodeUser())) {
                        totalPenjualan = totalPenjualan + p.getJumlahRp();
                    }
                }
            }
            if (totalPenjualan != 0) {
                series5.getData().add(new XYChart.Data<>(s, totalPenjualan));
            }
        }
        if (!series5.getData().isEmpty()) {
            salesPerformanceChart.getData().add(series5);
        }
    }

    private void setBestSellingItems(List<PenjualanDetail> listPenjualanDetail) throws Exception {
        bestSellingItemChart.getData().clear();
        HashMap hm = new HashMap();
        for (PenjualanDetail d : listPenjualanDetail) {
            if (hm.get(d.getKodeJenis()) == null) {
                hm.put(d.getKodeJenis(), d.getBerat());
            } else {
                hm.put(d.getKodeJenis(), (Double) hm.get(d.getKodeJenis()) + d.getBerat());
            }
        }
        Iterator i = hm.entrySet().iterator();
        while (i.hasNext()) {
            Map.Entry me = (Map.Entry) i.next();
            bestSellingItemChart.getData().add(new PieChart.Data(me.getKey().toString(), (Double) me.getValue()));
        }
    }

    private void getKategori() {
        try (Connection con = Koneksi.getConnection()) {
            allKategori.clear();
            allKategori.addAll(KategoriDAO.getAll(con));
            kategoriTable.refresh();
        } catch (Exception e) {
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }

    private void updateHarga() {
        mainApp.showDataKategoriBarang();
    }
}
