package xyz.hexode.appstatcollector.activity

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import kotlinx.android.synthetic.main.activity_app_details.*
import xyz.hexode.appstatcollector.R
import xyz.hexode.appstatcollector.util.picasso.updateLaunchIcon
import xyz.hexode.appstatcollector.util.toHex
import java.io.ByteArrayInputStream
import java.security.cert.CertificateException
import java.security.cert.CertificateFactory
import java.security.cert.X509Certificate

class AppDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_app_details)
//        supportPostponeEnterTransition()

        intent?.extras?.let {
            app_details_launch_icon.transitionName =
                    it.getString(AppListActivity.INTENT_EXTRA_APP_ICON_TRANSITION_NAME)
            val uri = Uri.parse(it.getString(AppListActivity.INTENT_EXTRA_APP_ICON_URI))
            app_details_launch_icon.updateLaunchIcon(uri)
            val appPackageName = it.getString(AppListActivity.INTENT_EXTRA_PACKAGE_NAME)
            app_details_package_name.text = appPackageName

            app_details_launch_icon.updateLaunchIcon(uri)

            @Suppress("DEPRECATION")
            @SuppressLint("PackageManagerGetSignatures")
            val signatures = packageManager.getPackageInfo(appPackageName, PackageManager.GET_SIGNATURES).signatures
            val signature = signatures.first()
            val certificateStream = ByteArrayInputStream(signature.toByteArray())
            try {
                val certificateFactory = CertificateFactory.getInstance("X509")
                val x509cert = certificateFactory.generateCertificate(certificateStream) as X509Certificate

                serialNumberTextView.text = "Serial number: ${x509cert.serialNumber}"
                publicKeyTextView.text = "Public key\n${x509cert.publicKey}"
                issuerTextView.text = "Issuer: ${x509cert.issuerDN}"
                subjectTextView.text = "Subject: ${x509cert.subjectDN}"
                validityTextView.text = "Validity\nNot Before: ${x509cert.notBefore}\nNot After: ${x509cert.notAfter}"
                signatureAlgorithmTextView.text = "Signature Algorithm: ${x509cert.sigAlgName}"
                rawSignatureTextView.text = "Raw Signature\n${x509cert.signature.toHex()}"

            } catch (e: CertificateException) {
                Log.e(TAG, "", e)
            }
        }
    }

    companion object {
        val TAG: String = AppDetailsActivity::class.java.simpleName
    }
}