package cn.itcast.hotel;

import cn.itcast.hotel.pojo.Hotel;
import cn.itcast.hotel.pojo.HotelDoc;
import cn.itcast.hotel.service.IHotelService;
import com.alibaba.fastjson.JSON;
import com.sun.org.apache.regexp.internal.RE;
import org.apache.http.HttpHost;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @Author Mnsx_x xx1527030652@gmail.com
 */
@SpringBootTest
public class HotelIndexTest {
    private RestHighLevelClient client;
    private static final String MAPPING_TEMPLATE = "{\n" +
            "  \"mappings\": {\n" +
            "    \"properties\": {\n" +
            "      \"id\": {\n" +
            "        \"type\": \"keyword\"\n" +
            "      },\n" +
            "      \"name\": {\n" +
            "        \"type\": \"text\",\n" +
            "        \"analyzer\": \"ik_max_word\",\n" +
            "        \"copy_to\": \"all\"\n" +
            "      },\n" +
            "      \"address\": {\n" +
            "        \"type\": \"keyword\",\n" +
            "        \"index\": false\n" +
            "      },\n" +
            "      \"price\": {\n" +
            "        \"type\": \"integer\"\n" +
            "      },\n" +
            "      \"score\": {\n" +
            "        \"type\": \"integer\"\n" +
            "      },\n" +
            "      \"brand\": {\n" +
            "        \"type\": \"keyword\",\n" +
            "        \"copy_to\": \"all\"\n" +
            "      },\n" +
            "      \"city\": {\n" +
            "        \"type\": \"keyword\"\n" +
            "      },\n" +
            "      \"starName\": {\n" +
            "        \"type\": \"keyword\"\n" +
            "      },\n" +
            "      \"business\": {\n" +
            "        \"type\": \"keyword\",\n" +
            "        \"copy_to\": \"all\"\n" +
            "      },\n" +
            "      \"location\": {\n" +
            "        \"type\": \"geo_point\"\n" +
            "      },\n" +
            "      \"pic\": {\n" +
            "        \"type\": \"keyword\",\n" +
            "        \"index\": false\n" +
            "      },\n" +
            "      \"all\": {\n" +
            "        \"type\": \"text\",\n" +
            "        \"analyzer\": \"ik_max_word\"\n" +
            "      }\n" +
            "    }\n" +
            "  }\n" +
            "}";
    @Autowired
    private IHotelService hotelService;

    /**
     * 批量添加数据
     * BulkRequest()
     * 请求.add(new IndexRequest(索引名称)).id(唯一标识编号).source(JSON字符串, XContentType.JSON)
     * bulk(请求， RequestOptions.DEFAULT)
     */
    @Test
    public void testBatchRequest() throws IOException {
        BulkRequest request = new BulkRequest();
        List<Hotel> hotels = hotelService.list();
        hotels.forEach(hotel -> {
            HotelDoc hotelDoc = new HotelDoc(hotel);
            request.add(new IndexRequest("hotel")
                    .id(hotel.getId().toString())
                    .source(JSON.toJSONString(hotelDoc), XContentType.JSON));
        });
        client.bulk(request, RequestOptions.DEFAULT);
    }

    /**
     * 删除文档数据通过编号
     * DeleteRequest(索引名称, id编号)
     * delete(请求, RequestOptions.DEFAULT)
     */
    @Test
    public void testDeleteDocument() throws IOException {
        DeleteRequest request = new DeleteRequest("hotel", "61083");

        client.delete(request, RequestOptions.DEFAULT);
    }

    /**
     * 更新文档数据通过编号
     * UpdateRequest(索引名称, id编号)
     * doc(字段, 数据...)
     * update(请求, RequestOptions.DEFAULT)
     */
    @Test
    public void testUpdateDocumentById() throws IOException {
        UpdateRequest request = new UpdateRequest("hotel", "61083");

        request.doc("score", 18, "name", "Rose");

        client.update(request, RequestOptions.DEFAULT);
    }

    /**
     * 查询文档通过编号
     * GetRequest(索引名称, id编号)
     * get(请求, RequestOptions.DEFAULT)
     * 响应.getSourceAsString()得到数据得json字符串
     */
    @Test
    public void testGetDocumentById() throws IOException {
        GetRequest request = new GetRequest("hotel", "61083");

        GetResponse documentFields = client.get(request, RequestOptions.DEFAULT);

        String json = documentFields.getSourceAsString();

        System.out.println(JSON.parseObject(json, HotelDoc.class));
    }

    /**
     * 添加文档
     * IndexRequest(索引名称).id(文档唯一标识符).source(json字符串, XContentType.JSON)
     * indices.exists(请求, RequestOptions.DEFAULT)
     */
    @Test
    public void testAddDocument() throws IOException {
        Hotel hotel = hotelService.getById(61083);

        HotelDoc hotelDoc = new HotelDoc(hotel);

        IndexRequest request = new IndexRequest("hotel").id(hotel.getId().toString());

        request.source(JSON.toJSONString(hotelDoc), XContentType.JSON);


        client.index(request, RequestOptions.DEFAULT);
    }

    /**
     * 判断是否存在索引
     * GetIndexRequest(索引名称)
     * indices.exists(请求, RequestOptions.DEFAULT)
     */
    @Test
    public void testExistsHotelIndex() throws IOException {
        GetIndexRequest request = new GetIndexRequest("hotel");
        boolean exists = client.indices().exists(request, RequestOptions.DEFAULT);
        System.err.println(exists);
    }

    /**
     * 删除索引
     * DeleteIndexRequest(索引名称)
     * indices.delete(请求, RequestOptions.DEFAULT)
     */
    @Test
    public void testDeleteHotelIndex() throws IOException {
        DeleteIndexRequest request = new DeleteIndexRequest("hotel");
        client.indices().delete(request, RequestOptions.DEFAULT);
    }

    /**
     * 创建索引
     * CreateIndexRequest(索引名称)
     * source(结构, XContentType.JSON)
     * indices.create(请求, RequestOptions.DEFAULT)
     */
    @Test
    public void testCreateHotelIndex() throws IOException {
        CreateIndexRequest request = new CreateIndexRequest("hotel");
        request.source(MAPPING_TEMPLATE, XContentType.JSON);
        client.indices().create(request, RequestOptions.DEFAULT);
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
