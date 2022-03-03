package uz.context.androidnetworking.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import uz.context.androidnetworking.util.Logger
import uz.context.androidnetworking.R
import uz.context.androidnetworking.model.Poster
import uz.context.androidnetworking.model.PosterResp
import uz.context.androidnetworking.network.volley.VolleyHandler
import uz.context.androidnetworking.network.volley.VolleyHttp
import uz.context.androidnetworking.retrofit.RetrofitHttp

class MainActivity : AppCompatActivity() {
    private lateinit var textView: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()

    }

    private fun initViews() {
        textView = findViewById(R.id.textView)
        updatePost()
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
        RetrofitHttp.posterService.deletePost(post.id).enqueue(object: Callback<PosterResp> {
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