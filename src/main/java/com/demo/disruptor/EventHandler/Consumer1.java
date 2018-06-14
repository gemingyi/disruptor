package com.demo.disruptor.EventHandler;

import com.demo.disruptor.Event.TestEvent;
import com.demo.disruptor.Model.Orange;
import com.lmax.disruptor.EventHandler;

/**
 * 消费者
 * Created by Administrator on 2018/4/15.
 */
public class Consumer1 implements EventHandler<TestEvent> {
    @Override
    public void onEvent(TestEvent orangeEvent, long l, boolean b)  {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Orange orange = orangeEvent.get();
        if(orange != null) {
            System.out.println("2消费者消费了" + orange.toString());
        }
    }
}
