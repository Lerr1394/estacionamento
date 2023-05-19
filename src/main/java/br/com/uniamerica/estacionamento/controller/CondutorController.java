package br.com.uniamerica.estacionamento.Controller;
import br.com.uniamerica.estacionamento.Repository.CondutorRepository;
import br.com.uniamerica.estacionamento.Entity.Condutor;
import br.com.uniamerica.estacionamento.Service.CondutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(value = "/api/condutor")
public class CondutorController {


    @Autowired
    private  CondutorService condutorService;

    @Autowired
    private CondutorRepository condutorRepository;


    @GetMapping("/{id}")
    public ResponseEntity<?> findByIdPath(@PathVariable("id") final Long id) {
        final Condutor condutor = this.condutorService.findCondById(id);

        return ResponseEntity.ok(condutor);
    }



    @GetMapping("/lista")
    public ResponseEntity<List<Condutor>> findAllCondutores() {
        List<Condutor> condutores = this.condutorService.findAllCondutores();
        return ResponseEntity.ok(condutores);
    }



    @GetMapping("/ativos")
    public ResponseEntity<List<Condutor>> buscarCondutoresAtivos() {
        List<Condutor> condutoresAtivos = this.condutorService.findAllCondAtivos();
        return ResponseEntity.ok(condutoresAtivos);
    }



    @PostMapping
    public ResponseEntity<?> cadastrar(@RequestBody final Condutor condutor){
        try{
            this.condutorService.cadastroCondutor(condutor);
            return ResponseEntity.ok("Registro cadastrado com sucesso");
        }
        catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (DataIntegrityViolationException e){
            return ResponseEntity.internalServerError().body("Error" + e.getCause().getCause().getMessage());
        }
    }



    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@PathVariable("id") final Long id, @RequestBody final Condutor condutor){
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
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable final Long id) {
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
    }



}


