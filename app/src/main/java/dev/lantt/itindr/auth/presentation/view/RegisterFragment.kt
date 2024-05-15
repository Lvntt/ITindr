package dev.lantt.itindr.auth.presentation.view

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.github.terrakok.cicerone.Router
import dev.lantt.itindr.auth.presentation.state.RegisterMviEffect
import dev.lantt.itindr.auth.presentation.state.RegisterMviIntent
import dev.lantt.itindr.auth.presentation.state.RegisterMviState
import dev.lantt.itindr.auth.presentation.store.RegisterViewModel
import dev.lantt.itindr.core.presentation.navigation.Screens.AboutYourself
import dev.lantt.itindr.databinding.FragmentRegisterBinding
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    private val viewModel: RegisterViewModel by viewModel()
    private val router: Router by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)

        viewModel.state
            .flowWithLifecycle(lifecycle)
            .onEach(::render)
            .launchIn(lifecycleScope)

        viewModel.effects
            .flowWithLifecycle(lifecycle)
            .onEach(::handleEffect)
            .launchIn(lifecycleScope)

        binding.emailTextInput.editText?.doOnTextChanged { text, _, _, _ ->
            viewModel.dispatch(RegisterMviIntent.EmailChanged(text.toString()))
        }
        binding.passwordTextInput.editText?.doOnTextChanged { text, _, _, _ ->
            viewModel.dispatch(RegisterMviIntent.PasswordChanged(text.toString()))
        }
        binding.repeatPasswordTextInput.editText?.doOnTextChanged { text, _, _, _ ->
            viewModel.dispatch(RegisterMviIntent.RepeatedPasswordChanged(text.toString()))
        }

        binding.registerButton.setOnClickListener {
            viewModel.dispatch(RegisterMviIntent.Register)
        }
        binding.backButton.setOnClickListener {
            viewModel.dispatch(RegisterMviIntent.Back)
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun render(state: RegisterMviState) {
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

    private fun handleEffect(effect: RegisterMviEffect) {
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