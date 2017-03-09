package com.tis.camplayer;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Asan on 09.03.2017.
 */

class EditDialog extends JDialog {

	private JTextField nameField, protocolField, loginField, passwordField, ipField, portField, streamField;

	EditDialog(Camera camera){
		setTitle("Add/edit camera");
		setBounds(50, 50, 350, 400);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setResizable(false);
		setModal(true);
		JPanel contentPanel = new JPanel(new GridBagLayout());
		GridBagConstraints cons = new GridBagConstraints();
		cons.fill = GridBagConstraints.BOTH;
		cons.insets = new Insets(10, 10, 10, 10);
		cons.weightx = 0.5;
		cons.weighty = 0.5;

		JLabel nameLabel = new JLabel("Name:", JLabel.RIGHT);
		cons.gridx = 0;
		cons.gridy = 0;
		cons.gridwidth = GridBagConstraints.RELATIVE;
		contentPanel.add(nameLabel, cons);

		nameField = new JTextField(camera.getName());
		cons.gridx = 1;
		cons.gridwidth = GridBagConstraints.REMAINDER;
		contentPanel.add(nameField, cons);

		JLabel protocolLabel = new JLabel("Protocol:", JLabel.RIGHT);
		cons.gridx = 0;
		cons.gridy = 1;
		cons.gridwidth = GridBagConstraints.RELATIVE;
		contentPanel.add(protocolLabel, cons);

		protocolField = new JTextField(camera.getName());
		cons.gridx = 1;
		cons.gridwidth = GridBagConstraints.REMAINDER;
		contentPanel.add(protocolField, cons);

		JLabel loginLabel = new JLabel("Login:", JLabel.RIGHT);
		cons.gridx = 0;
		cons.gridy = 2;
		cons.gridwidth = GridBagConstraints.RELATIVE;
		contentPanel.add(loginLabel, cons);

		loginField = new JTextField(camera.getLogin());
		cons.gridx = 1;
		cons.gridwidth = GridBagConstraints.REMAINDER;
		contentPanel.add(loginField, cons);

		JLabel passwordLabel = new JLabel("Password:", JLabel.RIGHT);
		cons.gridx = 0;
		cons.gridy = 3;
		cons.gridwidth = GridBagConstraints.RELATIVE;
		contentPanel.add(passwordLabel, cons);

		passwordField = new JPasswordField(camera.getLogin());
		cons.gridx = 1;
		cons.gridwidth = GridBagConstraints.REMAINDER;
		contentPanel.add(passwordField, cons);

		JLabel ipLabel = new JLabel("IP address:", JLabel.RIGHT);
		cons.gridx = 0;
		cons.gridy = 4;
		cons.gridwidth = GridBagConstraints.RELATIVE;
		contentPanel.add(ipLabel, cons);

		ipField = new JTextField(camera.getLogin());
		cons.gridx = 1;
		cons.gridwidth = GridBagConstraints.REMAINDER;
		contentPanel.add(ipField, cons);

		JLabel portLabel = new JLabel("Port:", JLabel.RIGHT);
		cons.gridx = 0;
		cons.gridy = 5;
		cons.gridwidth = GridBagConstraints.RELATIVE;
		contentPanel.add(portLabel, cons);

		portField = new JTextField(camera.getLogin());
		cons.gridx = 1;
		cons.gridwidth = GridBagConstraints.REMAINDER;
		contentPanel.add(portField, cons);

		JLabel streamLabel = new JLabel("Stream address:", JLabel.RIGHT);
		cons.gridx = 0;
		cons.gridy = 6;
		cons.gridwidth = GridBagConstraints.RELATIVE;
		contentPanel.add(streamLabel, cons);

		streamField = new JTextField(camera.getLogin());
		cons.gridx = 1;
		cons.gridwidth = GridBagConstraints.REMAINDER;
		contentPanel.add(streamField, cons);

		JButton okButton = new JButton("OK");
		okButton.addActionListener(e -> {
			String prot, ip, port;
			prot = protocolField.getText();
			ip = ipField.getText();
			port = portField.getText();
			if(isValidProtocol(prot) && isValidIP(ip) && isValidPort(port)) {
				camera.setName(nameField.getText());
				camera.setAddress(prot, loginField.getText(), passwordField.getText(), ip, port, streamField.getText());
				dispose();
			} else JOptionPane.showMessageDialog(this, "Wrong parameters!",
					"Warning", JOptionPane.WARNING_MESSAGE);
		});
		cons.gridx = 0;
		cons.gridy = 7;
		cons.gridwidth = GridBagConstraints.RELATIVE;
		contentPanel.add(okButton, cons);

		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(e -> dispose());
		cons.gridx = 1;
		cons.gridwidth = GridBagConstraints.REMAINDER;
		contentPanel.add(cancelButton, cons);

		setContentPane(contentPanel);
		setVisible(true);
	}

	boolean isValidProtocol(String prot){
		return prot.equals("rtsp");//|prot.equals("rtp")|prot.equals("http");
	}

	boolean isValidIP(String ip){
		boolean flag = true;
		String temp[] = ip.split(".");
		for(String num : temp){
			int part = -1;
			try {
				part = Integer.parseInt(num);
			} catch (NumberFormatException e) {
				return false;
			}
		flag = flag && (part < 256 && part > 0);
		}
		return flag;
	}

	boolean isValidPort(String port){
		int temp = -1;
		try{
			temp = Integer.parseInt(port);
		} catch (NumberFormatException e) {
			return false;
		}
		return (temp < 65535 && temp > 0);
	}
}
