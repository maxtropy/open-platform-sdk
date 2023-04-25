package com.maxtropy.arch.openplatform.sdk.api.model.response;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class AnswerResponse implements Serializable {
    /**
     * 表单模块id
     */
    private Long queId;

    /**
     * 表单模块标题
     */
    private String queTitle;

    private Integer queType;

    private List<DataValueResponse> values;

    private List<List<AnswerResponse>> tableValues;

    @Data
    public class DataValueResponse implements Serializable {

        private String id;
        /**
         * 传入的值
         */

        private String value;

        /**
         * 人员选择的时候需要将值传入该字段 而不是传入value
         */
        private String optionId;

        /**
         * 其他信息
         */
        private String otherInfo;

    }

}
