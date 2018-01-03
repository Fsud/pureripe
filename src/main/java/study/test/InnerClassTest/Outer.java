package study.test.InnerClassTest;

/**
 * Created by fankun on 2017/12/5.
 */
public class Outer {
    String outString = "I am out";
    public Inner getInner(){
        return new Inner();
    }
    class Inner{
        String innerString = "I am inner";
        public void printOut(){
            System.out.println(Outer.this.outString);
        }
    }

    public static void main(String[] args) {
        Outer outer = new Outer();
        Outer.Inner inner = outer.new Inner();
        Outer.Inner inner2 = outer.new Inner();
        System.out.println(inner == inner2);
        inner.printOut();
    }
}
