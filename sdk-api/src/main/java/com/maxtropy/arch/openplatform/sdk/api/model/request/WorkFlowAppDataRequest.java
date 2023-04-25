package com.maxtropy.arch.openplatform.sdk.api.model.request;

import com.maxtropy.arch.openplatform.sdk.core.model.RequestBody;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class WorkFlowAppDataRequest extends RequestBody {

    /**
     * 职员Code
     */

    private String staffCode;

    /**
     * 分页条数
     */

    private Long pageSize;

    /**
     * 分页页码
     */

    private Long pageNum;

    /**
     * 获取个人待办事项：
     * 1； 获取个人已办事项：2； 获取我发起的已通过：
     * 3； 获取我发起的已拒绝：4； 获取我发起的草稿：
     * 5； 获取我发起的待完善：6； 获取我发起的流程中：
     * 7； 获取全部数据：
     * 8； 获取全部已通过数据：
     * 9； 获取全部已拒绝数据：
     * 10； 获取全部流程中数据：
     * 11； 获取个人抄送事项：12
     */
    private Long type;

    private List<SortReq> sorts;

    private String queriesRel;

    private List<QueriesReq> queries;

    @Data
    public static class SortReq implements Serializable {

        /**
         * 排序字段
         */
        private Long queId;
        /**
         * 是否正序
         */
        private Boolean isAscend;

    }

    @Data
    @Builder
    public static class QueriesReq implements Serializable {

        /**
         * 表单模块的id
         */
        private Long queId;

        /**
         * 检索值
         */

        private String searchKey;

        /**
         * 检索值
         */

        private List<String> searchKeys;

        /**
         * 数字模块中，是搜索结果中最小值，日期类型，就是最早日期(format:yyyy-MM-dd HH:mm:ss)
         */
        private String minValue;

        /**
         * 数字模块中，是搜索结果中最大值，日期类型，就是最晚日期(format:yyyy-MM-dd HH:mm:ss)
         */
        private String maxValue;

        /**
         * 筛选数据范围， 1：全部，2：已填写，3：未填写
         */
        private Integer scope;

        /**
         * 模糊搜索全部字段数据的key值
         */
        private List<String> queryKey;


    }
}
