package org.wudiSpringFramework.mvc.type;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 存储http请求路径和请求方法
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestPathInfo {
    // http请求路径
    private String httpMethod;
    // 请求方法
    private String httpPath;
}
