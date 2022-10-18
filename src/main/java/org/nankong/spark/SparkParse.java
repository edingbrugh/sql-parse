package org.nankong.spark;

import org.apache.spark.sql.catalyst.plans.logical.LogicalPlan;
import org.apache.spark.sql.execution.SparkSqlParser;
import org.nankong.common.CommonUtil;

import java.util.Set;

/**
 * @Description: Spark 解析
 * @Author NanKong
 * @Date 2022/9/23 14:12
 */
public class SparkParse {

    private static void parse(String command) {
        SparkSqlParser sparkSqlParser = new SparkSqlParser();
        LogicalPlan logicalPlan = sparkSqlParser.parsePlan(command);
        String tree = logicalPlan.treeString();
        System.out.println(tree);
    }

    public static void main(String[] args) {
        String sql = "create table if not exists account_login_times_bak_99\n" +
                "(user_name string, user_id BIGINT, login_times int) \n" +
                "COMMENT '测试表'\n" +
                "PARTITIONED BY (ds string)\n" +
                "row format delimited fields terminated by \"\";";
        Integer line = CommonUtil.lineNum(sql).size();
        System.out.println("line ：" + line);
        parse(sql);
    }
}
