package com.demo.disruptor.EventHandler;

import com.demo.disruptor.Event.TestEvent;
import com.demo.disruptor.Model.Orange;
import com.lmax.disruptor.EventHandler;

/**
 * 消费者
 * Created by Administrator on 2018/6/3.
 */
public class Consumer2 implements EventHandler<TestEvent> {
    @Override
    public void onEvent(TestEvent orangeEvent, long l, boolean b) throws Exception {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Orange orange = orangeEvent.get();
        System.out.println("清理了" + orange.toString() + "的橘子皮");
    }
}
