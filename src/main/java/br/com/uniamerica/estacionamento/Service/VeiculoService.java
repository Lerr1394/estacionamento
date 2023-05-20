package br.com.uniamerica.estacionamento.Service;

import br.com.uniamerica.estacionamento.Entity.Condutor;
import br.com.uniamerica.estacionamento.Repository.ModeloRepository;
import br.com.uniamerica.estacionamento.Repository.MovimentacaoRepository;
import br.com.uniamerica.estacionamento.Repository.VeiculoRepository;
import br.com.uniamerica.estacionamento.Entity.Modelo;
import br.com.uniamerica.estacionamento.Entity.Veiculo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.time.Year;
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
/*
        Assert.isTrue(!veiculoBD.isEmpty(), "Veiculo não encontrado");

 */
        return veiculoBD.get();

    }





    @Transactional(rollbackFor = Exception.class)
    public Veiculo cadastroVeiculo(Veiculo veiculo){
        /*
        Optional<Veiculo> veiculoBD = veiculoRepository.findById(veiculo.getId());
        Assert.isTrue(veiculoBD.get().getId() != veiculo.getId(),"Id do veiculo já existe");
        Assert.isTrue(veiculo.getPlaca().matches("[A-Z]{3}[0-9][A-Z][0-9]{2}") ,"Erro no formato da placa, formato correto AAA0A00 ");
        Assert.notNull(veiculo.getTipo() ,"Precisa um tipo de Veiculo ");


         */
        return veiculoRepository.save(veiculo);

    }


    @Transactional(rollbackFor = Exception.class)
    public Veiculo modificarVeiculo(Veiculo veiculo, Long id){

        veiculo.setId(id);
        /*
        Optional<Veiculo> veiculoBD = veiculoRepository.findById(veiculo.getId());
        Assert.isTrue(veiculoBD.get().getId() == veiculo.getId(),"Veiculo não encontrado");
        Assert.isTrue(veiculo.getPlaca().matches("[A-Z]{3}[0-9][A-Z][0-9]{2}") ,"Erro no formato da placa, formato correto AAA0A00 ");
        Assert.notNull(veiculo.getTipo() ,"Precisa um tipo de Veiculo ");

         */

        return veiculoRepository.save(veiculo);

    }


    @Transactional(rollbackFor = Exception.class)
    public void deletarVeiculo(Long id) {

        veiculoRepository.deleteById(id);

        /*
        Optional<Veiculo> veiculoBD = this.veiculoRepository.findById(id);
        Assert.isTrue(!veiculoBD.isEmpty(),"Veiculo não encontrado ");
        Veiculo veiculo = veiculoBD.get();
        Assert.isTrue(!this.movimentacaoRepository.existsByveiculo(veiculo),"Veiculo vinculado a movimentação não pode ser excluido");
        this.veiculoRepository.delete(veiculo);

         */

    }


}
