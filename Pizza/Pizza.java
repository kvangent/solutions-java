import java.io.*;

public class Pizza {
	public static void main(String[] args) throws Exception {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));
		int tc = Integer.parseInt(in.readLine());
		while(tc --> 0){
			String[] input = in.readLine().split("\\s+");
			int col = Integer.parseInt(input[0]);
			int row = Integer.parseInt(input[0]); 
			int[] colSum = new int[col];
			int[] rowSum = new int[row];
			for(int r = 0; r < row; r++){ //take in input
				input = in.readLine().split("\\s+"); 
				for(int c = 0; c < col; c++){
					int v = Integer.parseInt(input[c]);
					colSum[c] += v;
					rowSum[r] += v;
				}
			}
			//Calculate best row
			int bestRow = Integer.MAX_VALUE;
			for(int r = 0; r < row; r++) {
				int sum = 0;
				for(int c = 0; c < col; c++)
					sum += r > c ? (r-c)*rowSum[c] : (c-r)*rowSum[c];
				if (sum < bestRow) bestRow = sum;
			}
			int bestCol = Integer.MAX_VALUE;
			for(int c = 0; c < col; c++){
				int sum = 0; 
				for(int r = 0; r < row; r++)
					sum += c > r ? (c-r)*colSum[r] : (r-c)*colSum[r];
				if (sum < bestCol) bestCol = sum;
			}
			out.write((bestRow+bestCol) + " blocks\n");
		}
		out.flush();
	}
}