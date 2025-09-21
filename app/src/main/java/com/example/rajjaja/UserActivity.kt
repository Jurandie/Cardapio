package com.example.rajjaja

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.rajjaja.databinding.ActivityUserBinding // Certifique-se que o nome do binding está correto
import java.text.NumberFormat
import java.util.Locale

class UserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserBinding
    private lateinit var sharedPreferences: SharedPreferences
    private val PREF_NAME = "CarrinhoPref"
    private val KEY_TOTAL_CARRINHO = "totalCarrinho"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

        val name = intent.getStringExtra(MainActivity.EXTRA_NAME)
        val sePrecisar = intent.getStringExtra(MainActivity.EXTRA_SEPRECISO) // Corresponde a User.phoneNo
        val sePrecisarDois = intent.getStringExtra(MainActivity.EXTRA_SEPRECISO2) // Corresponde a User.sePrec
        val imageId = intent.getIntExtra(MainActivity.EXTRA_IMAGE_ID, R.drawable.a)
        val lastMessage = intent.getStringExtra(MainActivity.EXTRA_LAST_MESSAGE)
        // CORREÇÃO: Usar a constante correta da MainActivity
        val precoString = intent.getStringExtra(MainActivity.EXTRA_PRICE_STRING) // Corresponde a User.sePrec2

        binding.nameProfile.text = name
        binding.sePreciso.text = if (sePrecisar.isNullOrEmpty()) "Item não informado" else "Item: $sePrecisar"
        binding.precisaoProfile.text = if (sePrecisarDois.isNullOrEmpty()) "...." else "Adicional: $sePrecisarDois" // Ajuste o label conforme necessário
        binding.profileImage.setImageResource(imageId)
        binding.lastMessageProfile.text = lastMessage
        // Exibindo o preço formatado
        binding.timeProfile.text = if (precoString.isNullOrEmpty()) "" else "Preço: ${formatPriceString(precoString)}"


        binding.addToCartButton.setOnClickListener {
            val precoNumerico = parsePrice(precoString) // Usando a string de preço recebida

            if (precoNumerico != null) {
                val totalAtual = sharedPreferences.getFloat(KEY_TOTAL_CARRINHO, 0.0f)
                val novoTotal = totalAtual + precoNumerico
                sharedPreferences.edit().putFloat(KEY_TOTAL_CARRINHO, novoTotal).apply()

                Toast.makeText(
                    this,
                    "${name ?: "Item"} adicionado! Novo total: ${formatPriceFloat(novoTotal)}", // Usar formatPriceFloat
                    Toast.LENGTH_LONG
                ).show()
                // Opcional: finalizar esta activity e voltar para a MainActivity
                // finish()
            } else {
                Toast.makeText(this, "Não foi possível adicionar o item: preço inválido.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun parsePrice(priceString: String?): Float? {
        if (priceString.isNullOrEmpty()) return null
        // Agora o priceString já deve ser algo como "20.00"
        return try {
            priceString.toFloat()
        } catch (e: NumberFormatException) {
            null // Retorna null se a conversão falhar
        }
    }

    // Formata um valor float para exibição como moeda (ex: "R$ 20,00")
    private fun formatPriceFloat(price: Float): String {
        val format = NumberFormat.getCurrencyInstance(Locale("pt", "BR"))
        return format.format(price)
    }

    // Formata uma string de preço (ex: "20.00") para exibição como moeda
    private fun formatPriceString(priceString: String?): String {
        if (priceString.isNullOrEmpty()) return "Preço indisponível"
        return try {
            val priceFloat = priceString.toFloat()
            formatPriceFloat(priceFloat)
        } catch (e: NumberFormatException) {
            "Preço inválido" // Retorna isso se a string não for um número válido
        }
    }
}