package com.headwire.automatedscreenrecorder.helpers;

public class Arguments {

    private String command;
    private String modifier;
    private String data;

    public Arguments(String line) {

        int firstSpace = line.indexOf(' ');
        if (firstSpace < 0) {
            this.command = line;
        } else {
            this.command = line.substring(0, firstSpace);
            line = line.substring(firstSpace + 1);
            int secondSpace = line.indexOf(' ');
            if (secondSpace < 0) {
                modifier = line;
            } else {
                modifier = line.substring(0, secondSpace);
                data = line.substring(secondSpace + 1);
            }
        }

    }

    public String toString() {
        return "[" + command + "," + modifier +"," + data+ "]";
    }

    public String getCommand() {
        return command;
    }

    public String getModifier() {
        return modifier;
    }

    public String getData() {
        return data;
    }

    public void appendData(String line) {
        data += "\n";
        data += line;
    }

    public void cleanData() {
        data = data.substring(1, data.length() -1);
    }
}
