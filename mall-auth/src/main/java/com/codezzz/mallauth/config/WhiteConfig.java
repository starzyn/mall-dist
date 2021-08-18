package com.codezzz.mallauth.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author codezzz
 * @Description: 路由白名单配置
 * @date 2021/8/18 19:43
 */

@ConfigurationProperties(prefix = "mall.url.ignore")
@Component
public class WhiteConfig {
    private List<String> whiteUrls;

}
