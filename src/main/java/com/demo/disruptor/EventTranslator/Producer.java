package com.demo.disruptor.EventTranslator;

import com.demo.disruptor.Event.TestEvent;
import com.demo.disruptor.Model.Orange;
import com.lmax.disruptor.EventTranslatorOneArg;

/**
 * 生产者
 * 实现 EventTranslator 或 EventTranslatorOneArg
 * Created by Administrator on 2018/4/15.
 */
public class Producer implements EventTranslatorOneArg<TestEvent, Orange> {

    //implements EventTranslator<TradeTransaction>{

    @Override
    public void translateTo(TestEvent orangeEvent, long l, Orange orange) {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("生产了" + orange.toString());
        orangeEvent.set(orange);
    }
}
