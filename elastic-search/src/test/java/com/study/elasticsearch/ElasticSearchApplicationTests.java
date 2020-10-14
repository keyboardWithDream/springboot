package com.study.elasticsearch;

import com.study.elasticsearch.domian.Article;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ElasticSearchApplicationTests {

	@Qualifier("elasticsearchRestClient")
	@Autowired
	RestClient restClient;


	@Autowired
	RestHighLevelClient restHighLevelClient;

	@Test
	void contextLoads() {
	}

	/**
	 * 保存文档
	 */
	@Test
	public void saveDocument(){
		Article article = new Article(1, "pxc", "Hello", "What is this?");
	}

}
