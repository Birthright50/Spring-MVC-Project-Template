package com.birthright.infrastructure.root.async;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.*;

import static java.util.concurrent.Executors.callable;

/**
 * Created by birthright on 24.03.17.
 */
public final class CompletableExecutors {

    private CompletableExecutors() throws Throwable {
        throw new Throwable();
    }

    public static CompletableExecutorService decorateToCompletableExecutor(ExecutorService delegate) {
        return new DelegatingCompletableExecutorService(delegate);
    }

   static class DelegatingCompletableExecutorService extends DelegatingExecutorService implements CompletableExecutorService {

        DelegatingCompletableExecutorService(ExecutorService executorService) {
            super(executorService);
        }

        @Override
        public <T> CompletableFuture<T> submit(Callable<T> task) {
            final CompletableFuture<T> cf = new CompletableFuture<>();
            delegate.submit(() -> {
                try {
                    cf.complete(task.call());
                } catch (CancellationException e) {
                    cf.cancel(true);
                } catch (Exception e) {
                    cf.completeExceptionally(e);
                }
            });
            return cf;
        }

        @Override
        public <T> CompletableFuture<T> submit(Runnable task, T result) {
            return submit(callable(task, result));
        }

        @Override
        public CompletableFuture<?> submit(Runnable task) {
            return submit(callable(task));
        }
    }

    /**
     * Executor service that delegates everything to another one
     * see {@link Executors.DelegatedExecutorService}
     */
  private static class DelegatingExecutorService extends AbstractExecutorService{
        protected final ExecutorService delegate;
        DelegatingExecutorService(ExecutorService executor) { delegate = executor; }
        public void execute(Runnable command) { delegate.execute(command); }
        public void shutdown() { delegate.shutdown(); }
        public List<Runnable> shutdownNow() { return delegate.shutdownNow(); }
        public boolean isShutdown() { return delegate.isShutdown(); }
        public boolean isTerminated() { return delegate.isTerminated(); }
        public boolean awaitTermination(long timeout, TimeUnit unit)
                throws InterruptedException {
            return delegate.awaitTermination(timeout, unit);
        }
        public Future<?> submit(Runnable task) {
            return delegate.submit(task);
        }
        public <T> Future<T> submit(Callable<T> task) {
            return delegate.submit(task);
        }
        public <T> Future<T> submit(Runnable task, T result) {
            return delegate.submit(task, result);
        }
        public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks)
                throws InterruptedException {
            return delegate.invokeAll(tasks);
        }
        public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks,
                                             long timeout, TimeUnit unit)
                throws InterruptedException {
            return delegate.invokeAll(tasks, timeout, unit);
        }
        public <T> T invokeAny(Collection<? extends Callable<T>> tasks)
                throws InterruptedException, ExecutionException {
            return delegate.invokeAny(tasks);
        }
        public <T> T invokeAny(Collection<? extends Callable<T>> tasks,
                               long timeout, TimeUnit unit)
                throws InterruptedException, ExecutionException, TimeoutException {
            return delegate.invokeAny(tasks, timeout, unit);
        }
    }
}