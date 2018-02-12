package study.juc.threadCooperate;

/**
 * http://www.cnblogs.com/dolphin0520/p/3920385.html
 * 1、wait-notify实现了线程协作的功能（相比于synchronize，实现的是线程同步）
 * 2、wait-notify必须在synchronize中使用，是因为wait-notify必须要事先获得对象monitor。synchronized就是帮助获取monitor的。
 * 同时需要注意，notify之后不会立即唤醒所有wait的线程，必须退出synchronized代码块才可以。
 * Created by fankun on 2018/2/12.
 */
public class WaitNotifyTest {
    public static Object object = new Object();
    public static void main(String[] args) {
        Thread1 thread1 = new Thread1();
        Thread2 thread2 = new Thread2();

        thread1.start();

        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        thread2.start();
    }

    static class Thread1 extends Thread{
        @Override
        public void run() {
            synchronized (object) {
                try {
                    object.wait();
                } catch (InterruptedException e) {
                }
                System.out.println("线程"+Thread.currentThread().getName()+"获取到了锁");
            }
        }
    }

    static class Thread2 extends Thread{
        @Override
        public void run() {
            synchronized (object) {
                object.notify();
                System.out.println("线程"+Thread.currentThread().getName()+"调用了object.notify()");
            }
            System.out.println("线程"+Thread.currentThread().getName()+"释放了锁");
        }
    }
}
