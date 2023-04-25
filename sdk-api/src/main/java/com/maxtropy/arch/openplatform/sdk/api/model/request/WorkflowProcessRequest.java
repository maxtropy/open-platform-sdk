package com.maxtropy.arch.openplatform.sdk.api.model.request;

import com.maxtropy.arch.openplatform.sdk.core.model.RequestBody;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author luwang
 * @description
 * @date 2023/04/19
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class WorkflowProcessRequest extends RequestBody {

    private String applyId;
    private String auditFeedback;
    private Integer auditNodeId;
    private Integer auditResult;
    private String staffCode;
}
