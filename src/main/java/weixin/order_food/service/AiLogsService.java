package weixin.order_food.service;

import weixin.order_food.entity.AiLogs;

import java.util.List;

/**
 * AI 日志服务接口
 */
public interface AiLogsService {
    
    /**
     * 保存日志
     */
    AiLogs saveLog(AiLogs log);
    
    /**
     * 创建日志
     */
    AiLogs createLog(String userId, String intent, String chatHistory);
    
    /**
     * 根据用户ID获取历史记录
     */
    List<AiLogs> getLogsByUserId(String userId);
    
    /**
     * 获取所有日志
     */
    List<AiLogs> getAllLogs();
}
