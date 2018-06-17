package com.cloverleaf.navigator.navichartviewer;

class CmdLineArgs {

    private final String openOnStartFileName;
    private final boolean showHelp;

    private CmdLineArgs(String openOnStartFileName, boolean showHelp) {
        this.openOnStartFileName = openOnStartFileName;
        this.showHelp = showHelp;
    }

    String getOpenOnStartFileName() {
        return openOnStartFileName;
    }

    boolean getShowHelp() {
        return showHelp;
    }

    static CmdLineArgs from(String[] args) {
        String openOnStartFileName = null;
        boolean showHelp = false;
        if(args.length >= 1 && "help".equalsIgnoreCase(args[0])) {
            showHelp = true;
        } else if(args.length >= 1 && args[0] != null && args[0].endsWith(".chart")) {
            openOnStartFileName = args[0];
        }

        return new CmdLineArgs(openOnStartFileName, showHelp);
    }
}
