package com.onlineservice.user.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class MyUserDetails implements UserDetails {

	private static final long serialVersionUID = 1L;
	private String username;
	private String password;
	private Collection<? extends GrantedAuthority> authorities;
	private boolean isAccountNonLocked;
	private boolean isEnabled;

	public MyUserDetails(User user) {
		this.username = user.getEmail();
		this.password = user.getPassword();
		this.isAccountNonLocked = user.getIsAccountNonLocked();
		this.isEnabled = user.getIsEnabled();
		List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
		if (user instanceof AdminUser) {
			grantedAuthorities.add(new SimpleGrantedAuthority("ADMIN"));
		} else if (user instanceof ServiceCenterAdminUser) {
			grantedAuthorities.add(new SimpleGrantedAuthority("SERVICE_CENTER_ADMIN"));
		} else if (user instanceof ServiceEngineerUser) {
			grantedAuthorities.add(new SimpleGrantedAuthority("SERVICE_ENGINEER"));
		} else if (user instanceof CustomerUser) {
			grantedAuthorities.add(new SimpleGrantedAuthority("CUSTOMER"));
		}
		this.authorities = grantedAuthorities;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return isAccountNonLocked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return isEnabled;
	}

}
