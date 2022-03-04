package uz.context.androidnetworking.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import uz.context.androidnetworking.util.Logger
import uz.context.androidnetworking.R
import uz.context.androidnetworking.adapter.PosterAdapter
import uz.context.androidnetworking.model.Poster
import uz.context.androidnetworking.model.PosterResp
import uz.context.androidnetworking.network.volley.VolleyHandler
import uz.context.androidnetworking.network.volley.VolleyHttp
import uz.context.androidnetworking.retrofit.RetrofitHttp

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var btnFloating: FloatingActionButton
    private lateinit var progressBar: ProgressBar
    private lateinit var posterAdapter: PosterAdapter
    private val posters = ArrayList<Poster>()
    private lateinit var textView: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()

    }

    private fun initViews() {
        recyclerView = findViewById(R.id.recyclerView)
        btnFloating = findViewById(R.id.btn_floating)
        recyclerView.layoutManager = LinearLayoutManager(this)
        progressBar = findViewById(R.id.progressBar)
        progressBar.isVisible = true
        apiPosterList()
    }

    private fun apiPosterList() {
        VolleyHttp.get(VolleyHttp.API_LIST_POST, VolleyHttp.paramsEmpty(), object : VolleyHandler {
            override fun onSuccess(response: String?) {
                val posterArray = Gson().fromJson(response, Array<Poster>::class.java)
                posters.addAll(posterArray)

                progressBar.isVisible = false
                refreshAdapter(posters)
            }

            override fun onError(error: String?) {
                progressBar.isVisible = true
                Log.d("@@@", error!!)
            }
        })
    }


    private fun refreshAdapter(posters: ArrayList<Poster>) {
        posterAdapter = PosterAdapter(this, posters)
        recyclerView.adapter = posterAdapter
    }

    fun dialogPoster(poster: Poster?) {
        AlertDialog.Builder(this)
            .setTitle("Delete User")
            .setMessage("Are you sure you want to delete this poster?")
            .setPositiveButton(
                android.R.string.yes
            ) { dialog, which -> apiPosterDelete(poster!!)
//                Handler(Looper.getMainLooper()).postDelayed({
//                    progressBar.isVisible = true
//                },1000)
            }
            .setNegativeButton(android.R.string.no, null)
            .setIcon(android.R.drawable.ic_dialog_alert)
            .show()
    }

    private fun apiPosterDelete(poster: Poster) {
        VolleyHttp.del(VolleyHttp.API_DELETE_POST + poster.id, object : VolleyHandler {
            override fun onSuccess(response: String?) {
                apiPosterList()
            }

            override fun onError(error: String?) {
                Log.d("@@@", error!!)
            }
        })
    }

    private fun workWithRetrofit() {
        RetrofitHttp.posterService.listPost().enqueue(object : Callback<ArrayList<PosterResp>> {
            override fun onResponse(
                call: Call<ArrayList<PosterResp>>,
                response: Response<ArrayList<PosterResp>>
            ) {
                Logger.d("@@@", response.body().toString())
                textView.text = response.body().toString()
            }

            override fun onFailure(call: Call<ArrayList<PosterResp>>, t: Throwable) {
                Logger.e("@@@", t.message.toString())
                textView.text = t.message.toString()
            }
        })
    }

    private fun createPost() {
        val post = Poster(1, 1, "Pdp", "Online")
        RetrofitHttp.posterService.createPost(post).enqueue(object : Callback<PosterResp> {
            override fun onResponse(call: Call<PosterResp>, response: Response<PosterResp>) {
                Log.d("@@@", response.body().toString())
                textView.text = response.body().toString()
            }

            override fun onFailure(call: Call<PosterResp>, t: Throwable) {
                Log.e("@@@", t.message.toString())
                textView.text = t.message.toString()
            }
        })
    }

    private fun updatePost() {
        val post = Poster(1, 1, "Pdp", "Online")
        RetrofitHttp.posterService.updatePost(post.id, post).enqueue(object : Callback<PosterResp> {
            override fun onResponse(call: Call<PosterResp>, response: Response<PosterResp>) {
                Log.d("@@@", response.body().toString())
                textView.text = response.body().toString()
            }

            override fun onFailure(call: Call<PosterResp>, t: Throwable) {
                Log.e("@@@", t.message.toString())
                textView.text = t.message.toString()
            }
        })
    }

    private fun deletePost() {
        val post = Poster(1, 1, "Pdp", "Online")
        RetrofitHttp.posterService.deletePost(post.id).enqueue(object : Callback<PosterResp> {
            override fun onResponse(call: Call<PosterResp>, response: Response<PosterResp>) {
                Log.d("@@@", response.body().toString())
                textView.text = response.body().toString()
            }

            override fun onFailure(call: Call<PosterResp>, t: Throwable) {
                Log.e("@@@", t.message.toString())
                textView.text = t.message.toString()
            }
        })
    }

    private fun workWithVolley() {
        VolleyHttp.get(VolleyHttp.API_LIST_POST, VolleyHttp.paramsEmpty(), object : VolleyHandler {
            override fun onSuccess(response: String?) {
                Logger.d("VolleyHttp", response!!)
                textView.text = response
            }

            override fun onError(error: String?) {
                Logger.d("VolleyHttp", error!!)
                textView.text = error
            }
        })
    }

    private fun poster() {
        val poster = Poster(1, 1, "Pdp", "Online")
        VolleyHttp.post(
            VolleyHttp.API_CREATE_POST,
            VolleyHttp.paramsCreate(poster),
            object : VolleyHandler {
                override fun onSuccess(response: String?) {
                    Log.d("@@@", response!!)
                }

                override fun onError(error: String?) {
                    Log.d("@@@", error!!)
                }
            })
    }

    private fun put() {
        val poster = Poster(1, 1, "Pdp", "Online")
        VolleyHttp.put(
            VolleyHttp.API_UPDATE_POST + poster.id,
            VolleyHttp.paramsUpdate(poster),
            object :
                VolleyHandler {
                override fun onSuccess(response: String?) {
                    Log.d("@@@", response!!)
                }

                override fun onError(error: String?) {
                    Log.d("@@@", error!!)
                }
            })
    }

    private fun del() {
        val poster = Poster(1, 1, "Pdp", "Online")
        VolleyHttp.del(VolleyHttp.API_DELETE_POST + poster.id, object : VolleyHandler {
            override fun onSuccess(response: String?) {
                Log.d("@@@", response!!)
            }

            override fun onError(error: String?) {
                Log.d("@@@", error!!)
            }
        })
    }
}