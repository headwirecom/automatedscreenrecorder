package com.headwire.automatedscreenrecorder.app;

import com.headwire.automatedscreenrecorder.commands.*;
import com.headwire.automatedscreenrecorder.helpers.Arguments;
import com.headwire.automatedscreenrecorder.helpers.Command;
import com.headwire.automatedscreenrecorder.helpers.Context;
import com.headwire.automatedscreenrecorder.texttospeech.AudioVideoMerge;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

/**
 * main screen recorder class
 */
public class AutomatedScreenRcorder {

    private static Logger LOG = LoggerFactory.getLogger(AutomatedScreenRcorder.class);

    /** list of all the available commands for the script language **/
    private static HashMap<String, Command> COMMANDS = new HashMap<>();

    /* initialize the list of commands */
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

    /** the context for the execution of this recording **/
    private final Context context;

    /** constructor
     *
     * @param context the context to use with this recording
     */
    public AutomatedScreenRcorder(Context context) {
        this.context = context;
    }

    /** run the screen recording based on the context.
     * this will first record the screen with the given script and then merge
     * all audio files into the resulting video file
     * **/
    public void run() {
        try {
            parseScript();

            // only process audio if we actually have audio files
            if(context.getAudioMarks().size() > 0) {
                new AudioVideoMerge().mergeAudioVideo(context);
            }

        } catch(Throwable t) {
            LOG.info("general error while recording");
            LOG.debug("general error while recording", t);
        }
        System.exit(0);
    }

    /** script parser */
    private void parseScript() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(context.getScript()));
        String line = br.readLine();
        while(line != null) {
            line = line.trim();
            if(line.length() == 0 || line.startsWith("#")) {
                // skip, comment or empty line
            } else {
                Arguments args = new Arguments(line);

                // check if this is a multi line argument
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
                    // make sure back ticks are removed from single line commands as well
                    if(args.getData().startsWith("`")) {
                        args.cleanData();
                    }
                    perform(args);
                } catch(Throwable t) {
                    LOG.info("not able to perform '{}'", line, t);
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

    /** execute the given argument (splits this into multiple commands is the command contains commas
     *
     * @param args
     */
    private void perform(Arguments args) {
        String commandName = args.getCommand();
        if(commandName.indexOf(",") > 0) {
            String[] commands = commandName.split(",");
            for(int i = 0; i < commands.length; i++) {
                args.setCommand(commands[i].trim());
                perform(args);
            }
            return;
        }
        Command command = COMMANDS.get(commandName);
        if(command != null) {
            command.execute(context, args);
        } else {
            LOG.info("unknown command '{}'", commandName);
        }
    }
}
