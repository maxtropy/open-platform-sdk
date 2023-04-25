package com.maxtropy.arch.openplatform.sdk.core.http;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.maxtropy.arch.openplatform.sdk.core.api.QueryParam;
import com.maxtropy.arch.openplatform.sdk.core.exception.SdkException;
import com.maxtropy.arch.openplatform.sdk.core.model.RequestBody;
import com.maxtropy.arch.openplatform.sdk.core.util.JsonUtil;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author luwang
 * @description
 * @date 2023/04/18
 */
public class HttpRequest implements Serializable {

    private Map<String, String> headerMap = new HashMap<>();

    protected String url;

    protected RequestBody requestModel;

    private List<QueryParam> queryParams;

    public HttpRequest(String url, RequestBody requestModel, List<QueryParam> queryParams) {
        this.url = url;
        this.requestModel = requestModel;
        this.queryParams = queryParams;
    }

    public void addHeader(String key, String value) {
        headerMap.put(key, value);
    }

    public Map<String, String> getHeaderMap() {
        return headerMap;
    }

    public String getUrl() {
        return url;
    }

    public List<QueryParam> getQueryParams() {
        return queryParams;
    }

    public String getRequestBody() {
        if (requestModel == null) {
            return "";
        }
        try {
            return JsonUtil.bean2JsonStr(requestModel);
        } catch (JsonProcessingException e) {
            throw new SdkException("解析异常", e);
        }
    }
}
