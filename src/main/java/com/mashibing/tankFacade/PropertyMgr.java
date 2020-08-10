package com.mashibing.tankFacade;

import java.io.IOException;
import java.util.Properties;

public class PropertyMgr {
    static Properties props = new Properties();

    private PropertyMgr() {  }

    static {
        try {
            props.load(PropertyMgr.class.getClassLoader().
                    getResourceAsStream("tanks/config.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static int get(String key){
        if(props==null) return -1 ;
        return Integer.parseInt((String)props.get(key)) ;
    }

    public static String getStr(String key) {
       return  (String)props.get(key);
    }
}
