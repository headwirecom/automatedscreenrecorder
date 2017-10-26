package com.headwire.automatedscreenrecorder.helpers;

import com.headwire.automatedscreenrecorder.webdriver.Driver;
import com.headwire.automatedscreenrecorder.recorder.Recorder;

import java.util.ArrayList;

public class Context {

    private static Driver DRIVER = Driver.getInstance();
    private static Recorder RECORDER = new Recorder();

    private long startTime = 0;
    private final Config config;
    private String script;

    private ArrayList<AudioMark> audioMarks = new ArrayList<>();
    private String outputFileName;
    private String outputPath;

    public Context(Config config) {
        this.config = config;
    }

    public void setScript(String script) {
        this.script = script;
    }

    public String getScript() {
        return script;
    }

    public Driver getDriver() {
        return DRIVER;
    }

    public String getDriverPath() {
        return config.getDriverPath();
    }

    public Recorder getRecorder() {
        return RECORDER;
    }

    public void markStart() {
        this.startTime = System.currentTimeMillis();
    }

    public void markAudio(String file, String text) {
        audioMarks.add(new AudioMark(System.currentTimeMillis() - startTime, file, text));
    }

    public ArrayList<AudioMark> getAudioMarks() {
        return audioMarks;
    }

    public String getFFMpegPath() {
        return config.getFFMpegPath();
    }

    public String getOutputPath() {
        return outputPath;
    }

    public String getOutputFileName() {
        return outputFileName;
    }

    public void setOutputPath(String outputPath) {
        this.outputPath = outputPath;
    }

    public void setOutputFileName(String outputFile) {
        this.outputFileName = outputFile;
    }

    public AudioMark getLastAudioMark() {
        return audioMarks.get(audioMarks.size() - 1);
    }

    public boolean produceAudio() {
        return config.produceAudio();
    }

    public long getMaxWait() {
        return config.getMaxWait();
    }
}
