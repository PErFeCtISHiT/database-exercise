package project.entities;

import javax.persistence.*;
import java.sql.Date;
import java.util.Set;

/**
 * @author: pis
 * @description: 月账单
 * @date: create in 13:41 2018/10/13
 */
@Entity
@Table(name = "bill", schema = "businessDB")
public class BillEntity {
    private Integer id;//账单id
    private UserEntity userEntity;//用户
    private Date bill_date;//账单日期
    private Integer call_month;//当月通话时长
    private Integer message_month;//当月短信数
    private Double local_flow_month;//当月使用的本地流量
    private Double internal_flow_month;//当月使用的全国流量
    private Double call_cost;//通话资费
    private Double message_cost;//信息资费
    private Double local_flow_cost;//本地流量资费
    private Double internal_flow_cost;//全国流量资费

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @ManyToOne()
    @JoinColumn(name = "user_id")
    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    @Basic
    @Column(name = "bill_date", nullable = false)
    public Date getBill_date() {
        return bill_date;
    }

    public void setBill_date(Date bill_date) {
        this.bill_date = bill_date;
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
    @Column(name = "local_flow_month", nullable = true)
    public Double getLocal_flow_month() {
        return local_flow_month;
    }

    public void setLocal_flow_month(Double local_flow_month) {
        this.local_flow_month = local_flow_month;
    }
    @Column(name = "internal_flow_month", nullable = true)
    public Double getInternal_flow_month() {
        return internal_flow_month;
    }

    public void setInternal_flow_month(Double internal_flow_month) {
        this.internal_flow_month = internal_flow_month;
    }
    @Column(name = "call_cost", nullable = true)
    public Double getCall_cost() {
        return call_cost;
    }

    public void setCall_cost(Double call_cost) {
        this.call_cost = call_cost;
    }
    @Column(name = "message_cost", nullable = true)
    public Double getMessage_cost() {
        return message_cost;
    }

    public void setMessage_cost(Double message_cost) {
        this.message_cost = message_cost;
    }
    @Column(name = "local_flow_cost", nullable = true)
    public Double getLocal_flow_cost() {
        return local_flow_cost;
    }

    public void setLocal_flow_cost(Double local_flow_cost) {
        this.local_flow_cost = local_flow_cost;
    }
    @Column(name = "internal_flow_cost", nullable = true)
    public Double getInternal_flow_cost() {
        return internal_flow_cost;
    }

    public void setInternal_flow_cost(Double internal_flow_cost) {
        this.internal_flow_cost = internal_flow_cost;
    }

}
