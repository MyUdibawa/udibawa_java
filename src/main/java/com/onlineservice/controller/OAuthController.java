package com.onlineservice.controller;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("oauth")
public class OAuthController {
	@Autowired
	DataSource dataSource;

	@Bean
	public TokenStore tokenStore() {
		return (TokenStore) new JdbcTokenStore(this.dataSource);
	}

	@PostMapping("revoke/{accessToken}")
	public void revokeAccessToken(@PathVariable("accessToken") String accessToken) {
		if (accessToken != null) {
			OAuth2AccessToken t = tokenStore().readAccessToken(accessToken);
			if (t != null)
				tokenStore().removeAccessToken(t);
			if (t.getRefreshToken() != null)
				tokenStore().removeRefreshToken(t.getRefreshToken());
		}
	}

}
