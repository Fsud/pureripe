package study.designPatterns.decorator;

/**
 * 装饰器模式，可以动态扩展类的行为，而不使用继承。符合开闭原则。
 * Created by fankun on 2017/10/30.
 */
public class DecoratorMain {
    public static void main(String[] args) {
        Shape circle = new Circle();

        Shape readCircle = new RedShapeDecoder(circle);

        Shape rectangle = new RedShapeDecoder(new Rectangle());

        circle.draw();
        readCircle.draw();
        rectangle.draw();
    }
}
