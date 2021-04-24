package com.thinvent.model;

import lombok.Data;

/**
 * @description -> 响应通用对象
 * @author -> xufeng
 * @date ->
 **/

@Data
public class ResponseModel
{
    private static final long serialVersionUID = 5413727785722549217L;
    protected Integer errCode;
    protected String errMsg;
    protected String token;
//    protected String requestId;
}
