package com.company;

import java.util.Random;

public class ArrClass {
    private final int dim;
    private final int threadNum;
    public final int[] arr;

    public ArrClass(int dim, int threadNum) {
        this.dim = dim;
        arr = new int[dim];
        this.threadNum = threadNum;
        for(int i = 0; i < dim; i++){
            arr[i] = i;
        }
        Random random = new Random();
        arr[random.nextInt(0,dim)]*=-1;
    }

    public long partMin(int startIndex, int finishIndex){
        long min =Long.MAX_VALUE;
        for(int i = startIndex; i < finishIndex; i++){
            if(min>arr[i]){
                min=arr[i];
            }
        }
        return min;
    }

    private long min = 0;

    synchronized private long getMin() {
        while (getThreadCount()<threadNum){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return min;
    }

    synchronized public void collectMin(long min){
        if(this.min>min){
        this.min += min;
        }
    }

    private int threadCount = 0;
    synchronized public void incThreadCount(){
        threadCount++;
        notify();
    }

    private int getThreadCount() {
        return threadCount;
    }

    public long threadMin(){
        ThreadMin[] threadMins = new ThreadMin[threadNum];
        int len = dim/threadNum;
        for (int i=0;i<threadNum-1;i++) {
            threadMins[i] = new ThreadMin(len*i, len*(i+1), this);
            threadMins[i].start();
        }
        threadMins[threadNum-1]= new ThreadMin(len*(threadNum-1), dim, this);

        threadMins[threadNum-1].start();

        return getMin();
    }
}
