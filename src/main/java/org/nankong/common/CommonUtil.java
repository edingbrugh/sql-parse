package org.nankong.common;

import org.apache.commons.lang3.StringUtils;
import org.nankong.SqlInfo;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @Description: 通用工具类
 * @Author NanKong
 * @Date 2022/9/26 19:14
 */
public class CommonUtil {


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
    *  @Description: 计算sql之间的行数，用户精准控制报错信息提示
    *  @Param: * @param command:
    *  @Return: * @return: java.util.Map<java.lang.Integer,org.nankong.SqlInfo>
    **/
    public static Map<Integer, SqlInfo> executePreHandle(String command) {
        Map<Integer, SqlInfo> map = new HashMap<>();
        if (StringUtils.isBlank(command)) {
            return map;
        }
        String[] sqlSplit = command.split(SQL_SEPARATOR);
        for (int i = 0; i < sqlSplit.length; i++) {
            String sql = sqlSplit[i];
            String[] interSql = sql.split(LINE_SEPARATOR);
            int blankNum = 0;
            for (String s1 : interSql) {
                if (StringUtils.isNoneBlank(s1)) {
                    break;
                }
                blankNum++;
            }
            map.put(i, SqlInfo.builder().preBlankNum(blankNum).originalSql(sql).total(interSql.length).build());
        }
        return map;
    }

    /**
    *  @Description: 计算当前执行sql前面还有多少行是空的
    *  @Param: * @param curSqlIndex:
     * @param map:
    *  @Return: * @return: java.lang.Integer
    **/
    public static Integer preSqlRowNum(Integer curSqlIndex, Map<Integer, SqlInfo> map) {
        AtomicInteger res = new AtomicInteger();
        map.keySet().stream().filter(v -> v < curSqlIndex).collect(Collectors.toList()).forEach(v -> res.addAndGet(map.get(v).getTotal()));
        return res.get();
    }


    public static void main(String[] args) {
        String s = "\n" +
                "\n" +
                "SELECT  * from \n" +
                "\n" +
                "aa_ranger;\n" +
                "\n" +
                "\n" +
                "\n" +
                "SELECT * from aaaaaaaa ;\n" +
                "\n" +
                "SELECT 1;";
        Map<Integer, SqlInfo> map = executePreHandle(s);
        Integer integer = preSqlRowNum(0, map);
        System.out.println(integer);
        map.keySet().forEach(v -> System.out.println(v));

    }
}
