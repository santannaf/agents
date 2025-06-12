package santannaf.demo.agents.faq

import java.util.concurrent.ConcurrentMap
import org.springframework.ai.chat.client.ChatClient
import org.springframework.ai.chat.client.advisor.PromptChatMemoryAdvisor
import org.springframework.ai.chat.memory.MessageWindowChatMemory
import org.springframework.ai.tool.annotation.Tool
import org.springframework.ai.tool.annotation.ToolParam
import org.springframework.beans.factory.ObjectProvider
import org.springframework.stereotype.Component

@Component
class FAQAgent(
    private val chatClientProvider: ObjectProvider<ChatClient>,
    private val advisorMap: ConcurrentMap<String, PromptChatMemoryAdvisor>
) {
    @Tool(name= "faq", description = "Responde perguntas frequentes sobre produtos bancários")
    fun faq(
        @ToolParam(description = "Pergunta feita pelo usuário") question: String,
        @ToolParam(description = "Nome do usuário") user: String
    ): String? {
        println(">>> Entrou na Tool com pergunta: \"$question\" e user: $user")

        val advisor = advisorMap.computeIfAbsent(user) {
            PromptChatMemoryAdvisor.builder(
                MessageWindowChatMemory.builder().maxMessages(30).build()
            ).conversationId(user).build()
        }

        val chatClient = chatClientProvider.ifAvailable ?: throw java.lang.IllegalStateException()

        return chatClient
            .prompt()
            .user(question)
            .advisors(advisor)
            .call()
            .content()
    }
}
