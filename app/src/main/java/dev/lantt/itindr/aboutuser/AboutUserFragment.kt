package dev.lantt.itindr.aboutuser

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.github.terrakok.cicerone.Router
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import dev.lantt.itindr.core.presentation.utils.Utils.loadImageWithShimmer
import dev.lantt.itindr.databinding.FragmentAboutUserBinding
import dev.lantt.itindr.feed.presentation.state.UiProfile
import dev.lantt.itindr.feed.presentation.view.InterestsAdapter
import org.koin.android.ext.android.inject

private const val ARG_PROFILE = "profile"

class AboutUserFragment : Fragment() {
    private var _binding: FragmentAboutUserBinding? = null
    private val binding get() = _binding!!

    private var profile: UiProfile? = null
    private val router: Router by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            profile = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                it.getParcelable(ARG_PROFILE, UiProfile::class.java)
            } else {
                @Suppress("DEPRECATION")
                it.getParcelable(ARG_PROFILE)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAboutUserBinding.inflate(inflater, container, false)

        profile?.let {
            if (it.avatarUrl != null) {
                binding.backgroundImage.loadImageWithShimmer(it.avatarUrl)
            }
            binding.userNameText.text = it.name
            binding.aboutUserText.text = it.aboutMyself

            val layoutManager = FlexboxLayoutManager(context)
            layoutManager.apply {
                justifyContent = JustifyContent.CENTER
            }

            binding.interestsRecyclerView.apply {
                adapter = InterestsAdapter(it.topics)
                setLayoutManager(layoutManager)
            }

            binding.toolbar.setNavigationOnClickListener {
                router.exit()
            }
        }

        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance(uiProfile: UiProfile) =
            AboutUserFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_PROFILE, uiProfile)
                }
            }
    }
}