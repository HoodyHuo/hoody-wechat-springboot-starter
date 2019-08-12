package vip.hoody.wechat.utils

import jline.internal.Nullable
import org.springframework.boot.configurationprocessor.json.JSONObject
import org.springframework.core.io.FileSystemResource
import org.springframework.core.io.Resource
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
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
    Map<String, Object> doGetRequest(
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
        Map<String, Object> result = restTemplate.getForObject(finalUrl, Map.class)
        return result
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

    /**
     * 新增其他类型永久素材
     * @param url
     * @param filePath
     * @param json
     * @return
     */
    Map<String, String> doPostRequestWithFileAndJson(String url, String filePath, String json) {
        HttpHeaders headers = new HttpHeaders()
        headers.setContentType(MediaType.MULTIPART_FORM_DATA)
        headers.setConnection("Keep-Alive")
        headers.setCacheControl("no-cache")
        FileSystemResource resource = new FileSystemResource(new File(filePath))
        MultiValueMap map = new LinkedMultiValueMap<String, Object>()
        map.put("media", resource)
        map.put("description", json)
        HttpEntity<MultiValueMap> httpEntity = new HttpEntity<>(map, headers)
        Map<String, String> re = restTemplate.postForObject(url, httpEntity, Map.class)
        return re
    }

    /**
     * 发起Post请求,
     * 使用multipart/form-data
     * @param filePath 文件路径
     * @return
     */
    Map<String, Object> doPostRequestWithFile(String url, String filePath) {
        HttpHeaders headers = new HttpHeaders()
        headers.setContentType(MediaType.MULTIPART_FORM_DATA)
        headers.setConnection("Keep-Alive")
        headers.setCacheControl("no-cache")
        FileSystemResource resource = new FileSystemResource(new File(filePath))
        MultiValueMap<String,Object> param = new LinkedMultiValueMap<>()
        param.put("file")
        String result = restTemplate.postForObject(url, param, String.class)
        return [data: result]
    }

    /**
     * 发起Post请求,
     *
     * @param url 地址
     * @param json 请求json
     * @return 下载文件 流
     */
    InputStream getInputStreamDoPostRequestWithJson(String url, String json) {
        HttpHeaders headers = new HttpHeaders()
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8)
        HttpEntity<String> entity = new HttpEntity<String>(json, headers)
        ResponseEntity<Resource> responseEntity = restTemplate.postForEntity(url, entity, Resource.class);
        InputStream inputStream = responseEntity.getBody().getInputStream()
        return inputStream
    }
}
