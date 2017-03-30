package com.birthright.infrastructure.root.async;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.extern.log4j.Log4j2;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * Created by birthright on 24.03.17.
 */
@Configuration
@EnableAsync
@Log4j2
public class AsyncConfig implements AsyncConfigurer {

    @Override
    public Executor getAsyncExecutor() {
        ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("async-%d").build();
        return CompletableExecutors.decorateToCompletableExecutor(Executors.newFixedThreadPool(10, threadFactory));
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        CompletableFuture.allOf().join();
        return (ex, method, params) -> log.error("Uncaught async error", ex);
    }
}
