import java.io.*;
import java.util.*;
import java.util.Map.Entry;

public class DeliveringGoods {
	static Node[] graph;
	static Set<Integer> clients;
	static Long[] cost;
	static List<Node> sorted;
	static Integer[] matched;
	
	public static void main(String[] args) throws Exception {
		//Scanner sc = new Scanner(new File("b.in"));
		Scanner sc = new Scanner(System.in);
		for(int caseCt = 1 ;; caseCt++){
			int n = sc.nextInt();
			int m = sc.nextInt();
			int c = sc.nextInt();
			if(n+m+c == 0) break;
			graph = new Node[n]; //init graph
			for(int i = 0; i < n; i++) graph[i] = new Node(i);
			clients = new HashSet<>(); //set up clients
			while(c --> 0) clients.add(sc.nextInt());
			while(m --> 0){ //take in edges
				int u = sc.nextInt();
				int v = sc.nextInt();
				long cost = sc.nextLong();
				graph[u].out.put(v, cost); //outgoing edge
			}
			cost = dijkstras(graph);
			for(int from = 0; from < n; from++){ //mark shortest path edges for dag
				if(cost[from] == null) continue; //can't reach this node, so skip it
				List<Integer> shortest = new LinkedList<>();
				for(Entry<Integer, Long> e : graph[from].out.entrySet()){
					int to = e.getKey(); //destination node
					long diff = cost[to] - cost[from];
					if(diff == e.getValue()) shortest.add(to); //shortest path
				}
				graph[from].out.keySet().retainAll(shortest); //keep shortest
				for(int v : graph[from].out.keySet()) graph[v].in.add(from); //mark parents
			}
			sorted = new LinkedList<>();
			topoSort(0, new HashSet<>());
			for(Node v : sorted){ //Calculate the transitive closure for clients
				if(clients.contains(v.id)) v.reaches.add(v.id); 
				for(int parent : v.in) graph[parent].reaches.addAll(v.reaches); //parents can visit their children
				v.reaches.remove(v.id); //remove itself for matching reasons
			}
			matched = new Integer[n];
			int unmatched = clients.size();
			//if it has a +out degree, match to +in degree vertex
			for(int i : clients) {
				if(match(i, new HashSet<>())) unmatched--;
			}
			System.out.printf("Case %d: %d\n", caseCt, unmatched);
		}
	}
	
	//Match each vertex with +out with a vertex with +in
	static boolean match(int u, Set<Integer> seen){
		if(!seen.add(u)) return false; //already tried to match
		for(int v : graph[u].reaches){
			if(matched[v] == null || match(matched[v], seen)){ //if it is matchable to somewhere else
				matched[v] = u;
				return true;
			}
		}
		return false; //no match possible
	}
	//Topologically sort nodes with lowest children at the start
	static void topoSort(int u, Set<Integer> seen){
		if(!seen.add(u)) return;
		for(int v : graph[u].out.keySet()) topoSort(v, seen); //visit children
		sorted.add(graph[u]); //add node to sorted list
	}
	//Use Dijkstra's to get the cost of each Node - O(V*log(E))
	static Long[] dijkstras(Node[] graph){
		Queue<Point> pq = new PriorityQueue<>();
		Long[] cost = new Long[graph.length];
		cost[0] = 0L; //start at the warehouse
		pq.add(new Point(0, 0));
		while(!pq.isEmpty()){
			Point now = pq.poll();
			if(now.cost > cost[now.loc]) continue; //already visited
			for(Entry<Integer, Long> e : graph[now.loc].out.entrySet()){//edge
				Point next = new Point(e.getKey(), now.cost+e.getValue());
				if(cost[next.loc] == null || next.cost < cost[next.loc]){//if better
					pq.add(next); //add it to the queue
					cost[next.loc] = next.cost; //update cost
				}
			}
		}
		return cost;
	}
	//Point class uses to represent a location/cost in Dijkstra's
	static class Point implements Comparable<Point>{
		int loc;
		long cost;
		
		public Point(int l, long c) { loc = l; cost = c; }
		@Override
		public int compareTo(Point o){ return Long.compare(cost, o.cost); };
	}
	//Node class used to track edges
	static class Node{
		int id;
		Map<Integer, Long> out = new HashMap<>();
		Set<Integer> in = new HashSet<>();
		Set<Integer> reaches = new HashSet<>();
		
		public Node(int i){ id = i; };
	}
}