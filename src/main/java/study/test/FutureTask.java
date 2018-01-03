package study.test;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListenableFutureTask;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;

/**
 * Created by fankun on 2017/5/23.
 */
public class FutureTask {
    public static void main(String[] args) {
        try{
            ExecutorService executor = Executors.newFixedThreadPool(3);
            //包装器包装
            ListeningExecutorService listeningExecutor = MoreExecutors.listeningDecorator(executor);

            ListenableFuture<String> future1 = listeningExecutor.submit(new Callable<String>() {
                public String call() throws Exception {
                    Thread.sleep(1000);
                    System.out.println("aaa");
                    return "this is result";
                }
            });

            ListenableFutureTask<String> futureTask = ListenableFutureTask.create(new Callable<String>() {
                public String call() throws Exception {
                    Thread.sleep(1000);
                    System.out.println("aaa");
                    return "this is result";
                }
            });

            //为Future添加回调函数
            Futures.addCallback(future1, new FutureCallback<String>() {
                @Override
                public void onSuccess(String s) {
                    System.out.println("callback hahaha");
                }

                @Override
                public void onFailure(Throwable throwable) {
                    System.out.println("callback ...");
                }
            });

            Thread.sleep(10);
            System.out.println("start --- ");
            System.out.println("future print:" + future1.get());

//            System.out.println(Runtime.getRuntime().availableProcessors());

//            Future<String> future2 = executor.submit(new Callable<String>() {
//                @Override
//                public String call() throws Exception {
//                    try {
//                        while (true) {
//                            System.out.println("task2 running.");
//                            Thread.sleep(50);
//                        }
//                    } catch (InterruptedException e) {
//                        System.out.println("Interrupted task2.");
//                    }
//                    return "task2=false";
//                }
//            });
//
//
//            // 等待5秒后，再停止第二个任务。因为第二个任务进行的是无限循环
//            Thread.sleep(49);
//            System.out.println("task2 cancel: " + future2.cancel(true));
        }catch (Exception e){

        }
    }
}
