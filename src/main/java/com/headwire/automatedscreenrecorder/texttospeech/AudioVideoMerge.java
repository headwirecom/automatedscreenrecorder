package com.headwire.automatedscreenrecorder.texttospeech;

import com.headwire.automatedscreenrecorder.Main;
import com.headwire.automatedscreenrecorder.helpers.AudioMark;
import com.headwire.automatedscreenrecorder.helpers.Context;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AudioVideoMerge {
	
//	private static ArrayList<String> audioInput = new ArrayList<>();
//	private static int pointer = 0;
//	private static ArrayList<Long> seconds = new ArrayList<>();

	public boolean mergeAudioVideo(Context ctx) {

		/**** code wie er im cmd eingegeben wird
		 * 
		 * String[] exeCmd = new String[]{"C:\\Users\\st_pa\\ffmpeg\\ffmpeg-3.3.3-win64-static\\bin\\ffmpeg", "-i", "C:\\Users\\st_pa\\ffmpeg\\ffmpeg-3.3.3-win64-static\\bin\\audio.wav", "-i", "C:\\Users\\st_pa\\ffmpeg\\ffmpeg-3.3.3-win64-static\\bin\\video.mp4" ,"-acodec", "copy", "-vcodec", "copy", "C:\\Users\\st_pa\\ffmpeg\\ffmpeg-3.3.3-win64-static\\bin\\eclipsefinalmpeeeee.mp4"};
		 * ffmpeg -i test_video.avi -i beep.mp3 -i beeps.mp3 -filter_complex "[1]adelay=5000[s2]; [2]adelay=10300[s3]; [s2][s3]amix=2[mixout]" -map 0:v -map [mixout] -c:v copy result.avi
		 */

		String ffmpegPath = ctx.getFFMpegPath();
		String outputPath = ctx.getOutputPath();
		String outputFileName = ctx.getOutputFileName();

		String[] begin = {ffmpegPath, "-i", outputPath + "/" + outputFileName + ".avi"};
		ArrayList<String> exeCmd = new ArrayList<>();
		exeCmd.addAll(Arrays.asList(begin));

		List<AudioMark> marks = ctx.getAudioMarks();
		ArrayList<String> audioInputs = new ArrayList<>();

		for (AudioMark mark: marks) {
			audioInputs.add("-i");
			audioInputs.add(mark.getFile());
		}

		exeCmd.addAll(audioInputs);
		exeCmd.add("-filter_complex");

		StringBuilder sb = new StringBuilder();
		sb.append('"');
		int index = 0;
		for (AudioMark mark: marks) {
			sb.append('[');
			sb.append(index + 1);
			sb.append(']');
			sb.append("adelay=");
			sb.append(mark.getTime());
			sb.append(",volume=");
			sb.append(marks.size() - index);
			sb.append("[s");
			sb.append(index +1);
			sb.append("];");
			index++;
		}

		for(int i = 1; i <= marks.size(); i++) {
			sb.append("[s");
			sb.append(i);
			sb.append(']');
		}

		sb.append("amix=" + marks.size() + "[mixout]\"");
		exeCmd.add(sb.toString());

		String[] end = {"-map", "0:v", "-map", "[mixout]", "-c:v", "copy"};
		
		exeCmd.addAll(Arrays.asList(end));

		File out = new File(outputPath + "/final-"+outputFileName + ".avi");
		if(out.exists()) {
			out.delete();
		}
		exeCmd.add(out.toString());

		System.out.println(exeCmd);

		String[] command = exeCmd.toArray(new String[exeCmd.size()]);

		for(int i = 0; i < command.length; i++) {
			if(i > 0) System.out.print(" ");
			System.out.print(command[i]);
		}
		System.out.println();

		ProcessBuilder pb = new ProcessBuilder(command);
		boolean exeCmdStatus = executeCMD(pb);

//		return true;
		return exeCmdStatus;
	} //End doSomething Function
	
//	public void collectAudios(String var) {
//		audioInput.add("-i");
//		pointer++;
//		audioInput.add(pointer, var);
//		pointer++;
//	}
	
	private boolean executeCMD(ProcessBuilder pb) {
		pb.redirectOutput(ProcessBuilder.Redirect.INHERIT);
		pb.redirectErrorStream(true);
		Process p = null;

		try {
			p = pb.start();
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("oops");
			p.destroy();
			return false;
		}
		// wait until the process is done
		try {
			p.waitFor();
		} catch (InterruptedException e) {
			e.printStackTrace();
			System.out.println("woopsy");
			p.destroy();
			return false;
		}
		return true;
	}// End function executeCMD
}
