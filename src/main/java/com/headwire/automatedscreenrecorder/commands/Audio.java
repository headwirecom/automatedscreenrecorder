package com.headwire.automatedscreenrecorder.commands;

import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.headwire.automatedscreenrecorder.helpers.Arguments;
import com.headwire.automatedscreenrecorder.helpers.AudioMark;
import com.headwire.automatedscreenrecorder.helpers.Command;
import com.headwire.automatedscreenrecorder.helpers.Context;
import com.headwire.automatedscreenrecorder.texttospeech.TextToSpeech;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;

public class Audio extends Command {

    public static String encodeTextToFileName(String fileName, String text) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(text.getBytes(StandardCharsets.UTF_8));
            StringBuffer hexString = new StringBuffer();

            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if(hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch(NoSuchAlgorithmException nsae) {
            return fileName;
        }
    }
    @Override
    public void execute(Context ctx, Arguments args) {
        if(!ctx.produceAudio()) {
            System.out.println(">>>>" + args.getData());
            return;
        }

        String fileName = "target/"+encodeTextToFileName(args.getModifier(), args.getData()) + ".mp3";

        System.out.println("writing audio for "+args.getData());

        ctx.markAudio(fileName, args.getData());

        System.out.println("/writing audio");

        try {
            TextToSpeech tts = new TextToSpeech(Region.getRegion(Regions.US_EAST_1));
            File file = new File(fileName);
            if(!file.exists()) {
                tts.transform(args.getData(), fileName);
            } else {
                System.out.println("skipping "+fileName+" as it already exists");
            }
        } catch(Throwable t) {
            throw new RuntimeException("failed to convert text to speech", t);
        }

    }
}
