package br.com.uniamerica.estacionamento.Controller;

import br.com.uniamerica.estacionamento.Repository.MarcaRepository;
import br.com.uniamerica.estacionamento.Entity.Marca;
import br.com.uniamerica.estacionamento.Service.MarcaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController @Controller
@RequestMapping({"/api/marca"})
public class MarcaController {


    @Autowired
    private MarcaService marcaService;

    @GetMapping(path = {"/{id}"})
    public ResponseEntity<?> findMarcaById(@PathVariable Long id) {
         Marca marca = marcaService.findMarcaById(id);

         return ResponseEntity.ok().body(marca);

        /*try{
            return  ResponseEntity.ok(marca);
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }*/

    }


    @GetMapping
    public ResponseEntity<List<Marca>> findAllMarcas() {
        List<Marca> marcas = marcaService.findAllMarcas();

        return ResponseEntity.ok().body(marcas);
    }


/*
    @GetMapping("/ativos")
    public ResponseEntity<?> findByAtivo(){
        final List<Marca> marcas = this.marcaService.findAllMarcasAtivos(true);

        return ResponseEntity.ok(marcas);
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
    public  ResponseEntity<Marca> createMarca(@RequestBody Marca marca){
         marca = marcaService.cadastroMarca(marca);
         URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                 .buildAndExpand(marca.getId()).toUri();
        return ResponseEntity.created(uri).body(marca);

    }



  @PutMapping(path = {"/{id}"})
    public ResponseEntity<?> updateMarca(@RequestBody Marca marca, @PathVariable Long id){

        marca = marcaService.modificarMarca(marca,id);

        return ResponseEntity.noContent().build();

    }


    @DeleteMapping(path = {"/{id}"})
    public ResponseEntity<?> deletar(@PathVariable Long id){

        marcaService.deletarMarca(id);

        return ResponseEntity.noContent().build();

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










