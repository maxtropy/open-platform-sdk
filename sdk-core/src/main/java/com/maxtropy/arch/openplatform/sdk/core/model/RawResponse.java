package com.maxtropy.arch.openplatform.sdk.core.model;

import com.maxtropy.arch.openplatform.sdk.core.exception.ApiFailException;
import com.maxtropy.arch.openplatform.sdk.core.exception.SdkException;
import com.maxtropy.arch.openplatform.sdk.core.util.JsonUtil;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

/**
 * @author luwang
 * @description
 * @date 2023/04/09
 */
public class RawResponse<T> implements Serializable {

    private String code;

    private String httpStatus;

    private String errorMsg;

    private Class<T> respClazz;

    private String rawData;

    public RawResponse(Class<T> respClazz) {
        this.respClazz = respClazz;

    }

    public void setRawData(String rawData) {
        this.rawData = rawData;
    }

    public String getRawData() {
        return rawData;
    }

    public <I> T get(Class<I> itemClazz) throws ApiFailException {
        if (!isSuccess()) {
            throw new ApiFailException(httpStatus, code, errorMsg);
        }

        if (respClazz.equals(CountResponse.class)) {
            CountResponse countResponseModel = new CountResponse();
            countResponseModel.setCount(Integer.parseInt(rawData));
            return (T)countResponseModel;
        }

        if (respClazz.equals(BooleanResponse.class)) {
            BooleanResponse booleanResponseModel = new BooleanResponse();
            booleanResponseModel.setSuccess(Boolean.parseBoolean(rawData));
            return (T) booleanResponseModel;
        }

        if (respClazz.equals(VoidResponse.class)) {
            try {
                return respClazz.getDeclaredConstructor().newInstance();
            } catch (Exception e) {
                throw new SdkException("初始化出错", e);
            }
        }
        try {
            if (respClazz.equals(ListResponse.class)) {
                if (itemClazz ==  null) {
                    throw new SdkException("请传入要解析的item Class类型");
                }
                ListResponse<I> listRes = new ListResponse();
                List<I> items = JsonUtil.jsonStr2BeanList(rawData, itemClazz);
                listRes.setItems(items);
                return (T) listRes;
            }
            return JsonUtil.jsonStr2Bean(rawData, respClazz);
        } catch (IOException e) {
            throw new SdkException("解析出错", e);
        }
    }

    public boolean isSuccess() {
        return "1".equals(code) && "200".equals(httpStatus);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(String httpStatus) {
        this.httpStatus = httpStatus;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
