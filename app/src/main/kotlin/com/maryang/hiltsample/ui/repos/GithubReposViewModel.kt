package com.maryang.hiltsample.ui.repos

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import com.maryang.hiltsample.base.BaseViewModel
import com.maryang.hiltsample.data.repository.GithubRepository
import com.maryang.hiltsample.entity.GithubRepo
import com.maryang.hiltsample.util.provider.SchedulerProviderInterface
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit

class GithubReposViewModel @ViewModelInject constructor(
    private val repository: GithubRepository,
    val schedulerProvider: SchedulerProviderInterface,
    @Assisted private val savedStateHandle: SavedStateHandle
) : BaseViewModel() {

    private val searchSubject = BehaviorSubject.createDefault("" to false)
    val loadingState = PublishSubject.create<Boolean>()
    val reposState = PublishSubject.create<List<GithubRepo>>()

    fun onCreate() {
        compositeDisposable += searchSubject
            .debounce(400, TimeUnit.MILLISECONDS, schedulerProvider.computation())
            .distinctUntilChanged()
            .observeOn(schedulerProvider.main())
            .doOnNext { loadingState.onNext(it.second) }
            .observeOn(schedulerProvider.io())
            .switchMapSingle {
                if (it.first.isEmpty()) Single.just(emptyList())
                else repository.searchGithubRepos(it.first)
            }
            .switchMapSingle {
                Completable.merge(
                    it.map { repo ->
                        repository.checkStar(repo.owner.userName, repo.name)
                            .doOnComplete { repo.star = true }
                            .onErrorComplete()
                    }
                ).toSingleDefault(it)
            }
            .observeOn(schedulerProvider.main())
            .doOnNext { loadingState.onNext(false) }
            .subscribe(reposState::onNext)
    }

    fun searchGithubRepos(search: String) {
        searchSubject.onNext(search to true)
    }

    fun searchGithubRepos() {
        searchSubject.onNext(searchSubject.value!!.first to false)
    }
}
