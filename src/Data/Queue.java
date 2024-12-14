package Data;


import java.util.LinkedList;

public class Queue {
    private final LinkedList<Customer> que;

    public Queue() {
        que = new LinkedList<>();
    }

    public synchronized void enqueue(Customer data)//0은 정상적으로 인큐, 1은 대기인원 가득참
    {
        que.addLast(data);
    }

    public void clear() {
        que.clear();
    }

    public synchronized Customer dequeue() {
        if (!isEmpty()) {
            return que.removeFirst();
        } else {
            return null;
        }
    }

    public synchronized int getSize() {
        return que.size();
    }

    public synchronized boolean isEmpty() {
        return que.isEmpty();
    }
}
