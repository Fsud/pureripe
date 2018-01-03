package study.designPatterns.singleton;

/**
 * 静态内部类式
 * 同样保证了线程安全。比饿汉好的地方是，延迟加载INSTANCE。
 * Created by fankun on 2017/10/20.
 */
public class StaticInnerClassSingleton {
    private static class SingletonHolder{
        private static StaticInnerClassSingleton INSTANCE = new StaticInnerClassSingleton();
    }

    private StaticInnerClassSingleton(){}

    public static StaticInnerClassSingleton getInstance(){
        return SingletonHolder.INSTANCE;
    }
}
