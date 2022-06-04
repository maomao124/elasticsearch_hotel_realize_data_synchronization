package mao.elasticsearch_hotel.entity;

import java.util.List;

/**
 * Project name(项目名称)：elasticsearch_hotel
 * Package(包名): mao.elasticsearch_hotel.entity
 * Class(类名): PageResult
 * Author(作者）: mao
 * Author QQ：1296193245
 * GitHub：https://github.com/maomao124/
 * Date(创建日期)： 2022/6/2
 * Time(创建时间)： 15:06
 * Version(版本): 1.0
 * Description(描述)： 返回的数据实体类
 */


public class PageResult
{
    private Long total;
    private List<HotelDoc> hotels;

    /**
     * Instantiates a new Page result.
     */
    public PageResult()
    {
    }

    /**
     * Instantiates a new Page result.
     *
     * @param total  the total
     * @param hotels the hotels
     */
    public PageResult(Long total, List<HotelDoc> hotels)
    {
        this.total = total;
        this.hotels = hotels;
    }

    /**
     * Gets total.
     *
     * @return the total
     */
    public Long getTotal()
    {
        return total;
    }

    /**
     * Sets total.
     *
     * @param total the total
     */
    public void setTotal(Long total)
    {
        this.total = total;
    }

    /**
     * Gets hotels.
     *
     * @return the hotels
     */
    public List<HotelDoc> getHotels()
    {
        return hotels;
    }

    /**
     * Sets hotels.
     *
     * @param hotels the hotels
     */
    public void setHotels(List<HotelDoc> hotels)
    {
        this.hotels = hotels;
    }
}
