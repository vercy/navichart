package com.cloverleaf.navigator.navichartviewer;

import javax.swing.*;
import java.awt.*;

class ViewerFrame extends JFrame {

    void initUI() {
        setTitle("NaviChart");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(new GridLayout(2,2));
        getContentPane().add(new JNaviChart());
        getContentPane().add(new JNaviChart());
        getContentPane().add(new JNaviChart());
        getContentPane().add(new JNaviChart());
        setSize(1024, 768);
        setLocationRelativeTo(null); // center on screen
    }

}
