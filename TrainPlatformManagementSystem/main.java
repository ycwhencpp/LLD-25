package TrainPlatformManagementSystem;
import java.util.HashMap;
import java.util.TreeSet;

public class main {

}

class TrainPlatformManagementSystem{
    HashMap<Integer, PlatForm> platformMap;
    HashMap<Integer, TreeSet<PlatFormStopage>> trainStopageMap;

    PlatFormAssignmentStartegy platFormAssignmentStartegy;

    public TrainPlatformManagementSystem(HashMap<Integer, PlatForm> platformMap,
        HashMap<Integer, TreeSet<PlatFormStopage>> trainStopageMap,
        PlatFormAssignmentStartegy platFormAssignmentStartegy) {
        this.platformMap = platformMap;
        this.trainStopageMap = trainStopageMap;
        this.platFormAssignmentStartegy = platFormAssignmentStartegy;
    }




    public boolean assignTrain (StopageRequest request){
        return request.platForm.assignTrain(request);
    }   

    public PlatForm getTrainInfo(StopageRequest request) {
        int train = request.train.id;
        TreeSet<PlatFormStopage> trainStopageData =  trainStopageMap.get(train);

        PlatFormStopage dummy = new PlatFormStopage(request.starttime, request.endtime, null);
        PlatFormStopage floor = trainStopageData.floor(dummy);

        if(floor == null || floor.endtime < request.starttime) return null;

        return floor.platform;
        
    }
    public Train getPlatformInfo(StopageRequest request) {
        int platform = request.platForm.id;
        PlatForm platFormObj =  platformMap.get(platform);
        return platFormObj.checkPlatormStatus(request);
        
    }


}

class PlatForm {
    int id;
    TreeSet<Stopage> stopages;

    public boolean isAvailiable (StopageRequest request) {
        Stopage dummy = new Stopage(1, request.starttime, request.endtime, null);
        Stopage floor = stopages.floor(dummy);
        Stopage ceiling = stopages.floor(dummy);


        if(floor != null && request.starttime <= floor.endtime ){
            return false;
        }
        //check overlap
        if(ceiling != null && request.endtime >= ceiling.starttime){
            return false;
        }
        return true;
    }

    public boolean assignTrain(StopageRequest request){
        if(!isAvailiable(request)) return false;
        stopages.add(new Stopage(1, request.starttime, request.endtime, request.train));
        return true;
    }

    public Train checkPlatormStatus(StopageRequest request){
        Stopage dummy = new Stopage(1, request.starttime, request.endtime, null);
        Stopage floor = stopages.floor(dummy);
        Stopage ceiling = stopages.ceiling(dummy);

        if (floor != null && floor.endtime >= request.starttime) return floor.train;
        if(ceiling != null && request.endtime >= ceiling.starttime) return ceiling.train;
        return null;

    }
}

class Stopage {
    int id;
    int starttime;
    int endtime;
    Train train;
    public Stopage(int id, int starttime, int endtime, Train train) {
        this.id = id;
        this.starttime = starttime;
        this.endtime = endtime;
        this.train = train;
    }

}

class Train {
    int id;
}


class StopageRequest{
    int starttime;
    int endtime;
    Train train;
    PlatForm platForm;
}

class PlatFormStopage{
    int starttime;
    int endtime;
    PlatForm platform;
    public PlatFormStopage(int starttime, int endtime, PlatForm platform) {
        this.starttime = starttime;
        this.endtime = endtime;
        this.platform = platform;
    }
}
interface PlatFormAssignmentStartegy{
    
}