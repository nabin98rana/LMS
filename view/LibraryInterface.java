/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nabin.lms.view;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

/**
 *
 * @author Navin
 */
public class LibraryInterface extends JFrame {

    private static final long serialVersionUID = 1L;

    private AddBookPanel abp;
    private BrowseLibrayPanel blp;
    private JTabbedPane jtb;
    private String filler;

    public LibraryInterface(String title) {
        super(title);

        jtb = new JTabbedPane();
        blp = new BrowseLibrayPanel();
        abp = new AddBookPanel();

        filler = "      ";

        jtb.addTab(filler + filler + "Add Book" + filler + filler, abp);
        jtb.addTab(filler + "Browse Library" + filler, blp);

        add(jtb);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setSize(400, 460);
        setResizable(false);

    }

    public AddBookPanel getAddBookPanel() {
        return abp;
    }

    public BrowseLibrayPanel getBrowseLibrayPanel() {
        return blp;
    }

    public JTabbedPane getJTabbedPane() {
        return jtb;
    }

    public String getFiller() {
        return filler;
    }
}
