import java.io.*;
import java.util.*;

public class ErraticAnts {
	static final int[][] dir = {{1,0},{0,1},{-1,0},{0,-1}};
	static Map<Integer, Set<Integer>> nodes;
	
	public static void main(String[] args) throws Exception {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));
		int n = Integer.parseInt(in.readLine());
		while(n --> 0){
			in.readLine(); // discard empty line
			nodes = new HashMap<>();
			int ct = Integer.parseInt(in.readLine());
			int y = 60, x = 60; //0 to 120 in the dim
			while(ct --> 0){
				int old = getHash(y, x);
				int[] d = getDelta(in.readLine().charAt(0)); //get delta
				y += d[0];
				x += d[1];
				int nw = getHash(y, x);
				getNode(old).add(nw); //connect old to new
				getNode(nw).add(old); //connect new to old
			}
			out.write(search(getHash(y, x)) + "\n");
		}
		out.flush();
	}
	//BFS for min cost path
	static int search(int goal){
		Set<Integer> v = new HashSet<>(); //visited
		Set<Integer> cur = new HashSet<>(); //this wave to search
		int start = getHash(60,60); // 0,0 starting point
		v.add(start); 
		cur.add(start);
		int cost = 0;
		while(!cur.isEmpty()){
			Set<Integer> next = new HashSet<>(); //next wave to search
			for(int c : cur){
				if(c == goal) return cost; //found the goal
				for(Integer nbr : getNode(c)) //for each in cur wave
					if(v.add(nbr)) next.add(nbr); //visit un-visited neighbors
			}
			cur = next; // search the next wave
			cost++; //increase number of steps taken
		}
		return -1;
	}
	//Return change [y, x]
	static int[] getDelta(char c){
		if (c == 'N') return dir[0];
		else if (c == 'E') return dir[1];
		else if (c == 'S') return dir[2];
		else return dir[3]; //W
	}
	//Return list of connected nodes
	static Set<Integer> getNode(int state){
		Set<Integer> node = nodes.get(state);
		if(node == null){
			node = new HashSet<>();
			nodes.put(state, node);
		}
		return node;
	}
	//Get hash of coordinates
	static int getHash(int y, int x){
		return (x<<10)|(y);
	}
}