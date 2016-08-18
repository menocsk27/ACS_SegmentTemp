import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.image.BufferedImage;
import  java.awt.image.DataBufferByte;
import java.io.File;
import java.lang.Math;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.highgui.Highgui;
import org.opencv.highgui.VideoCapture;

public class HelloCV {

	public static void main(String[] args) {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    	VideoCapture camera = new VideoCapture("Dissolve1-15.mp4");
    	
    	if(!camera.isOpened()){	
    		System.out.println("Error");
    	}
    	else {
    		Mat frame = new Mat();
    	    while(true){
    	    	if (camera.read(frame)){
    	    		System.out.println("Frame Obtained");
    	    		System.out.println("Captured Frame Width " + 
    	    		frame.width() + " Height " + frame.height());
    	    		Highgui.imwrite("camera.jpg", frame);
    	    		System.out.println("OK");
    	    		break;
    	    	}
    	    }
    	    
    	}
    	camera.release();
	}
}
