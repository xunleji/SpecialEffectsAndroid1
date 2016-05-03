/*
Copyright 2009 David Revell

This file is part of SwiFTP.

SwiFTP is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

SwiFTP is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with SwiFTP.  If not, see <http://www.gnu.org/licenses/>.
*/

package com.example.specialeffectsandroid.remotephone;

/**
 * Since STOR and APPE are essentially identical except for append vs truncate,
 * the common code is in this class, and inherited by CmdSTOR and CmdAPPE.
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.util.Log;


abstract public class CmdAbstractStore extends FtpCmd {
	public static final String message = "TEMPLATE!!"; 
	
	public CmdAbstractStore(SessionThread sessionThread, String input) {
		super(sessionThread, CmdAbstractStore.class.toString());
	}
	
	public void doStorOrAppe(String param, boolean append) {
		myLog.l(Log.DEBUG, "STOR/APPE executing with append=" + append);
		File storeFile = inputPathToChrootedFile(sessionThread.getWorkingDir(), param);
		
		String errString = null;
		FileOutputStream out = null;
		storing: {
			// Get a normalized absolute path for the desired file
			if(violatesChroot(storeFile)) {
				errString = "550 Invalid name or chroot violation\r\n";
				break storing;
			}
			if(storeFile.isDirectory()) {
				errString = "451 Can't overwrite a directory\r\n";
				break storing;
			}

			try {
				if(storeFile.exists()) {
					if(!append) {
						if(!storeFile.delete()) {
							errString = "451 Couldn't truncate file\r\n";
							break storing;
						}
						// Notify other apps that we just deleted a file
						Util.deletedFileNotify(storeFile.getPath());
					}
				}
				out = new FileOutputStream(storeFile, append);
			} catch(FileNotFoundException e) {
				try {
					errString = "451 Couldn't open file \"" + param + "\" aka \"" + 
						storeFile.getCanonicalPath() + "\" for writing\r\n";
				} catch (IOException io_e) {
					errString = "451 Couldn't open file, nested exception\r\n";
				}
				break storing;
			}
			if(!sessionThread.startUsingDataSocket()) {
				errString = "425 Couldn't open data socket\r\n";
				break storing;
			}
			myLog.l(Log.DEBUG, "Data socket ready");
			sessionThread.writeString("150 Data socket ready\r\n");
			byte[] buffer = new byte[Defaults.getDataChunkSize()];
			//dedicatedWriter = new DedicatedWriter(out);
			//dedicatedWriter.start();  // start the writer thread executing
			//myLog.l(Log.DEBUG, "Started DedicatedWriter");
			int numRead;
//			Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
//			int newPriority = Thread.currentThread().getPriority();
//			myLog.l(Log.DEBUG, "New STOR prio: " + newPriority);
			if(sessionThread.isBinaryMode() ) {
				myLog.d("Mode is binary");
			} else {
				myLog.d("Mode is ascii");
			}
			int bytesSinceReopen = 0;
			int bytesSinceFlush = 0;
			while(true) {
				switch(numRead = sessionThread.receiveFromDataSocket(buffer)) {
				case -1:
					myLog.l(Log.DEBUG, "Returned from final read");
					// We're finished reading
					break storing; 
				case 0: 
					errString = "426 Couldn't receive data\r\n";
					break storing;
				case -2:
					errString = "425 Could not connect data socket\r\n";
					break storing;
				default:
					try {
						if(sessionThread.isBinaryMode()) {
							out.write(buffer, 0, numRead);
						} else {
							int startPos=0, endPos;
							for(endPos = 0; endPos < numRead; endPos++ ) {
								if(buffer[endPos] == '\r') {
									// Our hacky method is to drop all \r
									out.write(buffer, startPos, endPos-startPos);
									startPos = endPos+1;
								}
							}
							if(startPos < numRead) {
								out.write(buffer, startPos, endPos-startPos);
							}
						}
						out.flush();
						
					} catch (IOException e) {
						errString = "451 File IO problem. Device might be full.\r\n";
						myLog.d("Exception while storing: " + e);
						myLog.d("Message: " + e.getMessage());
						myLog.d("Stack trace: ");
						StackTraceElement[] traceElems = e.getStackTrace();
						for(StackTraceElement elem : traceElems) {
							myLog.d(elem.toString());
						}
						break storing;
					}
					break;
				}
			}
		}
		try {
			if(out != null) {
				out.close();
			}
		} catch (IOException e) {}
		
		if(errString != null) {
			myLog.l(Log.INFO, "STOR error: " + errString.trim());
			sessionThread.writeString(errString);
		} else {
			sessionThread.writeString("226 Transmission complete\r\n");
			Util.newFileNotify(storeFile.getPath());
		}
		sessionThread.closeDataSocket();
		myLog.l(Log.DEBUG, "STOR finished");
	}
}
