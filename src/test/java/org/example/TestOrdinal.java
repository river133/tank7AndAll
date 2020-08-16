package org.example;

import org.junit.Test;

public class TestOrdinal {
    @Test
    public void t(){
        System.out.println("CellPhone List:");
        for(Mobile m : Mobile.values()) {
            System.out.println(m + " costs " + m.showPrice() + " dollars");
        }
        System.out.println("----------------------");
        Mobile ret = Mobile.Samsung;
        System.out.println("The ordinal is = " + ret.ordinal());
        System.out.println("MobileName = " + ret.name());

        /*CellPhone List:
        Samsung costs 400 dollars
        Nokia costs 250 dollars
        Motorola costs 325 dollars
        ------------------------
        The ordinal is = 0
        MobileName = Samsung*/

    }
}

