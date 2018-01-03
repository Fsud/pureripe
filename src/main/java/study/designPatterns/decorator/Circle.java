package study.designPatterns.decorator;

/**
 * 被装饰类
 * Created by fankun on 2017/10/30.
 */
public class Circle implements Shape {
    @Override
    public void draw() {
        System.out.println("Shape:Circle");
    }
}
