package com.example.qurbatask.mviBase

import android.widget.Toast
import androidx.annotation.IdRes
import androidx.annotation.StringRes
import androidx.navigation.NavDirections
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.Flow

abstract class MviEvent {
    override fun toString(): String {
        return this.javaClass.simpleName
    }
}
abstract class MviAction<out R: MviResult> {
    abstract suspend fun invoke(): Flow<R>

    override fun toString(): String {
        return this.javaClass.simpleName
    }
}
abstract class MviResult {
    override fun toString(): String {
        return this.javaClass.simpleName
    }
}
abstract class MviState {
    override fun toString(): String {
        return this.javaClass.simpleName
    }
}

sealed class MviEffect {
    data class ShowSnackBar(@StringRes val stringRes: Int, val length: Int = Snackbar.LENGTH_SHORT) : MviEffect()
    data class ShowToast(@StringRes val stringRes: Int, val length: Int = Toast.LENGTH_SHORT) : MviEffect()
    data class Navigate(val direction: NavDirections) : MviEffect()
    data class PopStack(@IdRes val destinationId: Int, val inclusive: Boolean) : MviEffect()
    abstract class FeatureEffect: MviEffect()
}