/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nabin.lms.model;

import java.io.Serializable;

/**
 *
 * @author Navin
 */
public class VIM implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;
    private byte[] data;

    public VIM() {
        name = null;
        data = null;
    }

    public VIM(String name, byte[] data) {
        this.name = name;
        this.data = data;
    }

    public String getName() {
        return name;
    }

    public byte[] getData() {
        return data;
    }

}
