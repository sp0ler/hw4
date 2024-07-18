package org.example;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Phaser;

/**
 * Работник питстопа, меняет шину на прибывшей машине на своем месте
 */
@Log4j2
@RequiredArgsConstructor
public class PitWorker extends Thread {

    //Место работника, он же номер колеса от 0 до 3
    private final int position;

    //Ссылка на сущность питстопа для связи
    private final PitStop pitStop;

    @Override
    @SneakyThrows
    public void run() {
        while (!isInterrupted()) {
            F1Cars car = pitStop.getCar();
            //TODO работник ждет машину на питстопе и меняет шину на своей позиции
            car.getWheel(position).replaceWheel();

            //TODO работник сообщает о готовности
            pitStop.getBarrier().await();
            log.info("Машина {} поменяла колесо {}", car.getName(), position);
        }
    }
}
