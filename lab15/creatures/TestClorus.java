package creatures;

import huglife.Action;
import huglife.Direction;
import huglife.Impassible;
import huglife.Occupant;
import huglife.Empty;

import org.junit.Test;

import java.awt.Color;
import java.util.HashMap;

import static org.junit.Assert.*;

public class TestClorus {

    @Test
    public void testBasics() {
        Clorus c = new Clorus(2.0);
        assertEquals(2, c.energy(), 0.01);
        assertEquals(new Color(34, 0, 231), c.color());
        c.move();
        assertEquals(1.97, c.energy(), 0.01);
        c.move();
        assertEquals(1.94, c.energy(), 0.01);
        c.stay();
        assertEquals(1.93, c.energy(), 0.01);
        c.stay();
        assertEquals(1.92, c.energy(), 0.01);
    }

    @Test
    public void testReplicate() {
        Clorus c = new Clorus(1.0);
        Clorus baby = c.replicate();
        assertNotSame(c, baby);
        assertEquals(0.5, c.energy(), 0.01);
        assertEquals(0.5, baby.energy(), 0.01);
    }

    @Test
    public void testAttack() {
        Clorus c = new Clorus(1.0);
        Plip p = new Plip(0.2);
        c.attack(p);
        assertEquals(c.energy(), 1.2, 0.01);
    }

    @Test
    public void testChooseStay() {
        Clorus c = new Clorus(1.2);
        HashMap<Direction, Occupant> surrounded = new HashMap<>();
        surrounded.put(Direction.TOP, new Impassible());
        surrounded.put(Direction.BOTTOM, new Impassible());
        surrounded.put(Direction.LEFT, new Impassible());
        surrounded.put(Direction.RIGHT, new Impassible());

        Action actual = c.chooseAction(surrounded);
        Action expected = new Action(Action.ActionType.STAY);

        assertEquals(expected, actual);
    }

    @Test
    public void testChooseReplicate() {
        Clorus c = new Clorus(1.2);
        HashMap<Direction, Occupant> oneExit = new HashMap<>();
        oneExit.put(Direction.TOP, new Impassible());
        oneExit.put(Direction.BOTTOM, new Impassible());
        oneExit.put(Direction.LEFT, new Impassible());
        oneExit.put(Direction.RIGHT, new Empty());

        Action actual = c.chooseAction(oneExit);
        Action expected = new Action(Action.ActionType.REPLICATE, Direction.RIGHT);

        assertEquals(expected, actual);
    }

    @Test
    public void testChooseAttack() {
        Clorus c = new Clorus(1.2);
        HashMap<Direction, Occupant> oneExit = new HashMap<>();
        oneExit.put(Direction.TOP, new Impassible());
        oneExit.put(Direction.BOTTOM, new Impassible());
        oneExit.put(Direction.LEFT, new Plip(0.1));
        oneExit.put(Direction.RIGHT, new Empty());

        Action actual = c.chooseAction(oneExit);
        Action expected = new Action(Action.ActionType.ATTACK, Direction.LEFT);

        assertEquals(actual, expected);
    }

    @Test
    public void testChooseMove() {
        Clorus c = new Clorus(0.2);
        HashMap<Direction, Occupant> oneExit = new HashMap<>();
        oneExit.put(Direction.TOP, new Impassible());
        oneExit.put(Direction.BOTTOM, new Impassible());
        oneExit.put(Direction.LEFT, new Impassible());
        oneExit.put(Direction.RIGHT, new Empty());

        Action actual = c.chooseAction(oneExit);
        Action expected = new Action(Action.ActionType.MOVE, Direction.RIGHT);

        assertEquals(actual, expected);
    }

}

