package me.abel.security.controller;

import me.abel.security.response.ApiResult;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @RequestMapping("index")
    @Secured("ROLE_admin")
    public ApiResult login (){
        return ApiResult.success();
    }

}
