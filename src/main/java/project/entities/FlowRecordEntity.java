package project.entities;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * @author: pis
 * @description: good good study
 * @date: create in 11:26 2018/10/23
 */
@Entity
@Table(name = "flow_record", schema = "businessDB")
public class FlowRecordEntity {
    private Integer id;//流量记录id
    private UserEntity userEntity;//所属用户
    private Double local_flow;//本地流量
    private Double internal_flow;//国内流量
    private Double local_flow_cost;//本地流量花费
    private Double internal_flow_cost;//国内流量花费
    private Timestamp flow_date;//账单日期

    @ManyToOne()
    @JoinColumn(name = "user_id")
    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    @Column(name = "local_flow", nullable = false)
    public Double getLocal_flow() {
        return local_flow;
    }

    public void setLocal_flow(Double local_flow) {
        this.local_flow = local_flow;
    }

    @Column(name = "internal_flow", nullable = false)
    public Double getInternal_flow() {
        return internal_flow;
    }

    public void setInternal_flow(Double internal_flow) {
        this.internal_flow = internal_flow;
    }

    public Double getLocal_flow_cost() {
        return local_flow_cost;
    }

    @Column(name = "local_flow_cost", nullable = false)
    public void setLocal_flow_cost(Double local_flow_cost) {
        this.local_flow_cost = local_flow_cost;
    }

    public Double getInternal_flow_cost() {
        return internal_flow_cost;
    }

    @Column(name = "internal_flow_cost", nullable = false)
    public void setInternal_flow_cost(Double internal_flow_cost) {
        this.internal_flow_cost = internal_flow_cost;
    }

    public Timestamp getFlow_date() {
        return flow_date;
    }

    @Column(name = "flow_date", nullable = false)
    public void setFlow_date(Timestamp flow_date) {
        this.flow_date = flow_date;
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
}
