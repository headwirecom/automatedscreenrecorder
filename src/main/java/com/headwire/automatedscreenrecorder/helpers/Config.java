package com.headwire.automatedscreenrecorder.helpers;

import java.util.Properties;

public class Config {

    private final Properties props;

    public Config(Properties props) {
        this.props = props;
    }

    public String getDriverPath() {
        return (String) props.get("driver");
    }

    public String getFFMpegPath() {
        return (String) props.get("ffmpeg");
    }
}
