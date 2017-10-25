package com.headwire.automatedscreenrecorder.commands;

import com.headwire.automatedscreenrecorder.helpers.Arguments;
import com.headwire.automatedscreenrecorder.helpers.Command;
import com.headwire.automatedscreenrecorder.helpers.Context;

public class Stop extends Command {
    @Override
    public void execute(Context ctx, Arguments args) {
        try {
            ctx.getRecorder().stop();
        } catch (Exception e) {
            throw new RuntimeException("failed to stop recording", e);
        }
    }
}
