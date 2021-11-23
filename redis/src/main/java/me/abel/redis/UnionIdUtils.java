package me.abel.redis;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicInteger;

public class UnionIdUtils {

    private static final DateTimeFormatter DF_FMT_PREFIX = DateTimeFormatter.ofPattern("yyMMddHHmmssSS");
    private static final AtomicInteger SEQ = new AtomicInteger(100);
    private static ZoneId ZONE_ID = ZoneId.of("Asia/Shanghai");

    /**
     * 按获取时间支持并发
     * 获取到的key长度为 seqKey.length + 18
     * @ThreadSafe
     * @param seqKey
     * @return
     */
    public static String getUniqueKey(String seqKey) {
        LocalDateTime dataTime = LocalDateTime.now(ZONE_ID);
        if(SEQ.intValue()>990){
            SEQ.getAndSet(100);
        }
        return seqKey + dataTime.format(DF_FMT_PREFIX) + SEQ.getAndIncrement();
    }

    public static void main(String[] args) {
        long longValue = new BigDecimal(getUniqueKey("9")).longValue();
        System.out.println(longValue);
    }

}
