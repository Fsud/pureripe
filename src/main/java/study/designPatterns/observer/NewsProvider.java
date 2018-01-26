package study.designPatterns.observer;

import java.util.Observable;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 观察者模式定义了一种一对多的依赖关系。一个对象持有多个其他对象。并且是使用接口持有。多个对象需要获取这一个对象的变化。即观察它。
 * 实现方式是，在“被观察”对象发生变化时，依次调用观察者的接口。
 * Created by fankun on 2018/1/19.
 */
public class NewsProvider extends Observable {
    private static final long DELAY = 2 * 1000;

    public NewsProvider() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            private int titleCount = 1;
            private int contentCount = 1;
            @Override
            public void run() {
                setChanged(); //调用setChagned方法，将changed域设置为true，这样才能通知到观察者们
                notifyObservers(new NewsModel("title:" + titleCount++, "content:" + contentCount++));
            }
        }, DELAY, 1000);
    }
}
