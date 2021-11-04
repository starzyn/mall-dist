package com.macro.mall.filter;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.server.HttpServerRequest;
import com.macro.mall.common.constant.AuthConstant;
import com.nimbusds.jose.JWSObject;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * <p>目前的做法知识应急做法，这种做法只是想替换现有项目中的 gateway 服务
 * 就个人认为，目前的 gateway 的功能只提供了黑白，认证，跨域的问题解决，
 * 对于这些功能，完全没必要去使用重量级的 gateway 服务来完成，使用 ng 完全能够做到
 * </p>
 * @author : zhan9yn
 * @version : 1.0
 * @date : 2021/11/4 10:07 上午
 */
@Component
@Slf4j
public class TokenFilter implements Filter {

    @SneakyThrows
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String token = ((HttpServletRequest) servletRequest).getHeader(AuthConstant.JWT_TOKEN_HEADER);
        if (StrUtil.isEmpty(token)) {
            filterChain.doFilter(request, servletResponse);
        }
        //从token中解析用户信息并设置到Header中去
        String realToken = token.replace(AuthConstant.JWT_TOKEN_PREFIX, "");
        JWSObject jwsObject = JWSObject.parse(realToken);
        String userStr = jwsObject.getPayload().toString();
        log.info("AuthGlobalFilter.filter() user:{}",userStr);
        request.setAttribute(AuthConstant.USER_TOKEN_HEADER, userStr);
        filterChain.doFilter(request, servletResponse);
    }
}
