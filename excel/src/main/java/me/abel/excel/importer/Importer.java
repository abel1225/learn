//package me.abel.excel.importer;
//
//import lombok.extern.slf4j.Slf4j;
//import me.abel.excel.Version;
//import org.apache.poi.hssf.usermodel.HSSFWorkbook;
//import org.apache.poi.ss.usermodel.Workbook;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.InputStream;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Objects;
//import java.util.function.Consumer;
//
///**
// * @author HuangShuCheng
// */
//@Slf4j
//public class Importer {
//
//    private Importer.Builder builder;
//
//    private Importer(Importer.Builder builder) {
//        this.builder = builder;
//    }
//
//    private Importer() {
//    }
//
//    private Workbook workbook() throws Exception {
//        switch (builder.version) {
//            case HS:
//                return new HSSFWorkbook(builder.inputStream);
//            case XS:
//            default:
//                return new XSSFWorkbook(builder.inputStream);
//        }
//    }
//
//    private void exception(Exception e) {
//        log.error("导出excel异常", e);
//        Consumer<Exception> consumer = this.builder.exceptionConsumer;
//        if (Objects.nonNull(consumer)) {
//            consumer.accept(e);
//        }
//    }
//
//    public void generate() {
//        try {
//            try (Workbook workbook = workbook()) {
//                for (SheetDefinition<?> sheet : builder.sheets) {
//                    sheet.generate(workbook);
//                }
//            } catch (Exception e) {
//                exception(e);
//            }
//        } catch (Exception e) {
//            exception(e);
//        }
//    }
//
//    public static class Builder {
//
//        private final List<SheetDefinition<?>> sheets;
//
//        private Version version;
//
//        private InputStream inputStream;
//
//        private Consumer<Exception> exceptionConsumer;
//
//        public Builder() {
//            version = Version.XS;
//            sheets = new ArrayList<>();
//        }
//
//        /**
//         * excel 2007 xlxs格式
//         */
//        public Importer.Builder xs() {
//            version = Version.XS;
//            return this;
//        }
//
//        /**
//         * excel 2003 xls格式
//         */
//        public Importer.Builder hs() {
//            version = Version.HS;
//            return this;
//        }
//
//
//        public Importer.Builder input(InputStream inputStream) {
//            this.inputStream = inputStream;
//            return this;
//        }
//
//        public Importer.Builder input(File file) {
//            try {
//                this.inputStream = new FileInputStream(file);
//            } catch (Exception e) {
//                throw new IllegalStateException("文件输入流创建失败");
//            }
//            return this;
//        }
//
//        public Importer.Builder exception(Consumer<Exception> exceptionConsumer) {
//            this.exceptionConsumer = exceptionConsumer;
//            return this;
//        }
//
//        public <T> SheetDefinition<T> sheet(Class<T> cls) {
//            SheetDefinition<T> h = new SheetDefinition<>(this, cls);
//            sheets.add(h);
//            return h;
//        }
//
//        public <T> SheetDefinition<T> sheet(Class<T> cls, Integer index) {
//            SheetDefinition<T> h = new SheetDefinition<>(this, cls, index);
//            sheets.add(h);
//            return h;
//        }
//
//        public Importer build() {
//            sheets.forEach(SheetDefinition::validate);
//            return new Importer(this);
//        }
//    }
//
//
//}
