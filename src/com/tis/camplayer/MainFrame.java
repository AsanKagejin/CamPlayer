package com.tis.camplayer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Vector;

/**
 * Created by Asan on 03.03.2017.
 */

public class MainFrame extends JFrame {

	private Vector<PlayerController> players;
	private JPanel contentPanel = new JPanel();
	private ProgramState state;

	public static void main(String args[]){
		SwingUtilities.invokeLater(MainFrame::new);
	}

	private MainFrame(){
		setTitle("TIS-TMU Camera viewer");
		setBounds(50, 50, 800, 600);
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				for (PlayerController playerComponent: players)
					playerComponent.say("close");
				state.saveState();
				System.exit(0);
			}
		});
		state = ProgramState.loadState();
		Vector<Canvas> canvases = createContainers(contentPanel);
		JMenuBar menuBar = new JMenuBar();
		JMenu menu = new JMenu("File");
		menuBar.add(menu);

		JMenuItem menuItem = new JMenuItem("Exit");
		menuItem.addActionListener(e -> dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING)));
		menu.add(menuItem);

		menu = new JMenu("View");
		menuBar.add(menu);

		menuItem = new JMenuItem("1 camera");
		menuItem.addActionListener(e -> redrawCams(1, 1));
		menu.add(menuItem);

		menuItem = new JMenuItem("2 cameras");
		menuItem.addActionListener(e -> redrawCams(2, 1));
		menu.add(menuItem);

		menuItem = new JMenuItem("4 cameras");
		menuItem.addActionListener(e -> redrawCams(2, 2));
		menu.add(menuItem);

		menuItem = new JMenuItem("Custom");
		menuItem.addActionListener((ActionEvent e) -> SwingUtilities.invokeLater(() -> {
			new CustomView(state);
			redrawCams(state.rows, state.cols);
		}));
		menu.add(menuItem);

		menuItem = new JMenuItem("Settings");
		menuItem.setSize(new Dimension(60, 20));
		menuItem.addActionListener(e -> SwingUtilities.invokeLater(() -> new CamsEditor(state)));
		menuBar.add(menuItem);

		setContentPane(contentPanel);
		setJMenuBar(menuBar);
		setVisible(true);
		players = createPlayerProcesses(canvases);
		//setMRLs();
	}

	private Vector<Canvas> createContainers(JPanel parent){
		Vector<Canvas> temp = new Vector<>();
		parent.setLayout(new GridLayout(state.cols, state.rows));
		for(int i = 0; i < (state.rows * state.cols); i++){
			Canvas canvas = new Canvas();
			parent.add(canvas);
			temp.add(canvas);
			canvas.validate();
		}
		parent.validate();
		return temp;
	}

	private Vector<PlayerController> createPlayerProcesses(Vector<Canvas> canvases){
		Vector<PlayerController> temp = new Vector<>();
		for (Canvas canvas: canvases)
			temp.add(PlayerControllerFactory.newPlayerController(canvas, state.cams));
		return temp;
	}
	private void redrawCams(int rows, int cols){
		for (PlayerController playerComponent: players)
			playerComponent.say("close");
		state.cols = cols;
		state.rows = rows;
		contentPanel.removeAll();
		players.clear();
		players = createPlayerProcesses(createContainers(contentPanel));
		contentPanel.revalidate();
		setMRLs();
	}

	private void setMRLs(){	//must be replaced with reading state.cams
		for (PlayerController player: players)
			player.say("play rtsp://oper:oper@192.168.9.33:554/axis-media/media.amp");
	}

	/*private void setMRLs(){
		for(int i = 0; i < players.size(); i++){
			players.elementAt(i).say("play " + state.cams.elementAt(i).getAddr());
		}
	}*/
}
