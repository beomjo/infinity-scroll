package k.bs.infinityscroll.base

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import k.bs.infinityscroll.util.error.CanceledByUserException
import k.bs.infinityscroll.util.getObject
import k.bs.infinityscroll.util.putObject
import java.lang.reflect.Type


@SuppressLint("Registered")
open class RxActivityBase : AppCompatActivity() {
    inline fun <reified T> getInput(): T {
        return getInput(T::class.java)
    }

    fun <T> getInput(clazz: Type): T {
        return intent.getObject(clazz) as T
    }

    fun finishWithResponse(any: Any) {
        setResult(RESULT_OK, Intent().putObject(any))
        finish()
    }

    fun finishWithCancel() {
        finishWithException(CanceledByUserException())
    }

    fun finishWithException(t: Throwable) {
        setResult(Activity.RESULT_CANCELED, Intent().putObject(t))
        finish()
    }
}

