package com.headwire.automatedscreenrecorder.commands;

import com.headwire.automatedscreenrecorder.helpers.Arguments;
import com.headwire.automatedscreenrecorder.helpers.Command;
import com.headwire.automatedscreenrecorder.helpers.Context;

public class DoubleClick extends Command {
    @Override
    public void execute(Context ctx, Arguments args) {
        try {
            ctx.getDriver().doubleclick(args.getData(), args.getModifier());
        } catch (Exception e) {
            throw new RuntimeException("failed to double click "+args.getCommand(), e);
        }
    }
}
