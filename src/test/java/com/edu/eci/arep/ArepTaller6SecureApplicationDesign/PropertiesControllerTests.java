package com.edu.eci.arep.ArepTaller6SecureApplicationDesign;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.edu.eci.arep.ArepTaller6SecureApplicationDesign.Controller.PropertiesController;
import com.edu.eci.arep.ArepTaller6SecureApplicationDesign.Model.DTO.PropertiesDto;
import com.edu.eci.arep.ArepTaller6SecureApplicationDesign.Model.Properties;
import com.edu.eci.arep.ArepTaller6SecureApplicationDesign.Service.PropertiesService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
class PropertiesControllerTest {

    @InjectMocks
    private PropertiesController controller;

    @Mock
    private PropertiesService service;

    @Test
    void testCreateProperties() {
        PropertiesDto dto = new PropertiesDto("Calle 123", 250000.0, 85.5, "Apartamento nuevo");
        Properties expected = new Properties(1L, "Calle 123", 250000.0, 85.5, "Apartamento nuevo");

        when(service.createProperties(dto)).thenReturn(expected);
        Properties result = controller.createProperties(dto);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Calle 123", result.getAddress());
        assertEquals(250000.0, result.getPrice());
        assertEquals(85.5, result.getSize());
        assertEquals("Apartamento nuevo", result.getDescription());
        verify(service, times(1)).createProperties(dto);
    }

    @Test
    void testFindProperties() {
        List<Properties> expectedList = Arrays.asList(
                new Properties(1L, "Calle 10", 200000.0, 60.0, "Casa pequeña"),
                new Properties(2L, "Carrera 45", 500000.0, 120.0, "Casa grande")
        );

        when(service.getProperties()).thenReturn(expectedList);
        List<Properties> result = controller.findProperties();

        assertEquals(2, result.size());
        verify(service, times(1)).getProperties();
    }

    @Test
    void testFindPropertiesById() {
        Properties expected = new Properties(3L, "Avenida 5", 300000.0, 90.0, "Apartamento moderno");

        when(service.getPropertiesById(3L)).thenReturn(Optional.of(expected));
        Optional<Properties> result = controller.findPropertiesById(3L);

        assertTrue(result.isPresent());
        assertEquals("Avenida 5", result.get().getAddress());
        verify(service, times(1)).getPropertiesById(3L);
    }

    @Test
    void testUpdateProperties() {
        PropertiesDto dto = new PropertiesDto("Diagonal 12", 400000.0, 100.0, "Casa remodelada");
        Properties expected = new Properties(4L, "Diagonal 12", 400000.0, 100.0, "Casa remodelada");

        when(service.updateProperties(4L, dto)).thenReturn(expected);
        Properties result = controller.updateProperties(4L, dto);

        assertNotNull(result);
        assertEquals(4L, result.getId());
        assertEquals("Diagonal 12", result.getAddress());
        verify(service, times(1)).updateProperties(4L, dto);
    }

    @Test
    void testDeleteProperties() {
        long id = 5L;
        when(service.deleteProperties(id)).thenReturn(ResponseEntity.noContent().build());
        controller.deleteProperties(id);
        verify(service, times(1)).deleteProperties(id);
    }
}
