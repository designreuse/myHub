package kr.co.myhub.app.test.thread;

import java.util.TimerTask;

public class TimerTest extends TimerTask {
    int count = 0;
    
    public TimerTest() {
        super();
    }

    public TimerTest(int count) {
        super();
        this.count = count;
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        System.out.println("count : " + this.count);
    }
    
    

}
