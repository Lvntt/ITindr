package dev.lantt.itindr.start.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.github.terrakok.cicerone.Router
import dev.lantt.itindr.core.presentation.navigation.Screens.Login
import dev.lantt.itindr.core.presentation.navigation.Screens.Register
import dev.lantt.itindr.databinding.FragmentStartBinding
import org.koin.android.ext.android.inject

class StartFragment : Fragment() {

    private var _binding: FragmentStartBinding? = null
    private val binding get() = _binding!!

    private val router: Router by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStartBinding.inflate(inflater, container, false)

        binding.registerButton.setOnClickListener {
            router.navigateTo(Register())
        }
        binding.loginButton.setOnClickListener {
            router.navigateTo(Login())
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}