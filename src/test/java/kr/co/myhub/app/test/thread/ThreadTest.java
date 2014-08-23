package kr.co.myhub.app.test.thread;

import java.util.Timer;

public class ThreadTest {

    public static void main(String[] args) {
ThreadGroup main = Thread.currentThread().getThreadGroup();
        
        ThreadGroup tg1 = new ThreadGroup("smt");
        
        Thread t1 = new Thread(tg1, "1");
        Thread t2 = new Thread(tg1, "2");
        Thread t3 = new Thread(tg1, "3");
        
        t1.start();
        t2.start();
        t3.start();
        
        
        System.out.println("activeGroupCount : " + main.activeGroupCount());
        System.out.println("activeCount : " + main.activeCount());
        System.out.println("getName : " + main.getName());
        System.out.println("getMaxPriority : " + main.getMaxPriority());
        System.out.println("getParent : " + main.getParent());
        
        System.out.println("activeCount : " + tg1.activeCount());
        System.out.println("activeGroupCount : " + tg1.activeGroupCount());
        
        TimerTest timerTest = new TimerTest(5);
        
        Timer timer = new Timer(true);
        
        // 태스크 지연시간
        long delay = 0;
        // 폴링시간
        long period = 1000 * 2;    // 10초
        
        // 스케쥴 설정
        timer.schedule(timerTest, delay, period);
        

    }

}
