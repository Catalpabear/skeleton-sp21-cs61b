import java.util.concurrent.Executor;

public class Daemon {
    public static void main(String[] args) throws InterruptedException {
        // 创建一个“工人”线程（用户线程）
        Thread workerThread = new Thread(new Task("【用户线程-工人】"), "Worker-Thread");

        // 创建一个“仆人”线程（守护线程）
        Thread servantThread = new Thread(new Task("【守护线程-仆人】"), "Servant-Thread");
        servantThread.setDaemon(true); // 关键！设置为守护线程

        System.out.println("主人（主线程）说：开始工作！");
        workerThread.start();
        servantThread.start();

        // 主线程（主人）等待3秒后“离开”（结束）
        Thread.sleep(3000);
        System.out.println("\n主人（主线程）说：我下班了，回家了！");
        System.out.println("所有非守护线程（主线程和工人线程）都结束了...");
        // 此时，JVM会检查是否还有非守护线程存活。如果没有，就终止所有守护线程并退出。
    }
    // 一个模拟长时间工作的任务
    static class Task implements Runnable {
        private String name;

        public Task(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            for (int i = 1; i <= 10; i++) {
                try {
                    Thread.sleep(1000); // 模拟每秒做一件事
                    System.out.println(name + " 完成了第 " + i + " 项工作");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            // 只有用户线程能执行到这行，守护线程会在中途被强制终止
            System.out.println(name + " 报告：所有工作已完成！");
        }
    }
}
