package ir.easazade.okhttp

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import okhttp3.Call
import okhttp3.Callback
import okhttp3.FormBody
import okhttp3.Interceptor
import okhttp3.Interceptor.Chain
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject
import timber.log.Timber
import java.io.IOException

class ReqResInActivity : AppCompatActivity() {

  private lateinit var okHttpClient: OkHttpClient
  private var token = ""

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_req_res_in)
    okHttpClient = createNewOkHttpClient(token)
  }

  fun simpleGet(v: View) {
    val request = Request.Builder()
      .url("https://reqres.in/api/users?page=2")
      .build()

    okHttpClient.newCall(request).enqueue(object : Callback {
      override fun onResponse(call: Call, response: Response) {
        val stringResponse = response.body()?.string()
      }

      override fun onFailure(call: Call, e: IOException) {
        Timber.e("simpleGet call failed")
        if (!call.isCanceled)
          call.cancel()
      }
    })
  }

  fun simplePost(v: View) {
    val JSON = MediaType.parse("application/json;charset=utf-8")
    val jsonData = JSONObject().apply {
      put("name", "alireza")
      put("age", 24)
    }
    val requestBody = RequestBody.create(JSON, jsonData.toString())
    val request = Request.Builder()
      .url("https://reqres.in/api/users")
      .post(requestBody)
      .build()

    okHttpClient.newCall(request).enqueue(object : Callback {
      override fun onResponse(call: Call, response: Response) {
      }

      override fun onFailure(call: Call, e: IOException) {
        Timber.e("simpleGet call failed")
        if (!call.isCanceled)
          call.cancel()
      }
    })
  }

  fun postFormData(v: View) {
    val requestBody = FormBody.Builder()
      .add("name", "alireza")
      .add("age", 25.toString())
      .build()

    val request = Request.Builder()
      .url("https://reqres.in/api/users")
      .post(requestBody)
      .build()

    okHttpClient.newCall(request).enqueue(object : Callback {
      override fun onResponse(call: Call, response: Response) {}
      override fun onFailure(call: Call, e: IOException) {}
    })
  }

}
