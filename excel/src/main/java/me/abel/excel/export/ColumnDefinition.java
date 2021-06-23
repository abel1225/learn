package me.abel.excel.export;

import java.util.function.BiFunction;
import java.util.function.Consumer;

/**
 * @author HuangShuCheng
 */
public class ColumnDefinition<T> {

    String key;
    String title;
    BiFunction<T, Object, String> formatter;
    Consumer<Exception> formatErrorHandler;

    public ColumnDefinition(String key, String title) {
        this.key = key;
        this.title = title;
    }

    public ColumnDefinition(String key, String title, BiFunction<T, Object, String> formatter) {
        this(key, title);
        this.formatter = formatter;
    }

    public ColumnDefinition(String key, String title, BiFunction<T, Object, String> formatter, Consumer<Exception> formatErrorHandler) {
        this(key, title, formatter);
        this.formatErrorHandler = formatErrorHandler;
    }

}
