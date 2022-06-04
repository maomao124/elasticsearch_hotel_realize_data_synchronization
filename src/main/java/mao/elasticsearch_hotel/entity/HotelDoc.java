package mao.elasticsearch_hotel.entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Project name(项目名称)：elasticsearch_hotel
 * Package(包名): mao.elasticsearch_hotel.entity
 * Class(类名): HotelDoc
 * Author(作者）: mao
 * Author QQ：1296193245
 * GitHub：https://github.com/maomao124/
 * Date(创建日期)： 2022/6/2
 * Time(创建时间)： 15:05
 * Version(版本): 1.0
 * Description(描述)： 无
 */

public class HotelDoc
{
    private Long id;
    private String name;
    private String address;
    private Integer price;
    private Integer score;
    private String brand;
    private String city;
    private String starName;
    private String business;
    //地理位置
    private String location;
    private String pic;
    private Object distance;
    //是否为广告
    private Boolean isAD;
    //补全
    private List<String> suggestion;

    /**
     * Instantiates a new Hotel doc.
     *
     * @param hotel the hotel
     */
    public HotelDoc(Hotel hotel)
    {
        this.id = hotel.getId();
        this.name = hotel.getName();
        this.address = hotel.getAddress();
        this.price = hotel.getPrice();
        this.score = hotel.getScore();
        this.brand = hotel.getBrand();
        this.city = hotel.getCity();
        this.starName = hotel.getStarName();
        this.business = hotel.getBusiness();
        this.location = hotel.getLatitude() + ", " + hotel.getLongitude();
        this.pic = hotel.getPic();
        // 组装suggestion
        if (this.business.contains("/"))
        {
            // business有多个值，需要切割
            String[] arr = this.business.split("/");
            // 添加元素
            this.suggestion = new ArrayList<>();
            this.suggestion.add(this.brand);
            Collections.addAll(this.suggestion, arr);
        }
        else
        {
            this.suggestion = Arrays.asList(this.brand, this.business);
        }
    }

    /**
     * Instantiates a new Hotel doc.
     *
     * @param id       the id
     * @param name     the name
     * @param address  the address
     * @param price    the price
     * @param score    the score
     * @param brand    the brand
     * @param city     the city
     * @param starName the star name
     * @param business the business
     * @param location the location
     * @param pic      the pic
     * @param distance the distance
     * @param isAD     the is ad
     */
    public HotelDoc(Long id, String name, String address, Integer price, Integer score, String brand, String city, String starName, String business, String location, String pic, Object distance, Boolean isAD)
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
        this.location = location;
        this.pic = pic;
        this.distance = distance;
        this.isAD = isAD;
        // 组装suggestion
        if (this.business.contains("/"))
        {
            // business有多个值，需要切割
            String[] arr = this.business.split("/");
            // 添加元素
            this.suggestion = new ArrayList<>();
            this.suggestion.add(this.brand);
            Collections.addAll(this.suggestion, arr);
        }
        else
        {
            this.suggestion = Arrays.asList(this.brand, this.business);
        }
    }

    /**
     * Instantiates a new Hotel doc.
     */
    public HotelDoc()
    {

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
     * Gets location.
     *
     * @return the location
     */
    public String getLocation()
    {
        return location;
    }

    /**
     * Sets location.
     *
     * @param location the location
     */
    public void setLocation(String location)
    {
        this.location = location;
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

    /**
     * Gets distance.
     *
     * @return the distance
     */
    public Object getDistance()
    {
        return distance;
    }

    /**
     * Sets distance.
     *
     * @param distance the distance
     */
    public void setDistance(Object distance)
    {
        this.distance = distance;
    }

    /**
     * Gets ad.
     *
     * @return the ad
     */
    public Boolean getAD()
    {
        return isAD;
    }

    /**
     * Sets ad.
     *
     * @param AD the ad
     */
    public void setAD(Boolean AD)
    {
        isAD = AD;
    }

    /**
     * Gets suggestion.
     *
     * @return the suggestion
     */
    public List<String> getSuggestion()
    {
        return suggestion;
    }

    /**
     * Sets suggestion.
     *
     * @param suggestion the suggestion
     */
    public void setSuggestion(List<String> suggestion)
    {
        this.suggestion = suggestion;
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
        stringbuilder.append("location：").append(location).append('\n');
        stringbuilder.append("pic：").append(pic).append('\n');
        stringbuilder.append("distance：").append(distance).append('\n');
        stringbuilder.append("isAD：").append(isAD).append('\n');
        stringbuilder.append("suggestion：").append(suggestion).append('\n');
        return stringbuilder.toString();
    }
}
