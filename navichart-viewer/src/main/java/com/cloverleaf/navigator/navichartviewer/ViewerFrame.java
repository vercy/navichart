package com.cloverleaf.navigator.navichartviewer;

import javax.swing.*;

class ViewerFrame extends JFrame {

    private JNaviChart chart;

    void initUI() {
        setTitle("NaviChart");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        chart = new JNaviChart();
        getContentPane().add(chart);
        setSize(1024, 768);
        setLocationRelativeTo(null); // center on screen
    }

    // TODO add event for updating frame title to match filename

    JNaviChart getChart() {
        return chart;
    }
}
