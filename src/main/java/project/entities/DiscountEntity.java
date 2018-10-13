package project.entities;

import javax.persistence.*;
import java.util.Set;

/**
 * @author: pis
 * @description: 优惠套餐
 * @date: create in 14:28 2018/10/12
 */
@Entity
@Table(name = "discount", schema = "businessDB")
public class DiscountEntity {
    private Integer id;//套餐id
    private Double price;//月功能费
    private Integer call_count;//通话时长/分钟
    private Integer message_count;//短信数/条
    private Double local_flow;//本地流量/M
    private Double internal_flow;//国内流量/M
    private Set<UserEntity> userEntities;//当前拥有该套餐的用户
    private String name;//名称
    @Column(name = "discount_name", nullable = false,length = 200)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "price", nullable = false, precision = 0)
    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Column(name = "call_count", nullable = true)
    public Integer getCall_count() {
        return call_count;
    }

    public void setCall_count(Integer call_count) {
        this.call_count = call_count;
    }

    @Column(name = "message_count", nullable = true)
    public Integer getMessage_count() {
        return message_count;
    }

    public void setMessage_count(Integer message_count) {
        this.message_count = message_count;
    }

    @Column(name = "local_flow", nullable = true, precision = 0)
    public Double getLocal_flow() {
        return local_flow;
    }

    public void setLocal_flow(Double local_flow) {
        this.local_flow = local_flow;
    }

    @Column(name = "internal_flow", nullable = true, precision = 0)
    public Double getInternal_flow() {
        return internal_flow;
    }

    public void setInternal_flow(Double internal_flow) {
        this.internal_flow = internal_flow;
    }

    @ManyToMany(mappedBy = "discountEntities", cascade = CascadeType.MERGE,fetch = FetchType.EAGER)
    public Set<UserEntity> getUserEntities() {
        return userEntities;
    }

    public void setUserEntities(Set<UserEntity> userEntities) {
        this.userEntities = userEntities;
    }
}
