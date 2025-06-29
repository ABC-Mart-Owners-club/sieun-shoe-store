package org.shoestore.product.lock;

import java.util.List;

public interface DistributedLock {
    String genLockName(Long productId);
    boolean getLocks(List<String> lockName);
    void releaseLocks(List<String> lockName);
}
