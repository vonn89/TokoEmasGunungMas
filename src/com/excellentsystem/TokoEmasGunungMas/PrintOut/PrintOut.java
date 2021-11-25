/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasGunungMas.PrintOut;

import com.excellentsystem.TokoEmasGunungMas.Koneksi;
import com.excellentsystem.TokoEmasGunungMas.Main;
import static com.excellentsystem.TokoEmasGunungMas.Main.sistem;
import static com.excellentsystem.TokoEmasGunungMas.Main.tglBarang;
import static com.excellentsystem.TokoEmasGunungMas.Main.tglNormal;
import static com.excellentsystem.TokoEmasGunungMas.Main.tglSql;
import com.excellentsystem.TokoEmasGunungMas.Model.AmbilBarangDetail;
import com.excellentsystem.TokoEmasGunungMas.Model.Barang;
import com.excellentsystem.TokoEmasGunungMas.Model.CetakBarcodeDetail;
import com.excellentsystem.TokoEmasGunungMas.Model.GadaiDetail;
import com.excellentsystem.TokoEmasGunungMas.Model.GadaiHead;
import com.excellentsystem.TokoEmasGunungMas.Model.HancurDetail;
import com.excellentsystem.TokoEmasGunungMas.Model.Keuangan;
import com.excellentsystem.TokoEmasGunungMas.Model.PembelianDetail;
import com.excellentsystem.TokoEmasGunungMas.Model.PenjualanDetail;
import com.excellentsystem.TokoEmasGunungMas.Model.PenjualanHead;
import com.excellentsystem.TokoEmasGunungMas.Model.PindahDetail;
import com.excellentsystem.TokoEmasGunungMas.Model.StokBarang;
import com.excellentsystem.TokoEmasGunungMas.Model.TambahBarangDetail;
import java.awt.print.PrinterJob;
import java.sql.Connection;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Copies;
import javax.print.attribute.standard.MediaPrintableArea;
import javax.print.attribute.standard.MediaSize;
import javax.print.attribute.standard.MediaSizeName;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRPrintServiceExporter;
import net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import simple.escp.SimpleEscp;
import simple.escp.json.JsonTemplate;

/**
 *
 * @author Yunaz
 */
public class PrintOut {

    public String angka(int satuan) {
        String[] huruf = {"", "Satu", "Dua", "Tiga", "Empat", "Lima", "Enam", "Tujuh", "Delapan", "Sembilan", "Sepuluh", "Sebelas"};
        String hasil = "";
        if (satuan < 12) {
            hasil = hasil + huruf[satuan];
        } else if (satuan < 20) {
            hasil = hasil + angka(satuan - 10) + " Belas";
        } else if (satuan < 100) {
            hasil = hasil + angka(satuan / 10) + " Puluh " + angka(satuan % 10);
        } else if (satuan < 200) {
            hasil = hasil + "Seratus " + angka(satuan - 100);
        } else if (satuan < 1000) {
            hasil = hasil + angka(satuan / 100) + " Ratus " + angka(satuan % 100);
        } else if (satuan < 2000) {
            hasil = hasil + "Seribu " + angka(satuan - 1000);
        } else if (satuan < 1000000) {
            hasil = hasil + angka(satuan / 1000) + " Ribu " + angka(satuan % 1000);
        } else if (satuan < 1000000000) {
            hasil = hasil + angka(satuan / 1000000) + " Juta " + angka(satuan % 1000000);
        } else if (satuan >= 1000000000) {
            hasil = "Angka terlalu besar, harus kurang dari 1 milyar!";
        }
        return hasil;
    }

    public void printSuratPenjualan(PenjualanHead p) throws Exception {
        DecimalFormat rp = new DecimalFormat("###,##0");
        DecimalFormat gr = new DecimalFormat("#,##0.00");
        DateFormat jam = new SimpleDateFormat("HH:mm");
        Map<String, Object> map = new HashMap<>();
        map.put("noPenjualan", p.getNoPenjualan());
        map.put("tglPenjualan", tglNormal.format(
                tglSql.parse(p.getTglPenjualan())));
        map.put("jam", jam.format(tglSql.parse(p.getTglPenjualan())));
        map.put("nama", p.getNama());
        map.put("alamat", p.getAlamat());
        map.put("noTelp", p.getNoTelp());
        map.put("sales", p.getKodeSales());
        int total = Integer.parseInt(new DecimalFormat("##0").format(p.getGrandtotal()));
        map.put("terbilang", angka(total));
        map.put("total", rp.format(p.getGrandtotal()));
        List<Map<String, Object>> tables = new ArrayList<>();
        for (PenjualanDetail d : p.getAllDetail()) {
            Map<String, Object> line = new HashMap<>();
            line.put("qty", "1");
            line.put("namaBarang", d.getNamaBarang());
            line.put("berat", gr.format(d.getBerat()) + " Gr");
            line.put("kadar", d.getBarang().getKadar());
            line.put("harga", rp.format(d.getHargaJual()));
            tables.add(line);
            Map<String, Object> line2 = new HashMap<>();
            line2.put("qty", "");
            line2.put("namaBarang", "");
            line2.put("berat", "");
            line2.put("kadar", "");
            line2.put("harga", "");
            tables.add(line2);
        }
        while (tables.size() < 6) {
            Map<String, Object> line = new HashMap<>();
            line.put("qty", "");
            line.put("namaBarang", "");
            line.put("berat", "");
            line.put("kadar", "");
            line.put("harga", "");
            tables.add(line);
            Map<String, Object> line2 = new HashMap<>();
            line2.put("qty", "");
            line2.put("namaBarang", "");
            line2.put("berat", "");
            line2.put("kadar", "");
            line2.put("harga", "");
            tables.add(line2);
        }
        map.put("table", tables);
        SimpleEscp simpleEscp = new SimpleEscp(Main.printerPenjualan);
        JsonTemplate template = new JsonTemplate(getClass().getResourceAsStream("template.json"));
        simpleEscp.print(template, map);
    }

    public void printBuktiPembelian(List<PembelianDetail> allDetail) throws Exception {
        JasperDesign jasperDesign = JRXmlLoader.load(getClass().getResourceAsStream("BuktiPembelianUmum.jrxml"));
        JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(allDetail);
        HashMap hash = new HashMap();
        hash.put("namaToko", sistem.getNamaToko());
        hash.put("alamatToko", sistem.getAlamatToko());
        hash.put("noTelpToko", sistem.getNoTelpToko());
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, hash, beanColDataSource);
        PrinterJob job = PrinterJob.getPrinterJob();
        PrintService[] services = PrintServiceLookup.lookupPrintServices(null, null);
        int selectedService = 0;
        for (int i = 0; i < services.length; i++) {
            if (services[i].getName().toUpperCase().contains(Main.printerGadai)) {
                selectedService = i;
            }
        }
        job.setPrintService(services[selectedService]);
        PrintRequestAttributeSet printRequestAttributeSet = new HashPrintRequestAttributeSet();
        MediaSizeName mediaSizeName = MediaSize.findMedia(1000, 1000, MediaPrintableArea.MM);
        printRequestAttributeSet.add(mediaSizeName);
        printRequestAttributeSet.add(new Copies(1));
        JRPrintServiceExporter exporter;
        exporter = new JRPrintServiceExporter();
        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        exporter.setParameter(JRPrintServiceExporterParameter.PRINT_SERVICE, services[selectedService]);
        exporter.setParameter(JRPrintServiceExporterParameter.PRINT_SERVICE_ATTRIBUTE_SET, services[selectedService].getAttributes());
        exporter.setParameter(JRPrintServiceExporterParameter.PRINT_REQUEST_ATTRIBUTE_SET, printRequestAttributeSet);
        exporter.setParameter(JRPrintServiceExporterParameter.DISPLAY_PAGE_DIALOG, Boolean.FALSE);
        exporter.setParameter(JRPrintServiceExporterParameter.DISPLAY_PRINT_DIALOG, Boolean.FALSE);
        exporter.exportReport();
    }

    public void printSuratHutang(List<GadaiDetail> gadai) throws Exception {
        JasperDesign jasperDesign = JRXmlLoader.load(getClass().getResourceAsStream("SuratHutang.jrxml"));
        JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(gadai);
        HashMap hash = new HashMap();
        hash.put("namaToko", sistem.getNamaToko());
        hash.put("alamatToko", sistem.getAlamatToko());
        hash.put("noTelpToko", sistem.getNoTelpToko());
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, hash, beanColDataSource);
        PrinterJob job = PrinterJob.getPrinterJob();
        PrintService[] services = PrintServiceLookup.lookupPrintServices(null, null);
        int selectedService = 0;
        for (int i = 0; i < services.length; i++) {
            if (services[i].getName().toUpperCase().contains(Main.printerGadai)) {
                selectedService = i;
            }
        }
        job.setPrintService(services[selectedService]);
        PrintRequestAttributeSet printRequestAttributeSet = new HashPrintRequestAttributeSet();
        MediaSizeName mediaSizeName = MediaSize.findMedia(1000, 1000, MediaPrintableArea.MM);
        printRequestAttributeSet.add(mediaSizeName);
        printRequestAttributeSet.add(new Copies(1));
        JRPrintServiceExporter exporter;
        exporter = new JRPrintServiceExporter();
        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        exporter.setParameter(JRPrintServiceExporterParameter.PRINT_SERVICE, services[selectedService]);
        exporter.setParameter(JRPrintServiceExporterParameter.PRINT_SERVICE_ATTRIBUTE_SET, services[selectedService].getAttributes());
        exporter.setParameter(JRPrintServiceExporterParameter.PRINT_REQUEST_ATTRIBUTE_SET, printRequestAttributeSet);
        exporter.setParameter(JRPrintServiceExporterParameter.DISPLAY_PAGE_DIALOG, Boolean.FALSE);
        exporter.setParameter(JRPrintServiceExporterParameter.DISPLAY_PRINT_DIALOG, Boolean.FALSE);
        exporter.exportReport();
    }

    public void printSuratHutangInternal(List<GadaiDetail> gadai) throws Exception {
        JasperDesign jasperDesign = JRXmlLoader.load(getClass().getResourceAsStream("SuratHutangIntern.jrxml"));
        JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(gadai);
        HashMap hash = new HashMap();
        hash.put("namaToko", sistem.getNamaToko());
        hash.put("alamatToko", sistem.getAlamatToko());
        hash.put("noTelpToko", sistem.getNoTelpToko());
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, hash, beanColDataSource);
        PrinterJob job = PrinterJob.getPrinterJob();
        PrintService[] services = PrintServiceLookup.lookupPrintServices(null, null);
        int selectedService = 0;
        for (int i = 0; i < services.length; i++) {
            if (services[i].getName().toUpperCase().contains(Main.printerGadai)) {
                selectedService = i;
            }
        }
        job.setPrintService(services[selectedService]);
        PrintRequestAttributeSet printRequestAttributeSet = new HashPrintRequestAttributeSet();
        MediaSizeName mediaSizeName = MediaSize.findMedia(1000, 1000, MediaPrintableArea.MM);
        printRequestAttributeSet.add(mediaSizeName);
        printRequestAttributeSet.add(new Copies(1));
        JRPrintServiceExporter exporter;
        exporter = new JRPrintServiceExporter();
        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        exporter.setParameter(JRPrintServiceExporterParameter.PRINT_SERVICE, services[selectedService]);
        exporter.setParameter(JRPrintServiceExporterParameter.PRINT_SERVICE_ATTRIBUTE_SET, services[selectedService].getAttributes());
        exporter.setParameter(JRPrintServiceExporterParameter.PRINT_REQUEST_ATTRIBUTE_SET, printRequestAttributeSet);
        exporter.setParameter(JRPrintServiceExporterParameter.DISPLAY_PAGE_DIALOG, Boolean.FALSE);
        exporter.setParameter(JRPrintServiceExporterParameter.DISPLAY_PRINT_DIALOG, Boolean.FALSE);
        exporter.exportReport();
    }

    public void printBuktiPelunasanGadai(List<GadaiDetail> gadai) throws Exception {
        JasperDesign jasperDesign = JRXmlLoader.load(getClass().getResourceAsStream("BuktiPelunasanGadai.jrxml"));
        JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(gadai);
        HashMap hash = new HashMap();
        hash.put("namaToko", sistem.getNamaToko());
        hash.put("alamatToko", sistem.getAlamatToko());
        hash.put("noTelpToko", sistem.getNoTelpToko());
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, hash, beanColDataSource);
        PrinterJob job = PrinterJob.getPrinterJob();
        PrintService[] services = PrintServiceLookup.lookupPrintServices(null, null);
        int selectedService = 0;
        for (int i = 0; i < services.length; i++) {
            if (services[i].getName().toUpperCase().contains(Main.printerGadai)) {
                selectedService = i;
            }
        }
        job.setPrintService(services[selectedService]);
        PrintRequestAttributeSet printRequestAttributeSet = new HashPrintRequestAttributeSet();
        MediaSizeName mediaSizeName = MediaSize.findMedia(1000, 1000, MediaPrintableArea.MM);
        printRequestAttributeSet.add(mediaSizeName);
        printRequestAttributeSet.add(new Copies(1));
        JRPrintServiceExporter exporter;
        exporter = new JRPrintServiceExporter();
        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        exporter.setParameter(JRPrintServiceExporterParameter.PRINT_SERVICE, services[selectedService]);
        exporter.setParameter(JRPrintServiceExporterParameter.PRINT_SERVICE_ATTRIBUTE_SET, services[selectedService].getAttributes());
        exporter.setParameter(JRPrintServiceExporterParameter.PRINT_REQUEST_ATTRIBUTE_SET, printRequestAttributeSet);
        exporter.setParameter(JRPrintServiceExporterParameter.DISPLAY_PAGE_DIALOG, Boolean.FALSE);
        exporter.setParameter(JRPrintServiceExporterParameter.DISPLAY_PRINT_DIALOG, Boolean.FALSE);
        exporter.exportReport();
    }

    public void printKeuanganHarian(String tanggal, double saldoAwal) throws Exception {
        try (Connection con = Koneksi.getConnection()) {
            JasperDesign jasperDesign = JRXmlLoader.load(getClass().getResourceAsStream("LaporanKeuanganHarianSummary.jrxml"));
            String sql = "select kategori,tipe_transaksi,sum(jumlah_rp) from tt_keuangan "
                    + " where left(tgl_keuangan,10) like '" + tanggal + "'  group by kategori order by no_keuangan";
            HashMap hash = new HashMap();
            hash.put("tanggal", tglNormal.format(tglBarang.parse(tanggal)));
            hash.put("saldoAwal", saldoAwal);
            hash.put("namaToko", sistem.getNamaToko());
            hash.put("alamatToko", sistem.getAlamatToko());
            hash.put("noTelpToko", sistem.getNoTelpToko());

            JRDesignQuery newQuery = new JRDesignQuery();
            newQuery.setText(sql);
            jasperDesign.setQuery(newQuery);
            JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, hash, con);
            PrinterJob job = PrinterJob.getPrinterJob();
            PrintService[] services = PrintServiceLookup.lookupPrintServices(null, null);
            int selectedService = 0;
            for (int i = 0; i < services.length; i++) {
                if (services[i].getName().toUpperCase().contains(Main.printerGadai)) {
                    selectedService = i;
                }
            }
            job.setPrintService(services[selectedService]);
            PrintRequestAttributeSet printRequestAttributeSet = new HashPrintRequestAttributeSet();
            MediaSizeName mediaSizeName = MediaSize.findMedia(1000, 1000, MediaPrintableArea.MM);
            printRequestAttributeSet.add(mediaSizeName);
            printRequestAttributeSet.add(new Copies(1));
            JRPrintServiceExporter exporter;
            exporter = new JRPrintServiceExporter();
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
            exporter.setParameter(JRPrintServiceExporterParameter.PRINT_SERVICE, services[selectedService]);
            exporter.setParameter(JRPrintServiceExporterParameter.PRINT_SERVICE_ATTRIBUTE_SET, services[selectedService].getAttributes());
            exporter.setParameter(JRPrintServiceExporterParameter.PRINT_REQUEST_ATTRIBUTE_SET, printRequestAttributeSet);
            exporter.setParameter(JRPrintServiceExporterParameter.DISPLAY_PAGE_DIALOG, Boolean.FALSE);
            exporter.setParameter(JRPrintServiceExporterParameter.DISPLAY_PRINT_DIALOG, Boolean.FALSE);
            exporter.exportReport();
        }
    }

    public void printPembelian(String tglMulai, String tglAkhir) throws Exception {
        try (Connection con = Koneksi.getConnection()) {
            JasperDesign jasperDesign = JRXmlLoader.load(getClass().getResourceAsStream("PembelianUmum.jrxml"));
            String sql = "select * from tt_pembelian_head a , tt_pembelian_detail b "
                    + " where a.no_pembelian=b.no_pembelian and "
                    + " left(a.tgl_pembelian,10) between '" + tglMulai + "' and '" + tglAkhir + "' and a.status ='true'"
                    + " order by a.kode_sales,b.kode_kategori,b.kode_jenis";

            HashMap hash = new HashMap();
            hash.put("namaToko", sistem.getNamaToko());
            hash.put("alamatToko", sistem.getAlamatToko());
            hash.put("noTelpToko", sistem.getNoTelpToko());
            JRDesignQuery newQuery = new JRDesignQuery();
            newQuery.setText(sql);
            jasperDesign.setQuery(newQuery);
            JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, hash, con);
            PrinterJob job = PrinterJob.getPrinterJob();
            PrintService[] services = PrintServiceLookup.lookupPrintServices(null, null);
            int selectedService = 0;
            for (int i = 0; i < services.length; i++) {
                if (services[i].getName().toUpperCase().contains(Main.printerGadai)) {
                    selectedService = i;
                }
            }
            job.setPrintService(services[selectedService]);
            PrintRequestAttributeSet printRequestAttributeSet = new HashPrintRequestAttributeSet();
            MediaSizeName mediaSizeName = MediaSize.findMedia(1000, 1000, MediaPrintableArea.MM);
            printRequestAttributeSet.add(mediaSizeName);
            printRequestAttributeSet.add(new Copies(1));
            JRPrintServiceExporter exporter;
            exporter = new JRPrintServiceExporter();
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
            exporter.setParameter(JRPrintServiceExporterParameter.PRINT_SERVICE, services[selectedService]);
            exporter.setParameter(JRPrintServiceExporterParameter.PRINT_SERVICE_ATTRIBUTE_SET, services[selectedService].getAttributes());
            exporter.setParameter(JRPrintServiceExporterParameter.PRINT_REQUEST_ATTRIBUTE_SET, printRequestAttributeSet);
            exporter.setParameter(JRPrintServiceExporterParameter.DISPLAY_PAGE_DIALOG, Boolean.FALSE);
            exporter.setParameter(JRPrintServiceExporterParameter.DISPLAY_PRINT_DIALOG, Boolean.FALSE);
            exporter.exportReport();
        }
    }

    public void printLaporanAmbilBarang(List<AmbilBarangDetail> listAmbilBarang, String tglMulai, String tglAkhir, String groupBy, String search
    ) throws Exception {
        JasperDesign jasperDesign = null;
        if (groupBy.equals("No Ambil")) {
            listAmbilBarang.sort(Comparator.comparing(AmbilBarangDetail::getNoAmbil));
            jasperDesign = JRXmlLoader.load(getClass().getResourceAsStream("LaporanAmbilBarangNoAmbil.jrxml"));
        } else if (groupBy.equals("Tanggal")) {
            Collections.sort(listAmbilBarang, (o1, o2) -> {
                return ((AmbilBarangDetail) o1).getAmbilBarangHead().getTglAmbil().compareTo(
                        ((AmbilBarangDetail) o2).getAmbilBarangHead().getTglAmbil());
            });
            jasperDesign = JRXmlLoader.load(getClass().getResourceAsStream("LaporanAmbilBarangTanggal.jrxml"));
        } else if (groupBy.equals("Kategori Barang")) {
            listAmbilBarang.sort(Comparator.comparing(AmbilBarangDetail::getKodeKategori));
            jasperDesign = JRXmlLoader.load(getClass().getResourceAsStream("LaporanAmbilBarangKategori.jrxml"));
        } else if (groupBy.equals("Jenis Barang")) {
            listAmbilBarang.sort(Comparator.comparing(AmbilBarangDetail::getKodeJenis));
            jasperDesign = JRXmlLoader.load(getClass().getResourceAsStream("LaporanAmbilBarangJenis.jrxml"));
        } else if (groupBy.equals("User")) {
            Collections.sort(listAmbilBarang, (o1, o2) -> {
                return ((AmbilBarangDetail) o1).getAmbilBarangHead().getKodeUser().compareTo(
                        ((AmbilBarangDetail) o2).getAmbilBarangHead().getKodeUser());
            });
            jasperDesign = JRXmlLoader.load(getClass().getResourceAsStream("LaporanAmbilBarangUser.jrxml"));
        }
        JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(listAmbilBarang);
        Map hash = new HashMap();
        hash.put("kodeCabang", Main.kodeToko);
        hash.put("tanggal", tglNormal.format(tglBarang.parse(tglMulai)) + " - " + tglNormal.format(tglBarang.parse(tglAkhir)));
        hash.put("groupBy", groupBy);
        hash.put("search", search);
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, hash, beanColDataSource);
        JRViewerFx jrViewerFx = new JRViewerFx(jasperPrint);
    }

    public void printLaporanTambahBarang(List<TambahBarangDetail> listTambahBarang, String tglMulai, String tglAkhir, String groupBy, String search
    ) throws Exception {
        JasperDesign jasperDesign = null;
        if (groupBy.equals("No Tambah")) {
            listTambahBarang.sort(Comparator.comparing(TambahBarangDetail::getNoTambah));
            jasperDesign = JRXmlLoader.load(getClass().getResourceAsStream("LaporanTambahBarangNoTambah.jrxml"));
        } else if (groupBy.equals("Tanggal")) {
            Collections.sort(listTambahBarang, (o1, o2) -> {
                return ((TambahBarangDetail) o1).getTambahBarang().getTglTambah().compareTo(
                        ((TambahBarangDetail) o2).getTambahBarang().getTglTambah());
            });
            jasperDesign = JRXmlLoader.load(getClass().getResourceAsStream("LaporanTambahBarangTanggal.jrxml"));
        } else if (groupBy.equals("Kategori Barang")) {
            listTambahBarang.sort(Comparator.comparing(TambahBarangDetail::getKodeKategori));
            jasperDesign = JRXmlLoader.load(getClass().getResourceAsStream("LaporanTambahBarangKategori.jrxml"));
        } else if (groupBy.equals("Jenis Barang")) {
            listTambahBarang.sort(Comparator.comparing(TambahBarangDetail::getKodeJenis));
            jasperDesign = JRXmlLoader.load(getClass().getResourceAsStream("LaporanTambahBarangJenis.jrxml"));
        } else if (groupBy.equals("User")) {
            Collections.sort(listTambahBarang, (o1, o2) -> {
                return ((TambahBarangDetail) o1).getTambahBarang().getKodeUser().compareTo(
                        ((TambahBarangDetail) o2).getTambahBarang().getKodeUser());
            });
            jasperDesign = JRXmlLoader.load(getClass().getResourceAsStream("LaporanTambahBarangUser.jrxml"));
        }
        JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(listTambahBarang);
        Map hash = new HashMap();
        hash.put("kodeCabang", Main.kodeToko);
        hash.put("tanggal", tglNormal.format(tglBarang.parse(tglMulai)) + " - " + tglNormal.format(tglBarang.parse(tglAkhir)));
        hash.put("groupBy", groupBy);
        hash.put("search", search);
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, hash, beanColDataSource);
        JRViewerFx jrViewerFx = new JRViewerFx(jasperPrint);
    }

    public void printLaporanBarcodeBarang(List<Barang> listBarcodeBarang, String tglMulai, String tglAkhir, String groupBy, String search
    ) throws Exception {
        JasperDesign jasperDesign = null;
        if (groupBy.equals("Tanggal")) {
            listBarcodeBarang.sort(Comparator.comparing(Barang::getBarcodeDate));
            jasperDesign = JRXmlLoader.load(getClass().getResourceAsStream("LaporanBarcodeBarangTanggal.jrxml"));
        } else if (groupBy.equals("Kategori Barang")) {
            listBarcodeBarang.sort(Comparator.comparing(Barang::getKodeKategori));
            jasperDesign = JRXmlLoader.load(getClass().getResourceAsStream("LaporanBarcodeBarangKategori.jrxml"));
        } else if (groupBy.equals("Jenis Barang")) {
            listBarcodeBarang.sort(Comparator.comparing(Barang::getKodeJenis));
            jasperDesign = JRXmlLoader.load(getClass().getResourceAsStream("LaporanBarcodeBarangJenis.jrxml"));
        } else if (groupBy.equals("User")) {
            listBarcodeBarang.sort(Comparator.comparing(Barang::getBarcodeBy));
            jasperDesign = JRXmlLoader.load(getClass().getResourceAsStream("LaporanBarcodeBarangUser.jrxml"));
        }
        JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(listBarcodeBarang);
        Map hash = new HashMap();
        hash.put("kodeCabang", Main.kodeToko);
        hash.put("tanggal", tglNormal.format(tglBarang.parse(tglMulai)) + " - " + tglNormal.format(tglBarang.parse(tglAkhir)));
        hash.put("groupBy", groupBy);
        hash.put("search", search);
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, hash, beanColDataSource);
        JRViewerFx jrViewerFx = new JRViewerFx(jasperPrint);
    }

    public void printLaporanPindahGudang(List<PindahDetail> listPindahDetail, String tglMulai, String tglAkhir, String search
    ) throws Exception {
        JasperDesign jasperDesign = null;
        listPindahDetail.sort(Comparator.comparing(PindahDetail::getNoPindah));
        jasperDesign = JRXmlLoader.load(getClass().getResourceAsStream("LaporanPindahGudang.jrxml"));
        JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(listPindahDetail);
        Map hash = new HashMap();
        hash.put("kodeCabang", Main.kodeToko);
        hash.put("tanggal", tglNormal.format(tglBarang.parse(tglMulai)) + " - " + tglNormal.format(tglBarang.parse(tglAkhir)));
        hash.put("search", search);
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, hash, beanColDataSource);
        JRViewerFx jrViewerFx = new JRViewerFx(jasperPrint);
    }

    public void printLaporanCetakBarcode(List<CetakBarcodeDetail> listCetakBarcode, String tglMulai, String tglAkhir, String search
    ) throws Exception {
        JasperDesign jasperDesign = null;
        listCetakBarcode.sort(Comparator.comparing(CetakBarcodeDetail::getNoCetak));
        jasperDesign = JRXmlLoader.load(getClass().getResourceAsStream("LaporanCetakBarcode.jrxml"));
        JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(listCetakBarcode);
        Map hash = new HashMap();
        hash.put("kodeCabang", Main.kodeToko);
        hash.put("tanggal", tglNormal.format(tglBarang.parse(tglMulai)) + " - " + tglNormal.format(tglBarang.parse(tglAkhir)));
        hash.put("search", search);
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, hash, beanColDataSource);
        JRViewerFx jrViewerFx = new JRViewerFx(jasperPrint);
    }

    public void printLaporanHancurBarang(List<HancurDetail> listHancur, String tglMulai, String tglAkhir, String search
    ) throws Exception {
        JasperDesign jasperDesign = null;
        listHancur.sort(Comparator.comparing(HancurDetail::getNoHancur));
        jasperDesign = JRXmlLoader.load(getClass().getResourceAsStream("LaporanHancurBarang.jrxml"));
        JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(listHancur);
        Map hash = new HashMap();
        hash.put("kodeCabang", Main.kodeToko);
        hash.put("tanggal", tglNormal.format(tglBarang.parse(tglMulai)) + " - " + tglNormal.format(tglBarang.parse(tglAkhir)));
        hash.put("search", search);
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, hash, beanColDataSource);
        JRViewerFx jrViewerFx = new JRViewerFx(jasperPrint);
    }

    public void printLaporanPenjualan(List<PenjualanDetail> listPenjualanDetail, String tglMulai, String tglAkhir, String groupBy, String search
    ) throws Exception {
        JasperDesign jasperDesign = null;
        if (groupBy.equals("No Penjualan")) {
            listPenjualanDetail.sort(Comparator.comparing(PenjualanDetail::getNoPenjualan));
            jasperDesign = JRXmlLoader.load(getClass().getResourceAsStream("LaporanPenjualanNoPenjualan.jrxml"));
        } else if (groupBy.equals("Tanggal")) {
            Collections.sort(listPenjualanDetail, (o1, o2) -> {
                return ((PenjualanDetail) o1).getPenjualan().getTglPenjualan().compareTo(
                        ((PenjualanDetail) o2).getPenjualan().getTglPenjualan());
            });
            jasperDesign = JRXmlLoader.load(getClass().getResourceAsStream("LaporanPenjualanTanggal.jrxml"));
        } else if (groupBy.equals("Kategori Barang")) {
            listPenjualanDetail.sort(Comparator.comparing(PenjualanDetail::getKodeKategori));
            jasperDesign = JRXmlLoader.load(getClass().getResourceAsStream("LaporanPenjualanKategori.jrxml"));
        } else if (groupBy.equals("Jenis Barang")) {
            listPenjualanDetail.sort(Comparator.comparing(PenjualanDetail::getKodeJenis));
            jasperDesign = JRXmlLoader.load(getClass().getResourceAsStream("LaporanPenjualanJenis.jrxml"));
        } else if (groupBy.equals("Sales")) {
            Collections.sort(listPenjualanDetail, (o1, o2) -> {
                return ((PenjualanDetail) o1).getPenjualan().getKodeSales().compareTo(
                        ((PenjualanDetail) o2).getPenjualan().getKodeSales());
            });
            jasperDesign = JRXmlLoader.load(getClass().getResourceAsStream("LaporanPenjualanSales.jrxml"));
        }
        JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(listPenjualanDetail);
        Map hash = new HashMap();
        hash.put("kodeCabang", Main.kodeToko);
        hash.put("tanggal", tglNormal.format(tglBarang.parse(tglMulai)) + " - " + tglNormal.format(tglBarang.parse(tglAkhir)));
        hash.put("groupBy", groupBy);
        hash.put("search", search);
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, hash, beanColDataSource);
        JRViewerFx jrViewerFx = new JRViewerFx(jasperPrint);
    }

    public void printLaporanBatalPenjualan(List<PenjualanDetail> listPenjualanDetail, String tglMulai, String tglAkhir, String search
    ) throws Exception {
        JasperDesign jasperDesign = null;
        listPenjualanDetail.sort(Comparator.comparing(PenjualanDetail::getNoPenjualan));
        jasperDesign = JRXmlLoader.load(getClass().getResourceAsStream("LaporanBatalJual.jrxml"));
        JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(listPenjualanDetail);
        Map hash = new HashMap();
        hash.put("kodeCabang", Main.kodeToko);
        hash.put("tanggal", tglNormal.format(tglBarang.parse(tglMulai)) + " - " + tglNormal.format(tglBarang.parse(tglAkhir)));
        hash.put("search", search);
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, hash, beanColDataSource);
        JRViewerFx jrViewerFx = new JRViewerFx(jasperPrint);
    }

    public void printLaporanPembelian(List<PembelianDetail> listPembelianDetail, String tglMulai, String tglAkhir, String groupBy, String search
    ) throws Exception {
        JasperDesign jasperDesign = null;
        if (groupBy.equals("No Pembelian")) {
            listPembelianDetail.sort(Comparator.comparing(PembelianDetail::getNoPembelian));
            jasperDesign = JRXmlLoader.load(getClass().getResourceAsStream("LaporanPembelianNoPembelian.jrxml"));
        } else if (groupBy.equals("Tanggal")) {
            Collections.sort(listPembelianDetail, (o1, o2) -> {
                return ((PembelianDetail) o1).getPembelian().getTglPembelian().compareTo(
                        ((PembelianDetail) o2).getPembelian().getTglPembelian());
            });
            jasperDesign = JRXmlLoader.load(getClass().getResourceAsStream("LaporanPembelianTanggal.jrxml"));
        } else if (groupBy.equals("Kategori Barang")) {
            listPembelianDetail.sort(Comparator.comparing(PembelianDetail::getKodeKategori));
            jasperDesign = JRXmlLoader.load(getClass().getResourceAsStream("LaporanPembelianKategori.jrxml"));
        } else if (groupBy.equals("Jenis Barang")) {
            listPembelianDetail.sort(Comparator.comparing(PembelianDetail::getKodeJenis));
            jasperDesign = JRXmlLoader.load(getClass().getResourceAsStream("LaporanPembelianJenis.jrxml"));
        } else if (groupBy.equals("Sales")) {
            Collections.sort(listPembelianDetail, (o1, o2) -> {
                return ((PembelianDetail) o1).getPembelian().getKodeSales().compareTo(
                        ((PembelianDetail) o2).getPembelian().getKodeSales());
            });
            jasperDesign = JRXmlLoader.load(getClass().getResourceAsStream("LaporanPembelianSales.jrxml"));
        }
        JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(listPembelianDetail);
        Map hash = new HashMap();
        hash.put("kodeCabang", Main.kodeToko);
        hash.put("tanggal", tglNormal.format(tglBarang.parse(tglMulai)) + " - " + tglNormal.format(tglBarang.parse(tglAkhir)));
        hash.put("groupBy", groupBy);
        hash.put("search", search);
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, hash, beanColDataSource);
        JRViewerFx jrViewerFx = new JRViewerFx(jasperPrint);
    }

    public void printLaporanBatalPembelian(List<PembelianDetail> listPembelianDetail, String tglMulai, String tglAkhir, String search
    ) throws Exception {
        JasperDesign jasperDesign = null;
        listPembelianDetail.sort(Comparator.comparing(PembelianDetail::getNoPembelian));
        jasperDesign = JRXmlLoader.load(getClass().getResourceAsStream("LaporanBatalPembelian.jrxml"));
        JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(listPembelianDetail);
        Map hash = new HashMap();
        hash.put("kodeCabang", Main.kodeToko);
        hash.put("tanggal", tglNormal.format(tglBarang.parse(tglMulai)) + " - " + tglNormal.format(tglBarang.parse(tglAkhir)));
        hash.put("search", search);
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, hash, beanColDataSource);
        JRViewerFx jrViewerFx = new JRViewerFx(jasperPrint);
    }

    public void printLaporanTerimaGadai(List<GadaiHead> listGadaiHead, String tglMulai, String tglAkhir, String groupBy, String search
    ) throws Exception {
        JasperDesign jasperDesign = null;
        if (groupBy.equals("Tanggal")) {
            Collections.sort(listGadaiHead, (o1, o2) -> {
                return ((GadaiHead) o1).getTglGadai().compareTo(
                        ((GadaiHead) o2).getTglGadai());
            });
            jasperDesign = JRXmlLoader.load(getClass().getResourceAsStream("LaporanTerimaGadaiTanggal.jrxml"));
        } else if (groupBy.equals("Sales")) {
            Collections.sort(listGadaiHead, (o1, o2) -> {
                return ((GadaiHead) o1).getKodeSales().compareTo(
                        ((GadaiHead) o2).getKodeSales());
            });
            jasperDesign = JRXmlLoader.load(getClass().getResourceAsStream("LaporanTerimaGadaiSales.jrxml"));
        }
        JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(listGadaiHead);
        Map hash = new HashMap();
        hash.put("kodeCabang", Main.kodeToko);
        hash.put("tanggal", tglNormal.format(tglBarang.parse(tglMulai)) + " - " + tglNormal.format(tglBarang.parse(tglAkhir)));
        hash.put("groupBy", groupBy);
        hash.put("search", search);
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, hash, beanColDataSource);
        JRViewerFx jrViewerFx = new JRViewerFx(jasperPrint);
    }

    public void printLaporanPelunasanGadai(List<GadaiHead> listGadaiHead, String tglMulai, String tglAkhir, String groupBy, String search
    ) throws Exception {
        JasperDesign jasperDesign = null;
        if (groupBy.equals("Tanggal")) {
            Collections.sort(listGadaiHead, (o1, o2) -> {
                return ((GadaiHead) o1).getTglLunas().compareTo(
                        ((GadaiHead) o2).getTglLunas());
            });
            jasperDesign = JRXmlLoader.load(getClass().getResourceAsStream("LaporanPelunasanGadaiTanggal.jrxml"));
        } else if (groupBy.equals("Sales")) {
            Collections.sort(listGadaiHead, (o1, o2) -> {
                return ((GadaiHead) o1).getSalesLunas().compareTo(
                        ((GadaiHead) o2).getSalesLunas());
            });
            jasperDesign = JRXmlLoader.load(getClass().getResourceAsStream("LaporanPelunasanGadaiSales.jrxml"));
        }
        JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(listGadaiHead);
        Map hash = new HashMap();
        hash.put("kodeCabang", Main.kodeToko);
        hash.put("tanggal", tglNormal.format(tglBarang.parse(tglMulai)) + " - " + tglNormal.format(tglBarang.parse(tglAkhir)));
        hash.put("groupBy", groupBy);
        hash.put("search", search);
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, hash, beanColDataSource);
        JRViewerFx jrViewerFx = new JRViewerFx(jasperPrint);
    }

    public void printLaporanStokGadai(List<GadaiHead> listGadaiHead, String tglMulai, String tglAkhir, String search
    ) throws Exception {
        JasperDesign jasperDesign = null;
        Collections.sort(listGadaiHead, (o1, o2) -> {
            return ((GadaiHead) o1).getTglGadai().compareTo(
                    ((GadaiHead) o2).getTglGadai());
        });
        jasperDesign = JRXmlLoader.load(getClass().getResourceAsStream("LaporanStokGadai.jrxml"));
        JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(listGadaiHead);
        Map hash = new HashMap();
        hash.put("kodeCabang", Main.kodeToko);
        hash.put("tanggal", tglNormal.format(tglBarang.parse(tglMulai)) + " - " + tglNormal.format(tglBarang.parse(tglAkhir)));
        hash.put("search", search);
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, hash, beanColDataSource);
        JRViewerFx jrViewerFx = new JRViewerFx(jasperPrint);
    }

    public void printLaporanBatalTerimaGadai(List<GadaiHead> listGadaiHead, String tglMulai, String tglAkhir, String search
    ) throws Exception {
        JasperDesign jasperDesign = null;
        Collections.sort(listGadaiHead, (o1, o2) -> {
            return ((GadaiHead) o1).getTglBatal().compareTo(
                    ((GadaiHead) o2).getTglBatal());
        });
        jasperDesign = JRXmlLoader.load(getClass().getResourceAsStream("LaporanBatalTerimaGadai.jrxml"));
        JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(listGadaiHead);
        Map hash = new HashMap();
        hash.put("kodeCabang", Main.kodeToko);
        hash.put("tanggal", tglNormal.format(tglBarang.parse(tglMulai)) + " - " + tglNormal.format(tglBarang.parse(tglAkhir)));
        hash.put("search", search);
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, hash, beanColDataSource);
        JRViewerFx jrViewerFx = new JRViewerFx(jasperPrint);
    }

    public void printLaporanKeuangan(List<Keuangan> listKeuangan, String tglMulai, String tglAkhir, double saldoAwal, String groupBy, String search
    ) throws Exception {
        JasperDesign jasperDesign = null;
        if (groupBy.equals("Tanggal")) {
            Collections.sort(listKeuangan, (o1, o2) -> {
                return ((Keuangan) o1).getTglKeuangan().compareTo(
                        ((Keuangan) o2).getTglKeuangan());
            });
            jasperDesign = JRXmlLoader.load(getClass().getResourceAsStream("LaporanKeuanganHarianTanggal.jrxml"));
        } else if (groupBy.equals("Kategori")) {
            listKeuangan.sort(Comparator.comparing(Keuangan::getKategori));
            jasperDesign = JRXmlLoader.load(getClass().getResourceAsStream("LaporanKeuanganHarianKategori.jrxml"));
        } else if (groupBy.equals("Sales")) {
            Collections.sort(listKeuangan, (o1, o2) -> {
                return ((Keuangan) o1).getKodeUser().compareTo(
                        ((Keuangan) o2).getKodeUser());
            });
            jasperDesign = JRXmlLoader.load(getClass().getResourceAsStream("LaporanKeuanganHarianSales.jrxml"));
        }
        JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(listKeuangan);
        Map hash = new HashMap();
        hash.put("kodeCabang", Main.kodeToko);
        hash.put("tanggal", tglNormal.format(tglBarang.parse(tglMulai)) + " - " + tglNormal.format(tglBarang.parse(tglAkhir)));
        hash.put("groupBy", groupBy);
        hash.put("saldoAwal", saldoAwal);
        hash.put("search", search);
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, hash, beanColDataSource);
        JRViewerFx jrViewerFx = new JRViewerFx(jasperPrint);
    }

    public void printLaporanTransaksiSales(List<Keuangan> listKeuangan, String tglMulai, String tglAkhir, String search
    ) throws Exception {
        JasperDesign jasperDesign = null;
        Collections.sort(listKeuangan, (o1, o2) -> {
            int sComp = ((Keuangan) o1).getKodeUser().compareTo(((Keuangan) o2).getKodeUser());
            if (sComp != 0) {
                return sComp;
            }
            return ((Keuangan) o1).getKategori().compareTo(((Keuangan) o2).getKategori());
        });
        jasperDesign = JRXmlLoader.load(getClass().getResourceAsStream("LaporanTransaksiSales.jrxml"));
        JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(listKeuangan);
        Map hash = new HashMap();
        hash.put("kodeCabang", Main.kodeToko);
        hash.put("tanggal", tglNormal.format(tglBarang.parse(tglMulai)) + " - " + tglNormal.format(tglBarang.parse(tglAkhir)));
        hash.put("search", search);
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, hash, beanColDataSource);
        JRViewerFx jrViewerFx = new JRViewerFx(jasperPrint);
    }

    public void printLaporanBarang(List<Barang> listBarcodeBarang, String search
    ) throws Exception {
        JasperDesign jasperDesign = null;
        Collections.sort(listBarcodeBarang, (o1, o2) -> {
            int sComp = ((Barang) o1).getKodeKategori().compareTo(((Barang) o2).getKodeKategori());
            if (sComp != 0) {
                return sComp;
            }
            return ((Barang) o1).getKodeJenis().compareTo(((Barang) o2).getKodeJenis());
        });
        jasperDesign = JRXmlLoader.load(getClass().getResourceAsStream("LaporanBarang.jrxml"));
        JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(listBarcodeBarang);
        Map hash = new HashMap();
        hash.put("kodeCabang", Main.kodeToko);
        hash.put("search", search);
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, hash, beanColDataSource);
        JRViewerFx jrViewerFx = new JRViewerFx(jasperPrint);
    }

    public void printLaporanStokBarang(List<StokBarang> listBarcodeBarang, String tanggal, String groupBy, String search
    ) throws Exception {
        JasperDesign jasperDesign = null;
        if (groupBy.equals("Kategori")) {
            Collections.sort(listBarcodeBarang, (o1, o2) -> {
                int sComp = ((StokBarang) o1).getKodeKategori().compareTo(((StokBarang) o2).getKodeKategori());
                if (sComp != 0) {
                    return sComp;
                }
                return ((StokBarang) o1).getKodeJenis().compareTo(((StokBarang) o2).getKodeJenis());
            });
            jasperDesign = JRXmlLoader.load(getClass().getResourceAsStream("LaporanStokBarangKategori.jrxml"));
        } else if (groupBy.equals("Gudang")) {
            Collections.sort(listBarcodeBarang, (o1, o2) -> {
                int sComp = ((StokBarang) o1).getKodeGudang().compareTo(((StokBarang) o2).getKodeGudang());
                if (sComp != 0) {
                    return sComp;
                }
                return ((StokBarang) o1).getKodeJenis().compareTo(((StokBarang) o2).getKodeJenis());
            });
            jasperDesign = JRXmlLoader.load(getClass().getResourceAsStream("LaporanStokBarangGudang.jrxml"));
        }
        JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(listBarcodeBarang);
        Map hash = new HashMap();
        hash.put("kodeCabang", Main.kodeToko);
        hash.put("tanggal", tglNormal.format(tglBarang.parse(tanggal)));
        hash.put("groupBy", groupBy);
        hash.put("search", search);
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, hash, beanColDataSource);
        JRViewerFx jrViewerFx = new JRViewerFx(jasperPrint);
    }
//    public static void printBarcode(List<Barang> barang) throws Exception {
//        PrintService[] services = PrintServiceLookup.lookupPrintServices(null, null);
//        int selectedService = 0;
//        for (int i = 0; i < services.length; i++) {
//            if (services[i].getName().toUpperCase().contains(Main.printerBarcode)) {
//                selectedService = i;
//            }
//        }
//        DecimalFormat gr = new DecimalFormat("##0.00");
//        for (int i = 0; i < barang.size(); i++) {
//            Barang b = barang.get(i);
//            if (i == 0 || i % 2 == 0) {
//                String commands = "^XA^MNN^LL216^XZ^XA^JUS^XZ";
//                commands = commands + "^XA^LH10,00";
//                commands = commands
//                        + "^FO20,00^ADN,18,10^FD" + b.getKodeBarcode() + "^FS"
//                        + "^FO05,20^BY1^BCN,50,N,N,N,N^FD" + b.getKodeBarcode() + "^FS"
//                        + "^FO00,90^ADN,18,10^FD" + b.getNamaBarang() + "^FS"
//                        + "^FO00,115^ADN,30,15^FD" + gr.format(b.getBerat()) + "^FS"
//                        + "^FO00,155^ADN,18,10^FD" + b.getKodeJenis() + "^FS"
//                        + "^FO100,155^ADN,18,10^FD" + b.getKodeIntern() + "^FS";
//                if (barang.size() == i + 1) {
//
//                } else {
//                    Barang b2 = barang.get(i + 1);
//                    commands = commands
//                            + "^FO480,00^ADN,18,10^FD" + b2.getKodeBarcode() + "^FS"
//                            + "^FO465,20^BY1^BCN,50,N,N,N,N^FD" + b2.getKodeBarcode() + "^FS"
//                            + "^FO460,90^ADN,18,10^FD" + b2.getNamaBarang() + "^FS"
//                            + "^FO460,115^ADN,30,15^FD" + gr.format(b2.getBerat()) + "^FS"
//                            + "^FO460,155^ADN,18,10^FD" + b2.getKodeJenis() + "^FS"
//                            + "^FO560,155^ADN,18,10^FD" + b2.getKodeIntern() + "^FS";
//                }
//                commands = commands + "^XZ";
//                PrintService pservice = services[selectedService];
//                DocPrintJob job = pservice.createPrintJob();
//                DocFlavor flavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;
//                Doc doc = new SimpleDoc(commands.getBytes(), flavor, null);
//                job.print(doc, null);
//            }
//        }
//    }

    public static void printBarcode(List<Barang> listBarang) throws Exception {
        PrintService[] services = PrintServiceLookup.lookupPrintServices(null, null);
        int selectedService = 0;
        for (int i = 0; i < services.length; i++) {
            if (services[i].getName().toUpperCase().contains(Main.printerBarcode)) {
                selectedService = i;
            }
        }
        DecimalFormat gr = new DecimalFormat("###,##0.00");
        for (int i = 0; i < listBarang.size(); i++) {
            Barang b = listBarang.get(i);
            if (i == 0 || i % 2 == 0) {
                String commands = "<ESC>A<ESC>A3H0130V0010";
                commands = commands + "<ESC>H0000<ESC>V0000<ESC>M" + b.getKodeBarcode()
                        + "<ESC>H0000<ESC>V0023<ESC>BG01050" + b.getKodeBarcode()
                        + "<ESC>H0000<ESC>V0095<ESC>S" + b.getNamaBarang()
                        + "<ESC>H0020<ESC>V0120<ESC>L0102<ESC>M" + gr.format(b.getBerat()) + "<ESC>L0102<ESC>S gr"
                        + "<ESC>H0000<ESC>V0160<ESC>L0101<ESC>S" + b.getKodeJenis() + "-" + gr.format(b.getBeratKemasan()) + "<ESC>H0110<ESC>V0160<ESC>S" + b.getKodeIntern();
                if (listBarang.size() == i + 1) {
                } else {
                    Barang b2 = listBarang.get(i + 1);
                    commands = commands + "<ESC>H0455<ESC>V0000<ESC>M" + b2.getKodeBarcode()
                            + "<ESC>H0455<ESC>V0023<ESC>BG01050" + b2.getKodeBarcode()
                            + "<ESC>H0455<ESC>V0095<ESC>S" + b2.getNamaBarang()
                            + "<ESC>H0475<ESC>V0120<ESC>L0102<ESC>M" + gr.format(b2.getBerat()) + "<ESC>L0102<ESC>S gr"
                            + "<ESC>H0455<ESC>V0160<ESC>L0101<ESC>S" + b2.getKodeJenis() + "-" + gr.format(b2.getBeratKemasan()) + "<ESC>H0565<ESC>V0160<ESC>S" + b2.getKodeIntern();
                }
                commands = commands + "<ESC>Q1<ESC>Z";
                char ch = 27;
                commands = commands.replace("<ESC>", String.valueOf(ch));
                PrintService pservice = services[selectedService];
                DocPrintJob job = pservice.createPrintJob();
                DocFlavor flavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;
                Doc doc = new SimpleDoc(commands.getBytes(), flavor, null);
                job.print(doc, null);
            }
        }
    }
}
