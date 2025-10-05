
package com.edu.eci.arep.ArepTaller6SecureApplicationDesign.Controller;

import com.edu.eci.arep.ArepTaller6SecureApplicationDesign.Model.DTO.PropertiesDto;
import com.edu.eci.arep.ArepTaller6SecureApplicationDesign.Model.Properties;
import com.edu.eci.arep.ArepTaller6SecureApplicationDesign.Service.PropertiesService;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
/**
 *
 * @author Santiago
 */
@RestController
@RequestMapping("/api/properties")
public class PropertiesController {
    
    @Autowired
    private PropertiesService propertiesService;
    
    @PostMapping("/")
    public Properties createProperties(@RequestBody PropertiesDto propertyDto){
        return propertiesService.createProperties(propertyDto);
    }
    
    @GetMapping("/")
    public List<Properties> findProperties(){
        return propertiesService.getProperties();
    }
    
    @GetMapping("/{id}")
    public Optional<Properties> findPropertiesById(@PathVariable long id){
        return propertiesService.getPropertiesById(id);
    }
    
    @PutMapping("/{id}")
    public Properties updateProperties(@PathVariable long id,@RequestBody PropertiesDto propertyDto){
        return propertiesService.updateProperties(id, propertyDto);
    }
    
    @DeleteMapping("/{id}")
    public void deleteProperties(@PathVariable long id){
        propertiesService.deleteProperties(id);
    }
}
