package org.Bibliotech;

import com.formdev.flatlaf.FlatDarkLaf;
import org.Bibliotech.Controller.*;

public class Main {
    public static void main(String[] args) {
        FlatDarkLaf.setup();
        Controller controller= Controller.getInstance();
    }
}