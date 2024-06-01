package dev.lantt.itindr.people.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import dev.lantt.itindr.core.presentation.mvi.MviFragment
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

    private var peopleListAdapter: PeopleListAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPeopleBinding.inflate(inflater, container, false)

        val layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
        binding.peopleRecyclerView.apply {
            this.layoutManager = layoutManager
            peopleListAdapter = PeopleListAdapter()
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
            PeopleMviEffect.ShowError -> TODO()
        }
    }

    override fun render(state: PeopleMviState) {
        peopleListAdapter?.submitList(state.people)
    }

}