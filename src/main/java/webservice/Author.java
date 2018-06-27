package webservice;

import webservice.helper.PropertyLoader;

import java.net.PasswordAuthentication;
import java.util.Properties;

public class Author {
	private final static PropertyLoader LOADER = new PropertyLoader();
	private final static Properties properties = LOADER.getProperties();
	private static PasswordAuthentication author;

	static {
		java.net.Authenticator.setDefault(new java.net.Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				author = new PasswordAuthentication(properties.getProperty("username"),
						properties.getProperty("password").toCharArray());
				return author;
			}
		});
	}

	public Author() {
	}
}
