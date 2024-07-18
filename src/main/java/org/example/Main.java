package org.example;

import lombok.SneakyThrows;

import java.util.concurrent.CountDownLatch;

public class Main {

    public static final int countTeam = 3;
    public static final int countCarsInTeam = 2;

    public static final CountDownLatch startRaceCDL = new CountDownLatch(1);
    public static final CountDownLatch finishRaceCDL = new CountDownLatch(countTeam * countCarsInTeam);

    public static void main(String[] args) {
        Team teams[] = new Team[countTeam];

        for (int i = 0; i < teams.length; i++) {
            teams[i] = new Team(i + 1);
        }

        Race race = new Race(1000, teams);

        race.start();
        race.printResults();
    }

    @SneakyThrows
    public static void waitStartRace() {
        startRaceCDL.await();
    }

    public static void startRace() {
        startRaceCDL.countDown();
    }

    @SneakyThrows
    public static void waitFinishRace() {
        finishRaceCDL.await();
    }

    public static void finishRace() {
        finishRaceCDL.countDown();
    }
}
