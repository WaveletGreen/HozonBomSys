package com.connor.hozon.bom.common.config.security;

/**
 * Created with IntelliJ IDEA.
 * User: xlf
 * Date: 2018/12/3
 * Time: 13:15
 */

import com.connor.hozon.bom.common.base.constant.SystemStaticConst;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
/**
 * spring-security登陆的密码进行MD5加密传到数据库
 */
public class CustomPasswordEncoder implements PasswordEncoder {
    private final String code = "Connor";

    @Override
    public String encode(CharSequence rawPassword) {
        Md5PasswordEncoder encoder = new Md5PasswordEncoder();
        return encoder.encodePassword(rawPassword.toString(), SystemStaticConst.SALT);
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        Md5PasswordEncoder encoder = new Md5PasswordEncoder();
        return encoder.isPasswordValid(encodedPassword, rawPassword.toString(), SystemStaticConst.SALT);
    }
}