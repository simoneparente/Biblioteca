package org.Bibliotech;

import com.formdev.flatlaf.FlatDarkLaf;
import org.Bibliotech.Controller.*;

public class Main {
    public static MainController mc;
    public static void main(String[] args) {
        FlatDarkLaf.setup();
        mc = new MainController();
        mc.openGUI(mc.getLoginView().getName());
    }
}