package io.github.sjouwer.gammautils.common;

public abstract class AbstractGamma {
    public abstract void setGamma(double newValue, double valueChangesPerTick, boolean smoothTransition);

    public abstract void inteporlateGamma(double newValue, double valueChangePerTick);

    public abstract double gamma();
}
