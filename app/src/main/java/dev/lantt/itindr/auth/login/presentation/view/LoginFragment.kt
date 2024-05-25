package dev.lantt.itindr.auth.login.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import com.github.terrakok.cicerone.Router
import dev.lantt.itindr.R
import dev.lantt.itindr.auth.login.presentation.state.LoginMviEffect
import dev.lantt.itindr.auth.login.presentation.state.LoginMviIntent
import dev.lantt.itindr.auth.login.presentation.state.LoginMviState
import dev.lantt.itindr.auth.login.presentation.store.LoginViewModel
import dev.lantt.itindr.core.presentation.mvi.MviFragment
import dev.lantt.itindr.core.presentation.navigation.Screens.Profile
import dev.lantt.itindr.core.presentation.navigation.Screens.RootBottomNavigation
import dev.lantt.itindr.core.presentation.utils.ToastManager
import dev.lantt.itindr.databinding.FragmentLoginBinding
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : MviFragment<LoginMviState, LoginMviIntent, LoginMviEffect>() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override val store: LoginViewModel by viewModel()
    private val router: Router by inject()
    private val toastManager: ToastManager by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)

        binding.emailTextInput.editText?.doOnTextChanged { text, _, _, _ ->
            store.dispatch(LoginMviIntent.EmailChanged(text.toString()))
        }
        binding.passwordTextInput.editText?.doOnTextChanged { text, _, _, _ ->
            store.dispatch(LoginMviIntent.PasswordChanged(text.toString()))
        }

        binding.loginButton.setOnClickListener {
            store.dispatch(LoginMviIntent.LoginRequested)
        }
        binding.backButton.setOnClickListener {
            store.dispatch(LoginMviIntent.Back)
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun render(state: LoginMviState) {
        binding.loginButton.isEnabled = state.isLoginAllowed

        if (state.loginState == LoginMviState.LoginState.LOADING) {
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

    }

    override fun handleEffect(effect: LoginMviEffect) {
        when (effect) {
            LoginMviEffect.GoToSetupScreen -> router.newRootScreen(Profile())
            LoginMviEffect.GoToFeedScreen -> router.newRootScreen(RootBottomNavigation())
            LoginMviEffect.GoToPreviousScreen -> router.exit()
            LoginMviEffect.ShowError -> toastManager.showToast(context, R.string.loginError)
        }
    }

}