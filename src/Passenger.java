import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Passenger {

    Graphics2D g2;
    GameWindow gw;
    private List<People> passengers;

    public Passenger (GameWindow gw) {
        this.gw = gw;
        g2 = gw.getG();

        passengers = new ArrayList<>();

    }

    public void addPassengers(){
        passengers.add(new People(gw.getDimension(), g2));
    }

    public void draw() {
        for(People passenger: passengers){
            passenger.draw();
        }
    }

    public void update() {
        People p = null;

        if (passengers.size() < gw.getMaxPassengers()) addPassengers();

        for(People passenger: passengers){
            if(gw.getPlayer().isPassenger(passenger) && !passenger.isPassengerCollected()){
                gw.getPlayer().pickUpPassenger(passenger);
                passenger.setPassengerCollected(true);
            }

            if(gw.getPlayer().isDropOff(passenger)){
                gw.getPlayer().dropOffPassenger(passenger);
                passenger.setDroppedOff(true);
                gw.increaseScore();
                p = passenger;
            }
        }

        passengers.remove(p);
    }

}
