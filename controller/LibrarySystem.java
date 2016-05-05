/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nabin.lms.controller;

import com.nabin.lms.model.Book;
import com.nabin.lms.model.Library;
import com.nabin.lms.model.VIM;
import com.nabin.lms.view.AddBookPanel;
import com.nabin.lms.view.BrowseLibrayPanel;
import com.nabin.lms.view.LibraryInterface;
import com.nabin.lms.view.LoadScreen;
import com.nabin.lms.view.MyTableModel;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author Navin
 */
public class LibrarySystem implements ChangeListener, ActionListener {

    private LibraryInterface screen;
    private AddBookPanel abp;
    private BrowseLibrayPanel blp;
    private LoadScreen ls;

    private JFileChooser chooser;
    private FileFilter filter, filter2;
    private int resultCode;
    private File vimFile, saveFile, libFile;
    private Library lib;
    private Book book;
    private List<VIM> vimCache;
    private FileInputStream fis;
    private FileOutputStream fos;
    private ObjectInputStream in;

    private String[][] dataBook, dataFile;

    private String fileName;
    private boolean exit;
    private String[] validFileTypes = {".wav", ".mp3", ".avi", ".png", ".jpeg"};
    private String validFileTypeReminder;

    public LibrarySystem() {
        initEventAttributes();

        screen = new LibraryInterface("Zaxzi's Libray Management System");
        abp = screen.getAddBookPanel();
        blp = screen.getBrowseLibrayPanel();

        screen.getJTabbedPane().addChangeListener(this);
        abp.addActionListener(this);
        blp.addActionListener(this);

        ls = new LoadScreen("Welcome to Zaxzi's Library Mangement System");
        ls.addActionListener(this);
        ls.setVisible(true);
    }

    private void initEventAttributes() {
        chooser = new JFileChooser();

        // filter2 = FileNameExtensionFilter("Video/Image/Music Files", ".wav", ".mp3", ".avi", ".mp4", ".png", ".jpeg");
        //filter = FileNameExtensionFilter("Library Files", ".ser");
        chooser.addChoosableFileFilter((javax.swing.filechooser.FileFilter) filter);
        chooser.addChoosableFileFilter((javax.swing.filechooser.FileFilter) filter2);

        lib = new Library();

        exit = false;

        vimCache = new ArrayList<>();
        vimFile = null;
        saveFile = null;
        libFile = null;
        validFileTypeReminder = "valid file types end with .wav, .mp3, .avi"
                + ".mp4,.png,.jpeg";

    }

    @Override
    public void stateChanged(ChangeEvent e) {
        if (screen.getJTabbedPane().getSelectedIndex() == 1) {
            screen.setSize(440, 452);
        } else {
            screen.setSize(400, 460);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == abp.getbBrowse()) {
            openChooserAndSetTheVIMFile();
        } else if (e.getSource() == abp.getbAddFile()) {
            addVIMFileToVimCache();
        } else if (e.getSource() == abp.getbAddBook()) {
            addVIMFilesInVimCacheToBookAndAddBookToLibray();
            reloadDataBook();
            reloadDataFile();
        } else if (e.getSource() == abp.getbListAllBooks()) {
            listAllBooksInLibrary();
        } else if (e.getSource() == abp.getbSave()) {
            save();
        } else if (e.getSource() == abp.getbSaveAndQuit()) {
            saveAndQuit();
        } else if (e.getSource() == blp.getButtonOpnenBook()) {
            openBook();
        } else if (e.getSource() == blp.getButtonDeleteBook()) {

        } else if (e.getSource() == blp.getButtonOpenFile()) {
            openFile();
        } else if (e.getSource() == blp.getButtonDeleteBook()) {

        } else if (e.getSource() == blp.getButtonSava()) {

            save();
        } else if (e.getSource() == blp.getButtonSavaAndQuit()) {

            saveAndQuit();
        } else if (e.getSource() == ls.getButtonLoad()) {

            reloadDataBook();
            reloadDataFile();
            loadLibrary();
            chooser.setFileFilter((javax.swing.filechooser.FileFilter) filter);
        } else if (e.getSource() == ls.getButtonNew()) {
            ls.dispose();
            screen.setVisible(true);

        } else if (e.getSource() == ls.getButtonExit()) {

            System.exit(0);
        }
    }

    private void openFile() {
        int row, column;
        VIM v;
        String fileName;
        File file;

        row = ((JTable) blp.getFileTable()).getSelectedRow();
        column = ((JTable) blp.getFileTable()).getSelectedColumn();
        fileName = ((JTable) blp.getFileTable()).getValueAt(row, column).toString();
        v = book.getVIMByName(fileName);

        try {
            file = new File(v.getName());
            fos = new FileOutputStream(file);
            fos.write(v.getData());
            fos.close();
            Desktop.getDesktop().open(file);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

    }

    private void openBook() {
        int row, column;
        String isbn;

        row = ((JTable) blp.getBookTable()).getSelectedRow();
        column = 3;
        isbn = ((JTable) blp.getBookTable()).getValueAt(row, column).toString();
        book = lib.getBookByISBN(isbn);

        dataFile = book.toStringVectorFiles();
        reloadDataFile();
    }

    private void loadLibrary() {
        chooser.setFileFilter((javax.swing.filechooser.FileFilter) filter2);
        resultCode = chooser.showOpenDialog(screen);
        if (resultCode == JFileChooser.APPROVE_OPTION) {
            libFile = chooser.getSelectedFile();

            try {
                fis = new FileInputStream(libFile);
                in = new ObjectInputStream(fis);
                lib = (Library) in.readObject();
                in.close();

            } catch (IOException ioe) {
                ioe.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            ls.dispose();
            reloadDataBook();
            screen.setVisible(true);
        }
    }

    private void reloadDataBook() {
        while (((MyTableModel) blp.getBookTable().getModel()).getRowCount() > 0) {
            ((MyTableModel) blp.getBookTable().getModel()).removeRow(0);
        }
        dataBook = lib.toStringVector();
        for (int i = 0; 1 < dataBook.length; i++) {
            ((MyTableModel) blp.getBookTable().getModel()).addRow(dataBook[i]);
        }
    }

    private void reloadDataFile() {
        while (((MyTableModel) blp.getBookTable().getModel()).getRowCount() > 0) {
            ((MyTableModel) blp.getBookTable().getModel()).removeRow(0);
        }
        if (dataFile != null) {
            for (int i = 0; 1 < dataFile.length; i++) {
                ((MyTableModel) blp.getBookTable().getModel()).insertRow(i, dataFile[i]);
            }
        }
    }

    private void saveAndQuit() {
        save();
        if (exit) {
            System.exit(0);
        }
    }

    private void save() {
        fileName = JOptionPane.showInputDialog(screen, "Enter file name to save as..", "Save", JOptionPane.INFORMATION_MESSAGE);
        if (fileName != null) {
            if (!fileName.trim().contentEquals("")) {
                FileOutputStream fos = null;
                ObjectOutput out = null;

                try {
                    saveFile = new File(fileName.trim() + ".ser");
                    if (!saveFile.exists()) {
                        fos = new FileOutputStream(saveFile);
                        out = new ObjectOutputStream(fos);
                        out.writeObject(lib);
                        fos.close();
                        out.close();
                        exit = true;
                    } else {
                        int resultCode = JOptionPane.showConfirmDialog(screen, "File name already exit.\n Overwrite it?", "Warning", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                        if (resultCode == JOptionPane.YES_NO_OPTION) {
                            fos = new FileOutputStream(saveFile);
                            out = new ObjectOutputStream(fos);
                            out.writeObject(lib);
                            fos.close();
                            out.close();
                            exit = true;
                        } else {
                            abp.getJtaLog().append("\n> save cancelled");
                            exit = false;
                        }
                    }

                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            } else {
                abp.getJtaLog().append("\n> save cancelled");
                exit = false;
            }
        } else {
            abp.getJtaLog().append("\n> save cancelled");
            exit = false;
        }
    }

    private void listAllBooksInLibrary() {
        abp.getJtaLog().setText("> Listing  all Books in Library");
        abp.getJtaLog().append("  " + lib.toString());
    }

    private void addVIMFilesInVimCacheToBookAndAddBookToLibray() {
        boolean ISBNAlreadyExist = false;
        boolean AllFieldsAreFilled = false;
        int isbn = 0;
        double price = 0.0;
        Book b;
        if (!abp.getJtIsbn().getText().trim().contentEquals("")
                && !abp.getJtPrice().getText().trim().contentEquals("")
                && !abp.getJtTitle().getText().trim().contentEquals("")
                && !abp.getJtAuthor().getText().trim().contentEquals("")) {
            AllFieldsAreFilled = true;
        }
        if (AllFieldsAreFilled) {
            try {
                isbn = Integer.parseInt(abp.getJtIsbn().getText().trim());
                price = Double.parseDouble(abp.getJtPrice().getText().trim());
                ISBNAlreadyExist = lib.doesISBNAlreadyExit(isbn);
                if (ISBNAlreadyExist) {
                    JOptionPane.showMessageDialog(screen, isbn + "already exist!\n please use another isbn");

                } else {
                    b = new Book(isbn, abp.getJtTitle().getText().trim(), abp.getJtAuthor().getText().trim(), price);
                    for (int i = 0; i < vimCache.size(); i++) {
                        b.addVIM(vimCache.get(i));
                    }
                    lib.addBook(b);
                    abp.getJtIsbn().setText("");
                    abp.getJtTitle().setText("");
                    abp.getJtAuthor().setText("");
                    abp.getJtPrice().setText("");
                    abp.getJtaLog().append("\n>" + b.getTitle() + "has been added to the library");
                    vimCache = new ArrayList<>();
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(screen, "ISBN and price is not a number!");
            }
        } else {
            JOptionPane.showMessageDialog(screen, "please fill out all non-optional fields.");
        }

    }

    private void addVIMFileToVimCache() {
        if (vimFile != null) {
            for (int i = 0; i < validFileTypes.length; i++) {
                if (abp.getJtFile().getText().trim().endsWith(validFileTypes[i])) {
                    byte[] data = new byte[(int) vimFile.length()];
                    try {
                        fis = new FileInputStream(vimFile);
                        fis.read(data);
                        fis.close();
                    } catch (FileNotFoundException e) {
                        JOptionPane.showMessageDialog(screen, "File not Found!!");
                    } catch (IOException e) {
                        JOptionPane.showMessageDialog(screen, "Error Reading File!!");
                    }
                    VIM v = new VIM(abp.getJtFile().getText().trim(), data);
                    vimCache.add(v);
                    vimFile = null;
                    abp.getJtaLog().append("\n>" + abp.getJtFile().getText().trim() + "is ready to added to book.");
                    abp.getJtFile().setText("optional");
                    return;
                }
            }
            JOptionPane.showMessageDialog(screen, "Somethig went wrong\n please click browse again" + "and choose your file");

        } else {
            JOptionPane.showMessageDialog(screen, abp.getJtFile().getText() + "is not valid file type!\n" + validFileTypeReminder);
        }
    }

    private void openChooserAndSetTheVIMFile() {
        resultCode = chooser.showOpenDialog(screen);
        if (resultCode == JFileChooser.APPROVE_OPTION) {
            vimFile = chooser.getSelectedFile();
            abp.getJtFile().setText(vimFile.getName());
        }
    }
}
