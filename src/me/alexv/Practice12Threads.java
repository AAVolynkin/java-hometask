package me.alexv;

public class Practice12Threads {
    //Напишите программу, в которой запускается 10 потоков и каждый из них выводит
    //числа от 0 до 100.
    public static void method1() {
        Runnable job = () -> {
            for (int i = 0; i < 100; i++) {
                System.out.println("Thread: " + Thread.currentThread().getName() + ". I: " + i);
            }
        };

        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(job);
            thread.setName("thread" + i);
            thread.start();
        }

        //Thread.sleep(3000);
    }

    //Выведете состояние потока перед его запуском, после запуска и во время выполнения
    public static void method2() throws InterruptedException {
        Thread thread = new Thread(() -> System.out.println( "State in run: " + Thread.currentThread().getState() ));
        System.out.println( "State before start: " + thread.getState() );
        thread.start();
        Thread.sleep(1000);
        System.out.println( "State after start: " + thread.getState() );

    }

    //Напишите программу, в которой запускается 100 потоков, каждый из которых
    //вызывает метод increment() 1000 раз.
    //После того, как потоки завершат работу, проверьте, чему равен count .
    //Если обнаружилась проблема, предложите ее решение.
    public static void method3() throws InterruptedException {
        Counter c = new Counter();

        Runnable job = () -> {
            for (int i = 0; i < 1000; i++) {
                c.increment();
            }
        };

        System.out.println("beg var count: " + c.getCount());
        for (int i = 0; i < 100; i++) {
            Thread thread = new Thread(job);
            thread.start();
        }
        Thread.sleep(3000);
        System.out.println("end var count: " + c.getCount());
        // increment не атомарная операция, поэтому разные потоки погут прочитать переменную
        // до внесения изменений др. потоком и не учесть эти изменения.
        // Добавляем synchronized(lock) для решения
        //Object lock = new Object();
        //public void increment() {
        //        synchronized(lock) {
        //            count = count + 1;
        //        }
        //    }
    }

    //Напишите программу, в которой создаются два потока, каждый из которых выводит
    //по очереди на консоль своё имя.
    //Начать можно с написания своего класс-потока, который выводит в бесконечном
    //цикле свое имя. Потом придется добавить синхронизацию с помощью wait() и
    //notify()
    public static void method4() {

        class MyThread extends Thread {
            private final  Object lock;

            MyThread(Object lock) {
                this.lock = lock;
            }
            @Override
            public void run() {
                while (true) {
                    synchronized (lock) {
                        try {
                            lock.notify();
                            System.out.println("Thread name: " + Thread.currentThread().getName());
                            lock.wait();
                        } catch (InterruptedException ignore) {
                        }
                    }
                }
            }
        }

        Object lock = new Object();

        for (int i = 0; i < 2; i++) {
            MyThread thread = new MyThread(lock);
            thread.setName("Thread" + (i + 1) );
            thread.start();
        }

    }
}

class Counter {
    private final Object lock = new Object();
    int count = 0;

    public void increment() {
        synchronized(lock) {
            count = count + 1;
        }
    }

    public int getCount() {
            return count;
    }
}