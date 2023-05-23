package br.com.uniamerica.estacionamento.Controller;

import br.com.uniamerica.estacionamento.Repository.ConfiguracaoRepository;
import br.com.uniamerica.estacionamento.Entity.Configuracao;
import br.com.uniamerica.estacionamento.Service.ConfiguracaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/configuracao")
public class ConfiguracaoController {

    @Autowired
    private ConfiguracaoRepository configuracaoRepository;
    @Autowired
    ConfiguracaoService configuracaoService;



    @GetMapping
    public ResponseEntity<List<Configuracao>> findAllConfig(){

        List<Configuracao> configuracoes = configuracaoService.findAllConf();
       return ResponseEntity.ok(configuracoes);

    }

    @GetMapping("/ativos")
    public ResponseEntity<List<Configuracao>> findByAtivos(){

        List<Configuracao> configuracoes = configuracaoService.findByAtivos();
        return ResponseEntity.ok(configuracoes);
    }



    @GetMapping("/{id}")
    public ResponseEntity<?> findByIdPath(@PathVariable("id") final Long id) {
        
        try {
            Configuracao configuracao = configuracaoService.findConfById(id);
            return ResponseEntity.ok().body(configuracao);
        }
        catch (RuntimeException e){
            
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
        
    }

    @PostMapping
    public ResponseEntity<?> cadastrar(@RequestBody final Configuracao configuracao){
        try{
            configuracaoService.cadastrarConfiguracao(configuracao);
            return ResponseEntity.ok("Configuração cadastrada com sucesso");
        }
        catch (RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@PathVariable("id") final Long id, @RequestBody final Configuracao configuracao){
        try{
            final Configuracao configuracaoBanco = this.configuracaoRepository.findById(id).orElse(null);

            if(configuracaoBanco == null || !configuracaoBanco.getId().equals(configuracao.getId()))
            {
                throw new RuntimeException("Não foi possível identificar o registro informado");
            }

            this.configuracaoRepository.save(configuracao);
            return ResponseEntity.ok("Registro editado com sucesso");
        }
        catch (DataIntegrityViolationException e){
            return ResponseEntity.internalServerError().body("Error " + e.getCause().getCause().getMessage());
        }
        catch (RuntimeException e){
            return ResponseEntity.internalServerError().body("Error " + e.getMessage());
        }
    }


}
