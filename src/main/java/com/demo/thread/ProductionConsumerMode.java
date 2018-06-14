package com.demo.thread;

import com.demo.disruptor.Model.Orange;

class Common {
    private Orange orange;
    //flag生产消费的标识 flag=true时 生产者生产，消费者等待 flag=false时 消费者生产，生产者等待
    private boolean flag = true;

    public synchronized void set(Orange orange) {
        try {
            if (this.flag == false) {
                this.wait();
            }
            this.orange = orange;
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            this.flag = false;
            this.notify();
        }
    }

    public synchronized Orange get() {
        try {
            if (this.flag == true) {
                this.wait();
            }
            Thread.sleep(100);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.flag = true;
            this.notify();
        }
        return orange;
    }

}

/**
 * 生产者
 */
class Production1 implements Runnable {
    private Common common;

    Production1(Common common) {
        this.common = common;
    }

    @Override
    public void run() {
        Orange orange;
        for (int i = 0; i < 10; i++) {
            orange = new Orange(i, "橘子");
            this.common.set(orange);
            System.out.println("生产了" + orange.toString());
        }
    }
}

/**
 * 消费者
 */
class Consumer1 implements Runnable {
    private Common common;

    Consumer1(Common common) {
        this.common = common;
    }

    @Override
    public void run() {
        Orange orange;
        while (true) {
            orange = common.get();
            if (orange != null) {
                System.out.println("消费了" + orange.toString());
            }
        }
    }
}

/**
 * Created by Administrator on 2018/6/3.
 */
public class ProductionConsumerMode {

    public static void main(String[] args) {
        Common common = new Common();
        Production1 p1 = new Production1(common);
        Consumer1 c1 = new Consumer1(common);
        new Thread(p1).start();
        new Thread(c1).start();
        long endDate = System.currentTimeMillis();
    }
}
