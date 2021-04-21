/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasGunungMas;

import static com.excellentsystem.TokoEmasGunungMas.Main.allUser;
import static com.excellentsystem.TokoEmasGunungMas.Main.gr;
import static com.excellentsystem.TokoEmasGunungMas.Main.rp;
import com.excellentsystem.TokoEmasGunungMas.Model.User;
import com.excellentsystem.TokoEmasGunungMas.Model.Verifikasi;
import java.text.Annotation;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeTableCell;
import javafx.util.StringConverter;

/**
 *
 * @author yunaz
 */
public class Function {
    public static StringConverter<LocalDate> getTglConverter(){
        return new StringConverter<LocalDate>() {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy");
            @Override 
            public String toString(LocalDate date) {
                if (date != null) 
                    return dateFormatter.format(date);
                else 
                    return "";
            }
            @Override 
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) 
                    return LocalDate.parse(string, dateFormatter);
                else 
                    return null;
            }
        };
    }
//    public static void setQtyField(TextField field){
//        field.setOnKeyReleased((event) -> {
//            try{
//                field.setText(gr.format(Double.parseDouble(field.getText().replaceAll(",", ""))));
//                field.end();
//            }catch(Exception e){
//                field.undo();
//            }
//        });
//    }
    public static Comparator<String> sortDate(DateFormat df){
        return (String t, String t1) -> {
            try{
                return Long.compare(df.parse(t).getTime(),df.parse(t1).getTime());
            }catch(ParseException e){
                return -1;
            }
        };
    }
    public static TableCell getTableCell(DecimalFormat df){ 
        TableCell cell = new TableCell<Annotation, Number>(){
            @Override
            public void updateItem(Number value, boolean empty) {
                super.updateItem(value, empty);
                if (empty)
                    setText(null);
                else 
                    setText(df.format(value.doubleValue()));
            }
        };
        return cell;
    }
    public static TreeTableCell getTreeTableCell(DecimalFormat df){ 
        TreeTableCell cell = new TreeTableCell<Annotation, Number>(){
            @Override
            public void updateItem(Number value, boolean empty) {
                super.updateItem(value, empty);
                if (empty)
                    setText(null);
                else 
                    setText(df.format(value.doubleValue()));
            }
        };
        return cell;
    }
    public static DateCell getDateCellDisableBefore(LocalDate date){
        return new DateCell() {
            @Override
            public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                DayOfWeek day = DayOfWeek.from(item);
                if (day == DayOfWeek.SUNDAY) 
                    this.setStyle("-fx-background-color: derive(RED, 150%);");
                if (item.isBefore(date)) 
                    this.setDisable(true);
            }
        };
    }
    public static DateCell getDateCellDisableAfter(LocalDate date){
        return new DateCell() {
            @Override
            public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                DayOfWeek day = DayOfWeek.from(item);
                if (day == DayOfWeek.SUNDAY) 
                    this.setStyle("-fx-background-color: derive(RED, 150%);");
                if (item.isAfter(date)) 
                    this.setDisable(true);
            }
        };
    }
    public static DateCell getDateCellMulai(DatePicker tglAkhir){
        return new DateCell() {
            @Override
            public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                DayOfWeek day = DayOfWeek.from(item);
                if (day == DayOfWeek.SUNDAY) 
                    this.setStyle("-fx-background-color: derive(RED, 150%);");
                if(item.isAfter(LocalDate.now()))
                    this.setDisable(true);
                if(item.isAfter(tglAkhir.getValue()))
                    this.setDisable(true);
            }
        };
    }
    public static DateCell getDateCellAkhir(DatePicker tglMulai){
        return new DateCell() {
            @Override
            public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                DayOfWeek day = DayOfWeek.from(item);
                if (day == DayOfWeek.SUNDAY) 
                    this.setStyle("-fx-background-color: derive(RED, 150%);");
                if(item.isAfter(LocalDate.now()))
                    this.setDisable(true);
                if(item.isBefore(tglMulai.getValue()))
                    this.setDisable(true);
            }
        };
    }
    public static void setBeratField(TextField field){
        field.setOnKeyReleased((event) -> {
            try{
                String string = field.getText();
                if(string.indexOf(".")>0){
                    String string2 = string.substring(string.indexOf(".")+1, string.length());
                    if(string2.contains("."))
                        field.undo();
                    else if(!string2.equals("") && Double.parseDouble(string2)!=0)
                        field.setText(gr.format(Double.parseDouble(string.replaceAll(",", ""))));
                }else
                    field.setText(gr.format(Double.parseDouble(string.replaceAll(",", ""))));
                field.end();
            }catch(Exception e){
                field.undo();
            }
        });
    }
    public static void setRpField(TextField field){
        field.setOnKeyReleased((event) -> {
            try{
                field.setText(rp.format(Double.parseDouble(field.getText().replaceAll(",", ""))));
                field.end();
            }catch(Exception e){
                field.undo();
            }
        });
    }
    public static boolean verifikasi(String username, String password, String status){
        Boolean v = false;
        for(User u : allUser){
            if(username.equals(u.getUsername())&&password.equals(u.getPassword())){
                for(Verifikasi ver : u.getVerifikasi()){
                    if(ver.getJenis().equals(status))
                        v = ver.isStatus();
                }
            }
        }
        return v;
    }
}
