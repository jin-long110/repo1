package com.core;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import sun.awt.IconInfo;

import java.util.*;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }

    @Test
    public void  shiyan(){
        String s="Ilovejava";
        System.out.println(s.substring(5));
    }
    @Test
    public void jihe(){
        ArrayList<String> objects = new ArrayList<>();
        objects.add("张华");
        objects.add("张二");
        objects.add("张四");
        objects.add("张狗");
        objects.add("张督");
        Iterator<String> iterator = objects.iterator();
        while (iterator.hasNext()){
            System.out.println(iterator.next());
        }
    }
    @Test
    public void hashtable(){
        Hashtable<Integer, String> hashtables = new Hashtable<>();
        hashtables.put(1,"张三");
    }
    @Test
    public void listdechongfu(){
        HashSet<String> strings = new HashSet<>();
        strings.add("赵三");
        strings.add("赵四");
        strings.add("赵三");
        strings.add("赵无");
        strings.add("赵六");
        strings.add("赵三");
        for (String s:strings) {
            System.out.println(s);
        }
        System.out.println("___+_+_+_+_+__+");
        Set<String> strings1 = Collections.synchronizedSet(strings);
        for (String st:strings1){
            System.out.println(st);
        }
    }

    @Test
    public void ceshi(){
        int a=0x122;
        System.out.println(a);
        System.out.println(a/2);
    }
}
