package org.example;

public class Main {
    public static void main(String[] args) {
        ConnectJDBC.main();
        IDandPW idpw = new IDandPW();
        LoginPage logpage = new LoginPage(idpw.getLoginInfo());


    }
}