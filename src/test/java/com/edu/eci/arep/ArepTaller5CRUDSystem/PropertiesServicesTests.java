package com.edu.eci.arep.ArepTaller5CRUDSystem;

import com.edu.eci.arep.ArepTaller5CRUDSystem.Model.DTO.PropertiesDto;
import com.edu.eci.arep.ArepTaller5CRUDSystem.Model.Properties;
import com.edu.eci.arep.ArepTaller5CRUDSystem.Repository.PropertiesRepository;
import com.edu.eci.arep.ArepTaller5CRUDSystem.Service.PropertiesService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PropertiesServiceTest {

    @InjectMocks
    private PropertiesService service;

    @Mock
    private PropertiesRepository repository;

    @Test
    void testCreateProperties() {
        PropertiesDto dto = new PropertiesDto("Calle 123", 250000.0, 80.0, "Apartamento");
        Properties saved = new Properties(1L, "Calle 123", 250000.0, 80.0, "Apartamento");

        when(repository.save(any(Properties.class))).thenReturn(saved);
        Properties result = service.createProperties(dto);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Calle 123", result.getAddress());
        verify(repository, times(1)).save(any(Properties.class));
    }

    @Test
    void testGetProperties() {
        List<Properties> expected = Arrays.asList(
                new Properties(1L, "Calle 1", 100000.0, 60.0, "Casa pequeña"),
                new Properties(2L, "Calle 2", 200000.0, 90.0, "Apartamento")
        );

        when(repository.findAll()).thenReturn(expected);
        List<Properties> result = service.getProperties();

        assertEquals(2, result.size());
        verify(repository, times(1)).findAll();
    }

    @Test
    void testGetPropertiesById() {
        Properties property = new Properties(1L, "Calle 10", 300000.0, 100.0, "Casa moderna");

        when(repository.findById(1L)).thenReturn(Optional.of(property));
        Optional<Properties> result = service.getPropertiesById(1L);

        assertTrue(result.isPresent());
        assertEquals("Calle 10", result.get().getAddress());
        verify(repository, times(1)).findById(1L);
    }

    @Test
    void testUpdateProperties() {
        Properties existing = new Properties(1L, "Calle vieja", 100000.0, 50.0, "Vieja");
        PropertiesDto dto = new PropertiesDto("Calle nueva", 200000.0, 70.0, "Renovada");

        when(repository.findById(1L)).thenReturn(Optional.of(existing));
        when(repository.save(any(Properties.class))).thenReturn(existing);
        Properties result = service.updateProperties(1L, dto);

        assertEquals("Calle nueva", result.getAddress());
        assertEquals(200000.0, result.getPrice());
        assertEquals(70.0, result.getSize());
        assertEquals("Renovada", result.getDescription());
        verify(repository, times(1)).save(existing);
    }

    @Test
    void testUpdatePropertiesIgnoresInvalidValues() {
        Properties existing = new Properties(2L, "Calle Original", 150000.0, 60.0, "Descripcion");
        PropertiesDto dto = new PropertiesDto("", -1.0, -1.0, "");

        when(repository.findById(2L)).thenReturn(Optional.of(existing));
        when(repository.save(any(Properties.class))).thenReturn(existing);

        Properties result = service.updateProperties(2L, dto);

        // Valores no cambian porque eran inválidos
        assertEquals("Calle Original", result.getAddress());
        assertEquals(150000.0, result.getPrice());
        assertEquals(60.0, result.getSize());
        assertEquals("Descripcion", result.getDescription());
        verify(repository, times(1)).save(existing);
    }

    @Test
    void testDeleteProperties() {
        long id = 3L;
        doNothing().when(repository).deleteById(id);
        ResponseEntity<Void> response = service.deleteProperties(id);
        assertEquals(204, response.getStatusCodeValue());
        verify(repository, times(1)).deleteById(id);
    }
}

