package study.designPatterns.observer;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by fankun on 2018/1/19.
 */
public class User implements Observer {
    private String mName;

    public User(String name) {
        mName = name;
    }

    /**
     * 被观察者改变时，会调用此方法通知观察者
     * @param observable
     * @param data
     */
    @Override
    public void update(Observable observable, Object data) {
        NewsModel model = (NewsModel) data;
        System.out.println(mName + " receive news:" + model.getTitle() + "  " + model.getContent());
    }
}
