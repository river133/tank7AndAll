package com.mashibing.tankDecorator;

import java.io.IOException;
import java.util.Properties;

public class PropertyColliders {
    static Properties props = new Properties();

    private PropertyColliders() {  }

    static {
        try {
            props.load(PropertyColliders.class.getClassLoader().
                    getResourceAsStream("tanks/config.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static String get(String key){
        if(props==null) return null ;
        return  (String)props.get(key);
    }

    public static void main(String[] args) {
        String colliders = PropertyColliders.get("colliders");
        String[] split = colliders.split(",");
        System.out.println(split.length);
        for (int i = 0; i < split.length; i++) {
            System.out.println(split[i]);
            //com.mashibing.tankFacade.BulletTankCollider
            //com.mashibing.tankFacade.TankTankCollider
        }
    }
}
