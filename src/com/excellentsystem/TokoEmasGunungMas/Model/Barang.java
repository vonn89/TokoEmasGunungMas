/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasGunungMas.Model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Xtreme
 */
public class Barang {
    private final BooleanProperty status = new SimpleBooleanProperty();
    
    private final StringProperty kodeBarcode = new SimpleStringProperty();
    private final StringProperty namaBarang = new SimpleStringProperty();
    private final StringProperty keterangan = new SimpleStringProperty();
    private final StringProperty kodeKategori = new SimpleStringProperty();
    private final StringProperty kodeJenis = new SimpleStringProperty();
    private final StringProperty kodeGudang = new SimpleStringProperty();
    private final StringProperty kodeIntern = new SimpleStringProperty();
    private final StringProperty kadar = new SimpleStringProperty();
    private final DoubleProperty berat = new SimpleDoubleProperty();
    private final DoubleProperty beratAsli = new SimpleDoubleProperty();
    private final DoubleProperty beratKemasan = new SimpleDoubleProperty();
    private final DoubleProperty nilaiPokok = new SimpleDoubleProperty();
    private final DoubleProperty hargaJual = new SimpleDoubleProperty();
    private final StringProperty statusBarang = new SimpleStringProperty();
    private final StringProperty barcodeDate = new SimpleStringProperty();
    private final StringProperty barcodeBy = new SimpleStringProperty();
    private final StringProperty deletedDate = new SimpleStringProperty();
    private final StringProperty deletedBy = new SimpleStringProperty();
    private final StringProperty soldDate = new SimpleStringProperty();
    private final StringProperty soldBy = new SimpleStringProperty();
    private Kategori kategori;

    public double getBeratKemasan() {
        return beratKemasan.get();
    }

    public void setBeratKemasan(double value) {
        beratKemasan.set(value);
    }

    public DoubleProperty beratKemasanProperty() {
        return beratKemasan;
    }
    
    

    public String getDeletedBy() {
        return deletedBy.get();
    }

    public void setDeletedBy(String value) {
        deletedBy.set(value);
    }

    public StringProperty deletedByProperty() {
        return deletedBy;
    }

    public String getDeletedDate() {
        return deletedDate.get();
    }

    public void setDeletedDate(String value) {
        deletedDate.set(value);
    }

    public StringProperty deletedDateProperty() {
        return deletedDate;
    }

    public String getBarcodeBy() {
        return barcodeBy.get();
    }

    public void setBarcodeBy(String value) {
        barcodeBy.set(value);
    }

    public StringProperty barcodeByProperty() {
        return barcodeBy;
    }

    public String getBarcodeDate() {
        return barcodeDate.get();
    }

    public void setBarcodeDate(String value) {
        barcodeDate.set(value);
    }

    public StringProperty barcodeDateProperty() {
        return barcodeDate;
    }

    public String getSoldBy() {
        return soldBy.get();
    }

    public void setSoldBy(String value) {
        soldBy.set(value);
    }

    public StringProperty soldByProperty() {
        return soldBy;
    }

    public double getHargaJual() {
        return hargaJual.get();
    }

    public void setHargaJual(double value) {
        hargaJual.set(value);
    }

    public DoubleProperty hargaJualProperty() {
        return hargaJual;
    }

    public String getSoldDate() {
        return soldDate.get();
    }

    public void setSoldDate(String value) {
        soldDate.set(value);
    }

    public StringProperty soldDateProperty() {
        return soldDate;
    }


    public Kategori getKategori() {
        return kategori;
    }

    public void setKategori(Kategori kategori) {
        this.kategori = kategori;
    }
    
    public boolean isStatus() {
        return status.get();
    }

    public void setStatus(boolean value) {
        status.set(value);
    }

    public BooleanProperty statusProperty() {
        return status;
    }


    public String getStatusBarang() {
        return statusBarang.get();
    }

    public void setStatusBarang(String value) {
        statusBarang.set(value);
    }

    public StringProperty statusBarangProperty() {
        return statusBarang;
    }

    public double getNilaiPokok() {
        return nilaiPokok.get();
    }

    public void setNilaiPokok(double value) {
        nilaiPokok.set(value);
    }

    public DoubleProperty nilaiPokokProperty() {
        return nilaiPokok;
    }

    public double getBeratAsli() {
        return beratAsli.get();
    }

    public void setBeratAsli(double value) {
        beratAsli.set(value);
    }

    public DoubleProperty beratAsliProperty() {
        return beratAsli;
    }
   
    public double getBerat() {
        return berat.get();
    }

    public void setBerat(double value) {
        berat.set(value);
    }

    public DoubleProperty beratProperty() {
        return berat;
    }

    public String getKadar() {
        return kadar.get();
    }

    public void setKadar(String value) {
        kadar.set(value);
    }

    public StringProperty kadarProperty() {
        return kadar;
    }

    public String getKodeIntern() {
        return kodeIntern.get();
    }

    public void setKodeIntern(String value) {
        kodeIntern.set(value);
    }

    public StringProperty kodeInternProperty() {
        return kodeIntern;
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

    public String getKeterangan() {
        return keterangan.get();
    }

    public void setKeterangan(String value) {
        keterangan.set(value);
    }

    public StringProperty keteranganProperty() {
        return keterangan;
    }

    public String getNamaBarang() {
        return namaBarang.get();
    }

    public void setNamaBarang(String value) {
        namaBarang.set(value);
    }

    public StringProperty namaBarangProperty() {
        return namaBarang;
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
    
}
