public class YieldDemo {

    // 一个共享计数器，两个线程都会去增加它
    static int countWithoutYield = 0;
    static int countWithYield = 0;

    public static void main(String[] args) throws InterruptedException {
        System.out.println("演示 Thread.yield() 方法");
        System.out.println("环境: CPU核心数-" + Runtime.getRuntime().availableProcessors());
        System.out.println("================================================");

        // 创建两个线程：一个不使用yield，一个使用yield
        Thread nonYieldThread = new Thread(new NonYieldTask(), "Non-Yield-Thread");
        Thread yieldThread = new Thread(new YieldTask(), "Yield-Thread");

        // 启动线程
        nonYieldThread.start();
        yieldThread.start();

        // 主线程等待两个工作线程完成
        yieldThread.join();
        //nonYieldThread.join();

        // 打印最终结果
        System.out.println("================================================");
        System.out.println("最终结果:");
        System.out.println("Non-Yield-Thread 循环次数: " + countWithoutYield);
        System.out.println("Yield-Thread    循环次数: " + countWithYield);
    }

    // 不使用yield的任务
    static class NonYieldTask implements Runnable {
        @Override
        public void run() {
            long startTime = System.currentTimeMillis();
            for (int i = 0; i < 10_000_000; i++) { // 1000万次循环
                countWithoutYield++;
                // 这里只是执行一些计算，不调用yield
            }
            long endTime = System.currentTimeMillis();
            System.out.println(Thread.currentThread().getName() + " 完成! 耗时: " + (endTime - startTime) + "ms");
        }
    }

    // 使用yield的任务
    static class YieldTask implements Runnable {
        @Override
        public void run() {
            long startTime = System.currentTimeMillis();
            for (int i = 0; i < 10_000_000; i++) { // 同样1000万次循环
                countWithYield++;
                Thread.yield(); // 每次循环都让出CPU
            }
            long endTime = System.currentTimeMillis();
            System.out.println(Thread.currentThread().getName() + " 完成! 耗时: " + (endTime - startTime) + "ms");

        }
    }
}