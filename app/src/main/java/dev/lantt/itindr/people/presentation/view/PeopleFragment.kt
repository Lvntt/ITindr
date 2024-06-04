package dev.lantt.itindr.people.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.github.terrakok.cicerone.Router
import dev.lantt.itindr.R
import dev.lantt.itindr.core.constants.Constants.ARG_PROFILE_ID
import dev.lantt.itindr.core.constants.Constants.PERSON_REQUEST_KEY
import dev.lantt.itindr.core.presentation.mvi.MviFragment
import dev.lantt.itindr.core.presentation.navigation.Screens.OtherProfile
import dev.lantt.itindr.core.presentation.utils.ToastManager
import dev.lantt.itindr.databinding.FragmentPeopleBinding
import dev.lantt.itindr.people.presentation.state.PeopleMviEffect
import dev.lantt.itindr.people.presentation.state.PeopleMviIntent
import dev.lantt.itindr.people.presentation.state.PeopleMviState
import dev.lantt.itindr.people.presentation.store.PeopleViewModel
import org.koin.android.ext.android.inject

class PeopleFragment : MviFragment<PeopleMviState, PeopleMviIntent, PeopleMviEffect>() {

    private var _binding: FragmentPeopleBinding? = null
    private val binding get() = _binding!!

    override val store: PeopleViewModel by inject()
    private val router: Router by inject()
    private val toastManager: ToastManager by inject()

    private var peopleListAdapter: PeopleListAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPeopleBinding.inflate(inflater, container, false)

        parentFragment?.parentFragmentManager?.setFragmentResultListener(PERSON_REQUEST_KEY, viewLifecycleOwner) { _, bundle ->
            val profileIdToRemove = bundle.getString(ARG_PROFILE_ID)
            profileIdToRemove?.let {
                store.dispatch(PeopleMviIntent.RemovePersonFromList(it))
            }
        }

        val layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
        binding.peopleRecyclerView.apply {
            this.layoutManager = layoutManager
            peopleListAdapter = PeopleListAdapter(
                onPersonClick = {
                    router.navigateTo(OtherProfile(it))
                }
            )
            peopleListAdapter?.submitList(store.state.value.people)

            adapter = peopleListAdapter

            addItemDecoration(SecondColumnSpacingDecoration(context))
            addOnScrollListener(
                object : StaggeredGridPaginationScrollListener() {
                    override fun loadMoreItems() {
                        store.dispatch(PeopleMviIntent.LoadMorePeople)
                    }

                    override fun isLastPage(): Boolean = store.state.value.isEnded

                    override fun isLoading(): Boolean = store.state.value.isLoading
                }
            )
        }

        return binding.root
    }

    override fun handleEffect(effect: PeopleMviEffect) {
        when (effect) {
            PeopleMviEffect.ShowError -> toastManager.showToast(context, R.string.networkError)
        }
    }

    override fun render(state: PeopleMviState) {
        peopleListAdapter?.submitList(state.people)
    }

}