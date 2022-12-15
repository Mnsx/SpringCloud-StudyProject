package cn.itcast.hotel;

import cn.itcast.hotel.pojo.Hotel;
import cn.itcast.hotel.pojo.HotelDoc;
import cn.itcast.hotel.service.IHotelService;
import com.alibaba.fastjson.JSON;
import org.apache.http.HttpHost;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.List;

/**
 * @Author Mnsx_x xx1527030652@gmail.com
 */
@SpringBootTest
public class HotelSearchTest {
    private RestHighLevelClient client;

    @Autowired
    private IHotelService hotelService;

    @Test
    void testHighlight() throws IOException {
        SearchRequest request = new SearchRequest("hotel");

        request.source ().query(QueryBuilders.matchQuery("name", "如家"));

        request.source().highlighter(new HighlightBuilder().field("city").requireFieldMatch(false));

        SearchResponse response = client.search(request, RequestOptions.DEFAULT);

        handleResponse(response);
    }

    /**
     * 分页查询并排序
     * SearchRequest(索引名称)
     * 排序 请求.source().sort(字段名称, SortOrder.ASC)
     * 分页 请求.source().from(0).size(5)
     * search(请求, RequestOptions.DEFAULT)
     */
    @Test
    void testPageAndSort() throws IOException {
        SearchRequest request = new SearchRequest("hotel");

        request.source().query(QueryBuilders.matchAllQuery());

        request.source().sort("price", SortOrder.ASC);

        request.source().from(0).size(5);

        SearchResponse response = client.search(request, RequestOptions.DEFAULT);

        handleResponse(response);
    }

    /**
     * SearchRequest(索引名称)
     * QueryBuilders.boolQuery()
     * .must(QueryBuilders.termQuery(字段名称, 数据)) 模糊查询
     * .filter(QueryBuilders.rangeQuery(字段名称) 范围查询
     * 请求.source().query(boolQuery)
     * search(请求, RequestOptions.DEFAULT)
     */
    @Test
    void testBoolMatch() throws IOException {
        SearchRequest request = new SearchRequest("hotel");

        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        boolQuery.must(QueryBuilders.termQuery("city", "杭州"));
        boolQuery.filter(QueryBuilders.rangeQuery("price").lte(1000));

        request.source().query(boolQuery);

        SearchResponse response = client.search(request, RequestOptions.DEFAULT);

        handleResponse(response);
    }

    /**
     * 批量匹配数据
     * SearchRequest(索引名称).source().query(QueryBuilders.multiMatchQuery(数据, 字段名称...)
     * search(请求, RequestOptions.DEFAULT)
     */
    @Test
    void testMultiMatch() throws IOException {
        SearchRequest request = new SearchRequest("hotel");

        request.source()
                .query(QueryBuilders.multiMatchQuery("如家", "name", "business"));

        SearchResponse response = client.search(request, RequestOptions.DEFAULT);

        handleResponse(response);
    }

    /**
     * 响应.getHits() 获取所有响应数据
     * SearchHits hits.getTotalHits() 获取响应数据得总量
     * 遍历hits可以获得所有数据
     * @param response 响应
     */
    private void handleResponse(SearchResponse response) {
        SearchHits hits = response.getHits();
        System.out.println(hits.getTotalHits());

        SearchHit[] hits1 = hits.getHits();
        for (SearchHit documentFields : hits1) {
            String sourceAsString = documentFields.getSourceAsString();
            HotelDoc hotel = JSON.parseObject(sourceAsString, HotelDoc.class);
            System.out.println(hotel);
        }
    }

    /**
     * 匹配查询
     * SearchRequest(索引名称).source().query(QueryBuilders.matchQuery(字段名称, 数据)
     * search(请求, RequestOptions.DEFAULT)
     */
    @Test
    void testMatch() throws IOException {
        SearchRequest request = new SearchRequest("hotel");

        request.source()
                .query(QueryBuilders.matchQuery("name", "如家"));

        SearchResponse response = client.search(request, RequestOptions.DEFAULT);

        handleResponse(response);
    }

    /**
     * 获取所有数据
     * SearchRequest(索引名称).source().query(QueryBuilders.matchAllQuery())
     * search(请求, RequestOptions.DEFAULT)
     */
    @Test
    void testMatchAll() throws IOException {
        // 准备Request
        SearchRequest request = new SearchRequest("hotel");

        // 准备DSL
        request.source().query(QueryBuilders.matchAllQuery());

        // 发送请求
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);

        handleResponse(response);
    }

    @BeforeEach
    void setUp() {
        this.client = new RestHighLevelClient(RestClient.builder(
                HttpHost.create("http://vm.mnsx.top:9200")
        ));
    }

    @AfterEach
    void shutdown() throws IOException {
        this.client.close();
    }
}
