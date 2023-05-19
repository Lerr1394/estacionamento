package br.com.uniamerica.estacionamento.Controller;
import br.com.uniamerica.estacionamento.Entity.Modelo;
import br.com.uniamerica.estacionamento.Service.ModeloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Controller
@RequestMapping(value = "/api/modelo")
public class ModeloController {

    @Autowired
    private ModeloService modeloService;



    @GetMapping("/{id}")
    public ResponseEntity<?> findModeloById(@PathVariable("id")  Long id){
         Modelo modelo = modeloService.findModeloById (id);

        return ResponseEntity.ok().body(modelo);
    }

    @GetMapping
    public ResponseEntity<List<Modelo>> findAllModelos() {
        List<Modelo> modelos = modeloService.findAllModelos();
        return ResponseEntity.ok().body(modelos);
    }


    @GetMapping("/ativos")
    public ResponseEntity<List<Modelo>> findModelosAtivos() {
        List<Modelo> modelosAtivos = modeloService.findAllModelosAtivos();
        return ResponseEntity.ok().body(modelosAtivos);
    }






    @PostMapping
    public ResponseEntity<Modelo> cadastrarModelo(@RequestBody  Modelo modelo){
        modelo = modeloService.cadastrarModelo(modelo);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(modelo.getId()).toUri();
        return ResponseEntity.created(uri).body(modelo);

        /*
        try{
            this.modeloService.cadastroModelo(modelo);
            return ResponseEntity.ok("Registro cadastrado com sucesso");
        }
        catch (DataIntegrityViolationException e){
            return ResponseEntity.internalServerError().body("Error" + e.getCause().getCause().getMessage());
        }*/


    }

    @PutMapping("/{id}")
    public ResponseEntity<?> edicao(@PathVariable("id")  Long id, @RequestBody  Modelo modelo){

        modelo = modeloService.modificarModelo(modelo,id);

        return ResponseEntity.ok(modelo);

        /*
        try{
            final Modelo modeloBanco = this.modeloService.findModeloById(id);

            if(modeloBanco == null || !modeloBanco.getId().equals(modelo.getId()))
            {
                throw new RuntimeException("Não foi possível identificar o registro informado");
            }

            this.modeloService.modificarModelo(modelo);
            return ResponseEntity.ok("Registro editado com sucesso");
        }
        catch (DataIntegrityViolationException e){
            return ResponseEntity.internalServerError().body("Error " + e.getCause().getCause().getMessage());
        }
        catch (RuntimeException e){
            return ResponseEntity.internalServerError().body("Error " + e.getMessage());
        }

         */
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletarModelo(@PathVariable  Long id) {

        modeloService.deletarModelo(id);

        return ResponseEntity.noContent().build();

        /*
        try {
            this.modeloService.deletarModelo(id);
            return ResponseEntity.ok("Modelo excluído com sucesso");
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
