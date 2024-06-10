package dev.lantt.itindr.chats.chatspreview.presentation.view

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.terrakok.cicerone.Router
import dev.lantt.itindr.R
import dev.lantt.itindr.chats.chat.presentation.state.UiChat
import dev.lantt.itindr.chats.chatspreview.presentation.state.ChatsPreviewMviEffect
import dev.lantt.itindr.chats.chatspreview.presentation.state.ChatsPreviewMviIntent
import dev.lantt.itindr.chats.chatspreview.presentation.state.ChatsPreviewMviState
import dev.lantt.itindr.chats.chatspreview.presentation.store.ChatsPreviewViewModel
import dev.lantt.itindr.core.constants.Constants.CHAT_ADDED_REQUEST_KEY
import dev.lantt.itindr.core.presentation.mvi.MviFragment
import dev.lantt.itindr.core.presentation.navigation.Screens.Chat
import dev.lantt.itindr.core.presentation.utils.ToastManager
import dev.lantt.itindr.databinding.FragmentChatsPreviewBinding
import org.koin.android.ext.android.inject

class ChatsPreviewFragment : MviFragment<ChatsPreviewMviState, ChatsPreviewMviIntent, ChatsPreviewMviEffect>() {

    private var _binding: FragmentChatsPreviewBinding? = null
    private val binding get() = _binding!!

    override val store: ChatsPreviewViewModel by inject()
    private val router: Router by inject()
    private val toastManager: ToastManager by inject()

    private var chatsPreviewAdapter: ChatsPreviewAdapter? = null
    private var loadingDialog: AlertDialog? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChatsPreviewBinding.inflate(inflater, container, false)

        parentFragment?.parentFragmentManager?.setFragmentResultListener(CHAT_ADDED_REQUEST_KEY, viewLifecycleOwner) { _, _ ->
            store.dispatch(ChatsPreviewMviIntent.LoadChatsPreview)
        }

        chatsPreviewAdapter = ChatsPreviewAdapter(
            onPreviewClick = {
                // TODO map data so adapter uses UiChatPreview with UiChat

                val uiChat = UiChat(
                    id = it.chat.id,
                    title = it.chat.title,
                    avatar = it.chat.avatar
                )
                router.navigateTo(Chat(uiChat))
            }
        )

        binding.chatsPreviewRecyclerView.apply {
            adapter = chatsPreviewAdapter
        }

        return binding.root
    }

    override fun handleEffect(effect: ChatsPreviewMviEffect) {
        when (effect) {
            ChatsPreviewMviEffect.ShowError -> toastManager.showToast(context, R.string.networkError)
        }
    }

    override fun render(state: ChatsPreviewMviState) {
        chatsPreviewAdapter?.submitList(store.state.value.previews)

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

}