package com.codezzz.mallcore.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author felixu
 * @since 2021.07.06
 */
@Data
@Component
//@ConfigurationProperties(prefix = "eztest.base-response")
public class BaseRespProperties {

    /**
     * 需要被包装的路径
     */
    private List<String> includes = new ArrayList<>();

    /**
     * 需要被排除包装的路径
     */
    private List<String> excludes = new ArrayList<>();
}
