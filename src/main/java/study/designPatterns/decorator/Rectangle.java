package study.designPatterns.decorator;


/**
 * Created by fankun on 2017/10/30.
 */
public class Rectangle implements Shape {
    @Override
    public void draw() {
        System.out.println("Shape:Rectangle");
    }
}
