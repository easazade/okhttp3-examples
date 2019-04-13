package ir.easazade.okhttp

import android.app.Application
import timber.log.Timber
import timber.log.Timber.DebugTree

class App : Application() {

  //TODO go to below address for advanced examples
  //https://www.baeldung.com/guide-to-okhttp

  override fun onCreate() {
    super.onCreate()
    Timber.plant(DebugTree())
  }

}