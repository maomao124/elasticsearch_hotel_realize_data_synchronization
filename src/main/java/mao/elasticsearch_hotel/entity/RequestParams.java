package mao.elasticsearch_hotel.entity;

/**
 * Project name(项目名称)：elasticsearch_hotel
 * Package(包名): mao.elasticsearch_hotel.entity
 * Class(类名): RequestParams
 * Author(作者）: mao
 * Author QQ：1296193245
 * GitHub：https://github.com/maomao124/
 * Date(创建日期)： 2022/6/2
 * Time(创建时间)： 15:07
 * Version(版本): 1.0
 * Description(描述)： 请求参数实体类
 */

public class RequestParams
{
    private String key;
    private Integer page;
    private Integer size;
    private String sortBy;
    private String brand;
    private String city;
    private String starName;
    private Integer minPrice;
    private Integer maxPrice;
    private String location;

    /**
     * Instantiates a new Request params.
     */
    public RequestParams()
    {
    }

    /**
     * Instantiates a new Request params.
     *
     * @param key      the key
     * @param page     the page
     * @param size     the size
     * @param sortBy   the sort by
     * @param brand    the brand
     * @param city     the city
     * @param starName the star name
     * @param minPrice the min price
     * @param maxPrice the max price
     * @param location the location
     */
    public RequestParams(String key, Integer page, Integer size, String sortBy, String brand,
                         String city, String starName, Integer minPrice, Integer maxPrice, String location)
    {
        this.key = key;
        this.page = page;
        this.size = size;
        this.sortBy = sortBy;
        this.brand = brand;
        this.city = city;
        this.starName = starName;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.location = location;
    }

    /**
     * Gets key.
     *
     * @return the key
     */
    public String getKey()
    {
        return key;
    }

    /**
     * Sets key.
     *
     * @param key the key
     */
    public void setKey(String key)
    {
        this.key = key;
    }

    /**
     * Gets page.
     *
     * @return the page
     */
    public Integer getPage()
    {
        return page;
    }

    /**
     * Sets page.
     *
     * @param page the page
     */
    public void setPage(Integer page)
    {
        this.page = page;
    }

    /**
     * Gets size.
     *
     * @return the size
     */
    public Integer getSize()
    {
        return size;
    }

    /**
     * Sets size.
     *
     * @param size the size
     */
    public void setSize(Integer size)
    {
        this.size = size;
    }

    /**
     * Gets sort by.
     *
     * @return the sort by
     */
    public String getSortBy()
    {
        return sortBy;
    }

    /**
     * Sets sort by.
     *
     * @param sortBy the sort by
     */
    public void setSortBy(String sortBy)
    {
        this.sortBy = sortBy;
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
     * Gets min price.
     *
     * @return the min price
     */
    public Integer getMinPrice()
    {
        return minPrice;
    }

    /**
     * Sets min price.
     *
     * @param minPrice the min price
     */
    public void setMinPrice(Integer minPrice)
    {
        this.minPrice = minPrice;
    }

    /**
     * Gets max price.
     *
     * @return the max price
     */
    public Integer getMaxPrice()
    {
        return maxPrice;
    }

    /**
     * Sets max price.
     *
     * @param maxPrice the max price
     */
    public void setMaxPrice(Integer maxPrice)
    {
        this.maxPrice = maxPrice;
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

    @Override
    @SuppressWarnings("all")
    public String toString()
    {
        final StringBuilder stringbuilder = new StringBuilder();
        stringbuilder.append("key：").append(key).append('\n');
        stringbuilder.append("page：").append(page).append('\n');
        stringbuilder.append("size：").append(size).append('\n');
        stringbuilder.append("sortBy：").append(sortBy).append('\n');
        stringbuilder.append("brand：").append(brand).append('\n');
        stringbuilder.append("city：").append(city).append('\n');
        stringbuilder.append("starName：").append(starName).append('\n');
        stringbuilder.append("minPrice：").append(minPrice).append('\n');
        stringbuilder.append("maxPrice：").append(maxPrice).append('\n');
        stringbuilder.append("location：").append(location).append('\n');
        return stringbuilder.toString();
    }
}
