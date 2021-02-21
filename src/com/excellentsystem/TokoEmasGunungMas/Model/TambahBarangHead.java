/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasGunungMas.Model;

import java.util.List;
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
public class TambahBarangHead {
    private final StringProperty noTambah = new SimpleStringProperty();
    private final StringProperty tglTambah = new SimpleStringProperty();
    private final StringProperty keterangan = new SimpleStringProperty();
    private final IntegerProperty totalQty = new SimpleIntegerProperty();
    private final DoubleProperty totalBerat = new SimpleDoubleProperty();
    private final StringProperty kodeUser = new SimpleStringProperty();
    private List<TambahBarangDetail> allDetail;

    public List<TambahBarangDetail> getAllDetail() {
        return allDetail;
    }

    public void setAllDetail(List<TambahBarangDetail> allDetail) {
        this.allDetail = allDetail;
    }
    

    public String getKodeUser() {
        return kodeUser.get();
    }

    public void setKodeUser(String value) {
        kodeUser.set(value);
    }

    public StringProperty kodeUserProperty() {
        return kodeUser;
    }

    public double getTotalBerat() {
        return totalBerat.get();
    }

    public void setTotalBerat(double value) {
        totalBerat.set(value);
    }

    public DoubleProperty totalBeratProperty() {
        return totalBerat;
    }

    public int getTotalQty() {
        return totalQty.get();
    }

    public void setTotalQty(int value) {
        totalQty.set(value);
    }

    public IntegerProperty totalQtyProperty() {
        return totalQty;
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

    public String getTglTambah() {
        return tglTambah.get();
    }

    public void setTglTambah(String value) {
        tglTambah.set(value);
    }

    public StringProperty tglTambahProperty() {
        return tglTambah;
    }

    public String getNoTambah() {
        return noTambah.get();
    }

    public void setNoTambah(String value) {
        noTambah.set(value);
    }

    public StringProperty noTambahProperty() {
        return noTambah;
    }
    
}
