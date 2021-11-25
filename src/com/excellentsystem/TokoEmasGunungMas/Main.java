/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasGunungMas;

import com.excellentsystem.TokoEmasGunungMas.DAO.GadaiHeadDAO;
import com.excellentsystem.TokoEmasGunungMas.DAO.OtoritasDAO;
import com.excellentsystem.TokoEmasGunungMas.DAO.SalesDAO;
import com.excellentsystem.TokoEmasGunungMas.DAO.SistemDAO;
import com.excellentsystem.TokoEmasGunungMas.DAO.StokBarangDAO;
import com.excellentsystem.TokoEmasGunungMas.DAO.UserDAO;
import com.excellentsystem.TokoEmasGunungMas.DAO.VerifikasiDAO;
import com.excellentsystem.TokoEmasGunungMas.Model.Otoritas;
import com.excellentsystem.TokoEmasGunungMas.Model.Sales;
import com.excellentsystem.TokoEmasGunungMas.Model.Sistem;
import com.excellentsystem.TokoEmasGunungMas.Model.User;
import com.excellentsystem.TokoEmasGunungMas.Model.Verifikasi;
import com.excellentsystem.TokoEmasGunungMas.View.BarcodeBarangController;
import com.excellentsystem.TokoEmasGunungMas.View.DashboardController;
import com.excellentsystem.TokoEmasGunungMas.View.DataBarangBarcodeController;
import com.excellentsystem.TokoEmasGunungMas.View.DataBarangNonBarcodeController;
import com.excellentsystem.TokoEmasGunungMas.View.DataPelangganController;
import com.excellentsystem.TokoEmasGunungMas.View.DataPelunasanGadaiController;
import com.excellentsystem.TokoEmasGunungMas.View.DataSalesController;
import com.excellentsystem.TokoEmasGunungMas.View.DataTerimaGadaiController;
import com.excellentsystem.TokoEmasGunungMas.View.Dialog.DataGudangController;
import com.excellentsystem.TokoEmasGunungMas.View.Dialog.DataJenisBarangController;
import com.excellentsystem.TokoEmasGunungMas.View.Dialog.DataKategoriBarangController;
import com.excellentsystem.TokoEmasGunungMas.View.Dialog.DataKategoriTransaksiController;
import com.excellentsystem.TokoEmasGunungMas.View.Dialog.DataTokoController;
import com.excellentsystem.TokoEmasGunungMas.View.Dialog.DataUserController;
import com.excellentsystem.TokoEmasGunungMas.View.Dialog.DetailGadaiController;
import com.excellentsystem.TokoEmasGunungMas.View.Dialog.DetailPelunasanGadaiController;
import com.excellentsystem.TokoEmasGunungMas.View.Dialog.DetailPembelianController;
import com.excellentsystem.TokoEmasGunungMas.View.Dialog.DetailPenjualanController;
import com.excellentsystem.TokoEmasGunungMas.View.Dialog.MessageController;
import com.excellentsystem.TokoEmasGunungMas.View.Dialog.SettingGadaiController;
import com.excellentsystem.TokoEmasGunungMas.View.Dialog.UbahPasswordController;
import com.excellentsystem.TokoEmasGunungMas.View.KeuanganController;
import com.excellentsystem.TokoEmasGunungMas.View.LoginController;
import com.excellentsystem.TokoEmasGunungMas.View.MainController;
import com.excellentsystem.TokoEmasGunungMas.View.PembelianController;
import com.excellentsystem.TokoEmasGunungMas.View.PenjualanController;
import com.excellentsystem.TokoEmasGunungMas.View.Report.LaporanAmbilBarangController;
import com.excellentsystem.TokoEmasGunungMas.View.Report.LaporanBarcodeBarangController;
import com.excellentsystem.TokoEmasGunungMas.View.Report.LaporanBatalBeliController;
import com.excellentsystem.TokoEmasGunungMas.View.Report.LaporanBatalJualController;
import com.excellentsystem.TokoEmasGunungMas.View.Report.LaporanBatalTerimaGadaiController;
import com.excellentsystem.TokoEmasGunungMas.View.Report.LaporanCetakBarcodeController;
import com.excellentsystem.TokoEmasGunungMas.View.Report.LaporanHancurBarangController;
import com.excellentsystem.TokoEmasGunungMas.View.Report.LaporanKeuanganController;
import com.excellentsystem.TokoEmasGunungMas.View.Report.LaporanOmzetController;
import com.excellentsystem.TokoEmasGunungMas.View.Report.LaporanPelunasanGadaiController;
import com.excellentsystem.TokoEmasGunungMas.View.Report.LaporanPembelianController;
import com.excellentsystem.TokoEmasGunungMas.View.Report.LaporanPenjualanController;
import com.excellentsystem.TokoEmasGunungMas.View.Report.LaporanPindahGudangController;
import com.excellentsystem.TokoEmasGunungMas.View.Report.LaporanStokGadaiController;
import com.excellentsystem.TokoEmasGunungMas.View.Report.LaporanTambahBarangController;
import com.excellentsystem.TokoEmasGunungMas.View.Report.LaporanTerimaGadaiController;
import com.excellentsystem.TokoEmasGunungMas.View.Report.LaporanTransaksiSalesController;
import com.excellentsystem.TokoEmasGunungMas.View.Report.LaporanUbahHargaKategoriController;
import com.excellentsystem.TokoEmasGunungMas.View.StokBarangBarcodeController;
import com.excellentsystem.TokoEmasGunungMas.View.StokOpnameController;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javafx.animation.Animation;
import javafx.animation.Transition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

/**
 *
 * @author Xtreme
 */
public class Main extends Application {

    public Stage MainStage;
    public Stage message;
    public Stage loading;
    public static Stage quickButton;

    public Dimension screenSize;
    public BorderPane mainLayout;
    public MainController mainController;

    private double x = 0;
    private double y = 0;

    public static DecimalFormat gr = new DecimalFormat("###,##0.##");
    public static DecimalFormat rp = new DecimalFormat("###,##0");
    public static DateFormat tglSql = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static DateFormat tglBarang = new SimpleDateFormat("yyyy-MM-dd");
    public static DateFormat tglSystem = new SimpleDateFormat("yyMMdd");
    public static DateFormat tglNormal = new SimpleDateFormat("dd MMM yyyy");
    public static DateFormat tglLengkap = new SimpleDateFormat("dd MMM yyyy HH:mm:ss");

    public static Sistem sistem;
//    public static Cabang cabang;
    public static User user;
    public static List<User> allUser;
    public static List<Sales> allSales;

    public static final String version = "1.0.0";
    public static String ipServer = "localhost";
    public static String kodeToko = "";
    public static String printerPenjualan = "LX-310";
    public static String printerGadai = "TMU-220";
    public static String printerBarcode = "GT-820";

    public void setSistem() {
        try {
            BufferedReader in = new BufferedReader(new FileReader("koneksi.txt"));
            ipServer = in.readLine();
            printerPenjualan = in.readLine();
            printerGadai = in.readLine();
            printerBarcode = in.readLine();
            try (Connection con = Koneksi.getConnection()) {
                sistem = SistemDAO.get(con);
                allUser = UserDAO.getAll(con);
                allSales = SalesDAO.getAll(con);
                List<Otoritas> listOtoritas = OtoritasDAO.getAll(con);
                List<Verifikasi> listVerifikasi = VerifikasiDAO.getAll(con);
                for (User u : allUser) {
                    List<Otoritas> otoritas = new ArrayList<>();
                    for (Otoritas o : listOtoritas) {
                        if (u.getUsername().equals(o.getUsername())) {
                            otoritas.add(o);
                        }
                    }
                    u.setOtoritas(otoritas);
                    List<Verifikasi> verifikasi = new ArrayList<>();
                    for (Verifikasi v : listVerifikasi) {
                        if (u.getUsername().equals(v.getUsername())) {
                            verifikasi.add(v);
                        }
                    }
                    u.setVerifikasi(verifikasi);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Application error - \n" + e);
            alert.showAndWait();
            System.exit(0);
        }
    }

    public void tutupToko() {
        try (Connection con = Koneksi.getConnection()) {
            while (tglBarang.parse(sistem.getTglSystem()).before(
                    tglBarang.parse(tglBarang.format(new Date())))) {
                StokBarangDAO.insertNewStokBarang(con);
                LocalDate yesterday = LocalDate.parse(sistem.getTglSystem(), DateTimeFormatter.ISO_DATE);
                LocalDate today = yesterday.plusDays(1);
                sistem.setTglSystem(today.toString());
                GadaiHeadDAO.updateBungaGadai(con);
                SistemDAO.update(con, sistem);
            }
            if (tglBarang.parse(sistem.getTglSystem()).after(
                    tglBarang.parse(tglBarang.format(new Date())))) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Application error - Tanggal system tidak cocok dengan tanggal komputer");
                alert.showAndWait();
                System.exit(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Application error - \n" + e);
            alert.showAndWait();
            System.exit(0);
        }
    }

    @Override
    public void start(Stage stage) {
        try {
            MainStage = stage;
            MainStage.setTitle("TOKO EMAS GUNUNG MAS");
            MainStage.setMaximized(true);
            MainStage.getIcons().add(new Image(Main.class.getResourceAsStream("Resource/logo.png")));
            screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            showLoginScene();
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Application error - \n" + e);
            alert.showAndWait();
        }
    }

    public void showLoginScene() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("View/Login.fxml"));
            AnchorPane container = (AnchorPane) loader.load();

            Scene scene = new Scene(container);
            final Animation animationshow = new Transition() {
                {
                    setCycleDuration(Duration.millis(1000));
                }

                @Override
                protected void interpolate(double frac) {
                    MainStage.setOpacity(1 - frac);
                }
            };
            animationshow.onFinishedProperty().set((EventHandler<ActionEvent>) (ActionEvent actionEvent) -> {
                final Animation animation = new Transition() {
                    {
                        setCycleDuration(Duration.millis(1000));
                    }

                    @Override
                    protected void interpolate(double frac) {
                        MainStage.setOpacity(frac);
                    }
                };
                animation.play();
                MainStage.hide();
                MainStage.setScene(scene);
                MainStage.show();
            });
            animationshow.play();
            LoginController controller = loader.getController();
            controller.setMainApp(this);
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(MainStage);
            alert.setTitle("Error");
            alert.setContentText("Application error - \n" + e);
            alert.showAndWait();
        }
    }

    public void showMainScene() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("View/Main.fxml"));
            mainLayout = (BorderPane) loader.load();
            Scene scene = new Scene(mainLayout);

            final Animation animationshow = new Transition() {
                {
                    setCycleDuration(Duration.millis(1000));
                }

                @Override
                protected void interpolate(double frac) {
                    MainStage.setOpacity(1 - frac);
                }
            };
            animationshow.onFinishedProperty().set((EventHandler<ActionEvent>) (ActionEvent actionEvent) -> {
                final Animation animation = new Transition() {
                    {
                        setCycleDuration(Duration.millis(1000));
                    }

                    @Override
                    protected void interpolate(double frac) {
                        MainStage.setOpacity(frac);
                    }
                };
                animation.play();
                MainStage.hide();
                MainStage.setScene(scene);
                MainStage.show();
            });
            animationshow.play();

            mainController = loader.getController();
            mainController.setMainApp(this);
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(MainStage);
            alert.setTitle("Error");
            alert.setContentText("Application error - \n" + e);
            alert.showAndWait();
        }
    }

    public void showDashboard() {
        FXMLLoader loader = changeStage("View/Dashboard.fxml");
        DashboardController controller = loader.getController();
        controller.setMainApp(this);
        mainController.setTitle("Dashboard");
    }

    public void showDataBarangNonBarcode() {
        FXMLLoader loader = changeStage("View/DataBarangNonBarcode.fxml");
        DataBarangNonBarcodeController controller = loader.getController();
        controller.setMainApp(this);
        mainController.setTitle("Data Barang Non-Barcode");
    }

    public void showBarcodeBarang() {
        FXMLLoader loader = changeStage("View/BarcodeBarang.fxml");
        BarcodeBarangController controller = loader.getController();
        controller.setMainApp(this);
        mainController.setTitle("Barcode Barang");
    }

    public void showDataBarangBarcode() {
        FXMLLoader loader = changeStage("View/DataBarangBarcode.fxml");
        DataBarangBarcodeController controller = loader.getController();
        controller.setMainApp(this);
        mainController.setTitle("Data Barang Barcode");
    }

    public void showStokBarangBarcode() {
        FXMLLoader loader = changeStage("View/StokBarangBarcode.fxml");
        StokBarangBarcodeController controller = loader.getController();
        controller.setMainApp(this);
        mainController.setTitle("Stok Barang Barcode");
    }

    public void showStokOpname() {
        FXMLLoader loader = changeStage("View/StokOpname.fxml");
        StokOpnameController controller = loader.getController();
        controller.setMainApp(this);
        mainController.setTitle("Stok Opname Barang");
    }

    public void showPenjualanBaru() {
        Stage stage = new Stage();
        FXMLLoader loader = showDialog(MainStage, stage, "View/Dialog/DetailPenjualan.fxml");
        DetailPenjualanController controller = loader.getController();
        controller.setMainApp(this, MainStage, stage);
    }

    public void showDataPenjualan() {
        FXMLLoader loader = changeStage("View/Penjualan.fxml");
        PenjualanController controller = loader.getController();
        controller.setMainApp(this);
        mainController.setTitle("Data Penjualan");
    }

    public void showPembelianBaru() {
        Stage stage = new Stage();
        FXMLLoader loader = showDialog(MainStage, stage, "View/Dialog/DetailPembelian.fxml");
        DetailPembelianController controller = loader.getController();
        controller.setMainApp(this, MainStage, stage);
    }

    public void showDataPembelian() {
        FXMLLoader loader = changeStage("View/Pembelian.fxml");
        PembelianController controller = loader.getController();
        controller.setMainApp(this);
        mainController.setTitle("Data Pembelian");
    }

    public void showTerimaGadai() {
        Stage stage = new Stage();
        FXMLLoader loader = showDialog(MainStage, stage, "View/Dialog/DetailGadai.fxml");
        DetailGadaiController controller = loader.getController();
        controller.setMainApp(this, MainStage, stage);
    }

    public void showDataTerimaGadai() {
        FXMLLoader loader = changeStage("View/DataTerimaGadai.fxml");
        DataTerimaGadaiController controller = loader.getController();
        controller.setMainApp(this);
        mainController.setTitle("Data Terima Gadai");
    }

    public void showDataPelunasanGadai() {
        FXMLLoader loader = changeStage("View/DataPelunasanGadai.fxml");
        DataPelunasanGadaiController controller = loader.getController();
        controller.setMainApp(this);
        mainController.setTitle("Data Pelunasan Gadai");
    }

    public void showPelunasanGadai() {
        Stage stage = new Stage();
        FXMLLoader loader = showDialog(MainStage, stage, "View/Dialog/DetailPelunasanGadai.fxml");
        DetailPelunasanGadaiController controller = loader.getController();
        controller.setMainApp(this, MainStage, stage);
    }

    public void showKeuangan() {
        FXMLLoader loader = changeStage("View/Keuangan.fxml");
        KeuanganController controller = loader.getController();
        controller.setMainApp(this);
        mainController.setTitle("Data Keuangan");
    }

    public void showLaporanTambahBarang() {
        FXMLLoader loader = changeStage("View/Report/LaporanTambahBarang.fxml");
        LaporanTambahBarangController controller = loader.getController();
        controller.setMainApp(this);
        mainController.setTitle("Laporan Tambah Barang");
    }

    public void showLaporanAmbilBarang() {
        FXMLLoader loader = changeStage("View/Report/LaporanAmbilBarang.fxml");
        LaporanAmbilBarangController controller = loader.getController();
        controller.setMainApp(this);
        mainController.setTitle("Laporan Ambil Barang");
    }

    public void showLaporanBarcodeBarang() {
        FXMLLoader loader = changeStage("View/Report/LaporanBarcodeBarang.fxml");
        LaporanBarcodeBarangController controller = loader.getController();
        controller.setMainApp(this);
        mainController.setTitle("Laporan Barcode Barang");
    }

    public void showLaporanCetakBarcode() {
        FXMLLoader loader = changeStage("View/Report/LaporanCetakBarcode.fxml");
        LaporanCetakBarcodeController controller = loader.getController();
        controller.setMainApp(this);
        mainController.setTitle("Laporan Cetak Barcode");
    }

    public void showLaporanPindahGudang() {
        FXMLLoader loader = changeStage("View/Report/LaporanPindahGudang.fxml");
        LaporanPindahGudangController controller = loader.getController();
        controller.setMainApp(this);
        mainController.setTitle("Laporan Pindah Gudang");
    }

    public void showLaporanHancurBarang() {
        FXMLLoader loader = changeStage("View/Report/LaporanHancurBarang.fxml");
        LaporanHancurBarangController controller = loader.getController();
        controller.setMainApp(this);
        mainController.setTitle("Laporan Hancur Barang");
    }

    public void showLaporanUbahHargaKategori() {
        FXMLLoader loader = changeStage("View/Report/LaporanUbahHargaKategori.fxml");
        LaporanUbahHargaKategoriController controller = loader.getController();
        controller.setMainApp(this);
        mainController.setTitle("Laporan Ubah Harga Kategori");
    }

    public void showLaporanPenjualan() {
        FXMLLoader loader = changeStage("View/Report/LaporanPenjualan.fxml");
        LaporanPenjualanController controller = loader.getController();
        controller.setMainApp(this);
        mainController.setTitle("Laporan Penjualan");
    }

    public void showLaporanBatalJual() {
        FXMLLoader loader = changeStage("View/Report/LaporanBatalJual.fxml");
        LaporanBatalJualController controller = loader.getController();
        controller.setMainApp(this);
        mainController.setTitle("Laporan Batal Jual");
    }

    public void showLaporanPembelian() {
        FXMLLoader loader = changeStage("View/Report/LaporanPembelian.fxml");
        LaporanPembelianController controller = loader.getController();
        controller.setMainApp(this);
        mainController.setTitle("Laporan Pembelian");
    }

    public void showLaporanBatalBeli() {
        FXMLLoader loader = changeStage("View/Report/LaporanBatalBeli.fxml");
        LaporanBatalBeliController controller = loader.getController();
        controller.setMainApp(this);
        mainController.setTitle("Laporan Batal Beli");
    }

    public void showLaporanTerimaGadai() {
        FXMLLoader loader = changeStage("View/Report/LaporanTerimaGadai.fxml");
        LaporanTerimaGadaiController controller = loader.getController();
        controller.setMainApp(this);
        mainController.setTitle("Laporan Terima Gadai");
    }

    public void showLaporanPelunasanGadai() {
        FXMLLoader loader = changeStage("View/Report/LaporanPelunasanGadai.fxml");
        LaporanPelunasanGadaiController controller = loader.getController();
        controller.setMainApp(this);
        mainController.setTitle("Laporan Pelunasan Gadai");
    }

    public void showLaporanStokGadai() {
        FXMLLoader loader = changeStage("View/Report/LaporanStokGadai.fxml");
        LaporanStokGadaiController controller = loader.getController();
        controller.setMainApp(this);
        mainController.setTitle("Laporan Stok Gadai");
    }

    public void showLaporanBatalTerimaGadai() {
        FXMLLoader loader = changeStage("View/Report/LaporanBatalTerimaGadai.fxml");
        LaporanBatalTerimaGadaiController controller = loader.getController();
        controller.setMainApp(this);
        mainController.setTitle("Laporan Batal Terima Gadai");
    }

    public void showLaporanKeuangan() {
        FXMLLoader loader = changeStage("View/Report/LaporanKeuangan.fxml");
        LaporanKeuanganController controller = loader.getController();
        controller.setMainApp(this);
        mainController.setTitle("Laporan Keuangan");
    }

    public void showLaporanTransaksiSales() {
        FXMLLoader loader = changeStage("View/Report/LaporanTransaksiSales.fxml");
        LaporanTransaksiSalesController controller = loader.getController();
        controller.setMainApp(this);
        mainController.setTitle("Laporan Transaksi Sales");
    }

    public void showLaporanOmzet() {
        FXMLLoader loader = changeStage("View/Report/LaporanOmzet.fxml");
        LaporanOmzetController controller = loader.getController();
        controller.setMainApp(this);
        mainController.setTitle("Laporan Omzet");
    }

    public void showDataPelanggan() {
        FXMLLoader loader = changeStage("View/DataPelanggan.fxml");
        DataPelangganController controller = loader.getController();
        controller.setMainApp(this);
        mainController.setTitle("Data Pelanggan");
    }

    public void showDataSales() {
        FXMLLoader loader = changeStage("View/DataSales.fxml");
        DataSalesController controller = loader.getController();
        controller.setMainApp(this);
        mainController.setTitle("Data Sales");
    }

    public void showDataUser() {
        Stage stage = new Stage();
        FXMLLoader loader = showDialog(MainStage, stage, "View/Dialog/DataUser.fxml");
        DataUserController controller = loader.getController();
        controller.setMainApp(this, MainStage, stage);
    }

    public DataTokoController showDataToko() {
        Stage stage = new Stage();
        FXMLLoader loader = showDialog(MainStage, stage, "View/Dialog/DataToko.fxml");
        DataTokoController controller = loader.getController();
        controller.setMainApp(this, MainStage, stage);
        return controller;
    }

    public DataKategoriBarangController showDataKategoriBarang() {
        Stage stage = new Stage();
        FXMLLoader loader = showDialog(MainStage, stage, "View/Dialog/DataKategoriBarang.fxml");
        DataKategoriBarangController controller = loader.getController();
        controller.setMainApp(this, MainStage, stage);
        return controller;
    }

    public DataJenisBarangController showDataJenisBarang() {
        Stage stage = new Stage();
        FXMLLoader loader = showDialog(MainStage, stage, "View/Dialog/DataJenisBarang.fxml");
        DataJenisBarangController controller = loader.getController();
        controller.setMainApp(this, MainStage, stage);
        return controller;
    }

    public DataGudangController showDataGudang() {
        Stage stage = new Stage();
        FXMLLoader loader = showDialog(MainStage, stage, "View/Dialog/DataGudang.fxml");
        DataGudangController controller = loader.getController();
        controller.setMainApp(this, MainStage, stage);
        return controller;
    }

    public SettingGadaiController showSettingGadai() {
        Stage stage = new Stage();
        FXMLLoader loader = showDialog(MainStage, stage, "View/Dialog/SettingGadai.fxml");
        SettingGadaiController controller = loader.getController();
        controller.setMainApp(this, MainStage, stage);
        return controller;
    }

    public DataKategoriTransaksiController showDataKategoriTransaksi() {
        Stage stage = new Stage();
        FXMLLoader loader = showDialog(MainStage, stage, "View/Dialog/DataKategoriTransaksi.fxml");
        DataKategoriTransaksiController controller = loader.getController();
        controller.setMainApp(this, MainStage, stage);
        return controller;
    }

    public UbahPasswordController showUbahPassword() {
        Stage stage = new Stage();
        FXMLLoader loader = showDialog(MainStage, stage, "View/Dialog/UbahPassword.fxml");
        UbahPasswordController controller = loader.getController();
        controller.setMainApp(this, MainStage, stage);
        return controller;
    }

    public FXMLLoader changeStage(String URL) {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource(URL));
            Node container = loader.load();
            BorderPane border = (BorderPane) mainLayout.getCenter();
            border.setCenter(container);
            return loader;
        } catch (Exception e) {
            showMessage(Modality.NONE, "Error", e.toString());
            return null;
        }
    }

    public FXMLLoader showDialog(Stage owner, Stage dialog, String URL) {
        try {
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.initOwner(owner);
            dialog.initStyle(StageStyle.TRANSPARENT);

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource(URL));
            AnchorPane container = (AnchorPane) loader.load();

            Scene scene = new Scene(container);
            scene.setFill(Color.TRANSPARENT);

            scene.setOnMousePressed((MouseEvent mouseEvent) -> {
                x = dialog.getX() - mouseEvent.getScreenX();
                y = dialog.getY() - mouseEvent.getScreenY();
            });
            scene.setOnMouseDragged((MouseEvent mouseEvent) -> {
                dialog.setX(mouseEvent.getScreenX() + x);
                dialog.setY(mouseEvent.getScreenY() + y);
            });
            owner.getScene().getRoot().setEffect(new ColorAdjust(0, 0, -0.5, -0.5));
            dialog.hide();
            dialog.setScene(scene);
            dialog.show();
            //set dialog on center parent
            dialog.setX((screenSize.getWidth() - dialog.getWidth()) / 2);
            dialog.setY((screenSize.getHeight() - dialog.getHeight()) / 2);
            return loader;
        } catch (IOException e) {
            showMessage(Modality.NONE, "Error", e.toString());
            return null;
        }
    }

    public void closeDialog(Stage owner, Stage dialog) {
        dialog.close();
        owner.getScene().getRoot().setEffect(new ColorAdjust(0, 0, 0, 0));
    }

    public MessageController showMessage(Modality modal, String type, String content) {
        try {
            if (message != null) {
                message.close();
            }
            message = new Stage();
            message.initModality(modal);
            message.initOwner(MainStage);
            message.initStyle(StageStyle.TRANSPARENT);

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("View/Dialog/Message.fxml"));
            AnchorPane container = (AnchorPane) loader.load();

            Scene scene = new Scene(container);
            scene.setFill(Color.TRANSPARENT);
            message.setX(screenSize.getWidth() - 430);
            message.setY(screenSize.getHeight() - 180);

            final Animation popup = new Transition() {
                {
                    setCycleDuration(Duration.millis(500));
                }

                @Override
                protected void interpolate(double frac) {
                    final double curPos = (screenSize.getHeight() - 180) * (1 - frac);
                    container.setTranslateY(curPos);
                }
            };
            popup.play();
            message.hide();
            message.setScene(scene);
            message.show();
            MessageController controller = loader.getController();
            controller.setMainApp(this, type, content);
            return controller;
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(MainStage);
            alert.setTitle("Error");
            alert.setContentText("Application error - \n" + e);
            alert.showAndWait();
            return null;
        }
    }

    public void closeMessage() {
        message.close();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
