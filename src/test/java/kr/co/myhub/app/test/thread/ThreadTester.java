package kr.co.myhub.app.test.thread;

public class ThreadTester {
    
    public static void main(String args[]) {
        Runnable threadJob = new MyRunnable();
        
        Thread myThread = new Thread(threadJob);
        
        myThread.start();
        System.out.println("test");
    }
}
