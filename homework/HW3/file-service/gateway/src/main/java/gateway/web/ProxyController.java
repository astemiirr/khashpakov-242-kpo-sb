package gateway.web;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@AllArgsConstructor
@Tag(name = "Gateway", description = "Сборник всех API")
public class ProxyController {
    private RestTemplate restTemplate;

    @GetMapping("/v3/api-docs/storage")
    public ResponseEntity<String> proxyServiceA() {
        String url = "http://storage:8081/v3/api-docs";
        return restTemplate.getForEntity(url, String.class);
    }

    @GetMapping("/v3/api-docs/analysis")
    public ResponseEntity<String> proxyServiceB() {
        String url = "http://analysis:8082/v3/api-docs";
        return restTemplate.getForEntity(url, String.class);
    }
}