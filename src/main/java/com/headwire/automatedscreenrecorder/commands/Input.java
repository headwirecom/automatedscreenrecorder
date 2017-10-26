package com.headwire.automatedscreenrecorder.commands;

import com.headwire.automatedscreenrecorder.helpers.Arguments;
import com.headwire.automatedscreenrecorder.helpers.Command;
import com.headwire.automatedscreenrecorder.helpers.Context;
import org.openqa.selenium.Keys;

public class Input extends Command {
    @Override
    public void execute(Context ctx, Arguments args) {
        try {
            String text = args.getModifier();
            if(args.getData() != null) {
                text += " " + args.getData();
            }
            for(int i = 0; i < text.length(); i++) {
                char ch = text.charAt(i);
                if(ch == '\\') {
                    i++;
                    if(i < text.length()) {
                        char modifier = text.charAt(i);
                        char send = 0;
                        switch(modifier) {
                            case '\\': send = '\\'; break;
                            case 't': send = '\t'; break;
                            case 'h':
                                ctx.getDriver().sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME));
                                break;
                            default:
                                send = modifier;
                        }
                        if(send != 0) {
                            ctx.getDriver().input(""+send);
                        }
                    }
                } else {
                    ctx.getDriver().input(""+ch);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("failed to click "+args.getCommand(), e);
        }
    }
}
