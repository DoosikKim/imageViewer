package github.com.doosikkim.imageviewer.dataloader;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import github.com.doosikkim.imageviewer.Logger;

/**
 * Created by doosik_kim on 2018. 4. 12..
 */
public class ImageLoadExecutor {

    private static final String TAG = ImageLoadExecutor.class.getSimpleName();
    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
    private static final int CORE_POOL_SIZE = Math.max(2, Math.min(CPU_COUNT - 1, 8));
    private static final int MAXIMUM_POOL_SIZE = CPU_COUNT * 2 + 1;
    private static final int KEEP_ALIVE_SECONDS = 5;

    private static final ThreadFactory sThreadFactory = new ThreadFactory() {
        private final AtomicInteger mCount = new AtomicInteger(1);

        public Thread newThread(Runnable r) {
            int id = mCount.getAndIncrement();
            Logger.d("newThread _id:"+id);
            return new Thread(r, "HandyThreadTask #" + id);
        }
    };

    private static final BlockingQueue<Runnable> sPoolWorkQueue =
            new LinkedBlockingQueue<>();

    /**
     * An {@link Executor} that can be used to execute tasks in parallel.
     */
    public static final ExecutorService THREAD_POOL_EXECUTOR;

    static {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, KEEP_ALIVE_SECONDS, TimeUnit.SECONDS,
                sPoolWorkQueue, sThreadFactory);
        threadPoolExecutor.allowCoreThreadTimeOut(true);
        Logger.d("threadPoolExecutor - CPU_COUNT:"+CPU_COUNT+", CORE_POOL_SIZE:"+CORE_POOL_SIZE+", MAXIMUM_POOL_SIZE:"+MAXIMUM_POOL_SIZE);
        THREAD_POOL_EXECUTOR = threadPoolExecutor;
    }

    public static void execute(Runnable runnable) {
        Logger.d("execute runnable:"+runnable);
        THREAD_POOL_EXECUTOR.execute(runnable);
    }


}
