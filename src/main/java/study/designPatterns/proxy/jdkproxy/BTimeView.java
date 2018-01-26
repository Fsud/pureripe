package study.designPatterns.proxy.jdkproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;


/**
 * Created by fankun on 2018/1/17.
 */
public class BTimeView implements InvocationHandler {

    private TimeView timeView;

    public BTimeView(TimeView timeView){
        this.timeView = timeView;
    }

    /**
     * 目标接口的所有方法+hashCode，equals，toString都会转发给此方法。
     * @param proxy
     * @param method
     * @param args
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        if(method.getName().equals("printTime")){
            System.out.println("___B___B___B___");
        }
        return method.invoke(timeView,args);
    }


}
