package me.abel.excel.importer;

import lombok.extern.slf4j.Slf4j;
import me.abel.excel.CellValueGetter;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.logging.log4j.util.TriConsumer;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @author HonestHuang
 */
@Slf4j
public class SheetDefinition<T> {
    private final Integer index;
    private final List<ColumnDefinition> columns = new ArrayList<>();
    private final Importer.Builder build;
    private List<T> list;
    private final Class<T> klass;
    private Integer start;
    private Integer titleIndex;
    private Integer end;
    private Consumer<T> consumer;
    private TriConsumer<T, Row, Row> rowConsumer;

    public SheetDefinition(Importer.Builder build, Class<T> klass) {
        this(build, klass, 0);
    }

    public SheetDefinition(Importer.Builder build, Class<T> klass, Integer index) {
        this.build = build;
        this.klass = klass;
        this.index = index;
    }

    public SheetDefinition<T> col(Integer index, String field) {
        columns.add(new ColumnDefinition(index, field));
        return this;
    }

    public SheetDefinition<T> col(Integer index, String field, Function<String, Object> formatter) {
        columns.add(new ColumnDefinition(index, field, formatter));
        return this;
    }

    public SheetDefinition<T> col(Integer index, String field, Function<String, Object> formatter, Consumer<Exception> formatErrorHandler) {
        columns.add(new ColumnDefinition(index, field, formatter, formatErrorHandler));
        return this;
    }

    public SheetDefinition<T> from(Integer start) {
        this.start = start;
        return this;
    }

    public SheetDefinition<T> title(Integer index) {
        this.titleIndex = index;
        return this;
    }

    public SheetDefinition<T> to(Integer end) {
        this.end = end;
        return this;
    }

    public SheetDefinition<T> list(List<T> list) {
        this.list = list;
        return this;
    }

    public SheetDefinition<T> consumer(Consumer<T> consumer) {
        this.consumer = consumer;
        return this;
    }

    public SheetDefinition<T> rowConsumer(TriConsumer<T, Row, Row> consumer) {
        this.rowConsumer = consumer;
        return this;
    }

    public Importer.Builder end() {
        return build;
    }

    public void validate() {

    }

    public void generate(Workbook workbook) throws Exception {
        final Sheet sheet = workbook.getSheetAt(index);

        int iStart = getValidStart(sheet.getFirstRowNum());
        int iEnd = getValidEnd(sheet.getLastRowNum());

        Row titleRow = Objects.nonNull(titleIndex) ? sheet.getRow(titleIndex) : null;

        for (int i = iStart; i <= iEnd; i++) {
            Row row = sheet.getRow(i);
            T obj;
            try {
                obj = klass.newInstance();
            } catch (Exception e) {
                throw new IllegalStateException("实例化失败");
            }

            for (ColumnDefinition column : columns) {
                Cell cell = Objects.nonNull(row) ? row.getCell(column.getIndex()) : null;
                Function<String, Object> formatter = column.getFormatter();
                Object val;
                if (Objects.nonNull(formatter)) {
                    val = formatter.apply(CellValueGetter.getStringValue(cell));
                } else {
                    val = CellValueGetter.getStringValue(cell);
                }
                setFieldValue(obj, column.getField(), val);
            }

            if (Objects.nonNull(list)) {
                list.add(obj);
            }
            if (Objects.nonNull(consumer)) {
                consumer.accept(obj);
            }
            if (Objects.nonNull(rowConsumer)) {
                rowConsumer.accept(obj, row, titleRow);
            }
        }


    }


    private int getValidStart(int firstRowNum) {
        if (Objects.isNull(this.start) || this.start < firstRowNum) {
            return firstRowNum;
        }
        return this.start;
    }

    private int getValidEnd(int lastRowNum) {
        if (Objects.isNull(this.end) || this.end > lastRowNum) {
            return lastRowNum;
        }
        return this.end;
    }

    private void setFieldValue(Object obj, String field, Object value) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        PropertyUtils.setProperty(obj, field, value);
    }

}
