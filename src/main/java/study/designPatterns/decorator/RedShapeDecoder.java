package study.designPatterns.decorator;

/**
 * 具体装饰类
 * Created by fankun on 2017/10/30.
 */
public class RedShapeDecoder extends ShapeDecoder {

    public RedShapeDecoder(Shape decoratedShape) {
        super(decoratedShape);
    }

    @Override
    public void draw(){
        System.out.println("Color:red");
        decoratedShape.draw();
    }
}
