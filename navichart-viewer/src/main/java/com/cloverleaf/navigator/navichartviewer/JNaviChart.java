package com.cloverleaf.navigator.navichartviewer;

import javax.swing.*;
import java.awt.*;

/** Swing component  */
public class JNaviChart extends JPanel {
    private String fileName;

    public String getFileName() {
        return fileName;
    }

    void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Override
    protected void paintComponent(Graphics g) {
        String str = fileName == null || fileName.equalsIgnoreCase("") ? "No input file" : fileName;
        g.drawString(str, 10, 100);
    }
}
