import java.io.*;

public class IntegerLists {
	public static void main(String[] args) throws Exception {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));
		int tc = Integer.parseInt(in.readLine());
		while (tc --> 0){
			char[] prog = in.readLine().toCharArray();
			int length = Integer.parseInt(in.readLine());
			char[] list = in.readLine().toCharArray();
			int f = 0, b = 0; //# of items to drop off front/back
			boolean rev = false; //front = false, back = true
			for(char c : prog){ //count number of items to take off f + b
				if(c == 'R') rev = !rev; //reverse side
				else{
					if(!rev) f++;
					else b++;
				}
			}
			if(f+b > length){ // if there aren't enough items to drop
				out.write("error\n");
				continue;
			}else if (f+b == length){ // if all the items are dropped
				out.write("[]\n");
				continue;
			}
			int fIdx = 0, bIdx = list.length-1;
			while (f-- > 0) { // for each chunk from the front end
				fIdx++; // move into the next chunk
				while (list[fIdx] != ',') fIdx++; //find the end of the chunk
			}
			while (b-- > 0) { // for each chunk on the back end
				bIdx--; //move into the next chunk
				while (list[bIdx] != ',') bIdx--; // find the end of the chunk
			}
			char[] rtn = new char[bIdx+1-fIdx];
			if(!rev){ //If not reversed, copy forward
				int i = fIdx+1, j = 1;
				while(i < bIdx) rtn[j++] = list[i++];
			}else{ //if revered, copy chunks in reverse order
				int j = 1, i = bIdx-1;
				while(i > fIdx){
					int oldI = i; //end of the chunk
					while(list[i] != ',' && list[i] != '[') i--; //find start of chunk
					for(int k = i+1; k <= oldI; k++) rtn[j++] = list[k]; //copy chunk
					rtn[j++] = ',';
					i--; //one less chunk to do
				}
			}
			rtn[0] = '['; 
			rtn[bIdx-fIdx] = ']';
			out.write(rtn);
			out.write('\n');			
		}
		out.flush();
	}
}
