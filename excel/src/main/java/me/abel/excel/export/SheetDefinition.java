//package me.abel.excel.export;
//
//import com.alibaba.excel.util.StringUtils;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.beanutils.PropertyUtils;
//import org.apache.poi.ss.usermodel.Cell;
//import org.apache.poi.ss.usermodel.Row;
//import org.apache.poi.ss.usermodel.Sheet;
//import org.apache.poi.ss.usermodel.Workbook;
//
//import java.util.ArrayList;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Objects;
//import java.util.function.BiFunction;
//import java.util.function.Consumer;
//
///**
// * @author HuangShuCheng
// */
//@Slf4j
//public class SheetDefinition<T> {
//    private String name;
//    private final List<ColumnDefinition<T>> columns = new ArrayList<>();
//    private final Exporter.Builder build;
//    private final Iterator<T> iterator;
//
//    public SheetDefinition(Exporter.Builder build, Iterator<T> iterator) {
//        this.build = build;
//        this.iterator = iterator;
//    }
//
//    public SheetDefinition(Exporter.Builder build, List<T> data) {
//        this(build, data.iterator());
//    }
//
//    public SheetDefinition<T> col(String key, String title) {
//        columns.add(new ColumnDefinition<>(key, title));
//        return this;
//    }
//
//    public SheetDefinition<T> col(String key, String title, BiFunction<T, Object, String> formatter) {
//        columns.add(new ColumnDefinition<>(key, title, formatter));
//        return this;
//    }
//
//    public SheetDefinition<T> col(String key, String title, BiFunction<T, Object, String> formatter, Consumer<Exception> formatErrorHandler) {
//        columns.add(new ColumnDefinition<>(key, title, formatter, formatErrorHandler));
//        return this;
//    }
//
//    public SheetDefinition<T> name(String name) {
//        this.name = name;
//        return this;
//    }
//
//    public Exporter.Builder end() {
//        return build;
//    }
//
//    public void validate() {
////        Assert.notBlank(name,"");
//    }
//
//    @SuppressWarnings("unchecked")
//    public void generate(Workbook workbook) {
//
//
//        final Sheet sheet = !StringUtils.isEmpty(name) ? workbook.createSheet(this.name) : workbook.createSheet();
//        Row rowTitle = sheet.createRow(0);
//
//        Boolean withSerialNo = this.build.getWithSerialNo();
//        if (withSerialNo) {
//            rowTitle.createCell(0).setCellValue(this.build.getSerialNoTitle());
//        }
//        //标题栏
//        for (int i = 0, len = columns.size(); i < len; i++) {
//            int index = i + (withSerialNo ? 1 : 0);
//            rowTitle.createCell(index).setCellValue(columns.get(i).title);
//        }
//        //解析内容
//        int rowNo = 0;
//        while (iterator.hasNext()) {
//            Row row = sheet.createRow(rowNo + 1);
//            T data = iterator.next();
//            if (withSerialNo) {
//                row.createCell(0).setCellValue(rowNo + 1);
//            }
//
//            for (int i = 0, len = columns.size(); i < len; i++) {
//                ColumnDefinition<T> c = columns.get(i);
//                int index = i + (withSerialNo ? 1 : 0);
//                Cell cell = row.createCell(index);
//
//                Object value = null;
//                try {
//                    value = getFieldValue(data, c.key);
//                } catch (Exception e) {
//                    log.error("SheetDefinition#getFieldValue", e);
//                    if (Objects.nonNull(c.formatErrorHandler)) {
//                        c.formatErrorHandler.accept(e);
//                    }
//                }
//
//                String formatValue;
//                if (Objects.nonNull(c.formatter)) {
//                    formatValue = c.formatter.apply(data, value);
//                } else {
//                    formatValue = Objects.toString(value, "");
//                }
//                cell.setCellValue(formatValue);
//            }
//            rowNo++;
//        }
//    }
//
//    private Object getFieldValue(T obj, String field) throws Exception {
//        return PropertyUtils.getProperty(obj, field);
//    }
//
//}
