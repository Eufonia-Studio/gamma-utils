package io.github.sjouwer.gammautils.server.config;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static io.github.sjouwer.gammautils.server.config.ConfigJsonLoader.GSON;

public class ConfigData {

    private double currentGama;
    private int transitionSpeed;
    private int gammaStep;

    public ConfigData() {
        this.currentGama = 100D;
        this.transitionSpeed = 4500;
        this.gammaStep = 10;
    }

    public void setGamma(double newValue) {
        this.currentGama = newValue;
        ConfigJsonLoader.saveConfig();
    }

    public void setTransitionSpeed(int transitionSpeed) {
        this.transitionSpeed = transitionSpeed;
        ConfigJsonLoader.saveConfig();
    }

    public void setGammaStep(int gammaStep) {
        this.gammaStep = gammaStep;
        ConfigJsonLoader.saveConfig();
    }

    public double currentGamma() {
        return this.currentGama;
    }

    public int gammaStep() {
        return this.gammaStep;
    }

    public int transitionSpeed() {
        return this.transitionSpeed;
    }

    public ConfigData toJson() {
        return this;
    }

    public static ConfigData fromJson(Path path) {
        try {
            return GSON.fromJson(Files.readString(path), ConfigData.class);
        } catch (IOException e) {
            e.printStackTrace();
            return new ConfigData();
        }
    }

    public static ConfigData read(Path path) {
        try {
            return GSON.fromJson(Files.readString(path), ConfigData.class);
        } catch (Exception e) {
            e.printStackTrace();
            return new ConfigData();
        }
    }
}
