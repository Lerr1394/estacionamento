package br.com.uniamerica.estacionamento.Repository;

import br.com.uniamerica.estacionamento.Entity.Marca;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface MarcaRepository extends JpaRepository<Marca, Long> {


    public List<Marca> findMarcaByAtivo(boolean ativo);
    public Marca findByNome(String nome);

}