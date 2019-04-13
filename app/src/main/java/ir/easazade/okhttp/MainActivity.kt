package ir.easazade.okhttp

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
  }

  //TODO go to below address for advanced examples
  //https://www.baeldung.com/guide-to-okhttp

  fun reqResIn(v: View) {
    val intent = Intent(this,ReqResInActivity::class.java)
    startActivity(intent)
  }

  fun myOwnHosting(v: View) {
    val intent = Intent(this,MyOwnHostActivity::class.java)
    startActivity(intent)
  }
}
