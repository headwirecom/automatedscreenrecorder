package com.headwire.automatedscreenrecorder;

import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.headwire.automatedscreenrecorder.app.AutomatedScreenRcorder;
import com.headwire.automatedscreenrecorder.helpers.Config;
import com.headwire.automatedscreenrecorder.helpers.Context;
import com.headwire.automatedscreenrecorder.texttospeech.TextToSpeech;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Main {

    private static Logger LOG = LoggerFactory.getLogger(Main.class);

    public static void main(String args[]) {

        LOG.trace("startup of screen recorder");

        if(args.length == 0) {
            System.out.println("automatedscreenrecorder [propertiesFile] <script>");
        } else if(args.length == 1) {
            initAndRun(args[0], "asr.properties");
        } else if(args.length >= 2) {
            initAndRun(args[1], args[0]);
        }
    }

    private static void initAndRun(String script, String properties) {
        Properties props = new Properties();
        try {
            props.load(new FileInputStream(properties));
        } catch (IOException e) {
            LOG.info("not able to load properties file {}", properties);
            LOG.debug("error",e);
            return;
        }

        Config config = new Config(props);
        Context context = new Context(config);

        context.setScript(script);

        new AutomatedScreenRcorder(context).run();

    }

}
