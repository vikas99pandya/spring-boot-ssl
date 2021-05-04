package main.app.config;

import main.app.exception.ConfigurationException;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.util.ResourceUtils;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;

@Configuration
public class RestServiceConfig {

    @Bean
    public CloseableHttpClient closeableHttpClient() throws ConfigurationException {
        return HttpClients.custom()
                .setSSLSocketFactory(sSLConnectionSocketFactory())
                .build();
    }

    @Bean
    public SSLConnectionSocketFactory sSLConnectionSocketFactory() throws ConfigurationException {
       return new SSLConnectionSocketFactory(sslContext());
    }

    @Bean
    public SSLContext sslContext() throws ConfigurationException {
        try {
            return SSLContextBuilder
                    .create()
                    .loadTrustMaterial(ResourceUtils.getFile("classpath:CustClient.jks"), "password".toCharArray(),new TrustSelfSignedStrategy())
                    .useProtocol("TLS")
                    .build();
        }
        catch(Exception ex){
            throw new ConfigurationException("CONFIG", ex.getMessage(), ex);
        }
    }

    @Bean
    public RestTemplate restTemplate() throws Exception{
        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(closeableHttpClient());
        requestFactory.setConnectTimeout(10000); // 10 seconds
        requestFactory.setReadTimeout(10000);
        return new RestTemplate(requestFactory);
    }
}
