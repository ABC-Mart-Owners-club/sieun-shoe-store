package org.shoestore.product.lock;

import java.util.List;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RedisDistributedLock implements DistributedLock {
    private final static long WAIT_TIME = 3000;
    private final static long LEASE_TIME = 3000;

    private final RedissonClient redissonClient;

    public RedisDistributedLock(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    @Override
    public String genLockName(Long productId) {
        return "product:"+productId;
    }

    @Override
    public boolean getLocks(List<String> lockNames) {
        RLock[] rLocks = lockNames.stream().map(redissonClient::getLock).toArray(RLock[]::new);
        RLock multiLock = redissonClient.getMultiLock(rLocks);
        try {
            // 락 획득 시도 (waitTime 동안 대기, leaseTime 후 자동 해제)
            return multiLock.tryLock(WAIT_TIME, LEASE_TIME, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // 인터럽트 상태 복원
            return false;
        }
    }

    @Override
    public void releaseLocks(List<String> lockNames) {
        RLock[] rLocks = lockNames.stream().map(redissonClient::getLock).toArray(RLock[]::new);
        RLock multiLock = redissonClient.getMultiLock(rLocks);

        try {
            multiLock.unlock();
            log.info("Multi-Lock released successfully: {}", lockNames);
        } catch (IllegalMonitorStateException e) {
            log.warn("Attempted to unlock an unowned or already unlocked multi-lock component: {}", lockNames);
        }
    }
}
