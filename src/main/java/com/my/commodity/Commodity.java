package com.my.commodity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by marcin on 07.01.16.
 */
@Data
@Entity
@Table(name="COMMODITY")
public class Commodity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", updatable = false, nullable = false)
    private Long id;

    @Column(nullable = false)
    private String name;

}
