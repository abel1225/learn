package me.abel.excel;

import lombok.Getter;

/**
 * @author HuangShuCheng
 */
@Getter
public enum Version {
    /**
     * HSSFWorkbook
     * excel 2003
     */
    HS,
    /**
     * XSSFWorkbook
     * excel 2007
     */
    XS,
    /**
     * SXSSFWorkbook
     * excel 2007 流式导出
     */
    SXS
}
