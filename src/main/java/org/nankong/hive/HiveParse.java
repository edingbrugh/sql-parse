package org.nankong.hive;

import org.apache.hadoop.hive.ql.parse.ASTNode;
import org.apache.hadoop.hive.ql.parse.ParseException;
import org.apache.hadoop.hive.ql.parse.ParseUtils;
import org.nankong.common.ErrorColumn;
import org.nankong.common.ErrorMsgUtil;
import org.nankong.common.ValidateInfo;

import java.util.*;

/**
 * @Description: hive sql 解析
 * @Author NanKong
 * @Date 2022/9/23 14:12
 */
public class HiveParse {


    /**
     * @Description: sql 解析核心方法，该方法依赖底层的引擎版本，若有需要根据自己需求对应自己引擎版本
     * @Param: * @param command:
     * @Return: * @return: org.apache.hadoop.hive.ql.parse.ASTNode
     **/
    public static ASTNode parse(String command) throws ParseException {
        return ParseUtils.parse(command);
    }

    /**
     * @Description: 单sql校验
     * @Param: * @param command:
     * @Return: * @return: org.nankong.common.ValidateInfo
     **/
    public static ValidateInfo singleSqlValidate(String command) {
        Optional<String> first = ErrorMsgUtil.split(command).stream().findFirst();
        if (!first.isPresent()) {
            throw new RuntimeException("SingleSqlValidate Exception");
        }
        String sql = first.get();
        try {
            parse(command);
            return ValidateInfo.builder().successNum(1).isTure(Boolean.TRUE).build();
        } catch (ParseException e) {
            String message = e.getMessage();
            System.out.println(message);
            ErrorColumn errorColumn = ErrorMsgUtil.parseLocation(message);
            return ValidateInfo.builder().isTure(Boolean.FALSE).errorMsg(message).build();
        }
    }


    /**
     * @Description: 多个sql校验，以;分割
     * @Param: * @param command:
     * @Return: * @return: org.nankong.common.ValidateInfo
     **/
    public static Set<ValidateInfo> multipleSqlValidate(String command) {
        Set<ValidateInfo> res = new HashSet<>();
        List<String> sqls = ErrorMsgUtil.split(command);
        for (String sql : sqls) {
            try {
                parse(sql);
                res.add(ValidateInfo.builder().successNum(1).isTure(Boolean.TRUE).build());
            } catch (ParseException e) {
                String message = e.getMessage();
                System.out.println(message);
                ErrorColumn errorColumn = ErrorMsgUtil.parseLocation(message);
                res.add(ValidateInfo.builder().isTure(Boolean.FALSE).errorMsg(message).build());
            }
        }
        return res;
    }

    public static void main(String[] args) {
        String sql = "" +
                "create table if not exists account_login_times_bak_99\n" +
                "(user_name string, user_id BIGINT, login_times int) \n" +
                "COMMENT '测试表'\n" +
                "PARTITIONED BY (ds string)\n" +
                "row format delimited fields terminated by \",\";";
        Integer line = ErrorMsgUtil.lineNum(sql).size();
        System.out.println("line ：" + line);
        Set<ValidateInfo> validateInfo = multipleSqlValidate(sql);
        System.out.println(validateInfo);
    }
}
