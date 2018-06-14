package com.demo.disruptor;

import com.demo.disruptor.Event.TestEvent;
import com.demo.disruptor.EventHandler.Consumer;
import com.demo.disruptor.EventHandler.Consumer1;
import com.demo.disruptor.EventHandler.Consumer2;
import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.SleepingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.EventHandlerGroup;
import com.lmax.disruptor.dsl.ProducerType;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Administrator on 2018/4/15.
 */
public class TestMain {
    public static void main(String[] args) {

        final EventFactory<TestEvent> EVENT_FACTORY = new EventFactory<TestEvent>() {
            @Override
            public TestEvent newInstance() {
                return new TestEvent();
            }
        };

        final int BUFFER_SIZE = 1;
        ExecutorService executor = Executors.newFixedThreadPool(4);

        Disruptor<TestEvent> disruptor = new Disruptor<TestEvent>(EVENT_FACTORY, BUFFER_SIZE, executor, ProducerType.SINGLE, new SleepingWaitStrategy());

        //消费者一，这里可以并行多个
        EventHandlerGroup<TestEvent> handlerGroup = disruptor.handleEventsWith(new Consumer(), new Consumer1());
        //窜行
        handlerGroup.then(new Consumer2());

        //初始化队列
        disruptor.start();
        CountDownLatch latch = new CountDownLatch(1);
        //添加数据
        executor.submit(new EventPublisher(latch, disruptor));
        try {
            latch.await();//等待生产完毕.
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        disruptor.shutdown();
        executor.shutdown();

    }
}
