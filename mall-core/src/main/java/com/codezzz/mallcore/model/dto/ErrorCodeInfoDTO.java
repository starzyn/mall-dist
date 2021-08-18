package com.codezzz.mallcore.model.dto;

import com.codezzz.mallcore.exception.ErrorGroup;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * @author felixu
 * @since 2020.10.22
 */
@Data
@AllArgsConstructor
public class ErrorCodeInfoDTO {

    private String groupName;

    private List<ErrorInfo> errors;

    @Data
    @AllArgsConstructor
    public static class ErrorInfo{

        private Integer code;

        private String message;

        private String name;

        private ErrorGroup group;
    }
}
