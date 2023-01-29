package org.Bibliotech;

import org.Bibliotech.Controller.*;

public class Main {
    public static MainController mc;
    public static void main(String[] args) {
        mc = new MainController();
        mc.openGUI(mc.getLoginView().getName());
    }
}