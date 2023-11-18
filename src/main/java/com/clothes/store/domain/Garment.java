package com.clothes.store.domain;
import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import org.hibernate.annotations.GenericGenerator;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// JPA Configuration
@Entity
@Table(name="garment")

// Lombok Configuration
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Garment implements Serializable {

	@Serial
	private static final long serialVersionUID = -8359168975855133954L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	@Column(name = "prd_id")
	private Long id;
	
	@Column(name = "prd_description", nullable = false)
	private String description;
	
	@Column(name = "prd_garment_type")
	@Enumerated(EnumType.STRING)
	private GarmentType type;
	
	@Column(name = "prd_base_price")
	private BigDecimal basePrice;
	
	public BigDecimal getFinalPrice() {
		return basePrice;
	}
}
