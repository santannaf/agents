package santannaf.demo.agents.supervisor

import io.modelcontextprotocol.client.McpClient
import io.modelcontextprotocol.client.McpSyncClient
import io.modelcontextprotocol.client.transport.HttpClientSseClientTransport
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class McpClientConfig {
    @Bean("profileAgent")
    fun mcpSyncClientProfileInvestor(): McpSyncClient {
        val mcp = McpClient.sync(
            HttpClientSseClientTransport.builder("http://localhost:8081")
                .build()
        ).build()
        mcp.initialize()
        println("Initialized mcp sync")
        return mcp
    }

    @Bean("simulationAgent")
    fun mcpSyncClientSimulationCDB(): McpSyncClient {
        val mcp = McpClient.sync(
            HttpClientSseClientTransport.builder("http://localhost:8082")
                .build()
        ).build()
        mcp.initialize()
        println("Initialized mcp sync")
        return mcp
    }

    @Bean("creditCardLimitAgent")
    fun mcpSyncClientCreditCardLimit(): McpSyncClient {
        val mcp = McpClient.sync(
            HttpClientSseClientTransport.builder("http://localhost:8083")
                .build()
        ).build()
        mcp.initialize()
        println("Initialized mcp sync")
        return mcp
    }
}
