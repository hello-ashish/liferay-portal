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

package com.liferay.headless.web.experience.internal.resource.v1_0;

import com.liferay.headless.web.experience.dto.v1_0.ContentStructure;
import com.liferay.headless.web.experience.dto.v1_0.StructuredContent;
import com.liferay.headless.web.experience.resource.v1_0.StructuredContentResource;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.util.TransformUtil;

import java.util.Collections;
import java.util.List;

import javax.annotation.Generated;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public abstract class BaseStructuredContentResourceImpl
	implements StructuredContentResource {

	@Override
	public Response deleteStructuredContents(Long structuredContentId)
		throws Exception {

		Response.ResponseBuilder responseBuilder = Response.ok();

		return responseBuilder.build();
	}

	@Override
	public Page<StructuredContent> getContentSpacesContentStructuresStructuredContentsPage(
			Long contentSpaceId, Long contentStructureId, Filter filter,
			Pagination pagination, Sort[] sorts)
		throws Exception {

		return Page.of(Collections.emptyList());
	}

	@Override
	public Page<StructuredContent> getContentSpacesStructuredContentsPage(
			Long contentSpaceId, Filter filter, Pagination pagination,
			Sort[] sorts)
		throws Exception {

		return Page.of(Collections.emptyList());
	}

	@Override
	public ContentStructure getContentStructures(Long contentStructureId)
		throws Exception {

		return new ContentStructure();
	}

	@Override
	public StructuredContent getStructuredContents(Long structuredContentId)
		throws Exception {

		return new StructuredContent();
	}

	@Override
	public Page<Long> getStructuredContentsCategoriesPage(
			Long structuredContentId, Pagination pagination)
		throws Exception {

		return Page.of(Collections.emptyList());
	}

	@Override
	public StructuredContent patchContentSpacesStructuredContents(
			Long contentSpaceId, StructuredContent structuredContent)
		throws Exception {

		return new StructuredContent();
	}

	@Override
	public StructuredContent postContentSpacesStructuredContents(
			Long contentSpaceId, StructuredContent structuredContent)
		throws Exception {

		return new StructuredContent();
	}

	@Override
	public StructuredContent postContentSpacesStructuredContentsBatchCreate(
			Long contentSpaceId, StructuredContent structuredContent)
		throws Exception {

		return new StructuredContent();
	}

	@Override
	public Response postStructuredContentsCategories(
			Long structuredContentId, Long referenceId)
		throws Exception {

		Response.ResponseBuilder responseBuilder = Response.ok();

		return responseBuilder.build();
	}

	@Override
	public Response postStructuredContentsCategoriesBatchCreate(
			Long structuredContentId, Long referenceId)
		throws Exception {

		Response.ResponseBuilder responseBuilder = Response.ok();

		return responseBuilder.build();
	}

	@Override
	public StructuredContent putStructuredContents(
			Long structuredContentId, StructuredContent structuredContent)
		throws Exception {

		return new StructuredContent();
	}

	protected Response buildNoContentResponse() {
		Response.ResponseBuilder responseBuilder = Response.noContent();

		return responseBuilder.build();
	}

	protected <T, R> List<R> transform(
		List<T> list, UnsafeFunction<T, R, Exception> unsafeFunction) {

		return TransformUtil.transform(list, unsafeFunction);
	}

	@Context
	protected AcceptLanguage acceptLanguage;

	@Context
	protected Company company;

}