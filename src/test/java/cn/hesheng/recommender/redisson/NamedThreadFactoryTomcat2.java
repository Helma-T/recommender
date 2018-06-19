package cn.hesheng.recommender.redisson;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class NamedThreadFactoryTomcat2 implements ThreadFactory {
    private static AtomicInteger tag = new AtomicInteger(0);

    @Override
    public Thread newThread(Runnable r) {
        Thread thread = new Thread(r);
        thread.setName("app2："+ tag.getAndIncrement());
        return thread;
    }
}
