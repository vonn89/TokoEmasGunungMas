/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasGunungMas.Model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Xtreme
 */
public class PindahDetail {

    private final StringProperty noPindah = new SimpleStringProperty();
    private final StringProperty kodeBarcode = new SimpleStringProperty();
    private final StringProperty gudangAsal = new SimpleStringProperty();
    private Barang barang;
    private PindahHead pindahHead;

    public PindahHead getPindahHead() {
        return pindahHead;
    }

    public void setPindahHead(PindahHead pindahHead) {
        this.pindahHead = pindahHead;
    }
    
    public String getGudangAsal() {
        return gudangAsal.get();
    }

    public void setGudangAsal(String value) {
        gudangAsal.set(value);
    }

    public StringProperty gudangAsalProperty() {
        return gudangAsal;
    }

    public Barang getBarang() {
        return barang;
    }

    public void setBarang(Barang barang) {
        this.barang = barang;
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
