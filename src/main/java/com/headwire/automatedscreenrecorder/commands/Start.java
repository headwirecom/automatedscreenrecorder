package com.headwire.automatedscreenrecorder.commands;

import com.headwire.automatedscreenrecorder.helpers.Arguments;
import com.headwire.automatedscreenrecorder.helpers.Command;
import com.headwire.automatedscreenrecorder.helpers.Context;

public class Start extends Command {
    @Override
    public void execute(Context ctx, Arguments args) {
        try {
            String fileName = args.getModifier() == null ? "out" : args.getModifier();
            ctx.getRecorder().start("target", fileName);
            ctx.setOutputPath("target");
            ctx.setOutputFileName(fileName);
            ctx.markStart();
        } catch (Exception e) {
            throw new RuntimeException("failed to start recording", e);
        }
    }
}
