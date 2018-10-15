package project;


import org.apache.log4j.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.Transactional;
import project.entities.BillEntity;
import project.entities.DiscountEntity;
import project.entities.OrderEntity;
import project.entities.UserEntity;
import project.service.DiscountService;
import project.service.OrderService;
import project.service.UserService;

import javax.annotation.Resource;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.*;

/**
 * @author: pis
 * @description: boot class
 * @date: create in 13:44 2018/10/12
 */
@SpringBootApplication
@EntityScan(basePackages = "project.entities")
@EnableJpaRepositories(basePackages = "project.dao")
public class Main implements CommandLineRunner {
    public Main(){

        try {
            //将操作日志输出到文件（可选）(application.properties中spring.jpa.show-sql=false
            PrintStream print=new PrintStream("sql.log");
            //System.setOut(print);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    private static final Logger log = Logger.getLogger(Main.class);
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
    @Transactional
    public void run(String... strings) {
        Set<Integer> usersID = new HashSet<>();
        while (usersID.size() < 10){//随机抽取10个用户
            usersID.add((int)(Math.random() * 100000));
        }
        for(Integer id : usersID){
            log.info("开始操作用户 " + id);
            DiscountEntity discountEntity = (DiscountEntity) discountService.findByID(1);
            UserEntity userEntity = (UserEntity) userService.findByID(id);
            queryDiscount(userEntity);
            queryOrder(userEntity);
            orderDiscount(userEntity,discountEntity);
            cancelDiscount(userEntity,discountEntity,0);
            queryCallBill(userEntity);
            queryFlowBill(userEntity);
            queryMonthBill(userEntity);
            log.info("用户 " + id + " 操作结束\n\n");
        }
    }

    /**
     * 查找用户当前套餐余量,通过计算套餐总量减去当月消费得到
     *
     * @param userEntity 用户实体
     */
    private void queryDiscount(UserEntity userEntity) {
        long startTime = System.currentTimeMillis();
        log.info("查询套餐开始");
        Set<DiscountEntity> discountEntities = userEntity.getDiscountEntities();
        int callCount = 0;//通话时长
        int messageCount = 0;//短信数
        double localFlow = 0.0;//本地流量
        double internalFlow = 0.0;//国内流量
        log.info("当前套餐");
        for (DiscountEntity discountEntity : discountEntities) {
            log.info("套餐类型: " + discountEntity.getName());
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
        log.info("查询套餐结束,用时: " + (System.currentTimeMillis() - startTime) + "毫秒\n");
    }


    /**
     * 查找用户历史订购套餐
     *
     * @param userEntity 用户实体
     */
    private void queryOrder(UserEntity userEntity) {
        long startTime = System.currentTimeMillis();
        log.info("查询历史套餐开始");
        log.info("套餐历史记录:");
        Set<OrderEntity> orderEntities = userEntity.getOrderEntities();
        for (OrderEntity orderEntity : orderEntities) {
            DiscountEntity discountEntity = orderEntity.getDiscount();
            log.info("套餐类型: " + discountEntity.getName() + " 订购日期: " + orderEntity.getOrd_date());
        }
        log.info("查询历史套餐结束,用时: " + (System.currentTimeMillis() - startTime) + "毫秒\n");
    }

    /**
     * 订购套餐，会生成历史记录及当前记录
     *
     * @param userEntity     订购的用户
     * @param discountEntity 套餐
     */
    private void orderDiscount(UserEntity userEntity, DiscountEntity discountEntity) {
        long startTime = System.currentTimeMillis();
        log.info("订购套餐开始");
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
        log.info("订购套餐结束,用时: " + (System.currentTimeMillis() - startTime) + "毫秒\n");
    }

    /**
     * 退订套餐
     *
     * @param userEntity     退订的用户
     * @param discountEntity 套餐
     * @param type           0代表立即生效，1代表下个月生效
     */
    private void cancelDiscount(UserEntity userEntity, DiscountEntity discountEntity, int type) {
        long startTime = System.currentTimeMillis();
        log.info("退订套餐开始");
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
        log.info("退订套餐结束,用时: " + (System.currentTimeMillis() - startTime) + "毫秒\n");
    }

    /**
     * 一个延时任务器，推迟任务的执行时间
     *
     * @param userEntity 操作的用户
     */
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
        Date monthIncDate = new Date(date.getYear(), (date.getMonth() + 1) % 12, 1);
        timer.schedule(task, monthIncDate);
        log.info("退订完成，将于 " + monthIncDate + " 生效\n");
    }

    /**
     * 查询某用户通话的资费生成(通话时长为总通话时长，通话资费为套餐外额外产生的通话资费
     *
     * @param userEntity 查询的用户
     */
    private void queryCallBill(UserEntity userEntity) {
        long startTime = System.currentTimeMillis();
        log.info("查询通话资费开始");
        Set<BillEntity> billEntities = userEntity.getBillEntities();
        int callCount = 0;
        double callCost = 0.0;
        for (BillEntity billEntity : billEntities) {
            callCount += billEntity.getCall_month();
            callCost += billEntity.getCall_cost();
        }
        log.info("通话时长: " + callCount + " 分钟" + "套餐外通话资费" + callCost + " 元");
        log.info("查询通话资费结束,用时: " + (System.currentTimeMillis() - startTime) + "毫秒\n");
    }


    /**
     * 查询某用户流量的资费生成(流量为总流量使用情况，流量资费为套餐外额外产生的流量资费
     *
     * @param userEntity 查询的用户
     */
    private void queryFlowBill(UserEntity userEntity) {
        long startTime = System.currentTimeMillis();
        log.info("查询流量资费开始");
        Set<BillEntity> billEntities = userEntity.getBillEntities();
        double localFlowCount = 0.0;
        double internalFlowCount = 0.0;
        double localFlowCost = 0.0;
        double internalFlowCost = 0.0;
        for (BillEntity billEntity : billEntities) {
            localFlowCount += billEntity.getLocal_flow_month();
            localFlowCost += billEntity.getLocal_flow_cost();
            internalFlowCount += billEntity.getInternal_flow_month();
            internalFlowCost += billEntity.getInternal_flow_cost();
        }
        log.info("本地流量: " + localFlowCount + " M " + "本地流量资费" + localFlowCost + " 元");
        log.info("国内流量: " + internalFlowCount + " M " + "国内流量资费" + internalFlowCost + " 元");
        log.info("查询流量资费结束,用时: " + (System.currentTimeMillis() - startTime) + "毫秒\n");
    }

    /**
     * 查询月账单(通话时长，信息数，流量使用，总资费)
     *
     * @param userEntity 查询的用户
     */
    private void queryMonthBill(UserEntity userEntity) {
        long startTime = System.currentTimeMillis();
        log.info("查询月账单开始");
        Set<BillEntity> billEntities = userEntity.getBillEntities();
        int callCount = 0;
        int messageCount = 0;
        double localFlowCount = 0.0;
        double internalFlowCount = 0.0;
        double totalCost = 0.0;
        for (BillEntity billEntity : billEntities) {
            callCount += billEntity.getCall_month();
            messageCount += billEntity.getMessage_month();
            localFlowCount += billEntity.getLocal_flow_month();
            internalFlowCount += billEntity.getInternal_flow_month();
            totalCost += billEntity.getCall_cost() + billEntity.getInternal_flow_cost()
                    + billEntity.getLocal_flow_cost() + billEntity.getMessage_cost();
        }
        log.info("通话时长: " + callCount + " 分钟");
        log.info("短信条数: " + messageCount + " 条");
        log.info("本地流量: " + localFlowCount + " M");
        log.info("国内流量: " + internalFlowCount + " M");
        log.info("总资费: " + totalCost + " 元");
        log.info("查询月账单结束,用时: " + (System.currentTimeMillis() - startTime) + "毫秒\n");
    }

}