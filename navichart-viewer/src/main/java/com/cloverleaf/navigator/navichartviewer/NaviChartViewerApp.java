package com.cloverleaf.navigator.navichartviewer;

public class NaviChartViewerApp {

    public static void main(String[] args) {
        CmdLineArgs cmdLine = CmdLineArgs.from(args);
        if(cmdLine.getShowHelp()) {
            showHelp();
            return;
        }

        ViewerFrame mainWindow = new ViewerFrame();
        mainWindow.initUI();
        mainWindow.setVisible(true);
    }

    private static void showHelp() {
        System.out.println("Navichart Viewer Version: 0 (prototype)");
        System.out.println("usage: navichart [help | fileName]");
        System.out.println("  help     - shows this message");
        System.out.println("  fileName - opens the passed file if exists");
    }
}
