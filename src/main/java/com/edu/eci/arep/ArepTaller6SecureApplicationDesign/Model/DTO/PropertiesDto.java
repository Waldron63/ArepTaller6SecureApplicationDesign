/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.edu.eci.arep.ArepTaller6SecureApplicationDesign.Model.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author Santiago
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class PropertiesDto {

    private String address;
    private Double price;
    private Double size;
    private String description;
}
