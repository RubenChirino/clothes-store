package com.clothes.store.domain;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

// JPA Config
@Entity
@Inheritance(strategy=InheritanceType.JOINED)
@DiscriminatorColumn(name="sale_type")
@Table(name="sale")
// Lombok Config
@NoArgsConstructor
@AllArgsConstructor
@Data
@SuperBuilder
public abstract class Sale implements Serializable {
    @Serial
    private static final long serialVersionUID = 4377003933780707501L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(name = "sale_id")
    private Long id;

    @ManyToOne(targetEntity = Client.class, cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinColumn(name="sale_cli_id", referencedColumnName="cli_id", nullable = false)
    private Client client;

    @Column(name = "sale_date")
    @Temporal(TemporalType.DATE)
    private Date date;

    @OneToMany(mappedBy="sale", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER, orphanRemoval = true)
    @JsonManagedReference
    private List<Item> items;

    public abstract Double withCharge(Double basePrice);

    public String getSocialReason() {
        return client.getSocialReason();
    }

    public BigDecimal getBrutePrice() {
        Double suma = items.stream()
                .collect(Collectors.summingDouble(item -> item.price().doubleValue()));
        return new BigDecimal(suma).setScale(2, RoundingMode.UP);
    }

    // Implementation of Template Method
    public BigDecimal finalPrice() {
        Double suma = items.stream()
                .collect(Collectors.summingDouble(item -> withCharge(item.price().doubleValue())));
        return new BigDecimal(suma).setScale(2, RoundingMode.UP);
    }

    public String getFinalPriceString() {
        return finalPrice().toString();
    }

    public boolean isDate(Date date) {
        return (this.date.compareTo(date) == 0) ? true : false;
    }


    public void addItem(Item item) {
        if (this.items == null) {
            this.items = new ArrayList<Item>();
        }
        this.items.add(item);
    }

}
