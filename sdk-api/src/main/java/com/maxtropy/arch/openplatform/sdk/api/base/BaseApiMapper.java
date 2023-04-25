package com.maxtropy.arch.openplatform.sdk.api.base;

import com.maxtropy.arch.openplatform.sdk.api.model.request.WorkFlowAppDataRequest;
import com.maxtropy.arch.openplatform.sdk.api.model.request.WorkflowProcessRequest;
import com.maxtropy.arch.openplatform.sdk.api.model.response.AppListResponse;
import com.maxtropy.arch.openplatform.sdk.api.model.response.CustomerItemResponse;
import com.maxtropy.arch.openplatform.sdk.core.api.*;
import com.maxtropy.arch.openplatform.sdk.core.auth.CredentialsProvider;
import com.maxtropy.arch.openplatform.sdk.core.common.HttpMethodEnum;
import com.maxtropy.arch.openplatform.sdk.core.common.ServiceClient;
import com.maxtropy.arch.openplatform.sdk.core.model.BooleanResponse;
import com.maxtropy.arch.openplatform.sdk.core.model.ListResponse;
import com.maxtropy.arch.openplatform.sdk.core.model.VoidResponse;

import java.net.URI;
import java.util.List;

/**
 * @author luwang
 * @description
 * @date 2023/04/09
 */
public class BaseApiMapper extends ApiMapper {
    public BaseApiMapper(URI endpoint, ServiceClient serviceClient, CredentialsProvider credentialsProvider) {
        super(endpoint, serviceClient, credentialsProvider);
    }

    public Api<ListResponse<CustomerItemResponse>> customerListApi(List<String> mcids) {
        Api<ListResponse<CustomerItemResponse>> api = Api.Builder
                .build(endpoint,
                credentialsProvider,
                serviceClient,
                "/base/customer/list", HttpMethodEnum.GET, ListResponse.class,
                        CustomerItemResponse.class);

        for (String mcid : mcids) {
            api.addQueryParam("mcids", mcid);
        }
        return api;
    }

    public Api<BooleanResponse> workflowProcessApi(WorkflowProcessRequest workflowProcessRequestModel) {
        return Api.Builder.build(endpoint,
                credentialsProvider,
                serviceClient,
                "/base/workflow/process", HttpMethodEnum.PUT, BooleanResponse.class)
                .setRequestBody(workflowProcessRequestModel);
    }

    public Api<AppListResponse>  workFlowAppData(String appId, WorkFlowAppDataRequest workFlowAppDataRequest) {
        return Api.Builder.build(endpoint,
                credentialsProvider,
                serviceClient,
                "/base/workflow/app/data/{appId}", HttpMethodEnum.POST, AppListResponse.class)
                .setRequestBody(workFlowAppDataRequest)
                .setPathVariables(appId);
    }

    public Api<VoidResponse> deleteFile(String key) {
        return Api.Builder.build(endpoint,
                credentialsProvider,
                serviceClient,
                "/base/file", HttpMethodEnum.DELETE, VoidResponse.class)
                .addQueryParam("key", key);
    }
}
