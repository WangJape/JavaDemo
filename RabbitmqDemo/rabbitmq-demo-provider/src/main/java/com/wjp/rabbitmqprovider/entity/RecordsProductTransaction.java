package com.wjp.rabbitmqprovider.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 客户直接购买产品的账目记录单
 * </p>
 *
 * @author Jape
 * @since 2020-02-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class RecordsProductTransaction implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 账目记录主键ID
     */
    private Integer rptId;

    /**
     * 商店主键
     */
    private Integer storeId;

    /**
     * 店铺名称
     */
    private String storeName;

    /**
     * 产品主键
     */
    private Integer productId;

    /**
     * 产品名称
     */
    private String productName;

    /**
     * 交易产品数量
     */
    private Integer productTransactionNumber;

    /**
     * 交易类型 consume：消费；

     */
    private String transactionType;

    /**
     * 交易金额
     */
    private BigDecimal transactionAmount;

    /**
     * 交易时间
     */
    private LocalDateTime transactionTime;


}
