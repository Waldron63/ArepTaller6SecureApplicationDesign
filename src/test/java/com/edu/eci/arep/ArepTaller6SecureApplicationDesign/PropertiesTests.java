package com.edu.eci.arep.ArepTaller6SecureApplicationDesign;

import com.edu.eci.arep.ArepTaller6SecureApplicationDesign.Model.DTO.PropertiesDto;
import com.edu.eci.arep.ArepTaller6SecureApplicationDesign.Model.Properties;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PropertiesTests {

    @Test
    void testPropertiesDtoConstructorAndGettersSetters() {
        PropertiesDto dto = new PropertiesDto();
        dto.setAddress("Calle 123");
        dto.setPrice(250000.0);
        dto.setSize(85.5);
        dto.setDescription("Apartamento en el centro");

        assertEquals("Calle 123", dto.getAddress());
        assertEquals(250000.0, dto.getPrice());
        assertEquals(85.5, dto.getSize());
        assertEquals("Apartamento en el centro", dto.getDescription());
    }

    @Test
    void testPropertiesDtoAllArgsConstructor() {
        PropertiesDto dto = new PropertiesDto(
                "Carrera 45", 500000.0, 120.0, "Casa grande");

        assertEquals("Carrera 45", dto.getAddress());
        assertEquals(500000.0, dto.getPrice());
        assertEquals(120.0, dto.getSize());
        assertEquals("Casa grande", dto.getDescription());
    }

    @Test
    void testPropertiesBuilder() {
        PropertiesDto dtoBuilder = PropertiesDto.builder()
                .address("Diagonal 12")
                .price(300000.0)
                .size(90.0)
                .description("Apartamento nuevo")
                .build();

        assertEquals("Diagonal 12", dtoBuilder.getAddress());
        assertEquals(300000.0, dtoBuilder.getPrice());
        assertEquals(90.0, dtoBuilder.getSize());
        assertEquals("Apartamento nuevo", dtoBuilder.getDescription());
    }

    @Test
    void testPropertiesEntityConstructorAndGettersSetters() {
        Properties property = new Properties();
        property.setId(1L);
        property.setAddress("Calle 10");
        property.setPrice(150000.0);
        property.setSize(60.0);
        property.setDescription("Casa pequeña");

        assertEquals(1L, property.getId());
        assertEquals("Calle 10", property.getAddress());
        assertEquals(150000.0, property.getPrice());
        assertEquals(60.0, property.getSize());
        assertEquals("Casa pequeña", property.getDescription());
    }

    @Test
    void testPropertiesEntityAllArgsConstructor() {
        Properties property = new Properties(
                2L, "Avenida 5", 200000.0, 70.0, "Apartamento moderno");

        assertEquals(2L, property.getId());
        assertEquals("Avenida 5", property.getAddress());
        assertEquals(200000.0, property.getPrice());
        assertEquals(70.0, property.getSize());
        assertEquals("Apartamento moderno", property.getDescription());
    }

    @Test
    void testPropertiesEntityBuilder() {
        Properties propertyBuilder = Properties.builder()
                .id(3L)
                .address("Carrera 7")
                .price(400000.0)
                .size(100.0)
                .description("Casa con jardín")
                .build();

        assertEquals(3L, propertyBuilder.getId());
        assertEquals("Carrera 7", propertyBuilder.getAddress());
        assertEquals(400000.0, propertyBuilder.getPrice());
        assertEquals(100.0, propertyBuilder.getSize());
        assertEquals("Casa con jardín", propertyBuilder.getDescription());
    }

    @Test
    void testPropertiesToString() {
        Properties property = new Properties(4L, "Calle 50", 600000.0, 150.0, "Mansión lujosa");
        String expected = "Property[id=4, address='Calle 50', price='600000.0', size='150.0', description='Mansión lujosa']";
        assertEquals(expected, property.toString());
    }

    @Test
    void testEqualsAndHashCodeEntity() {
        Properties p1 = new Properties(5L, "Calle 80", 250000.0, 90.0, "Apartamento familiar");
        Properties p2 = new Properties(5L, "Calle 80", 250000.0, 90.0, "Apartamento familiar");

        assertEquals(p1, p2);
        assertEquals(p1.hashCode(), p2.hashCode());
    }

    @Test
    void testEqualsAndHashCode() {
        PropertiesDto p1 = new PropertiesDto("Calle 80", 250000.0, 90.0, "Apartamento familiar");
        PropertiesDto p2 = new PropertiesDto("Calle 80", 250000.0, 90.0, "Apartamento familiar");

        assertEquals(p1, p2);
        assertEquals(p1.hashCode(), p2.hashCode());
    }
}
