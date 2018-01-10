package com.andlp.lib.queue;

/**
 * 717219917@qq.com      2018/1/10  13:39
 */

public interface Queue {
      void clear();//清空队列
      Object deQueue();//出队列
      boolean isEmpty();//判断是否为空
      Object peek();//取对头元素
      void push(Object obj);// 入队列
      int size();//元素的个数
}
