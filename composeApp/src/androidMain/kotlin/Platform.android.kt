import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.browser.customtabs.CustomTabsIntent
import channel.ChannelConfig
import com.jerryokafor.smshare.R
import java.net.URLEncoder

actual class Platform actual constructor() {
    actual val platform: String
        get() = "Android ${Build.VERSION.SDK_INT}"
}


actual class AuthManager {
    actual var currentChannel: ChannelConfig? = null

    private var context: Context? = null

    actual fun authenticateUser(channelConfig: ChannelConfig) {
        currentChannel = channelConfig

        Log.d("AuthManager", "Authenticating user with $channelConfig | Context: $context")
        val challenge = URLEncoder.encode("123456NJSANLENLNE", "utf-8")
        val redirectUrl = URLEncoder.encode("http://com.jerryokafor.smshare/auth/callback", "utf-8")

        val oath2Url = channelConfig.createLoginUrl(
            redirectUrl = redirectUrl,
            challenge = challenge
        )

        Log.d("AuthManager", "Launching: $oath2Url")

        context?.let { cntx ->
            val intent: CustomTabsIntent = CustomTabsIntent.Builder()
                .apply {
                    setCloseButtonIcon(
                        BitmapFactory.decodeResource(cntx.resources, R.drawable.ic_arrow_back)
                    )
                }
                .build()

            intent.launchUrl(cntx, Uri.parse(oath2Url))
        }

    }

    fun bindContext(context: Context) {
        this.context = context
    }

}


//http://com.jerryokafor.smsshare/auth/callback