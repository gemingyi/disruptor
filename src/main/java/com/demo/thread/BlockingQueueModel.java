package com.demo.thread;

import com.demo.disruptor.Model.Orange;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * 生产者
 */
class Production implements Runnable {

    private final BlockingQueue<Orange> blockingQueue;

    Production(BlockingQueue blockingQueue) {
        this.blockingQueue = blockingQueue;
    }

    @Override
    public void run() {
        Orange orange;
        try {
            for (int i = 0; i < 10; i++) {
                orange = new Orange(i, "橘子");
                System.out.println("生产了" + orange.toString());
                blockingQueue.put(orange);
                Thread.sleep(100);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

/**
 * 消费者
 */
class Consumer implements Runnable {

    private final BlockingQueue<Orange> blockingQueue;
    private String consumerName;

    Consumer(BlockingQueue blockingQueue, String consumerName) {
        this.blockingQueue = blockingQueue;
        this.consumerName = consumerName;
    }

    @Override
    public void run() {
        Orange orange;
        try {
            while (true){
                orange = blockingQueue.take();
                if(orange != null) {
                    System.out.println(consumerName + "消费了" + orange.toString());
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

/**
 * Created by Administrator on 2018/6/3.
 */
public class BlockingQueueModel {
    public static void main(String[] args) {
        BlockingQueue blockingQueue = new LinkedBlockingDeque<Orange>(1);
        Production p1 = new Production(blockingQueue);
        Consumer c1 = new Consumer(blockingQueue, "asd");
        Consumer c2 = new Consumer(blockingQueue, "dsa");
        new Thread(p1).start();
        new Thread(c1).start();
        new Thread(c2).start();
    }
}