package com.codezzz.mallauth.filter;

import com.alibaba.fastjson.JSON;
import com.codezzz.mallauth.config.WhiteConfig;
import com.codezzz.mallauth.constant.AuthConstant;
import com.codezzz.mallcore.exception.ErrorCode;
import com.codezzz.mallcore.model.dto.RespDTO;
import com.nimbusds.jose.JWSObject;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.http.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.Charset;
import java.text.ParseException;
import java.util.Objects;

/**
 * @author codezzz
 * @Description: 全局token解析过滤器
 * @date 2021/8/18 19:42
 */


@Component
@RequiredArgsConstructor
public class AuthenticateFilter implements GlobalFilter, Ordered {

    private static Logger LOGGER = LoggerFactory.getLogger(AuthenticateFilter.class);

    private final WhiteConfig whiteConfig;

    /**
     * 做认证
     * @param exchange
     * @param chain
     * @return
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path = exchange.getRequest().getPath().value();
        boolean isIgnored = whiteConfig.getPath().parallelStream().anyMatch(ign -> new AntPathMatcher().match(ign, path));
        String token = exchange.getRequest().getHeaders().getFirst(AuthConstant.JWT_TOKEN_HEADER);
        if (isIgnored) {
            return chain.filter(exchange);
        }

        if (StringUtils.isEmpty(token)) {
            return unauthenticated(exchange.getResponse());
        }else {
            try {
                //从token中解析用户信息并设置到Header中去
                String realToken = token.replace(AuthConstant.JWT_TOKEN_PREFIX, "");
                JWSObject jwsObject = JWSObject.parse(realToken);
                if (Objects.isNull(jwsObject)) {
                    return unauthenticated(exchange.getResponse());
                } else {
                    String userStr = jwsObject.getPayload().toString();
                    LOGGER.info("AuthGlobalFilter.filter() user:{}", userStr);
                    ServerHttpRequest request = exchange.getRequest().mutate().header(AuthConstant.USER_PREFIX, userStr).build();
                    exchange = exchange.mutate().request(request).build();
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }


        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 0;
    }

    private Mono<Void> unauthenticated(ServerHttpResponse response) {
        response.setStatusCode(HttpStatus.OK);
        response.getHeaders().set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        response.getHeaders().set("Access-Control-Allow-Origin","*");
        response.getHeaders().set("Cache-Control","no-cache");
        String body= JSON.toJSONString(RespDTO.onFail(ErrorCode.AUTHENTICATION_FAILED));
        DataBuffer buffer =  response.bufferFactory().wrap(body.getBytes(Charset.forName("UTF-8")));
        return response.writeWith(Mono.just(buffer));
    }
}
