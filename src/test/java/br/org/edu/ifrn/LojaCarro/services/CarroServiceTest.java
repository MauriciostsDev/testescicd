package br.org.edu.ifrn.LojaCarro.services;

import br.org.edu.ifrn.LojaCarro.controllers.CarroInvalidoException;
import br.org.edu.ifrn.LojaCarro.model.Carro;
import br.org.edu.ifrn.LojaCarro.repository.CarroRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.Year;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CarroServiceTest {

    @Mock
    private CarroRepository carroRepository;

    @InjectMocks
    private CarroService carroService;

    @Test
    void saveDeveDelegarParaRepositoryERetornarCarroSalvo() {
        Carro carro = criarCarro(1L, "Gol", 2020);

        when(carroRepository.save(carro)).thenReturn(carro);

        Carro resultado = carroService.save(carro);

        assertSame(carro, resultado);
        verify(carroRepository).save(carro);
        verifyNoMoreInteractions(carroRepository);
    }

    @Test
    void updateDeveDelegarParaRepositoryERetornarCarroAtualizado() {
        Carro carro = criarCarro(2L, "Onix", 2022);

        when(carroRepository.save(carro)).thenReturn(carro);

        Carro resultado = carroService.update(carro);

        assertSame(carro, resultado);
        verify(carroRepository).save(carro);
        verifyNoMoreInteractions(carroRepository);
    }

    @Test
    void saveDeveLancarCarroInvalidoExceptionParaModeloX() {
        Carro carro = criarCarro(1L, "x", 2022);

        CarroInvalidoException ex = assertThrows(CarroInvalidoException.class, () -> carroService.save(carro));
        assertEquals("Modelo 'x' não pode ser utilizado", ex.getMessage());
        verifyNoMoreInteractions(carroRepository);
    }

    @Test
    void saveDeveLancarCarroInvalidoExceptionParaModeloY() {
        Carro carro = criarCarro(1L, "y", 2022);

        CarroInvalidoException ex = assertThrows(CarroInvalidoException.class, () -> carroService.save(carro));
        assertEquals("Modelo 'y' não pode ser utilizado", ex.getMessage());
        verifyNoMoreInteractions(carroRepository);
    }

    @Test
    void saveDeveLancarCarroInvalidoExceptionParaPrecoNulo() {
        Carro carro = criarCarro(1L, "Gol", 2020);
        carro.setPreco(null);

        CarroInvalidoException ex = assertThrows(CarroInvalidoException.class, () -> carroService.save(carro));
        assertEquals("O preço é obrigatório", ex.getMessage());
        verifyNoMoreInteractions(carroRepository);
    }

    @Test
    void saveDeveLancarCarroInvalidoExceptionParaAnoFuturo() {
        int anoFuturo = Year.now().getValue() + 1;
        Carro carro = criarCarro(1L, "Gol", anoFuturo);
        carro.setPreco(BigDecimal.valueOf(50000));

        CarroInvalidoException ex = assertThrows(CarroInvalidoException.class, () -> carroService.save(carro));
        assertEquals("Ano deve ser entre 1886 e " + Year.now().getValue(), ex.getMessage());
        verifyNoMoreInteractions(carroRepository);
    }

    @Test
    void saveDeveLancarCarroInvalidoExceptionParaModeloCurto() {
        Carro carro = criarCarro(1L, "Go", 2020);
        carro.setPreco(BigDecimal.valueOf(50000));

        CarroInvalidoException ex = assertThrows(CarroInvalidoException.class, () -> carroService.save(carro));
        assertEquals("O modelo deve ter ao menos 3 caracteres", ex.getMessage());
        verifyNoMoreInteractions(carroRepository);
    }

    @Test
    void updateDeveLancarCarroInvalidoExceptionParaAnoFuturo() {
        int anoFuturo = Year.now().getValue() + 1;
        Carro carro = criarCarro(1L, "Onix", anoFuturo);
        carro.setPreco(BigDecimal.valueOf(50000));

        CarroInvalidoException ex = assertThrows(CarroInvalidoException.class, () -> carroService.update(carro));
        assertEquals("Ano deve ser entre 1886 e " + Year.now().getValue(), ex.getMessage());
        verifyNoMoreInteractions(carroRepository);
    }

    @Test
    void deleteByIdDeveDelegarParaRepositoryComIdInformado() {
        Long id = 10L;

        carroService.deleteById(id);

        verify(carroRepository, times(1)).deleteById(id);
        verifyNoMoreInteractions(carroRepository);
    }

    @Test
    void findByIdDeveRetornarCarroQuandoEncontrado() {
        Long id = 3L;
        Carro carro = criarCarro(id, "HB20", 2021);

        when(carroRepository.findById(id)).thenReturn(Optional.of(carro));

        Optional<Carro> resultado = carroService.findById(id);

        assertTrue(resultado.isPresent());
        assertSame(carro, resultado.get());
        verify(carroRepository).findById(id);
        verifyNoMoreInteractions(carroRepository);
    }

    @Test
    void findByIdDeveRetornarOptionalVazioQuandoNaoEncontrado() {
        Long id = 99L;

        when(carroRepository.findById(id)).thenReturn(Optional.empty());

        Optional<Carro> resultado = carroService.findById(id);

        assertTrue(resultado.isEmpty());
        verify(carroRepository).findById(id);
        verifyNoMoreInteractions(carroRepository);
    }

    @Test
    void findAllDeveRetornarListaDeCarrosDoRepository() {
        List<Carro> carros = List.of(
                criarCarro(1L, "Gol", 2020),
                criarCarro(2L, "Onix", 2022)
        );

        when(carroRepository.findAll()).thenReturn(carros);

        List<Carro> resultado = carroService.findAll();

        assertEquals(2, resultado.size());
        assertSame(carros, resultado);
        verify(carroRepository).findAll();
        verifyNoMoreInteractions(carroRepository);
    }

    private Carro criarCarro(Long id, String modelo, int ano) {
        Carro carro = new Carro();
        carro.setId(id);
        carro.setModelo(modelo);
        carro.setAno(ano);
        carro.setPreco(BigDecimal.valueOf(10000));
        return carro;
    }
}
