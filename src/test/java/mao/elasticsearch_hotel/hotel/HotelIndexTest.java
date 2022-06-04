package mao.elasticsearch_hotel.hotel;

import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.xcontent.XContentType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

import static mao.elasticsearch_hotel.constants.HotelConstants.MAPPING_TEMPLATE;

/**
 * Project name(项目名称)：elasticsearch_hotel
 * Package(包名): mao.elasticsearch_hotel.hotel
 * Class(类名): HotelIndexTest
 * Author(作者）: mao
 * Author QQ：1296193245
 * GitHub：https://github.com/maomao124/
 * Date(创建日期)： 2022/6/2
 * Time(创建时间)： 16:11
 * Version(版本): 1.0
 * Description(描述)： 文档索引操作的测试
 */

@SpringBootTest
public class HotelIndexTest
{
    @Autowired
    private RestHighLevelClient client;

    /**
     * 创建hotel索引
     *
     * @throws IOException IOException
     */
    @Test
    void testCreateIndex() throws IOException
    {
        // 1.准备Request      PUT /hotel
        CreateIndexRequest request = new CreateIndexRequest("hotel");
        // 2.准备请求参数
        request.source(MAPPING_TEMPLATE, XContentType.JSON);
        // 3.发送请求
        client.indices().create(request, RequestOptions.DEFAULT);
    }

    /**
     * 查询索引是否存在
     *
     * @throws IOException IOException
     */
    @Test
    void testExistsIndex() throws IOException
    {
        // 1.准备Request
        GetIndexRequest request = new GetIndexRequest("hotel");
        // 3.发送请求
        boolean isExists = client.indices().exists(request, RequestOptions.DEFAULT);

        System.out.println(isExists ? "存在" : "不存在");
    }

    /**
     * 删除索引
     *
     * @throws IOException IOException
     */
    @Test
    void testDeleteIndex() throws IOException
    {
        // 1.准备Request
        DeleteIndexRequest request = new DeleteIndexRequest("hotel");
        // 3.发送请求
        client.indices().delete(request, RequestOptions.DEFAULT);
    }
}
