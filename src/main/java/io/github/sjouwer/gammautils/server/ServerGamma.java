package io.github.sjouwer.gammautils.server;

import io.github.sjouwer.gammautils.common.AbstractGamma;
import io.github.sjouwer.gammautils.common.network.NetworkManager;
import io.github.sjouwer.gammautils.server.config.ConfigJsonLoader;
import io.github.sjouwer.gammautils.server.listener.ServerListener;
import io.github.sjouwer.gammautils.server.network.PacketS2CSetGamma;

public class ServerGamma extends AbstractGamma {

    public static ServerGamma INSTANCE = new ServerGamma();

    @Override
    public void setGamma(double newValue, double valueChangesPerTick, boolean smoothTransition) {
        if (smoothTransition) {
            if (newValue < ConfigJsonLoader.data().currentGamma()) {
                valueChangesPerTick *= -1;
            }
            this.inteporlateGamma(newValue, valueChangesPerTick);
            return;
        }
        ConfigJsonLoader.data().setGamma(newValue);
        NetworkManager.sendToAll(new PacketS2CSetGamma(newValue, 0, false), ServerListener.server());
    }

    public void increaseGamma(double value) {
        double newValue = ConfigJsonLoader.data().currentGamma();
        if (value == 0) {
            newValue += ConfigJsonLoader.data().gammaStep();
        } else {
            newValue += value;
        }
        this.setGamma(newValue, 0, false);
    }

    public void decreaseGamma(double value) {
        double newValue = ConfigJsonLoader.data().currentGamma();
        if (value == 0) {
            newValue -= ConfigJsonLoader.data().gammaStep();
        } else {
            newValue -= value;
        }
        this.setGamma(newValue, 0, false);
    }

    @Override
    public void inteporlateGamma(double newValue, double valueChangePerTick) {
        ConfigJsonLoader.data().setGamma(newValue);
        NetworkManager.sendToAll(new PacketS2CSetGamma(newValue, valueChangePerTick, true), ServerListener.server());
    }

    public void toggleGamma(boolean value) {
        double currentValue;
        if (value) {
            currentValue = ConfigJsonLoader.data().currentGamma();
        } else {
            currentValue = 100D;
        }
        this.setGamma(currentValue, ConfigJsonLoader.data().transitionSpeed(), true);
    }

    @Override
    public double gamma() {
        return ConfigJsonLoader.data().currentGamma();
    }
}
