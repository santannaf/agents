package santannaf.demo.agents.credit_card.limit

import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap
import org.springframework.ai.chat.client.ChatClient
import org.springframework.ai.chat.client.advisor.PromptChatMemoryAdvisor
import org.springframework.ai.tool.ToolCallbackProvider
import org.springframework.ai.tool.method.MethodToolCallbackProvider
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ChatClientConfig {
    @Bean
    fun chatClient(builder: ChatClient.Builder, toolCallbackProvider: ToolCallbackProvider): ChatClient {
        val chatClient = builder
//            .defaultSystem(TemplateSystemPrompt.systemPrompt)
            .defaultToolCallbacks(toolCallbackProvider)
            .build()
        println("Chat Client initialized.")
        return chatClient
    }

    @Bean
    fun advisorMap(): ConcurrentMap<String, PromptChatMemoryAdvisor> = ConcurrentHashMap()

    @Bean
    fun toolCallbackProvider(tool: CreditCardLimitAgent): ToolCallbackProvider {
        val toolCallbackProvider = MethodToolCallbackProvider.builder()
            .toolObjects(tool)
            .build()
        println("Tool Callback Provider initialized.")
        return toolCallbackProvider
    }
}
