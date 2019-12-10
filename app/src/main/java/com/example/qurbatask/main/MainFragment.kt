package com.example.qurbatask.main

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.view.View.GONE
import androidx.core.content.ContextCompat
import com.example.qurbatask.R
import com.example.qurbatask.mviBase.BaseFragment
import kotlinx.android.synthetic.main.fragment_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import android.provider.Settings.Secure
import android.util.Log
import com.example.qurbatask.main.JWT.GetJwtEvent
import com.example.qurbatask.network.genericEntities.ApiRequest
import com.example.qurbatask.network.request.JWTRequest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview


@UseExperimental(ExperimentalCoroutinesApi::class)
@ExperimentalCoroutinesApi
@FlowPreview
class MainFragment : BaseFragment<GetJwtEvent, MainState>(R.layout.fragment_main) {
    override val viewModel: MainViewModel by viewModel()

    override fun renderState(state: MainState) {
        if (state.isLoading) progressBar.visibility = View.VISIBLE else progressBar.visibility =
            GONE

        tv_test.text = state.message
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (checkForPermission()) {
            val android_id = Secure.getString(
                requireActivity().contentResolver,
                Secure.ANDROID_ID
            )
            Log.d("xxx", android_id)
            sendEvent { GetJwtEvent.Started(
                ApiRequest(
                    JWTRequest(deviceId = android_id)
                )
            ) }
        } else {
            requestPermissions(
                arrayOf(Manifest.permission.READ_PHONE_STATE),
                100
            )
        }

    }

    private fun checkForPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            requireActivity(),
            Manifest.permission.READ_PHONE_STATE
        ) == PackageManager.PERMISSION_GRANTED
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            100 -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    val android_id = Secure.getString(
                        requireActivity().contentResolver,
                        Secure.ANDROID_ID
                    )
                    Log.d("xxx", android_id)
                    sendEvent { GetJwtEvent.Started(
                        ApiRequest(
                            JWTRequest(android_id)
                        )
                    ) }
                }
                return
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

    }


}