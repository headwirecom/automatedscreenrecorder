package com.headwire.automatedscreenrecorder.recorder;

import org.monte.media.Format;
import org.monte.media.Registry;
import org.monte.screenrecorder.ScreenRecorder;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class CustomScreenRecorder extends ScreenRecorder {

	 private String name;
	 
	    public CustomScreenRecorder(GraphicsConfiguration cfg,
                                    Rectangle captureArea, Format fileFormat, Format screenFormat,
                                    Format mouseFormat, Format audioFormat, File movieFolder,
                                    String name) throws IOException, AWTException {
	         super(cfg, captureArea, fileFormat, screenFormat, mouseFormat,
	                  audioFormat, movieFolder);
	         this.name = name;
	    }
	 
	    @Override
	    protected File createMovieFile(Format fileFormat) throws IOException {
	          if (!movieFolder.exists()) {
	                movieFolder.mkdirs();
	          } else if (!movieFolder.isDirectory()) {
	                throw new IOException("\"" + movieFolder + "\" is not a directory.");
	          }
	                         
	          return new File(movieFolder, name  + "."
	                  + Registry.getInstance().getExtension(fileFormat));
	    }
}
