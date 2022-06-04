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
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
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
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.elasticsearch.search.suggest.Suggest;
import org.elasticsearch.search.suggest.SuggestBuilder;
import org.elasticsearch.search.suggest.SuggestBuilders;
import org.elasticsearch.search.suggest.completion.CompletionSuggestion;
import org.elasticsearch.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
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
            //排序
            String sortBy = params.getSortBy();
            if (sortBy != null && !sortBy.equals("default"))
            {
                request.source().sort(sortBy, SortOrder.DESC);
            }
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
        try
        {
            log.debug("开始添加或者更新ElasticSearch文档：" + id);
            //查询数据库
            Hotel hotel = this.getById(id);
            //判断结果是否为空
            if (hotel == null || hotel.getId() == null)
            {
                //数据库里查不到，无法插入或者更新
                log.warn("数据库里id为" + id + "的数据不存在，无法添加或者更新ElasticSearch");
                return;
            }
            //数据库里存在
            //转文档类型
            HotelDoc hotelDoc = new HotelDoc(hotel);
            //构建请求
            IndexRequest indexRequest = new IndexRequest("hotel");
            //设置id
            indexRequest.id(hotel.getId().toString());
            //转json
            String json = JSON.toJSONString(hotelDoc);
            //构建请求体
            indexRequest.source(json, XContentType.JSON);
            //发起请求
            IndexResponse indexResponse = restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
            log.debug("添加或者更新ElasticSearch文档成功：" + id);
        }
        catch (Exception e)
        {
            //判断是否成功
            if (e.getMessage().contains("response=HTTP/1.1 200 OK}"))
            {
                log.debug("添加或者更新ElasticSearch文档成功：" + id);
            }
            else
            {
                log.warn("添加或者更新ElasticSearch文档失败：" + id);
                //抛出
                throw new RuntimeException(e);
            }
        }


    }

    @Override
    public void deleteElasticSearchHotelById(Long id)
    {
        try
        {
            log.debug("开始删除ElasticSearch文档：" + id);
            //构建请求
            DeleteRequest deleteRequest = new DeleteRequest("hotel");
            //设置id
            deleteRequest.id(id.toString());
            //发起请求
            DeleteResponse deleteResponse = restHighLevelClient.delete(deleteRequest, RequestOptions.DEFAULT);
            log.debug("删除ElasticSearch文档成功：" + id);
        }
        catch (Exception e)
        {
            //判断是否成功
            if (e.getMessage().contains("response=HTTP/1.1 200 OK}"))
            {
                log.debug("删除ElasticSearch文档成功：" + id);
            }
            else
            {
                log.warn("删除ElasticSearch文档失败：" + id);
                //抛出
                throw new RuntimeException(e);
            }
        }

    }

    @Override
    public Map<String, List<String>> getFilters(RequestParams params)
    {
        try
        {
            //构建请求
            SearchRequest searchRequest = new SearchRequest("hotel");
            //查询
            buildBasicQuery(params, searchRequest);
            //分页
            searchRequest.source().size(0);
            //聚合
            buildAggregations(searchRequest);
            //发起请求
            SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
            //获取数据
            //获取aggregations部分
            Aggregations aggregations = searchResponse.getAggregations();
            Map<String, List<String>> filters = new HashMap<>(3);
            //获取品牌
            List<String> brandList = getAggregationByName(aggregations, "brandAgg");
            filters.put("brand", brandList);
            //获取城市
            List<String> cityList = getAggregationByName(aggregations, "cityAgg");
            filters.put("city", cityList);
            //获取星级
            List<String> starList = getAggregationByName(aggregations, "starAgg");
            filters.put("starName", starList);
            //返回
            return filters;
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }

    }

    /**
     * 聚合
     *
     * @param request SearchRequest
     */
    private void buildAggregations(SearchRequest request)
    {
        //分桶
        request.source().aggregation(AggregationBuilders.terms("brandAgg").field("brand").size(100));
        request.source().aggregation(AggregationBuilders.terms("cityAgg").field("city").size(100));
        request.source().aggregation(AggregationBuilders.terms("starAgg").field("starName").size(100));
    }

    /**
     * 根据聚合结果获取数据
     *
     * @param aggregations Aggregations
     * @param aggName      聚合的名字
     * @return List<String>
     */
    private List<String> getAggregationByName(Aggregations aggregations, String aggName)
    {
        //根据聚合名称，获取聚合结果
        Terms terms = aggregations.get(aggName);
        //获取buckets
        List<? extends Terms.Bucket> buckets = terms.getBuckets();
        //遍历数据
        List<String> list = new ArrayList<>(buckets.size());
        for (Terms.Bucket bucket : buckets)
        {
            String brandName = bucket.getKeyAsString();
            list.add(brandName);
        }
        //返回
        return list;
    }
}
