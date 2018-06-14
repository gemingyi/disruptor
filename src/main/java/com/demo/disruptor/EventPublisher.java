package com.demo.disruptor;

import com.demo.disruptor.Event.TestEvent;
import com.demo.disruptor.EventTranslator.Producer;
import com.demo.disruptor.Model.Orange;
import com.lmax.disruptor.dsl.Disruptor;

import java.util.concurrent.CountDownLatch;

/**
 * Created by Administrator on 2018/4/15.
 */
public class EventPublisher implements Runnable {
    private Disruptor<TestEvent> disruptor;
    private CountDownLatch latch;

    public EventPublisher(CountDownLatch latch,Disruptor<TestEvent> disruptor) {
        this.disruptor = disruptor;
        this.latch = latch;
    }

    @Override
    public void run() {
        Producer producer = new Producer();
        for (int i = 0; i < 10; i++) {
            disruptor.publishEvent(producer, new Orange(i, "大橘子"));
        }
        //// 执行完毕后通知 await()方法
        latch.countDown();
    }
}
