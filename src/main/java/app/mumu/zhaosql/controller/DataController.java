package app.mumu.zhaosql.controller;

import app.mumu.zhaosql.exception.BadRequestException;
import app.mumu.zhaosql.model.dto.CommodityDTO;
import app.mumu.zhaosql.model.entity.*;
import app.mumu.zhaosql.model.param.OrderParam;
import app.mumu.zhaosql.model.support.BaseResponse;
import app.mumu.zhaosql.service.DataService;
import app.mumu.zhaosql.service.OrderService;
import app.mumu.zhaosql.util.Fun;
import app.mumu.zhaosql.util.ServiceUtils;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/data")
@CrossOrigin
public class DataController {

    private final OrderService orderService;
    private final DataService dataService;

    public DataController(OrderService orderService, DataService dataService) {
        this.orderService = orderService;
        this.dataService = dataService;
    }

    @GetMapping("select/{tableName}")
    public Page pageBy(@PathVariable(name = "tableName") String tableName, @RequestParam(value = "page", defaultValue = "0") Integer page) {
        return dataService.pageBy(tableName, ServiceUtils.page(page));
    }

    @PostMapping("search/{tableName}")
    public Page search(@PathVariable(name = "tableName") String tableName, @RequestBody String json) {
        return dataService.search(tableName, json);
    }

//    @PostMapping("search/id/{tableName}")
//    public Page searchId(@PathVariable(name = "tableName") String tableName, @RequestBody String json) {
//        return dataService.search(tableName, json);
//    }

    @PostMapping("insert/{tableName}")
    public BaseResponse insert(@PathVariable(name = "tableName") String tableName, @RequestBody String json) {
        return dataService.execute(tableName, json, (s, map) -> dataService.insert(s, map));
    }

    @PutMapping("update/{tableName}")
    public BaseResponse update(@PathVariable(name = "tableName") String tableName, @RequestBody String json) {
        return dataService.execute(tableName, json, (s, map) -> dataService.update(s, map));
    }

    @PostMapping("delete/{tableName}")
    public BaseResponse delete(@PathVariable(name = "tableName") String tableName, @RequestBody String json) {
        return dataService.delete(tableName, json);
    }

    @PostMapping("order/insert")
    public BaseResponse insert(@RequestBody OrderParam orderParam) {
        return orderService.insert(orderParam);
    }

    @PostMapping("order/select")
    public Page pageOfOrder(@RequestBody OrderParam orderParam) {
        return orderService.select(orderParam);
    }

    @PostMapping("order/detail")
    public List orderDetail(@RequestParam Long orderId) {
        return orderService.orderDetail(orderId);
    }

    @GetMapping("order/stat")
    public Map stat() {
        return orderService.stat();
    }

}
