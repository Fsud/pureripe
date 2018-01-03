package study.juc.threadPool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by fankun on 2017/10/24.
 */
public class ThreadPoolCompare {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        long begin = System.currentTimeMillis();
        for (int i = 0; i < 100; i++) {
            executorService.submit(()->System.out.print("123--"));
        }
    }
}
