public class Planet {
  public double xxPos;
  public double yyPos;
  public double xxVel;
  public double yyVel;
  public double mass;
  public String imgFileName;

  public Planet(double xP, double yP, double xV,
                double yV, double m, String img) {
    this.xxPos = xP;
    this.yyPos = yP;
    this.xxVel = xV;
    this.yyVel = yV;
    this.mass = m;
    this.imgFileName = img;
  }

  public Planet(Planet p) {
    this.xxPos = p.xxPos;
    this.yyPos = p.yyPos;
    this.xxVel = p.xxVel;
    this.yyVel = p.yyVel;
    this.mass = p.mass;
    this.imgFileName = p.imgFileName;
  }

  public double calcDistance(Planet p) {
    return Math.sqrt((this.xxPos - p.xxPos) * (this.xxPos - p.xxPos) +
                     (this.yyPos - p.yyPos) * (this.yyPos - p.yyPos));
  }

  public double calcForceExertedBy(Planet p) {
    final double G = 6.67e-11;
    return G * this.mass * p.mass / (this.calcDistance(p) * this.calcDistance(p));
  }

  public double calcForceExertedByX(Planet p) {
    return this.calcForceExertedBy(p) * (p.xxPos - this.xxPos) / this.calcDistance(p);
  }

  public double calcForceExertedByY(Planet p) {
    return this.calcForceExertedBy(p) * (p.yyPos - this.yyPos) / this.calcDistance(p);
  }

  public double calcNetForceExertedByX(Planet[] pa) {
    double netResult = 0.0;
    for (Planet p: pa) {
      if (!this.equals(p)) {
        netResult += this.calcForceExertedByX(p);
      }
    }
    return netResult;
  }

  public double calcNetForceExertedByY(Planet[] pa) {
    double netResult = 0.0;
    for (Planet p: pa) {
      if (!this.equals(p)) {
        netResult += this.calcForceExertedByY(p);
      }
    }
    return netResult;
  }

  public void update(double dt, double fX, double fY) {
    double aX = fX / this.mass;
    double aY = fY / this.mass;
    this.xxVel += aX * dt;
    this.yyVel += aY * dt;
    this.xxPos += this.xxVel * dt;
    this.yyPos += this.yyVel * dt;
  }

  public void draw() {
    StdDraw.picture(this.xxPos, this.yyPos, "images/" + this.imgFileName);
  }
}
