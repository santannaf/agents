package santannaf.demo.agents.credit_card.limit

import java.util.*
import java.util.concurrent.ConcurrentHashMap
import org.springframework.ai.tool.annotation.Tool
import org.springframework.ai.tool.annotation.ToolParam
import org.springframework.stereotype.Component

@Component
class CreditCardLimitAgent {
    private val balanceByUser: MutableMap<String, Double> = ConcurrentHashMap<String, Double>()

    init {
        balanceByUser["thales"] = 3500.0
        balanceByUser["ana"] = 1200.0
        balanceByUser["joao"] = 800.0
        balanceByUser["usuario-web"] = 8000.0
    }

    @Tool(name = "", description = "Ajusta o limite do usuário com base no perfil de uso")
    fun ajustarLimite(
        @ToolParam user: String,
        @ToolParam(description = "Tipo de operação: 'aumento' ou 'reducao'") operation: String
    ): String {
        println(">>> Entrou na Tool com pergunta: \"$user\" e operation: $operation")

        val saldoAtual = balanceByUser.getOrDefault(user.lowercase(Locale.getDefault()), 1000.0)

        if (operation.equals("aumento", ignoreCase = true)) {
            val novoLimite = saldoAtual * 1.2 // aumento de 20%
            balanceByUser[user.lowercase(Locale.getDefault())] = novoLimite
            return String.format(
                "Limite aumentado para R$ %.2f com base no saldo anterior de R$ %.2f.",
                novoLimite,
                saldoAtual
            )
        } else if (operation.equals("reducao", ignoreCase = true)) {
            val novoLimite = saldoAtual * 0.8 // redução de 20%
            balanceByUser[user.lowercase(Locale.getDefault())] = novoLimite
            return String.format(
                "Limite reduzido para R$ %.2f com base no saldo anterior de R$ %.2f.",
                novoLimite,
                saldoAtual
            )
        } else {
            return "Operação inválida. Use 'aumento' ou 'reducao'."
        }
    }
}
