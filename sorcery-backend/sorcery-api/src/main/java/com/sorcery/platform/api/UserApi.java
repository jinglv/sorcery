package com.sorcery.platform.api;

import cn.hutool.json.JSONUtil;
import com.sorcery.platform.domain.JsonResponse;
import com.sorcery.platform.domain.User;
import com.sorcery.platform.domain.UserInfo;
import com.sorcery.platform.service.UserService;
import com.sorcery.platform.support.UserSupport;
import com.sorcery.platform.utils.RSAUtil;
import com.sorcery.platform.vo.user.LoginUserVO;
import com.sorcery.platform.vo.user.RegisterUserVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 用户相关接口
 *
 * @author jinglv
 * @date 2022/9/1 10:30
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
@Api(tags = "用户相关接口", value = "UserApi", protocols = "JSON")
public class UserApi {

    private final UserService userService;

    private final UserSupport userSupport;

    @ApiOperation(value = "用户注册")
    @PostMapping("/users")
    public JsonResponse<String> addUser(@RequestBody RegisterUserVO user) {
        userService.addUser(user);
        return JsonResponse.success();
    }

    @ApiOperation(value = "获取RSA公钥")
    @GetMapping("/rsa-pks")
    public JsonResponse<String> getRsaPublicKey() {
        String pk = RSAUtil.getPublicKeyStr();
        return JsonResponse.success(pk);
    }

    @ApiOperation(value = "获取认证token")
    @PostMapping("/user-tokens")
    public JsonResponse<Map<String, String>> login(@RequestBody LoginUserVO user) throws Exception {
        Map<String, String> loginToken = userService.login(user);
        return JsonResponse.success(loginToken);
    }

    @ApiOperation(value = "获取认证dts token")
    @PostMapping("/user-dts")
    public JsonResponse<Map<String, Object>> loginForDts(@RequestBody LoginUserVO user) throws Exception {
        log.info("登录用户信息：{}", JSONUtil.parse(user));
        Map<String, Object> login = userService.loginForDts(user);
        return JsonResponse.success(login);
    }

    @ApiOperation(value = "用户注销")
    @DeleteMapping("/logout")
    public JsonResponse<String> logout(HttpServletRequest request) {
        String refreshToken = request.getHeader("refreshToken");
        Long userId = userSupport.getCurrentUserId();
        userService.logout(refreshToken, userId);
        return JsonResponse.success();
    }

    @ApiOperation(value = "获取用户信息")
    @GetMapping("/users")
    public JsonResponse<User> getUserInfo() {
        Long userId = userSupport.getCurrentUserId();
        User user = userService.getUserInfo(userId);
        log.info("查询到用户需信息: {}", JSONUtil.parse(user));
        return JsonResponse.success(user);
    }

    @ApiOperation(value = "更新用户信息")
    @PutMapping("/users-info")
    public JsonResponse<String> updateUserInfo(@RequestBody UserInfo userInfo) {
        Long userId = userSupport.getCurrentUserId();
        userInfo.setUserId(userId);
        userService.updateUserInfo(userInfo);
        return JsonResponse.success();
    }

}
