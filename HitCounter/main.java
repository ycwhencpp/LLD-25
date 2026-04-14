package HitCounter;
import java.util.concurrent.atomic.AtomicLongArray;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class main {
    public static void main(String[] args) {
        HitCounter2 hc = new HitCounter2(300);
        hc.hit(2);
        hc.hit(5);
        System.err.println(hc.getHits(100));
        hc.hit(310);
        hc.hit(310);
        hc.hit(310);
        System.err.println(hc.getHits(340));
    }
}

class HitCounter{
    AtomicLongArray bucket ;
    int bucketLen;

    public HitCounter(int n){
        bucketLen = n;
        bucket = new AtomicLongArray(n);
    }

    public void hit(int timestamp){
        int index = timestamp % bucketLen;

       while (true) { 
            long data = bucket.get(index);

            int time =  (int) (data >> 32);
            int count = (int)(data);

            long updatedData;
            if(time != timestamp){
                updatedData = ((long)timestamp << 32) | (1L);
            } else {
                updatedData = ((long) timestamp <<32) | (count+1L);
            }
           
            if(bucket.compareAndSet(index,data, updatedData)){
                break;
            }
       }
    }

    public int getHits(int timestamp){
        int totalHit = 0;
        for(int i=0; i<bucketLen; i++){
            long data = bucket.get(i);
            int time = (int)(data >> 32);
            int count = (int)(data);
            if(timestamp- time <bucketLen){
                totalHit+= count;
            }
        }
        return totalHit;
    }
}
class HitCounter2{
    int[][] hitData;
    int bucketLen;
    Lock[] locks;

    public HitCounter2(int n){
        hitData = new int[n][2];
        bucketLen = n;
        locks = new Lock[n];

        for(int i=0; i<n; i++){
            locks[i] = new ReentrantLock();
        }
    }

    public void hit(int timestamp){
        int index = timestamp % bucketLen;

        synchronized (locks[index]) {
            int time = hitData[index][1];

            if(time != timestamp){
                hitData[index][0] = 0;
            }
            hitData[index][0]++;
            hitData[index][1] = timestamp;
        }
    }

    public int getHits(int timestamp){
        int totalHit = 0;
        for(int i=0; i<bucketLen; i++){
            synchronized (locks[i]) {
                int time = hitData[i][1];
                if(timestamp - time < bucketLen){
                    totalHit+=hitData[i][0];
                }
            }
        }
        return totalHit;
    }
}