package santannaf.demo.agents.investment.profile

import java.util.concurrent.ConcurrentMap
import org.springframework.ai.chat.client.ChatClient
import org.springframework.ai.chat.client.advisor.PromptChatMemoryAdvisor
import org.springframework.ai.chat.memory.MessageWindowChatMemory
import org.springframework.ai.tool.annotation.Tool
import org.springframework.ai.tool.annotation.ToolParam
import org.springframework.beans.factory.ObjectProvider
import org.springframework.stereotype.Component

@Component
class ProfileInvestor(
    private val chatClientProvider: ObjectProvider<ChatClient>,
    private val advisorMap: ConcurrentMap<String, PromptChatMemoryAdvisor>
) {
    @Tool(name = "investmentProfile", description = "Explica o perfil de investidor")
    fun investmentProfile(
        @ToolParam question: String,
        @ToolParam user: String
    ): String? {
        println(">>> Entrou na Tool com pergunta: \"$question\" e user: $user")

        val advisor = advisorMap.computeIfAbsent(user) {
            PromptChatMemoryAdvisor.builder(
                MessageWindowChatMemory.builder()
                    .maxMessages(50)
                    .build()
            ).build()
        }

        val chatClient = chatClientProvider.ifAvailable ?: throw java.lang.IllegalStateException()

        val response = try {
            chatClient.prompt()
                .user(question)
                .advisors(advisor)
                .call()
                .content()
        } catch (e: Exception) {
            println("!!! Erro ao chamar LLM: ${e.message}")
            return "Erro interno ao processar a pergunta."
        }

        println("<<< Resposta da LLM: $response")
        return response
    }
}
