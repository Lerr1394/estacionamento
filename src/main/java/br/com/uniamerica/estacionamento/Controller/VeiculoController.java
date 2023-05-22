package br.com.uniamerica.estacionamento.Controller;


import br.com.uniamerica.estacionamento.Entity.Veiculo;
import br.com.uniamerica.estacionamento.Service.VeiculoService;
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
@RequestMapping("/api/veiculo")
public class VeiculoController {

    @Autowired
    private VeiculoService veiculoService;





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


    @GetMapping("/{id}")
    public ResponseEntity<?> findVeiculoById(@PathVariable("id") Long id){

        try {
            Veiculo veiculo = veiculoService.findVeiculoById(id);
            return ResponseEntity.ok().body(veiculo);
        }
        catch (RuntimeException e){

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }

    }

    @PostMapping
    public ResponseEntity<?> cadastrarVeiculo(@RequestBody  Veiculo veiculo){

        try {
            veiculoService.cadastroVeiculo(veiculo);
            return ResponseEntity.status(HttpStatus.OK).body("Veiculo cadstrado com sucesso");
        }

        catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }


    }






    @PutMapping("/{id}")
    public ResponseEntity<?> editarVeiculo(@PathVariable("id")  Long id, @RequestBody   Veiculo veiculo){

    try {
        veiculoService.modificarVeiculo(veiculo,id);
        return ResponseEntity.status(HttpStatus.OK).body("Veiculo modificado com sucesso");
    }

    catch (RuntimeException e){

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletarVeiculo(@PathVariable  Long id) {
        try {

            veiculoService.deletarVeiculo(id);
            return ResponseEntity.status(HttpStatus.OK).body("Veiculo excluido com sucesso");

        }
        catch (RuntimeException e){

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }

    }


}
