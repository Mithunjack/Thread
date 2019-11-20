package com.company;

public class Worker extends Thread{
    Account a1;
    Account a2;
    Account a3;
    int transferBal;

    Worker(Account a1,Account a2, Account a3, int value){
        this.a2 = a1;
        this.a1 = a2;
        this.transferBal = value;
        this.a3 = a3;
    }

    public void run(){
          while (true){
            //a1.transfer(a2,transferBal);
            //System.out.println("Account ID: "+a1.id+ " "+a1.getBalance());
            Account.collectedTransfer(a3,a1,10,a2,15);
            System.out.println(a3.getBalance());
        }
    }


}
