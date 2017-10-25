package com.headwire.automatedscreenrecorder.recorder;

import org.monte.media.Format;
import org.monte.media.FormatKeys.*;
import org.monte.media.math.Rational;
import org.monte.screenrecorder.ScreenRecorder;

import java.awt.*;
import java.io.File;

import static org.monte.media.FormatKeys.EncodingKey;
import static org.monte.media.FormatKeys.FrameRateKey;
import static org.monte.media.FormatKeys.KeyFrameIntervalKey;
import static org.monte.media.FormatKeys.MIME_AVI;
import static org.monte.media.FormatKeys.MediaTypeKey;
import static org.monte.media.FormatKeys.MimeTypeKey;
import static org.monte.media.VideoFormatKeys.*;


public class Recorder {

	private static ScreenRecorder screenRecorder;
	public static String videoname;
	public static String path;

	private void initScreenRecorder(String filepath, String filename) throws Exception {
		videoname = filename;
		path = filepath;
		File file = new File(path);

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int width = screenSize.width;
		int height = screenSize.height;

		Rectangle captureSize = new Rectangle(0,0, width, height);

		GraphicsConfiguration gc = GraphicsEnvironment
				.getLocalGraphicsEnvironment()
				.getDefaultScreenDevice()
				.getDefaultConfiguration();

		Recorder.screenRecorder = new CustomScreenRecorder(gc, captureSize,
				new Format(MediaTypeKey, MediaType.FILE, MimeTypeKey, MIME_AVI),
				new Format(MediaTypeKey, MediaType.VIDEO, EncodingKey, ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE,
						CompressorNameKey, ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE,
						DepthKey, 24, FrameRateKey, Rational.valueOf(15),
						QualityKey, 1.0f,
						KeyFrameIntervalKey, 15 * 60),
				new Format(MediaTypeKey, MediaType.VIDEO, EncodingKey, "black",
						FrameRateKey, Rational.valueOf(30)),
				null, file, videoname);
	}

	public void start(String path, String filename) throws Exception {
		initScreenRecorder(path, filename);
		screenRecorder.start();
	}

	public void stop() throws Exception{
		screenRecorder.stop();
	}
}
