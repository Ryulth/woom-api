package com.ryulth.woom.domain.repository.elasticsearch

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.ryulth.woom.domain.model.Post
import mu.KLogging
import org.elasticsearch.action.search.SearchRequest
import org.elasticsearch.client.RequestOptions
import org.elasticsearch.client.RestHighLevelClient
import org.elasticsearch.index.query.QueryBuilders
import org.elasticsearch.search.builder.SearchSourceBuilder
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Repository

@Repository
class PostSearchRepositoryImpl(
    @Qualifier("elasticsearchClient")
    private val elasticsearchClient: RestHighLevelClient
) : PostSearchRepository {
    companion object : KLogging() {
        private val objectMapper = ObjectMapper()
                .registerModule(JavaTimeModule())
    }

    override fun findAll(): List<Post> {
        val searchSourceBuilder = SearchSourceBuilder()
                .query(QueryBuilders.matchAllQuery())
        logger.info { searchSourceBuilder }
        return searchPost(searchSourceBuilder)
    }

    override fun findAllByHasCategorySetContain(hasCategory: String): List<Post> {
        val searchSourceBuilder = SearchSourceBuilder()
                .query(QueryBuilders.matchQuery("hasCategorySet", hasCategory))
        return searchPost(searchSourceBuilder)
    }

    override fun findAllByWantCategorySetContain(hasCategory: String): List<Post> {
        val searchSourceBuilder = SearchSourceBuilder()
                .query(QueryBuilders.termQuery("wantCategorySet", hasCategory))
        return searchPost(searchSourceBuilder)
    }

    private fun searchPost(searchSourceBuilder: SearchSourceBuilder): List<Post> {
        val searchRequest = SearchRequest("woom-dynamodb")
        searchRequest.types("post")
        searchRequest.source(searchSourceBuilder)
        return elasticsearchClient.search(searchRequest, RequestOptions.DEFAULT).hits.map {
            objectMapper.readValue(it.sourceAsString, Post::class.java)
        }.toList()
//        Flux.create<Post> { sink: FluxSink<Post> ->
//            elasticsearchClient.searchAsync(searchRequest, RequestOptions.DEFAULT, object : ActionListener<SearchResponse> {
//                override fun onResponse(searchResponse: SearchResponse) {
//                    for (hit in searchResponse.hits) {
//                        try {
//                            val post: Post = objectMapper.readValue(hit.sourceAsString, Post::class.java)
//                            sink.next(post)
//                        } catch (e: IOException) {
//                            e.printStackTrace()
//                        }
//                    }
//                    sink.complete()
//                }
//
//                override fun onFailure(e: Exception?) {}
//            })
//        }.subscribe()
    }
}
