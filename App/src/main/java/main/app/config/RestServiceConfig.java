package main.app.config;

import lombok.extern.slf4j.Slf4j;
import main.app.exception.ConfigurationException;
import org.apache.http.HeaderElement;
import org.apache.http.HeaderElementIterator;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeaderElementIterator;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.ResourceUtils;
import org.springframework.web.client.RestTemplate;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import javax.net.ssl.SSLContext;
import java.util.concurrent.TimeUnit;

@Configuration
@Slf4j
public class RestServiceConfig {

    private static final int DEFAULT_KEEP_ALIVE_TIME_MILLIS = 20 * 1000;
    private static final int CLOSE_IDLE_CONNECTION_WAIT_TIME_SECS = 30;

    @Bean
    public CloseableHttpClient closeableHttpClient() throws ConfigurationException {
        return HttpClients.custom()
                .setSSLSocketFactory(sSLConnectionSocketFactory())
                .setConnectionManager(connectionManager())
                .setKeepAliveStrategy(connectionKeepAliveStrategy())
                .setDefaultRequestConfig(requestConfig())
                .build();
    }

    @Bean
    public Registry<ConnectionSocketFactory> connectionFactory() throws ConfigurationException {
        return RegistryBuilder
                .<ConnectionSocketFactory>create()
                .register("https", sSLConnectionSocketFactory())
                .register("http", new PlainConnectionSocketFactory())
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
    public PoolingHttpClientConnectionManager connectionManager(){
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(connectionFactory());
        connectionManager.setMaxTotal(25);
        connectionManager.setDefaultMaxPerRoute(15);
        return connectionManager;
    }

    @Bean
    public RequestConfig requestConfig(){
        return RequestConfig
                .custom()
                .setConnectionRequestTimeout(10000) // timeout to get connection from pool
                .setSocketTimeout(5000) // standard connection timeout
                .setConnectTimeout(8000) // standard connection timeout
                .build();
    }

    @Bean
    public RestTemplate restTemplate() throws Exception{
        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(closeableHttpClient());
        return new RestTemplate(requestFactory);
    }

    @Bean
    public ConnectionKeepAliveStrategy connectionKeepAliveStrategy() {
        return new ConnectionKeepAliveStrategy() {
            @Override
            public long getKeepAliveDuration(HttpResponse response, HttpContext context) {
                HeaderElementIterator it = new BasicHeaderElementIterator
                        (response.headerIterator(HTTP.CONN_KEEP_ALIVE));
                while (it.hasNext()) {
                    HeaderElement he = it.nextElement();
                    String param = he.getName();
                    String value = he.getValue();

                    if (value != null && param.equalsIgnoreCase("timeout")) {
                        return Long.parseLong(value) * 1000;
                    }
                }
                return DEFAULT_KEEP_ALIVE_TIME_MILLIS;
            }
        };
    }

    @Bean
    public Runnable idleConnectionMonitor(final PoolingHttpClientConnectionManager connectionManager) {
        return new Runnable() {
            @Override
            @Scheduled(fixedDelay = 10000)
            public void run() {
                try {
                    if (connectionManager != null) {
                        log.trace("run IdleConnectionMonitor - Closing expired and idle connections...");
                        connectionManager.closeExpiredConnections();
                        connectionManager.closeIdleConnections(CLOSE_IDLE_CONNECTION_WAIT_TIME_SECS, TimeUnit.SECONDS);
                    } else {
                        log.trace("run IdleConnectionMonitor - Http Client Connection manager is not initialised");
                    }
                } catch (Exception e) {
                    log.error("run IdleConnectionMonitor - Exception occurred. msg={}, e={}", e.getMessage(), e);
                }
            }
        };
    }
}
