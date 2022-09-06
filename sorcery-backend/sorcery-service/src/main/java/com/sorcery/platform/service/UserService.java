package com.sorcery.platform.service;

import com.mysql.cj.util.StringUtils;
import com.sorcery.platform.constant.UserConstant;
import com.sorcery.platform.dao.UserDAO;
import com.sorcery.platform.domain.User;
import com.sorcery.platform.domain.UserInfo;
import com.sorcery.platform.exception.ConditionException;
import com.sorcery.platform.utils.MD5Util;
import com.sorcery.platform.utils.RSAUtil;
import com.sorcery.platform.utils.TokenUtil;
import com.sorcery.platform.vo.LoginUserVO;
import com.sorcery.platform.vo.RegisterUserVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 用户服务
 *
 * @author jinglv
 * @date 2022/9/1 10:29
 */
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserDAO userDAO;

    private static final String CHARSET = "UTF-8";

    /**
     * 用户注册
     *
     * @param registerUser 注册用户信息
     */
    @Transactional(rollbackFor = Exception.class)
    public void addUser(RegisterUserVO registerUser) {
        String userName = registerUser.getUserName();
        if (StringUtils.isNullOrEmpty(userName)) {
            throw new ConditionException("用户名不能为空！");
        }
        User dbUser = this.getUserByUserName(userName);
        if (dbUser != null) {
            throw new ConditionException("该手用户名已被注册！");
        }
        Date now = new Date();
        String salt = String.valueOf(now.getTime());
        String password = registerUser.getPassword();
        String rawPassword;
        try {
            rawPassword = RSAUtil.decrypt(password);
        } catch (Exception e) {
            throw new ConditionException("密码解密失败！");
        }
        User user = new User();
        String md5Password = MD5Util.sign(rawPassword, salt, CHARSET);
        user.setSalt(salt);
        user.setPassword(md5Password);
        user.setCreateTime(now);
        user.setUpdateTime(now);
        userDAO.addUser(user);
        // 添加用户信息
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(user.getId());
        userInfo.setNick(UserConstant.DEFAULT_NICK);
        userInfo.setGender(UserConstant.GENDER_FEMALE);
        userInfo.setBirth(UserConstant.DEFAULT_BIRTH);
        userInfo.setCreateTime(now);
        userInfo.setUpdateTime(now);
        userDAO.addUserInfo(userInfo);
    }

    /**
     * 用户登录
     *
     * @param user 用户登录信息
     * @return token
     * @throws Exception 异常
     */
    public Map<String, String> login(LoginUserVO user) throws Exception {
        String userName = user.getUserName();
        if (StringUtils.isNullOrEmpty(userName)) {
            throw new ConditionException("用户名不能为空！");
        }
        User dbUser = this.getUserByUserName(userName);
        if (dbUser == null) {
            throw new ConditionException("当前用户不存在！");
        }
        String rawPassword;
        try {
            rawPassword = RSAUtil.decrypt(user.getPassword());
        } catch (Exception e) {
            throw new ConditionException("密码解密失败！");
        }
        String salt = dbUser.getSalt();
        String md5Password = MD5Util.sign(rawPassword, salt, CHARSET);
        if (!md5Password.equalsIgnoreCase(dbUser.getPassword())) {
            throw new ConditionException("密码错误！");
        }
        Map<String, String> token = new HashMap<>(16);
        token.put("token", TokenUtil.generateToken(dbUser.getId()));
        return token;
    }

    public Map<String, Object> loginForDts(LoginUserVO user) throws Exception {
        String userName = user.getUserName() == null ? "" : user.getUserName();
        if (StringUtils.isNullOrEmpty(userName)) {
            throw new ConditionException("参数异常！");
        }
        User dbUser = userDAO.getUserByUserName(userName);
        if (dbUser == null) {
            throw new ConditionException("当前用户不存在！");
        }
        String password = user.getPassword();
        String rawPassword;
        try {
            rawPassword = RSAUtil.decrypt(password);
        } catch (Exception e) {
            throw new ConditionException("密码解密失败！");
        }
        String salt = dbUser.getSalt();
        String md5Password = MD5Util.sign(rawPassword, salt, CHARSET);
        if (!md5Password.equals(dbUser.getPassword())) {
            throw new ConditionException("密码错误！");
        }
        Long userId = dbUser.getId();
        String accessToken = TokenUtil.generateToken(userId);
        String refreshToken = TokenUtil.generateRefreshToken(userId);
        //保存refresh token到数据库
        userDAO.deleteRefreshTokenByUserId(userId);
        userDAO.addRefreshToken(refreshToken, userId, new Date(), new Date());
        Map<String, Object> result = new HashMap<>();
        result.put("accessToken", accessToken);
        result.put("refreshToken", refreshToken);
        return result;
    }

    public void logout(String refreshToken, Long userId) {
        userDAO.deleteRefreshToken(refreshToken, userId);
    }

    /**
     * 通过用户登录名查询用户信息
     *
     * @param userName 用户登录名
     * @return 用户信息
     */
    public User getUserByUserName(String userName) {
        return userDAO.getUserByUserName(userName);
    }

    /**
     * 通过用户ID查询用户信息
     *
     * @param userId 用户ID
     * @return 用户信息
     */
    public User getUserInfo(Long userId) {
        User user = userDAO.getUserById(userId);
        UserInfo userInfo = userDAO.getUserInfoByUserId(userId);
        user.setUserInfo(userInfo);
        return user;
    }

    /**
     * 更新用户信息
     *
     * @param userInfo 用户信息
     */
    @Transactional(rollbackFor = Exception.class)
    public void updateUserInfo(UserInfo userInfo) {
        userInfo.setUpdateTime(new Date());
        userDAO.updateUserInfo(userInfo);
    }

}
