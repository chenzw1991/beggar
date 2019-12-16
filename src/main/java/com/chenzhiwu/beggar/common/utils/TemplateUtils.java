package com.chenzhiwu.beggar.common.utils;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * Description :
 * Author : Chen.MeiJie
 * Create Time : 2018/11/19.
 */
public class TemplateUtils {
    private static Logger logger = LoggerFactory.getLogger(TemplateUtils.class);
    private static final Map<String, String> templateMap = new ConcurrentHashMap<>();

    public static String loadTemplate(String templateName) {
        logger.info("load Template [{}]", templateName);
        String content = templateMap.get(templateName);
        if (StringUtils.isEmpty(content)) {
            String result;
            try {
                logger.info("File is not found, reading template file from classpath.");
                result = new BufferedReader(new InputStreamReader(ClassLoader.getSystemClassLoader().getResourceAsStream("renderTemplate/" + templateName + ".tem")))
                        .lines().parallel().collect(Collectors.joining(System.lineSeparator()));

                synchronized (templateMap) {
                    logger.info("Put content [{}] into templatesMap,", result);
                    templateMap.put(templateName, result);
                }
                return result;
            } catch (Exception e) {
                logger.warn("An error occured during loading template :" + templateName, e);
            }
            return "";
        } else {
            return content;
        }
    }

    public static String renderTemplate(String templateName, Map<String, String> params) {
        String content = loadTemplate(templateName);
        for (Map.Entry<String, String> entry : params.entrySet()) {
            content = content.replace("${" + entry.getKey() + "}", entry.getValue());
        }
        logger.debug("Render template, templateName is {}, params is {}, content [{}]", templateName, params, content);
        return content;
    }

    /**
     * 传入文本, 渲染表达式
     *
     * @param content
     * @param params
     * @return
     */
    public static String renderExpression(String content, Map<String, String> params) {
        String result = content;
        for (Map.Entry<String, String> entry : params.entrySet()) {
            result = content.replace("${" + entry.getKey() + "}", entry.getValue());
        }
        logger.debug("params is {}, origin content :{}, result :{}", params, content, result);
        return content;
    }
}
