package me.abel.excel.importer;


import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @author HuangShuCheng
 */
public class ColumnDefinition {

    private final Integer index;
    private final String field;
    private Function<String, Object> formatter;
    private Consumer<Exception> formatErrorHandler;

    public ColumnDefinition(Integer index, String field) {
        this.index = index;
        this.field = field;
    }

    public ColumnDefinition(Integer index, String field, Function<String, Object> formatter) {
        this(index, field);
        this.formatter = formatter;
    }

    public ColumnDefinition(Integer index, String field, Function<String, Object> formatter, Consumer<Exception> formatErrorHandler) {
        this(index, field, formatter);
        this.formatErrorHandler = formatErrorHandler;
    }

    public Integer getIndex() {
        return index;
    }

    public String getField() {
        return field;
    }

    public Function<String, Object> getFormatter() {
        return formatter;
    }

    public Consumer<Exception> getFormatErrorHandler() {
        return formatErrorHandler;
    }
}
