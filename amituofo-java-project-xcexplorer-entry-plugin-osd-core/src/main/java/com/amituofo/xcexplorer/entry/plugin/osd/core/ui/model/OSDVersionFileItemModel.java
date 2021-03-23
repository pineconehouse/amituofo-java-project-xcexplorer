package com.amituofo.xcexplorer.entry.plugin.osd.core.ui.model;

import java.awt.EventQueue;
import java.util.Comparator;

import com.amituofo.common.api.ObjectHandler;
import com.amituofo.common.define.HandleFeedback;
import com.amituofo.common.ex.ServiceException;
import com.amituofo.common.ui.swingexts.model.BasicTableModel;
import com.amituofo.xfs.plugin.fs.objectstorage.OSDFileItem;
import com.amituofo.xfs.plugin.fs.objectstorage.OSDVersionFileItem;
import com.amituofo.xfs.service.ItemEvent;

public abstract class OSDVersionFileItemModel extends BasicTableModel<OSDVersionFileItem> { // AbstractTableModel
	protected boolean skipEmptyItem = false;
	protected OSDFileItem object;

	public OSDVersionFileItemModel(Class[] columnTypes, String[] columns, int[] columnWidths, int[] columnHAlignments) {
		super(null, columnTypes, columns, columnWidths, columnHAlignments, null, null);
	}

	public OSDFileItem getWorkingObject() {
		return object;
	}

	public void listVersions(OSDFileItem object, final ObjectHandler<Integer, OSDVersionFileItem> event) throws ServiceException {
		removeRows();

		this.object = object;

		list(new ObjectHandler<Integer, OSDVersionFileItem>() {

			@Override
			public HandleFeedback handle(Integer eventType, OSDVersionFileItem item) {
				if (eventType == ItemEvent.ITEM_FOUND) {
					if (skipEmptyItem && (item.getSize() == null || item.getSize() == 0)) {
						return null;
					}

					EventQueue.invokeLater(new Runnable() {

						@Override
						public void run() {
							// add(data);
							 insert(0, item);
						}
					});
				}
				if (event != null) {
					return event.handle(eventType, item);
				}
				return null;
			}

			@Override
			public void exceptionCaught(OSDVersionFileItem data, Throwable e) {
				if (event != null) {
					event.exceptionCaught(data, e);
				}
			}
		});
	}

	// @Override
	// public boolean delete(HCPVersionFileItem item) throws ServiceException {
	// return item.delete(null);
	// }

	public boolean isSkipEmptyItem() {
		return skipEmptyItem;
	}

	public void setSkipEmptyItem(boolean skipEmptyItem) {
		this.skipEmptyItem = skipEmptyItem;
	}

	@Override
	protected void list(ObjectHandler<Integer, OSDVersionFileItem> event, Object... args) {
		object.listVersions(event);
	}

	@Override
	public Comparator getComparator(int column) {
		// TODO Auto-generated method stub
		return null;
	}

}
