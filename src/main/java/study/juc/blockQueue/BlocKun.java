package study.juc.blockQueue;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by fankun on 2017/10/24.
 */
public class BlocKun<T> {
    private Queue<T> queue;
    private AtomicInteger size;

    public BlocKun(Integer size){
        this.queue = new LinkedList<T>();
        this.size = new AtomicInteger(size);
    }

    public synchronized void put(T item){
        if(size.get()==queue.size()){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        queue.add(item);
        notifyAll();
    }

    public synchronized T take(){
        if(size.get()==0){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        T item = queue.remove();
        notifyAll();
        return item;
    }
}
