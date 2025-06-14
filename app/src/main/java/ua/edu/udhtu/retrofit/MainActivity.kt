package ua.edu.udhtu.retrofit

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.transition.Visibility
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ua.edu.udhtu.retrofit.databinding.ActivityMainBinding
import ua.edu.udhtu.retrofit.rest.API
import ua.edu.udhtu.retrofit.rest.ResponseMain
import ua.edu.udhtu.retrofit.rest.RetrofitClient

class MainActivity : ComponentActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btLoad.setOnClickListener {
            binding.pbProgress.visibility = View.VISIBLE
            binding.tvFact.visibility = View.GONE
            val retrofit = RetrofitClient.getClient("https://catfact.ninja/").create(API::class.java)
            retrofit.getRandomFact().enqueue(object : Callback<ResponseMain> {
                override fun onResponse(call: Call<ResponseMain>, response: Response<ResponseMain>) {
                    if (response.body() != null) {
                        binding.tvFact.text = response.body()!!.fact
                        binding.pbProgress.visibility = View.GONE
                        binding.tvFact.visibility = View.VISIBLE
                    }
                }

                override fun onFailure(call: Call<ResponseMain>, t: Throwable) {
                    Log.w("RetrofitAppLog", "An error occured while parsing the cat fact")
                }

            })
        }
    }
}