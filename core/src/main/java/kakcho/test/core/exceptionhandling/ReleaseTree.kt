package kakcho.test.core.exceptionhandling

import android.util.Log
import org.jetbrains.annotations.NotNull
import timber.log.Timber

/**
 * Created by Kamal Nayan on 22-09-2021 at 20:40
 */

class ReleaseTree : @NotNull Timber.Tree() {

    /**
     *
     * We are overriding the log function so that wherever we are using log and
     * planted the Timber in a way that if the app is in release mode
     * then the below function will be invoked
     *
     *
     * @param priority Int - Is priority of log , example [Log.ERROR],[Log.WARN] etc...
     * @param tag String?  - If any tag of log
     * @param message String - Message in log
     * @param throwable Throwable? - If any throwable in log
     */

    override fun log(priority: Int, tag: String?, message: String, throwable: Throwable?) {

        if (priority == Log.ERROR || priority == Log.WARN) {
            if (throwable != null) {
                /** SEND IT TO CLOUD : BELOW IS CODE FOR FIREBASE CRASHLYTICS
                 *  FirebaseCrashlytics.getInstance().recordException(throwable)
                 */
            }
        }
    }
}