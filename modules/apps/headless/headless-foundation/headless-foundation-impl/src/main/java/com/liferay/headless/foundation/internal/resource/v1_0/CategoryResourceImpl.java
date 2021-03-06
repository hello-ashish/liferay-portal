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

package com.liferay.headless.foundation.internal.resource.v1_0;

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.service.AssetCategoryService;
import com.liferay.asset.kernel.service.AssetVocabularyService;
import com.liferay.headless.foundation.dto.v1_0.Category;
import com.liferay.headless.foundation.internal.dto.v1_0.UserAccountUtil;
import com.liferay.headless.foundation.resource.v1_0.CategoryResource;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserService;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.util.Collections;
import java.util.List;
import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Javier Gamarra
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/category.properties",
	scope = ServiceScope.PROTOTYPE, service = CategoryResource.class
)
public class CategoryResourceImpl extends BaseCategoryResourceImpl {

	@Override
	public Category getCategories(Long categoryId) throws Exception {
		return _toCategory(_assetCategoryService.getCategory(categoryId));
	}

	@Override
	public Page<Category> getCategoriesCategoriesPage(
			Long categoryId, Pagination pagination)
		throws Exception {

		return Page.of(
			transform(
				_assetCategoryService.getChildCategories(
					categoryId, pagination.getStartPosition(),
					pagination.getEndPosition(), null),
				this::_toCategory),
			pagination,
			_assetCategoryService.getChildCategoriesCount(categoryId));
	}

	@Override
	public Page<Category> getVocabulariesCategoriesPage(
			Long vocabularyId, Pagination pagination)
		throws Exception {

		AssetVocabulary assetVocabulary = _assetVocabularyService.getVocabulary(
			vocabularyId);

		return Page.of(
			transform(
				_assetCategoryService.getVocabularyRootCategories(
					assetVocabulary.getGroupId(), vocabularyId,
					pagination.getStartPosition(), pagination.getEndPosition(),
					null),
				this::_toCategory),
			pagination,
			_assetCategoryService.getVocabularyRootCategoriesCount(
				assetVocabulary.getGroupId(), vocabularyId));
	}

	@Override
	public Category postCategoriesCategories(Long categoryId, Category category)
		throws Exception {

		AssetCategory assetCategory = _assetCategoryService.getCategory(
			categoryId);

		Group group = _groupService.getGroup(assetCategory.getGroupId());

		Locale locale = LocaleUtil.fromLanguageId(group.getDefaultLanguageId());

		return _toCategory(
			_assetCategoryService.addCategory(
				group.getGroupId(), categoryId,
				Collections.singletonMap(locale, category.getName()),
				Collections.singletonMap(locale, category.getDescription()),
				assetCategory.getVocabularyId(), null, new ServiceContext()));
	}

	@Override
	public Category postVocabulariesCategories(
			Long vocabularyId, Category category)
		throws Exception {

		AssetVocabulary assetVocabulary = _assetVocabularyService.getVocabulary(
			vocabularyId);

		Group group = _groupService.getGroup(assetVocabulary.getGroupId());

		Locale locale = LocaleUtil.fromLanguageId(group.getDefaultLanguageId());

		return _toCategory(
			_assetCategoryService.addCategory(
				assetVocabulary.getGroupId(), 0,
				Collections.singletonMap(locale, category.getName()),
				Collections.singletonMap(locale, category.getDescription()),
				vocabularyId, null, new ServiceContext()));
	}

	private Category[] _toCategories(AssetCategory assetCategory)
		throws Exception {

		List<Category> categories = transform(
			assetCategory.getAncestors(), this::_toCategory);

		return categories.toArray(new Category[categories.size()]);
	}

	private Category _toCategory(AssetCategory assetCategory) throws Exception {
		return new Category() {
			{
				setAvailableLanguages(assetCategory.getAvailableLanguageIds());

				if (assetCategory.getParentCategory() != null) {
					setCategory(_toCategory(assetCategory.getParentCategory()));
					setCategoryId(assetCategory.getParentCategoryId());
				}

				setCreator(
					UserAccountUtil.toUserAccount(
						_userService.getUserById(assetCategory.getUserId())));
				setCreatorId(assetCategory.getUserId());
				setDateCreated(assetCategory.getCreateDate());
				setDateModified(assetCategory.getModifiedDate());
				setDescription(
					assetCategory.getDescription(
						acceptLanguage.getPreferredLocale()));
				setId(assetCategory.getCategoryId());
				setName(
					assetCategory.getTitle(
						acceptLanguage.getPreferredLocale()));
				setSubcategories(_toCategories(assetCategory));
				setVocabularyId(assetCategory.getVocabularyId());
			}
		};
	}

	@Reference
	private AssetCategoryService _assetCategoryService;

	@Reference
	private AssetVocabularyService _assetVocabularyService;

	@Reference
	private GroupService _groupService;

	@Reference
	private UserService _userService;

}