package CircuitBreaker;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class main {

    public static void main(String[] args) {
        CircuitBreaker cb = new CircuitBreaker();
        try{
            Runnable worker = ()->{
                if(cb.allowRequest(1)){
                    System.out.println(Thread.currentThread().getName() + " -> Request ALLOWED. Doing work...");
                    try {
                        Thread.sleep(50);
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                } else {
                    System.out.println(Thread.currentThread().getName() + " -> Request REJECTED (Fail Fast).");
                }
            };

            Thread[] threads = new Thread[10];

            for (int i = 0; i < 10; i++) {
                threads[i] = new Thread(worker, "Thread-" + (i + 1));
            }

            for(Thread t : threads){
                t.join();
            }
            worker.run();
            Thread.sleep(2500);


            if(cb.allowRequest(5)){
                cb.recordSucess();
            }
            new Thread(worker, "11").start();
            new Thread(worker, "12").start();
        } catch (Exception e){

        }

    }

}
enum State{
    open, 
    close,
    half
}
class CircuitBreaker{

    AtomicInteger requestCount = new AtomicInteger(0);
    int threshold =20;
    int cooldown = 10;

    volatile int breakTime = 0;

    AtomicReference<State> state = new AtomicReference<>(State.close);

    public boolean allowRequest(int time){
        State s = state.get();
        if(s == State.open){
            if(time - breakTime > threshold){
                if(state.compareAndSet(State.open, State.half)){
                    return true;
                }
            }
            return false;
        }

        if(s == State.half){
            return false;
        }
        int curr = requestCount.incrementAndGet();
        if(curr >= threshold){
            if(state.compareAndSet(State.close, State.open)){
                breakTime = time;
            }
            
            return false;
        }

        return true;
    }

    public void recordSucess(){
        if(state.compareAndSet(State.half, State.close)){
            requestCount.set(0);
        }
    }
    public void recordFailure(int time){
        if(state.compareAndSet(State.half, State.open)){
           breakTime = time;
        }
    }

}