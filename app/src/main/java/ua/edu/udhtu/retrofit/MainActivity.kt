package ua.edu.udhtu.retrofit

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ua.edu.udhtu.retrofit.databinding.ActivityMainBinding
import ua.edu.udhtu.retrofit.rest.API
import ua.edu.udhtu.retrofit.rest.ResponseMain
import ua.edu.udhtu.retrofit.rest.RetrofitClient
import java.text.MessageFormat
import java.text.SimpleDateFormat
import java.time.Instant
import java.util.Date
import java.util.Locale

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
            val retrofit = RetrofitClient.getClient("https://data-api.coindesk.com/index/cc/v1/").create(API::class.java)
            retrofit.getExchangeRate("ccix", "BTC-USD").enqueue(object : Callback<ResponseMain> {
                override fun onResponse(call: Call<ResponseMain>, response: Response<ResponseMain>) {
                    if (response.body() != null) {
                        binding.tvFact.text =
                            MessageFormat.format(
                                "Bitcoin Exchange Rate as of {0}:",
                                SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date.from(Instant.now())),
                            )
                        binding.tvRate.text = String.format(Locale.getDefault(), "1BTC = $%.2f", response.body()!!.data.rates.value)
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