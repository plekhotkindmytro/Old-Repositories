package dmytro;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoginBean {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(LoginBean.class);

	private String email;
	private String password;

	public String getEmail() {
		return email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(final String password) {
		this.password = password;
	}

	public void fakeLogin() {
		LOGGER.info("Email address: {}, Password: {}", email, password);
	}
}
