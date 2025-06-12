package santannaf.demo.agents.supervisor

import io.modelcontextprotocol.client.McpSyncClient
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap
import org.springframework.ai.chat.client.ChatClient
import org.springframework.ai.chat.client.advisor.PromptChatMemoryAdvisor
import org.springframework.ai.mcp.SyncMcpToolCallbackProvider
import org.springframework.ai.tool.ToolCallbackProvider
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ChatClientConfig {
    @Bean("toolCallbackProvider")
    fun toolCallbackProvider(profileAgent: McpSyncClient, simulationAgent: McpSyncClient, creditCardLimitAgent: McpSyncClient): ToolCallbackProvider =
        SyncMcpToolCallbackProvider(profileAgent, simulationAgent, creditCardLimitAgent)

    @Bean
    fun chatClient(builder: ChatClient.Builder, toolCallbackProvider: ToolCallbackProvider): ChatClient =
        builder
            .defaultSystem(TemplateSystemPrompt.systemPrompt)
            .defaultToolCallbacks(toolCallbackProvider)
            .build()

    @Bean
    fun advisorMap(): ConcurrentMap<String, PromptChatMemoryAdvisor> = ConcurrentHashMap()
}
