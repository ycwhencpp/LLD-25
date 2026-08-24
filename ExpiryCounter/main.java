import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;

public class main {

    public static void main(String[] args) {
        
       ExpiryCounter2 ex = new ExpiryCounter2(5);
       ex.put_element(1, 1);
       ex.put_element(1, 4);
       ex.put_element(1, 5);

       ex.put_element(2, 6);

       System.out.println(ex.get_element_count(1, 5));
       System.out.println(ex.get_element_count(1, 6));
       System.out.println(ex.get_element_count(2, 6));
       System.out.println(ex.get_element_count(1, 8));



    }

}

class ExpiryCounter{
    int expiryTime;
    HashMap<Integer, Deque<Integer>> counterMap = new HashMap<>();
    public ExpiryCounter(int n){
        expiryTime = n;
    }

    public void put_element(int k, int second){
        counterMap.get(k).offerLast(second);
    }

    public int get_element_count(int k, int second){
        Deque<Integer> deque = counterMap.getOrDefault(k, new LinkedList<>());

        cleanup(deque, second);
        return deque.size();
    }

    public int get_total_elements(int second){
        int count =0;
        for(Deque<Integer> dq : counterMap.values()){
            cleanup(dq, second);
            count+=dq.size();

        }
        return count;
    }

    public void cleanup(Deque<Integer> deque, int second){
        while(!deque.isEmpty() && second - deque.peek() >= expiryTime ){
            deque.removeFirst();
        }
    }


}



class ExpiryCounter2{
    int expiryTime;
    HashMap<Integer, int[][]> map = new HashMap<>();
    public ExpiryCounter2(int n){
        expiryTime = n;

    }

    public void put_element(int k, int second){
        if(!map.containsKey(k)){
            map.put(k, new int[expiryTime][2]);
        }
        updatebucketCount(k , second);
    }

    public void updatebucketCount(int k, int second){
        int index = second % expiryTime;
        int[][] arr = map.get(k);
        // 1 2 3 4 5 
        if(arr[index][0] != second){
            arr[index][1] = 0;
        }
        arr[index][0] = second;
        arr[index][1]++;
    }

    public int get_element_count(int k, int second){
       int[][] arr = map.get(k);
       return calculateCount(arr, second);
       
    }

    public int calculateCount(int[][] arr, int second){
        int ans =0;
        for(int i=0; i<arr.length; i++){
                if(second - arr[i][0] < expiryTime){
                    ans+= arr[i][1];
                }
        }
        return ans;
    }

    public int get_total_elements(int second){
        int count =0;
        for(int[][] arr : map.values()){
           count+= calculateCount(arr, second);

        }
        return count;
    }


}

/**
 * Design and implement an Expiry Counter system.
 *  The system should track occurrences of elements, but each occurrence has a limited lifespan.
 * Constraints & Requirements:Initialization:
 *  The counter is initialized with a global expiry time $T$ 
 * (seconds).put_element(k): Adds one instance of key $k$.
 *  This instance is valid for exactly $T$ seconds from the moment it is added.get_element_count(k):
 *  Returns the number of instances of $k$ that have not yet expired.get_total_elements(): 
 * Returns the total count of all valid (non-expired) instances across all keys.
 * 
 * 
 * 
 * occurence ->map 
 * with timestamp 
 * put -> 
 * store new instance of k with timestamp 
 * 
 * total -> get all keys 
 * 
 * basically a system with TTL 
 * how redis work 
 *  - bg schedulers (stale data)
 *  - scrape on read (increase in latency)
 * queue or something. ??(so scrape from above and return size)
 * 
 * map of treeset to store ?? why sorted so removal is easy o(K) and findign till when is expired is O(logK)
 * reading will be n*m(stale data +new data)
 * get k will be k
 * 
 *  I suggested a priority queue along with hashmap based approach where I would add elements with their expiry timestamps to the PQ like (k1, t1), (k2, t2) etc. On the read operation, I would first remove the expired timestamps and then return the count of that element. For all elements read, I would do that for each element using the hashmap's keyset.
The interviewer didn't sound very convinced with this approach and asked me if I can do better. 
Then I suggested a binary search based approach in which 
I would just maintain a mapping of element to list of timestamps like 'k': [t1, t2, ... tn],
 on a specific element read, I would find the valid timestamps in O(logn).
  He found this 'fine' and asked me to code it up. I coded this in around 7-8 mins(python bisect rocks!)
   and ran the code which worked as expected.
    The interviewer questioned me on why am 
    I not discarding the stale timestamps at the time of reading which is inefficient, 
    I said we can do that, just got missed. This is the point
     I think I lost the interviewer as he kept on insisting on the point that
      I should have thought of this as a production system(without mentioning that in the question anywhere).
 * 
 * 
 */