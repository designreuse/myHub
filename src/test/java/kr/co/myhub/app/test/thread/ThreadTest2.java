package kr.co.myhub.app.test.thread;

public class ThreadTest2 {

    public static void main(String args[]) {
        
        ThreadGroup tg1 = new ThreadGroup("smt");
        
        
        Thread thread = new Thread(tg1, new Runnable() {
            @Override
            public void run() {
                while (true) {
                    
                    try {
                        Thread.sleep(1000 * 5);
                        
                        break;
                    } catch (InterruptedException e) {
                    
                        e.printStackTrace();
                    }
                }
            }
        });
        
        thread.setDaemon(true);
        thread.setName("t1");
        thread.start();
        
        System.out.println("isAlive : " + thread.isAlive());
        System.out.println("isDaemon : " + thread.isDaemon());
        System.out.println("isInterrupted : " + thread.isInterrupted());
        
        Thread.currentThread();
        System.out.println("activeCount before : " + Thread.activeCount());
        
        try {
            Thread.sleep(1000 * 6);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        System.out.println("activeCount after: " + Thread.activeCount());
        
    }
}
