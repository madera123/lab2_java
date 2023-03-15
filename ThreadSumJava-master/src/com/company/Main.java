package com.company;
public class Main {

    public static void main(String[] args) {
        int dim = 100000000;
        int threadNum =4 ;
        long oneTime=0;
        long moreTime=0;
        int col=100;
        for (int i=0; i<col;i++) {
            long time = System.nanoTime();
            ArrClass arrClass = new ArrClass(dim, threadNum);
            long minIndex = arrClass.partMin(0, dim);
            time = System.nanoTime() - time;
            oneTime+=time;
            //System.out.println(minIndex + " time:" + time);
            time = System.nanoTime();
            minIndex = arrClass.threadMin();
            time = System.nanoTime() - time;
            //System.out.println(minIndex + " time:" + time);
            moreTime+=time;
        }
        System.out.println("one Thread average  time:" + (oneTime/col));
        System.out.println("more Thread average time:" + (moreTime/col));
    }
}
