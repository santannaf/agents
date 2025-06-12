package santannaf.demo.agents.faq

import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap
import org.springframework.ai.chat.client.ChatClient
import org.springframework.ai.chat.client.advisor.PromptChatMemoryAdvisor
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor
import org.springframework.ai.document.Document
import org.springframework.ai.reader.tika.TikaDocumentReader
import org.springframework.ai.tool.ToolCallbackProvider
import org.springframework.ai.tool.method.MethodToolCallbackProvider
import org.springframework.ai.transformer.splitter.TextSplitter
import org.springframework.ai.transformer.splitter.TokenTextSplitter
import org.springframework.ai.vectorstore.pgvector.PgVectorStore
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ChatClientConfig {
    @Bean
    fun chatClient(
        builder: ChatClient.Builder,
        toolCallbackProvider: ToolCallbackProvider,
        vectorStoreProperties: VectorStoreProperties,
        pgVectorStore: PgVectorStore
    ): ChatClient {
        vectorStoreProperties.documentsToLoad.forEach { document ->
            val documentReader: TikaDocumentReader = TikaDocumentReader(document)
            val docs: MutableList<Document?>? = documentReader.get()
            if (docs != null && docs.isNotEmpty()) {
                val textSplitter: TextSplitter = TokenTextSplitter()
                val splitDocs = textSplitter.apply(docs)
                pgVectorStore.add(splitDocs)
            }
        }

        val chatClient = builder
            .defaultSystem(TemplateSystemPrompt.systemPrompt)
            .defaultToolCallbacks(toolCallbackProvider)
            .defaultAdvisors(QuestionAnswerAdvisor(pgVectorStore))
            .build()

        return chatClient
    }

    @Bean
    fun advisorMap(): ConcurrentMap<String, PromptChatMemoryAdvisor> = ConcurrentHashMap()

    @Bean
    fun toolCallbackProvider(tool: FAQAgent): ToolCallbackProvider {
        val toolCallbackProvider = MethodToolCallbackProvider.builder()
            .toolObjects(tool)
            .build()
        return toolCallbackProvider
    }
}
