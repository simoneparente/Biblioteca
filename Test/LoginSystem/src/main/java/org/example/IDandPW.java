package org.example;

import java.util.HashMap;

public class IDandPW
{
    //hashmap conservano coppie di (ID,valore)
    HashMap<String,String> loginInfo = new HashMap<String,String>();
    IDandPW(){
        loginInfo.put("Simone", "Clemente");
        loginInfo.put("Lorenzo", "BigMoney");
        loginInfo.put("Mario", "Pinguino");
        loginInfo.put("Stefano", "MaScopass");
        loginInfo.put("Davide", "acapocchj");
    }
    protected HashMap<String, String> getLoginInfo() {
        return loginInfo;
    }

    public void setLoginInfo(HashMap<String, String> loginInfo) {
        this.loginInfo = loginInfo;
    }
}