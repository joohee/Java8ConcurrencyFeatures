package net.joey.reflect.example;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
public class RentCar {
    private int rate;
    private String type;
    public int price;

    public RentCar(int length) {
        if (length < 455) {
            type = "small";
            rate = 35;
        } else if ((length >= 455) && (length < 495)) {
            type = "mid-sized";
            rate = 45;
        } else {
            type = "large";
            rate = 55;
        }
    }

    public void computeRentalCost(int numDays) {
        price = numDays * rate;
        log.info("The cost of your rental car is {} euros.", price);
    }
}