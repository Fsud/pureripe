package study.test;

import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by fankun on 2017/10/23.
 */
public class CasTest {
        private static final AtomicReference<CasTest> INSTANCE = new AtomicReference<CasTest>();

        private CasTest() {}

        public static CasTest getInstance() {
            for (;;) {
                CasTest singleton = INSTANCE.get();
                if (null != singleton) {
                    return singleton;
                }

                singleton = new CasTest();
                if (INSTANCE.compareAndSet(null, singleton)) {
                    return singleton;
                }
            }
        }

    public static void main(String[] args) throws Exception{

        System.out.println(CasTest.class.getResource("/").getFile());
    }
}
