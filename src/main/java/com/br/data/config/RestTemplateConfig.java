package com.br.data.config;

import java.net.SocketException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

import javax.net.ssl.SSLContext;

import org.apache.http.NoHttpResponseException;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.BasicHttpClientConnectionManager;
import org.apache.http.ssl.TrustStrategy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;


@Configuration
public class RestTemplateConfig {

	@Value("${custom.rest.connection.timeout:3000}")
	private Integer timeout;

	@Value("${custom.rest.connection.readtimeout:3000}")
	private Integer readTimeout;
	
	@Value("${custom.rest.connection.retry:3000}")
	private Integer retry;
	
	@Bean
	public RestTemplate restTemplate() throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException {
	
        TrustStrategy acceptingTrustStrategy;
		acceptingTrustStrategy = new TrustStrategy() {
			@Override
			public boolean isTrusted(java.security.cert.X509Certificate[] chain, String authType)
					throws CertificateException {
				return true;
			}
		};
		
        SSLContext sslContext = org.apache.http.ssl.SSLContexts.custom().loadTrustMaterial(null, acceptingTrustStrategy).build();
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext, NoopHostnameVerifier.INSTANCE);
        
        Registry<ConnectionSocketFactory> socketConnectionFactory = RegistryBuilder.<ConnectionSocketFactory>create()
        		.register("https", sslsf)
        		.register("http", new PlainConnectionSocketFactory())
        		.build();
        
        BasicHttpClientConnectionManager connectionManager = new BasicHttpClientConnectionManager(socketConnectionFactory);
        
        
        
        
        CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(sslsf)
        		.setConnectionManager(connectionManager)
        		.setRetryHandler((exception, executionCount, context) -> {
        			if(executionCount > retry) {
        				return false;
        			}
        			return exception instanceof NoHttpResponseException || exception instanceof SocketException;
        		})
        		.build();
        
        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
		requestFactory.setConnectTimeout(timeout);
		requestFactory.setConnectionRequestTimeout(timeout);
		requestFactory.setReadTimeout(readTimeout);
		
		return new RestTemplate(requestFactory);
	}
}
