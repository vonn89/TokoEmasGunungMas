/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasGunungMas.Model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Xtreme
 */
public class Sistem {
    private final StringProperty namaToko = new SimpleStringProperty();
    private final StringProperty alamatToko = new SimpleStringProperty();
    private final StringProperty noTelpToko = new SimpleStringProperty();
    private final StringProperty prefixBarcode = new SimpleStringProperty();
    private final DoubleProperty beratLabel = new SimpleDoubleProperty();
    private final StringProperty tglSystem = new SimpleStringProperty();

    public String getPrefixBarcode() {
        return prefixBarcode.get();
    }

    public void setPrefixBarcode(String value) {
        prefixBarcode.set(value);
    }

    public StringProperty prefixBarcodeProperty() {
        return prefixBarcode;
    }


    public String getNoTelpToko() {
        return noTelpToko.get();
    }

    public void setNoTelpToko(String value) {
        noTelpToko.set(value);
    }

    public StringProperty noTelpTokoProperty() {
        return noTelpToko;
    }

    public String getAlamatToko() {
        return alamatToko.get();
    }

    public void setAlamatToko(String value) {
        alamatToko.set(value);
    }

    public StringProperty alamatTokoProperty() {
        return alamatToko;
    }

    public String getNamaToko() {
        return namaToko.get();
    }

    public void setNamaToko(String value) {
        namaToko.set(value);
    }

    public StringProperty namaTokoProperty() {
        return namaToko;
    }

    
    public String getTglSystem() {
        return tglSystem.get();
    }

    public void setTglSystem(String value) {
        tglSystem.set(value);
    }

    public StringProperty tglSystemProperty() {
        return tglSystem;
    }
    public double getBeratLabel() {
        return beratLabel.get();
    }

    public void setBeratLabel(double value) {
        beratLabel.set(value);
    }

    public DoubleProperty beratLabelProperty() {
        return beratLabel;
    }


}
