package org.shoestore.product.lock;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RedisDistributedLockTest {
    private static final long LEFT_COUNT = 3L;

    @Autowired
    private DistributedLock  distributedLock;

    private final HashMap<Long, Long> mem = new HashMap<>();

    @BeforeEach
    void init(){
        mem.put(1L, LEFT_COUNT);
    }

    @Test
    void Lock획득_test() throws InterruptedException {
        int numberOfThreads = 10;
        ExecutorService executorService = Executors.newFixedThreadPool(32); // 스레드 풀 생성
        CountDownLatch latch = new CountDownLatch(numberOfThreads); // 모든 스레드가 완료될 때까지 대기

        List<Integer> success = new ArrayList<>();
        List<Integer> failure = new ArrayList<>();

        // 모든 스레드가 동시에 재고 감소를 요청하도록 스레드 풀에 작업 제출
        for (int i = 0; i < numberOfThreads; i++) {
            int finalI = i;
            executorService.submit(() -> {
                try {
                    // saleService 에서 주문 생성 시 상품별 lock 획득
                    String lockName = distributedLock.genLockName(1L);
                    distributedLock.getLocks(List.of(lockName));
                    Long value = mem.get(1L);
                    if (value == 0) {
                        failure.add(finalI);
                    } else {
                        mem.put(1L, value-1);
                        success.add(finalI);
                    }
                    distributedLock.releaseLocks(List.of(lockName));
                    // 상품별 lock 해제
                } catch (Exception e) {
                    // 락 획득 실패 또는 재고 부족 예외 등 처리
                    System.err.println("Thread failed to decrease stock: " + e.getMessage());
                } finally {
                    latch.countDown(); // 작업 완료 시 카운트 다운
                }
            });
        }
        // 모든 스레드가 작업을 완료할 때까지 메인 스레드 대기
        // 최대 60초까지 기다리도록 설정. 이 시간 안에 모든 작업이 끝나지 않으면 테스트 실패로 간주.
        boolean allThreadsFinished = latch.await(60, TimeUnit.SECONDS);
        assertThat(allThreadsFinished).isTrue(); // 모든 스레드가 시간 내에 완료되었는지 확인

        executorService.shutdown(); // 스레드 풀 종료
        executorService.awaitTermination(10, TimeUnit.SECONDS); // 스레드 풀이 완전히 종료될 때까지 대기

        assertThat(success.size()).isEqualTo(LEFT_COUNT);
        assertThat(failure.size()).isEqualTo(numberOfThreads-LEFT_COUNT);
    }
}