package test;

import java.io.IOException;
import java.lang.reflect.Field;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;

class A extends Thread{
    A(){
        setDaemon(true);
    }
    public void run() {
        (new B()).start();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            System.out.println("A done");
        }
    }
}

class B extends Thread{
    public void run() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            System.out.println("B done");
        }
    }
}
public class testClass {

    public static void main(String args[]) {
        B b = new B();
        b.start();
        try {
            Thread.sleep(200);
            b.interrupt();
        } catch (InterruptedException e) {
            e.printStackTrace();

        }

    }

    public static void quickSort(Integer a[], int l, int r) {
        if (l < r) {
            int q = partition(a, l, r);
//            System.out.println(q + " " + l + " " + r);
            quickSort(a, l, q - 1);
            quickSort(a, q + 1, r);
        }
    }

    public static int partition(Integer a[], int l, int r) {
        int i = l;
        int j = r;
        int key = a[r];
        while (i < j) {
            while (i < j && a[i] <= key)
                ++i;
            if(i < j)
                swap(a[i], a[j--]);
            while (i < j && a[j] >= key)
                --j;
            if(i < j)
                swap(a[i++], a[j]);
        }
        return i;
    }

    public static void swap(Integer a, Integer b) {
        if(a == null || b == null)
            return;
        Class<Integer> aClass = (Class<Integer>) a.getClass();
        try {
            Field value = aClass.getDeclaredField("value");
            value.setAccessible(true);
            int temp = a;
            value.setInt(a, b);
            value.setInt(b, temp);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
