package com.headwire.automatedscreenrecorder.app;

import com.headwire.automatedscreenrecorder.commands.*;
import com.headwire.automatedscreenrecorder.helpers.Arguments;
import com.headwire.automatedscreenrecorder.helpers.Command;
import com.headwire.automatedscreenrecorder.helpers.Context;
import com.headwire.automatedscreenrecorder.texttospeech.AudioVideoMerge;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class AutomatedScreenRcorder {

    private static HashMap<String, Command> COMMANDS = new HashMap<>();

    static {
        COMMANDS.put("use", new Use());
        COMMANDS.put("open", new Open());
        COMMANDS.put("moveTo", new MoveTo());
        COMMANDS.put("click", new Click());
        COMMANDS.put("wait", new Wait());
        COMMANDS.put("input", new Input());
        COMMANDS.put("enter", new Enter());
        COMMANDS.put("acceptDialog", new Accept());
        COMMANDS.put("start", new Start());
        COMMANDS.put("stop", new Stop());
        COMMANDS.put("audio", new Audio());
        COMMANDS.put("switchTo", new SwitchTo());
        COMMANDS.put("doubleClick", new DoubleClick());
    }

    private final Context context;

    public AutomatedScreenRcorder(Context context) {
        this.context = context;
    }

    public void run() {
        try {
            parseScript();

            // only process audio if we actually have audio files
            if(context.getAudioMarks().size() > 0) {
                new AudioVideoMerge().mergeAudioVideo(context);
            }

        } catch(Throwable t) {
            System.out.println("error while recording");
            t.printStackTrace();
        }
        System.exit(0);
    }

    private void parseScript() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(context.getScript()));
        String line = br.readLine();
        while(line != null) {
            line = line.trim();
            if(line.length() == 0 || line.startsWith("#")) {
                // skip, comment or empty line
            } else {
                Arguments args = new Arguments(line);
                if(args.getData() != null && args.getData().startsWith("`")) {
                    line = br.readLine();
                    while(line != null) {
                        line = line.trim();
                        args.appendData(line);
                        if(line.endsWith("`")) {
                            args.cleanData();
                            break;
                        }
                    }
                }
                try {
                    perform(args);
                } catch(Throwable t) {
                    System.out.println("error while performing "+line);
                    t.printStackTrace();
                    br.close();
                    context.getDriver().quit();
                    return;
                }
            }
            if(line != null) {
                line = br.readLine();
            }
        }
        br.close();
        context.getDriver().quit();
    }

    private void perform(Arguments args) {
        String commandName = args.getCommand();
        Command command = COMMANDS.get(commandName);
        if(command != null) {
            command.execute(context, args);
        } else {
            System.out.println("unknown command "+commandName);
        }
    }
}
