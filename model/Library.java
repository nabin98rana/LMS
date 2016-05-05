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
public class Library extends Object implements Serializable {

    private static final long serialVersionUID = 1L;
    private List<Book> collection;

    public Library() {
        collection = new ArrayList<>();
    }

    public void addBook(Book book) {
        collection.add(book);
    }

    public Book getBookByName(String name) {
        Book v = null;
        Iterator<Book> i = collection.iterator();
        while (i.hasNext()) {
            v = i.next();
            if (v.getTitle().toLowerCase().contentEquals(name.toLowerCase())) {
                return v;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        String total = "\n";

        Iterator<Book> i = collection.iterator();
        while (i.hasNext()) {
            Book b = (Book) i.next();
            total = total + b.toString();
        }
        return total;

    }

    public boolean doesISBNAlreadyExit(int isbn) {
        Iterator<Book> i = collection.iterator();
        while (i.hasNext()) {
            if (i.next().getIsbn() == isbn) {
                return true;
            }
        }
        return false;
    }

    public String[][] toStringVector() {
        String[][] total = new String[collection.size()][5];
        for (int i = 0; i < collection.size(); i++) {
            total[i][0] = collection.get(i).getTitle();
            total[i][1] = collection.get(i).getAuthor();
            total[i][2] = collection.get(i).getPRice();
            total[i][3] = collection.get(i).getISBN();
        }
        return total;
    }

    public Book getBookByISBN(String isbn) {
        for (int i = 0; i < collection.size(); i++) {
            if (collection.get(i).getISBN().contentEquals(isbn)) {
                return collection.get(i);
            }
        }
        return null;
    }
}
