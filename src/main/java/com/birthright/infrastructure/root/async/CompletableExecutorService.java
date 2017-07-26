package com.birthright.infrastructure.root.async;

/**
 * Created by birthright on 24.03.17.
 */

import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

/**
 * DelegatingCompletableExecutorService {@code ExecutorService} to covariantly return {@code
 * CompletableFuture} in place of {@code Future}.
 */
public interface CompletableExecutorService extends ExecutorService {
    /**
     * @return a decorateToCompletableExecutor future representing pending completion of the task, never missing
     */
    @Override
    <T> CompletableFuture<T> submit(Callable<T> task);

    /**
     * @return a decorateToCompletableExecutor future representing pending completion of the task, never missing
     */
    @Override
    <T> CompletableFuture<T> submit(Runnable task, T result);

    /**
     * @return a decorateToCompletableExecutor future representing pending completion of the task, never missing
     */
    @Override
    CompletableFuture<?> submit(Runnable task);
}
