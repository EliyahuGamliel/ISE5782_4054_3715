package primitives;

public class Material {
    Double3 kD, kS;
    int nShininess = 0;

    public Material() {
        this.kD = new Double3(0);
        this.kS = new Double3(0);
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
