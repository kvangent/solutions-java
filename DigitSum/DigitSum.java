import java.io.*;

public class DigitSum {
	public static void main(String[] args) throws Exception {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));
		int n = Integer.parseInt(in.readLine());
		while(n --> 0){
			String[] input = in.readLine().split("\\s+");
			long a = Long.parseLong(input[0]);
			long b = Long.parseLong(input[1]);
			System.out.println(sum(b+1)-sum(a));
		}
	}
	// Sum for 0 to n-1 (integer XXXd)
	static long sum(long n){
		if(n == 0) return 0; 
		long d = n%10; //extract d from the right
		return d*(d-1)/2 // 0 to d-1
				+ d*digitSum(n/10) // XXX0 to XXXd (XXX happened d times)
				+ 10*sum(n/10) + (n/10) * 45; // 0 to XXX0 
	}
	//Return the sum of digits of n
	static long digitSum(long n){
		long rtn = 0;
		while(n > 0){ rtn += n%10; n /= 10; };
		return rtn;
	}
}
