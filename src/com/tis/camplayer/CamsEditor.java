package com.tis.camplayer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Vector;

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


		camList = new JList<>();
		camList.setCellRenderer(new CameraListRenderer());
		DefaultListModel<Camera> listModel = new DefaultListModel<>();
		for (Camera camera : state.cams)
			listModel.addElement(camera);
		camList.setModel(listModel);
		camList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		cons.gridy = 1;
		cons.weighty = 1.0;
		cons.gridheight = 5;
		cons.gridwidth = GridBagConstraints.RELATIVE;
		contentPanel.add(camList, cons);

		JButton addButton = new JButton("Add camera");
		addButton.setSize(80, 20);
		addButton.addActionListener(e -> {
			Camera camera = new Camera();
			new EditDialog(camera);
			listModel.addElement(camera);
		});
		cons.gridheight = 1;
		cons.weightx = 0.1;
		cons.weighty = 0.1;
		cons.gridx = 1;
		cons.insets = new Insets(4, 4, 4, 4);
		contentPanel.add(addButton, cons);

		JButton editButton = new JButton("Edit camera");
		editButton.setSize(80, 20);
		editButton.addActionListener(e -> new EditDialog(camList.getSelectedValue()));
		cons.gridy = 2;
		contentPanel.add(editButton, cons);

		JButton deleteButton = new JButton("Delete camera");
		deleteButton.setSize(80, 20);
		deleteButton.addActionListener(e -> camList.remove(camList.getSelectedIndex()));
		cons.gridy = 3;
		contentPanel.add(deleteButton, cons);

		JPanel hidden = new JPanel();
		cons.weighty = 1.0;
		cons.gridy = 4;
		contentPanel.add(hidden, cons);

		JButton closeButton = new JButton("Close");
		closeButton.setSize(80, 20);
		closeButton.addActionListener((ActionEvent e) -> {
			int size = listModel.getSize();
			state.cams = new Vector<>(size);
			for(int i = 0; i < size; i++)
				state.cams.add(listModel.get(i));
			dispose();
		});
		cons.weighty = 0.1;
		cons.gridy = 5;
		cons.anchor = GridBagConstraints.LAST_LINE_END;
		contentPanel.add(closeButton, cons);

		setContentPane(contentPanel);
		setVisible(true);
	}
}
