package org.nankong.common;


import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.stringtemplate.v4.ST;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Description: 错误信息解析攻击类
 * @Author NanKong
 * @Date 2022/9/23 15:11
 */
public class ErrorMsgUtil {

    private static final Logger LOG = LoggerFactory.getLogger(ErrorMsgUtil.class);


    private static final String LINE_SEPARATOR = "\n";

    private static final String SQL_SEPARATOR = ";";

    /**
     * @Description: 获取字符串共有多少行
     * @Param: * @param str:
     * @Return: * @return: java.lang.Integer
     **/
    public static List<String> lineNum(String str) {
        return Arrays.asList(str.split(LINE_SEPARATOR));
    }

    /**
     * @Description: sql 分割
     * @Param: * @param command:
     * @Return: * @return: java.util.List<java.lang.String>
     **/
    public static List<String> split(String command) {
        return Arrays.asList(command.split(SQL_SEPARATOR));
    }

    /**
     * @Description: 解析错误信息的具体位置
     * @Param: * @param msg:
     * @Return: * @return: org.nankong.common.ErrorColumn
     **/
    public static ErrorColumn parseLocation(String msg) {
        List<String> parseList = Arrays.asList(msg.split(" "));
        if (parseList.size() < 1) {
            LOG.error("解析错误信息的具体位置失败");
        }
        String[] split = parseList.get(1).split(":");
        if (split.length != 2) {
            LOG.error("解析错误信息的具体位置失败");
            return new ErrorColumn();
        }
        return new ErrorColumn(Integer.valueOf(split[0]), Integer.valueOf(split[1]));
    }


    /***
    *  @Description: 计算初出错的信息sql在整体sql的位置
    *  @Param: * @param sql:
     * @param executeSql:
     * @param msg:
    *  @Return: * @return: org.nankong.common.ErrorColumn
    **/
    public static ErrorColumn parseErrorMsgLocation(String sql, String executeSql , String msg) {
        // 计算每个sql在整个待执行sql的第几个位置
        Map<Integer, Integer> locationMap = new HashMap<>();
        List<String> split = split(sql);
        for (int i = 0; i < split.size(); i++) {
            String currentSql = split.get(1);
            Integer preLine = i == 0 ? parsePreBlankNum(currentSql) : Math.addExact(locationMap.get(i -1), parsePreBlankNum(currentSql));
        }
        return null;
    }

    /**
    *  @Description: 计算当前sql前面有多少个换行数
    *  @Param: * @param currentSql:
    *  @Return: * @return: int
    **/
    private static int parsePreBlankNum(String currentSql) {
        List<String> strings = lineNum(currentSql);
        List<String> collect = strings.stream().filter(StringUtils::isBlank).collect(Collectors.toList());
        return collect.size();
    }

    public static void main(String[] args) {
        String str ="\n" +
                "\n" +
                "\n" +
                "hhh" +
                "jjj" +
                "" +
                "mm;";
        System.out.println(parsePreBlankNum(str));
    }

}
