package com.example.newsapp.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.models.NewsResponse
import com.example.newsapp.repository.NewsRepository
import com.example.newsapp.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class NewsViewModel(
    val newsRepository: NewsRepository
) : ViewModel() {

    val breakingNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    val breakingNewsPage = 1

    init {
        getBreakingNews("br")
    }

    //inicia requisicao
    fun getBreakingNews(countryCode: String)  = viewModelScope.launch {

        breakingNews.postValue(Resource.Loading())
        val response = newsRepository.getBreakingNews(countryCode, breakingNewsPage)

        //após receber uma resposta breakingNews ira receber caso seja Sucesso ou Erro
        breakingNews.postValue(handleBreakingNewsResponse(response))
    }

    //retorna sucesso ou erro da requisição
    private fun handleBreakingNewsResponse(response: Response<NewsResponse>) : Resource<NewsResponse> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                //caso não seja nulo envio o resultado para o Resource
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }
}