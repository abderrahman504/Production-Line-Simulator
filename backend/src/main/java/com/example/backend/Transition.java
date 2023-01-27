package com.example.backend;

public class Transition {
    int first;
    String color;
    int second;
    boolean machineToQueue;
    public Transition(int f, int s, String c, boolean m){
        first = f;
        second = s;
        color = c;
        machineToQueue = m;
    }
}
