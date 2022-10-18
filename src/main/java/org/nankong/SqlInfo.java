package org.nankong;

import lombok.Builder;
import lombok.Data;

/**
 * @Description: 执行sql额外信息统计
 * @Author NanKong
 * @Date 2022/10/18 15:35
 */

@Builder
@Data
public class SqlInfo {

    // 原始的执行sql 语句
    private String originalSql;

    // 执行执行sql 前置空白行数，当sql正在真正执行的时候才有效
    private int preBlankNum;

    // 该sql总行数
    private int total;
}
