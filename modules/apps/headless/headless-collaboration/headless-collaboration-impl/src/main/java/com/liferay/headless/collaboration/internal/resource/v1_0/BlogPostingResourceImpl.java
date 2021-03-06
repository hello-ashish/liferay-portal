/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.headless.collaboration.internal.resource.v1_0;

import static com.liferay.portal.vulcan.util.LocalDateTimeUtil.toLocalDateTime;

import com.liferay.blogs.model.BlogsEntry;
import com.liferay.blogs.service.BlogsEntryService;
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.document.library.util.DLURLHelper;
import com.liferay.headless.collaboration.dto.v1_0.BlogPosting;
import com.liferay.headless.collaboration.dto.v1_0.ImageObject;
import com.liferay.headless.collaboration.resource.v1_0.BlogPostingResource;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.servlet.taglib.ui.ImageSelector;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.time.LocalDateTime;

import java.util.Objects;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.core.Response;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Javier Gamarra
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/blog-posting.properties",
	scope = ServiceScope.PROTOTYPE, service = BlogPostingResource.class
)
public class BlogPostingResourceImpl extends BaseBlogPostingResourceImpl {

	@Override
	public Response deleteBlogPostings(Long blogPostingId) throws Exception {
		_blogsEntryService.deleteEntry(blogPostingId);

		return buildNoContentResponse();
	}

	@Override
	public BlogPosting getBlogPostings(Long blogPostingId) throws Exception {
		BlogsEntry blogsEntry = _blogsEntryService.getEntry(blogPostingId);

		return _toBlogPosting(blogsEntry);
	}

	@Override
	public Page<BlogPosting> getContentSpacesBlogPostingsPage(
		Long parentId, Pagination pagination) {

		return Page.of(
			transform(
				_blogsEntryService.getGroupEntries(
					parentId, WorkflowConstants.STATUS_APPROVED,
					pagination.getStartPosition(), pagination.getEndPosition()),
				this::_toBlogPosting),
			pagination,
			_blogsEntryService.getGroupEntriesCount(
				parentId, WorkflowConstants.STATUS_APPROVED));
	}

	@Override
	public BlogPosting postContentSpacesBlogPostings(
			Long contentSpaceId, BlogPosting blogPosting)
		throws Exception {

		LocalDateTime localDateTime = toLocalDateTime(
			blogPosting.getDatePublished());

		return _toBlogPosting(
			_blogsEntryService.addEntry(
				blogPosting.getHeadline(), blogPosting.getAlternativeHeadline(),
				blogPosting.getFriendlyUrlPath(), blogPosting.getDescription(),
				blogPosting.getArticleBody(), localDateTime.getMonthValue() - 1,
				localDateTime.getDayOfMonth(), localDateTime.getYear(),
				localDateTime.getHour(), localDateTime.getMinute(), true, true,
				new String[0], blogPosting.getCaption(),
				_getImageSelector(blogPosting), null,
				_createServiceContext(contentSpaceId, blogPosting)));
	}

	@Override
	public BlogPosting putBlogPostings(
			Long blogPostingId, BlogPosting blogPosting)
		throws Exception {

		LocalDateTime localDateTime = toLocalDateTime(
			blogPosting.getDatePublished());

		BlogsEntry blogsEntry = _blogsEntryService.getEntry(blogPostingId);

		return _toBlogPosting(
			_blogsEntryService.updateEntry(
				blogPostingId, blogPosting.getHeadline(),
				blogPosting.getAlternativeHeadline(),
				blogPosting.getFriendlyUrlPath(), blogPosting.getDescription(),
				blogPosting.getArticleBody(), localDateTime.getMonthValue() - 1,
				localDateTime.getDayOfMonth(), localDateTime.getYear(),
				localDateTime.getHour(), localDateTime.getMinute(), true, true,
				new String[0], blogPosting.getCaption(),
				_getImageSelector(blogPosting), null,
				_createServiceContext(blogsEntry.getGroupId(), blogPosting)));
	}

	private ServiceContext _createServiceContext(
		long groupId, BlogPosting blogPosting) {

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);

		String[] keywords = blogPosting.getKeywords();

		if (ArrayUtil.isNotEmpty(keywords)) {
			serviceContext.setAssetTagNames(keywords);
		}

		serviceContext.setScopeGroupId(groupId);

		return serviceContext;
	}

	private ImageObject _getImageObject(long imageId) throws Exception {
		FileEntry fileEntry = _dlAppService.getFileEntry(imageId);

		FileVersion fileVersion = _dlAppService.getFileVersion(
			fileEntry.getFileEntryId());

		return new ImageObject() {
			{
				setContentUrl(
					_dlurlHelper.getPreviewURL(
						fileEntry, fileVersion, null, "", false, false));
				setEncodingFormat(fileEntry.getMimeType());
				setFileExtension(fileEntry.getExtension());
				setId(fileEntry.getFileEntryId());
				setSizeInBytes(fileEntry.getSize());
				setTitle(fileEntry.getTitle());
			}
		};
	}

	private ImageSelector _getImageSelector(BlogPosting blogPosting) {
		ImageObject imageObject = blogPosting.getImage();

		Long imageId = imageObject.getId();

		if (Objects.equals(imageId, 0L)) {
			return null;
		}

		try {
			FileEntry fileEntry = _dlAppService.getFileEntry(imageId);

			return new ImageSelector(
				FileUtil.getBytes(fileEntry.getContentStream()),
				fileEntry.getFileName(), fileEntry.getMimeType(),
				"{\"height\": 0, \"width\": 0, \"x\": 0, \"y\": 0}");
		}
		catch (Exception e) {
			throw new BadRequestException(
				"Unable to get file entry " + imageId, e);
		}
	}

	private BlogPosting _toBlogPosting(BlogsEntry blogsEntry) throws Exception {
		ImageObject imageObject = _getImageObject(
			blogsEntry.getCoverImageFileEntryId());

		return new BlogPosting() {
			{
				setAlternativeHeadline(blogsEntry.getSubtitle());
				setArticleBody(blogsEntry.getContent());
				setCaption(blogsEntry.getCoverImageCaption());
				setContentSpace(blogsEntry.getGroupId());
				setDateCreated(blogsEntry.getCreateDate());
				setDateModified(blogsEntry.getModifiedDate());
				setDatePublished(blogsEntry.getDisplayDate());
				setDescription(blogsEntry.getDescription());
				setEncodingFormat("text/html");
				setFriendlyUrlPath(blogsEntry.getUrlTitle());
				setHeadline(blogsEntry.getTitle());
				setId(blogsEntry.getEntryId());
				setImage(imageObject);
			}
		};
	}

	@Reference
	private BlogsEntryService _blogsEntryService;

	@Reference
	private DLAppService _dlAppService;

	@Reference
	private DLURLHelper _dlurlHelper;

}