package com.company;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

public class Account {
    public int id;
    private int balance;

    public Account(int initialDeposit,int id) {
        balance = initialDeposit;
        this.id = id;
    }

    public synchronized int getBalance() {
        return balance;
    }

    public synchronized void deposit(int amount) {
        balance += amount;
    }

    public boolean withdraw(int amount) {
        synchronized (this) {
            if (balance >= amount) {
                balance = balance-amount;
                return true;
            } else {
                return false;
            }
        }
    }

    // Attention, this code can produce a deadlock, if two (or more) threads
    // transfer money from/to a circle of accounts.
    public boolean transferWithDeadlock (Account dest, int amount) {
        if (withdraw(amount)) {
            dest.deposit(amount);
            return true;
        } else {
            return false;
        }
    }

    // Idead for a deadlock prevention. Compare the accounts and always lock
    // the `smaller` account first. This realtes to having one philosopher
    // taking its sticks in reverse order.
    public boolean transfer(Account dest, int amount) {
        if (dest.id>this.id) {  // This comparison does not work yet, correct it.
            synchronized(dest) {
                synchronized(this) {
                    if (withdraw(amount)) {
                        dest.deposit(amount);
                        return true;
                    } else {
                        return false;
                    }
                }
            }
        } else {
            synchronized(this) {
                synchronized(dest) {
                    if (withdraw(amount)) {
                        dest.deposit(amount);
                        return true;
                    } else {
                        return false;
                    }
                }
            }
        }

    }

    public static boolean collectedTransfer(Account destination,Account s1,int amount1,Account s2,int amount2){
        int collectedMoney=0;

        Account[] array = new Account[3];
        array[0] = s1;
        array[1] = s2;
        array[2] = destination;


        Arrays.sort(array, new Comparator<Account>() {
            @Override
            public int compare(Account t, Account t1) {
                if (t.id>t1.id){
                    return 1;
                }
                else if (t.id<t1.id){
                    return -1;
                }
                else {
                    return 0;
                }
            }
        });

         synchronized (array[0]){
                synchronized (array[1]){
                    synchronized (array[2]){
                        if (s1.withdraw(amount1)){
                            collectedMoney = collectedMoney + amount1;
                        }else {
                            System.out.println("do not have enough balance to transfer");
                        }
                        if (s2.withdraw(amount2)){
                            collectedMoney = collectedMoney + amount2;
                        }else {
                            System.out.println("do not have enough balance to transfer");
                        }
                        destination.deposit(collectedMoney);
                    }
                }
         }

        return true;
    }
}
