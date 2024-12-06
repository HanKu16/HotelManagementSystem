package org.po2_jmp;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 50; ++i) {
            Thread.sleep(1000);
            i++;
            System.out.println(i + ". Backend is working");
        }
    }

}