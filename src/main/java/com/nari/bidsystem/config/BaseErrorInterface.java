package com.nari.bidsystem.config;

/**
 * @Author ZhangXD
 * @Date 2021/10/21 15:21
 * @Description
 */
public interface BaseErrorInterface {

    /**
     * 获取错误码
     * @return String类型的字符串
     */
    String getErrorCode();

    /**
     * 获取错误信息
     * @return String类型的字符串
     */
    String getErrorMessage();

}
