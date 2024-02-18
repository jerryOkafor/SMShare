import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.browser.customtabs.CustomTabsIntent
import channel.ChannelConfig
import java.net.URLEncoder

actual class Platform actual constructor() {
    actual val platform: String
        get() = "Android ${Build.VERSION.SDK_INT}"
}


actual class AuthManager private actual constructor() {
    actual var currentChannel: ChannelConfig? = null

    lateinit var context: Context
        private set

    constructor(context: Context) : this() {
        this.context = context
    }

    actual fun authenticateUser(channelConfig: ChannelConfig) {
        currentChannel = channelConfig

        Log.d("AuthManager", "Authenticating user with $channelConfig | Context: $context")
        val challenge = URLEncoder.encode("123456NJSANLENLNE", "utf-8")
        val redirectUrl = URLEncoder.encode("http://com.jerryokafor.smshare/auth/callback", "utf-8")

        val oath2Url = channelConfig.createLoginUrl(
            redirectUrl = redirectUrl,
            challenge = challenge
        )

        val builder = CustomTabsIntent.Builder()
        val customTabsIntent = builder.build().apply {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }

        Log.d("AuthManager", "Launching: $oath2Url")

        customTabsIntent.launchUrl(context, Uri.parse(oath2Url))
    }

}


//http://com.jerryokafor.smsshare/auth/callback