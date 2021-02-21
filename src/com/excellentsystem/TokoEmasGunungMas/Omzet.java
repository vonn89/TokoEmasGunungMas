/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasGunungMas;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Yunaz
 */
public class Omzet {

    private final StringProperty tanggal = new SimpleStringProperty();
    private final DoubleProperty penjualan = new SimpleDoubleProperty();
    private final DoubleProperty pembelian = new SimpleDoubleProperty();
    private final DoubleProperty terimaGadai = new SimpleDoubleProperty();
    private final DoubleProperty gadaiLunas = new SimpleDoubleProperty();
    private final DoubleProperty bungaGadai = new SimpleDoubleProperty();

    public double getBungaGadai() {
        return bungaGadai.get();
    }

    public void setBungaGadai(double value) {
        bungaGadai.set(value);
    }

    public DoubleProperty bungaGadaiProperty() {
        return bungaGadai;
    }

    public double getGadaiLunas() {
        return gadaiLunas.get();
    }

    public void setGadaiLunas(double value) {
        gadaiLunas.set(value);
    }

    public DoubleProperty gadaiLunasProperty() {
        return gadaiLunas;
    }

    public double getTerimaGadai() {
        return terimaGadai.get();
    }

    public void setTerimaGadai(double value) {
        terimaGadai.set(value);
    }

    public DoubleProperty terimaGadaiProperty() {
        return terimaGadai;
    }

    public double getPembelian() {
        return pembelian.get();
    }

    public void setPembelian(double value) {
        pembelian.set(value);
    }

    public DoubleProperty pembelianProperty() {
        return pembelian;
    }

    public double getPenjualan() {
        return penjualan.get();
    }

    public void setPenjualan(double value) {
        penjualan.set(value);
    }

    public DoubleProperty penjualanProperty() {
        return penjualan;
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
