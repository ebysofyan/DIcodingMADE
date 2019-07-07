package ebysofyan.app.made.submission

import android.app.Application
import ebysofyan.app.made.submission.data.local.MyObjectBox
import io.objectbox.BoxStore


/**
 * Created by @ebysofyan on 7/8/19
 */

class App : Application() {
    companion object {
        var app: App? = null
        var boxStore: BoxStore? = null
    }

    override fun onCreate() {
        super.onCreate()

        app = this
        boxStore = MyObjectBox.builder().androidContext(applicationContext).build()
    }
}