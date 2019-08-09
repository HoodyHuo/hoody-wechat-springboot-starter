package vip.hoody.wechat.utils

import jline.internal.Nullable
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
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

    /**
     * 发起GET请求
     * @param url 请求的URL地址
     * @param variables 请求参数 ,会添加在url中
     * @return
     */
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

    /**
     * 发起POST请求,参数为JSON
     * @param url 请求的URL地址
     * @param jsonString 请求的JSON字符串
     * @return
     */
    Map<String, Object> doPostRequestWithJson(
            String url,
            String jsonString) {

        HttpHeaders headers = new HttpHeaders()
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8)
        HttpEntity<String> entity = new HttpEntity<String>(jsonString, headers)
        Map<String, Object> result = restTemplate.postForObject(url, entity, Map.class)
        return result
    }
}
