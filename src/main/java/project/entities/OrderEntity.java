package project.entities;

import javax.persistence.*;
import java.sql.Date;

/**
 * @author: pis
 * @description: 用户订单
 * @date: create in 14:47 2018/10/12
 */
@Entity
@Table(name = "user_order", schema = "businessDB")
public class OrderEntity {
    private Integer id;//订单id
    private UserEntity user;//用户
    private DiscountEntity discount;//套餐
    private Date ord_date;//订购日期

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id")
    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }


    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "discount_id")
    public DiscountEntity getDiscount() {
        return discount;
    }

    public void setDiscount(DiscountEntity discount) {
        this.discount = discount;
    }

    @Basic
    @Column(name = "ord_date", nullable = false)
    public Date getOrd_date() {
        return ord_date;
    }

    public void setOrd_date(Date ord_date) {
        this.ord_date = ord_date;
    }
}
