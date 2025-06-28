package org.shoestore.product.lock;

import java.util.List;

public class RedisDistributedLock implements DistributedLock {

    @Override
    public String genLockName(Long productId) {
        return "product:"+productId;
    }

    @Override
    public boolean getLocks(List<String> lockName) {
        return false;
    }

    @Override
    public void releaseLocks(List<String> lockName) {

    }
}
