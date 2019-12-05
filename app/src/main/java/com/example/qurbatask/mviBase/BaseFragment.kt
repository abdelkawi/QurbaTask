package com.example.qurbatask.mviBase

import BaseViewModel
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@FlowPreview
@ExperimentalCoroutinesApi
abstract class BaseFragment<E : MviEvent, S : MviState>(
    @LayoutRes val contentLayoutId: Int
) : Fragment(contentLayoutId) {

    abstract val viewModel: BaseViewModel<E, S>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.stateLiveData.observe(requireActivity()) { state -> renderState(state) }
        viewModel.effects
            .onEach { effect -> effect?.let { onUiEffect(effect) } }
            .flowOn(Dispatchers.Main)
            .launchIn(lifecycleScope)
    }

    protected open fun onUiEffect(effect: MviEffect) {
        when (effect) {
            is MviEffect.ShowSnackBar -> Snackbar.make(
                requireView(),
                getString(effect.stringRes),
                effect.length
            ).show()
            is MviEffect.ShowToast -> Toast.makeText(
                requireContext(),
                getString(effect.stringRes),
                effect.length
            ).show()
            is MviEffect.Navigate -> findNavController().navigate(effect.direction)
            is MviEffect.PopStack -> findNavController().popBackStack(
                effect.destinationId,
                effect.inclusive
            )
        }
    }

    protected fun sendEvent(uiEvent: () -> E) {
        viewModel.sendEvent(uiEvent())
    }

    protected fun currentState() = viewModel.currentState
    abstract fun renderState(state: S)

}