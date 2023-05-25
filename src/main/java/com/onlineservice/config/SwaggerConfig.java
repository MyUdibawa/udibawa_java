package com.onlineservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.google.common.base.Predicate;

import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.GrantType;
import springfox.documentation.service.OAuth;
import springfox.documentation.service.ResourceOwnerPasswordCredentialsGrant;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.ApiKeyVehicle;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import java.util.Collections;
import java.util.List;

import static com.google.common.collect.Lists.*;
import static com.google.common.base.Predicates.*;
import static springfox.documentation.builders.PathSelectors.*;

@PropertySource("classpath:swagger.properties")
@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Value("${app.client.id}")
	private String clientId;
	@Value("${app.client.secret}")
	private String clientSecret;
	@Value("${host.full.dns.auth.link}")
	private String authLink;
	@Value("${spring.application.name}")
	private String applicationName;

	private static final String BASE_CONTROLLER_PACKAGE = "com.onlineservice.controller";

	@Bean
	public Docket apis() {
		return new Docket(DocumentationType.SWAGGER_2).groupName(applicationName).select()
				.apis(RequestHandlerSelectors.basePackage(BASE_CONTROLLER_PACKAGE)).build()
				.securitySchemes(Collections.singletonList(securitySchema()))
				.securityContexts(Collections.singletonList(securityContext())).pathMapping("/")
				.useDefaultResponseMessages(false).apiInfo(apiInfo());
	}

	private OAuth securitySchema() {
		List<AuthorizationScope> authorizationScopeList = newArrayList();
		authorizationScopeList.add(new AuthorizationScope("read", "read all"));
		authorizationScopeList.add(new AuthorizationScope("write", "access all"));
		authorizationScopeList.add(new AuthorizationScope("trust", "trust all"));

		List<GrantType> grantTypes = newArrayList();
		GrantType creGrant = new ResourceOwnerPasswordCredentialsGrant(authLink + "/oauth/token");

		grantTypes.add(creGrant);

		return new OAuth("oauth2schema", authorizationScopeList, grantTypes);

	}

	private SecurityContext securityContext() {
		return SecurityContext.builder().securityReferences(defaultAuth()).forPaths(categoryPaths()).build();
	}

	@SuppressWarnings("unchecked")
	private Predicate<String> categoryPaths() {
		return or(regex("/admin.*"), regex("/termsandcondition.*"), regex("/useofinformation.*"));
	}

	private List<SecurityReference> defaultAuth() {

		final AuthorizationScope[] authorizationScopes = new AuthorizationScope[3];
		authorizationScopes[0] = new AuthorizationScope("read", "read all");
		authorizationScopes[1] = new AuthorizationScope("write", "write all");
		authorizationScopes[2] = new AuthorizationScope("trust", "trust all");

		return Collections.singletonList(new SecurityReference("oauth2schema", authorizationScopes));
	}

	@Bean
	public SecurityConfiguration securityInfo() {
		return new SecurityConfiguration(clientId, clientSecret, "realm", "swagger", "", ApiKeyVehicle.HEADER, "", " ");
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title(applicationName)
				.description("API can be tested in Swagger. Please Notify if any bugs or any new requirement")
				.contact(new Contact("Karthik Devadiga", "https://api.udibawa.com/", "karthikkbdevadiga@gmail.com"))
				.license("Apache License Version 2.0").licenseUrl("https://www.apache.org/licenses/LICENSE-2.0")
				.version("2.0.1").build();
	}

}
