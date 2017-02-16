package com.nice.utils;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by biezhi on 2017/2/13.
 */
public class ThreadUtils {

    private static final ThreadPoolExecutor threadPoolExecutor = getThreadPoolExecutor(Runtime.getRuntime().availableProcessors() * 2, Integer.MAX_VALUE);

    public static ThreadPoolExecutor getThreadPoolExecutor(int threads, int queues) {
        return new ThreadPoolExecutor(threads, threads, 0, TimeUnit.MILLISECONDS,
                queues == 0 ? new SynchronousQueue<>()
                        : (queues < 0 ? new LinkedBlockingQueue<>()
                        : new LinkedBlockingQueue<>(queues)));
    }

    public static void submit(Runnable t){
        threadPoolExecutor.submit(t);
    }

}
