/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frc.robot;

import frc.robot.vision.VisionData;
import frc.robot.utils.FixedStack;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 *
 * @author aryan
 */
public class TestFixedStack {

    FixedStack stack;

    @Test
    public void addingAbility() {
        VisionData add = new VisionData(false, 0, 0, 0);
        stack = new FixedStack(3);
        stack.add(add);
        assertEquals(stack.peek(), add);
    }

    @Test
    public void testOverflow() {
        stack = new FixedStack(3);
        VisionData add = new VisionData(false, 0, 0, 0);
        VisionData add2 = new VisionData(false, 1, 1, 1);
        VisionData add3 = new VisionData(false, 2, 2, 2);
        VisionData add4 = new VisionData(true, -1, -1, -1);

        stack.add(add);
        stack.add(add2);
        stack.add(add3);
        assertEquals(stack.peek(), add3);

        stack.add(add4);
        assertEquals(stack.peek(), add4);
        assertEquals(stack.size(), 3);

    }
}
