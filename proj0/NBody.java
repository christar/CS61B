public class NBody {
    public static double readRadius(String fileName) {
        In in = new In(fileName);
        in.readInt();
        return in.readDouble();
    }

    public static Planet[] readPlanets(String fileName) {
        In in = new In(fileName);
        int planetNumber = in.readInt();
        in.readDouble();
        Planet[] planetArray = new Planet[planetNumber];
        for (int i = 0; i < planetNumber && !in.isEmpty(); i++) {
            planetArray[i] = new Planet(in.readDouble(), in.readDouble(), in.readDouble(),
                                        in.readDouble(), in.readDouble(), in.readString());
        }
        return planetArray;
    }

    public static void main(String[] args) {
        /* Step1: collect all needed input */
        double T = Double.parseDouble(args[0]);
        double dt = Double.parseDouble(args[1]);
        String filename = args[2];
        double radius = NBody.readRadius(filename);
        Planet[] planets = NBody.readPlanets(filename);

        /* Step2: draw the background */
        StdDraw.setScale(-radius, radius);
        StdDraw.clear();
        StdDraw.picture(0, 0, "images/starfield.jpg");
        StdDraw.show();

        /* Step3: draw one planet */
        /* Step4: draw all planets */
        for (Planet p: planets) {
            p.draw();
        }

        StdDraw.enableDoubleBuffering();
        double time = 0.0;
        while (time < T) {
            double[] xForces = new double[planets.length];
            double[] yForces = new double[planets.length];

            /* store all net x and y forces */
            for (int i = 0; i < planets.length; i++) {
                xForces[i] = planets[i].calcNetForceExertedByX(planets);
                yForces[i] = planets[i].calcNetForceExertedByY(planets);
            }

            /* apply forces on each planet */
            for (int i = 0; i < planets.length; i++) {
                planets[i].update(dt, xForces[i], yForces[i]);
            }

            StdDraw.clear();
            StdDraw.picture(0, 0, "images/starfield.jpg");

            for (Planet p: planets) {
                p.draw();
            }

            StdDraw.show();
            StdDraw.pause(10);
            time += dt;
        }

        /* print final state of the universe */
        StdOut.printf("%d\n", planets.length);
        StdOut.printf("%.2e\n", radius);
        for (int i = 0; i < planets.length; i++) {
            StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                    planets[i].xxPos, planets[i].yyPos, planets[i].xxVel,
                    planets[i].yyVel, planets[i].mass, planets[i].imgFileName);
        }
    }
}