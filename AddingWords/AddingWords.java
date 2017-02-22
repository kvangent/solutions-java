import java.io.*;
import java.util.*;

public class AddingWords {
	static Map<String, Integer> str2num = new HashMap<>(); //word -> value
	static Map<Integer, String> num2str = new HashMap<>(); //word -> value
	
	public static void main(String[] args) throws Exception {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));
		while(true){
			String line = in.readLine();
			if(line == null) break;
			String[] input = line.split("\\s+");
			if(input[0].equals("def")){ //update definition
				Integer old = str2num.get(input[1]);
				num2str.remove(old); // remove old entry (if there is one)
				int val = Integer.parseInt(input[2]);
				str2num.put(input[1], val); //add new entries
				num2str.put(val, input[1]);
			}else if(input[0].equals("clear")){ //clear definitions
				str2num = new HashMap<>();
				num2str = new HashMap<>();
			}else if(input[0].equals("calc")){ //perform calculation
				boolean error = false;
				boolean add = true; //add or subtract
				int result = 0;
				for(int i = 1; i < input.length-1; i += 2){ 
					Integer val = str2num.get(input[i]);
					if(val == null) { error = true; break; }; //no key, so stop
					result += add ? val : -val; // add or subtract
					add = input[i+1].equals("+"); //set next operation
				}
				String r = num2str.get(result);
				out.write(line.substring(5).trim() + ' '); //print without calc
				if(error || r == null) out.write("unknown\n");
				else out.write(r + '\n');	
			}
		}
		out.flush();
	}
}