/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nabin.lms.view;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

/**
 *
 * @author Navin
 */
public class LoadScreen extends JFrame {

    private static final long serialVersionID = 1L;
    private JPanel panel;
    private JButton jbtnNew, jbtnLoad, jbtnExit;

    public LoadScreen(String title) {
        super(title);

        initWidgets();
        addWidgets();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setSize(440, 70);
        setResizable(false);

    }

    private void initWidgets() {
        panel = new JPanel(new FlowLayout());

        jbtnNew = new JButton("Start New Library");
        jbtnLoad = new JButton("Load Saved Library");
        jbtnExit = new JButton("Exit");

    }

    private void addWidgets() {
        panel.add(jbtnNew);
        panel.add(jbtnLoad);
        panel.add(jbtnExit);

        panel.setBackground(new Color(194, 230, 248));
        setContentPane(panel);

    }

    public void addActionListener(ActionListener l) {
        jbtnNew.addActionListener(l);
        jbtnLoad.addActionListener(l);
        jbtnExit.addActionListener(l);
    }

    public JButton getButtonLoad() {
        return jbtnLoad;
    }

    public JButton getButtonNew() {
        return jbtnNew;
    }

    public JButton getButtonExit() {
        return jbtnExit;
    }

}
