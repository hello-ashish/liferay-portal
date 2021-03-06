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

package com.liferay.user.associated.data.web.internal.display.context;

import com.liferay.frontend.taglib.clay.servlet.taglib.display.context.SearchContainerManagementToolbarDisplayContext;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.LabelItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.LabelItemList;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.PortletURLUtil;

import java.util.List;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Samuel Trong Tran
 */
public class AnonymizeNonreviewableUADDataManagementToolbarDisplayContext
	extends SearchContainerManagementToolbarDisplayContext {

	public AnonymizeNonreviewableUADDataManagementToolbarDisplayContext(
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse,
		HttpServletRequest request, SearchContainer searchContainer) {

		super(
			liferayPortletRequest, liferayPortletResponse, request,
			searchContainer);

		_currentURL = PortletURLUtil.getCurrent(
			liferayPortletRequest, liferayPortletResponse);
		_liferayPortletResponse = liferayPortletResponse;
		_searchContainer = searchContainer;
	}

	@Override
	public String getClearResultsURL() {
		PortletURL clearResultsURL = getPortletURL();

		clearResultsURL.setParameter("navigation", (String)null);

		return clearResultsURL.toString();
	}

	public List<LabelItem> getFilterLabelItems() {
		return new LabelItemList() {
			{
				String navigation = getNavigation();

				if (!navigation.equals("all")) {
					add(
						labelItem -> {
							PortletURL removeLabelURL = getPortletURL();

							removeLabelURL.setParameter(
								"navigation", (String)null);

							labelItem.putData(
								"removeLabelURL", removeLabelURL.toString());

							labelItem.setCloseable(true);

							String label = String.format(
								"%s: %s", LanguageUtil.get(request, "status"),
								LanguageUtil.get(request, navigation));

							labelItem.setLabel(label);
						});
				}
			}
		};
	}

	@Override
	public int getItemsTotal() {
		return _searchContainer.getTotal();
	}

	public PortletURL getPortletURL() {
		try {
			return PortletURLUtil.clone(_currentURL, _liferayPortletResponse);
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(e, e);
			}

			return _liferayPortletResponse.createRenderURL();
		}
	}

	@Override
	public Boolean isSelectable() {
		return false;
	}

	@Override
	protected String[] getNavigationKeys() {
		return new String[] {"all", "pending", "done"};
	}

	@Override
	protected String[] getOrderByKeys() {
		return new String[] {"name", "items", "status"};
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AnonymizeNonreviewableUADDataManagementToolbarDisplayContext.class);

	private final PortletURL _currentURL;
	private final LiferayPortletResponse _liferayPortletResponse;
	private final SearchContainer _searchContainer;

}