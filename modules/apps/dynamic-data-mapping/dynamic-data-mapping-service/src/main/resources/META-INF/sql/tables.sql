create table DDMContent (
	uuid_ VARCHAR(75) null,
	contentId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	name STRING null,
	description_ STRING null,
	data_ TEXT null
);

create table DDMDataProviderInstance (
	uuid_ VARCHAR(75) null,
	dataProviderInstanceId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	name STRING null,
	description_ TEXT null,
	definition_ TEXT null,
	type_ VARCHAR(75) null
);

create table DDMDataProviderInstanceLink (
	dataProviderInstanceLinkId LONG not null primary key,
	companyId LONG,
	dataProviderInstanceId LONG,
	structureId LONG
);

create table DDMFormInstance (
	uuid_ VARCHAR(75) null,
	formInstanceId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	versionUserId LONG,
	versionUserName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	structureId LONG,
	version VARCHAR(75) null,
	name STRING null,
	description_ STRING null,
	settings_ TEXT null,
	lastPublishDate DATE null
);

create table DDMFormInstanceRecord (
	uuid_ VARCHAR(75) null,
	formInstanceRecordId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	versionUserId LONG,
	versionUserName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	formInstanceId LONG,
	formInstanceVersion VARCHAR(75) null,
	storageId LONG,
	version VARCHAR(75) null,
	lastPublishDate DATE null
);

create table DDMFormInstanceRecordVersion (
	formInstanceRecordVersionId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	formInstanceId LONG,
	formInstanceVersion VARCHAR(75) null,
	formInstanceRecordId LONG,
	version VARCHAR(75) null,
	status INTEGER,
	statusByUserId LONG,
	statusByUserName VARCHAR(75) null,
	statusDate DATE null,
	storageId LONG
);

create table DDMFormInstanceVersion (
	formInstanceVersionId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	formInstanceId LONG,
	structureVersionId LONG,
	name STRING null,
	description_ STRING null,
	settings_ TEXT null,
	version VARCHAR(75) null,
	status INTEGER,
	statusByUserId LONG,
	statusByUserName VARCHAR(75) null,
	statusDate DATE null
);

create table DDMStorageLink (
	uuid_ VARCHAR(75) null,
	storageLinkId LONG not null primary key,
	companyId LONG,
	classNameId LONG,
	classPK LONG,
	structureId LONG,
	structureVersionId LONG
);

create table DDMStructure (
	uuid_ VARCHAR(75) null,
	structureId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	versionUserId LONG,
	versionUserName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	parentStructureId LONG,
	classNameId LONG,
	structureKey VARCHAR(75) null,
	version VARCHAR(75) null,
	name STRING null,
	description_ TEXT null,
	definition_ TEXT null,
	storageType VARCHAR(75) null,
	type_ INTEGER,
	lastPublishDate DATE null
);

create table DDMStructureLayout (
	uuid_ VARCHAR(75) null,
	structureLayoutId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	structureVersionId LONG,
	definition_ TEXT null
);

create table DDMStructureLink (
	structureLinkId LONG not null primary key,
	companyId LONG,
	classNameId LONG,
	classPK LONG,
	structureId LONG
);

create table DDMStructureVersion (
	structureVersionId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	structureId LONG,
	version VARCHAR(75) null,
	parentStructureId LONG,
	name STRING null,
	description_ TEXT null,
	definition_ TEXT null,
	storageType VARCHAR(75) null,
	type_ INTEGER,
	status INTEGER,
	statusByUserId LONG,
	statusByUserName VARCHAR(75) null,
	statusDate DATE null
);

create table DDMTemplate (
	uuid_ VARCHAR(75) null,
	templateId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	versionUserId LONG,
	versionUserName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	classNameId LONG,
	classPK LONG,
	resourceClassNameId LONG,
	templateKey VARCHAR(75) null,
	version VARCHAR(75) null,
	name TEXT null,
	description_ TEXT null,
	type_ VARCHAR(75) null,
	mode_ VARCHAR(75) null,
	language VARCHAR(75) null,
	script TEXT null,
	cacheable BOOLEAN,
	smallImage BOOLEAN,
	smallImageId LONG,
	smallImageURL STRING null,
	lastPublishDate DATE null
);

create table DDMTemplateLink (
	templateLinkId LONG not null primary key,
	companyId LONG,
	classNameId LONG,
	classPK LONG,
	templateId LONG
);

create table DDMTemplateVersion (
	templateVersionId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	classNameId LONG,
	classPK LONG,
	templateId LONG,
	version VARCHAR(75) null,
	name TEXT null,
	description_ TEXT null,
	language VARCHAR(75) null,
	script TEXT null,
	status INTEGER,
	statusByUserId LONG,
	statusByUserName VARCHAR(75) null,
	statusDate DATE null
);