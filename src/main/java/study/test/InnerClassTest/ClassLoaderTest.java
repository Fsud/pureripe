package study.test.InnerClassTest;

/**
 * Created by fankun on 2017/12/12.
 */
public class ClassLoaderTest {
    static {
        System.out.println("ClassLoaderTest come");
    }

    public static void main(String[] args) throws ClassNotFoundException {
        Class c = ClassLoaderTest.class;
        Class c1 = Class.forName("study.test.InnerClassTest.ClassLoaderTest");
    }
}

