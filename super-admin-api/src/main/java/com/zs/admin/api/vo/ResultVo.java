package com.zs.admin.api.vo;


import com.zs.admin.api.constant.Constant;
import lombok.Data;

/**
 * @Auther: zs
 * @Date: 2019/8/24 11:59
 * @Description:
 */
@Data
public class ResultVo {
    private Long code;
    private String msg;
    private Object data;

    public static ResultVo success(){
        ResultVo result = new ResultVo();
        result.setCode(Constant.RESULT_CODE_SUCCESS);
        return result;
    }

    public static ResultVo success(String msg){
        ResultVo result = new ResultVo();
        result.setMsg(msg);
        result.setCode(Constant.RESULT_CODE_SUCCESS);
        return result;
    }

    public static ResultVo fail(){
        ResultVo result = new ResultVo();
        result.setCode(Constant.RESULT_CODE_FAIL);
        return result;
    }

    public static ResultVo data(Object data){
        ResultVo result = new ResultVo();
        result.setCode(Constant.RESULT_CODE_SUCCESS);
        result.setData(data);
        return result;
    }

    public static ResultVo fail(String msg){
        ResultVo result = new ResultVo();
        result.setMsg(msg);
        result.setCode(Constant.RESULT_CODE_FAIL);
        return result;
    }
}
