package br.com.uniamerica.estacionamento.Repository;

import br.com.uniamerica.estacionamento.Entity.Condutor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CondutorRepository extends JpaRepository<Condutor, Long> {

     List<Condutor> findByAtivo(boolean ativo);

     Optional<Condutor> findByCpf(String cpf);

     Optional<Condutor> findBytelefone(String telefone);

}