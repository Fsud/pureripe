package study.designPatterns.singleton;

/**
 *懒汉式。如果两个线程同时进入if判断，会使单例失败
 * Created by fankun on 2017/10/20.
 */
public class LazySingleton {
    private static LazySingleton lazySingleton;

    private LazySingleton(){}

    /**
     * 可以在这个方法上加入synchronised，变成线程安全的懒汉式
     * 这样可以保证线程安全，但是其实绝大多数情况并不需要同步，所以效率变得很低
     * @return
     */
    private static LazySingleton getInstance(){
        if(lazySingleton == null){
            lazySingleton = new LazySingleton();
        }
        return lazySingleton;
    }
}
