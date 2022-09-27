package cn.utoverse.utoverselib.util.config;

import lombok.Getter;

public enum ConfigFile {
    CONFIG("config", "config.yml");

    @Getter
    private String key;
    @Getter
    private String path;

    ConfigFile(String key, String path) {
        this.key = key;
        this.path = path;
    }
}
