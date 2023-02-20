package me.abel.excel.util;

import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.builder.ExcelWriterBuilder;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * @Description
 * @Author Abel.li
 * @Date 2023/2/17 AM10:24
 */
public class EasyExcelUtils {
    private static final Logger logger = LoggerFactory.getLogger(EasyExcelUtils.class);
    public static void doExport(HttpServletResponse response, String fileName, Collection<?> data, List<List<String>> header) {
        ExcelWriter writer=null;
        ServletOutputStream outputStream = null;
        try {
            String excelName = (StringUtils.isEmpty(fileName) ? "subject" : URLEncoder.encode(fileName, "UTF-8")) + ".xlsx";
            response.setHeader("Content-disposition", "attachment; filename=" + excelName);
//            "application/x-download"
//            response.setContentType("application/vnd.ms-excel;charset=UTF-8");
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=UTF-8");
//            response.setHeader("Pragma", "No-cache");
//            response.setHeader("Access-Control-Expose-Headers", "X-File-Name");
//            response.setHeader("X-File-Name", excelName);
//            response.setHeader("Cache-Control", "no-cache");
//            response.setDateHeader("Expires", 0);
            response.resetBuffer();
            outputStream = response.getOutputStream();

            if (CollectionUtils.isEmpty(data)) {
                writer = new ExcelWriterBuilder().excelType(ExcelTypeEnum.XLSX).file(outputStream).build();
            } else {
                writer = new ExcelWriterBuilder().excelType(ExcelTypeEnum.XLSX).head(header).file(outputStream)
//                        .build();
//                        .registerWriteHandler(new HorizontalCellStyleStrategy(headWriteCellStyle, contentWriteCellStyle))
                        .registerWriteHandler(new ExcelWidthStyleStrategy()).build();
            }

            //实例化表单
            WriteSheet sheet = new WriteSheet();
            if(StringUtils.isEmpty(fileName)){
                sheet.setSheetName("data");
                sheet.setSheetNo(0);
            }else{
                sheet.setSheetName(fileName);
            }
            //输出
            writer.write(data, sheet);
            writer.finish();
            outputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("导出异常 {}", ExceptionUtils.getStackTrace(e));
        } finally {
            if (writer != null) {
                writer.finish();
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

    public static List<List<Object>> convertObject(List<JSONObject> result, Set<String> exportField) {
        List<List<Object>> resultData = new ArrayList<>();
        for (JSONObject data : result) {
            List<Object> dataList = new ArrayList<>();
            Map<String, Object> innerMap = data.getInnerMap();
            for (String field : exportField) {
                dataList.add(innerMap.get(field));
            }
            resultData.add(dataList);
        }
        return resultData;
    }
}
