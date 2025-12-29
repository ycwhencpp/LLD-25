
import java.util.PriorityQueue;

class main{
    public static void main(String[] args) {
        
        executor ex = new unixcommandExecutor();
        jobrepo jr = new jobrepo(new PriorityQueue<>((a,b)->a.id-b.id));
        jr.pq.offer(new job(0, "first"));
        jr.pq.offer(new job(1, "second"));
        jr.pq.offer(new job(2, "third"));

        scheduler scheduler = new unixcommandscheduler(jr, ex);

        scheduler.startscheduler();
      


    }
  
}

abstract  class scheduler{
    jobrepo jobrepo;
    executor executor;

     public scheduler(jobrepo jr, executor ex){
        this.jobrepo =jr;
        this.executor = ex;
    }
    public abstract void startscheduler();

}

class unixcommandscheduler extends scheduler{
    public unixcommandscheduler(jobrepo jr, executor ex){
        super(jr, ex);
    }
    @Override
    public void startscheduler(){
        while (!this.jobrepo.pq.isEmpty()){
            System.out.println("started");
            job j = this.jobrepo.pq.poll();
            //maybe diff logic for fail storing somehwrre or notifying 
            j.status = jobstatus.FINSIHED;
            executor.execute(j);
        }
    }
}

class job {
    int id;
    String command;
    jobstatus status = jobstatus.SCHEDULED;

    public job (int id, String s){
        this.id = id;
        this.command = s;

    }

}
enum jobstatus{
    FINSIHED,
    STARTED,
    BLOCKED,
    SCHEDULED
}

class jobrepo{
    PriorityQueue<job> pq;

    public jobrepo(PriorityQueue<job> pq) {
        this.pq = pq;
    }

}

interface executor {
    public void execute(job job);
}

class unixcommandExecutor implements  executor {
    @Override
    public void execute(job job){ //could be boolean as well to show fail or success
        System.out.println("excuted job" + job.id);
    }
}