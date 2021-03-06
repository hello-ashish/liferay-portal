<%--
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
--%>

<%@ include file="/init.jsp" %>

<%
long assetCategoryId = ParamUtil.getLong(request, "categoryId");

if (assetCategoryId > 0) {
	AssetCategory assetCategory = AssetCategoryLocalServiceUtil.getCategory(assetCategoryId);

	PortalUtil.setPageKeywords(HtmlUtil.escape(assetCategory.getTitle(locale)), request);
}

String assetTagName = ParamUtil.getString(request, "tag");

if (Validator.isNotNull(assetTagName)) {
	PortalUtil.setPageKeywords(assetTagName, request);
}

if (assetPublisherDisplayContext.isEnableTagBasedNavigation() && !assetPublisherDisplayContext.isSelectionStyleAssetList() && assetPublisherDisplayContext.isSelectionStyleManual() && ((assetPublisherDisplayContext.getAllAssetCategoryIds().length > 0) || (assetPublisherDisplayContext.getAllAssetTagNames().length > 0))) {
	assetPublisherDisplayContext.setSelectionStyle("dynamic");
}
%>

<div class="subscribe-action">
	<c:if test="<%= !portletName.equals(AssetPublisherPortletKeys.HIGHEST_RATED_ASSETS) && !portletName.equals(AssetPublisherPortletKeys.MOST_VIEWED_ASSETS) && !portletName.equals(AssetPublisherPortletKeys.RECENT_CONTENT) && !portletName.equals(AssetPublisherPortletKeys.RELATED_ASSETS) && PortletPermissionUtil.contains(permissionChecker, 0, layout, portletDisplay.getId(), ActionKeys.SUBSCRIBE, false, false) && assetPublisherWebUtil.getEmailAssetEntryAddedEnabled(portletPreferences) %>">
		<c:choose>
			<c:when test="<%= assetPublisherWebUtil.isSubscribed(themeDisplay.getCompanyId(), user.getUserId(), themeDisplay.getPlid(), portletDisplay.getId()) %>">
				<portlet:actionURL name="unsubscribe" var="unsubscribeURL">
					<portlet:param name="redirect" value="<%= currentURL %>" />
				</portlet:actionURL>

				<liferay-ui:icon
					icon="start"
					label="<%= true %>"
					markupView="lexicon"
					message="unsubscribe"
					url="<%= unsubscribeURL %>"
				/>
			</c:when>
			<c:otherwise>
				<portlet:actionURL name="subscribe" var="subscribeURL">
					<portlet:param name="redirect" value="<%= currentURL %>" />
				</portlet:actionURL>

				<liferay-ui:icon
					icon="start-o"
					label="<%= true %>"
					markupView="lexicon"
					message="subscribe"
					url="<%= subscribeURL %>"
				/>
			</c:otherwise>
		</c:choose>
	</c:if>

	<%
	boolean enableRSS = !PortalUtil.isRSSFeedsEnabled() ? false : assetPublisherDisplayContext.isEnableRSS();
	%>

	<c:if test="<%= enableRSS %>">
		<liferay-portlet:resourceURL id="getRSS" varImpl="rssURL" />

		<liferay-rss:rss
			resourceURL="<%= rssURL %>"
		/>
	</c:if>
</div>

<%
PortletURL portletURL = renderResponse.createRenderURL();

if (assetCategoryId > 0) {
	portletURL.setParameter("categoryId", String.valueOf(assetCategoryId));
}

SearchContainer searchContainer = new SearchContainer(renderRequest, null, null, SearchContainer.DEFAULT_CUR_PARAM, assetPublisherDisplayContext.getDelta(), portletURL, null, null);

if (!assetPublisherDisplayContext.isPaginationTypeNone()) {
	searchContainer.setDelta(assetPublisherDisplayContext.getDelta());
	searchContainer.setDeltaConfigurable(false);
}
%>

<c:if test="<%= assetPublisherDisplayContext.isShowMetadataDescriptions() %>">
	<liferay-asset:categorization-filter
		assetType="content"
		portletURL="<%= portletURL %>"
	/>
</c:if>

<%
request.setAttribute("view.jsp-viewInContext", assetPublisherDisplayContext.isAssetLinkBehaviorViewInPortlet());
%>

<c:choose>
	<c:when test="<%= assetPublisherDisplayContext.isSelectionStyleDynamic() %>">
		<%@ include file="/view_dynamic_list.jspf" %>
	</c:when>
	<c:when test="<%= assetPublisherDisplayContext.isSelectionStyleManual() %>">
		<%@ include file="/view_manual.jspf" %>
	</c:when>
	<c:otherwise>

		<%
		Map<Long, List<AssetPublisherAddItemHolder>> scopeAssetPublisherAddItemHolders = assetPublisherDisplayContext.getScopeAssetPublisherAddItemHolders(1);
		%>

		<c:if test="<%= MapUtil.isEmpty(scopeAssetPublisherAddItemHolders) && !((assetCategoryId > 0) || Validator.isNotNull(assetTagName)) %>">

			<%
			renderRequest.setAttribute(WebKeys.PORTLET_CONFIGURATOR_VISIBILITY, Boolean.TRUE);
			%>

		</c:if>

		<div class="alert alert-info text-center">
			<div>
				<liferay-ui:message key="this-application-is-not-visible-to-users-yet" />
			</div>

			<div>
				<aui:a href="javascript:;" onClick="<%= portletDisplay.getURLConfigurationJS() %>"><liferay-ui:message key="select-an-asset-list-to-make-it-visible" /></aui:a>
			</div>
		</div>
	</c:otherwise>
</c:choose>

<c:if test="<%= !assetPublisherDisplayContext.isPaginationTypeNone() && (searchContainer.getTotal() > searchContainer.getResults().size()) %>">
	<liferay-ui:search-paginator
		searchContainer="<%= searchContainer %>"
		type="<%= assetPublisherDisplayContext.getPaginationType() %>"
	/>
</c:if>

<aui:script use="querystring-parse">
	var queryString = window.location.search.substring(1);

	var queryParamObj = new A.QueryString.parse(queryString);

	var assetEntryId = queryParamObj['<portlet:namespace />assetEntryId'];

	if (assetEntryId) {
		window.location.hash = assetEntryId;
	}
</aui:script>

<%!
private static Log _log = LogFactoryUtil.getLog("com_liferay_asset_publisher_web.view_jsp");
%>