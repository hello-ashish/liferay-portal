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

package com.liferay.document.library.uad.display;

import com.liferay.document.library.constants.DLPortletKeys;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFileEntryType;
import com.liferay.document.library.kernel.service.DLFileEntryTypeLocalService;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.user.associated.data.display.UADDisplay;

import java.io.Serializable;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	immediate = true, service = {DLFileEntryUADDisplay.class, UADDisplay.class}
)
public class DLFileEntryUADDisplay extends BaseDLFileEntryUADDisplay {

	@Override
	public String[] getColumnFieldNames() {
		return new String[] {"fileName", "description"};
	}

	@Override
	public String getEditURL(
			DLFileEntry dlFileEntry,
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse)
		throws Exception {

		PortletURL portletURL = liferayPortletResponse.createLiferayPortletURL(
			portal.getControlPanelPlid(liferayPortletRequest),
			DLPortletKeys.DOCUMENT_LIBRARY_ADMIN, PortletRequest.RENDER_PHASE);

		portletURL.setParameter(
			"mvcRenderCommandName", "/document_library/edit_file_entry");
		portletURL.setParameter(
			"redirect", portal.getCurrentURL(liferayPortletRequest));
		portletURL.setParameter(
			"fileEntryId", String.valueOf(dlFileEntry.getFileEntryId()));

		return portletURL.toString();
	}

	@Override
	public Map<String, Object> getFieldValues(
		DLFileEntry dlFileEntry, String[] fieldNames) {

		Map<String, Object> fieldValues = super.getFieldValues(
			dlFileEntry, fieldNames);

		List<String> fieldNamesList = Arrays.asList(fieldNames);

		if (fieldNamesList.contains("type")) {
			DLFileEntryType dlFileEntryType =
				dlFileEntryTypeLocalService.fetchDLFileEntryType(
					dlFileEntry.getFileEntryTypeId());

			String typeName = "--";

			if (dlFileEntryType != null) {
				typeName = dlFileEntryType.getName();
			}

			fieldValues.put("type", typeName);
		}

		return fieldValues;
	}

	@Override
	public Serializable getParentContainerId(DLFileEntry dlFileEntry) {
		return dlFileEntry.getFolderId();
	}

	@Reference
	protected DLFileEntryTypeLocalService dlFileEntryTypeLocalService;

	@Reference
	protected Portal portal;

}