package org.example;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;

import java.util.List;

import static org.example.Main.startRace;
import static org.example.Main.waitFinishRace;

@Log4j2
public class Race {

    @Getter
    private final long distance;

    private List<F1Cars> participantCars = new java.util.ArrayList<>();

    private List<Team> teams = new java.util.ArrayList<>();

    public Race(long distance, Team[] participantCars) {
        this.distance = distance;
        teams.addAll(List.of(participantCars));
    }

    /**
     * Запускаем гонку
     */
    public void start() {
        for (Team team : teams) {
            team.prepareRace(this);

            for (F1Cars car : team.getCars()) {
                register(car);
            }
        }

        //TODO даем команду на старт гонки
        log.info("На старт.");
        log.info("Внимание.");
        log.info("МАРШ!!!");
        startRace();

        //TODO блокируем поток до завершения гонки
        waitFinishRace();
    }


    //Регистрируем участников гонки
    public void register(F1Cars participantCar) {
        participantCars.add(participantCar);
    }


    public void start(F1Cars f1Cars) {
        //фиксация времени старта
        f1Cars.setTime(System.nanoTime());
    }

    public long finish(F1Cars participant) {
        //фиксация времени финиша
        return System.nanoTime() - participant.getTime(); //длительность гонки у данного участника
    }

    public void printResults() {
        participantCars.sort(F1Cars::compareTo);
        log.info("Результат гонки:");
        int position = 1;
        for (F1Cars participant : participantCars) {
            log.info("Позиция: {}, Имя: {}, время {}", position++, participant.getName(), participant.getTime());
        }
    }
}
