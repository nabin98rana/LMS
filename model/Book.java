/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nabin.lms.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Navin
 */
public class Book implements Serializable {

    private static final long serialVersionUID = 1L;

    private int isbn;
    private String author, title;
    private double price;

    private List<VIM> vims;

    public Book() {
        isbn = 0;
        author = null;
        title = null;
        price = 0;
        vims = new ArrayList<VIM>();

    }

    public Book(int isbn, String author, String title, double price) {
        this.isbn = isbn;
        this.author = author;
        this.title = title;
        this.price = price;
        vims = new ArrayList<VIM>();
    }

    public void addVIM(VIM v) {
        vims.add(v);
    }

    public int getIsbn() {
        return isbn;
    }

    public String getISBN() {
        return String.valueOf(isbn);
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public double getPrice() {
        return price;
    }

    public String getPRice() {
        return String.valueOf(price);
    }

    public VIM getVIMByName(String name) {
        VIM v = null;
        Iterator<VIM> i = vims.iterator();
        while (i.hasNext()) {
            v = i.next();
            if (v.getName().toLowerCase().contentEquals(name.toLowerCase())) {
                return v;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        String vimNames = "";
        String vimAmount = "(" + String.valueOf(vims.size()) + ")";
        Iterator<VIM> i = vims.iterator();
        while (i.hasNext()) {
            vimNames += i.next().getName() + ",";

        }
        return "\n Title:" + title + "\nAuthor:" + author + "\nISBN:" + price + "\nVIM Files" + vimAmount + ":" + vimNames + "\n";
    }

    public String[][] toStringVectorFiles() {
        String total[][] = new String[vims.size()][3];
        VIM v;
        for (int i = 0; i < vims.size(); i++) {
            v = vims.get(i);
            if (v.getName().endsWith("wav") || v.getName().endsWith("mp3")) {
                total[i][0] = v.getName();
            }
            if (v.getName().endsWith("png") || v.getName().endsWith("jpeg")) {
                total[i][1] = v.getName();
            } else {
                total[i][2] = v.getName();
            }
        }
        return total;
    }

}
