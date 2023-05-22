package br.com.uniamerica.estacionamento.Service;

import br.com.uniamerica.estacionamento.Entity.*;
import br.com.uniamerica.estacionamento.Repository.ModeloRepository;
import br.com.uniamerica.estacionamento.Repository.MovimentacaoRepository;
import br.com.uniamerica.estacionamento.Repository.VeiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

@Service
public class VeiculoService {

    @Autowired
    private VeiculoRepository veiculoRepository;
    @Autowired
    private ModeloRepository modeloRepository;
    @Autowired
    private MovimentacaoRepository movimentacaoRepository;

    public List<Veiculo> findAllVeiculos(){

        return veiculoRepository.findAll();
    }

    public List<Veiculo> findAllVeiculosAtivo(){

        return veiculoRepository.findByAtivo(true);
    }



    public Veiculo findVeiculoById(Long id){
        Optional<Veiculo> veiculoBD = veiculoRepository.findById(id);

        Assert.isTrue(!veiculoBD.isEmpty(), "Veiculo não encontrado");


        return veiculoBD.get();

    }





    @Transactional(rollbackFor = Exception.class)
    public void cadastroVeiculo(Veiculo veiculo){

        Optional<Veiculo> veiculoBD = veiculoRepository.findById(veiculo.getId());
        Assert.isTrue(veiculoBD.isEmpty(),"Id do veiculo já cadastrado");
        Assert.isNull(veiculoRepository.findByPlaca(veiculo.getPlaca()),"Veiculo já cadastrado com essa placa");
        Assert.isTrue(veiculo.getPlaca().matches("[A-Z]{3}[0-9][A-Z][0-9]{2}") ,"Erro no formato da placa, formato correto AAA0A00 ");
        Assert.notNull(veiculo.getTipo() ,"Precisa um tipo de Veiculo ");
        Assert.notNull(veiculo.getModeloId(),"Precisa ter um modelo asociado");

        veiculoRepository.save(veiculo);

    }


    @Transactional(rollbackFor = Exception.class)
    public Veiculo modificarVeiculo(Veiculo veiculo, Long id){


        Optional<Veiculo> veiculoBD = veiculoRepository.findById(veiculo.getId());
        Assert.isTrue(!veiculoBD.isEmpty(),"Veiculo não cadastrado");
        Assert.isNull(veiculoRepository.findByPlaca(veiculo.getPlaca()),"Veiculo já cadastrado com essa placa");
        Assert.isTrue(veiculo.getPlaca().matches("[A-Z]{3}[0-9][A-Z][0-9]{2}") ,"Erro no formato da placa, formato correto AAA0A00 ");
        Assert.notNull(veiculo.getTipo() ,"Precisa um tipo de Veiculo ");
        Assert.notNull(veiculo.getModeloId(),"Precisa ter um modelo asociado");


        return veiculoRepository.save(veiculo);

    }


    @Transactional(rollbackFor = Exception.class)
    public void deletarVeiculo(Long id) {




        Optional<Veiculo> veiculoBD = veiculoRepository.findById(id);
        Assert.isTrue(!veiculoBD.isEmpty(),"Veiculo não encontrado ");
        veiculoRepository.deleteById(id);

        /*
        Veiculo veiculo = veiculoBD.get();
        Assert.isTrue(!this.movimentacaoRepository.existsByveiculo(veiculo),"Veiculo vinculado a movimentação não pode ser excluido");
        this.veiculoRepository.delete(veiculo);

        */

    }


}
