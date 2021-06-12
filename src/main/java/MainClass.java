import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MainClass {
    public static final int CARS_COUNT = 10;
    public static final int CARS_IN_TUNNEL = CARS_COUNT / 2;
    public static CountDownLatch preparation = new CountDownLatch(CARS_COUNT);
    public static CountDownLatch isStart = new CountDownLatch(1);
    public static CountDownLatch isFinish = new CountDownLatch(CARS_COUNT);
    public static boolean WIN = false;
    public static Semaphore smp = new Semaphore(CARS_IN_TUNNEL);
    final static Lock lock = new ReentrantLock();

    public static void main(String[] args) throws InterruptedException {
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Подготовка!!!");
        Race race = new Race(new Road(60), new Tunnel(), new Road(40));
        Car[] cars = new Car[CARS_COUNT];
        for (int i = 0; i < cars.length; i++) {
            cars[i] = new Car(race, 20 + (int) (Math.random() * 10));
        }
        for (Car car : cars) {
            new Thread(car).start();
        }
        preparation.await();

        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка началась!!!");
        isStart.countDown();

        isFinish.await();
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка закончилась!!!");
    }
}
