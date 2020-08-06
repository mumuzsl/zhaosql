package app.mumu.zhaosql.service;

import app.mumu.zhaosql.model.dto.CommodityDTO;
import app.mumu.zhaosql.model.dto.OrderDetailDTO;
import app.mumu.zhaosql.model.entity.Order;
import app.mumu.zhaosql.model.entity.OrderDetail;
import app.mumu.zhaosql.model.param.OrderParam;
import app.mumu.zhaosql.model.support.BaseResponse;
import app.mumu.zhaosql.repository.OrderDetailRepository;
import app.mumu.zhaosql.repository.OrderRepository;
import app.mumu.zhaosql.util.ServiceUtils;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class OrderService {

    private final OrderDetailRepository orderDetailRepository;
    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository, OrderDetailRepository orderDetailRepository) {
        this.orderDetailRepository = orderDetailRepository;
        this.orderRepository = orderRepository;
    }

    public BaseResponse insert(OrderParam orderParam) {
        Order order = orderParam.getOrder();
        Order save = orderRepository.save(order);

        AtomicReference<Float> totalPrice = new AtomicReference<>((float) 0);

        List<OrderDetailDTO> orderDetailDTOS = orderParam.getOds();
        orderDetailDTOS.forEach(orderDetailDTO -> {
            CommodityDTO commodity = orderDetailDTO.getCommodity();
            Integer num = orderDetailDTO.getNum();
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setCode(commodity.getCode());
            orderDetail.setOrderId(save.getId());
            orderDetail.setPrice(commodity.getSellingPrice());
            orderDetail.setSum(num * commodity.getSellingPrice());
            orderDetail.setNum(num);
            orderDetailRepository.save(orderDetail);

            totalPrice.updateAndGet(v -> v + num * commodity.getSellingPrice());
        });

        save.setTotalPrice(totalPrice.get());
        orderRepository.save(save);

        return BaseResponse.ok("order insert succeed");
    }

    public Page pageBy(Integer page) {
        return orderRepository.findAll(PageRequest.of(page, 10));
    }

    public List orderDetail(Long orderId) {
        System.out.println(orderId);
//        Optional<OrderDetail> byOrderId = orderDetailRepository.findByOrderId(orderId);
        return orderDetailRepository.findAllByOrderId(orderId);
    }

    public Page orderSearch(Order order, Integer page) {
        ExampleMatcher exampleMatcher = ServiceUtils.buildContainsExampleMatcher(ExampleMatcher.matching(), "name");
        Example<Order> of = Example.of(order, exampleMatcher);
        return orderRepository.findAll(of, PageRequest.of(ServiceUtils.page2(page), 10));
    }

    public Page select(OrderParam orderParam) {
        Order order = orderParam.getOrder();
        return order == null ? pageBy(orderParam.getPage()) : orderSearch(order, orderParam.getPage());
    }

    public Map stat() {
        return orderRepository.getMaxMin();
    }
}
