//package me.abel.excel.export;
//
//import lombok.extern.slf4j.Slf4j;
//import me.abel.excel.Version;
//import org.apache.poi.hssf.usermodel.HSSFWorkbook;
//import org.apache.poi.ss.usermodel.Workbook;
//import org.apache.poi.xssf.streaming.SXSSFWorkbook;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//
//import java.io.OutputStream;
//import java.util.ArrayList;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Objects;
//import java.util.function.Consumer;
//
///**
// * @author HuangShuCheng
// */
//@Slf4j
//public class Exporter {
//
//
//    private final Builder builder;
//
//    private Exporter(Builder builder) {
//        this.builder = builder;
//    }
//
//
//    private void exception(Exception e) {
//        log.error("导出excel异常", e);
//        Consumer<Exception> consumer = this.builder.exceptionConsumer;
//        if (Objects.nonNull(consumer)) {
//            consumer.accept(e);
//        }
//    }
//
//
//    public void generate() {
//        OutputStream out = builder.outputStream;
//        try {
//            try (Workbook workbook = workbook()) {
//                builder.sheets.forEach(s -> s.generate(workbook));
//                workbook.write(out);
//            } catch (Exception e) {
//                exception(e);
//            }
//
//        } catch (Exception e) {
//            exception(e);
//        } finally {
//            if (Objects.isNull(builder.outputStream) && Objects.nonNull(out)) {
//                try {
//                    out.close();
//                } catch (Exception ignored) {
//                }
//            }
//        }
//
//    }
//
//    private Workbook workbook() {
//        switch (builder.version) {
//            case HS:
//                return new HSSFWorkbook();
//            case SXS:
//                return new SXSSFWorkbook(500);
//            case XS:
//            default:
//                return new XSSFWorkbook();
//        }
//    }
//
//
//    public static class Builder {
//
//        private final List<SheetDefinition<?>> sheets;
//
//        private Version version;
//
//        private Boolean withSerialNo;
//
//        private String serialNoTitle;
//
//        private OutputStream outputStream;
//
//        private Consumer<Exception> exceptionConsumer;
//
//        public Builder() {
//            serialNoTitle = "序号";
//            withSerialNo = true;
//            version = Version.XS;
//            sheets = new ArrayList<>();
//        }
//
//        public Builder hideSerialNo() {
//            this.withSerialNo = false;
//            return this;
//        }
//
//        public Builder serialNoTitle(String title) {
//            this.serialNoTitle = title;
//            return this;
//        }
//
//        /**
//         * excel 2007 xlxs格式
//         */
//        public Builder xs() {
//            version = Version.XS;
//            return this;
//        }
//
//        public Builder sxs() {
//            version = Version.SXS;
//            return this;
//        }
//
//        public Builder output(OutputStream outputStream) {
//            this.outputStream = outputStream;
//            return this;
//        }
//
//        public Builder exception(Consumer<Exception> exceptionConsumer) {
//            this.exceptionConsumer = exceptionConsumer;
//            return this;
//        }
//
//        public <T> SheetDefinition<T> sheet(List<T> list) {
//            SheetDefinition<T> h = new SheetDefinition<>(this, list);
//            sheets.add(h);
//            return h;
//        }
//
//        public <T> SheetDefinition<T> sheet(Iterator<T> iterator) {
//            SheetDefinition<T> h = new SheetDefinition<>(this, iterator);
//            sheets.add(h);
//            return h;
//        }
//
//        public Exporter build() {
//            sheets.forEach(SheetDefinition::validate);
//            return new Exporter(this);
//        }
//
//        public Boolean getWithSerialNo() {
//            return withSerialNo;
//        }
//
//        public String getSerialNoTitle() {
//            return serialNoTitle;
//        }
//    }
//}
