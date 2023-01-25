package org.example;

import javax.swing.*;

public class Controller {

    private Login login;
    private RegisterGUI registerGUI;

    public Controller() {
        login = new Login(getController());
        registerGUI = new RegisterGUI(getController());
    }

    public Controller getController() {
        return this;
    }
    public Login getLoginFrame() {
        return login;
    }
    public RegisterGUI getRegisterGUI() {
        return registerGUI;
    }
    public void goToPage(JFrame oldF, JFrame newF) {
        oldF.dispose();
        newF.setVisible(true);
    }
}
