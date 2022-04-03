package primitives;

public class Material {
    Double3 kD = Double3.ZERO, kS = Double3.ZERO;
    int nShininess = 0;
    

    public Material setkD(Double3 kD) {
        this.kD = kD;
        return this;
    }
    public Material setkS(Double3 kS) {
        this.kS = kS;
        return this;
    }
    public Material setnShininess(int nShininess) {
        this.nShininess = nShininess;
        return this;
    }
}
