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
            case "Login":
                loginView.showView();
                break;
            case "Register":
                registerView.showView();
                break;
            case "Search":
                searchView.showView();
                break;
            default:
                System.out.println("Error: No such GUI");
                break;
        }
    }

    public void switchGUI(String guiToShow, String guiToHide){
        switch(guiToShow){
            case "Login":
                switch(guiToHide){
                    case "Register":
                        registerView.hideView();
                        break;
                    case "Search":
                        searchView.hideView();
                        break;
                    default:
                        System.out.println("Error: No such GUI");
                        break;
                }
                loginView.showView();
                break;
            case "Register":
                switch(guiToHide){
                    case "Login":
                        loginView.hideView();
                        break;
                    case "Search":
                        searchView.hideView();
                        break;
                    default:
                        System.out.println("Error: No such GUI");
                        break;
                }
                registerView.showView();
                break;
            case "Search":
                switch(guiToHide){
                    case "Login":
                        loginView.hideView();
                        break;
                    case "Register":
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
