package mao.elasticsearch_hotel.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import mao.elasticsearch_hotel.entity.Hotel;
import mao.elasticsearch_hotel.entity.HotelDoc;
import mao.elasticsearch_hotel.entity.PageResult;
import mao.elasticsearch_hotel.entity.RequestParams;
import mao.elasticsearch_hotel.mapper.HotelMapper;
import mao.elasticsearch_hotel.service.IHotelService;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.geo.GeoPoint;
import org.elasticsearch.common.unit.DistanceUnit;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder;
import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.elasticsearch.search.suggest.Suggest;
import org.elasticsearch.search.suggest.SuggestBuilder;
import org.elasticsearch.search.suggest.SuggestBuilders;
import org.elasticsearch.search.suggest.completion.CompletionSuggestion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Project name(项目名称)：elasticsearch_hotel
 * Package(包名): mao.elasticsearch_hotel.service.impl
 * Class(类名): HotelServiceImpl
 * Author(作者）: mao
 * Author QQ：1296193245
 * GitHub：https://github.com/maomao124/
 * Date(创建日期)： 2022/6/2
 * Time(创建时间)： 14:58
 * Version(版本): 1.0
 * Description(描述)： 无
 */

@Service
@Slf4j
public class HotelServiceImpl extends ServiceImpl<HotelMapper, Hotel> implements IHotelService
{
    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @Override
    public PageResult search(RequestParams params)
    {
        try
        {
            // 1.准备Request
            SearchRequest request = new SearchRequest("hotel");
            // 2.准备请求参数
            // 2.1.query
            buildBasicQuery(params, request);
            // 2.2.分页
            int page = params.getPage();
            int size = params.getSize();
            request.source().from((page - 1) * size).size(size);
            // 2.3.距离排序
            String location = params.getLocation();
            //判断距离信息是否为空
            if (StringUtils.isNotBlank(location))
            {
                //如果距离信息不为空，按距离排序
                request.source().sort(SortBuilders
                        .geoDistanceSort("location", new GeoPoint(location))
                        //升序
                        .order(SortOrder.ASC)
                        //单位为千米
                        .unit(DistanceUnit.KILOMETERS)
                );
            }
            // 3.发送请求
            SearchResponse response = restHighLevelClient.search(request, RequestOptions.DEFAULT);
            // 4.解析响应
            return handleResponse(response);
        }
        catch (IOException e)
        {
            throw new RuntimeException("搜索数据失败", e);
        }
    }

    @Override
    public List<String> getSuggestions(String prefix)
    {
        try
        {
            //构建搜索请求
            SearchRequest searchRequest = new SearchRequest("hotel");
            //构建请求体
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            //自动补全
            searchSourceBuilder.suggest(new SuggestBuilder()
                    .addSuggestion("suggestions",
                            SuggestBuilders.completionSuggestion("suggestion")
                                    .prefix(prefix)
                                    .size(10)
                                    .skipDuplicates(true)));
            //放入到请求中
            searchRequest.source(searchSourceBuilder);
            //发起请求
            SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
            //获取数据
            //获取补全部分
            Suggest suggest = searchResponse.getSuggest();
            CompletionSuggestion suggestions = suggest.getSuggestion("suggestions");
            List<CompletionSuggestion.Entry.Option> options = suggestions.getOptions();
            //遍历数据
            List<String> list = new ArrayList<>(options.size());
            for (CompletionSuggestion.Entry.Option option : options)
            {
                //获取文本信息
                String text = option.getText().string();
                //加入到集合中
                list.add(text);
            }
            //返回数据
            return list;
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }


    /**
     * 填充查询参数的方法
     *
     * @param params  RequestParams
     * @param request SearchRequest
     */
    private void buildBasicQuery(RequestParams params, SearchRequest request)
    {
        // 1.准备Boolean查询
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();

        // 1.1.关键字搜索，match查询，放到must中
        String key = params.getKey();
        //判断关键字是否为空
        if (StringUtils.isNotBlank(key))
        {
            // 不为空，根据关键字查询
            boolQuery.must(QueryBuilders.matchQuery("all", key));
        }
        else
        {
            // 为空，查询所有
            boolQuery.must(QueryBuilders.matchAllQuery());
        }

        // 1.2.品牌
        String brand = params.getBrand();
        //判断品牌信息是否为空
        if (StringUtils.isNotBlank(brand))
        {
            //品牌信息不为空，过滤掉其它品牌，不参与算分
            boolQuery.filter(QueryBuilders.termQuery("brand", brand));
        }
        // 1.3.城市
        String city = params.getCity();
        //判断城市是否为空
        if (StringUtils.isNotBlank(city))
        {
            //不为空，过滤掉其它城市，不参与算分
            boolQuery.filter(QueryBuilders.termQuery("city", city));
        }
        // 1.4.星级
        String starName = params.getStarName();
        //判断星级信息是否为空
        if (StringUtils.isNotBlank(starName))
        {
            //不为空，过滤掉其它星级，不参与算分
            boolQuery.filter(QueryBuilders.termQuery("starName", starName));
        }
        // 1.5.价格范围
        //最小价格
        Integer minPrice = params.getMinPrice();
        //最大价格
        Integer maxPrice = params.getMaxPrice();
        //判断是否选择了价格区间
        if (minPrice != null && maxPrice != null)
        {
            //选择了价格区间
            //是否有最大值，没有最大值，就是xxx元以上
            maxPrice = maxPrice == 0 ? Integer.MAX_VALUE : maxPrice;
            //设置价格区间
            boolQuery.filter(QueryBuilders.rangeQuery("price").gte(minPrice).lte(maxPrice));
        }

        // 2.算分函数查询
        FunctionScoreQueryBuilder functionScoreQuery = QueryBuilders.functionScoreQuery(
                boolQuery, // 原始查询，boolQuery
                new FunctionScoreQueryBuilder.FilterFunctionBuilder[]
                        { // function数组
                                new FunctionScoreQueryBuilder.FilterFunctionBuilder(
                                        //判断是否是广告，如果是，分数加10倍
                                        QueryBuilders.termQuery("isAD", true), // 过滤条件
                                        ScoreFunctionBuilders.weightFactorFunction(10) // 算分函数
                                )
                        }
        );

        // 3.设置查询条件
        request.source().query(functionScoreQuery);
    }

    /**
     * 处理响应信息的方法
     *
     * @param response SearchResponse
     * @return PageResult
     */
    private PageResult handleResponse(SearchResponse response)
    {
        SearchHits searchHits = response.getHits();
        // 4.1.总条数
        long total = searchHits.getTotalHits().value;
        // 4.2.获取文档数组
        SearchHit[] hits = searchHits.getHits();
        // 4.3.遍历
        List<HotelDoc> hotels = new ArrayList<>(hits.length);
        for (SearchHit hit : hits)
        {
            // 4.4.获取source
            String json = hit.getSourceAsString();
            // 4.5.反序列化，非高亮的
            HotelDoc hotelDoc = JSON.parseObject(json, HotelDoc.class);
            // 4.6.处理高亮结果
            // 1)获取高亮map
            Map<String, HighlightField> map = hit.getHighlightFields();
            //判断集合是否为空，避免空指针
            if (map != null && !map.isEmpty())
            {
                // 2）根据字段名，获取高亮结果
                HighlightField highlightField = map.get("name");
                //判断高亮字段是否为空，避免空指针
                if (highlightField != null)
                {
                    // 3）获取高亮结果字符串数组中的第1个元素
                    String hName = highlightField.getFragments()[0].toString();
                    // 4）把高亮结果放到HotelDoc中，覆盖原有的数据
                    hotelDoc.setName(hName);
                }
            }
            // 4.8.排序信息
            Object[] sortValues = hit.getSortValues();
            if (sortValues.length > 0)
            {
                //设置距离
                hotelDoc.setDistance(sortValues[0]);
            }

            // 4.9.放入集合
            hotels.add(hotelDoc);
        }
        //返回数据
        return new PageResult(total, hotels);
    }

    @Override
    public void insertElasticSearchHotelById(Long id)
    {

    }

    @Override
    public void deleteElasticSearchHotelById(Long id)
    {

    }
}
