package MultiThreadingTry02;
import java.lang.Math;
import java.util.Scanner;

//main just functions to create and call lotto obj's main method
public class lotteryTryV1 {
	public static void main(String[] args) {
		lottoGenerator lotto = new lottoGenerator();
	    lotto.start();
	    
	}
}



class lottoGenerator extends Thread {
	private Stopper s = new Stopper();
	private int lastGenerated;
	private static int MAXNUMBER=10;
	private static int MINNUMBER=1;
	private int delayMs=450;
	
    //this is essentially our main for this
	public void run() {
		//instructions + countdown
		System.out.println("Press enter to try to select " + MAXNUMBER);
		System.out.println("Start in 3");
		for(int i = 2; i>0; i--) {
			generatorSleep(1000);
			System.out.println(i);
		}
		generatorSleep(1000);
		System.out.println("GOOOOOOOOOOOOOOOOOO");
		
		//start the user input taker
		s.start();
		
		//while haven't received the stop command generate a random number, print it then 
		//wait for 450ms
		while(s.isNotStopped()) {
			genRandom(true);
			generatorSleep(delayMs);
		}
		
		//report whether they won or lost
	    System.out.println("You picked "+ lastGenerated);
	    if (lastGenerated==MAXNUMBER) {
	    	System.out.println("You picked the largest number YOU WINNNN!!!!!");
	    }
	    else {
	    	System.out.println("You lost! Try again.");
	    }

		
	}
	
	//generates a random number and a divider for it
	private void genRandom(boolean printSpaces) {
		if(printSpaces) {
			for(int i = 0; i<10; i++) {
				System.out.println();
		}}
		lastGenerated=(int) (Math.random()*(MAXNUMBER -MINNUMBER)) + MINNUMBER+1;
		System.out.println(lastGenerated);
	}
	
	//need to have the errortrap in order to use the sleep command, this method
	//just makes the code look nicer
	private void generatorSleep(int miliseconds) {
		try {
			sleep(miliseconds);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
}


//this checks to see whether the user has quit or not and if so stop the generation by 
//setting is not stopped to false
class Stopper extends Thread {
	private boolean isNotStopped = true;
	private Scanner s= new Scanner(System.in);
	
	public boolean isNotStopped() {
		return isNotStopped;
	}
	
	public void run() {
		enterToStop();
	}

	public void enterToStop() {
		s.nextLine();
		isNotStopped=false;
	}
}