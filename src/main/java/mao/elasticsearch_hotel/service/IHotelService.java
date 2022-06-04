package mao.elasticsearch_hotel.service;

import com.baomidou.mybatisplus.extension.service.IService;
import mao.elasticsearch_hotel.entity.Hotel;
import mao.elasticsearch_hotel.entity.PageResult;
import mao.elasticsearch_hotel.entity.RequestParams;

import java.util.List;

/**
 * Project name(项目名称)：elasticsearch_hotel
 * Package(包名): mao.elasticsearch_hotel.service
 * Class(类名): IHotelService
 * Author(作者）: mao
 * Author QQ：1296193245
 * GitHub：https://github.com/maomao124/
 * Date(创建日期)： 2022/6/2
 * Time(创建时间)： 14:54
 * Version(版本): 1.0
 * Description(描述)： 接口
 */


public interface IHotelService extends IService<Hotel>
{
    /**
     * 搜索
     *
     * @param params 参数
     * @return PageResult
     */
    PageResult search(RequestParams params);

    /**
     * 自动补全功能
     *
     * @param prefix 要自动补全的前缀或者关键字
     * @return 符合条件的列表
     */
    List<String> getSuggestions(String prefix);

    /**
     * 添加ElasticSearch数据
     *
     * @param id 要添加的id号，信息从数据库里根据id查
     */
    void insertElasticSearchHotelById(Long id);

    /**
     * 删除ElasticSearch里某个id的数据
     *
     * @param id id
     */
    void deleteElasticSearchHotelById(Long id);
}
