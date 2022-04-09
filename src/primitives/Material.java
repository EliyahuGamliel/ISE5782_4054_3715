package primitives;

public class Material {
    public Double3 kD, kS;
    public Double3 kT, kR;
    public int nShininess = 0;

    public Material() {
        this.kD = new Double3(0);
        this.kS = new Double3(0);
        this.kT = new Double3(0);
        this.kR = new Double3(0);
    }

    public Material setkD(double kD) {
        this.kD = new Double3(kD);
        return this;
    }

    public Material setkS(double kS) {
        this.kS = new Double3(kS);
        return this;
    }

    public Material setkD(Double3 kD) {
        this.kD = kD;
        return this;
    }

    public Material setkS(Double3 kS) {
        this.kS = kS;
        return this;
    }

    public Material setShininess(int nShininess) {
        this.nShininess = nShininess;
        return this;
    }

    public Material setkT(Double3 kT) {
        this.kT = kT;
        return this;
    }

    public Material setkR(Double3 kR) {
        this.kR = kR;
        return this;
    }

    public Material setkT(double kT) {
        this.kT = new Double3(kT);
        return this;
    }

    public Material setkR(double kR) {
        this.kR = new Double3(kR);
        return this;
    }

    public Double3 getkD() {
        return kD;
    }
    public Double3 getkS() {
        return kS;
    }
    public int getnShininess() {
        return nShininess;
    }
}
