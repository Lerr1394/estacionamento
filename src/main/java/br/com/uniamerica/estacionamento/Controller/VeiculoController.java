package br.com.uniamerica.estacionamento.Controller;


import br.com.uniamerica.estacionamento.Entity.Veiculo;
import br.com.uniamerica.estacionamento.Service.VeiculoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Controller
@RequestMapping("/api/veiculo")
public class VeiculoController {

    @Autowired
    private VeiculoService veiculoService;

    @GetMapping("/{id}")
    public ResponseEntity<?> findVeiculoById(@PathVariable("id") Long id){
         Veiculo veiculo = veiculoService.findVeiculoById(id);

        return ResponseEntity.ok().body(veiculo);
    }



    @GetMapping
    public ResponseEntity<?> findallVeiculos(){
        final List<Veiculo> veiculos = veiculoService.findAllVeiculos();

        return ResponseEntity.ok().body(veiculos);
    }

    @GetMapping("/ativos")
    public ResponseEntity<List<Veiculo>>findVeiByAtivos(){
        List<Veiculo> veiculosAtivos = veiculoService.findAllVeiculosAtivo();
        return ResponseEntity.ok().body(veiculosAtivos);
    }




    @PostMapping
    public ResponseEntity<Veiculo> cadastrar(@RequestBody  Veiculo veiculo){

        veiculo = veiculoService.cadastroVeiculo(veiculo);

        /*
        try{
            this.veiculoService.cadastroVeiculo(veiculo);
            return ResponseEntity.ok("Registro cadastrado com sucesso");
        }
        catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (DataIntegrityViolationException e){
            return ResponseEntity.internalServerError().body("Error" + e.getCause().getCause().getMessage());
        }

         */


        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(veiculo.getId()).toUri();
        return ResponseEntity.created(uri).body(veiculo);
    }






    @PutMapping("/{id}")
    public ResponseEntity<?> editarVeiculo(@PathVariable("id")  Long id, @RequestBody   Veiculo veiculo){



        /*
        try{
            final Veiculo veiculoBanco = this.veiculoService.findVeiculoById(id);

            if(veiculoBanco == null || !veiculoBanco.getId().equals(veiculo.getId()))
            {
                throw new RuntimeException("Não foi possível identificar o registro informado");
            }

            this.veiculoService.modificarVeiculo(veiculo);
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


        veiculo = veiculoService.modificarVeiculo(veiculo,id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable  Long id) {

        veiculoService.deletarVeiculo(id);

        return ResponseEntity.noContent().build();

        /*
        try {
            this.veiculoService.deletarVeiculo(id);
            return ResponseEntity.ok("veiculo excluído com sucesso");
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
