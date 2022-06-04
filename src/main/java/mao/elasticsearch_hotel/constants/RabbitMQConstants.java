package mao.elasticsearch_hotel.constants;

/**
 * Project name(项目名称)：elasticsearch_hotel
 * Package(包名): mao.elasticsearch_hotel.constants
 * Class(类名): RabbitMQConstants
 * Author(作者）: mao
 * Author QQ：1296193245
 * GitHub：https://github.com/maomao124/
 * Date(创建日期)： 2022/6/4
 * Time(创建时间)： 13:57
 * Version(版本): 1.0
 * Description(描述)： 存放RabbitMQ常量
 */

public class RabbitMQConstants
{
    public static final String EXCHANGE_NAME = "hotel.exchange";
    public static final String INSERT_QUEUE_NAME = "hotel.insert.queue";
    public static final String DELETE_QUEUE_NAME = "hotel.delete.queue";
    public static final String INSERT_KEY = "hotel.insert";
    public static final String DELETE_KEY = "hotel.delete";
}
