/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasGunungMas.View;

import com.excellentsystem.TokoEmasGunungMas.Main;
import static com.excellentsystem.TokoEmasGunungMas.Main.sistem;
import static com.excellentsystem.TokoEmasGunungMas.Main.user;
import com.excellentsystem.TokoEmasGunungMas.Model.Otoritas;
import javafx.animation.Animation;
import javafx.animation.Transition;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Accordion;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author Xtreme
 */
public class MainController {

    @FXML
    private VBox vbox;
    @FXML
    private Label title;
    @FXML
    private Label kodeCabangLabel;

    @FXML
    private Accordion accordion;

    @FXML
    private TitledPane dashboardPane;

    @FXML
    private TitledPane barangPane;
    @FXML
    private VBox barangVbox;
    @FXML
    private MenuButton dataBarangNonBarcode;
    @FXML
    private MenuButton barcodeBarang;
    @FXML
    private MenuButton dataBarangBarcode;
    @FXML
    private MenuButton stokBarangBarcode;

    @FXML
    private TitledPane penjualanPane;
    @FXML
    private VBox penjualanVbox;
    @FXML
    private MenuButton penjualanBaru;
    @FXML
    private MenuButton dataPenjualan;

    @FXML
    private TitledPane pembelianPane;
    @FXML
    private VBox pembelianVbox;
    @FXML
    private MenuButton pembelianBaru;
    @FXML
    private MenuButton dataPembelian;

    @FXML
    private TitledPane gadaiPane;
    @FXML
    private VBox gadaiVbox;
    @FXML
    private MenuButton terimaGadai;
    @FXML
    private MenuButton dataTerimaGadai;
    @FXML
    private MenuButton pelunasanGadai;
    @FXML
    private MenuButton dataPelunasanGadai;

    @FXML
    private TitledPane keuanganPane;

    @FXML
    private TitledPane laporanPane;
    @FXML
    private VBox laporanVbox;
    @FXML
    private MenuButton laporanBarang;
    @FXML
    private MenuButton laporanPenjualan;
    @FXML
    private MenuButton laporanPembelian;
    @FXML
    private MenuButton laporanGadai;
    @FXML
    private MenuButton laporanKeuangan;

    @FXML
    private TitledPane pengaturanPane;
    @FXML
    private VBox pengaturanVbox;
    @FXML
    private MenuButton dataPelanggan;
    @FXML
    private MenuButton dataSales;
    @FXML
    private MenuButton dataUser;
    @FXML
    private MenuButton dataToko;
    @FXML
    private MenuButton dataGudang;
    @FXML
    private MenuButton kategoriBarang;
    @FXML
    private MenuButton jenisBarang;
    @FXML
    private MenuButton kategoriTransaksi;
    @FXML
    private MenuButton pengaturanGadai;

    @FXML
    private TitledPane loginButton;
    @FXML
    private VBox userVbox;
    @FXML
    private MenuButton logoutButton;
    @FXML
    private MenuButton ubahPasswordButton;

    private Main mainApp;

    public void setMainApp(Main mainApp) {
        try {
            this.mainApp = mainApp;
            vbox.setPrefWidth(0);
            for (Node n : barangVbox.getChildren()) {
                n.managedProperty().bind(n.visibleProperty());
            }
            for (Node n : penjualanVbox.getChildren()) {
                n.managedProperty().bind(n.visibleProperty());
            }
            for (Node n : pembelianVbox.getChildren()) {
                n.managedProperty().bind(n.visibleProperty());
            }
            for (Node n : gadaiVbox.getChildren()) {
                n.managedProperty().bind(n.visibleProperty());
            }
            for (Node n : laporanVbox.getChildren()) {
                n.managedProperty().bind(n.visibleProperty());
            }
            for (Node n : pengaturanVbox.getChildren()) {
                n.managedProperty().bind(n.visibleProperty());
            }
            for (Node n : userVbox.getChildren()) {
                n.managedProperty().bind(n.visibleProperty());
            }
            title.setText(sistem.getNamaToko());
            kodeCabangLabel.setText(Main.kodeToko);
            setUser();
        } catch (Exception e) {
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }

    public void setUser() {
        BorderPane border = (BorderPane) mainApp.mainLayout.getCenter();
        ImageView img = new ImageView(new Image(Main.class.getResourceAsStream("Resource/ESystemslogoonlywhite.png")));
        img.setFitHeight(150);
        img.setFitWidth(150);
        border.setCenter(img);
        if (user == null) {
            loginButton.setText("Login");
            logoutButton.setVisible(false);
            ubahPasswordButton.setVisible(false);
            mainApp.showLoginScene();

            dataBarangNonBarcode.setVisible(false);
            barcodeBarang.setVisible(false);
            dataBarangBarcode.setVisible(false);
            stokBarangBarcode.setVisible(false);

            penjualanBaru.setVisible(false);
            dataPenjualan.setVisible(false);
            pembelianBaru.setVisible(false);
            dataPembelian.setVisible(false);
            terimaGadai.setVisible(false);
            dataTerimaGadai.setVisible(false);
            pelunasanGadai.setVisible(false);
            dataPelunasanGadai.setVisible(false);

            dataPelanggan.setVisible(false);
            dataSales.setVisible(false);
            dataUser.setVisible(false);
            dataToko.setVisible(false);
            dataGudang.setVisible(false);
            kategoriBarang.setVisible(false);
            jenisBarang.setVisible(false);
            kategoriTransaksi.setVisible(false);
            pengaturanGadai.setVisible(false);

            laporanBarang.setVisible(false);
            laporanPenjualan.setVisible(false);
            laporanPembelian.setVisible(false);
            laporanGadai.setVisible(false);
            laporanKeuangan.setVisible(false);
        } else {
            logoutButton.setVisible(true);
            ubahPasswordButton.setVisible(true);
            loginButton.setText(user.getUsername());
            for (Otoritas o : user.getOtoritas()) {
                if (o.getJenis().equals("Dashboard")) {
                    if (o.isStatus()) {
                        mainApp.showDashboard();
                    } else {
                        accordion.getPanes().remove(dashboardPane);
                    }
                } else if (o.getJenis().equals("Data Barang Non Barcode")) {
                    dataBarangNonBarcode.setVisible(o.isStatus());
                } else if (o.getJenis().equals("Barcode Barang")) {
                    barcodeBarang.setVisible(o.isStatus());
                } else if (o.getJenis().equals("Data Barang Barcode")) {
                    dataBarangBarcode.setVisible(o.isStatus());
                } else if (o.getJenis().equals("Stok Barang Barcode")) {
                    stokBarangBarcode.setVisible(o.isStatus());
                } else if (o.getJenis().equals("Penjualan Baru")) {
                    penjualanBaru.setVisible(o.isStatus());
                } else if (o.getJenis().equals("Data Penjualan")) {
                    dataPenjualan.setVisible(o.isStatus());
                } else if (o.getJenis().equals("Pembelian Baru")) {
                    pembelianBaru.setVisible(o.isStatus());
                } else if (o.getJenis().equals("Data Pembelian")) {
                    dataPembelian.setVisible(o.isStatus());
                } else if (o.getJenis().equals("Terima Gadai")) {
                    terimaGadai.setVisible(o.isStatus());
                } else if (o.getJenis().equals("Data Terima Gadai")) {
                    dataTerimaGadai.setVisible(o.isStatus());
                } else if (o.getJenis().equals("Pelunasan Gadai")) {
                    pelunasanGadai.setVisible(o.isStatus());
                } else if (o.getJenis().equals("Data Pelunasan Gadai")) {
                    dataPelunasanGadai.setVisible(o.isStatus());
                } else if (o.getJenis().equals("Keuangan") && o.isStatus() == false) {
                    accordion.getPanes().remove(keuanganPane);
                } else if (o.getJenis().equals("Data Pelanggan")) {
                    dataPelanggan.setVisible(o.isStatus());
                } else if (o.getJenis().equals("Data Sales")) {
                    dataSales.setVisible(o.isStatus());
                } else if (o.getJenis().equals("Data User")) {
                    dataUser.setVisible(o.isStatus());
                } else if (o.getJenis().equals("Data Toko")) {
                    dataToko.setVisible(o.isStatus());
                } else if (o.getJenis().equals("Data Gudang")) {
                    dataGudang.setVisible(o.isStatus());
                } else if (o.getJenis().equals("Kategori Barang")) {
                    kategoriBarang.setVisible(o.isStatus());
                } else if (o.getJenis().equals("Jenis Barang")) {
                    jenisBarang.setVisible(o.isStatus());
                } else if (o.getJenis().equals("Kategori Transaksi")) {
                    kategoriTransaksi.setVisible(o.isStatus());
                } else if (o.getJenis().equals("Pengaturan Gadai")) {
                    pengaturanGadai.setVisible(o.isStatus());
                } else if (o.getJenis().equals("Laporan Barang")) {
                    laporanBarang.setVisible(o.isStatus());
                } else if (o.getJenis().equals("Laporan Penjualan")) {
                    laporanPenjualan.setVisible(o.isStatus());
                } else if (o.getJenis().equals("Laporan Pembelian")) {
                    laporanPembelian.setVisible(o.isStatus());
                } else if (o.getJenis().equals("Laporan Gadai")) {
                    laporanGadai.setVisible(o.isStatus());
                } else if (o.getJenis().equals("Laporan Keuangan")) {
                    laporanKeuangan.setVisible(o.isStatus());
                }
            }
        }
            if (barcodeBarang.isVisible() == false
                    && dataBarangNonBarcode.isVisible() == false
                    && dataBarangBarcode.isVisible() == false
                    && stokBarangBarcode.isVisible() == false) {
                accordion.getPanes().remove(barangPane);
            }
            if (penjualanBaru.isVisible() == false
                    && dataPenjualan.isVisible() == false) {
                accordion.getPanes().remove(penjualanPane);
            }
            if (pembelianBaru.isVisible() == false
                    && dataPembelian.isVisible() == false) {
                accordion.getPanes().remove(pembelianPane);
            }
            if (terimaGadai.isVisible() == false
                    && pelunasanGadai.isVisible() == false
                    && dataTerimaGadai.isVisible() == false
                    && dataPelunasanGadai.isVisible() == false) {
                accordion.getPanes().remove(gadaiPane);
            }
            if (laporanBarang.isVisible() == false
                    && laporanPenjualan.isVisible() == false
                    && laporanPembelian.isVisible() == false
                    && laporanGadai.isVisible() == false
                    && laporanKeuangan.isVisible() == false) {
                accordion.getPanes().remove(laporanPane);
            }
            if (pengaturanGadai.isVisible() == false
                    && dataPelanggan.isVisible() == false
                    && kategoriBarang.isVisible() == false
                    && jenisBarang.isVisible() == false
                    && kategoriTransaksi.isVisible() == false
                    && dataGudang.isVisible() == false
                    && dataToko.isVisible() == false
                    && dataUser.isVisible() == false
                    && dataSales.isVisible() == false) {
                accordion.getPanes().remove(pengaturanPane);
            }
    }

    @FXML
    private void showMenu() {
        final Animation hideSidebar = new Transition() {
            {
                setCycleDuration(Duration.millis(180));
            }

            @Override
            protected void interpolate(double frac) {
                final double curWidth = 220 * (1.0 - frac);
                vbox.setPrefWidth(curWidth);
                dashboardPane.setExpanded(false);
                barangPane.setExpanded(false);
                penjualanPane.setExpanded(false);
                pembelianPane.setExpanded(false);
                gadaiPane.setExpanded(false);
                keuanganPane.setExpanded(false);
                laporanPane.setExpanded(false);
                pengaturanPane.setExpanded(false);
                loginButton.setExpanded(false);
            }
        };
        final Animation showSidebar = new Transition() {
            {
                setCycleDuration(Duration.millis(180));
            }

            @Override
            protected void interpolate(double frac) {
                final double curWidth = 220 * frac;
                vbox.setPrefWidth(curWidth);
            }
        };
        if (showSidebar.statusProperty().get() == Animation.Status.STOPPED && hideSidebar.statusProperty().get() == Animation.Status.STOPPED) {
            if (vbox.getPrefWidth() != 0) {
                hideSidebar.play();
            } else {
                showSidebar.play();
            }
        }
    }

    public void setTitle(String x) {
        title.setText(x);
        if (vbox.getPrefWidth() != 0) {
            showMenu();
        }
    }

    @FXML
    private void minimize() {
        mainApp.MainStage.setIconified(true);
    }

    @FXML
    private void logout() {
        mainApp.showLoginScene();
    }

    @FXML
    private void exit() {
        System.exit(0);
    }

    @FXML
    private void showUbahPassword() {
        mainApp.showUbahPassword();
    }

    @FXML
    private void showDashboard() {
        mainApp.showDashboard();
    }

    @FXML
    private void showDataBarangNonBarcode() {
        mainApp.showDataBarangNonBarcode();
    }

    @FXML
    private void showBarcodeBarang() {
        mainApp.showBarcodeBarang();
    }

    @FXML
    private void showDataBarangBarcode() {
        mainApp.showDataBarangBarcode();
    }

    @FXML
    private void showStokBarangBarcode() {
        mainApp.showStokBarangBarcode();
    }

    @FXML
    private void showStokOpname() {
        mainApp.showStokOpname();
    }

    @FXML
    private void showPenjualanBaru() {
        mainApp.showPenjualanBaru();
    }

    @FXML
    private void showPenjualan() {
        mainApp.showDataPenjualan();
    }

    @FXML
    private void showPembelianBaru() {
        mainApp.showPembelianBaru();
    }

    @FXML
    private void showPembelian() {
        mainApp.showDataPembelian();
    }

    @FXML
    private void showTerimaGadai() {
        mainApp.showTerimaGadai();
    }

    @FXML
    private void showDataTerimaGadai() {
        mainApp.showDataTerimaGadai();
    }

    @FXML
    private void showPelunasanGadai() {
        mainApp.showPelunasanGadai();
    }

    @FXML
    private void showDataPelunasanGadai() {
        mainApp.showDataPelunasanGadai();
    }

    @FXML
    private void showKeuangan() {
        mainApp.showKeuangan();
    }

    @FXML
    private void showDataCustomer() {
        mainApp.showDataPelanggan();
    }

    @FXML
    private void showDataSales() {
        mainApp.showDataSales();
    }

    @FXML
    private void showDataUser() {
        mainApp.showDataUser();
    }

    @FXML
    private void showDataToko() {
        mainApp.showDataToko();
    }

    @FXML
    private void showDataKategoriBarang() {
        mainApp.showDataKategoriBarang();
    }

    @FXML
    private void showDataJenisBarang() {
        mainApp.showDataJenisBarang();
    }

    @FXML
    private void showDataGudang() {
        mainApp.showDataGudang();
    }

    @FXML
    private void showSettingGadai() {
        mainApp.showSettingGadai();
    }

    @FXML
    private void showDataKategoriTransaksi() {
        mainApp.showDataKategoriTransaksi();
    }

    @FXML
    private void showLaporanTambahBarang() {
        mainApp.showLaporanTambahBarang();
    }

    @FXML
    private void showLaporanAmbilBarang() {
        mainApp.showLaporanAmbilBarang();
    }

    @FXML
    private void showLaporanBarcodeBarang() {
        mainApp.showLaporanBarcodeBarang();
    }

    @FXML
    private void showLaporanCetakBarcode() {
        mainApp.showLaporanCetakBarcode();
    }

    @FXML
    private void showLaporanPindahGudang() {
        mainApp.showLaporanPindahGudang();
    }

    @FXML
    private void showLaporanHancurBarang() {
        mainApp.showLaporanHancurBarang();
    }

    @FXML
    private void showLaporanUbahHargaKategori() {
        mainApp.showLaporanUbahHargaKategori();
    }

    @FXML
    private void showLaporanPenjualan() {
        mainApp.showLaporanPenjualan();
    }

    @FXML
    private void showLaporanBatalJual() {
        mainApp.showLaporanBatalJual();
    }

    @FXML
    private void showLaporanPembelian() {
        mainApp.showLaporanPembelian();
    }

    @FXML
    private void showLaporanBatalBeli() {
        mainApp.showLaporanBatalBeli();
    }

    @FXML
    private void showLaporanTerimaGadai() {
        mainApp.showLaporanTerimaGadai();
    }

    @FXML
    private void showLaporanPelunasanGadai() {
        mainApp.showLaporanPelunasanGadai();
    }

    @FXML
    private void showLaporanStokGadai() {
        mainApp.showLaporanStokGadai();
    }

    @FXML
    private void showLaporanBatalTerimaGadai() {
        mainApp.showLaporanBatalTerimaGadai();
    }

    @FXML
    private void showLaporanKeuangan() {
        mainApp.showLaporanKeuangan();
    }

    @FXML
    private void showLaporanTransaksiSales() {
        mainApp.showLaporanTransaksiSales();
    }

    @FXML
    private void showLaporanOmzet() {
        mainApp.showLaporanOmzet();
    }
}
