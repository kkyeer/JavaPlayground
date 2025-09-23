package lab.jdk;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @Author: kkyeer
 * @Description: 研究并发执行时的异常
 * @Date:Created in 13:31 2025/8/25
 * @Modified By:
 */

public class JucException {
    static Logger logger = LoggerFactory.getLogger(JucException.class);

    private static class CustomException extends Exception {
        public CustomException(String message) {
            super("这是一个自定义异常");
        }
    }

    private static class CustomError extends Error{
        public CustomError(String message) {
            super("这是一个自定义错误");
        }
    }

    private static class ThrowNormalExRunnable implements Runnable {
        @Override
        public void run() {
            try {
                throw new CustomException("normal exception");
            } catch (CustomException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static class ThrowErrorRunnable implements Runnable {
        @Override
        public void run() {
            throw new CustomError("normal exception");
        }
    }

    private static class InterruptRunnable implements Runnable {
        @Override
        public void run() {
            Thread.currentThread().interrupt();
            logger.info("after interrupted");
        }
    }

    public static void main(String[] args) {
//        normalException();
//        throwError();
        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        Future<?> submit = executorService.submit(new InterruptRunnable());
        try {
            submit.get();
        } catch (InterruptedException e) {
            logger.error("捕获到interrupted exception", e);
        } catch (ExecutionException e) {
            if (e.getCause() instanceof RuntimeException) {
                RuntimeException inner = (RuntimeException) e.getCause();
                logger.error("捕获到runtime exception,inner", inner.getCause());
            }else {
                logger.error("捕获到execution exception,inner", e.getCause());
            }
        } catch (Throwable e) {
            logger.error("捕获到throwable", e);
        }
    }

    private static void throwError() {
        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        Future<?> submit = executorService.submit(new ThrowErrorRunnable());
        try {
            submit.get();
        } catch (InterruptedException e) {
            logger.error("捕获到interrupted exception", e);
        } catch (ExecutionException e) {
            if (e.getCause() instanceof RuntimeException) {
                RuntimeException inner = (RuntimeException) e.getCause();
                logger.error("捕获到runtime exception,inner", inner.getCause());
            }else {
                logger.error("捕获到execution exception,inner", e.getCause());
            }
        }
    }

    private static void normalException() {
        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        Future<?> submit = executorService.submit(new ThrowNormalExRunnable());
        try {
            submit.get();
        } catch (InterruptedException e) {
            logger.error("捕获到interrupted exception", e);
        } catch (ExecutionException e) {
            if (e.getCause() instanceof RuntimeException) {
                RuntimeException inner = (RuntimeException) e.getCause();
                logger.error("捕获到runtime exception,inner", inner.getCause());
            }else {
                logger.error("捕获到execution exception,inner", e.getCause());
            }
        }
    }


}
