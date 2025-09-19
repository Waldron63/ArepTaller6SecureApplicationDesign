/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.edu.eci.arep.ArepTaller5CRUDSystem.Model;

import jakarta.persistence.*;
import lombok.*;

/**
 *
 * @author Santiago
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Builder
@Table(name = "property")
public class Properties {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id", nullable = false, updatable = false)
    private Long id;
    @Column(name="address")
    private String address;
    @Column(name="price")
    private Double price;
    @Column(name="size")
    private Double size;
    @Column(name="description")
    private String description;
    
    @Override
    public String toString() {
        return String.format(
                "Property[id=%d, address='%s', price='%s', size='%s', description='%s']",
                id, address, price, size, description);
    }
}
