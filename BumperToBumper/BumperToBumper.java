import java.io.*;
import java.util.*;

public class BumperToBumper {
	
	public static void main(String[] args) throws Exception {
		//Scanner sc = new Scanner(new File("a.in"));
		Scanner sc = new Scanner(System.in);
		for(int caseCt = 1;; caseCt++){//For each Test Case
			Car a = new Car(sc.nextInt());
			Car b = new Car(sc.nextInt());
			if(a.loc == 0 && b.loc == 0) break; //end case
			for(int i = sc.nextInt(); i-->0;) a.startStops.add(sc.nextInt()); //Set up A
			for(int i = sc.nextInt(); i-->0;) b.startStops.add(sc.nextInt()); //Set up B
			int out = simulate(a, b); //simulate cars
			System.out.printf("Case %d: ", caseCt);
			if(out == -1) System.out.println("safe and sound");
			else System.out.printf("bumper tap at time %d\n", out);
		}	
	}
	
	static int simulate(Car a,  Car b){
		int t = -1; //track time
		while(!a.startStops.isEmpty() || !b.startStops.isEmpty()){
			a.simulate(t); //System.err.println("A: " + a.loc + " " + a.moving);
			b.simulate(t);  //System.err.println("B: " + b.loc + " " + b.moving);
			t++;
			if(Math.abs(a.loc-b.loc) < 5) return t; //collision detected
		} //Move cars until they are in the final state
		//if rear car is moving and front car is stopped
		if(a.loc < b.loc && a.moving && !b.moving) return t+(b.loc-a.loc)-4;
		if(b.loc < a.loc && b.moving && !a.moving) return t+(a.loc-b.loc)-4;
		return -1; //no collision
	}

	static class Car{
		boolean moving = false; //describes if the car is moving or not
		int loc; //position of the car
		Queue<Integer> startStops = new LinkedList<>(); //times the car starts and stops
		
		//Creates a new car with rear bumper at start
		public Car(int start){
			this.loc = start;
		}
		
		//Simulates this car time from T until T+1
		void simulate(int t){
			if(!startStops.isEmpty() && t == startStops.peek()) { //if at change
				moving = !moving; //flip state
				startStops.poll(); //remove change from queue
			}
			if(moving) loc++; //if moving, move the car forward 1m/s
		}
	}
	
}