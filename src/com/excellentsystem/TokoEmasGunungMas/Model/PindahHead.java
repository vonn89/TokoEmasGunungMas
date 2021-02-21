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
public class PindahHead {
    private final StringProperty noPindah = new SimpleStringProperty();
    private final StringProperty tglPindah = new SimpleStringProperty();
    private final StringProperty gudangTujuan = new SimpleStringProperty();
    private final IntegerProperty totalQty = new SimpleIntegerProperty();
    private final DoubleProperty totalBerat = new SimpleDoubleProperty();
    private final DoubleProperty totalBeratAsli = new SimpleDoubleProperty();
    private final StringProperty kodeUser = new SimpleStringProperty();
    private List<PindahDetail> allDetail;

    public String getGudangTujuan() {
        return gudangTujuan.get();
    }

    public void setGudangTujuan(String value) {
        gudangTujuan.set(value);
    }

    public StringProperty gudangTujuanProperty() {
        return gudangTujuan;
    }


    public List<PindahDetail> getAllDetail() {
        return allDetail;
    }

    public void setAllDetail(List<PindahDetail> allDetail) {
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

    public double getTotalBeratAsli() {
        return totalBeratAsli.get();
    }

    public void setTotalBeratAsli(double value) {
        totalBeratAsli.set(value);
    }

    public DoubleProperty totalBeratAsliProperty() {
        return totalBeratAsli;
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

    public String getTglPindah() {
        return tglPindah.get();
    }

    public void setTglPindah(String value) {
        tglPindah.set(value);
    }

    public StringProperty tglPindahProperty() {
        return tglPindah;
    }

    public String getNoPindah() {
        return noPindah.get();
    }

    public void setNoPindah(String value) {
        noPindah.set(value);
    }

    public StringProperty noPindahProperty() {
        return noPindah;
    }
    
}
