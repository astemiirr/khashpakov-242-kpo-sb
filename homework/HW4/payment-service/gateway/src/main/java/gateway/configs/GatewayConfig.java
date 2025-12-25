package gateway.configs;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;

@Configuration
public class GatewayConfig {
    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                      .route("orders", r -> r.path("/orders/**").uri("http://order-service:8080"))
                      .route("payments", r -> r.path("/payments/**").uri("http://payments-service:8080"))
                      .route(
                              "orders-swagger", r -> r.path("/v3/api-docs/orders").filters(f -> f.modifyResponseBody(
                                      String.class, String.class, (exchange, body) -> {
                                          String modified = body.replaceAll(
                                                  "\"servers\":\\[" + ".*?\\]",
                                                  "\"servers\":[{\"url\":\"\"}]"
                                          );
                                          return Mono.just(modified);
                                      }
                              )).uri("http://order-service:8080")
                      )
                      .route(
                              "payments-swagger",
                              r -> r.path("/v3/api-docs/payments").filters(f -> f.modifyResponseBody(
                                      String.class, String.class, (exchange, body) -> {
                                          String modified = body.replaceAll(
                                                  "\"servers\":\\[.*?\\]",
                                                  "\"servers\":[{\"url\":\"\"}]"
                                          );
                                          return Mono.just(modified);
                                      }
                              )).uri("http://payments-service:8080")
                      )
                      .build();
    }
}