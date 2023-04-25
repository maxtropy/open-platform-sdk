package com.maxtropy.arch.openplatform.sdk.core.http;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.maxtropy.arch.openplatform.sdk.core.api.QueryParam;
import com.maxtropy.arch.openplatform.sdk.core.model.RequestBody;
import com.maxtropy.arch.openplatform.sdk.core.exception.SdkException;
import com.maxtropy.arch.openplatform.sdk.core.util.JsonUtil;

import java.util.List;

/**
 * @author luwang
 * @description
 * @date 2023/04/18
 */
public class HttpPutRequest extends HttpRequest {

    public HttpPutRequest(String url, RequestBody requestModel, List<QueryParam> queryParams) {
        super(url, requestModel, queryParams);
    }



}
