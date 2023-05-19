package br.com.uniamerica.estacionamento.Repository;

import br.com.uniamerica.estacionamento.Entity.Condutor;
import br.com.uniamerica.estacionamento.Entity.Movimentacao;
import br.com.uniamerica.estacionamento.Entity.Veiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovimentacaoRepository extends JpaRepository<Movimentacao, Long> {

    boolean existsByCondutor(Condutor condutor);


    List<Movimentacao> findBySaidaIsNull();

    boolean existsByveiculo(Veiculo veiculo);

    List<Movimentacao> findByAtivo(boolean b);

    boolean existsByVeiculoAndSaidaIsNull(Veiculo veiculo);
}