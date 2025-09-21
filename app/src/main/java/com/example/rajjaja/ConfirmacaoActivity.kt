package com.example.rajjaja

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

// Certifique-se que o nome do binding está correto. Se seu arquivo XML é confirmacao.xml,
// o binding gerado será ConfirmacaoBinding (ou ActivityConfirmacaoBinding, dependendo das suas configurações)
import com.example.rajjaja.databinding.ConfirmacaoBinding // Verifique este nome!
import java.text.NumberFormat
import java.util.Locale

class ConfirmacaoActivity : AppCompatActivity() {

    // Se o seu arquivo XML for "confirmacao.xml", o binding provavelmente será "ConfirmacaoBinding"
    // Se for "activity_confirmacao.xml", será "ActivityConfirmacaoBinding"
    private lateinit var binding: ConfirmacaoBinding
    private lateinit var sharedPreferences: SharedPreferences
    private val PREF_NAME = "CarrinhoPref"
    private val KEY_TOTAL_CARRINHO = "totalCarrinho"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Inflar o layout usando View Binding
        binding = ConfirmacaoBinding.inflate(layoutInflater) // VERIFIQUE O NOME DO BINDING
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

        displayTotalCarrinho()

        binding.buttonFinalizarPagamentoConfirmacao.setOnClickListener {
            val totalFinal = sharedPreferences.getFloat(KEY_TOTAL_CARRINHO, 0.0f)
            val formaPagamentoSelecionada = when (binding.radioGroupPagamentoConfirmacao.checkedRadioButtonId) {
                R.id.radioButtonDinheiroConfirmacao -> "Dinheiro"
                R.id.radioButtonCreditoConfirmacao -> "Crédito"
                R.id.radioButtonDebitoConfirmacao -> "Débito"
                else -> "Não selecionada"
            }
            val mesaSelecionada = when (binding.radioGroupMesaConfirmacao.checkedRadioButtonId) {
                R.id.radioButtonMesa1Confirmacao -> "Mesa 01"
                R.id.radioButtonMesa2Confirmacao -> "Mesa 02"
                R.id.radioButtonMesa3Confirmacao -> "Mesa 03"
                else -> "Não selecionada"
            }

            if (formaPagamentoSelecionada == "Não selecionada") {
                Toast.makeText(this, "Por favor, selecione a forma de pagamento.", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            if (mesaSelecionada == "Não selecionada") {
                Toast.makeText(this, "Por favor, selecione o número da mesa.", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            // Simulação de processamento
            Toast.makeText(
                this,
                "Pedido Confirmado! Total: ${formatPrice(totalFinal)}, Pagamento: $formaPagamentoSelecionada, Mesa: $mesaSelecionada",
                Toast.LENGTH_LONG
            ).show()

            // Opcional: Limpar o carrinho após finalizar
            // sharedPreferences.edit().remove(KEY_TOTAL_CARRINHO).apply()
            // displayTotalCarrinho() // Para atualizar a UI se limpar

            // Opcional: Voltar para a MainActivity ou fechar esta tela
            // finish()
            // Ou iniciar uma nova activity, por exemplo, de "Pedido Enviado"
            // val intent = Intent(this, PedidoEnviadoActivity::class.java)
            // startActivity(intent)
        }
    }

    private fun displayTotalCarrinho() {
        val total = sharedPreferences.getFloat(KEY_TOTAL_CARRINHO, 0.0f)
        val totalFormatado = formatPrice(total)
        binding.textViewTotalPedidoConfirmacao.text = "Total: $totalFormatado"
    }

    private fun formatPrice(price: Float): String {
        val format = NumberFormat.getCurrencyInstance(Locale("pt", "BR"))
        return format.format(price)
    }
}