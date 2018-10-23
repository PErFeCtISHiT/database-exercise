package project.entities;

import javax.persistence.*;
import java.util.Set;

/**
 * @author: pis
 * @description: 用户实体，记录了用户的id，当前套餐
 * @date: create in 14:22 2018/10/12
 */
@Entity
@Table(name = "user", schema = "businessDB")
public class UserEntity {
    private Integer id;//用户id
    private Set<DiscountEntity> discountEntities;//用户当前的套餐情况
    private Set<OrderEntity> orderEntities;//用户套餐记录情况
    private Set<BillEntity> billEntities;//用户月账单
    private Integer call_month;//当月目前通话时长
    private Integer message_month;//当月目前短信数
    private Double local_flow_month;//当月目前使用的本地流量
    private Double internal_flow_month;//当月目前使用的全国流量

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_discount", joinColumns = {@JoinColumn(referencedColumnName = "ID")},
            inverseJoinColumns = {@JoinColumn(referencedColumnName = "ID")})
    public Set<DiscountEntity> getDiscountEntities() {
        return discountEntities;
    }

    public void setDiscountEntities(Set<DiscountEntity> discountEntities) {
        this.discountEntities = discountEntities;
    }

    @Column(name = "call_month", nullable = true)
    public Integer getCall_month() {
        return call_month;
    }

    public void setCall_month(Integer call_month) {
        this.call_month = call_month;
    }

    @Column(name = "message_month", nullable = true)
    public Integer getMessage_month() {
        return message_month;
    }

    public void setMessage_month(Integer message_month) {
        this.message_month = message_month;
    }

    @Column(name = "local_flow_month", nullable = true, precision = 0)
    public Double getLocal_flow_month() {
        return local_flow_month;
    }

    public void setLocal_flow_month(Double local_flow_month) {
        this.local_flow_month = local_flow_month;
    }

    @Column(name = "internal_flow_month", nullable = true, precision = 0)
    public Double getInternal_flow_month() {
        return internal_flow_month;
    }

    public void setInternal_flow_month(Double internal_flow_month) {
        this.internal_flow_month = internal_flow_month;
    }

    @OneToMany(mappedBy = "user", cascade = CascadeType.MERGE,fetch = FetchType.LAZY)
    public Set<OrderEntity> getOrderEntities() {
        return orderEntities;
    }

    public void setOrderEntities(Set<OrderEntity> orderEntities) {
        this.orderEntities = orderEntities;
    }

    @OneToMany(mappedBy = "userEntity",fetch = FetchType.LAZY)
    public Set<BillEntity> getBillEntities() {
        return billEntities;
    }

    public void setBillEntities(Set<BillEntity> billEntities) {
        this.billEntities = billEntities;
    }
}
