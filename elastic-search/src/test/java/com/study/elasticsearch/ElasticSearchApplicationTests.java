package com.study.elasticsearch;

import com.alibaba.fastjson.JSON;
import com.study.elasticsearch.domian.UserInfo;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
class ElasticSearchApplicationTests {

	@Autowired
	RestHighLevelClient restHighLevelClient;

	/**
	 * 创建索引
	 */
	@Test
	public void createIndex() throws IOException {
		//创建索引请求
		CreateIndexRequest indexRequest = new CreateIndexRequest("blog");
		//执行请求 -> 获得响应
		CreateIndexResponse createIndexResponse = restHighLevelClient.indices().create(indexRequest, RequestOptions.DEFAULT);
		System.out.println(createIndexResponse.isAcknowledged());
	}

	/**
	 * 获取索引
	 * 判断索引是否存在
	 */
	@Test
	public void existIndex() throws IOException {
		//创建获取索引请求
		GetIndexRequest getIndexRequest = new GetIndexRequest("blog");
		//执行请求 -> 返回boolean值
		boolean exists = restHighLevelClient.indices().exists(getIndexRequest, RequestOptions.DEFAULT);
		System.out.println(exists);
	}

	/**
	 * 删除索引
	 */
	@Test
	public void deleteIndex() throws IOException {
		//创建删除索引请求
		DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest("blog");
		//执行请求
		AcknowledgedResponse response = restHighLevelClient.indices().delete(deleteIndexRequest, RequestOptions.DEFAULT);
		System.out.println(response.isAcknowledged());
	}

	/**
	 * 添加文档
	 */
	@Test
	public void createDocument() throws IOException {
		//创建对象
		UserInfo userInfo = new UserInfo("harlan", 23);
		//创建请求
		IndexRequest indexRequest = new IndexRequest("blog");
		//设置规则
		indexRequest.id("1");
		indexRequest.timeout(TimeValue.timeValueSeconds(1));
		indexRequest.timeout("1s");
		//将数据放入请求中 json
		indexRequest.source(JSON.toJSONString(userInfo), XContentType.JSON);
		//客户端发送请求, 获取响应结果
		IndexResponse response = restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
		System.out.println(response.status());
	}

	/**
	 * 获取文档
	 * 判断文档是否存在
	 */
	@Test
	public void isExists() throws IOException {
		GetRequest getRequest = new GetRequest("blog", "1");
		boolean exists = restHighLevelClient.exists(getRequest, RequestOptions.DEFAULT);
		System.out.println(exists);
	}

	/**
	 *获取文档
	 */
	@Test
	public void getDocument() throws IOException {
		GetRequest getRequest = new GetRequest("blog", "1");
		GetResponse response = restHighLevelClient.get(getRequest, RequestOptions.DEFAULT);
		System.out.println(response.getSourceAsString());
	}

	/**
	 * 更新文档
	 */
	@Test
	public void updateDocument() throws IOException {
		UpdateRequest updateRequest = new UpdateRequest("blog", "1");
		updateRequest.timeout("1s");
		UserInfo userInfo = new UserInfo("root", 20);
		updateRequest.doc(JSON.toJSONString(userInfo), XContentType.JSON);
		UpdateResponse response = restHighLevelClient.update(updateRequest, RequestOptions.DEFAULT);
		System.out.println(response.status());
	}

	/**
	 * 删除文档
	 */
	@Test
	public void deleteDocument() throws IOException {
		DeleteRequest deleteRequest = new DeleteRequest("blog", "1");
		DeleteResponse response = restHighLevelClient.delete(deleteRequest, RequestOptions.DEFAULT);
		System.out.println(response.status());
	}
}
