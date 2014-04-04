package org.gingko.ext;

/**
 * Created by Administrator on 14-4-1.
 */
public class Test {

    public static void main(String[] args) throws Exception {
        //Stock.INSTANCE.download();

        Thread t1 = new Thread(new ThreadTesterA());
        Thread t2 = new Thread(new ThreadTesterB());
        t1.start();
        //t1.join(); // wait t1 to be finished
        t2.start();
        //t2.join(); // in this program, this may be removed
    }

    static class ThreadTesterA implements Runnable {

        private int counter;

        @Override
        public void run() {
            while (counter <= 10000) {
                System.out.print("Counter = " + counter + " ");
                counter++;
            }
            System.out.println();
        }
    }

    static class ThreadTesterB implements Runnable {

        private int i;

        @Override
        public void run() {
            while (i <= 10000) {
                System.out.print("i = " + i + " ");
                i++;
            }
            System.out.println();
        }
    }
}
