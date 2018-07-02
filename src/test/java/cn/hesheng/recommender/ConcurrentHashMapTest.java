package cn.hesheng.recommender;


import cn.hesheng.recommender.redisson.NamedThreadFactoryTomcat1;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 并发hashMap测试
 */
@Slf4j
public class ConcurrentHashMapTest {

    static Map<String,MockObject> map = new ConcurrentHashMap<String,MockObject>();

    public static void main(String[] args) {
        ExecutorService executorServiceTomcat1 = Executors.newFixedThreadPool(50,new NamedThreadFactoryTomcat1());

        map.put("key0",new MockObject(0,"key0"));
        map.put("key1",new MockObject(1,"key1"));

        final Random random = new Random();

        for (int i = 0; i < 100; i++) {
            executorServiceTomcat1.submit(()->{
                int index = random.nextInt(100);
                //产生一个0,或者1的索引标号
                index = index%2;

                String key = "key" + index;
                MockObject mockObject = map.get(key);
                log.info("expect key {} actual key {}",key,mockObject.getName());
                if(!key.equals(mockObject.getName())){
                    log.error("error");
                }
            });
        }
    }

    private static class MockObject {
        private int id;
        private String name;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public MockObject(int id, String name) {
            this.id = id;
            this.name = name;
        }

        @Override
        public String toString() {
            return "MockObject{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    '}';
        }
    }
}
