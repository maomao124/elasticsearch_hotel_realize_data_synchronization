package mao.elasticsearch_hotel.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Project name(项目名称)：elasticsearch_hotel
 * Package(包名): mao.elasticsearch_hotel.config
 * Class(类名): ElasticSearchConfig
 * Author(作者）: mao
 * Author QQ：1296193245
 * GitHub：https://github.com/maomao124/
 * Date(创建日期)： 2022/6/2
 * Time(创建时间)： 15:15
 * Version(版本): 1.0
 * Description(描述)： ElasticSearch配置
 */

@Configuration
public class ElasticSearchConfig
{

    /**
     * 创建ElasticSearch客户端
     *
     * @return RestHighLevelClient
     */
    @Bean
    public RestHighLevelClient restHighLevelClient()
    {
        return new RestHighLevelClient(RestClient.builder(
                new HttpHost("localhost", 9200, "http")
        ));
    }

}
