package ir.easazade.okhttp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response
import java.io.IOException

class MyOwnHostActivity : AppCompatActivity() {

  private lateinit var okHttpClient: OkHttpClient
  private var token = ""

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_my_own_host)
    okHttpClient = createNewOkHttpClient(token)
  }

  /*
  WHAT I WANT TO GET

  Array ( [username] => alireza [titles] => Array ( [0] => title1 [1] => title2 [2] => title3 ) [submit] => Upload Image )
File is an image - image/png.The file rocketman1.png has been uploaded.
   */

  fun multiPart(v: View) {
    val file = getFileFromAssetsAndCopyToCache("image2.jpg", ".jpg", this)
    val multiPartBuilder = MultipartBody.Builder()
      .setType(MultipartBody.FORM)
      .addFormDataPart("user", "alireza")
      .addFormDataPart(
        "fileToUpload",
        file.name,
        RequestBody.create(MediaType.parse("image/jpg"), file)
      )

    val titles = listOf("awesome", "powerful", "strong", "best programmer")
    for (i in 0 until titles.size) {
      multiPartBuilder.addFormDataPart("titles[$i]", titles[i])
    }

    val requestBody = multiPartBuilder.build()

    val request = Request.Builder()
      .url("http://alirezaeasazade.ir/upload.php")
      .post(requestBody)
      .build()

    okHttpClient.newCall(request).enqueue(object : Callback {
      override fun onResponse(call: Call, response: Response) {}
      override fun onFailure(call: Call, e: IOException) {}
    })
  }
}
