package com.carhub.api;

import com.carhub.api.proxy.filter.AuthHeaderFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;

@SpringBootApplication
@EnableSwagger2
@EnableZuulProxy
@EnableEurekaClient
@ComponentScan({"com.carhub.api.proxy"})
public class ProxyApplication {
	public static void main(String[] args) {
		SpringApplication.run(ProxyApplication.class, args);
	}

	@Bean
	AuthHeaderFilter authHeaderFilter() {
		return new AuthHeaderFilter();
	}

    @Bean
    UiConfiguration uiConfig() {
        return new UiConfiguration("validatorUrl", "list", "alpha", "schema",
                UiConfiguration.Constants.DEFAULT_SUBMIT_METHODS, false, true, 60000L);
    }

    @Bean
    public FallbackProvider ProxyFallbackProvider() {
	    return new FallbackProvider() {
			@Override
			public String getRoute() {
				// Might be confusing: it's the serviceId property and not the route
                // * to fallback all routed services
				return "*";
			}

			@Override
			public ClientHttpResponse fallbackResponse(String route, Throwable cause) {
				return new ClientHttpResponse() {
					@Override
					public HttpStatus getStatusCode() throws IOException {
						return HttpStatus.SERVICE_UNAVAILABLE;
					}

					@Override
					public int getRawStatusCode() throws IOException {
						return HttpStatus.SERVICE_UNAVAILABLE.value();
					}

					@Override
					public String getStatusText() throws IOException {
						return HttpStatus.SERVICE_UNAVAILABLE.toString();
					}

					@Override
					public void close() {}

					@Override
					public InputStream getBody() throws IOException {
						return new ByteArrayInputStream(
                                String.format(
                                        "{\n" +
                                        "    \"time\": \"" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime()) +"\",\n" +
                                        "    \"level\": \"error\",\n" +
                                        "    \"httpStatus\": \"" + getStatusCode().name() + "\",\n" +
                                        "    \"status\": " + getRawStatusCode() + ",\n" +
                                        "    \"message\": \"Service "+ firstToUpperCase(route) + " is down.\",\n" +
                                        "    \"component\": \"" + firstToUpperCase(route) + " API.\"\n" +
                                        "}"
						).getBytes());
					}

					private String firstToUpperCase(String input){
                        return input.substring(0, 1).toUpperCase() + input.substring(1);
                    }

					@Override
					public HttpHeaders getHeaders() {
						HttpHeaders headers = new HttpHeaders();
						headers.setContentType(MediaType.APPLICATION_JSON);
						headers.setAccessControlAllowCredentials(true);
						headers.setAccessControlAllowOrigin("*");
						return headers;
					}
				};
			}
		};
    }


}
