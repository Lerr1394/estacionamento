package br.com.uniamerica.estacionamento.Controller;

import br.com.uniamerica.estacionamento.Entity.Movimentacao;
import br.com.uniamerica.estacionamento.Service.MovimentaçaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequestMapping("/api/movimentacao")
public class MovimentacaoController {

    @Autowired
    private MovimentaçaoService movimentaçaoService;

    @GetMapping("/{id}")
    public ResponseEntity<?> findByIdPath(@PathVariable("id") final Long id){
        final Movimentacao movimentacao = this.movimentaçaoService.findById(id);
        return ResponseEntity.ok(movimentacao);
    }

    @GetMapping("/list")
    public ResponseEntity<?> findall(){
        final List<Movimentacao> movimentacao = this.movimentaçaoService.findAll();

        return ResponseEntity.ok(movimentacao);    }
        @GetMapping("/abertos")
    public ResponseEntity<List<Movimentacao>> findBySaida() {
        final List<Movimentacao> movimentacaos = this.movimentaçaoService.findBySaidaIsNull();

        return ResponseEntity.ok(movimentacaos);
    }

    @PostMapping
    public ResponseEntity<?> editar(@RequestParam final Movimentacao movimentacaos){
        try{
            this.movimentaçaoService.cadastrar(movimentacaos);
            return ResponseEntity.ok("Registro cadastrado com sucesso");
        }
        catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (DataIntegrityViolationException e){
            return ResponseEntity.internalServerError().body("Error" + e.getCause().getCause().getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@PathVariable("id") final Long id, @RequestBody final Movimentacao movimentacaos){
        try{
            final Movimentacao movimentacaosBanco = this.movimentaçaoService.findById(id);

            if(movimentacaosBanco == null || !movimentacaosBanco.getId().equals(movimentacaos.getId()))
            {
                throw new RuntimeException("Não foi possível identificar o registro informado");
            }

            this.movimentaçaoService.Update(movimentacaos);
            return ResponseEntity.ok("Registro editado com sucesso");
        }
        catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (DataIntegrityViolationException e){
            return ResponseEntity.internalServerError().body("Error" + e.getCause().getCause().getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable final Long id) {
        try {
            this.movimentaçaoService.delete(id);
            return ResponseEntity.ok("veiculo excluído com sucesso");
        }catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (DataIntegrityViolationException e){
            return ResponseEntity.internalServerError().body("Error " + e.getCause().getCause().getMessage());
        } catch (RuntimeException e){
            return ResponseEntity.internalServerError().body("Error " + e.getMessage());
        }
    }
}
