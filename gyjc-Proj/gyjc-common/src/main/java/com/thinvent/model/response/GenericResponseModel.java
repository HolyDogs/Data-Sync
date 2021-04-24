package com.thinvent.model.response;

import com.thinvent.model.ResponseModel;
import lombok.Data;

/**
 * @description -> 响应通用对象
 * @author -> xufeng
 * @date ->
 **/

@Data
public class GenericResponseModel<T> extends ResponseModel
{
    private static final long serialVersionUID = 7100791756352030649L;
    private T data;

//    public GenericResponseModel(){}

//    public GenericResponseModel(String requestId)
//    {
//        this.requestId = requestId;
//    }
}
