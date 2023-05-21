package br.com.uniamerica.estacionamento.Controller;
import br.com.uniamerica.estacionamento.Repository.CondutorRepository;
import br.com.uniamerica.estacionamento.Entity.Condutor;
import br.com.uniamerica.estacionamento.Service.CondutorService;
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
@RequestMapping(value = "/api/condutor")
public class CondutorController {


    @Autowired
    private  CondutorService condutorService;



    @GetMapping
    public ResponseEntity<List<Condutor>> findAllCondutores() {
        List<Condutor> condutores = condutorService.findAllCondutores();
        return ResponseEntity.ok(condutores);
    }



    @GetMapping("/ativos")
    public ResponseEntity<List<Condutor>> buscarCondutoresAtivos() {
        List<Condutor> condutoresAtivos = condutorService.findAllCondAtivos();
        return ResponseEntity.ok(condutoresAtivos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findCondutorById(@PathVariable("id")  Long id) {

        try {
            Condutor condutor = condutorService.findCondById(id);
            return ResponseEntity.ok(condutor);
        }
        catch (RuntimeException e){

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }

    }



    @PostMapping
    public ResponseEntity<?> cadastrarCondutor(@RequestBody  Condutor condutor){

        try {

            condutorService.cadastrarCondutor(condutor);
            return ResponseEntity.status(HttpStatus.CREATED).body("Condutor cadastrado com sucesso");

        }

        catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }


    }



    @PutMapping("/{id}")
    public ResponseEntity<?> ModificarCondutor(@PathVariable("id")  Long id, @RequestBody  Condutor condutor) {

        try {
            condutorService.modificarCondutor(condutor, id);
            return ResponseEntity.status(HttpStatus.OK).body("Condutor modificado com sucesso");
        } catch (RuntimeException e) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());

        }
    }




    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletarCondutor(@PathVariable  Long id) {

        try {
            condutorService.deletarCondutor(id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Condutor excluido com sucesso");
        }
        catch (RuntimeException e){

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }


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


