package com.amituofo.xcexplorer.entry.plugin.osd.core.action;

import com.amituofo.common.ex.InvalidParameterException;
import com.amituofo.common.ex.ServiceException;
import com.amituofo.common.ui.action.InputValidator;
import com.amituofo.common.ui.action.RefreshAction;
import com.amituofo.common.util.StringUtils;
import com.amituofo.common.util.ValidUtils;
import com.amituofo.task.TaskDetail;
import com.amituofo.task.TaskManagement;
import com.amituofo.xcexplorer.core.global.GlobalResource;
import com.amituofo.xcexplorer.core.space.WorkingSpace;
import com.amituofo.xcexplorer.core.ui.ContentExplorerContainer;
import com.amituofo.xcexplorer.core.ui.ItemExplorer;
import com.amituofo.xcexplorer.core.ui.compoent.EntryChoosePanel;
import com.amituofo.xcexplorer.core.ui.compoent.WorkingSpaceChoosePanel;
import com.amituofo.xcexplorer.core.ui.compoent.container.ClassicItemExplorerContainer;
import com.amituofo.xcexplorer.entry.plugin.osd.core.task.OSDTaskFactory;
import com.amituofo.xcexplorer.entry.plugin.osd.core.task.TaskUtils;
import com.amituofo.xcexplorer.util.UIUtils;
import com.amituofo.xfs.plugin.fs.objectstorage.OSDBucketspace;
import com.amituofo.xfs.service.Itemspace;
import com.amituofo.xfs.service.ItemspaceConfig;

public class OSDItemspaceAction {

	private String customItemspaceTitle;

	public OSDItemspaceAction(String customItemspaceTitle) {
		this.customItemspaceTitle = customItemspaceTitle;
	}

	protected String inputItemspaceName(String defaultName) {
		String newname = UIUtils.openInput("Please input the name of " + getCustomItemspaceName() + ":", defaultName, new InputValidator<String>() {

			@Override
			public void validate(String value) throws InvalidParameterException {
				ValidUtils.invalidIfEmpty(value, "Name must be specificed.");
				// ValidUtils.invalidIfLengthGreaterThan(value, 32, "namespace names must be one through 32 characters long.");
				ValidUtils.invalidIfContains(value, new String[] { "\\", "/", ":", "*", "?", "\"", "<", ">", "|" }, "Invalid character \\/:*?\"<>| included.");
			}
		});

		return newname;

	}

	protected boolean deleteAllInBucket(String bucketName) {
		String newname = UIUtils
				.openInput("Please input [YES] to continue delete all the objects in " + getCustomItemspaceName() + " [" + bucketName + "]:", "", new InputValidator<String>() {

					@Override
					public void validate(String value) throws InvalidParameterException {
						ValidUtils.invalidIfNotEquals(value.toUpperCase(), "YES", "Please input [YES] to continue!");
					}
				});

		return "YES".equalsIgnoreCase(newname);
	}

	public void createBucket(ItemExplorer ie, WorkingSpace workingspace) {
		String name = inputItemspaceName("your " + getCustomItemspaceName() + " name");

		if (StringUtils.isNotEmpty(name)) {
			ItemspaceConfig config = new ItemspaceConfig(name);
			try {
				workingspace.getFileSystemEntry().createItemSpace(config);

				refreshItemspace(ie.getExplorerContainer());
			} catch (ServiceException e1) {
				e1.printStackTrace();
				UIUtils.openAndLogError("Error when trying to create " + getCustomItemspaceName() + " " + name, e1);
			}
		}
	}

	public void listUncompletedMultipartUploads(WorkingSpace workingspace) {
	}

	public void deleteBucket(final ItemExplorer ie, final WorkingSpace workingspace) {
		Itemspace itemspace = workingspace.getItemspace();
		final String name = itemspace.getName();
		if (UIUtils.openInfoConfirm("Are you sure to delete " + getCustomItemspaceName() + " [" + name + "] ?")) {

			if (!deleteAllInBucket(name)) {
				return;
			}

			TaskDetail task = GlobalResource.getTaskFactory(OSDTaskFactory.class).createBucketEmptyingTask(itemspace);
			final TaskManagement taskMgr = GlobalResource.getTaskmanagement();
			taskMgr.add(task, TaskUtils.newListenerForRefreshAfterTaskDone(new RefreshAction() {

				@Override
				public void refresh() {
					try {
						workingspace.getFileSystemEntry().deleteItemSpace(name);
						workingspace.close();

						refreshItemspace(ie.getExplorerContainer());
					} catch (ServiceException e1) {
						e1.printStackTrace();
						UIUtils.openAndLogError("Error when trying to delete " + getCustomItemspaceName() + " " + name, e1);
					}
				}
			}));

		}
	}

	public void deleteBucketObjects(ItemExplorer ie, WorkingSpace workingspace) {
		Itemspace itemspace = workingspace.getItemspace();

		if (!UIUtils.openInfoConfirm("Are you sure to emptying " + getCustomItemspaceName() + " [" + itemspace.getName() + "] ?")) {
			return;
		}

		if (!deleteAllInBucket(itemspace.getName())) {
			return;
		}

		TaskDetail task = GlobalResource.getTaskFactory(OSDTaskFactory.class).createBucketEmptyingTask(itemspace);
		// TaskDetail task = new S3TaskFactory().createBucketEmptyingTask((S3DefaultBucketspace) itemspace);
		final TaskManagement taskMgr = GlobalResource.getTaskmanagement();
		taskMgr.add(task, TaskUtils.newListenerForRefreshAfterTaskDone(ie));
	}

	protected void refreshItemspace(ContentExplorerContainer ec) {
		if (ec instanceof ClassicItemExplorerContainer) {
			ClassicItemExplorerContainer cec = (ClassicItemExplorerContainer) ec;
			EntryChoosePanel entryToolbar = cec.getEntryChooseBar();
			WorkingSpaceChoosePanel workingspaceChoosebar = entryToolbar.getCurrentWorkingSpaceChooseBar();
			workingspaceChoosebar.refresh();
		}
	}

	public void openConfiguration(ItemExplorer ie, WorkingSpace ws) {
//		Itemspace itemspace = ws.getItemspace();
		// if (itemspace instanceof S3DefaultBucketspace) {
		// S3DefaultBucketspace namespace = (S3DefaultBucketspace) itemspace;
		// bucket.getConfiguration();
		// }
	}

	public String getCustomItemspaceName() {
		return customItemspaceTitle;
	}

	public void openCORSConfiguration(OSDBucketspace itemspace) {
	}

	public void openLifecycleConfiguration(OSDBucketspace itemspace) {
	}

}
