package io.github.sjouwer.gammautils.client;

import io.github.sjouwer.gammautils.common.AbstractGamma;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.SimpleOption;

import java.util.Timer;
import java.util.TimerTask;

public class ClientGamma extends AbstractGamma {

    public static ClientGamma INSTANCE = new ClientGamma();
    private Timer timerInterpolation;

    @Override
    public void setGamma(double newValue, double valueChangesPerTick, boolean smoothTransition) {
        if (timerInterpolation != null) {
            timerInterpolation.cancel();
            timerInterpolation = null;
        }

        if (smoothTransition) {
            this.inteporlateGamma(newValue, valueChangesPerTick);
            return;
        }

        MinecraftClient.getInstance().options.getGamma().setValue(newValue);
    }

    @Override
    public void inteporlateGamma(double newValue, double valueChangePerTick) {
        this.timerInterpolation = new Timer();
        this.timerInterpolation.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                final SimpleOption<Double> GAMMA = MinecraftClient.getInstance().options.getGamma();
                double nextValue = GAMMA.getValue() + valueChangePerTick;
                if ((valueChangePerTick > 0 && nextValue >= newValue) ||
                        (valueChangePerTick < 0 && nextValue <= newValue)) {
                    this.cancel();
                    MinecraftClient.getInstance().options.getGamma().setValue(newValue);
                } else {
                    MinecraftClient.getInstance().options.getGamma().setValue(newValue);
                }
            }
        }, 0, 10);
    }

    @Override
    public double gamma() {
        return MinecraftClient.getInstance().options.getGamma().getValue();
    }
}
