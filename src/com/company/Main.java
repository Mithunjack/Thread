package com.company;

public class Main{

    public static void main(String[] args) {
	// write your code here


        Account a1 = new Account(500,1);
        Account a2 = new Account(700,2);
        Account a3 = new Account(1000,3);

        System.out.println(a1.getBalance());
        System.out.println(a2.getBalance());

        Worker t1 = new Worker(a1,a2,a3,300);
        Worker t2 = new Worker(a3,a1,a2,300);

        t1.start();
        t2.start();

    }
}
