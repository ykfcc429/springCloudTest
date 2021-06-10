package com.member.service;

import javax.mail.MessagingException;

/**
 * @author yankaifeng
 * 创建日期 2021/6/8
 */
public interface MainService {

    /***
     * @author yankaifeng
     * //TODO true 账户密码存在 false反之
     * 2021/6/8 17:18
     * @param account  账户
     * @param password	密码
     * @return {@link boolean}
     **/
    boolean checkLoginAccount(String account, String password);

    String getToken(String account, String password, String ip);

    void sendVerificationCode(String receiver)throws MessagingException;
}
