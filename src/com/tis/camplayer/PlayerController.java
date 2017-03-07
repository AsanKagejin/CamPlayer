package com.tis.camplayer;

import com.sun.jna.Native;

import java.awt.Canvas;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

/**
 * Created by Asan on 03.03.2017.
 */

class PlayerController {

	private BufferedWriter out;
	private Canvas container;
	private String attachedMRL;

	PlayerController(OutputStream stream, Canvas canvas){
		out = new BufferedWriter(new OutputStreamWriter(stream));
		container = canvas;
		canvas.addMouseListener(new MouseListener(){
			@Override
			public void mouseEntered(MouseEvent e) {}

			@Override
			public void mouseExited(MouseEvent e) {}

			@Override
			public void mousePressed(MouseEvent e) {}

			@Override
			public void mouseReleased(MouseEvent e) {}

			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getButton() == MouseEvent.BUTTON1) {
					say("close");
					try {
						OutputStream output = PlayerControllerFactory.startNewJVM(Native.getComponentID(container));
						out = new BufferedWriter(new OutputStreamWriter(output));
						// replace with say("play " + attachedMRL);
						say("play rtsp://oper:oper@192.168.9.33:554/axis-media/media.amp");
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					System.err.print("Did it\n");
				}
			}
		} );
	}

	void say(String command){
		try {
			out.write(command+"\n");
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	void setAttachedMRL(String MRL){
		attachedMRL = MRL;
	}
}
