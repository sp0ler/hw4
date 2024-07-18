package org.example;

import lombok.Getter;
import lombok.SneakyThrows;

import static org.example.Main.countCarsInTeam;

@Getter
public class Team {

    private final long id;

    private final F1Cars[] cars = new F1Cars[countCarsInTeam];

    private final PitStop pitStop = new PitStop();

    public Team(long id) {
        this.id = id;
        for (int i = 0; i < this.cars.length; i++) {
            this.cars[i] = new F1Cars(id * 10 + i, pitStop);
        }
        pitStop.start();
    }

    @SneakyThrows
    public void prepareRace(Race race) {
        for (int i = 0; i < this.cars.length; i++) {
            this.cars[i].prepareRace(race);
        }
    }
}
