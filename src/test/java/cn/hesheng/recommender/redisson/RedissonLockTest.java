package cn.hesheng.recommender.redisson;

import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.api.RBucket;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Slf4j
public class RedissonLockTest {

    @Autowired
    private RedissonClient redisson;

    private AccessTokenMock accessTokenMock;

    @Before
    public void beforeClass(){
        //对应到特定公众号的lock
        RLock lock = redisson.getLock("accessToken+gb2332ssdff");
        accessTokenMock = new AccessTokenMock(lock);
    }


    /**
     * 模拟多个tomcat同时获取accessToken 用redisson lock的单元测试
     * @throws InterruptedException
     */
    @Test
    public void mockMultiThreadGetAccessToken() throws InterruptedException {
        ExecutorService executorServiceTomcat1 = Executors.newFixedThreadPool(50,new NamedThreadFactoryTomcat1());
        ExecutorService executorServiceTomcat2 = Executors.newFixedThreadPool(50,new NamedThreadFactoryTomcat2());

        for (int i = 0; i < 10; i++) {
            executorServiceTomcat1.submit(()->{
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                accessTokenMock.getAccessToken();
            });

            executorServiceTomcat2.submit(()->{
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                accessTokenMock.getAccessToken();
            });
        }

        TimeUnit.SECONDS.sleep(20);
    }

    @Test
    public void redissonDemo() {
        String user_id="1";
        String key=user_id+"_key";
        //获取锁
        RLock lock = redisson.getLock(key);
        lock.lock();
        //执行具体逻辑...

        RBucket<Object> bucket = redisson.getBucket("a");
        bucket.set("bb");

        lock.unlock();
    }

    /**
     * 公平锁
     */
    @Test
    public void testFairLock(){

        RLock fairLock = redisson.getFairLock("anyLock");
        try{
            // 最常见的使用方法
            fairLock.lock();

            // 支持过期解锁功能, 10秒钟以后自动解锁,无需调用unlock方法手动解锁
            fairLock.lock(10, TimeUnit.SECONDS);

            // 尝试加锁，最多等待100秒，上锁以后10秒自动解锁
            boolean res = fairLock.tryLock(100, 10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            fairLock.unlock();
        }

    }
}

