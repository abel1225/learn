package me.abel.security.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

/**
 * @Description
 * @Author Abel.li
 * @Date 2021/10/25 下午18:44
 */
@Data
public class ApplicationException extends RuntimeException {

    private int status;
    private String msg;

    public ApplicationException(String msg) {
        super(msg + "["+HttpStatus.FORBIDDEN.value()+"]");
        this.status = HttpStatus.FORBIDDEN.value();
        this.msg = msg;
    }

    public ApplicationException(int status, String msg) {
        super(msg + "["+status+"]");
        this.status = status;
        this.msg = msg;
    }

}
