package weixin.order_food.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import weixin.order_food.entity.AiLogs;
import weixin.order_food.repository.AiLogsRepository;
import weixin.order_food.service.AiLogsService;

import java.util.List;

/**
 * AI 日志服务实现类
 */
@Service
public class AiLogsServiceImpl implements AiLogsService {

    @Autowired
    private AiLogsRepository aiLogsRepository;

    @Override
    public AiLogs saveLog(AiLogs log) {
        return aiLogsRepository.save(log);
    }

    @Override
    public AiLogs createLog(String userId, String intent, String chatHistory) {
        AiLogs log = new AiLogs();
        log.setUserId(userId);
        log.setIntent(intent);
        log.setChatHistory(chatHistory);
        return aiLogsRepository.save(log);
    }

    @Override
    public List<AiLogs> getLogsByUserId(String userId) {
        return aiLogsRepository.findByUserIdOrderByCreatedAtDesc(userId);
    }

    @Override
    public List<AiLogs> getAllLogs() {
        return aiLogsRepository.findAll();
    }
}
