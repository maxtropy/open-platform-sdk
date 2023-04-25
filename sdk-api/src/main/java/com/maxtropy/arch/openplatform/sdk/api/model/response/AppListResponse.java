package com.maxtropy.arch.openplatform.sdk.api.model.response;

import com.maxtropy.arch.openplatform.sdk.core.model.ResponseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class AppListResponse extends ResponseModel {

    /**
     * 分页总数
     */

    private Long total;

    /**
     * 分页页码
     */

    private Integer pageNum;

    /**
     * 分页大小
     */

    private Integer pageSize;

    /**
     * 分页数据
     */

    private List<Result> list;


    @Data
    public static class Result implements Serializable {

        /**
         * 表单数据
         */

        private List<AnswerResponse> answers;

        /**
         * 当前数据的id
         */

        private Long applyId;

    }

}