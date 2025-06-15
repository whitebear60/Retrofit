package ua.edu.udhtu.retrofit

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ua.edu.udhtu.retrofit.databinding.ActivityMainBinding
import ua.edu.udhtu.retrofit.rest.API
import ua.edu.udhtu.retrofit.rest.Result
import ua.edu.udhtu.retrofit.rest.RetrofitClient
import ua.edu.udhtu.retrofit.searchresults.SearchResultsActivity
import java.text.MessageFormat

class MainActivity : ComponentActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btLoad.setOnClickListener {
            Log.i("RETROFIT_SEARCH", binding.tiLocation.editText?.text.toString())
            binding.pbProgress.visibility = View.VISIBLE
            val inLocation = binding.tiLocation.editText?.text.toString()
            val inLang = binding.tiLanguage.editText?.text.toString()
            if ((inLocation.trim() == "" || inLocation.trim() == "null") && ( inLang.trim() == "" || inLocation.trim() == "null")) {
                Toast.makeText(this, getString(R.string.toastEmptyForm), Toast.LENGTH_SHORT).show()
                binding.pbProgress.visibility = View.GONE
            } else {
                val retrofit = RetrofitClient.instance.create(API::class.java)
                retrofit.searchUsers(MessageFormat.format("location:{0} language:{1}", inLocation, inLang)).enqueue(object : Callback<Result> {
                    override fun onResponse(call: Call<Result>, response: Response<Result>) {
                        Log.i("RETROFIT_SEARCH", response.raw().request.url.toString())
                        if (response.body() != null) {
                            val intent = Intent(this@MainActivity, SearchResultsActivity::class.java)
                            intent.putExtra("searchResults", response.body()!!.items)
                            startActivity(intent)
//                        binding.tvFact.text = response.body()!!.items.toString()
                            binding.pbProgress.visibility = View.GONE
                            Log.i("RETROFIT", response.raw().request.url.toString())
                        }
                    }

                    override fun onFailure(call: Call<Result>, t: Throwable) {
                        Log.w("RetrofitAppLog", "An error occured while parsing")
                    }

                })
            }
        }
    }
}