package org.Bibliotech.Controller;

import org.Bibliotech.View.*;

public class MainController {

    LoginView loginView;
    RegisterView registerView;
    SearchView searchView;

    public MainController(){
        loginView = new LoginView();
        registerView = new RegisterView();
        searchView = new SearchView();
    }

    public LoginView getLoginView() {
        return loginView;
    }

    public RegisterView getRegisterView() {
        return registerView;
    }

    public SearchView getSearchView() {
        return searchView;
    }

    public void setSearchView(SearchView searchView) {
        this.searchView = searchView;
    }

    public void openGUI(String guiToShow){
        switch(guiToShow){
            case "login":
                loginView.showView();
                break;
            case "register":
                registerView.showView();
                break;
            case "search":
                searchView.showView();
                break;
            default:
                System.out.println("Error: No such GUI");
                break;
        }
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
