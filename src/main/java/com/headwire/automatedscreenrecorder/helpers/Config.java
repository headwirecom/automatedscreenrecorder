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

    public boolean produceAudio() {
        return "true".equals(props.get("audio"));
    }

    public long getMaxWait() {
        long ret = Long.MAX_VALUE;
        String maxWait = (String)props.get("maxWait");
        if(maxWait != null) {
            ret = Integer.parseInt(maxWait);
            if(ret == 0) {
                ret = Long.MAX_VALUE;
            }
        }
        return ret;
    }
}
