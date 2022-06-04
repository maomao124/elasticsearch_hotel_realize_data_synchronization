package mao.elasticsearch_hotel.hotel;

import com.alibaba.fastjson.JSON;
import mao.elasticsearch_hotel.entity.Hotel;
import mao.elasticsearch_hotel.entity.HotelDoc;
import mao.elasticsearch_hotel.service.IHotelService;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.xcontent.XContentType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.List;

/**
 * Project name(项目名称)：elasticsearch_hotel
 * Package(包名): mao.elasticsearch_hotel.hotel
 * Class(类名): HotelDocumentTest
 * Author(作者）: mao
 * Author QQ：1296193245
 * GitHub：https://github.com/maomao124/
 * Date(创建日期)： 2022/6/2
 * Time(创建时间)： 16:09
 * Version(版本): 1.0
 * Description(描述)： 文档操作的测试
 */

@SpringBootTest
public class HotelDocumentTest
{
    @Autowired
    private RestHighLevelClient client;

    @Autowired
    private IHotelService hotelService;

    /**
     * 测试添加一条数据
     * response=HTTP/1.1 200 OK}
     *
     * @throws IOException IOException
     */
    @Test
    void testAddDocument() throws IOException
    {
        // 1.查询数据库hotel数据
        Hotel hotel = hotelService.getById(61083L);
        // 2.转换为HotelDoc
        HotelDoc hotelDoc = new HotelDoc(hotel);
        // 3.转JSON
        String json = JSON.toJSONString(hotelDoc);

        // 1.准备Request
        IndexRequest request = new IndexRequest("hotel").id(hotelDoc.getId().toString());
        // 2.准备请求参数DSL，其实就是文档的JSON字符串
        request.source(json, XContentType.JSON);
        // 3.发送请求
        client.index(request, RequestOptions.DEFAULT);
    }

    /**
     * 获取刚才插入的数据
     *
     * @throws IOException IOException
     */
    @Test
    void testGetDocumentById() throws IOException
    {
        // 1.准备Request      // GET /hotel/_doc/{id}
        GetRequest request = new GetRequest("hotel", "61083");
        // 2.发送请求
        GetResponse response = client.get(request, RequestOptions.DEFAULT);
        // 3.解析响应结果
        String json = response.getSourceAsString();

        HotelDoc hotelDoc = JSON.parseObject(json, HotelDoc.class);
        System.out.println("hotelDoc = " + hotelDoc);
    }


    /**
     * 删除刚才插入的数据
     *
     * @throws IOException IOException
     */
    @Test
    void testDeleteDocumentById() throws IOException
    {
        // 1.准备Request      // DELETE /hotel/_doc/{id}
        DeleteRequest request = new DeleteRequest("hotel", "61083");
        // 2.发送请求
        client.delete(request, RequestOptions.DEFAULT);
    }

    /**
     * 更新文档数据
     *
     * @throws IOException IOException
     */
    @Test
    void testUpdateById() throws IOException
    {
        // 1.准备Request
        UpdateRequest request = new UpdateRequest("hotel", "61083");
        // 2.准备参数
        request.doc(
                "price", "870"
        );
        // 3.发送请求
        client.update(request, RequestOptions.DEFAULT);
    }

    /**
     * 从数据库里批量插入所有数据
     *
     * @throws IOException IOException
     */
    @Test
    void testBulkRequest() throws IOException
    {
        // 查询所有的酒店数据
        List<Hotel> list = hotelService.list();
        System.out.println("一共：" + list.size() + "条数据");
        // 1.准备Request
        BulkRequest request = new BulkRequest();
        // 2.准备参数
        for (Hotel hotel : list)
        {
            // 2.1.转为HotelDoc
            HotelDoc hotelDoc = new HotelDoc(hotel);
            // 2.2.转json
            String json = JSON.toJSONString(hotelDoc);
            // 2.3.添加请求
            request.add(new IndexRequest("hotel").id(hotel.getId().toString()).source(json, XContentType.JSON));
        }

        // 3.发送请求
        client.bulk(request, RequestOptions.DEFAULT);
    }


}
