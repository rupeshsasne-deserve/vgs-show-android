package com.verygoodsecurity.vgsshow

import android.content.Context
import androidx.annotation.WorkerThread
import com.verygoodsecurity.vgsshow.core.Environment
import com.verygoodsecurity.vgsshow.core.network.HttpRequestManager
import com.verygoodsecurity.vgsshow.core.network.IHttpRequestManager
import com.verygoodsecurity.vgsshow.core.network.client.HttpMethod
import com.verygoodsecurity.vgsshow.core.network.model.VGSRequest
import com.verygoodsecurity.vgsshow.core.network.url.UrlHelper
import com.verygoodsecurity.vgsshow.util.connection.ConnectionHelper
import com.verygoodsecurity.vgsshow.util.extension.logDebug

class VGSShow {

    private val proxyNetworkManager: IHttpRequestManager

    constructor(
        context: Context,
        vaultId: String,
        environment: Environment
    ) : this(context, vaultId, environment.rawValue)

    constructor(context: Context, vaultId: String, environment: String) {
        this.proxyNetworkManager = HttpRequestManager(
            UrlHelper.buildProxyUrl(vaultId, environment),
            ConnectionHelper(context)
        )
    }

    @WorkerThread
    fun request(fieldName: String, token: String) {
        logDebug("request{fieldName=$fieldName, token=$token}")
        val response = proxyNetworkManager.execute(
            VGSRequest.Builder("post", HttpMethod.POST)
                .body(mapOf(fieldName to token))
                .build()
        )
        logDebug(response.toString())
    }
}