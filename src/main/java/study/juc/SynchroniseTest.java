package study.juc;

/**
 * Created by fankun on 2018/1/29.
 */
public class SynchroniseTest {

    public static void main(String[] args) throws Exception{
        TT t1 = new TT();
        Thread th1 = new Thread(()->{try{
            t1.print("t1");
        }catch (Exception e){
            System.out.println(111);
        }});
        Thread th2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    t1.print2("t2");
                }catch (Exception e){
                    System.out.println(111);
                }
            }
        });
        th1.start();
        th2.start();
    }
}
