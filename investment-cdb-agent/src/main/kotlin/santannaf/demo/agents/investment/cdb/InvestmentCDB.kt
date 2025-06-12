package santannaf.demo.agents.investment.cdb

import org.springframework.ai.tool.annotation.Tool
import org.springframework.ai.tool.annotation.ToolParam
import org.springframework.stereotype.Component
import kotlin.math.pow

@Component
class InvestmentCDB {

    companion object {
        const val CDI_ANUAL: Double = 0.1465 // 14,65% a.a.
    }

    @Tool(name = "simulateCDB", description = "Simula o rendimento de um CDB com base em valor, prazo e tipo de taxa")
    fun simulation(
        @ToolParam(description = "Valor inicial investido") amount: Double,
        @ToolParam(description = "Prazo em meses") time: Int,
        @ToolParam(description = "Tipo de taxa: 'pre' ou 'pos' fixado") type: String
    ): String {
        val taxaMensal: Double = if (type.equals("pre", ignoreCase = true)) {
            0.0125 // 1,25% ao mês fixo para pré
        } else if (type.equals("pos", ignoreCase = true)) {
            (1 + CDI_ANUAL).pow(1.0 / 12.0) - 1
        } else {
            return "Tipo inválido. Use 'pre' ou 'pos'."
        }

        val valorFinal = amount * (1 + taxaMensal).pow(time.toDouble())
        return String.format(
            "Para um investimento de R$ %.2f durante %d meses (%s-fixado), o valor final estimado é R$ %.2f.",
            amount, time, type, valorFinal
        )
    }
}
