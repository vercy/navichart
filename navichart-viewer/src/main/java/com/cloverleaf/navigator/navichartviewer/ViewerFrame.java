package com.cloverleaf.navigator.navichartviewer;

import javax.swing.*;
import java.awt.*;

class ViewerFrame extends JFrame {

    private static final double ONE_SIXTH = 1.0 / 6;

    void initUI() {
        setTitle("NaviChart");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        
        c.weightx = ONE_SIXTH;
        c.weighty = ONE_SIXTH;
        c.gridx = 0;
        c.gridy = 0;
        getContentPane().add(new JNaviChart(), c);

        c.weightx = ONE_SIXTH * 5;
        c.weighty = ONE_SIXTH;
        c.gridx = 1;
        c.gridy = 0;
        getContentPane().add(new JNaviChart(), c);

        c.weightx = ONE_SIXTH;
        c.weighty = ONE_SIXTH * 5;
        c.gridx = 0;
        c.gridy = 1;
        getContentPane().add(new JNaviChart(), c);

        c.weightx = ONE_SIXTH * 5;
        c.weighty = ONE_SIXTH * 5;
        c.gridx = 1;
        c.gridy = 1;
        getContentPane().add(new JNaviChart(), c);


        setSize(1024, 768);
        setLocationRelativeTo(null); // center on screen
    }

}
