package com.example.rajjaja

import android.content.Intent // Certifique-se que o Intent está importado
import android.os.Bundle
import android.util.Log
// import android.widget.Button // Não é mais necessário importar Button diretamente se estiver usando apenas binding
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.rajjaja.databinding.ActivityMainBinding // Import do binding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding // Declaração do binding
    private lateinit var userArrayList: ArrayList<User>

    // ... (Companion object com as constantes EXTRA_ permanece o mesmo) ...
    companion object {
        const val EXTRA_NAME = "extra_name"
        const val EXTRA_SEPRECISO = "extra_Preciso"
        const val EXTRA_SEPRECISO2 = "extra_Preciso2"
        const val EXTRA_IMAGE_ID = "extra_image_id"
        const val EXTRA_LAST_MESSAGE = "extra_last_message"
        const val EXTRA_PRICE_STRING = "extra_price_string"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater) // Inflar o binding
        enableEdgeToEdge()
        setContentView(binding.root) // Usar a view raiz do binding

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // ... (Seu código para popular imageIdArray, nameArray, etc., e o loop para criar userArrayList permanece o mesmo) ...
        val imageIdArray = intArrayOf(
            R.drawable.a, R.drawable.b, R.drawable.c, R.drawable.d, R.drawable.e, R.drawable.f, R.drawable.sobremesa
        )
        val nameArray = arrayOf("Sashimi", "Niguiri", "Hot", "Temaki", "Gyoza", "Carpaccio", "Sobremesa")
        val lastMessageArray = arrayOf("Laminas de salmão", "Lamina de salmao com arroz", "Sushi frito", "Cone de alga recheado de arroz com peixe", "Pastel asiatico frito, com recheio de porco ou salmao", "Laminas de salmao com molho", "Pudim de leite condensado")
        val pricesArray = arrayOf("20.00", "10.15", "10.30", "10.45", "11.00", "11.15", "10.00")
        val sePre = arrayOf("Detalhe 1A", "Detalhe 1B", "Detalhe 1C", "Detalhe 1D", "Detalhe 1E", "Detalhe 1F", "Detalhe 1G")
        val countryArray = arrayOf("Info 2A", "Info 2B", "Info 2C", "Info 2D", "Info 2E", "Info 2F", "Info 2G")

        userArrayList = ArrayList()
        val itemCount = nameArray.size
        for (i in 0 until itemCount) {
            if (i < imageIdArray.size && i < lastMessageArray.size &&
                i < pricesArray.size && i < sePre.size && i < countryArray.size) {
                val user = User(
                    nameArray[i],
                    lastMessageArray[i],
                    imageIdArray[i],
                    pricesArray[i],
                    sePre[i],
                    countryArray[i]
                )
                userArrayList.add(user)
            } else {
                Log.w("MainActivity", "Inconsistência no tamanho dos arrays de dados no índice: $i. Item não adicionado.")
            }
        }


        binding.listview.isClickable = true
        binding.listview.adapter = MyAdapter(this, userArrayList)

        binding.listview.setOnItemClickListener { _, _, position, _ ->
            if (position < userArrayList.size) {
                val clickedUser = userArrayList[position]
                val intent = Intent(this, UserActivity::class.java).apply {
                    putExtra(EXTRA_NAME, clickedUser.name)
                    putExtra(EXTRA_SEPRECISO, clickedUser.phoneNo)
                    putExtra(EXTRA_SEPRECISO2, clickedUser.sePrec)
                    putExtra(EXTRA_IMAGE_ID, clickedUser.imageId)
                    putExtra(EXTRA_LAST_MESSAGE, clickedUser.lastMessage)
                    putExtra(EXTRA_PRICE_STRING, clickedUser.sePrec2)
                }
                startActivity(intent)
            }
        }

        // --- ADICIONE ESTE TRECHO PARA O BOTÃO ---
        binding.buttonConfirmarCarrinho.setOnClickListener {
            val intent = Intent(this, ConfirmacaoActivity::class.java)
            startActivity(intent)
        }
        // --- FIM DO TRECHO PARA O BOTÃO ---
    }
}