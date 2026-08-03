package CaseStudy;

import CaseStudy.main2.Message;
import CaseStudy.main2.PubSubService;
import CaseStudy.main2.Topic;
import java.util.HashMap;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class main2 {
    public static void main(String[] args) {
        
    }


    class Publisher {
        PubSubService pubSubService;
        public void publish(Message message, Topic topic){
            pubSubService.publish(message, topic);
        }
    }


    class Topic {
        int id;
        HashMap<Integer, Subscriber> map;
        BlockingQueue<Message> blockingQueue = new LinkedBlockingQueue<>(1000);

        public Topic(){
            start();
        }


        public void brodcast(Message msg){
            blockingQueue.offer(msg);
        }

        public void start(){
            Thread worker = new Thread(()->{
                try {
                    while (true) { 
                        Message msg = blockingQueue.take();
                        for(Subscriber sub : map.values()){
                            sub.consume(msg);
                        }
                    }
                }
                catch (Exception e) {
                    System.out.println(e);
                }
            });
            worker.start();
        }
    }

    
    interface Subscriber{
        public void consume(Message msg);
    }

    class EmailSubscriber implements Subscriber {
        public void consume(Message msg){
            System.out.println(msg.msg);
        }
    }

    class Message {
        String msg;
        
    }

    class PubSubService{

        HashMap<Integer, Topic> topicMap;

        public void publish(Message msg, Topic topic){
            topic.brodcast(msg);
        }
    }
}

