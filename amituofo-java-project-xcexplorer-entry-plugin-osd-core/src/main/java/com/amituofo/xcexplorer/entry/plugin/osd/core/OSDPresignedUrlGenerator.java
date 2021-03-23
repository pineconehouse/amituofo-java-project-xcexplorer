package com.amituofo.xcexplorer.entry.plugin.osd.core;

import java.net.URL;
import java.util.Date;

import com.amituofo.common.ex.ServiceException;
import com.amituofo.xfs.service.FileItem;

public interface OSDPresignedUrlGenerator {

	URL generatePresignedUrl(FileItem item, boolean read, boolean write, boolean delete, Date expireAt) throws ServiceException;

}
