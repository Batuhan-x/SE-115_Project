// Main.java — Students version
import java.io.*;
import java.util.*;

public class Main {
    static final int MONTHS = 12;
    static final int DAYS = 28;
    static final int COMMS = 5;
    static String[] commodities = {"Gold", "Oil", "Silver", "Wheat", "Copper"};
    static String[] months = {"January","February","March","April","May","June",
            "July","August","September","October","November","December"};
    static int[][][] profits = new int[12][28][5];

    // ======== REQUIRED METHOD LOAD DATA (Students fill this) ========
    public static void loadData() {




        for (int i = 0; i < months.length; i++) {

            String filename =   "Data_Files/"+months[i]+".txt";
            Scanner reader = null;

            try{
                 reader = new Scanner(new File(filename));
                 reader.nextLine();
                 while(reader.hasNextLine()){

                     String line = reader.nextLine();

                     String[]trimmer =line.split(",");

                     int day = Integer.parseInt(trimmer[0]);
                     String commodity = trimmer[1];
                     int profit = Integer.parseInt(trimmer[2]);

                     int commodityindex =-1;

                     for(int j =0;j<commodities.length;j++){
                         if(commodities[j].equals(commodity)){
                             commodityindex =j;
                             break;
                         }

                     }
                     if (commodityindex != -1 && day >= 1 && day <= 28) {
                         profits[i][day - 1][commodityindex] = profit;
                     }

                 }
            }catch(Exception e){


            }finally{
                if(reader!=null){
                    reader.close();
                }
            }




        }
    }

    // ======== 10 REQUIRED METHODS (Students fill these) ========

    public static String mostProfitableCommodityInMonth(int month) {
        int totalmax=Integer.MIN_VALUE;
        int total=0;
        int com=0;
        if(month>11 || month<0){
            return "INVALID_MONTH";
        } else{
            for(int i=0;i<commodities.length;i++){
                for(int j=0;j<profits[month].length;j++) {
                    total += profits[month][j][i];
                }
                if (total>totalmax){
                    totalmax=total;
                    com=i;
                }
                total=0;
            }
        }

        return commodities[com]+" "+totalmax;
    }

    public static int totalProfitOnDay(int month, int day) {
        int sum=0;
        if(month > 11 || month < 0 || day < 1 || day > 28){
            return -99999;
        }
        for (int i=0;i<commodities.length;i++){
            sum+=profits[month][day-1][i];
        }

        return sum;
    }

    public static int commodityProfitInRange(String commodity, int from, int to) {
        if ( from < 1 || to> 28 || from > to) {
            return -99999;
        }
        int commoditycheck=-1;
        for(int i=0;i<commodities.length;i++){
            if(commodities[i].equals(commodity)){
                commoditycheck=i;
                break;
            }
        }
        if(commoditycheck==-1){
            return -99999;
        }

        int total=0;
        for(int i=0;i<months.length;i++){
            for(int day=from;day<=to;day++){
                total+=profits[i][day-1][commoditycheck];
            }
        }

        return total;
    }

    public static int bestDayOfMonth(int month) {
        if(month>11 || month<0){
            return -1;
        }
        int day=0;
        int maxday=Integer.MIN_VALUE;
        int sum=0;
        for(int i=0;i<28;i++){
            for(int j =0;j<5;j++){
                sum+=profits[month][i][j];
            }
            if(sum>maxday){
                maxday=sum;
                day=i+1;
            }
            sum=0;
        }
        return day;
    }

    public static String bestMonthForCommodity(String comm){
        int commoditycheck =-1;
        for(int i=0;i<commodities.length;i++){
            if(comm.equals(commodities[i])){
                commoditycheck=i;
                break;
            }
        }
        if(commoditycheck==-1){
            return "INVALID_COMMODITY";
        }
        int maxmonth=Integer.MIN_VALUE;
        int sum=0;
        int monthrecorder=0;
        for(int i=0;i<months.length;i++){
            for(int j=0;j<28;j++){
                sum+=profits[i][j][commoditycheck];
            }
            if(sum>maxmonth){
                maxmonth=sum;
               monthrecorder=i;
            }
            sum=0;
        }
        return months[monthrecorder];
    }

    public static int consecutiveLossDays(String comm) {
        int commoditycheck =-1;
        for(int i=0;i<commodities.length;i++){
            if(comm.equals(commodities[i])){
                commoditycheck=i;
                break;
            }
        }
        if(commoditycheck==-1){
            return -1;
        }
        int consecutiveloss=0;
        int maxloss=0;
        for(int i=0;i<months.length;i++){
            for(int j=0;j<28;j++){
                if(profits[i][j][commoditycheck]<0){
                    consecutiveloss++;
                    if(maxloss<consecutiveloss){
                        maxloss=consecutiveloss;
                    }
                    }else{
                     consecutiveloss=0;
                }

            }
        }
        return maxloss;
    }

    public static int daysAboveThreshold(String comm, int threshold) {
        int commoditycheck =-1;
        for(int i=0;i<commodities.length;i++){
            if(comm.equals(commodities[i])){
                commoditycheck=i;
                break;
            }
        }
        if(commoditycheck==-1){
            return -1;
        }
        int daycounter=0;
        for(int i=0;i<months.length;i++) {
              for (int j = 0; j < 28; j++) {
                  if(profits[i][j][commoditycheck]>threshold){
                      daycounter++;
                  }


            }
        }

        return daycounter;
    }

    public static int biggestDailySwing(int month) {
        if(month>11 || month<0) {
            return -99999;
        }
        int totalday=0;
        int sumday=0;
        int absoloutedifference =0;
        for(int j =0;j<commodities.length;j++){
            sumday+=profits[month][0][j];

        }
        totalday=sumday;
        sumday=0;

        for(int i=1;i<28;i++) {
            for(int j =0;j<commodities.length;j++){
                sumday+=profits[month][i][j];

            }
            int diff =Math.abs(sumday-totalday);
            if (diff>absoloutedifference){
                absoloutedifference = diff;
            }
            totalday=sumday;
            sumday=0;
        }
        return absoloutedifference;
    }

    public static String compareTwoCommodities(String c1, String c2) {
        int c1check =-1;
        int c2check=-1;
        for(int i=0;i<commodities.length;i++) {
            if (c1.equals(commodities[i])) {
                c1check = i;

            }if(c2.equals(commodities[i])){
                c2check=i;
            }
        }
        if(c2check==-1 || c1check==-1){
            return "INVALID_COMMODITY";

        }
        int c1sum=0;
        int c2sum=0;
        int diff=0;
        for(int i=0;i<months.length;i++) {
            for (int j = 0; j < 28; j++) {
                c1sum+=profits[i][j][c1check];
                c2sum+=profits[i][j][c2check];
            }
        }
        diff=Math.abs(c1sum-c2sum);
        if(c1sum>c2sum){
            return c1+" is better by "+diff;
        }else if(c1sum<c2sum){
            return c2+" is better by "+diff;
        }else{
            return "Equal";
        }

    }

    public static String bestWeekOfMonth(int month) {

        if(month>11 || month<0) {
            return "INVALID_MONTH";
        }
        int Weekmax=Integer.MIN_VALUE;
        int sumweek=0;
        int weekcount=0;
        for(int i=0;i<28;i++){
            for(int j =0;j<commodities.length;j++){
                sumweek+=profits[month][i][j];
            }
            if((i+1)%7==0){
                if(sumweek>Weekmax) {
                    Weekmax = sumweek;
                    if(i<=6) {
                        weekcount = 1;
                    }else if(6<i && i<=13){
                        weekcount=2;
                    }else if(13<i && i<=20){
                        weekcount=3;
                    } else if (20<i && i<=27) {
                        weekcount=4;
                    }

                }
                sumweek=0;
            }

        }
        return "Week "+weekcount;
    }

    public static void main(String[] args) {
        loadData();
        System.out.println("Data loaded – ready for queries");
        System.out.println(profits[3][0][0]);

    }
}