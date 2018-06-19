package cn.hesheng.recommender.redisson;


import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.Lock;

@Slf4j
public class AccessTokenMock {

    private int i = 0;

    private Lock lock;

    public AccessTokenMock(Lock lock) {
        this.lock = lock;
    }

    /**
     * 获取全局统一的token
     */
    public void getAccessToken(){
        try {
            lock.lock();
            log.info("getAccessToken {}",++i);
        } finally {
            lock.unlock();
        }
    }
}
