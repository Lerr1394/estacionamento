package br.com.uniamerica.estacionamento.controller;


import br.com.uniamerica.estacionamento.Repository.CondutorRepository;
import br.com.uniamerica.estacionamento.Repository.MovimentacaoRepository;
import br.com.uniamerica.estacionamento.Repository.VeiculoRepository;
import br.com.uniamerica.estacionamento.entity.Condutor;
import br.com.uniamerica.estacionamento.entity.Veiculo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/api/veiculo")
public class VeiculoController {

    @Autowired
    private VeiculoRepository veiculoRepository;

    @Autowired
    private CondutorRepository condutorRepository;
    @Autowired
    private MovimentacaoRepository movimentacaoRepository;

    @GetMapping("/{id}")
    public ResponseEntity<?> findByIdPath(@PathVariable("id")final Long id){
        final Veiculo veiculo = this.veiculoRepository.findById(id).orElse(null);

        return veiculo == null
                ? ResponseEntity.badRequest().body("Não encontrado.")
                : ResponseEntity.ok(veiculo);
    }

    @GetMapping("/list")
    public ResponseEntity<?> findall(){
        final List<Veiculo> veiculo = this.veiculoRepository.findAll();

        return ResponseEntity.ok(veiculo);
    }

    @GetMapping("/ativos")
    public ResponseEntity<?> findByAtivo(){
        final List<Condutor> condutor = this.veiculoRepository.findByAtivo(true);

        return ResponseEntity.ok(condutor);
    }
    @PostMapping
    public ResponseEntity<?> cadastrar(@RequestBody final Veiculo veiculo){
        try{
            this.veiculoRepository.save(veiculo);
            return ResponseEntity.ok("Cadastro efetuado com sucesso");
        }
        catch (DataIntegrityViolationException e){
            return ResponseEntity.internalServerError().body("Error" + e.getCause().getCause().getMessage());
        }
    }

    @PutMapping
    public ResponseEntity<?> editar(@RequestParam("id") final Long id, @RequestBody final Veiculo veiculo){
        try{
            final Veiculo veiculoBanco = this.veiculoRepository.findById(id).orElse(null);

            if(veiculoBanco == null || !veiculoBanco.getId().equals(veiculo.getId()))
            {
                throw new RuntimeException("Não foi identificado o registro indicado");
            }

            this.veiculoRepository.save(veiculo);
            return ResponseEntity.ok("Modificação realizada com sucesso");
        }
        catch (DataIntegrityViolationException e){
            return ResponseEntity.internalServerError().body("Error " + e.getCause().getCause().getMessage());
        }
        catch (RuntimeException e){
            return ResponseEntity.internalServerError().body("Error " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable final Long id) {
        try {
            Optional<Condutor> optionalCondutor = condutorRepository.findById(id);
            if (optionalCondutor.isPresent()) {
                Condutor condutor = optionalCondutor.get();
                if (movimentacaoRepository.existsByCondutor(condutor)) {
                    condutor.setAtivo(true);
                    condutorRepository.save(condutor);
                } else {
                    condutorRepository.delete(condutor);
                }
                return ResponseEntity.ok("Exclusão  realizada com sucesso");
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (RuntimeException e) {
            return ResponseEntity.internalServerError().body("Erro ao excluir: " + e.getMessage());
        }
    }
}
