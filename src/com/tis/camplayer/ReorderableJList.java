package com.tis.camplayer;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.*;
import java.io.IOException;

/**
 * Created by Asan on 13.03.2017.
 */

public class ReorderableJList<E> extends JList implements DragSourceListener, DropTargetListener, DragGestureListener {

	private static DataFlavor localObjectFlavor;
	static {
		try {
			localObjectFlavor =
					new DataFlavor (DataFlavor.javaJVMLocalObjectMimeType);
		} catch (ClassNotFoundException cnfe) { cnfe.printStackTrace(); }
	}
	private static DataFlavor[] supportedFlavors = { localObjectFlavor };
	private DragSource dragSource;
	private DropTarget dropTarget;
	private Object dropTargetCell;
	private int draggedIndex = -1;

	public ReorderableJList () {
		super();
		setCellRenderer (new ReorderableListCellRenderer());
		setModel (new DefaultListModel());
		dragSource = new DragSource();
		DragGestureRecognizer dgr =
				dragSource.createDefaultDragGestureRecognizer (this,
						DnDConstants.ACTION_MOVE,
						this);
		dropTarget = new DropTarget (this, this);
	}

	// DragSourceListener implementation
	@Override
	public void dragEnter(DragSourceDragEvent dsde) {}

	@Override
	public void dragOver(DragSourceDragEvent dsde) {}

	@Override
	public void dropActionChanged(DragSourceDragEvent dsde) {}

	@Override
	public void dragExit(DragSourceEvent dse) {}

	@Override
	public void dragDropEnd(DragSourceDropEvent dsde) {
		dropTargetCell = null;
		draggedIndex = -1;
		repaint();
	}

	//DropTargetListener implementation
	@Override
	public void dragEnter(DropTargetDragEvent dtde) {
		if (dtde.getSource() != dropTarget)
			dtde.rejectDrag();
		else
			dtde.acceptDrag(DnDConstants.ACTION_COPY_OR_MOVE);
	}

	@Override
	public void dragOver(DropTargetDragEvent dtde) {
		if (dtde.getSource() != dropTarget)
			dtde.rejectDrag();
		Point dragPoint = dtde.getLocation();
		int index = locationToIndex (dragPoint);
		if (index == -1)
			dropTargetCell = null;
		else
			dropTargetCell = getModel().getElementAt(index);
		repaint();
	}

	@Override
	public void dropActionChanged(DropTargetDragEvent dtde) {}

	@Override
	public void dragExit(DropTargetEvent dte) {}

	@Override
	public void drop(DropTargetDropEvent dtde) {
		if (dtde.getSource() != dropTarget) {
			dtde.rejectDrop();
			return;
		}
		Point dropPoint = dtde.getLocation();
		int index = locationToIndex (dropPoint);
		boolean dropped = false;
		try {
			if ((index == -1) || (index == draggedIndex)) {
				dtde.rejectDrop();
				return;
			}
			dtde.acceptDrop (DnDConstants.ACTION_MOVE);
			Object dragged = dtde.getTransferable().getTransferData(localObjectFlavor);
			boolean sourceBeforeTarget = (draggedIndex < index);
			DefaultListModel mod = (DefaultListModel) getModel();
			mod.remove (draggedIndex);
			mod.add ((sourceBeforeTarget ? index-1 : index), dragged);
			dropped = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		dtde.dropComplete (dropped);
	}

	//DragGestureListener implementation
	@Override
	public void dragGestureRecognized(DragGestureEvent dge) {
		Point clickPoint = dge.getDragOrigin();
		int index = locationToIndex(clickPoint);
		if (index == -1)
			return;
		Object target = getModel().getElementAt(index);
		Transferable trans = new RJLTransferable (target);
		draggedIndex = index;
		dragSource.startDrag (dge,Cursor.getDefaultCursor(), trans, this);
	}

	class ReorderableListCellRenderer extends DefaultListCellRenderer {

		boolean isTargetCell;
		boolean isLastItem;
		Insets normalInsets, lastItemInsets;
		int BOTTOM_PAD = 30;

		ReorderableListCellRenderer() {
			super();
			normalInsets = super.getInsets();
			lastItemInsets = new Insets (normalInsets.top, normalInsets.left, normalInsets.bottom + BOTTOM_PAD, normalInsets.right);
		}

		public Component getListCellRendererComponent (JList list, Object value, int index, boolean isSelected, boolean hasFocus) {
			isTargetCell = (value == dropTargetCell);
			isLastItem = (index == list.getModel().getSize()-1);
			boolean showSelected = isSelected &
					(dropTargetCell == null);
			return super.getListCellRendererComponent (list, value,
					index, showSelected,
					hasFocus);
		}

		public void paintComponent (Graphics g) {
			super.paintComponent(g);
			if (isTargetCell) {
				g.setColor(Color.black);
				g.drawLine (0, 0, getSize().width, 0);
			}
		}
	}

	class RJLTransferable implements Transferable {
		Object object;

		RJLTransferable(Object o) {
			object = o;
		}

		public Object getTransferData(DataFlavor df)
				throws UnsupportedFlavorException, IOException {
			if (isDataFlavorSupported(df))
				return object;
			else
				throw new UnsupportedFlavorException(df);
		}

		public boolean isDataFlavorSupported(DataFlavor df) {
			return (df.equals(localObjectFlavor));
		}

		public DataFlavor[] getTransferDataFlavors() {
			return supportedFlavors;
		}
	}
}
