package com.clothes.store.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import java.io.Serializable;

@Entity
@Table(name="client")

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Client implements Serializable {
	
    private static final long serialVersionUID = 6363777413501451503L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(name = "cli_id")
    private Long id;

    @Column(name = "cli_name")
    private String name;

    @Column(name = "cli_last_name")
    private String lastName;

    public String getSocialReason() {
        return name + " " + lastName;
    }
}
