package santannaf.demo.agents.faq

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.Resource

@Configuration
@ConfigurationProperties(prefix = "sfg.aiapp")
data class VectorStoreProperties(
    var vectorStorePath: String? = null,
    var documentsToLoad: MutableList<Resource> = mutableListOf()
)
