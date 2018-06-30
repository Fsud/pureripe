package study.designPatterns.proxy;

import study.designPatterns.proxy.cglib.TimeCglib;
import study.designPatterns.proxy.jdkproxy.BTimeView;
import study.designPatterns.proxy.jdkproxy.EiEi;
import study.designPatterns.proxy.jdkproxy.TimeView;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

/**
 * Created by fankun on 2018/1/17.
 */
public class Test {
    void testJDK(){
        InvocationHandler invocationHandler = new BTimeView(()-> System.out.println(System.currentTimeMillis()));
        TimeView timeView = (TimeView)Proxy.newProxyInstance(getClass().getClassLoader(),new Class[]{TimeView.class, EiEi.class},invocationHandler);
        timeView.printTime();
    }

    void testCGLIB(){
        MethodInterceptor methodInterceptor =(Object obj, Method method, Object[] args, MethodProxy proxy)->{
            if(method.getName().equals("printTime")){

                System.out.println("___B___B___B___");
            }
            return proxy.invokeSuper(obj,args);
        };

        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(TimeCglib.class);
        enhancer.setCallback(methodInterceptor);

        /**
         * 被代理对象的所有非final方法调用都会进入intercept方法
         */
        TimeCglib timeCglib = (TimeCglib)enhancer.create();
        timeCglib.printTime();
    }

    public static void main(String[] args) {
        new Test().testJDK();
    }
}
