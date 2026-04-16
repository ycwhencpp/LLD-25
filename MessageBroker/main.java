package MessageBroker;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

public class main {
    
}

class publisher {
    MessageBroker broker;
    public void publish(String message, String topic){
        broker.publish(message, topic);
    }


}

class MessageBroker{
    ConcurrentHashMap<String, Topic> topicMap;

    public void publish(String message, String topic){
        Topic Topic = topicMap.get(topic);
        Topic.queue.offer(message);
    }

}

class Topic {
    ConcurrentHashMap<Integer, Subscriber> subscribers;
    BlockingQueue<String> queue = new LinkedBlockingQueue<>();

    public Topic(){
        start();
    }

    public void start() {
        
        Thread worker = new Thread(()->{
            try {
                while (true) { 
                    String msg = queue.take();
                    for(Subscriber sub : subscribers.values()){
                        sub.update(msg);
                    }
                }
            } catch (Exception e) {
            }
        });
        worker.start();
    }
}

interface Subscriber{
     public void update(String msg);
}

class EmailSubscriber implements  Subscriber{
    public void update(String msg){
        System.out.println(msg +"recived");
    }
}