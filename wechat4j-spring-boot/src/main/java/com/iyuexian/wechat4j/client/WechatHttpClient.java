package com.iyuexian.wechat4j.client;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * 微信HTTP客户端
 * 封装HTTP请求功能
 * 
 * @author WeChat4j Team
 */
@Component
public class WechatHttpClient {

    private static final Logger logger = LoggerFactory.getLogger(WechatHttpClient.class);

    private final RestTemplate restTemplate;

    public WechatHttpClient() {
        this.restTemplate = new RestTemplate();
    }

    /**
     * GET请求
     */
    public String get(String url, Map<String, Object> params) throws Exception {
        return get(url, params, 30000);
    }

    /**
     * GET请求（带超时）
     */
    public String get(String url, Map<String, Object> params, int timeout) throws Exception {
        return get(url, params, new HashMap<>(), timeout);
    }

    /**
     * GET请求（带请求头）
     */
    public String get(String url, Map<String, Object> params, Map<String, String> headers) throws Exception {
        return get(url, params, headers, 30000);
    }

    /**
     * GET请求（完整参数）
     */
    public String get(String url, Map<String, Object> params, Map<String, String> headers, int timeout) throws Exception {
        try {
            // 构建URL参数
            StringBuilder urlBuilder = new StringBuilder(url);
            if (params != null && !params.isEmpty()) {
                urlBuilder.append("?");
                params.forEach((key, value) -> 
                    urlBuilder.append(key).append("=").append(value).append("&"));
                // 移除最后一个&
                urlBuilder.setLength(urlBuilder.length() - 1);
            }

            // 设置请求头
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.set("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36");
            if (headers != null) {
                headers.forEach(httpHeaders::set);
            }

            HttpEntity<String> entity = new HttpEntity<>(httpHeaders);
            
            logger.debug("GET请求: {}", urlBuilder.toString());
            
            ResponseEntity<String> response = restTemplate.exchange(
                urlBuilder.toString(), 
                HttpMethod.GET, 
                entity, 
                String.class
            );

            String responseBody = response.getBody();
            logger.debug("GET响应: {}", responseBody);
            
            return responseBody;

        } catch (Exception e) {
            logger.error("GET请求失败: {}", url, e);
            throw new Exception("HTTP GET请求失败: " + e.getMessage(), e);
        }
    }

    /**
     * POST JSON请求
     */
    public String postJson(String url, Object data, Map<String, String> headers) throws Exception {
        try {
            // 设置请求头
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);
            httpHeaders.set("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36");
            if (headers != null) {
                headers.forEach(httpHeaders::set);
            }

            String jsonData = JSON.toJSONString(data);
            HttpEntity<String> entity = new HttpEntity<>(jsonData, httpHeaders);

            logger.debug("POST请求: {}, 数据: {}", url, jsonData);

            ResponseEntity<String> response = restTemplate.exchange(
                url, 
                HttpMethod.POST, 
                entity, 
                String.class
            );

            String responseBody = response.getBody();
            logger.debug("POST响应: {}", responseBody);

            return responseBody;

        } catch (Exception e) {
            logger.error("POST请求失败: {}", url, e);
            throw new Exception("HTTP POST请求失败: " + e.getMessage(), e);
        }
    }

    /**
     * 下载文件
     */
    public byte[] downloadFile(String url) throws Exception {
        try {
            // 设置请求头
            HttpHeaders headers = new HttpHeaders();
            headers.set("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36");

            HttpEntity<String> entity = new HttpEntity<>(headers);

            logger.debug("下载文件: {}", url);

            ResponseEntity<byte[]> response = restTemplate.exchange(
                url, 
                HttpMethod.GET, 
                entity, 
                byte[].class
            );

            byte[] data = response.getBody();
            logger.debug("文件下载完成，大小: {} bytes", data != null ? data.length : 0);

            return data;

        } catch (Exception e) {
            logger.error("下载文件失败: {}", url, e);
            throw new Exception("文件下载失败: " + e.getMessage(), e);
        }
    }

    /**
     * POST表单请求
     */
    public String postForm(String url, Map<String, String> formData, Map<String, String> headers) throws Exception {
        try {
            // 设置请求头
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            httpHeaders.set("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36");
            if (headers != null) {
                headers.forEach(httpHeaders::set);
            }

            // 构建表单数据
            StringBuilder formBody = new StringBuilder();
            if (formData != null) {
                formData.forEach((key, value) -> 
                    formBody.append(key).append("=").append(value).append("&"));
                if (formBody.length() > 0) {
                    formBody.setLength(formBody.length() - 1); // 移除最后一个&
                }
            }

            HttpEntity<String> entity = new HttpEntity<>(formBody.toString(), httpHeaders);

            logger.debug("POST表单请求: {}, 数据: {}", url, formBody.toString());

            ResponseEntity<String> response = restTemplate.exchange(
                url, 
                HttpMethod.POST, 
                entity, 
                String.class
            );

            String responseBody = response.getBody();
            logger.debug("POST表单响应: {}", responseBody);

            return responseBody;

        } catch (Exception e) {
            logger.error("POST表单请求失败: {}", url, e);
            throw new Exception("HTTP POST表单请求失败: " + e.getMessage(), e);
        }
    }
}