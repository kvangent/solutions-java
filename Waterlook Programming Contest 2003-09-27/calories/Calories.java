import java.util.*;

public class Calories {
    static int[] g2C = {9, 4, 4, 4, 7}; //gram to calories
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while(true){
            double fat = 0.0, tot = 0.0;
            String food = sc.nextLine();
            if(food.equals("-")) break;
            while(true){
                if(food.equals("-")) break;
                double[] n = calc(food.split(" "));
                fat += n[0];
                tot += n[1];
                food = sc.nextLine();
            }
            System.out.println(Math.round((fat*100/tot)) + "%");
        }       
    }
    //Return an array [ %cal, totalCalories ]
    static double[] calc(String[] f){
        int[] per = new int[5]; int totPer = 100;
        double[] cal = new double[5]; double sumCal = 0.0;
        for(int i = 0; i < f.length; i++ ){
            int num = Integer.parseInt(f[i].substring(0, f[i].length()-1));
            char type = f[i].charAt(f[i].length()-1);
            switch(type){
            case 'g':
                num *= g2C[i];
                //fall through
            case 'C':
                cal[i] += num;
                sumCal += num;
                break;
            case '%':
                per[i] += num;
                totPer -= num;
            }
        }
        double totCal = sumCal*100/totPer;
        sumCal = 0;
        for(int i = 0; i < 5; i++){
            if(per[i] != 0) cal[i] = (per[i]*totCal)/100;
            sumCal += cal[i];
        }
        double[] r = {cal[0], sumCal};
        return r;
    }
}