package com.maryang.hiltsample.util

import com.maryang.hiltsample.entity.GithubRepo

object Functional {
    fun sumImperative(arr: List<Int>): Int {
        var result = 0
        arr.forEach {
            result += it
        }
        return result
    }

    fun sumFunctional(arr: List<Int>): Int =
        arr.reduce { acc, i -> acc + arr[i] }

    fun starRepoNames(repos: List<GithubRepo>): List<String> =
        repos.filter { it.stargazersCount > 15 }
            .map { it.name }

    fun findRepo(repos: List<GithubRepo>, search: String): List<GithubRepo> =
        repos.filter {
            it.name.contains(search)
                || it.description?.contains(search) ?: false
                || it.owner.userName.contains(search)
        }
}
