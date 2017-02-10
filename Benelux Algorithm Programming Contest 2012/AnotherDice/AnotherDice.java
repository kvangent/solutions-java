import java.io.*;
import java.util.*;

public class AnotherDice {
	final static int[] dieVal = { 1, 2, 3, 4, 5, 5};
	final static double[] factorial = { 1, 1, 2, 2*3, 2*3*4, 2*3*4*5,
										2*3*4*5*6, 2*3*4*5*6*7, 2*3*4*5*6*7*8};
	static Double[][][] mem = new Double[41][9][(1<<6)];
	
	
	public static void main(String[] args) throws Exception{
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));
		int n = Integer.parseInt(in.readLine());
		while(n --> 0){
			int pts = Integer.parseInt(in.readLine());
			System.out.printf("%f\n", solve(pts, 8, 0));
		}
	}
	
	//Points req, dice needed, Used numbers
	static double solve(int pts, int d, int u){
		if(pts < 0) pts = 0; // negative points is as good as winning!
		if(pts == 0 && used(5, u)) return 1.0; // no points and used worm = win
		if(d == 0) return 0.0;
		if(mem[pts][d][u] == null){
			mem[pts][d][u] = 0.0;
			double total = 0.0;
			int[] roll = {d, 0, 0, 0, 0, 0};
			while(true){
				double best = 0.0; // try all the options and choose best
				for(int i = 0; i < 6; i++) if (roll[i] > 0 && !used(i, u)){
					best = Math.max(best, 
							solve(pts-(roll[i]*dieVal[i]), d-roll[i], u|(1<<i)));
				}
				double prob = factorial[d]; // calc the odds of this permutation
				for(int i = 0; i < 6; i++) prob /= factorial[roll[i]];
				mem[pts][d][u] += prob * best;
				total += prob; //keep track of total number of combos
				if(!nextRoll(d, roll)) break;
			}
			mem[pts][d][u] /= total; // winning 
		}
		return mem[pts][d][u];
	}
	// Get the next roll in the sequence
	static boolean nextRoll(int dice, int[] roll){
		if(roll[5] == dice) return false;
		int i = 5; //Start with the right most digit
		while(roll[i] == 0) i--; //
		if(i == 5){
			int j = i-1;
			while(roll[j] == 0) j--;
			roll[j]--;
			int swap = roll[i];
			roll[i] = 0;
			roll[j+1] = swap+1;
		}else{
			roll[i]--;
			roll[i+1]++;
		}
		return true;
	}
	// Check if side i is used in bitset u
	static boolean used(int i, int u){ return ((1<<i)&u) != 0; }
}