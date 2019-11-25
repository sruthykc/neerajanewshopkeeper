package com.diviso.graeshoppe.config;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.common.OAuth2AccessToken;

import com.corundumstudio.socketio.AuthorizationListener;
import com.corundumstudio.socketio.HandshakeData;
import com.corundumstudio.socketio.SocketIOServer;

@Configuration
public class SocketIOServerConfiguration {

	// private OAuth2RestTemplate oAuth2RestTemplate;

	@Value("${socket.server.host}")
	private String host;

	@Value("${socket.server.port}")
	private Integer port;

	@Bean
	public SocketIOServer getSocketIoServer() {
		System.out.println("Host is " + host + " port is " + port);
		com.corundumstudio.socketio.Configuration configuration = new com.corundumstudio.socketio.Configuration();
		configuration.setHostname(host);
		configuration.setPort(port);
//		configuration.setKeyStorePassword("password");
//		InputStream stream = SocketIOServerConfiguration.class.getResourceAsStream("/config/tls/kc.pkcs12");
//		configuration.setKeyStore(stream);
		SocketIOServer ioServer = new SocketIOServer(configuration);
		ioServer.start();

//		configuration.setAuthorizationListener(new AuthorizationListener() {
//
//			@Override
//			public boolean isAuthorized(HandshakeData data) {
//				if (oAuth2RestTemplate.getOAuth2ClientContext().getAccessToken().isExpired()) {
//					return false;
//				} else {
//					return false;
//				}
//			}
//		});

		return ioServer;
	}

}