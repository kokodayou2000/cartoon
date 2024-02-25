package org.example.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.example.utils.JsonData;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 异常处理器
 */
@ControllerAdvice //如果添加了 RequestBody就返回JSON格式的数据
@Slf4j
public class BizExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public JsonData handler(Exception e){

        if (e instanceof BizException){
            BizException bizException = (BizException)e;
            log.error("[业务异常{}]",e.getMessage());
            return JsonData.buildCodeAndMsg(bizException.getCode(), bizException.getMsg());
        }

        log.error("[非业务异常{}]",e.getMessage());
        return JsonData.buildError("全局异常，未知错误");

    }
}
