package com.headwire.automatedscreenrecorder.commands;

import com.headwire.automatedscreenrecorder.helpers.Arguments;
import com.headwire.automatedscreenrecorder.helpers.Command;
import com.headwire.automatedscreenrecorder.helpers.Context;

public class SwitchTo extends Command {
    @Override
    public void execute(Context ctx, Arguments args) {
        try {
            System.out.println(System.currentTimeMillis());
            ctx.getDriver().switchTo(args.getModifier());
            System.out.println(System.currentTimeMillis());
        } catch (Exception e) {
            throw new RuntimeException("failed to switch to iframe "+args.getModifier(), e);
        }
    }
}
