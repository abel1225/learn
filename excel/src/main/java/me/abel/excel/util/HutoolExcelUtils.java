package me.abel.excel.util;

import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Collection;
import java.util.Map;

/**
 * @Description
 * @Author Abel.li
 * @Date 2023/2/17 AM10:24
 */
public class HutoolExcelUtils {
    private static final Logger logger = LoggerFactory.getLogger(HutoolExcelUtils.class);

    public static void doExport(HttpServletResponse response, String fileName, Collection<?> data, Map<String, String> header){
        ExcelWriter writer = null;

        ServletOutputStream outputStream = null;
        try {
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset:utf-8");
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8") + ".xlsx");
            outputStream = response.getOutputStream();
            writer = ExcelUtil.getWriter(true);
            for (Map.Entry<String, String> entry : header.entrySet()) {
                writer.addHeaderAlias(entry.getKey(), entry.getValue());
            }
            writer.setOnlyAlias(true);
            writer.write(data, true);
            writer.flush(outputStream, true);
        } catch (IOException e) {
            logger.error("导出异常 {}", ExceptionUtils.getStackTrace(e));
            throw new RuntimeException(e);
        } finally {
            if (writer != null) {
                writer.close();
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
