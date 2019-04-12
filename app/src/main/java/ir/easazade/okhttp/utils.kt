package ir.easazade.okhttp

import android.content.Context
import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject
import timber.log.Timber
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.Random

fun String.jsonPrettyPrint() {
  try {
    Timber.d(
      JSONObject(this).toString(2)
    )
  } catch (e: Exception) {
    Timber.e(e, "error when parsing json")
  }
}

fun createNewOkHttpClient(token: String): OkHttpClient {
  val builder = OkHttpClient.Builder()
  if (BuildConfig.DEBUG) {
    val loggerInterceptor = HttpLoggingInterceptor { msg ->
      Log.d("tagtag", msg)
    }
    loggerInterceptor.level = HttpLoggingInterceptor.Level.BODY
    builder.addInterceptor(loggerInterceptor)
  }
  builder.addInterceptor { chain ->
    val request = chain.request().newBuilder()
      .addHeader("Content-Type", "application/json")
      .addHeader("X-Requested-With", "XMLHttpRequest")
      .addHeader("Authorization", "bearer $token").build()

    val response = chain.proceed(request)
//    response.peekBody(4000).string().jsonPrettyPrint()
    return@addInterceptor response
  }
  return builder.build()
}

fun generateImageFileName(extensionWithDot: String): String {
  val rand = Random()
  val letters = arrayOf(
    "a", "b", "c", "d", "e", "d", "g", "h", "i",
    "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y",
    "z", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "P", "Q",
    "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "1", "2", "3", "4", "5", "6", "7", "8", "9"
  )

  var imageFileName = ""
  for (i in 0..9) {
    val x = rand.nextInt(60)
    imageFileName += letters[x]
  }

  return imageFileName + extensionWithDot
}

fun getFileFromAssetsAndCopyToCache(path: String, extension: String, context: Context): File {
  val outputCacheFile = File(context.cacheDir, generateImageFileName(extension))
  var fos: FileOutputStream? = null
  try {
    //read file
    val inputStream = context.assets.open(path)
    val size = inputStream.available()
    val buffer = ByteArray(size)
    inputStream.read(buffer)
    inputStream.close()

    //now create file in cache directory
    fos = FileOutputStream(outputCacheFile)
    fos.write(buffer)
    fos.close()
  } catch (e: java.lang.Exception) {
    Timber.e(e)
    fos?.close()
  } catch (ioe: IOException) {
    Timber.e(ioe)
    fos?.close()
  }
  return outputCacheFile
}