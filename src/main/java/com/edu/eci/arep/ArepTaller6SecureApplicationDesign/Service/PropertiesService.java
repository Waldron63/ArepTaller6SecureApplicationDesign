
package com.edu.eci.arep.ArepTaller6SecureApplicationDesign.Service;

import com.edu.eci.arep.ArepTaller6SecureApplicationDesign.Model.DTO.PropertiesDto;
import com.edu.eci.arep.ArepTaller6SecureApplicationDesign.Model.Properties;
import com.edu.eci.arep.ArepTaller6SecureApplicationDesign.Repository.PropertiesRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 *
 * @author Santiago
 */
@Service
public class PropertiesService {

    @Autowired
    private PropertiesRepository propertyRepository;

    public Properties createProperties(PropertiesDto propertyDto) {
        Properties properties = Properties.builder()
                .address(propertyDto.getAddress())
                .price(propertyDto.getPrice())
                .size(propertyDto.getSize())
                .description(propertyDto.getDescription())
                .build();
        return propertyRepository.save(properties);
    }

    public List<Properties> getProperties() {
        return propertyRepository.findAll();
    }

    public Optional<Properties> getPropertiesById(long id) {
        return propertyRepository.findById(id);
    }

    public Properties updateProperties(long id, PropertiesDto propertyDto) {
        Properties oldProp = propertyRepository.findById(id).get();
        if (!Objects.equals(propertyDto.getAddress(), "")){
            oldProp.setAddress(propertyDto.getAddress());
        }
        if (!(propertyDto.getPrice() < 0)){
            oldProp.setPrice(propertyDto.getPrice());
        }
        if (!(propertyDto.getSize() < 0)){
            oldProp.setSize(propertyDto.getSize());
        }
        if (!Objects.equals(propertyDto.getDescription(), "")){
            oldProp.setDescription(propertyDto.getDescription());
        }
        propertyRepository.save(oldProp);
        return oldProp;
    }

    public ResponseEntity<Void> deleteProperties(long id) {
        propertyRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
