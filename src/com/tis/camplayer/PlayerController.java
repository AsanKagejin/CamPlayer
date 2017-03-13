package com.tis.camplayer;

import com.sun.jna.Native;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Vector;

/**
 * Created by Asan on 03.03.2017.
 */

class PlayerController {

	private BufferedWriter out;
	private Canvas container;
	private JList<Camera> camList;
	private JPopupMenu popupMenu;
	String attachedMRL;

	PlayerController(OutputStream stream, Canvas canvas, Vector<Camera> cams){
		out = new BufferedWriter(new OutputStreamWriter(stream));
		container = canvas;
		popupMenu = new JPopupMenu("Select camera");
		camList = new JList<>();
		camList.setCellRenderer(new CameraListRenderer());
		DefaultListModel<Camera> listModel = new DefaultListModel<>();
		for (Camera camera : cams)
			listModel.addElement(camera);
		camList.setModel(listModel);
		camList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		camList.getSelectionModel().addListSelectionListener(e -> {
			Camera camera = camList.getSelectedValue();
			setAttachedMRL(camera.getAddress());
			say("play " + attachedMRL);
		});
		popupMenu.add(camList);
		canvas.addMouseListener(new MouseListener(){
			@Override
			public void mouseEntered(MouseEvent e) {}

			@Override
			public void mouseExited(MouseEvent e) {}

			@Override
			public void mousePressed(MouseEvent e) {showPopupMenu(e);}

			@Override
			public void mouseReleased(MouseEvent e) {showPopupMenu(e);}

			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getButton() == MouseEvent.BUTTON1) {
					say("close");
					try {
						OutputStream output = PlayerControllerFactory.startNewJVM(Native.getComponentID(container));
						out = new BufferedWriter(new OutputStreamWriter(output));
						say("play " + attachedMRL);
						System.err.print(e.getSource().getClass().toGenericString()+" source\n");
						System.err.print(this.getClass().toGenericString()+" this\n");
						//say("play rtsp://oper:oper@192.168.9.33:554/axis-media/media.amp");
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}
		} );
	}

	boolean say(String command){
		try {
			out.write(command+"\n");
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	void setAttachedMRL(String MRL){
		attachedMRL = MRL;
	}

	void rebuildPopupMenu(Vector<Camera> cams){
		DefaultListModel<Camera> listModel = new DefaultListModel<>();
		for (Camera camera : cams)
			listModel.addElement(camera);
		camList.setModel(listModel);
	}

	private void showPopupMenu(MouseEvent e){
		if(e.isPopupTrigger())
			popupMenu.show(e.getComponent(), e.getX(), e.getY());
	}
}
