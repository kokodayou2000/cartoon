package org.example.exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.example.enums.BizCodeEnum;

/**
 * 自定义异常
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class BizException extends RuntimeException{
    private int code;
    private String msg;

    public BizException(int code,String msg){
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    public BizException(BizCodeEnum bizCodeEnum){
        super(bizCodeEnum.getMessage());
        this.code = bizCodeEnum.getCode();
        this.msg = bizCodeEnum.getMessage();
    }

}
