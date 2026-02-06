package weixin.order_food.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import weixin.order_food.entity.AiLogs;

import java.util.List;

/**
 * AI 日志 Repository
 */
@Repository
public interface AiLogsRepository extends JpaRepository<AiLogs, Long> {
    
    /**
     * 根据用户ID查询日志，按创建时间倒序排列
     */
    List<AiLogs> findByUserIdOrderByCreatedAtDesc(String userId);

    /**
     * 根据意图查询日志
     */
    List<AiLogs> findByIntent(String intent);
}
