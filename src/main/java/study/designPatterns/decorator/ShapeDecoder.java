package study.designPatterns.decorator;

/**
 * 抽象装饰类，需要和被装饰类继承同一接口（Shape）
 * Created by fankun on 2017/10/30.
 */
public abstract class ShapeDecoder implements Shape{
    protected Shape decoratedShape;

    public ShapeDecoder(Shape decoratedShape){
        this.decoratedShape = decoratedShape;
    }

    public void draw(){
        decoratedShape.draw();
    }
}
