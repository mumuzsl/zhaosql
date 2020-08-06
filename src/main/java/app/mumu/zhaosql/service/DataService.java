package app.mumu.zhaosql.service;

import app.mumu.zhaosql.exception.BadRequestException;
import app.mumu.zhaosql.exception.NotTableException;
import app.mumu.zhaosql.model.dto.CommodityDTO;
import app.mumu.zhaosql.model.entity.*;
import app.mumu.zhaosql.model.param.SearchParam;
import app.mumu.zhaosql.model.support.BaseResponse;
import app.mumu.zhaosql.repository.*;
import app.mumu.zhaosql.util.BeanUtils;
import app.mumu.zhaosql.util.Fun;
import app.mumu.zhaosql.util.ServiceUtils;
import com.alibaba.fastjson.JSONObject;
import org.springframework.data.domain.*;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.*;

@Service
public class DataService {

    private static final String[] KEYS = {"id", "entity"};

    private final CommodityRepository commodityRepository;
    private final BrandRepository brandRepository;
    private final ClassifyRepository classifyRepository;
    //    private final OrderDetailRepository orderDetailRepository;
//    private final OrderRepository orderRepository;
    private final SupplierRepository supplierRepository;
    private final HashMap<String, Object> tableMap;

    public DataService(CommodityRepository commodityRepository,
                       BrandRepository brandRepository,
                       ClassifyRepository classifyRepository,
                       OrderDetailRepository orderDetailRepository,
                       OrderRepository orderRepository,
                       SupplierRepository supplierRepository,
                       HashMap<String, Object> tableMap) {
        this.commodityRepository = commodityRepository;
        this.brandRepository = brandRepository;
        this.classifyRepository = classifyRepository;
//        this.orderDetailRepository = orderDetailRepository;
//        this.orderRepository = orderRepository;
        this.supplierRepository = supplierRepository;
        this.tableMap = tableMap;

        tableMap.put("commodity", commodityRepository);
        tableMap.put("brand", brandRepository);
        tableMap.put("classify", classifyRepository);
        tableMap.put("supplier", supplierRepository);
//        tableMap.put("order", orderRepository);
//        tableMap.put("order_detail", orderDetailRepository);
    }

    private BaseRepository base(String tableName) {
        if (!tableMap.containsKey(tableName)) {
            throw new BadRequestException("没有这个表");
        }

        return (BaseRepository) tableMap.get(tableName);
    }

    int DEFAULT_SIZE = 5;

    public Page<CommodityDTO> convertToCommodityDTO(Page<Commodity> commodityPage) {
        return commodityPage.map(commodity -> {
            CommodityDTO commodityDTO = new CommodityDTO().convertFrom(commodity);

            commodityDTO.setBrandName(brandRepository.selectName(commodity.getBrandId()));

            commodityDTO.setSupplierName(supplierRepository.selectName(commodity.getSupplierId()));

            commodityDTO.setClassifyName(classifyRepository.selectName(commodity.getClassifyId()));

            return commodityDTO;
        });
    }

    public List select(String tableName) {
        if (!tableMap.containsKey(tableName)) {
            throw new BadRequestException("没有这个表");
        }
        BaseRepository baseRepository = (BaseRepository) tableMap.get(tableName);
        return baseRepository.findAll();
    }

    public List select(String tableName, Integer start, Integer len) {
        if (!tableMap.containsKey(tableName)) {
            throw new BadRequestException("没有这个表");
        }

        Iterable<Integer> iterable = () -> new Iterator<Integer>() {
            int n = start;

            @Override
            public boolean hasNext() {
                return n <= len;
            }

            @Override
            public Integer next() {
                return n++;
            }
        };

        BaseRepository baseRepository = (BaseRepository) tableMap.get(tableName);
        return baseRepository.findAllById(iterable);
    }

    public Page pageBy(String tableName, Integer page) {
        return pageBy(tableName, PageRequest.of(page, DEFAULT_SIZE));
    }

    private Page pageBy(String tableName, Pageable pageable) {
        Page all = base(tableName).findAll(pageable);
        return all;
//        return tableName.equals("commodity") ? convertToCommodityDTO(all) : all;
    }

    public BaseResponse insert(String tableName, Map map) {
        base(tableName).save(map.get(KEYS[1]));
        return BaseResponse.ok("insert succeed");
    }

    public Page search(String tableName, String json) {
        SearchParam searchParam = JSONObject.parseObject(json, SearchParam.class);
        String value = searchParam.getValue();
        Map map = parseDataObjoct(tableName, value);
        Object o = map.get(KEYS[1]);
//        String field = searchParam.getField();
        ExampleMatcher exampleMatcher = ServiceUtils.buildContainsExampleMatcher("id", "name");
        if (tableName.equals("commodity"))
            exampleMatcher = ServiceUtils.buildContainsExampleMatcher(exampleMatcher, "code");
        Example<Object> of = Example.of(o, exampleMatcher);
        return base(tableName).findAll(of, PageRequest.of(searchParam.getPage(), DEFAULT_SIZE));
    }

    public BaseResponse update(String tableName, Map map) {
        Object o = map.get(KEYS[0]);
        if (o == null)
            throw new BadRequestException("没有id");
        Object o1 = map.get(KEYS[1]);
        BaseRepository base = base(tableName);
        Object byId = base.findById(o).get();
        BeanUtils.updateProperties(o1, byId);
        base.save(byId);
        return BaseResponse.ok("update succeed");
    }

    public BaseResponse delete(String tableName, String json) {
        List list = JSONObject.parseObject(json, List.class);
        BaseRepository base = base(tableName);
        for (Object o : list) {
            base.deleteById(o);
        }
        return BaseResponse.ok("delete succeed");
    }

    public BaseEntity find(String tableName, BaseEntity baseEntity) {
        switch (tableName) {
            case "commodity":
                Commodity commodity = commodityRepository.findById(((Commodity) baseEntity).getId()).get();
                BeanUtils.updateProperties(commodity, baseEntity);
                commodityRepository.save(commodity);
                return null;//(CommodityRepository) base(tableName)).existsByCode(((Commodity) baseEntity).getCode());
            case "brand":
                return brandRepository.findById(((Brand) baseEntity).getId()).get();//(BrandRepository) base(tableName)).existsById(((Brand) baseEntity).getId());
            case "supplier":
                return supplierRepository.findById(((Supplier) baseEntity).getId()).get();//(SupplierRepository) base(tableName)).existsById(((Supplier) baseEntity).getId());
            case "classify":
                return classifyRepository.findById(((Classify) baseEntity).getId()).get();//(ClassifyRepository) base(tableName)).existsById(((Classify) baseEntity).getId());
            default:
                throw new NotTableException();
        }
    }

    public Map parseDataObjoct(String tableName, String json) {
        switch (tableName) {
            case "commodity":
                Commodity commodity = JSONObject.parseObject(json, Commodity.class);
                return ServiceUtils.convertToMap(KEYS, commodity.getId(), commodity);
            case "brand":
                Brand brand = JSONObject.parseObject(json, Brand.class);
                return ServiceUtils.convertToMap(KEYS, brand.getId(), brand);
            case "supplier":
                Supplier supplier = JSONObject.parseObject(json, Supplier.class);
                return ServiceUtils.convertToMap(KEYS, supplier.getId(), supplier);
            case "classify":
                Classify classify = JSONObject.parseObject(json, Classify.class);
                return ServiceUtils.convertToMap(KEYS, classify.getId(), classify);
            case "order":
                Order order = JSONObject.parseObject(json, Order.class);
                return ServiceUtils.convertToMap(KEYS, order.getId(), order);
            default:
                throw new NotTableException();
        }
    }

//    public BaseEntity parseDataObjoct(String tableName, String json) {
//        switch (tableName) {
//            case "commodity":
//                return JSONObject.parseObject(json, Commodity.class);
//            case "brand":
//                return JSONObject.parseObject(json, Brand.class);
//            case "supplier":
//                return JSONObject.parseObject(json, Supplier.class);
//            case "classify":
//                return JSONObject.parseObject(json, Classify.class);
//            default:
//                throw new NotTableException();
//        }
//    }

    public BaseResponse execute(String table, String json, Fun<String, Map, BaseResponse> fun) {
        return fun.fun(table, parseDataObjoct(table, json));
    }
}
