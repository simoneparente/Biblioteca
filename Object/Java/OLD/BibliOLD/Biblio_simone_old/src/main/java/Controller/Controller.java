package Controller;

import Views.LoginView;

import javax.swing.*;

public class Controller {
    public JFrame actualFrame;

    public Controller() {
        LoginView loginView = new LoginView();

        loginView.show_Finestra();
    }



    //public static void loginToRegister() {
    //  initialframe.dispose();
    //new RegisterView();
    //}
}
