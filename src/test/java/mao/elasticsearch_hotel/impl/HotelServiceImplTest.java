package mao.elasticsearch_hotel.impl;

import mao.elasticsearch_hotel.service.IHotelService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Project name(项目名称)：elasticsearch_hotel
 * Package(包名): mao.elasticsearch_hotel.service.impl
 * Class(测试类名): HotelServiceImplTest
 * Author(作者）: mao
 * Author QQ：1296193245
 * GitHub：https://github.com/maomao124/
 * Date(创建日期)： 2022/6/2
 * Time(创建时间)： 15:28
 * Version(版本): 1.0
 * Description(描述)： 测试类
 */

@SpringBootTest
class HotelServiceImplTest
{
    @Autowired
    private IHotelService hotelService;

    @Test
    void name()
    {
        long count = hotelService.count();
        System.out.println(count);
    }
}