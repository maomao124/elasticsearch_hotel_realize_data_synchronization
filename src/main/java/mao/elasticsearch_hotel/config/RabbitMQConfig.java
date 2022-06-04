package mao.elasticsearch_hotel.config;

import mao.elasticsearch_hotel.constants.RabbitMQConstants;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Project name(项目名称)：elasticsearch_hotel
 * Package(包名): mao.elasticsearch_hotel.config
 * Class(类名): RabbitMQConfig
 * Author(作者）: mao
 * Author QQ：1296193245
 * GitHub：https://github.com/maomao124/
 * Date(创建日期)： 2022/6/4
 * Time(创建时间)： 14:00
 * Version(版本): 1.0
 * Description(描述)： RabbitMQConfig
 * 不考虑消息丢失问题，不考虑持久化，不考虑重复消费
 */

@Configuration
public class RabbitMQConfig
{
    /**
     * 声明交换机 ，直接交换机
     *
     * @return DirectExchange
     */
    @Bean
    public DirectExchange directExchange()
    {
        return new DirectExchange(RabbitMQConstants.EXCHANGE_NAME, false, false);
    }

    /**
     * 声明队列 ，用于添加和更新酒店信息
     *
     * @return Queue
     */
    @Bean
    public Queue insertAndUpdateQueue()
    {
        return new Queue(RabbitMQConstants.INSERT_QUEUE_NAME, false, false, false, null);
    }

    /**
     * 声明队列 ，用于删除酒店信息
     *
     * @return Queue
     */
    @Bean
    public Queue deleteQueue()
    {
        return new Queue(RabbitMQConstants.DELETE_QUEUE_NAME, false, false, false, null);
    }

    /**
     * insertAndUpdateQueue绑定交换机
     *
     * @return Binding
     */
    @Bean
    public Binding insertAndUpdateQueue_binding_directExchange()
    {
        return BindingBuilder.bind(insertAndUpdateQueue()).to(directExchange()).with(RabbitMQConstants.INSERT_KEY);
    }

    /**
     * deleteQueue绑定交换机
     *
     * @return Binding
     */
    @Bean
    public Binding deleteQueue_binding_directExchange()
    {
        return BindingBuilder.bind(deleteQueue()).to(directExchange()).with(RabbitMQConstants.DELETE_KEY);
    }
}
