package com.maxtropy.arch.openplatform.sdk.api.dmes;

import com.maxtropy.arch.openplatform.sdk.core.api.ApiMapper;
import com.maxtropy.arch.openplatform.sdk.core.auth.CredentialsProvider;
import com.maxtropy.arch.openplatform.sdk.core.common.ServiceClient;

import java.net.URI;

/**
 * @author luwang
 * @description
 * @date 2023/04/09
 */
public class DmesMapper extends ApiMapper {
    public DmesMapper(URI endpoint, ServiceClient serviceClient, CredentialsProvider credentialsProvider) {
        super(endpoint, serviceClient, credentialsProvider);
    }
}
