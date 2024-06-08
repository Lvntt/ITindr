package dev.lantt.itindr.chats.chat.presentation.view

import android.app.AlertDialog
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.github.terrakok.cicerone.Router
import dev.lantt.itindr.R
import dev.lantt.itindr.chats.chat.presentation.state.ChatMviEffect
import dev.lantt.itindr.chats.chat.presentation.state.ChatMviIntent
import dev.lantt.itindr.chats.chat.presentation.state.ChatMviState
import dev.lantt.itindr.chats.chat.presentation.state.UiChat
import dev.lantt.itindr.chats.chat.presentation.store.ChatViewModel
import dev.lantt.itindr.core.presentation.mvi.MviFragment
import dev.lantt.itindr.core.presentation.utils.ToastManager
import dev.lantt.itindr.core.presentation.utils.Utils.loadImageWithShimmer
import dev.lantt.itindr.databinding.FragmentChatBinding
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

private const val ARG_UI_CHAT = "arg_ui_chat"

class ChatFragment : MviFragment<ChatMviState, ChatMviIntent, ChatMviEffect>() {

    private var uiChat: UiChat? = null

    private var _binding: FragmentChatBinding? = null
    private val binding get() = _binding!!

    override val store: ChatViewModel by inject { parametersOf(uiChat?.id) }
    private val toastManager: ToastManager by inject()
    private val router: Router by inject()

    private var chatAdapter: ChatAdapter? = null
    private var loadingDialog: AlertDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        arguments?.let {
            uiChat = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                it.getParcelable(ARG_UI_CHAT, UiChat::class.java)
            } else {
                @Suppress("DEPRECATION")
                it.getParcelable(ARG_UI_CHAT)
            }
        }
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChatBinding.inflate(inflater, container, false)

        chatAdapter = ChatAdapter()

        binding.messagesRecyclerView.apply {
            adapter = chatAdapter
            // TODO reverse recyclerview
            // TODO 8dp spacing between same user messages
        }
        chatAdapter?.submitList(store.state.value.messages)

        binding.toolbarName.text = uiChat?.title
        uiChat?.avatar?.let {
            binding.toolbarAvatar.clipToOutline = true
            binding.toolbarAvatar.scaleType = ImageView.ScaleType.CENTER_CROP
            binding.toolbarAvatar.loadImageWithShimmer(it)
        }

        binding.toolbar.setNavigationOnClickListener {
            router.exit()
        }

        return binding.root
    }

    override fun handleEffect(effect: ChatMviEffect) {
        when (effect) {
            ChatMviEffect.ShowError -> toastManager.showToast(context, R.string.networkError)
        }
    }

    override fun render(state: ChatMviState) {
        chatAdapter?.submitList(state.messages)

        if (state.isChatLoading) {
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
        fun newInstance(uiChat: UiChat) =
            ChatFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_UI_CHAT, uiChat)
                }
            }
    }
}