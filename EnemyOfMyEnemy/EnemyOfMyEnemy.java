import java.io.*;
import java.util.*;

public class EnemyOfMyEnemy {
	static Map<String, Person> persons;
	
	public static void main(String[] args) throws Exception {
		//Scanner sc = new Scanner(new File("c.in"));
		Scanner sc = new Scanner(System.in);
		for(int caseCt = 1 ;; caseCt++){
			int r = sc.nextInt(); //relationships
			int l = sc.nextInt(); 
			if(r == 0 && l == 0) break;
			persons = new HashMap<>();
			while(r --> 0){
				boolean friends = sc.next().equals("f");
				int num = sc.nextInt();
				if(friends){ //Make them friends
					Set<String> list = new HashSet<>();
					while(num --> 0) list.add(sc.next());
					makeFriends(list);
				}else{ //make them enemies
					String a = sc.next();
					String b = sc.next();
					getPerson(a).enemies.add(b);
					getPerson(b).enemies.add(a);
				}
			}
			for(Person p : persons.values()) makeFriends(p.enemies); //make all the enemies friends
			System.out.printf("Case %d:", caseCt);
			while(l --> 0){ //check all the lists given
				int num = sc.nextInt();
				Set<String> list = new HashSet<>();
				while(num --> 0) list.add(sc.next());
				System.out.print(check(list) ? " yes" : " no");
			}
			System.out.println();
		}
	}
	
	//Check to see if all the friends are friends with everyone else
	static boolean check(Set<String> list){
		for(String name : list){
			Person p = getPerson(name);
			if(!p.friends.containsAll(list)) return false;;
		}
		return true;
	}
	
	//Make the list of friends all mutual friends.
	static void makeFriends(Set<String> list){
		for(String name : list){
			Person p = getPerson(name);
			p.friends.addAll(list);
		}
	}
	
	//Get Person associated with name. If no person, create one.
	static Person getPerson(String name){
		Person p = persons.get(name);
		if(p == null) p = new Person(name);
		return p;
	}
	
	//Person class uses to track friends and enemies
	static class Person{
		Set<String> friends = new HashSet<>();
		Set<String> enemies = new HashSet<>();
		
		public Person(String name){
			friends.add(name); //friend of myself
			persons.put(name, this); //register in map
		}
	}
}
