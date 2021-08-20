//package com.codezzz.mallcore.config;
//
//import com.codezzz.mallcore.model.dto.RespDTO;
//import com.codezzz.mallcore.properties.BaseRespProperties;
//import com.codezzz.mallcore.util.JsonMapper;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.core.MethodParameter;
//import org.springframework.http.MediaType;
//import org.springframework.http.converter.HttpMessageConverter;
//import org.springframework.http.converter.StringHttpMessageConverter;
//import org.springframework.http.server.ServerHttpRequest;
//import org.springframework.http.server.ServerHttpResponse;
//import org.springframework.http.server.ServletServerHttpResponse;
//import org.springframework.lang.NonNull;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
//
//import javax.servlet.http.HttpServletResponse;
//import java.util.List;
//import java.util.regex.Pattern;
//
///**
// * 根据规范，应该统一返回 {@link RespDTO}，但是为了防止有些同学忘记返回，所以会对返回做一次校验处理
// */
//@RestControllerAdvice
//@RequiredArgsConstructor
//public class BaseResponseAdvice implements ResponseBodyAdvice<Object> {
//
//    @Value("${api-full-prefix:}")
//    private String prefix;
//
//    private final BaseRespProperties properties;
//
//    @Override
//    public boolean supports(@NonNull MethodParameter returnType, @NonNull Class<? extends HttpMessageConverter<?>> converterType) {
//        return true;
//    }
//
//    @Override
//    public Object beforeBodyWrite(Object body, @NonNull MethodParameter returnType, @NonNull MediaType contentType,
//                                  @NonNull Class<? extends HttpMessageConverter<?>> converterType,
//                                  @NonNull ServerHttpRequest request, @NonNull ServerHttpResponse response) {
//        ServletServerHttpResponse servletServerHttpResponse = (ServletServerHttpResponse) response;
//        HttpServletResponse realResponse = servletServerHttpResponse.getServletResponse();
//        // 均返回 Json 格式
//        // 主要是为了处理 body 为 String 类型时，后面的 JsonMapper.toNonNullJson(resp) 后
//        // 将会以 content-type 为 text/plain 返回 Json 字符串
//        realResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
//        // 得到请求路径
//        String path = request.getURI().getPath();
//        // 如果路径不在配置需要包装的路径中，直接返回
//        if (!match(properties.getIncludes(), path))
//            return body;
//        // 没有返回，直接返回 RespDTO.onSuc() 这样能将 void 返回一起处理
//        if (body == null)
//            // 如果是 String 需要单独处理，不然 RespDTO 继续被 StringHttpMessageConverter 处理会报错
//            return (converterType == StringHttpMessageConverter.class) ? JsonMapper.toNonNullJson(RespDTO.onSuc()) : RespDTO.onSuc();
//        // 已经是 RespDTO(避免被包两层) 或者不是项目路径开头直接返回(避免其他框架如 Swagger 等的请求被包装)
//        if (body instanceof RespDTO || match(properties.getExcludes(), request.getURI().getPath()))
//            return body;
//        // 其他类型直接包装
//        RespDTO<Object> resp = RespDTO.onSuc(body);
//        // 如果是 String 需要单独处理，不然 RespDTO 继续被 StringHttpMessageConverter 处理会报错
//        return (body instanceof String) ? JsonMapper.toNonNullJson(resp) : resp;
//    }
//
//    private boolean match(@NonNull List<String> pattens, String uri) {
//        return pattens.stream().map(Pattern::compile).anyMatch(p -> p.matcher(uri).find());
//    }
//}