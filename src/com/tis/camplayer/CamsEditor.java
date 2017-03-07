package com.tis.camplayer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Created by Asan on 06.03.2017.
 */

class CamsEditor extends JDialog {
	private JList<Camera> camList;

	CamsEditor(ProgramState state){
		setTitle("Edit cameras");
		setBounds(50, 50, 350, 400);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				state.setCams(camList.getModel());
			}
		});
		setResizable(false);
		setModal(true);
		JPanel contentPanel = new JPanel(new GridBagLayout());
		GridBagConstraints cons = new GridBagConstraints();
		cons.fill = GridBagConstraints.BOTH;
		cons.insets = new Insets(10, 10, 10, 10);

		JLabel label = new JLabel("Cameras:");
		cons.gridx = 0;
		cons.gridy = 0;
		cons.weightx = 0.8;
		cons.weighty = 0.1;
		cons.gridwidth = GridBagConstraints.REMAINDER;
		contentPanel.add(label, cons);


		camList = new JList<>(state.cams);
		camList.setCellRenderer(new CameraListRenderer());
		cons.gridy = 1;
		cons.weighty = 1.0;
		cons.gridheight = 5;
		cons.gridwidth = GridBagConstraints.RELATIVE;
		contentPanel.add(camList, cons);

		JButton addButton = new JButton("Add camera");
		addButton.setSize(80, 20);
		cons.gridheight = 1;
		cons.weightx = 0.1;
		cons.weighty = 0.1;
		cons.gridx = 1;
		cons.insets = new Insets(4, 4, 4, 4);
		contentPanel.add(addButton, cons);

		JButton editButton = new JButton("Edit camera");
		editButton.setSize(80, 20);
		cons.gridy = 2;
		contentPanel.add(editButton, cons);

		JButton deleteButton = new JButton("Delete camera");
		deleteButton.setSize(80, 20);
		cons.gridy = 3;
		contentPanel.add(deleteButton, cons);

		JPanel hidden = new JPanel();
		cons.weighty = 1.0;
		cons.gridy = 4;
		contentPanel.add(hidden, cons);

		JButton closeButton = new JButton("Close");
		closeButton.setSize(80, 20);
		cons.weighty = 0.1;
		cons.gridy = 5;
		cons.anchor = GridBagConstraints.LAST_LINE_END;
		contentPanel.add(closeButton, cons);

		setContentPane(contentPanel);
		setVisible(true);
	}
}
