package ${configYAML.apiPackagePath}.resource.${versionDirName}.test;

<#compress>
	<#list openAPIYAML.components.schemas?keys as schemaName>
		import ${configYAML.apiPackagePath}.dto.${versionDirName}.${schemaName};
	</#list>
</#compress>

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.vulcan.multipart.MultipartBody;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import io.restassured.specification.RequestSender;

import java.net.URL;

import javax.annotation.Generated;

import org.jboss.arquillian.test.api.ArquillianResource;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author ${configYAML.author}
 * @generated
 */
@Generated("")
public abstract class Base${schemaName}ResourceTestCase {

	@BeforeClass
	public static void setUpClass() {
		RestAssured.defaultParser = Parser.JSON;
	}

	@Before
	public void setUp() throws Exception {
		_resourceURL = new URL(_url.toExternalForm() + "/o${configYAML.application.baseURI}/${openAPIYAML.info.version}");
	}

	<#list javaTool.getJavaSignatures(openAPIYAML, schemaName) as javaSignature>
		@Test
		public void test${javaSignature.methodName?cap_first}() throws Exception {
			Assert.assertTrue(true);
		}
	</#list>

	<#list javaTool.getJavaSignatures(openAPIYAML, schemaName) as javaSignature>
		<@compress single_line=true>
			protected void invoke${javaSignature.methodName?cap_first}(
				<#list javaSignature.javaParameters as javaParameter>
					${javaParameter.parameterType} ${javaParameter.parameterName}

					<#if javaParameter_has_next>
						,
					</#if>
				</#list>
			) throws Exception {
		</@compress>

			RequestSender requestSender = _createRequestSender();

			requestSender.post(
				"${javaSignature.path}"
			);
		}
	</#list>

	private RequestSender _createRequestSender() {
		return RestAssured.given(
		).auth(
		).preemptive(
		).basic(
			"test@liferay.com", "test"
		).header(
			"Accept", "application/json"
		).header(
			"Content-Type", "application/json"
		).when(
		);
	}

	private static ObjectMapper _inputObjectMapper = new ObjectMapper() {
		{
			setSerializationInclusion(JsonInclude.Include.NON_NULL);
		}
	};
	private static ObjectMapper _outputObjectMapper = new ObjectMapper();

	private URL _resourceURL;

	@ArquillianResource
	private URL _url;

}