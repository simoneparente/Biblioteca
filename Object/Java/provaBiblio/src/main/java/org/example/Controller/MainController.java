package org.example.Controller;

import org.example.View.*;

import javax.swing.*;

public class MainController {

    LoginView loginView;
    RegisterView registerView;
    SearchView searchView;
    public MainController(){
        loginView = new LoginView();
        registerView = new RegisterView();
        searchView = new SearchView();
    }

    public void switchGUI(String guiToShow, String guiToHide){
        switch(guiToShow){
            case "login":
                switch(guiToHide){
                    case "register":
                        registerView.hideView();
                        break;
                    case "search":
                        searchView.hideView();
                        break;
                    case "":
                        break;
                    default:
                        System.out.println("Error: No such GUI");
                        break;
                }
                loginView.showView();
                break;
            case "register":
                switch(guiToHide){
                    case "login":
                        loginView.hideView();
                        break;
                    case "search":
                        searchView.hideView();
                        break;
                    case "":
                        break;
                    default:
                        System.out.println("Error: No such GUI");
                        break;
                }
                registerView.showView();
                break;
            case "search":
                switch(guiToHide){
                    case "login":
                        loginView.hideView();
                        break;
                    case "register":
                        registerView.hideView();
                        break;
                    case "":
                        break;
                    default:
                        System.out.println("Error: No such GUI");
                        break;
                }
                searchView.showView();
                break;
            default:
                System.out.println("Error: No such GUI");
                break;
        }
    }
}
