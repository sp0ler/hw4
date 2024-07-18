package org.example;

import lombok.Getter;
import lombok.SneakyThrows;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Phaser;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class PitStop extends Thread {

    public static final int countWorkers = 4;

    PitWorker[] workers = new PitWorker[countWorkers];

    @Getter
    private final CyclicBarrier barrier = new CyclicBarrier(countWorkers + 1);

    private final ReentrantLock lock = new ReentrantLock();
    private final Condition condition = lock.newCondition();
    private final Object mutex = new Object();

    private F1Cars car = null;

    public PitStop() {
        for (int i = 0; i < workers.length; i++) {
            workers[i] = new PitWorker(i, this);
            workers[i].start();
        }
    }

    @SneakyThrows
    public void pitline(F1Cars f1Cars) {
        // TODO условие: на питстоп может заехать только 1 пилот
        // TODO держим поток до момента смены всех шин
        // TODO каждую шину меняет отдельный PitWowker поток
        // TODO дожидаемся когда все PitWorker завершат свою работу над машиной
        // TODO метод запускается из потока болида, нужна синхронизация с потоком питстопа
        synchronized (mutex) {
            setF1Cars(f1Cars);

            // TODO отпускаем машину
            barrier.await();
            this.car = null;
        }
    }


    @Override
    public void run() {
        while(!isInterrupted()){
            //синхронизируем поступающие болиды и работников питстопа при необходимости
        }
    }

    @SneakyThrows
    public F1Cars getCar() {
        //TODO Блокируем поток до момента поступления машины на питстоп и возвращаем ее
        lock.lock();
        try {
            while (this.car == null) {
                condition.await();
            }
            return this.car;
        } finally {
            lock.unlock();
        }
    }

    public void setF1Cars(F1Cars f1Cars) {
        this.car = f1Cars;
        lock.lock();
        try {
            condition.signalAll();
        } finally {
            lock.unlock();
        }
    }

    @SneakyThrows
    public synchronized void done() {

    }
}
