import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Traffic {

    private int speed = 2;

    private List<Vehicle> vehicles;
    private GameWindow gw;
    private boolean isEven = true;

    public Traffic (GameWindow gw){

        this.gw = gw;
        vehicles = new ArrayList<>();

        addVehicle();
        addVehicle();
    }

//    Adds a vehicle to the amount that are on screen and switches between adding a new horizontal and vertical vehicle. Direction of the vehicles are randomized.
    public void addVehicle(){
        if(vehicles.size() >= 20) return;

        int direction = new Random().nextInt(2);

        if (direction == 0) direction = -1;

        if(isEven){
            vehicles.add(new Vehicle(gw, speed * direction, 0));
            isEven = false;
        }
        else{
            vehicles.add(new Vehicle(gw, 0, speed * direction));
            isEven = true;
        }
    }

    public  void update(){
        for (Vehicle vehicle: vehicles){
            vehicle.move();
            if (gw.getPlayer().isAccident(vehicle) && gw.isPlaying()){
                gw.getPlayer().damage();
            }

//            Setting of focus power up to all vehicles
            if(gw.getPowerUp() != null){
                if(gw.getPowerUp().isFocusActive() && !vehicle.isInFocus()){
                    vehicle.setFocus(0.25);
                }

                if (!gw.getPowerUp().isFocusActive() && vehicle.isInFocus()){
                    vehicle.setFocus(4);
                }
            }
        }
    }

    public void draw(){
        for (Vehicle vehicle: vehicles){
            vehicle.draw(gw.getG());
        }
    }

//    Increases speed for new vehicles.
    public void increaseSpeed(){
        if (speed >= 10) return;

        speed = speed + 1;
    }

    public int trafficSize(){
        return vehicles.size();
    }

    public int getSpeed() {
        return speed;
    }
}
