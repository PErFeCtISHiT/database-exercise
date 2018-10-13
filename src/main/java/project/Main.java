package project;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import project.entities.DiscountEntity;
import project.entities.OrderEntity;
import project.entities.UserEntity;
import project.service.DiscountService;
import project.service.OrderService;
import project.service.UserService;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author: pis
 * @description: boot class
 * @date: create in 13:44 2018/10/12
 */
@SpringBootApplication
@EntityScan(basePackages = "project.entities")
@EnableJpaRepositories(basePackages = "project.dao")
public class Main implements CommandLineRunner {
    private static final Logger log = LoggerFactory.getLogger(Main.class);
    @Resource
    private UserService userService;
    @Resource
    private OrderService orderService;
    @Resource
    private DiscountService discountService;

    public static void main(String[] args) {
        //spring boot启动后会运行run方法中的操作，并通过log的方式输出到控制台
        SpringApplication.run(Main.class, args);
    }

    @Override
    public void run(String... strings) {
        UserEntity userEntity = (UserEntity) userService.findByID(1);
        assert userEntity != null;
        log.info("用户id: " + userEntity.getId());
        selectOrder(userEntity);//套餐记录
        selectDiscount(userEntity);//套餐余量


        DiscountEntity discountEntity = (DiscountEntity) discountService.findByID(1);
        log.info("给用户 " + userEntity.getId() + " 订购套餐 " + discountEntity.getName());
        orderDiscount(userEntity, discountEntity);//订购套餐
        discountEntity = (DiscountEntity) discountService.findByID(2);
        log.info("给用户 " + userEntity.getId() + " 退订套餐 " + discountEntity.getName());
        cancelDiscount(userEntity, discountEntity, 1);//退订套餐（立即生效）
    }

    /**
     * @param userEntity     退订的用户
     * @param discountEntity 套餐
     * @param type           0代表立即生效，1代表下个月生效
     */
    private void cancelDiscount(UserEntity userEntity, DiscountEntity discountEntity, int type) {
        Set<DiscountEntity> discountEntities = userEntity.getDiscountEntities();
        for (DiscountEntity discountEntity1 : discountEntities) {
            if (discountEntity1.getId().equals(discountEntity.getId())) {
                discountEntities.remove(discountEntity1);
                break;
            }
        }
        if (type == 0) {//立即更新
            if (!userService.modify(userEntity)) {
                log.info("退订套餐失败");
            }
            log.info("退订完成,立即生效");
        } else {//下个月更新
            startTimer(userEntity);
        }
    }

    private void startTimer(UserEntity userEntity) {
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                if (!userService.modify(userEntity)) {
                    log.info("退订套餐失败");
                }
            }
        };
        Timer timer = new Timer();
        Date date = new Date();
        Date monthIncDate = new Date(date.getYear(),(date.getMonth()+1) % 12,date.getDate(),
                date.getHours(),date.getMinutes(),date.getSeconds());
        timer.schedule(task,monthIncDate);
        log.info("退订完成，将于 " + monthIncDate + " 生效");
    }


    /**
     * 给用户订购套餐，会生成历史记录及当前记录
     *
     * @param userEntity     订购的用户
     * @param discountEntity 套餐
     */
    private void orderDiscount(UserEntity userEntity, DiscountEntity discountEntity) {
        userEntity.getDiscountEntities().add(discountEntity);
        if (!userService.modify(userEntity)) {
            log.error("订购套餐失败");
            return;
        }
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setUser(userEntity);
        orderEntity.setDiscount(discountEntity);
        orderEntity.setOrd_date(new java.sql.Date(new java.util.Date().getTime()));
        if (!orderService.modify(orderEntity)) {
            log.error("订购套餐失败");
            return;
        }
        log.info("订购完成");
    }


    /**
     * 查找用户当前套餐余量,通过计算套餐总量减去当月消费得到
     *
     * @param userEntity 用户实体
     */
    private void selectDiscount(UserEntity userEntity) {
        Set<DiscountEntity> discountEntities = userEntity.getDiscountEntities();
        Integer callCount = 0;//通话时长
        Integer messageCount = 0;//短信数
        Double localFlow = 0.0;//本地流量
        Double internalFlow = 0.0;//国内流量
        for (DiscountEntity discountEntity : discountEntities) {
            if (discountEntity.getCall_count() != null)
                callCount += discountEntity.getCall_count();
            if (discountEntity.getMessage_count() != null)
                messageCount += discountEntity.getMessage_count();
            if (discountEntity.getLocal_flow() != null)
                localFlow += discountEntity.getLocal_flow();
            if (discountEntity.getInternal_flow() != null)
                internalFlow += discountEntity.getInternal_flow();
        }
        callCount -= userEntity.getCall_month();
        log.info("套餐余量:");
        if (callCount < 0)
            log.info("通话套餐余量: 0");
        else
            log.info("通话套餐余量: " + callCount + "分钟");
        messageCount -= userEntity.getMessage_month();
        if (messageCount < 0)
            log.info("短信套餐余量: 0分钟");
        else
            log.info("短信套餐余量: " + messageCount + "条");
        localFlow -= userEntity.getLocal_flow_month();
        if (localFlow < 0)
            log.info("本地套餐余量: 0M");
        else
            log.info("本地套餐余量: " + localFlow + "M");
        internalFlow -= userEntity.getInternal_flow_month();
        if (internalFlow < 0)
            log.info("国内套餐余量: 0M");
        else
            log.info("国内套餐余量: " + internalFlow + "M");
    }


    /**
     * 查找用户历史订购套餐
     *
     * @param userEntity 用户实体
     */
    private void selectOrder(UserEntity userEntity) {
        log.info("他的套餐历史记录:");
        Set<OrderEntity> orderEntities = userEntity.getOrderEntities();
        for (OrderEntity orderEntity : orderEntities) {
            DiscountEntity discountEntity = orderEntity.getDiscount();
            log.info("套餐类型: " + discountEntity.getName() + " 订购日期: " + orderEntity.getOrd_date());
        }
    }
}
