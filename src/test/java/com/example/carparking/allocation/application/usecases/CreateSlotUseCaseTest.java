package com.example.carparking.allocation.application.usecases;

import com.example.carparking.allocation.domain.Slot;
import com.example.carparking.allocation.infrastructure.percistence.SlotRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Testes unitários para o caso de uso de criação de vagas.
 * Valida se as regras de unicidade de código de vaga estão sendo respeitadas.
 */
@ExtendWith(MockitoExtension.class)
class CreateSlotUseCaseTest {

    @Mock
    private SlotRepository repository;

    @InjectMocks
    private CreateSlotUseCase useCase;

    @Test
    @DisplayName("Deve criar uma nova vaga com sucesso quando o código é único")
    void createSlot_success() {
        // Arrange: Prepara o código da vaga e simula que ele não existe no banco
        String slotCode = "A1";
        when(repository.existsBySlotCode(slotCode)).thenReturn(false);
        when(repository.save(any(Slot.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act: Executa a criação
        Slot createdSlot = useCase.createSlot(slotCode);

        // Assert: Verifica se os dados salvos estão corretos
        assertNotNull(createdSlot);
        assertEquals(slotCode, createdSlot.getSlotCode());
        verify(repository, times(1)).existsBySlotCode(slotCode);
        verify(repository, times(1)).save(any(Slot.class));
    }

    @Test
    @DisplayName("Deve lançar erro ao tentar criar vaga com código já existente")
    void createSlot_alreadyExists() {
        // Arrange: Simula que o código já está em uso
        String slotCode = "A1";
        when(repository.existsBySlotCode(slotCode)).thenReturn(true);

        // Act & Assert: Verifica se a exceção de negócio é lançada
        assertThrows(IllegalArgumentException.class, () -> useCase.createSlot(slotCode));
        verify(repository, times(1)).existsBySlotCode(slotCode);
        verify(repository, never()).save(any(Slot.class));
    }
}
