package com.nari.bidsystem.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author ZhangXD
 * @Date 2021/10/22 15:33
 * @Description
 */
@Configuration
public class GlobalException implements HandlerExceptionResolver {

    private static final Logger logger = LoggerFactory.getLogger(GlobalException.class);

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

        logger.warn("访问" + request.getRequestURI() + " 发生错误, 错误信息:" + ex.getMessage());

        ModelAndView mv = new ModelAndView();
        mv.addObject("error", ex.toString());
        return mv;
    }
}
