package br.com.uniamerica.estacionamento.Service;

import br.com.uniamerica.estacionamento.Repository.ConfiguracaoRepository;
import br.com.uniamerica.estacionamento.Repository.MovimentacaoRepository;
import br.com.uniamerica.estacionamento.Entity.Configuracao;
import br.com.uniamerica.estacionamento.Entity.Movimentacao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;


@Service
public class MovimentaçaoService {

    @Autowired
    private ConfiguracaoRepository configuracaoRepository;
    @Autowired
    private MovimentacaoRepository movimentacaoRepository;


    public Movimentacao findById(Long id) {
        Optional<Movimentacao> optionalMovimentacao = this.movimentacaoRepository.findById(id);
        if (optionalMovimentacao.isPresent()) {
            return optionalMovimentacao.get();
        } else {
            throw new RuntimeException("Condutor não encontrado");
        }
    }

    public List<Movimentacao> findAll() {
        return this.movimentacaoRepository.findAll();
    }

    public List<Movimentacao> findBySaidaIsNull(){
        return this.movimentacaoRepository.findBySaidaIsNull();
    }

    @Transactional(rollbackFor = Exception.class)
    public Movimentacao cadastrar(Movimentacao movimentacao){

        Configuracao configuracao = configuracaoRepository.findById(movimentacao.getId())
                .orElseThrow(() -> new IllegalArgumentException("Modelo não encontrado"));
        if (configuracao == null) {
            throw new IllegalStateException("Não há configurações cadastradas.");
        }
        if (movimentacaoRepository.existsByVeiculoAndSaidaIsNull(movimentacao.getVeiculo())) {
            throw new IllegalArgumentException("O veículo já está em movimento");
        }
        if (movimentacao.getEntrada().isAfter(movimentacao.getSaida())) {
            throw new IllegalArgumentException("A data de entrada deve ser antes da data de saída");
        }

        BigDecimal valorHora = configuracao.getValorHora();
        BigDecimal valorMinutoHora = configuracao.getValorMinutoHora();
        BigDecimal valorTotal = BigDecimal.ZERO;
        LocalTime tempo = LocalTime.of(0, 0);

        movimentacao.setValorHora(valorHora);
        movimentacao.setTempo(tempo);

        return this.movimentacaoRepository.save(movimentacao);
    }

    @Transactional(rollbackFor = Exception.class)
    public Movimentacao Update(Movimentacao movimentacao){


        Configuracao configuracao = configuracaoRepository.findById(movimentacao.getId())
                .orElseThrow(() -> new IllegalArgumentException("Modelo não encontrado"));

        BigDecimal valorHora = configuracao.getValorHora();
        BigDecimal valorMinutoHora = configuracao.getValorMinutoHora();
        BigDecimal valorTotal = BigDecimal.ZERO;

        long tempoEmMinutos = ChronoUnit.MINUTES.between(movimentacao.getEntrada().atStartOfDay(), movimentacao.getSaida().atStartOfDay());

        if (tempoEmMinutos > 0) {
            int horas = (int) (tempoEmMinutos / 60);
            int minutos = (int) (tempoEmMinutos % 60);

            BigDecimal valorTotalHoras = BigDecimal.valueOf(horas).multiply(valorHora);
            BigDecimal valorTotalMinutos = BigDecimal.valueOf(minutos).multiply(valorMinutoHora);
            valorTotal = valorTotalHoras.add(valorTotalMinutos);

        }


        LocalDateTime entrada = LocalDateTime.of(movimentacao.getEntrada(), LocalTime.MIDNIGHT);
        LocalDateTime saida = LocalDateTime.of(movimentacao.getSaida(), LocalTime.MIDNIGHT);
        Duration duration = Duration.between(entrada, saida);
        LocalTime tempo = LocalTime.ofSecondOfDay(duration.getSeconds());


        movimentacao.setTempo(tempo);
        movimentacao.setValorTotal(valorTotal);


        return this.movimentacaoRepository.save(movimentacao);
    }

    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        Optional<Movimentacao> optionalMovimentacao = movimentacaoRepository.findById(id);
        if (optionalMovimentacao.isPresent()) {
            Movimentacao movimentacao = optionalMovimentacao.get();
            if (movimentacao.getSaida() == null) {
                movimentacaoRepository.delete(movimentacao);
            } else {
                throw new IllegalArgumentException("Não é possível excluir uma movimentação com data de saída preenchida");
            }
        } else {
            throw new IllegalArgumentException("Movimentação não encontrada");
        }
    }



}
