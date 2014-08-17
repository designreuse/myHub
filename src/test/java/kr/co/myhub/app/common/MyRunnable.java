package kr.co.myhub.app.common;

public class MyRunnable implements Runnable {

    @Override
    public void run() {
        this.go();    
    }
    
    public void go() {
        System.out.println("go");
        this.doMore();
    }
    
    public void doMore() {
        System.out.println("doMore");
    }
    
    

}
