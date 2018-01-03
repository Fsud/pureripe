package study.designPatterns.singleton;

/**
 * 枚举实现单例，jvm会保证EnumTool的构造方法只会调用一次
 *
 * Created by fankun on 2017/10/20.
 */
public class EnumSingleton {
    enum EnumTool{
        INSTANCE;
        private EnumSingleton enumSingleton;
        private EnumTool(){
            enumSingleton = new EnumSingleton();
        }

        public EnumSingleton getInstance(){
            return enumSingleton;
        }
    }
    private EnumSingleton(){}

    public static EnumSingleton getInstance(){
        return EnumTool.INSTANCE.getInstance();
    }
}
