/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.edu.eci.arep.ArepTaller5CRUDSystem.Repository;

import com.edu.eci.arep.ArepTaller5CRUDSystem.Model.Properties;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Santiago
 */
@Repository
public interface PropertiesRepository extends CrudRepository<Properties, Long> {

}
