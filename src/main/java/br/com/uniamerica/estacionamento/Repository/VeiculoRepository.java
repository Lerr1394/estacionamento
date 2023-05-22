package br.com.uniamerica.estacionamento.Repository;

import br.com.uniamerica.estacionamento.Entity.Modelo;
import br.com.uniamerica.estacionamento.Entity.Veiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VeiculoRepository extends JpaRepository<Veiculo, Long> {
    public List<Veiculo> findByAtivo(boolean ativo);

    public Veiculo findByPlaca(String placa);

}