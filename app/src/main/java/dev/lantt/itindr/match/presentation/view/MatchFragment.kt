package dev.lantt.itindr.match.presentation.view

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import com.github.terrakok.cicerone.Router
import dev.lantt.itindr.R
import dev.lantt.itindr.core.constants.Constants.ARG_MATCHED_USER_ID
import dev.lantt.itindr.core.constants.Constants.CHAT_ADDED
import dev.lantt.itindr.core.constants.Constants.CHAT_ADDED_REQUEST_KEY
import dev.lantt.itindr.core.presentation.mvi.MviFragment
import dev.lantt.itindr.core.presentation.navigation.Screens.Chat
import dev.lantt.itindr.core.presentation.utils.ToastManager
import dev.lantt.itindr.databinding.FragmentMatchBinding
import dev.lantt.itindr.match.presentation.state.MatchMviEffect
import dev.lantt.itindr.match.presentation.state.MatchMviIntent
import dev.lantt.itindr.match.presentation.state.MatchMviState
import dev.lantt.itindr.match.presentation.store.MatchViewModel
import org.koin.android.ext.android.inject

class MatchFragment : MviFragment<MatchMviState, MatchMviIntent, MatchMviEffect>() {
    private var _binding: FragmentMatchBinding? = null
    private val binding get() = _binding!!

    private var matchedUserId: String? = null

    override val store: MatchViewModel by inject()
    private val router: Router by inject()
    private val toastManager: ToastManager by inject()

    private var loadingDialog: AlertDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            matchedUserId = it.getString(ARG_MATCHED_USER_ID)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMatchBinding.inflate(inflater, container, false)

        matchedUserId?.let {
            store.dispatch(MatchMviIntent.InitUserId(it))
        }

        binding.writeMessageButton.setOnClickListener {
            store.dispatch(MatchMviIntent.CreateChat)
        }

        return binding.root
    }

    override fun handleEffect(effect: MatchMviEffect) {
        when (effect) {
            is MatchMviEffect.CreateChat -> {
                router.exit()
                router.navigateTo(Chat(effect.uiChat))
                parentFragmentManager.setFragmentResult(CHAT_ADDED_REQUEST_KEY, bundleOf(CHAT_ADDED to true))
            }
            MatchMviEffect.ShowError -> toastManager.showToast(context, R.string.networkError)
        }
    }

    override fun render(state: MatchMviState) {
        if (state.isLoading) {
            showLoadingDialog()
        } else {
            dismissLoadingDialog()
        }
    }

    private fun showLoadingDialog() {
        val builder = AlertDialog.Builder(context)
        builder.setTitle(context?.getString(R.string.loading))
        builder.setView(layoutInflater.inflate(R.layout.loading_progress_bar, null))
        builder.setCancelable(false)

        loadingDialog = builder.create()
        loadingDialog?.show()
    }

    private fun dismissLoadingDialog() {
        loadingDialog?.dismiss()
    }

    companion object {
        @JvmStatic
        fun newInstance(userId: String) =
            MatchFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_MATCHED_USER_ID, userId)
                }
            }
    }
}