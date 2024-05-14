package dev.lantt.itindr.aboutyourself.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import dev.lantt.itindr.databinding.FragmentAboutYourselfBinding

class AboutYourselfFragment : Fragment() {

    private var _binding: FragmentAboutYourselfBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAboutYourselfBinding.inflate(inflater, container, false)

        val interestsRecyclerView = binding.interestsRecyclerView
        val interestsAdapter = InterestsAdapter(interests = TestInterests.values)

//        layoutManager.flexDirection = FlexDirection.ROW
//        layoutManager.justifyContent = JustifyContent.FLEX_START

        interestsRecyclerView.adapter = interestsAdapter

        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            AboutYourselfFragment()
    }
}