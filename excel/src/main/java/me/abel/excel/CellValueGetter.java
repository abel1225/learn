//package me.abel.excel;
//
//import org.apache.poi.ss.usermodel.Cell;
//
//import java.util.Objects;
//
//public class CellValueGetter {
//
//    public static Object getValue(Cell cell) {
//        if (Objects.isNull(cell)) {
//            return null;
//        }
//        CellType type = cell.getCellType();
//        switch (type) {
//            case BOOLEAN:
//                return cell.getBooleanCellValue();
//            case NUMERIC:
//                return cell.getNumericCellValue();
//            case STRING:
//            case BLANK:
//                return cell.getStringCellValue();
//            case ERROR:
//                return cell.getErrorCellValue();
//            case FORMULA:
//                return "公式无法获取";
//            default:
//                return "无效的单元格类型";
//        }
//        return null;
//    }
//
//    public static String getStringValue(Cell cell) {
//        return Objects.toString(getValue(cell), "");
//    }
//}
