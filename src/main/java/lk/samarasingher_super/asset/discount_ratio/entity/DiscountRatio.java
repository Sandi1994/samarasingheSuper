package lk.samarasingher_super.asset.discount_ratio.entity;


import com.fasterxml.jackson.annotation.JsonFilter;
import lk.samarasingher_super.asset.common_asset.model.enums.LiveOrDead;
import lk.samarasingher_super.asset.invoice.entity.Invoice;
import lk.samarasingher_super.util.audit.AuditEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.math.BigDecimal;
import java.util.List;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonFilter( "DiscountRatio" )
public class DiscountRatio extends AuditEntity {

    @Column( nullable = false, length = 45 )
    private String name;

    @Column( nullable = false, precision = 10, scale = 2 )
    private BigDecimal amount;

    private LiveOrDead liveOrDead;

    @OneToMany( mappedBy = "discountRatio" )
    private List< Invoice > invoices;

}

