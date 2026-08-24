# question
Consider there are differnt types of alexa devices available. One with audio, one with screen, one with audio and screen. These devices may have a battery or may not. Battery devices will have battery percentage. Both battery and non battery devices can be put charging. The task is to show the battery percentage. Include a show methond and that method should show the current battery percentage if it has a battery. If not just say, battery not available. You should also say whether its currently charging or not. There will four statements to print show method like Charging and battery percentage, charging and no battery, just battery percent and no battery.

- devices
    - audio
    - video
    - battery
        -percentage 
         charging()
    - non-battery


1. primary capiblities 
2. error handling 
3. scope bondaries 

# requirement gathering 
- device should support charging and battery percentage method 
- should print statment based on type of device
- charging on no battery phone should throw error 
- extensibility 
    - should be able to support new fratures as wwell 
    - modular 
    - safe operations 


# entities(look for noun or things that stand out)
- device 
- alexa 
- battery 
- feature 

# class design (here we define what state and methods a entity exposes)
- try top down 

1. Device
    - PowerBehaviour
    - List<Feature>

    + showPercentage()
    + isCharging()

2. Alexa extends Device

3. PowerBehaviour interface 
    + showPercentage()
    + isCharging()
4. BatteryPowerBehaviour implements PowerBehaviour
5. NoBatterypowerBheaviour implements PowerBehaviour
6. Battery 
    - int percentage 
    - BatteryState

7. BatteryState[CHARGING, IDLE, FULL_CHARGED, DISCHARGED]

8. Feature interface 
    + execute()

9. AudioFeature implements Feature 
10. VideoFeature implements Feature 
11. AudioWithVideo Implements Feature 
12. Camera Implements Feature 

# Implementation 
    - define the core logic 
    - consider edge cases 

abstract class Device{
    PowerBehaviour powerBehaviour;
    List<Feature> features;

    public Device(PowerBehaviour powerBehaviour, List<Feature> features){
        this.powerBehaviour = powerBehaviour;
        this.features = features;
    }

    public void showPercentage(){
        this.powerBehaviour.showPercentage();
    }

    public boolean isCharging() {
        return  this.powerBehaviour.isCharging();
    }
}

interface PowerBehaviour{
     public void showPercentage();
      public boolean isCharging();
}

class BatteryPowerBehaviour implement PowerBehaviour{
    Battery battery;

    public void showPercentage(){
        battery.showPercentage();
    }
    public boolean isCharging(){
        battery.state == batteryState.CHARGING;
    }

}
class NoBatteryPowerBehaviour implement PowerBehaviour{
   

    public void showPercentage(){
        sout("Battery not supported")
    }
    public boolean isCharging(){
        sout("battery not suported")
    }

}