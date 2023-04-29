package br.com.uniamerica.estacionamento.Repository;

import br.com.uniamerica.estacionamento.entity.Condutor;
import br.com.uniamerica.estacionamento.entity.Modelo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ModeloRepository extends JpaRepository<Modelo, Long> {
    List<Condutor> findByAtivo(boolean b);

//    ResponseEntity<?> finByAtivoTrue();
}