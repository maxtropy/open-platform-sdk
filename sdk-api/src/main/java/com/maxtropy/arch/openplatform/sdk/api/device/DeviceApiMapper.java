package com.maxtropy.arch.openplatform.sdk.api.device;

import com.maxtropy.arch.openplatform.sdk.core.api.Api;
import com.maxtropy.arch.openplatform.sdk.core.api.ApiMapper;
import com.maxtropy.arch.openplatform.sdk.core.model.CountResponse;
import com.maxtropy.arch.openplatform.sdk.core.auth.CredentialsProvider;
import com.maxtropy.arch.openplatform.sdk.core.common.HttpMethodEnum;
import com.maxtropy.arch.openplatform.sdk.core.common.ServiceClient;

import java.net.URI;

/**
 * @author luwang
 * @description
 * @date 2023/04/08
 */
public class DeviceApiMapper extends ApiMapper {
    public DeviceApiMapper(URI endpoint, ServiceClient serviceClient,
                           CredentialsProvider credentialsProvider) {
        super(endpoint, serviceClient, credentialsProvider);
    }

    public Api<CountResponse> alarmCountApi(Integer deviceId) {
        return Api.Builder.build(endpoint,
                credentialsProvider,
                serviceClient,
                "/device/alarm/count", HttpMethodEnum.GET, CountResponse.class)
                .addQueryParam("deviceId", deviceId + "");
    }


}
