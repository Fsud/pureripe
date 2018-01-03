package study.designPatterns.singleton;

import java.io.Serializable;

/**
 * 1.看似完美，但是java内存模型中，线程对实例的可见性问题，导致还是会有问题。
 * 做法：volatile修饰单例类
 *
 * 2.序列化会破坏单例
 * 做法：继承implements方法并实现readResolve方法
 * Created by fankun on 2017/10/20.
 */
public class DoubleCheckLazySingleton implements Serializable{
    //volatile
    private static  DoubleCheckLazySingleton lazySingleton;

    private DoubleCheckLazySingleton(){}

    /**
     * @return
     */
    private static DoubleCheckLazySingleton getInstance(){
        if(lazySingleton == null){
            synchronized (DoubleCheckLazySingleton.class) {
                if(lazySingleton == null){
                    lazySingleton = new DoubleCheckLazySingleton();
                }
            }
        }
        return lazySingleton;
    }

    private Object readResolve(){
        return lazySingleton;
    }
}
