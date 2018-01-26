package study.designPatterns.observer;

/**
 * Created by fankun on 2018/1/19.
 */
/**
 * 测试类
 */
public class Test {
    public static void main(String[] args) {

        NewsProvider provider = new NewsProvider();
        User user;
        for (int i = 0; i < 10; i++) {
            user = new User("user:"+i);
            provider.addObserver(user);
        }

    }
}
