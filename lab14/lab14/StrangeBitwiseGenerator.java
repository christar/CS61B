package lab14;

import lab14lib.Generator;

public class StrangeBitwiseGenerator implements Generator {
    private int period;
    private int state;
    public StrangeBitwiseGenerator(int period) {
        this.period = period;
        this.state = 0;
    }

    @Override
    public double next() {
        this.state += 1;
        int weirdState = this.state & (this.state >> 3) & (this.state >> 8) % this.period;
        return -1.0 + 2.0 * (weirdState % this.period) / this.period;
    }
}
