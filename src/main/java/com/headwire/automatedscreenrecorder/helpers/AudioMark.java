package com.headwire.automatedscreenrecorder.helpers;

public class AudioMark {

    private final long time;
    private final String text;
    private final String file;

    public AudioMark(long time, String file, String text) {
        this.time = time;
        this.file = file;
        this.text = text;
    }

    public String getFile() {
        return file;
    }

    public String getText() {
        return text;
    }

    public long getTime() {
        return time;
    }
}
