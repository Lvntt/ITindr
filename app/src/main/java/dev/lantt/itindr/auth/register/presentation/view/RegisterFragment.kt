package dev.lantt.itindr.auth.register.presentation.view

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import com.github.terrakok.cicerone.Router
import dev.lantt.itindr.auth.register.presentation.state.RegisterMviEffect
import dev.lantt.itindr.auth.register.presentation.state.RegisterMviIntent
import dev.lantt.itindr.auth.register.presentation.state.RegisterMviState
import dev.lantt.itindr.auth.register.presentation.store.RegisterViewModel
import dev.lantt.itindr.core.presentation.mvi.MviFragment
import dev.lantt.itindr.core.presentation.navigation.Screens.AboutYourself
import dev.lantt.itindr.databinding.FragmentRegisterBinding
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegisterFragment : MviFragment<RegisterMviState, RegisterMviIntent, RegisterMviEffect>() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    private val router: Router by inject()
    override val store: RegisterViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)

        binding.emailTextInput.editText?.doOnTextChanged { text, _, _, _ ->
            store.dispatch(RegisterMviIntent.EmailChanged(text.toString()))
        }
        binding.passwordTextInput.editText?.doOnTextChanged { text, _, _, _ ->
            store.dispatch(RegisterMviIntent.PasswordChanged(text.toString()))
        }
        binding.repeatPasswordTextInput.editText?.doOnTextChanged { text, _, _, _ ->
            store.dispatch(RegisterMviIntent.RepeatedPasswordChanged(text.toString()))
        }

        binding.registerButton.setOnClickListener {
            store.dispatch(RegisterMviIntent.RegisterRequested)
        }
        binding.backButton.setOnClickListener {
            store.dispatch(RegisterMviIntent.Back)
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun render(state: RegisterMviState) {
        binding.registerButton.isEnabled = state.isRegistrationAllowed

        if (state.registrationState == RegisterMviState.RegistrationState.LOADING) {
            binding.loadingProgressBar.visibility = View.VISIBLE
        } else {
            binding.loadingProgressBar.visibility = View.GONE
        }

        if (state.emailErrorResId != null) {
            binding.emailError.text = context?.getString(state.emailErrorResId)
            binding.emailError.visibility = View.VISIBLE
        } else {
            binding.emailError.visibility = View.GONE
        }

        if (state.passwordErrorResId != null) {
            binding.passwordError.text = context?.getString(state.passwordErrorResId)
            binding.passwordError.visibility = View.VISIBLE
        } else {
            binding.passwordError.visibility = View.GONE
        }

        if (state.repeatedPasswordErrorResId != null) {
            binding.repeatedPasswordError.text = context?.getString(state.repeatedPasswordErrorResId)
            binding.repeatedPasswordError.visibility = View.VISIBLE
        } else {
            binding.repeatedPasswordError.visibility = View.GONE
        }
    }

    override fun handleEffect(effect: RegisterMviEffect) {
        when (effect) {
            RegisterMviEffect.GoToAboutYourselfScreen -> router.newRootScreen(AboutYourself())
            RegisterMviEffect.GoToPreviousScreen -> router.exit()
            RegisterMviEffect.ShowError -> showErrorDialog()
        }
    }

    private fun showErrorDialog() {
        // TODO
        val builder = AlertDialog.Builder(context)
        builder.setTitle("title")
        builder.setMessage("message")
        builder.setPositiveButton("OK") { dialog, _ ->
            dialog.dismiss()
        }

        val dialog = builder.create()
        dialog.show()
    }

}