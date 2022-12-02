package org.example;

import java.util.HashMap;

public class IDandPW
{
    //hashmap conservano coppie di (ID,valore)
    HashMap<String,String> logininfo = new HashMap<String,String>();
    IDandPW(){
        logininfo.put("Simone", "Clemente");
        logininfo.put("Lorenzo", "BigMoney");
        logininfo.put("Mario", "Pinguino");
        logininfo.put("Stefano", "MaScopass");
        logininfo.put("Davide", "acapocchj");
    }
    protected HashMap<String, String> getLoginInfo() {
        return logininfo;
    }

    public void setLoginInfo(HashMap<String, String> logininfo) {
        this.logininfo = logininfo;
    }


}
