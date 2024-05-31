package dev.lantt.itindr.match

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import dev.lantt.itindr.databinding.FragmentMatchBinding

private const val ARG_MATCHED_USER_ID = "profile"

class MatchFragment : Fragment() {
    private var _binding: FragmentMatchBinding? = null
    private val binding get() = _binding!!

    private var matchedUserId: String? = null

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

        return binding.root
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