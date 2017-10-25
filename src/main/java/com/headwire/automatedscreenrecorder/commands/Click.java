package com.headwire.automatedscreenrecorder.commands;

import com.headwire.automatedscreenrecorder.helpers.Arguments;
import com.headwire.automatedscreenrecorder.helpers.Command;
import com.headwire.automatedscreenrecorder.helpers.Context;

public class Click extends Command {
    @Override
    public void execute(Context ctx, Arguments args) {
        try {
            ctx.getDriver().click(args.getData(), args.getModifier());
        } catch (Exception e) {
            throw new RuntimeException("failed to click "+args.getCommand(), e);
        }
    }
}
