package org.example;

import org.example.Controller.*;

public class Main {
    public static MainController mc;
    public static void main(String[] args) {
        mc = new MainController();
        mc.switchGUI("login", "");
    }
}