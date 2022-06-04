package mao.elasticsearch_hotel.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * Project name(项目名称)：elasticsearch_hotel
 * Package(包名): mao.elasticsearch_hotel.entity
 * Class(类名): Hotel
 * Author(作者）: mao
 * Author QQ：1296193245
 * GitHub：https://github.com/maomao124/
 * Date(创建日期)： 2022/6/2
 * Time(创建时间)： 15:03
 * Version(版本): 1.0
 * Description(描述)： 无
 */


@TableName("tb_hotel")
public class Hotel
{
    @TableId(type = IdType.INPUT)
    private Long id;
    private String name;
    private String address;
    private Integer price;
    private Integer score;
    private String brand;
    private String city;
    private String starName;
    private String business;
    private String longitude;
    private String latitude;
    private String pic;

    /**
     * Instantiates a new Hotel.
     */
    public Hotel()
    {

    }

    /**
     * Instantiates a new Hotel.
     *
     * @param id        the id
     * @param name      the name
     * @param address   the address
     * @param price     the price
     * @param score     the score
     * @param brand     the brand
     * @param city      the city
     * @param starName  the star name
     * @param business  the business
     * @param longitude the longitude
     * @param latitude  the latitude
     * @param pic       the pic
     */
    public Hotel(Long id, String name, String address, Integer price, Integer score, String brand, String city, String starName, String business, String longitude, String latitude, String pic)
    {
        this.id = id;
        this.name = name;
        this.address = address;
        this.price = price;
        this.score = score;
        this.brand = brand;
        this.city = city;
        this.starName = starName;
        this.business = business;
        this.longitude = longitude;
        this.latitude = latitude;
        this.pic = pic;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public Long getId()
    {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(Long id)
    {
        this.id = id;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName()
    {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * Gets address.
     *
     * @return the address
     */
    public String getAddress()
    {
        return address;
    }

    /**
     * Sets address.
     *
     * @param address the address
     */
    public void setAddress(String address)
    {
        this.address = address;
    }

    /**
     * Gets price.
     *
     * @return the price
     */
    public Integer getPrice()
    {
        return price;
    }

    /**
     * Sets price.
     *
     * @param price the price
     */
    public void setPrice(Integer price)
    {
        this.price = price;
    }

    /**
     * Gets score.
     *
     * @return the score
     */
    public Integer getScore()
    {
        return score;
    }

    /**
     * Sets score.
     *
     * @param score the score
     */
    public void setScore(Integer score)
    {
        this.score = score;
    }

    /**
     * Gets brand.
     *
     * @return the brand
     */
    public String getBrand()
    {
        return brand;
    }

    /**
     * Sets brand.
     *
     * @param brand the brand
     */
    public void setBrand(String brand)
    {
        this.brand = brand;
    }

    /**
     * Gets city.
     *
     * @return the city
     */
    public String getCity()
    {
        return city;
    }

    /**
     * Sets city.
     *
     * @param city the city
     */
    public void setCity(String city)
    {
        this.city = city;
    }

    /**
     * Gets star name.
     *
     * @return the star name
     */
    public String getStarName()
    {
        return starName;
    }

    /**
     * Sets star name.
     *
     * @param starName the star name
     */
    public void setStarName(String starName)
    {
        this.starName = starName;
    }

    /**
     * Gets business.
     *
     * @return the business
     */
    public String getBusiness()
    {
        return business;
    }

    /**
     * Sets business.
     *
     * @param business the business
     */
    public void setBusiness(String business)
    {
        this.business = business;
    }

    /**
     * Gets longitude.
     *
     * @return the longitude
     */
    public String getLongitude()
    {
        return longitude;
    }

    /**
     * Sets longitude.
     *
     * @param longitude the longitude
     */
    public void setLongitude(String longitude)
    {
        this.longitude = longitude;
    }

    /**
     * Gets latitude.
     *
     * @return the latitude
     */
    public String getLatitude()
    {
        return latitude;
    }

    /**
     * Sets latitude.
     *
     * @param latitude the latitude
     */
    public void setLatitude(String latitude)
    {
        this.latitude = latitude;
    }

    /**
     * Gets pic.
     *
     * @return the pic
     */
    public String getPic()
    {
        return pic;
    }

    /**
     * Sets pic.
     *
     * @param pic the pic
     */
    public void setPic(String pic)
    {
        this.pic = pic;
    }

    @Override
    @SuppressWarnings("all")
    public String toString()
    {
        final StringBuilder stringbuilder = new StringBuilder();
        stringbuilder.append("id：").append(id).append('\n');
        stringbuilder.append("name：").append(name).append('\n');
        stringbuilder.append("address：").append(address).append('\n');
        stringbuilder.append("price：").append(price).append('\n');
        stringbuilder.append("score：").append(score).append('\n');
        stringbuilder.append("brand：").append(brand).append('\n');
        stringbuilder.append("city：").append(city).append('\n');
        stringbuilder.append("starName：").append(starName).append('\n');
        stringbuilder.append("business：").append(business).append('\n');
        stringbuilder.append("longitude：").append(longitude).append('\n');
        stringbuilder.append("latitude：").append(latitude).append('\n');
        stringbuilder.append("pic：").append(pic).append('\n');
        return stringbuilder.toString();
    }
}
