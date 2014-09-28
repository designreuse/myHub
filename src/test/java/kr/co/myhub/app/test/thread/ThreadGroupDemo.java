package kr.co.myhub.app.test.thread;

public class ThreadGroupDemo {

    public static void main(String[] args) throws InterruptedException {
        
        // 그룹 쓰레드 생성
        ThreadGroup tg1 = new ThreadGroup("smt");
        
        // 쓰레드 생성1
        Thread thread1 = new Thread(tg1, new Runnable() {
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
        
        thread1.setDaemon(true);
        thread1.setName("t1");
        thread1.start();
        
        // 쓰레드 생성1
        Thread thread2 = new Thread(tg1, new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(1000 * 10);
                        break;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        
        thread2.setDaemon(true);
        thread2.setName("t1");
        thread2.start();
        
        Thread.currentThread();
        System.out.println("총 쓰레드: " + Thread.activeCount());
        
        // 생성된 쓰레드 그룹 카운트
        System.out.println("그룹 쓰레드 카운트 : " + tg1.activeCount());
        
        // 생성된 쓰레드 그룹 카운트
        Thread.sleep(1000 * 6);
        System.out.println("그룹 쓰레드 카운트(6초후): " + tg1.activeCount());
        
        // 메인 쓰레드
        ThreadGroup main = Thread.currentThread().getThreadGroup();
        System.out.println("쓰레드 그룹 갯수: " + main.activeGroupCount());
        main.list();
        
        // 생성된 쓰레드 그룹 카운트
        Thread.sleep(1000 * 11);
        System.out.println("그룹 쓰레드 카운트(11초후) : " + tg1.activeCount());
    }

}
