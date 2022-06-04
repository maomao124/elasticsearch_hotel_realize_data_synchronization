package mao.elasticsearch_hotel.listener;

import mao.elasticsearch_hotel.constants.RabbitMQConstants;
import mao.elasticsearch_hotel.service.IHotelService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Project name(项目名称)：elasticsearch_hotel_realize_data_synchronization
 * Package(包名): mao.elasticsearch_hotel.listener
 * Class(类名): HotelListener
 * Author(作者）: mao
 * Author QQ：1296193245
 * GitHub：https://github.com/maomao124/
 * Date(创建日期)： 2022/6/4
 * Time(创建时间)： 19:10
 * Version(版本): 1.0
 * Description(描述)： rabbitmq的监听器，消费者
 */

@Component
public class HotelListener
{
    @Resource
    private IHotelService hotelService;

    /**
     * rabbitmq的监听器，添加或者更新文档
     *
     * @param id id
     */
    @RabbitListener(queues = {RabbitMQConstants.INSERT_QUEUE_NAME})
    public void insertAndUpdateHotelListener(Long id)
    {
        hotelService.insertElasticSearchHotelById(id);
    }

    /**
     * 同理，删除文档
     *
     * @param id id
     */
    @RabbitListener(queues = {RabbitMQConstants.DELETE_QUEUE_NAME})
    public void deleteHotelListener(Long id)
    {
        hotelService.deleteElasticSearchHotelById(id);
    }
}
