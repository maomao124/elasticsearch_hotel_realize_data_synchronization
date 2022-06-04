package mao.elasticsearch_hotel.mapper;

import mao.elasticsearch_hotel.entity.Hotel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * Project name(项目名称)：elasticsearch_hotel
 * Package(包名): mao.elasticsearch_hotel.mapper
 * Class(测试类名): HotelMapperTest
 * Author(作者）: mao
 * Author QQ：1296193245
 * GitHub：https://github.com/maomao124/
 * Date(创建日期)： 2022/6/2
 * Time(创建时间)： 15:23
 * Version(版本): 1.0
 * Description(描述)： 测试类
 */

@SpringBootTest
class HotelMapperTest
{

    @Autowired
    private HotelMapper hotelMapper;

    /**
     * Gets all.
     */
    @Test
    void getAll()
    {
        List<Hotel> hotelList = hotelMapper.selectList(null);
        for (Hotel hotel : hotelList)
        {
            System.out.println(hotel);
        }
    }

    /**
     * Count.
     */
    @Test
    void count()
    {
        Long count = hotelMapper.selectCount(null);
        System.out.println(count);
    }
}