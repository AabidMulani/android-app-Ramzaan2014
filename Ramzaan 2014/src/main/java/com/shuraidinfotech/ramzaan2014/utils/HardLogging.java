package com.shuraidinfotech.ramzaan2014.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by AABID on 18/5/14.
 */
public class HardLogging {

	static File myFile;
	static BufferedWriter myOutWriter;

	public static void LogThis(String tag,String stats) {
		try {
			SimpleDateFormat s = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
			String format = s.format(new Date());
			myOutWriter  = new BufferedWriter(new FileWriter(myFile,true));
			myOutWriter.append(format+" ["+tag+"] : "+stats);
			Logger.v("Log:- ", stats);
			myOutWriter.newLine();
			myOutWriter.flush();
			myOutWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void init() {
		try {
			myFile = new File("/sdcard/LogActivities.dsm");
			if(!myFile.exists())
				myFile.createNewFile();
//			LogThis("\n\n----------NEW START OF APPLICATION-------------");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
