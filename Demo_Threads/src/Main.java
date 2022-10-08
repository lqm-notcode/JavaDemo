import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Main {
    volatile int state = 0;
    ReentrantLock lock = new ReentrantLock();
    Condition con = lock.newCondition();
    public static void main(String[] args)
    {
        Main main = new Main();
        Main.DigitRunnbale r1 = main.new DigitRunnbale(1,10);
        Main.CharRunnbale r2 = main.new CharRunnbale('a', 10);
        Thread t1 = new Thread(r1);
        Thread t2 = new Thread(r2);
        t1.start();
        t2.start();
    }

    class CharRunnbale implements Runnable{
        boolean isDigit = true;
        int k = 0;
        char c = 'a';
        int n = 0;

        CharRunnbale(char c, int n){
            this.n = n;
            this.c = c;
            isDigit = false;
        }
        @Override
        public void run(){
            for(int i = 0; i < n; i++)
            {
                lock.lock();
                while (state != 0)
                {
                    try
                    {
                        con.await();
                    } catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                }
                System.out.print((char) (c + i));
                con.signalAll();
                state = 1;
                lock.unlock();
            }
        }
    }
    class DigitRunnbale implements Runnable{
        boolean isDigit = true;
        int k = 0;
        char c = 'a';
        int n = 0;
        DigitRunnbale(int i, int n){
            this.k = i;
            this.n = n;
        }
        @Override
        public void run(){
            for(int i = 0; i < n; i++){
                lock.lock();
                while(state != 1){
                    try
                    {
                        con.await();
                    } catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                }
                System.out.print(k+i);
                System.out.println(" ");
                state = 0;
                con.signalAll();
                lock.unlock();
            }
        }
    }

}
