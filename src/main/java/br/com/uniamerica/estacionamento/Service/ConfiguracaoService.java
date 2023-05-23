package br.com.uniamerica.estacionamento.Service;

import br.com.uniamerica.estacionamento.Entity.Configuracao;
import br.com.uniamerica.estacionamento.Repository.ConfiguracaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.awt.image.RescaleOp;
import java.util.List;
import java.util.Optional;

@Service
public class ConfiguracaoService {

    @Autowired
    private ConfiguracaoRepository configuracaoRepository;


    public List<Configuracao> findAllConf(){

        return configuracaoRepository.findAll();
    }


    public List<Configuracao> findByAtivos(){
        return configuracaoRepository.findByAtivo(true);
    }

    @Transactional(rollbackFor = Exception.class)
    public Configuracao findConfById(Long id){

        Optional<Configuracao> configuracaoBD = configuracaoRepository.findById(id);

        Assert.isTrue(!configuracaoBD.isEmpty(),"Configuração não encontrada");

        return configuracaoBD.get();

    }


    @Transactional(rollbackFor = Exception.class)
    public void cadastrarConfiguracao(Configuracao configuracao){

        Optional<Configuracao> configuracaoBD = configuracaoRepository.findById(configuracao.getId());

        Assert.isTrue(configuracaoBD.isEmpty(),"Já existe uma configuração cadastrada com esse ID");
        int total_vagas = 30;
        Assert.isTrue(configuracao.getVagasCarro() <= total_vagas,"Numero máximo de vagas é 30");
        total_vagas -= configuracao.getVagasCarro();
        Assert.isTrue(configuracao.getVagasMoto() <= total_vagas,"Numero máximo de vagas é 30");
        total_vagas -= configuracao.getVagasMoto();
        Assert.isTrue(configuracao.getVagasVan() <= total_vagas,"Numero máximo de vagas é 30");
        total_vagas -= configuracao.getVagasVan();


        configuracaoRepository.save(configuracao);

    }



}
