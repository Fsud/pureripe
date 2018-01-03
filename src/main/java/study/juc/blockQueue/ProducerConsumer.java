package study.juc.blockQueue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by fankun on 2017/10/23.
 */
public class ProducerConsumer {
    AtomicInteger seq = new AtomicInteger(0);

    public static void main(String[] args) {
        new ProducerConsumer().go();
    }

    public void go() {
        BlockingQueue<String> queue = new ArrayBlockingQueue<String>(5);
        Producer producer = new Producer(queue);
        Consumer consumer = new Consumer(queue);
        Thread t1 = new Thread(producer,"Ⅰ");
        Thread t2 = new Thread(producer,"Ⅱ");
        Thread t3 = new Thread(producer,"Ⅲ");
        t1.start();
        t2.start();
        t3.start();
        new Thread(consumer).start();
    }

    private class Producer implements Runnable{
        private BlockingQueue<String> queue;

        public Producer(BlockingQueue<String> queue){this.queue = queue;}

        @Override
        public void run() {
            while(true){
                try {
                    String item = "产品"+seq.getAndIncrement();
                    queue.put(item);
                    System.out.println("Producer"+Thread.currentThread().getName()+"放入"+item);
                    TimeUnit.SECONDS.sleep((int)(Math.random()*10));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private class Consumer implements Runnable{

        private BlockingQueue<String> queue;

        public Consumer(BlockingQueue<String> queue){this.queue = queue;}

        @Override
        public void run() {
            while(true) {
                try {
                    String item = queue.take();
                    System.out.println("Consumer取出" + item);
                    TimeUnit.SECONDS.sleep((int)(Math.random()*10));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
