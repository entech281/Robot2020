/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frc.robot.utils;

import frc.robot.vision.VisionData;

/**
 *
 * @author aryan
 */
public class FixedStack {

    private int capacity;

    private VisionData[] stack;

    private int top = -1;

    public FixedStack(int size) {
        this.capacity = size;
        stack = new VisionData[capacity];
    }

    private void shift() {
        for (int i = 0; i < capacity - 1; i++) {
            stack[i] = stack[i + 1];
        }
    }

    public void add(VisionData data) {
        top += 1;
        if (top == capacity) {
            shift();
            top -= 1;
        }
        stack[top] = data;
    }

    public VisionData peek() {
        return stack[top];
    }

    public int size() {
        return top + 1;
    }

    public boolean isEmpty() {
        return top < 0;
    }
}
