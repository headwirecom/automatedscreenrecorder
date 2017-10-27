package com.headwire.automatedscreenrecorder.helpers;

import java.util.Properties;

public class Config {

    private final Properties props;

    public Config(Properties props) {
        this.props = props;
    }

    private String getProp(String name) {
        return System.getProperty(name, props.getProperty(name));
    }

    public String getDriverPath() {
        return (String) getProp("driver");
    }

    public String getFFMpegPath() {
        return (String) getProp("ffmpeg");
    }

    public boolean produceAudio() {
        return getProp("audio") == null || "".equals(getProp("audio")) || "true".equals(getProp("audio"));
    }

    public long getMaxWait() {
        long ret = Long.MAX_VALUE;
        String maxWait = (String)getProp("maxWait");
        if(maxWait != null) {
            ret = Integer.parseInt(maxWait);
            if(ret == 0) {
                ret = Long.MAX_VALUE;
            }
        }
        return ret;
    }
}
