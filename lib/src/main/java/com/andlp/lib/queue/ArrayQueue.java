package com.andlp.lib.queue;

/**
 * 717219917@qq.com      2018/1/10  13:38
 */

/**
 * 顺序循环队列
 * @author Administrator
 *
 */
public class ArrayQueue implements Queue {
    private static  int  DEFAULT_SIZE = 10;
    private Object array[] = null;
    private int front, rear, count; //队首，队尾标注和队列的大小

    public ArrayQueue() {
        array = new Object[DEFAULT_SIZE];
        front = rear = count = 0;
    }

    public boolean isEmpty() {
        if((rear == front) && (0 == count))
            return true;
        else
            return false;
    }

    public int size() {
        return count;
    }

    @Override public void push(Object obj) {
        if((rear == front) && (count>0))
            expand();
        array[rear] = obj;
        rear = (rear+1)%DEFAULT_SIZE;
        count ++;
    }

    @Override public Object deQueue() {
        if(0 == count) {
            throw new IllegalStateException("队列已空，无数据元素可出队列！");
        } else {
            Object obj = array[front];
            front = (front+1)%DEFAULT_SIZE;
            count --;
            return obj;
        }
    }


    @Override public Object peek() {
        if(0 == count) {
            throw new IllegalStateException("队列已空，无数据元素可出队列！");
        } else return array[front];
    }

    @Override
    public void clear() {
        for(int i=0; i<DEFAULT_SIZE; i++) {
            array[i] = null;
        }
        front = rear = count = 0;
    }

    private void expand() {
        Object newArray[] = new Object[2*DEFAULT_SIZE];
        for(int i=0; i<count; i++) {
            newArray[i] = array[(front+i)%DEFAULT_SIZE];
        }
        array = newArray;
        front = 0;
        rear = count;
        DEFAULT_SIZE = 2*DEFAULT_SIZE;
    }

    public String toString() {
        String str= "[";
        for(int i=0; i<count; i++) {
            str =str + array[(front+i)%DEFAULT_SIZE];
            str += ",  ";
        }
        str += "]";
        return str;
    }

}