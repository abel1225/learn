
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDate;

public class DecimalCalculate {

    public static void main(String[] args) {
        BigDecimal decimal = new BigDecimal("86.71");
        System.out.println(decimal.doubleValue());

        LocalDate localDate = LocalDate.now();
        LocalDate months = localDate.plusMonths(1);
        System.out.println(StringUtils.join(months.getYear(), "", StringUtils.leftPad(String.valueOf(months.getMonthValue()), 2, "0")));
    }
}
