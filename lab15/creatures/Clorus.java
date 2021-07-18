package creatures;
import huglife.Creature;
import huglife.Direction;
import huglife.Action;
import huglife.Occupant;
import huglife.HugLifeUtils;
import java.awt.Color;
import java.util.Map;
import java.util.List;

public class Clorus extends Creature {

    private double moveEnergyGain = -0.03;
    private double stayEnergyGain = -0.01;
    private double repEnergyGiven = 0.5;

    public Clorus(double e) {
        super("clorus");
        energy = e;
    }

    @Override
    public void move() {
        energy += moveEnergyGain;
    }

    @Override
    public void attack(Creature c) {
        energy += c.energy();
    }

    @Override
    public Clorus replicate() {
        Clorus baby = new Clorus(energy * repEnergyGiven);
        energy *= (1 - repEnergyGiven);
        return baby;
    }

    @Override
    public void stay() {
        energy += stayEnergyGain;
    }

    @Override
    public Action chooseAction(Map<Direction, Occupant> neighbors) {
        List<Direction> empties = getNeighborsOfType(neighbors, "empty");
        if (empties.size() == 0) {
            return new Action(Action.ActionType.STAY);
        }

        List<Direction> plips = getNeighborsOfType(neighbors, "plip");
        if (plips.size() > 0) {
            return new Action(Action.ActionType.ATTACK, HugLifeUtils.randomEntry(plips));
        }

        if (energy >= 1.0) {
            return new Action(Action.ActionType.REPLICATE, HugLifeUtils.randomEntry(empties));
        }

        return new Action(Action.ActionType.MOVE, HugLifeUtils.randomEntry(empties));
    }

    @Override
    public Color color() {
        return color(34, 0, 231);
    }
}
