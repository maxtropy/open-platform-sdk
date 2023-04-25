package com.maxtropy.arch.openplatform.sdk.core.http;

import com.maxtropy.arch.openplatform.sdk.core.api.QueryParam;
import com.maxtropy.arch.openplatform.sdk.core.model.RequestBody;

import java.util.List;

/**
 * @author luwang
 * @description
 * @date 2023/04/18
 */
public class HttpPostRequest extends HttpRequest {
    public HttpPostRequest(String url, RequestBody requestModel, List<QueryParam> queryParams) {
        super(url, requestModel, queryParams);
    }
}
