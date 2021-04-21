/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasGunungMas.Model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Xtreme
 */
public class StokBarang {
    private final StringProperty tanggal = new SimpleStringProperty();
    private final StringProperty kodeBarcode = new SimpleStringProperty();
    private final StringProperty kodeKategori = new SimpleStringProperty();
    private final StringProperty kodeJenis = new SimpleStringProperty();
    private final StringProperty kodeGudang = new SimpleStringProperty();
    private final DoubleProperty beratAwal = new SimpleDoubleProperty();
    private final DoubleProperty beratAsliAwal = new SimpleDoubleProperty();
    private final IntegerProperty stokAwal = new SimpleIntegerProperty();
    private final DoubleProperty beratMasuk = new SimpleDoubleProperty();
    private final DoubleProperty beratAsliMasuk = new SimpleDoubleProperty();
    private final IntegerProperty stokMasuk = new SimpleIntegerProperty();
    private final DoubleProperty beratKeluar = new SimpleDoubleProperty();
    private final DoubleProperty beratAsliKeluar = new SimpleDoubleProperty();
    private final IntegerProperty stokKeluar = new SimpleIntegerProperty();
    private final DoubleProperty beratAkhir = new SimpleDoubleProperty();
    private final DoubleProperty beratAsliAkhir = new SimpleDoubleProperty();
    private final IntegerProperty stokAkhir = new SimpleIntegerProperty();
    private Jenis jenis;
    private Barang barang;


    public Barang getBarang() {
        return barang;
    }

    public void setBarang(Barang barang) {
        this.barang = barang;
    }
    
    public Jenis getJenis() {
        return jenis;
    }

    public void setJenis(Jenis jenis) {
        this.jenis = jenis;
    }
    
    public int getStokKeluar() {
        return stokKeluar.get();
    }

    public void setStokKeluar(int value) {
        stokKeluar.set(value);
    }

    public IntegerProperty stokKeluarProperty() {
        return stokKeluar;
    }

    public double getBeratAsliKeluar() {
        return beratAsliKeluar.get();
    }

    public void setBeratAsliKeluar(double value) {
        beratAsliKeluar.set(value);
    }

    public DoubleProperty beratAsliKeluarProperty() {
        return beratAsliKeluar;
    }

    public double getBeratKeluar() {
        return beratKeluar.get();
    }

    public void setBeratKeluar(double value) {
        beratKeluar.set(value);
    }

    public DoubleProperty beratKeluarProperty() {
        return beratKeluar;
    }

    public int getStokAkhir() {
        return stokAkhir.get();
    }

    public void setStokAkhir(int value) {
        stokAkhir.set(value);
    }

    public IntegerProperty stokAkhirProperty() {
        return stokAkhir;
    }

    public double getBeratAsliAkhir() {
        return beratAsliAkhir.get();
    }

    public void setBeratAsliAkhir(double value) {
        beratAsliAkhir.set(value);
    }

    public DoubleProperty beratAsliAkhirProperty() {
        return beratAsliAkhir;
    }

    public double getBeratAkhir() {
        return beratAkhir.get();
    }

    public void setBeratAkhir(double value) {
        beratAkhir.set(value);
    }

    public DoubleProperty beratAkhirProperty() {
        return beratAkhir;
    }

    public int getStokMasuk() {
        return stokMasuk.get();
    }

    public void setStokMasuk(int value) {
        stokMasuk.set(value);
    }

    public IntegerProperty stokMasukProperty() {
        return stokMasuk;
    }

    public double getBeratAsliMasuk() {
        return beratAsliMasuk.get();
    }

    public void setBeratAsliMasuk(double value) {
        beratAsliMasuk.set(value);
    }

    public DoubleProperty beratAsliMasukProperty() {
        return beratAsliMasuk;
    }

    public double getBeratMasuk() {
        return beratMasuk.get();
    }

    public void setBeratMasuk(double value) {
        beratMasuk.set(value);
    }

    public DoubleProperty beratMasukProperty() {
        return beratMasuk;
    }

    public int getStokAwal() {
        return stokAwal.get();
    }

    public void setStokAwal(int value) {
        stokAwal.set(value);
    }

    public IntegerProperty stokAwalProperty() {
        return stokAwal;
    }

    public double getBeratAsliAwal() {
        return beratAsliAwal.get();
    }

    public void setBeratAsliAwal(double value) {
        beratAsliAwal.set(value);
    }

    public DoubleProperty beratAsliAwalProperty() {
        return beratAsliAwal;
    }

    public double getBeratAwal() {
        return beratAwal.get();
    }

    public void setBeratAwal(double value) {
        beratAwal.set(value);
    }

    public DoubleProperty beratAwalProperty() {
        return beratAwal;
    }

    public String getKodeGudang() {
        return kodeGudang.get();
    }

    public void setKodeGudang(String value) {
        kodeGudang.set(value);
    }

    public StringProperty kodeGudangProperty() {
        return kodeGudang;
    }

    public String getKodeJenis() {
        return kodeJenis.get();
    }

    public void setKodeJenis(String value) {
        kodeJenis.set(value);
    }

    public StringProperty kodeJenisProperty() {
        return kodeJenis;
    }

    public String getKodeKategori() {
        return kodeKategori.get();
    }

    public void setKodeKategori(String value) {
        kodeKategori.set(value);
    }

    public StringProperty kodeKategoriProperty() {
        return kodeKategori;
    }

    public String getKodeBarcode() {
        return kodeBarcode.get();
    }

    public void setKodeBarcode(String value) {
        kodeBarcode.set(value);
    }

    public StringProperty kodeBarcodeProperty() {
        return kodeBarcode;
    }

    public String getTanggal() {
        return tanggal.get();
    }

    public void setTanggal(String value) {
        tanggal.set(value);
    }

    public StringProperty tanggalProperty() {
        return tanggal;
    }
    
}
