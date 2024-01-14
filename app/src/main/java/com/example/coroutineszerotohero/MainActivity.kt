package com.example.coroutineszerotohero

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

// Las coroutinas las vamos a usar para las Activities y para el ViewModel (cada una con su Graddle)

class MainActivity : AppCompatActivity() {

    val retrofit = RetrofitHelper.getInstance()

    // Existen 3 tipos de hilos distintos: (2 y 3 son tipos de DISPATRCHERS):
    // 1. MAIN = Hilo principal -> pinta la UI -> Si se sobrecarga se bloquea (si es algo de UI, es un must que esté en MAIN)
    // 2. IO = Hilo que deberíamos usar para hacer peticiones/ acciones que tardan pero que no toman mucho procesamiento (llamar Retrofit, etc)
    // 3. Default = Operacines pesadas de mucho cálculo, mucho algoritmo, algo muy Compleja, que use mucha CPU (procesar info)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lifecycleScope.launch(Dispatchers.IO){
            val response: Response<SuperHeroDataResponse> = retrofit.getSuperheroes("a")
            // una función suspendida sólo puede ser llamada desde dentro de una Corrutina o desde otra función suspendida
            withContext(Dispatchers.Main) {  // a veces se puede usar igual el runOnUiThread en lugar de withContext
                if(response.isSuccessful){
                    Toast.makeText(this@MainActivity,"FUNCIONA",Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    fun waitForCoroutines(){    // Esta función nos sirve para lanzar una Corrutina en el hilo secundario pero cuando necesitamos que se
        //termine para poder continuar en el hilo principal
        lifecycleScope.launch(Dispatchers.IO){
    //        val deferred1 = async { retrofit.getSuperheroes("a") }  // con esto lo volvemos tipo deferred
    //        val deferred2 = async { retrofit.getSuperheroes("b") }
    //        val deferred3 = async { retrofit.getSuperheroes("c") }
    //        val deferred4 = async { retrofit.getSuperheroes("d") }

    //        val response = deferred1.await()  // con esto bloquemos que no termine hasta que haya terminado el deferred1
    //        val response2 = deferred2.await()
    //        val response3 = deferred3.await()
    //        val response4 = deferred4.await()
    //

            
            // Ahora en caso de que fueran 50 variables, mejor hacemos una lista:
            val deferreds = listOf(
                async { retrofit.getSuperheroes("a") },
                async { retrofit.getSuperheroes("b") },
                async { retrofit.getSuperheroes("c") },
                async { retrofit.getSuperheroes("d") },
                async { retrofit.getSuperheroes("e") },
                async { retrofit.getSuperheroes("f") },
                async { retrofit.getSuperheroes("g") },
                async { retrofit.getSuperheroes("h") },
                async { retrofit.getSuperheroes("i") },
                async { retrofit.getSuperheroes("j") }
            )

            val response = deferreds.awaitAll()   // se llama igual "response" suponiendo que las líneas de arriba no están (43-51)
        }
    }

    suspend fun suscribete() {

    }
}