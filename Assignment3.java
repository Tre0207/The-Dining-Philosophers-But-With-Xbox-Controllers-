
public class Assignment3 {
    public static void main(String [] args) throws InterruptedException {
        Studentt[] players = new Studentt[5];
        Thread[] myThread = new Thread[5];
        for(int i = 0; i < 5; i++){
            players[i] = new Studentt(i);
        }
        for(int i = 0; i < 5; i++){
            myThread[i] = new Thread(players[i]);
        }
        for(int i = 0; i < 5; i++){
            myThread[i].start();
        }
        for(int i = 0; i < 5; i++){
            myThread[i].join();
        }
        System.out.println("\n---SUMMARY TABLE---");
        for(int i = 0; i < 5; i++){
            System.out.println("Student " + (i + 1) + " number of time(s) played: " + players[i].timesPlayed);
        }
    }
}

class Xbox {

    boolean[] controllers = {true,true,true,true,true};

public synchronized boolean getController(int number){
    if(controllers[number]){
        controllers[number] = false;
        return true;
    }
    else
        return false;
    }
public synchronized void putController(int number){
    controllers[number] = false;
    }
}

class Studentt implements Runnable {
    private Xbox xbox = new Xbox();
    private int studentNumber;
    public int timesPlayed;
    public Studentt(int studentNumber){
        this.studentNumber = studentNumber;
    }

    @Override
    public void run() {
        long start = System.currentTimeMillis();
        long end = start + 120*1000;
        try {
            while (System.currentTimeMillis() < end) {
                // thinking
                System.out.println("Student " + (studentNumber + 1) + " is thinking about Java");
                Thread.sleep(((int) (Math.random() * 3000)));
                //attempts to grab controller
                System.out.println("Student " + (studentNumber + 1) + " attempts to get controller " + (studentNumber + 1));
                if(xbox.getController(studentNumber)){
                    

                    System.out.println("Student " + (studentNumber + 1) + " has controller " + (studentNumber + 1));
                }
                else{
                    System.out.println("Controller " + (studentNumber + 1) + " was being used. Try again later");
                    Thread.sleep(500);
                }
                System.out.println("Student " + (studentNumber + 1) + " attempts to get controller " + ((studentNumber + 1) % 5 + 1));
                if(xbox.getController((studentNumber + 1) % 5)){
                    System.out.println("Student " + (studentNumber + 1) + " has controller " + ((studentNumber + 1) % 5 + 1));
                }
                else{
                    System.out.println("Controller " + ((studentNumber + 1) % 5 + 1) + " was being used. Try again later");
                    Thread.sleep(500);
                }
                if(!xbox.getController(studentNumber) && !xbox.getController((studentNumber + 1) % 5)){
                    timesPlayed++;
                    System.out.println("Student " + (studentNumber + 1) + " is playing Xbox");
                    Thread.sleep(((int) (Math.random() * 5000)));
                    xbox.putController(studentNumber);
                    xbox.putController((studentNumber + 1) % 5);
                }
            }
            Thread.currentThread().interrupt();
        } 
        catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return;
        }
    }
}
