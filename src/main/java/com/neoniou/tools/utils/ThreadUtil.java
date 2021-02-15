package com.neoniou.tools.utils;

import java.util.concurrent.*;

/**
 * @author Neo.Zzj
 * @date 2020/12/22
 */
public class ThreadUtil {

    private static ExecutorService threadPool;

    public static void createDefaultThreadPool() {
        createThreadPool();
    }

    private static void createThreadPool() {
        threadPool = new ThreadPoolExecutor(6,
                6,
                1L,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(3),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.CallerRunsPolicy());
    }

    public static ExecutorService getThreadPool() {
        return threadPool;
    }

    public static void execute(Runnable command) {
        threadPool.execute(command);
    }

    public static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
