package study.designPatterns.singleton;

/**
 * 饿汉.
 * 缺点：1.启动时就加载，可能造成浪费。2.如果类被加载了两次，单例就会失效
 * Created by fankun on 2017/10/20.
 */
public class HungrySingleton {
    private static HungrySingleton singleton = new HungrySingleton();

    private HungrySingleton() {
    };

    public static HungrySingleton getInstance() {
        return singleton;
    }
}
