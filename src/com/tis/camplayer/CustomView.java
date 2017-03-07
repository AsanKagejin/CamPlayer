package com.tis.camplayer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Created by Asan on 06.03.2017.
 */

class CustomView extends JDialog{

	private JSpinner rowsSpin, colsSpin;

	CustomView(ProgramState state){
		setTitle("Edit cameras");
		setBounds(50, 50, 200, 100);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				state.setSize((Integer)colsSpin.getValue(), (Integer)rowsSpin.getValue());
			}
		});
		setResizable(false);
		setModal(true);
		JPanel contentPanel = new JPanel(new GridLayout(2, 2));

		JLabel rowsLabel = new JLabel("Rows:");
		contentPanel.add(rowsLabel);

		JLabel colsLabel = new JLabel("Columns:");
		contentPanel.add(colsLabel);

		rowsSpin = new JSpinner(new SpinnerNumberModel(state.rows, 1, 10, 1));
		((JSpinner.DefaultEditor)rowsSpin.getEditor()).getTextField().setEditable(false);
		contentPanel.add(rowsSpin);

		colsSpin = new JSpinner(new SpinnerNumberModel(state.cols, 1, 10, 1));
		((JSpinner.DefaultEditor)colsSpin.getEditor()).getTextField().setEditable(false);
		contentPanel.add(colsSpin);

		setContentPane(contentPanel);
		setVisible(true);
	}
}
