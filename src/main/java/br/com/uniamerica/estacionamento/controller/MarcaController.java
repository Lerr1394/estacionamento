package br.com.uniamerica.estacionamento.controller;

import br.com.uniamerica.estacionamento.Repository.MarcaRepository;
import br.com.uniamerica.estacionamento.entity.Condutor;
import br.com.uniamerica.estacionamento.entity.Marca;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/Marca")
public class MarcaController {

    @Autowired
    private MarcaRepository marcaRepository;

    @GetMapping("/{id}")
    public ResponseEntity<?> findByIdPath(@PathVariable("id") final Long id) {
        final Marca marcas = this.marcaRepository.findById(id).orElse(null);

        return marcas == null
                ? ResponseEntity.badRequest().body("Não encontrado.")
                : ResponseEntity.ok(marcas);
    }

    @GetMapping("/lista")
    public ResponseEntity<?> findAll() {
        final List<Marca> marcas = this.marcaRepository.findAll();

        return ResponseEntity.ok(marcas);
    }

    @GetMapping("/ativos")
    public ResponseEntity<?> findByAtivo(){
        final List<Condutor> condutor = this.marcaRepository.findByAtivo(true);

        return ResponseEntity.ok(condutor);
    }
    @PostMapping
    public ResponseEntity<?> cadastrar(@RequestBody final Marca marcas){
        try{
            this.marcaRepository.save(marcas);
            return ResponseEntity.ok("Cadastro efetuado com sucesso");
        }
        catch (DataIntegrityViolationException e){
            return ResponseEntity.internalServerError().body("Error" + e.getCause().getCause().getMessage());
        }
    }

    @PutMapping
    public ResponseEntity<?> editar(@RequestParam("id") final Long id, @RequestBody final Marca marcas){
        try{
            final Marca marcaBanco = this.marcaRepository.findById(id).orElse(null);

            if(marcaBanco == null || !marcaBanco.getId().equals(marcas.getId()))
            {
                throw new RuntimeException("Não foi identificado o registro indicado");
            }

            this.marcaRepository.save(marcas);
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
            final Marca marcaBanco = this.marcaRepository.findById(id).orElse(null);

            if (marcaBanco == null) {
                return ResponseEntity.notFound().build();
            }

            this.marcaRepository.delete(marcaBanco);
            return ResponseEntity.ok("Exclusão  realizada com sucesso");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Erro ao excluir registro: " + e.getMessage());
        }
    }













}
