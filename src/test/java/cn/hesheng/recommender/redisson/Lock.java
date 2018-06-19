package cn.hesheng.recommender.redisson;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.api.RBucket;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.concurrent.TimeUnit;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class Lock {

    @Autowired
    private RedissonClient redisson;
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
     * @param redisson
     */
    @Test
    public void testFairLock(RedissonClient redisson){

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

