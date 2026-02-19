package com.example.carparking.allocation.application.usecases;

import com.example.carparking.allocation.domain.Slot;
import com.example.carparking.allocation.infrastructure.percistence.SlotRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Testes unitários para listagem e busca de vagas.
 * Valida a recuperação de dados paginados e busca individual.
 */
@ExtendWith(MockitoExtension.class)
class ListSlotUseCaseTest {

    @Mock
    private SlotRepository repository;

    @InjectMocks
    private ListSlotUseCase useCase;

    @Test
    @DisplayName("Deve retornar todas as vagas de forma paginada")
    void getAllSlots_success() {
        // Arrange
        Pageable pageable = Pageable.unpaged();
        Slot slot = Slot.create("A1");
        Page<Slot> page = new PageImpl<>(Collections.singletonList(slot));
        when(repository.findAll(pageable)).thenReturn(page);

        // Act
        Page<Slot> result = useCase.getAllSlots(pageable);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        verify(repository, times(1)).findAll(pageable);
    }

    @Test
    @DisplayName("Deve filtrar e retornar apenas as vagas disponíveis")
    void getAvailableSlots_success() {
        // Arrange
        Pageable pageable = Pageable.unpaged();
        Slot slot = Slot.create("A1");
        Page<Slot> page = new PageImpl<>(Collections.singletonList(slot));
        when(repository.findAllByAvailableTrue(pageable)).thenReturn(page);

        // Act
        Page<Slot> result = useCase.getAvailableSlots(pageable);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        verify(repository, times(1)).findAllByAvailableTrue(pageable);
    }

    @Test
    @DisplayName("Deve encontrar uma vaga específica pelo seu código")
    void findBySlotCode_success() {
        // Arrange
        String slotCode = "A1";
        Slot slot = Slot.create(slotCode);
        when(repository.findBySlotCode(slotCode)).thenReturn(Optional.of(slot));

        // Act
        Slot result = useCase.findBySlotCode(slotCode);

        // Assert
        assertNotNull(result);
        assertEquals(slotCode, result.getSlotCode());
    }

    @Test
    @DisplayName("Deve lançar erro quando o código da vaga não for encontrado")
    void findBySlotCode_notFound() {
        // Arrange
        String slotCode = "A1";
        when(repository.findBySlotCode(slotCode)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> useCase.findBySlotCode(slotCode));
    }
}
