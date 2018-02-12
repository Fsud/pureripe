package study.juc;

/**
 * Created by fankun on 2018/1/29.
 */
public class TT{
    public synchronized void print(String name) throws Exception{
            System.out.println(name);
            Thread.sleep(5000);
            for (int i = 0; i < 10; i++) {
                System.out.println(i);
            }
    }

    public synchronized void print2(String name) throws Exception{
        System.out.println(name);
        Thread.sleep(5000);
        for (int i = 0; i < 10; i++) {
            System.out.println(i);
        }

    }
}
