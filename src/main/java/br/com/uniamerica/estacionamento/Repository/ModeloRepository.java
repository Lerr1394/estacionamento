package br.com.uniamerica.estacionamento.Repository;

import br.com.uniamerica.estacionamento.Entity.Marca;
import br.com.uniamerica.estacionamento.Entity.Modelo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ModeloRepository extends JpaRepository<Modelo, Long> {



    List<Modelo> findByAtivo(boolean ativo);


}