package br.com.uniamerica.estacionamento.controller;
import br.com.uniamerica.estacionamento.Repository.ModeloRepository;
import br.com.uniamerica.estacionamento.entity.Condutor;
import br.com.uniamerica.estacionamento.entity.Modelo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(value = "/api/modelo")
public class ModeloController {

    @Autowired
    private ModeloRepository modeloRepository;

    @GetMapping("/{id}")
    public ResponseEntity<?> findByIdPath(@PathVariable("id") final Long id){
        final Modelo modelo = this.modeloRepository.findById(id).orElse(null);

        return modelo == null
                ? ResponseEntity.badRequest().body("Não encontrado.")
                : ResponseEntity.ok(modelo);
    }

    @GetMapping("/lista")
    public ResponseEntity<?> findAll(){
        final  List<Modelo> modelos = this.modeloRepository.findAll();
        return ResponseEntity.ok(modelos);
    }

    @GetMapping("/ativos")
    public ResponseEntity<?> findByAtivo(){
        final List<Condutor> condutor = this.modeloRepository.findByAtivo(true);

        return ResponseEntity.ok(condutor);
    }

    @PostMapping
    public ResponseEntity<?> editar(@RequestParam final Modelo modelo){
        try{
            this.modeloRepository.save(modelo);
            return ResponseEntity.ok("Cadastro efetuado com sucesso");
        }
        catch (DataIntegrityViolationException e){
            return ResponseEntity.internalServerError().body("Error" + e.getCause().getCause().getMessage());
        }
    }

    @PutMapping
    public ResponseEntity<?> editar(@RequestParam("id") final Long id, @RequestBody final Modelo modelo){
        try{
            final Modelo modeloBanco = this.modeloRepository.findById(id).orElse(null);

            if(modeloBanco == null || !modeloBanco.getId().equals(modelo.getId()))
            {
                throw new RuntimeException("Não foi identificado o registro indicado");
            }

            this.modeloRepository.save(modelo);
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
    public ResponseEntity<?> deletar(@PathVariable final Long id) {
        try {
            final Modelo modeloBanco = this.modeloRepository.findById(id).orElse(null);

            if (modeloBanco == null) {
                return ResponseEntity.notFound().build();
            }

            this.modeloRepository.delete(modeloBanco);
            return ResponseEntity.ok("Exclusão  realizada com sucesso");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Erro ao excluir registro: " + e.getMessage());
        }
    }
}
