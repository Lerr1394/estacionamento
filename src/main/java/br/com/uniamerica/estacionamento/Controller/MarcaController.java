package br.com.uniamerica.estacionamento.Controller;

import br.com.uniamerica.estacionamento.Repository.MarcaRepository;
import br.com.uniamerica.estacionamento.Entity.Marca;
import br.com.uniamerica.estacionamento.Service.MarcaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

 @Controller
@RequestMapping({"/api/marca"})
public class MarcaController {


    @Autowired
    private MarcaService marcaService;

    @GetMapping( "/{id}")
    public ResponseEntity<?> findMarcaById(@PathVariable Long id) {

        try{
            Marca marca = marcaService.findMarcaById(id);
            return ResponseEntity.ok().body(marca);
        }
        catch (RuntimeException e){
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }

    }


    @GetMapping
    public ResponseEntity<List<Marca>> findAllMarcas() {
        List<Marca> marcas = marcaService.findAllMarcas();

        return ResponseEntity.ok().body(marcas);
    }



    @GetMapping("/ativos")
    public ResponseEntity<?> findMarcaByAtivo(){
        final List<Marca> marcas = marcaService.findAllMarcasAtivas();

        return ResponseEntity.ok().body(marcas);
    }



/*

    @PostMapping
    public ResponseEntity<?> cadastrar(@RequestBody final Marca marca){
        try{
            this.marcaService.cadastroMarca(marca);
            return ResponseEntity.ok("Registro cadastrado com sucesso");
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
*/

    @PostMapping
    public  ResponseEntity<?> createMarca(@RequestBody Marca marca){

        try {
            marcaService.cadastrarMarca(marca);
            return ResponseEntity.ok().body("Cadastro de Marca Exitoso");
        }
        catch (RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }



  @PutMapping("/{id}")
    public ResponseEntity<?> modificarMarca(@RequestBody Marca marca, @PathVariable Long id){

        try{
            marcaService.modificarMarca(marca,id);
            return ResponseEntity.ok("Marca modificada com sucesso");
        }
        catch(RuntimeException e) {

            return ResponseEntity.badRequest().body(e.getMessage());
      }


    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletarMarca(@PathVariable Long id){

        try {
            marcaService.deletarMarca(id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Marca excluida com sucesso");

        }
        catch (RuntimeException e){

           return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }


    }



    }



/*

    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@PathVariable("id") final Long id, @RequestBody final Marca marcas){
        try{


            this.marcaService.modificarMarca(marcas);
            return ResponseEntity.ok("Registro editado com sucesso");
        }
        catch (RuntimeException e){
            return ResponseEntity.internalServerError().body("Error " + e.getMessage());
        }
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable final Long id) {
        try {

            this.marcaService.deletarMarca(id);
            return ResponseEntity.ok("Registro excluído com sucesso");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Erro ao excluir registro: " + e.getMessage());
        }

        */










