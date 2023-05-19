package br.com.uniamerica.estacionamento.Controller;
import br.com.uniamerica.estacionamento.Repository.CondutorRepository;
import br.com.uniamerica.estacionamento.Entity.Condutor;
import br.com.uniamerica.estacionamento.Service.CondutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Controller
@RequestMapping(value = "/api/condutor")
public class CondutorController {


    @Autowired
    private  CondutorService condutorService;


    @GetMapping("/{id}")
    public ResponseEntity<?> findCondutorById(@PathVariable("id")  Long id) {
         Condutor condutor = condutorService.findCondById(id);

        return ResponseEntity.ok(condutor);
    }



    @GetMapping
    public ResponseEntity<List<Condutor>> findAllCondutores() {
        List<Condutor> condutores = condutorService.findAllCondutores();
        return ResponseEntity.ok(condutores);
    }



    @GetMapping("/ativos")
    public ResponseEntity<List<Condutor>> buscarCondutoresAtivos() {
        List<Condutor> condutoresAtivos = this.condutorService.findAllCondAtivos();
        return ResponseEntity.ok(condutoresAtivos);
    }



    @PostMapping
    public ResponseEntity<Condutor> cadastrarCond(@RequestBody  Condutor condutor){

        condutor = condutorService.cadastroCondutor(condutor);


        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(condutor.getId()).toUri();
        return ResponseEntity.created(uri).body(condutor);



        /*
        try{
            this.condutorService.cadastroCondutor(condutor);
            return ResponseEntity.ok("Registro cadastrado com sucesso");
        }
        catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (DataIntegrityViolationException e){
            return ResponseEntity.internalServerError().body("Error" + e.getCause().getCause().getMessage());
        }*/



    }



    @PutMapping("/{id}")
    public ResponseEntity<?> editarCondutor(@PathVariable("id")  Long id, @RequestBody  Condutor condutor){

        condutor = condutorService.modificarCondutor(condutor,id);

        return  ResponseEntity.noContent().build();






        /*
        try{
            this.condutorService.modificarCondutor(condutor);
            return ResponseEntity.ok("Registro editado com sucesso");

        }catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (DataIntegrityViolationException e){
            return ResponseEntity.internalServerError().body("Error " + e.getCause().getCause().getMessage());
        }
        catch (RuntimeException e){
            return ResponseEntity.internalServerError().body("Error " + e.getMessage());
        }

         */
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletarCondutor(@PathVariable  Long id) {

        condutorService.deletarCondutor(id);

        return ResponseEntity.noContent().build();

        /*
        try {
            this.condutorService.deletarCondutor(id);
            return ResponseEntity.ok("Condutor excluído com sucesso");
        }catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (DataIntegrityViolationException e){
            return ResponseEntity.internalServerError().body("Error " + e.getCause().getCause().getMessage());
        } catch (RuntimeException e){
            return ResponseEntity.internalServerError().body("Error " + e.getMessage());
        }


         */
    }



}


