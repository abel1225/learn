package me.abel.security.response;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

/**
 * @Description
 * @Author Abel.li
 * @Date 2021/10/25 下午18:44
 */
@Data
@Builder
public class ApiResult {

    private int status;
    private String msg;

    public static ApiResult success (){
        return ApiResult.builder().status(200).msg("success").build();
    }

    public static ApiResult failure (){
        return ApiResult.builder().status(HttpStatus.FORBIDDEN.value()).msg("failure").build();
    }

}
