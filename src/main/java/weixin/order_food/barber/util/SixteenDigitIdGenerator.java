package weixin.order_food.barber.util;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 16位纯数字ID生成器
 * 采用 13位时间戳 + 3位随机数 的方式生成
 */
public class SixteenDigitIdGenerator implements IdentifierGenerator {

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) {
        long timestamp = System.currentTimeMillis(); // 13位
        long random = ThreadLocalRandom.current().nextInt(100, 1000); // 3位 (100~999)
        return timestamp * 1000 + random;
    }
}