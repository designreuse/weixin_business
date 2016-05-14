package com.cht.emm.security;

/* Copyright 2004, 2005, 2006 Acegi Technology Pty Limited
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;


import org.hibernate.criterion.Restrictions;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.authentication.dao.SaltSource;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.authentication.encoding.PlaintextPasswordEncoder;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.Assert;

import com.cht.emm.common.dao.util.ConditionQuery;
import com.cht.emm.model.ThirdPartConfig;
import com.cht.emm.model.User;
import com.cht.emm.model.UserDetail;
import com.cht.emm.model.id.UserGroup;
import com.cht.emm.model.id.UserRole;
import com.cht.emm.security.entity.UsernamePasswordAuthenticationTokenExt;
import com.cht.emm.service.RoleService;
import com.cht.emm.service.ThirdPartConfigService;
import com.cht.emm.service.UserService;
import com.cht.emm.util.ClassCache;
import com.cht.emm.util.PropertiesReader;
import com.cht.emm.util.RemoteValidator;
import com.cht.emm.util.Response;
import com.cht.emm.util.UUIDGen;


/**
 * An {@link AuthenticationProvider} implementation that retrieves user details
 * from a {@link UserDetailsService}.
 * 
 * @author Ben Alex
 * @author Rob Winch
 */
public class MyDaoAuthenticationProvider extends
		AbstractUserDetailsAuthenticationProvider {
	// ~ Static fields/initializers
	// =====================================================================================

	/**
	 * The plaintext password used to perform
	 * {@link PasswordEncoder#isPasswordValid(String, String, Object)} on when
	 * the user is not found to avoid SEC-2056.
	 */
	private static final String USER_NOT_FOUND_PASSWORD = "userNotFoundPassword";

	// ~ Instance fields
	// ================================================================================================

	private PasswordEncoder passwordEncoder;

	/**
	 * The password used to perform
	 * {@link PasswordEncoder#isPasswordValid(String, String, Object)} on when
	 * the user is not found to avoid SEC-2056. This is necessary, because some
	 * {@link PasswordEncoder} implementations will short circuit if the
	 * password is not in a valid format.
	 */
	private String userNotFoundEncodedPassword;

	private SaltSource saltSource;

	private UserDetailsService userDetailsService;

	private UserService userService;
	
	private RoleService roleService;

	private PropertiesReader propertiesReader;
	
	private ThirdPartConfigService thirdPartConfigService;

	public MyDaoAuthenticationProvider() {
		setPasswordEncoder(new PlaintextPasswordEncoder());
	}

	// ~ Methods
	// ========================================================================================================

	/**
	 * 验证用户
	 */
	@SuppressWarnings("deprecation")
	protected void additionalAuthenticationChecks(UserDetails userDetails,
			UsernamePasswordAuthenticationToken authentication)
			throws AuthenticationException {
		Object salt = null;

		if (this.saltSource != null) {
			salt = this.saltSource.getSalt(userDetails);
		}

		if (authentication.getCredentials() == null) {
			logger.debug("Authentication failed: no credentials provided");

			throw new BadCredentialsException(messages.getMessage(
					"AbstractUserDetailsAuthenticationProvider.badCredentials",
					"Bad credentials"), userDetails);
		}

		String presentedPassword = authentication.getCredentials().toString();

		// 可以到第三方系统中去验证

		if (!passwordEncoder.isPasswordValid(userDetails.getPassword(),
				presentedPassword, salt)) {
			logger.debug("Authentication failed: password does not match stored value");

			throw new BadCredentialsException(messages.getMessage(
					"AbstractUserDetailsAuthenticationProvider.badCredentials",
					"Bad credentials"), userDetails);
		}
	}

	protected void doAfterPropertiesSet() throws Exception {
		Assert.notNull(this.userDetailsService,
				"A UserDetailsService must be set");
	}

	protected final UserDetails retrieveUser(String username,
			UsernamePasswordAuthenticationToken authentication)
			throws AuthenticationException {
		UserDetails loadedUser = null;

		String userSource = ((UsernamePasswordAuthenticationTokenExt) authentication)
				.getUserSource();
		if("local".equals(userSource)){
			userSource = null;
		}
		String password = (String) authentication.getCredentials();
		if (userSource != null) {
			ThirdPartConfig thirdPartConfig = thirdPartConfigService
					.get(userSource);
			
			try {
				RemoteValidator validator = (RemoteValidator) ClassCache
						.getInstance().getClassInstance(
								thirdPartConfig.getClassName());
				Response response = validator.valid(username, password,thirdPartConfig.getRemoteUrl(),
						thirdPartConfig.getOthers());
				if (response.isSuccessful()) {
					ConditionQuery query = new ConditionQuery();
					query.add(Restrictions.eq("username", username + "|"
							+ userSource));
					List<User> users = userService.listAll(query, null, -1, -1);
					if (users == null || users.size() == 0) {
						User newUser = new User();
						newUser.setId(UUIDGen.getUUID());
						newUser.setUsername(username + "|" + userSource);
						newUser.setPassword(password);
						newUser.setThirdPartConfig(thirdPartConfig);
						newUser.setUserType(User.UserType.COMMON.getType());
						newUser.setUserSource(1);
						newUser.save();

						UserDetail detail = new UserDetail();
						detail.setId(newUser.getId());
						detail.setCreateTime(new Timestamp(new Date().getTime()));
						detail.setUser(newUser);
						detail.save();

						UserGroup userGroup = new UserGroup();
						userGroup.setId(UUIDGen.getUUID());
						userGroup.setUser(newUser);
						userGroup.setGroup(thirdPartConfig.getGroup());
						userGroup.save();

						UserRole userRole = new UserRole();
						userRole.setId(UUIDGen.getUUID());
						userRole.setUser(newUser);
						userRole.setRole(roleService.get("user"));
						userRole.save();
						
						UserRole comUserRole = new UserRole(UUIDGen.getUUID(),newUser,roleService.get(ConstantId.ROLE_COMMON_ID));
						comUserRole.save();
					} else {
						User user = users.get(0);
						user.setPassword(password);
						user.update();
					}
					loadedUser = this.getUserDetailsService().loadUserByUsername(
							username + "|" + userSource);
				}
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 
			// 本地验证
		} else {
			try {
				loadedUser = this.getUserDetailsService().loadUserByUsername(
						username);
			} catch (UsernameNotFoundException notFound) {
				if (authentication.getCredentials() != null) {
					String presentedPassword = authentication.getCredentials()
							.toString();
				}
				throw notFound;
			} catch (Exception repositoryProblem) {
				throw new AuthenticationServiceException(
						repositoryProblem.getMessage(), repositoryProblem);
			}
		}

		if (loadedUser == null) {
			throw new AuthenticationServiceException(
					"UserDetailsService returned null, which is an interface contract violation");
		}
		return loadedUser;
	}

	/**
	 * Sets the PasswordEncoder instance to be used to encode and validate
	 * passwords. If not set, the password will be compared as plain text.
	 * <p>
	 * For systems which are already using salted password which are encoded
	 * with a previous release, the encoder should be of type
	 * {@code org.springframework.security.authentication.encoding.PasswordEncoder}
	 * . Otherwise, the recommended approach is to use
	 * {@code org.springframework.security.crypto.password.PasswordEncoder}.
	 * 
	 * @param passwordEncoder
	 *            must be an instance of one of the {@code PasswordEncoder}
	 *            types.
	 */
	public void setPasswordEncoder(Object passwordEncoder) {
		Assert.notNull(passwordEncoder, "passwordEncoder cannot be null");

		if (passwordEncoder instanceof PasswordEncoder) {
			setPasswordEncoder((PasswordEncoder) passwordEncoder);
			return;
		}

		if (passwordEncoder instanceof org.springframework.security.crypto.password.PasswordEncoder) {
			final org.springframework.security.crypto.password.PasswordEncoder delegate = (org.springframework.security.crypto.password.PasswordEncoder) passwordEncoder;
			setPasswordEncoder(new PasswordEncoder() {
				public String encodePassword(String rawPass, Object salt) {
					checkSalt(salt);
					return delegate.encode(rawPass);
				}

				public boolean isPasswordValid(String encPass, String rawPass,
						Object salt) {
					checkSalt(salt);
					return delegate.matches(rawPass, encPass);
				}

				private void checkSalt(Object salt) {
					Assert.isNull(salt,
							"Salt value must be null when used with crypto module PasswordEncoder");
				}
			});

			return;
		}

		throw new IllegalArgumentException(
				"passwordEncoder must be a PasswordEncoder instance");
	}

	private void setPasswordEncoder(PasswordEncoder passwordEncoder) {
		Assert.notNull(passwordEncoder, "passwordEncoder cannot be null");

		this.userNotFoundEncodedPassword = passwordEncoder.encodePassword(
				USER_NOT_FOUND_PASSWORD, null);
		this.passwordEncoder = passwordEncoder;
	}

	protected PasswordEncoder getPasswordEncoder() {
		return passwordEncoder;
	}

	/**
	 * The source of salts to use when decoding passwords. <code>null</code> is
	 * a valid value, meaning the <code>DaoAuthenticationProvider</code> will
	 * present <code>null</code> to the relevant <code>PasswordEncoder</code>.
	 * <p>
	 * Instead, it is recommended that you use an encoder which uses a random
	 * salt and combines it with the password field. This is the default
	 * approach taken in the
	 * {@code org.springframework.security.crypto.password} package.
	 * 
	 * @param saltSource
	 *            to use when attempting to decode passwords via the
	 *            <code>PasswordEncoder</code>
	 */
	public void setSaltSource(SaltSource saltSource) {
		this.saltSource = saltSource;
	}

	protected SaltSource getSaltSource() {
		return saltSource;
	}

	public void setUserDetailsService(UserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}

	protected UserDetailsService getUserDetailsService() {
		return userDetailsService;
	}

	/**
	 * @return the userService
	 */
	public UserService getUserService() {
		return userService;
	}

	/**
	 * @param userService the userService to set
	 */
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	/**
	 * @return the roleService
	 */
	public RoleService getRoleService() {
		return roleService;
	}

	/**
	 * @param roleService the roleService to set
	 */
	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}

	/**
	 * @return the propertiesReader
	 */
	public PropertiesReader getPropertiesReader() {
		return propertiesReader;
	}

	/**
	 * @param propertiesReader the propertiesReader to set
	 */
	public void setPropertiesReader(PropertiesReader propertiesReader) {
		this.propertiesReader = propertiesReader;
	}

	/**
	 * @return the thirdPartConfigService
	 */
	public ThirdPartConfigService getThirdPartConfigService() {
		return thirdPartConfigService;
	}

	/**
	 * @param thirdPartConfigService the thirdPartConfigService to set
	 */
	public void setThirdPartConfigService(
			ThirdPartConfigService thirdPartConfigService) {
		this.thirdPartConfigService = thirdPartConfigService;
	}
}
