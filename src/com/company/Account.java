package com.company;

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

    public static boolean collectedTransfer(Account destination,Account a1,int amount1,Account a2,int amount2){
        int collectedMoney=0;
        if(destination.id<a1.id){
            synchronized (destination){
                synchronized (a1){
                    synchronized (a2){
                        if (a1.withdraw(amount1)){
                            collectedMoney = collectedMoney + amount1;
                        }else {
                            System.out.println("do not have enough balance to transfer");
                        }
                        if (a2.withdraw(amount2)){
                            collectedMoney = collectedMoney + amount2;
                        }else {
                            System.out.println("do not have enough balance to transfer");
                        }
                        destination.deposit(collectedMoney);
                    }
                }
            }
        }
        else {
            if(a1.id < a2.id){

                synchronized (a1){
                    synchronized (destination){
                        synchronized (a2){
                            if (a1.withdraw(amount1)){
                                collectedMoney = collectedMoney + amount1;
                            }else {
                                System.out.println("do not have enough balance to transfer");
                            }
                            if (a2.withdraw(amount2)){
                                collectedMoney = collectedMoney + amount2;
                            }else {
                                System.out.println("do not have enough balance to transfer");
                            }
                            destination.deposit(collectedMoney);
                        }
                    }
                }
            }else{
                synchronized (a2){
                    synchronized (destination){
                        synchronized (a1){
                            if (a1.withdraw(amount1)){
                                collectedMoney = collectedMoney + amount1;
                            }else {
                                System.out.println("do not have enough balance to transfer");
                            }
                            if (a2.withdraw(amount2)){
                                collectedMoney = collectedMoney + amount2;
                            }else {
                                System.out.println("do not have enough balance to transfer");
                            }
                            destination.deposit(collectedMoney);
                        }
                    }
                }
            }
        }
        return true;
    }
}
