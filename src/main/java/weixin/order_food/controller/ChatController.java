package weixin.order_food.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import weixin.order_food.constant.AiSystemPrompt;
import weixin.order_food.service.AiLogsService;
import weixin.order_food.tools.DrinksTools;
import weixin.order_food.tools.OrderTools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * AI 对话控制器
 */
@RestController
@RequestMapping("/api/chat")
public class ChatController {

    private final ChatClient chatClient;
    private final AiLogsService aiLogsService;
    private final ObjectMapper objectMapper;

    @Autowired
    public ChatController(ChatClient.Builder builder,
                          DrinksTools drinksTools,
                          OrderTools orderTools,
                          AiLogsService aiLogsService,
                          ObjectMapper objectMapper) {
        // 构建 ChatClient，注入系统提示词和工具
        this.chatClient = builder
                .defaultSystem(AiSystemPrompt.BAR_MANAGER_PROMPT)
                .defaultTools(drinksTools, orderTools)
                .build();
        this.aiLogsService = aiLogsService;
        this.objectMapper = objectMapper;
    }

    /**
     * 对话接口
     * @param request 包含 userId 和 message
     * @return AI 回复
     */
    @PostMapping
    public Map<String, String> chat(@RequestBody Map<String, String> request) {
        String userId = request.get("userId");
        String message = request.get("message");

        if (userId == null || message == null) {
            throw new IllegalArgumentException("userId 和 message 不能为空");
        }

        // 调用 AI
        String response = chatClient.prompt()
                .user(message)
                .call()
                .content();

        // 异步记录日志 (这里简单实现为同步)
        try {
            // 构建简单的对话历史 JSON
            List<Map<String, String>> history = new ArrayList<>();
            Map<String, String> userMsg = new HashMap<>();
            userMsg.put("role", "user");
            userMsg.put("content", message);
            history.add(userMsg);

            Map<String, String> assistantMsg = new HashMap<>();
            assistantMsg.put("role", "assistant");
            assistantMsg.put("content", response);
            history.add(assistantMsg);

            String historyJson = objectMapper.writeValueAsString(history);
            
            // 简单判断意图 (实际场景可能需要 AI 返回意图)
            String intent = "chat";
            if (response.contains("下单成功")) {
                intent = "order";
            } else if (response.contains("推荐") || message.contains("推荐")) {
                intent = "recommend";
            }

            aiLogsService.createLog(userId, intent, historyJson);
        } catch (Exception e) {
            // 日志记录失败不影响主流程
            e.printStackTrace();
        }

        Map<String, String> result = new HashMap<>();
        result.put("reply", response);
        return result;
    }
}
