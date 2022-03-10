package com.flink.utils;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

public class ConfigReader {
    private Config app_config;

    public void loadConfig(String configFile){
        this.app_config = ConfigFactory.parseResources(configFile);
    }

    public String getConfigString(String propertyPath){
        return app_config.getString(propertyPath);
    }

    public Boolean getConfigBoolean(String propertyPath){
        return app_config.getBoolean(propertyPath);
    }
}