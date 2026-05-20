
package br.org.edu.ifrn.LojaCarro.services;

import br.org.edu.ifrn.LojaCarro.controllers.CarroInvalidoException;
import br.org.edu.ifrn.LojaCarro.model.Carro;
import br.org.edu.ifrn.LojaCarro.repository.CarroRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Year;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
public class CarroService {

    private static final Logger logger = LoggerFactory.getLogger(CarroService.class);

    @Autowired
    public CarroRepository carroRepository;

    public Carro save(Carro c) {
        validarCarro(c);
        return carroRepository.save(c);
    }

    // Novo método para deletar por ID
    public void deleteById(Long id) {
        carroRepository.deleteById(id);
    }

    // Novo método para pesquisar por ID
    public Optional<Carro> findById(Long id) {
        return carroRepository.findById(id);
    }

    // Novo método para listar todos os carros
    public List<Carro> findAll() {
        return carroRepository.findAll();
    }

    // Método para atualizar (usa o save existente, mas pode ser renomeado se preferir)
    public Carro update(Carro c) {
        validarCarro(c);
        return carroRepository.save(c);  // Retorna o carro salvo para feedback
    }

    private void validarCarro(Carro c) {
        if (c == null) {
            return;
        }

        if (c.getPreco() == null) {
            String mensagem = "O preço é obrigatório";
            logger.warn("Validação de negócio no service falhou: {}", mensagem);
            throw new CarroInvalidoException(mensagem);
        }

        int anoAtual = Year.now().getValue();
        if (c.getAno() <= 1885 || c.getAno() > anoAtual) {
            String mensagem = "Ano deve ser entre 1886 e " + anoAtual;
            logger.warn("Validação de negócio no service falhou: {}", mensagem);
            throw new CarroInvalidoException(mensagem);
        }

        if (c.getModelo() == null) {
            String mensagem = "O modelo é obrigatório";
            logger.warn("Validação de negócio no service falhou: {}", mensagem);
            throw new CarroInvalidoException(mensagem);
        }

        validarModeloProibido(c);

        if (c.getModelo().trim().length() < 3) {
            String mensagem = "O modelo deve ter ao menos 3 caracteres";
            logger.warn("Validação de negócio no service falhou: {}", mensagem);
            throw new CarroInvalidoException(mensagem);
        }
    }

    private void validarModeloProibido(Carro c) {
        String modelo = c.getModelo().trim().toLowerCase(Locale.ROOT);
        if (modelo.equals("x")) {
            String mensagem = "Modelo 'x' não pode ser utilizado";
            logger.warn("Validação de negócio no service falhou: {}", mensagem);
            throw new CarroInvalidoException(mensagem);
        }
        if (modelo.equals("y")) {
            String mensagem = "Modelo 'y' não pode ser utilizado";
            logger.warn("Validação de negócio no service falhou: {}", mensagem);
            throw new CarroInvalidoException(mensagem);
        }
    }
}