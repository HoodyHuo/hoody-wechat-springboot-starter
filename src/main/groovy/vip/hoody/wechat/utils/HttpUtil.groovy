package vip.hoody.wechat.utils

import jline.internal.Nullable
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder

@Component
class HttpUtil {
    private RestTemplate restTemplate

    HttpUtil() {
        this.restTemplate = new RestTemplate()
    }

    ResponseEntity<String> doGetRequest(
            String url,
            @Nullable Map<String, ?> variables) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
        if (variables != null) {
            variables.each { key, value ->
                builder.queryParam(key, value)
            }
        }
        final String finalUrl = builder.build().encode().toUri()
        // 发起Get请求
        return restTemplate.getForEntity(finalUrl, String.class)
    }
}
