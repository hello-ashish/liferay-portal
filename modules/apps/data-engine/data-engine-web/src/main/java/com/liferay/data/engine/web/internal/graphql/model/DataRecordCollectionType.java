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

package com.liferay.data.engine.web.internal.graphql.model;

import java.util.List;

/**
 * @author Leonardo Barros
 */
public class DataRecordCollectionType implements DataRecordCollection {

	@Override
	public DataDefinition getDataDefinition() {
		return _dataDefinition;
	}

	@Override
	public String getDataRecordCollectionId() {
		return _dataRecordCollectionId;
	}

	@Override
	public List<LocalizedValueType> getDescriptions() {
		return _descriptions;
	}

	@Override
	public List<LocalizedValueType> getNames() {
		return _names;
	}

	@Override
	public void setDataDefinition(DataDefinition dataDefinition) {
		_dataDefinition = dataDefinition;
	}

	@Override
	public void setDataRecordCollectionId(String dataRecordCollectionId) {
		_dataRecordCollectionId = dataRecordCollectionId;
	}

	@Override
	public void setDescriptions(List<LocalizedValueType> descriptions) {
		_descriptions = descriptions;
	}

	@Override
	public void setNames(List<LocalizedValueType> names) {
		_names = names;
	}

	private DataDefinition _dataDefinition;
	private String _dataRecordCollectionId;
	private List<LocalizedValueType> _descriptions;
	private List<LocalizedValueType> _names;

}