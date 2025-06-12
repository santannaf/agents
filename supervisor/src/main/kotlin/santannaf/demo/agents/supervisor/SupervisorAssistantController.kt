package santannaf.demo.agents.supervisor

import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap
import org.springframework.ai.chat.client.ChatClient
import org.springframework.ai.chat.client.advisor.PromptChatMemoryAdvisor
import org.springframework.ai.chat.memory.MessageWindowChatMemory
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

@RestController
@ResponseBody
@RequestMapping
@CrossOrigin(origins = ["*"])
class SupervisorAssistantController(
    private val singularity: ChatClient
) {
    val advisorMap: ConcurrentMap<String, PromptChatMemoryAdvisor> = ConcurrentHashMap()

    data class ChatRequest(val user: String, val message: String)

    @PostMapping(path = ["/chat"])
    fun inquire(@RequestBody request: ChatRequest): String? {
        val advisor = advisorMap.computeIfAbsent(request.user) {
            PromptChatMemoryAdvisor.builder(
                MessageWindowChatMemory.builder()
                    .maxMessages(50)
                    .build()
            )
                .conversationId(request.user)
                .build()
        }

        val prompt = """
            User: ${request.user}
            Question: ${request.message}
        """.trimIndent()

        return singularity
            .prompt()
            .user(prompt)
            .advisors(advisor)
            .call()
            .content()
    }
}
