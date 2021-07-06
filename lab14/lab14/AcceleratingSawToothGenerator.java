package lab14;

import lab14lib.Generator;

public class AcceleratingSawToothGenerator implements Generator {
    private int period;
    private double factor;
    private int state;
    private int step;

    public AcceleratingSawToothGenerator(int period, double factor) {
        this.period = period;
        this.factor = factor;
        this.state = 0;
        this.step = 0;
    }

    @Override
    public double next() {
        this.state += 1;
        this.step += 1;
        if (this.step % this.period == 0) {
            this.step = 0;
            this.period *= this.factor;
        }
        return 2.0 * this.step / this.period - 1.0;
    }
}
