package br.com.uniamerica.estacionamento.Service;

import br.com.uniamerica.estacionamento.Repository.MarcaRepository;
import br.com.uniamerica.estacionamento.Repository.ModeloRepository;
import br.com.uniamerica.estacionamento.Repository.VeiculoRepository;
import br.com.uniamerica.estacionamento.Entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

@Service
public class ModeloService {

    @Autowired
    private ModeloRepository modeloRepository;
    @Autowired
    private VeiculoRepository veiculoRepository;
    @Autowired
    private MarcaRepository marcaRepository;

    public List<Modelo> findAllModelos(){

        return this.modeloRepository.findAll();
    }


    public List<Modelo> findAllModelosAtivos(){

        return  this.modeloRepository.findByAtivo(true);
    }


    public Modelo findModeloById(Long id){
        Optional<Modelo> modeloBD = this.modeloRepository.findById(id);

        Assert.isTrue(!modeloBD.isEmpty(), "Modelo não encontrado");

        return modeloBD.get();

    }


    @Transactional(rollbackFor = Exception.class)
    public Modelo cadastroModelo(Modelo modelo){
        Optional<Modelo> modeloBD = modeloRepository.findById(modelo.getId());
        Assert.isTrue(modeloBD.get().getId() != modelo.getId(),"Id do Modelo já existe");


        return modeloRepository.save(modelo);

    }


    @Transactional(rollbackFor = Exception.class)
    public Modelo modificarModelo(Modelo modelo){
        Optional<Modelo> modeloBD = modeloRepository.findById(modelo.getId());



        Assert.isTrue(!modeloBD.isEmpty(),"Modelo não existe");
        Assert.isTrue(modeloBD.get().getId() != modelo.getId(),"Id do Modelo já existe");
        Assert.isTrue(modeloBD.get().getNome() != modelo.getNome(),"Modelo já cadastrado");

        return modeloRepository.save(modelo);
    }





    @Transactional(rollbackFor = Exception.class)
    public void deletarModelo(Long id) {
        Optional<Modelo> modeloBD = this.modeloRepository.findById(id);
        Assert.isTrue(!modeloBD.isEmpty(),"Modelo não existe");

        this.modeloRepository.delete(modeloBD.get());

    }






}
