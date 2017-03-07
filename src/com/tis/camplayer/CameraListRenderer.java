package com.tis.camplayer;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Asan on 06.03.2017.
 */

class CameraListRenderer extends JLabel implements ListCellRenderer{

	CameraListRenderer(){
		setOpaque(true);
	}

	@Override
	public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
		Camera entry = (Camera) value;
		setText(entry.getName());
		if(isSelected){
			setBackground(new Color(127, 127, 255));
			setForeground(Color.white);
		} else {
			setBackground(Color.white);
			setForeground(Color.black);
		}
		return this;
	}
}
