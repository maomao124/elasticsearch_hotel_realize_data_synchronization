package mao.elasticsearch_hotel.controller;

import mao.elasticsearch_hotel.entity.PageResult;
import mao.elasticsearch_hotel.entity.RequestParams;
import mao.elasticsearch_hotel.service.IHotelService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * Project name(项目名称)：elasticsearch_hotel
 * Package(包名): mao.elasticsearch_hotel.controller
 * Class(类名): HotelController
 * Author(作者）: mao
 * Author QQ：1296193245
 * GitHub：https://github.com/maomao124/
 * Date(创建日期)： 2022/6/2
 * Time(创建时间)： 14:53
 * Version(版本): 1.0
 * Description(描述)： Controller
 */

@RestController
@RequestMapping("hotel")
public class HotelController
{
    @Resource
    private IHotelService hotelService;

    /**
     * 获取hotel列表
     *
     * @param params 参数
     * @return PageResult
     */
    @PostMapping("list")
    public PageResult search(@RequestBody RequestParams params)
    {
        return hotelService.search(params);
    }

    /**
     * 自动补全功能
     *
     * @param prefix 要自动补全的前缀或者关键字
     * @return 符合条件的列表
     */
    @GetMapping("suggestion")
    public List<String> getSuggestions(@RequestParam("key") String prefix)
    {
        return hotelService.getSuggestions(prefix);
    }
}
