package br.com.uniamerica.estacionamento.Service;

import br.com.uniamerica.estacionamento.Entity.Modelo;
import br.com.uniamerica.estacionamento.Repository.MarcaRepository;
import br.com.uniamerica.estacionamento.Entity.Marca;
import br.com.uniamerica.estacionamento.Repository.ModeloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

@Service
public class MarcaService {

    @Autowired
    private MarcaRepository marcaRepository;
    @Autowired
    private ModeloRepository modeloRepository;

    public List<Marca> findAllMarcas(){

        return marcaRepository.findAll();
    }

    public List<Marca> findAllMarcasAtivas(){

        return  marcaRepository.findMarcaByAtivo(true);
    }

@Transactional(rollbackFor = Exception.class)
    public Marca findMarcaById(Long id){
        Optional<Marca> marcaBD = this.marcaRepository.findById(id);
        if(marcaBD.isEmpty()){
            throw  new RuntimeException("Marca não encontrada");
        }
            return marcaBD.get();
    }



    @Transactional(rollbackFor = Exception.class)
    public void cadastrarMarca( Marca marca){

       Optional<Marca> marcaDB = marcaRepository.findById(marca.getId());

        Assert.isTrue(marcaDB.isEmpty(),"Marca já cadastrada com esse ID");
        Assert.isNull(marcaRepository.findByNome(marca.getNome()),"Marca cadastrada com ese nome");
        Assert.isTrue( marca.getNome().length() > 2,"Quantidade de caracteres minimos para ó nome é 3");
        Assert.isTrue( marca.getNome().length() <= 15,"Quantidade de caracteres Maximos para ó nome é 15");


        this.marcaRepository.save(marca);
    }




    @Transactional(rollbackFor = Exception.class)
    public Marca modificarMarca(Marca marca, Long id){

        Optional<Marca> marcaBD = marcaRepository.findById(id);
        Assert.isTrue(!marcaBD.isEmpty(),"Marca não existe");
        Assert.isNull(marcaRepository.findByNome(marca.getNome()),"Marca cadastrada com ese nome");
        Assert.isTrue( marca.getNome().length() > 2,"Quantidade de caracteres minimos para ó nome é 3");
        Assert.isTrue( marca.getNome().length() <= 15,"Quantidade de caracteres Maximos para ó nome é 15");

       return  marcaRepository.save(marca);
    }


    @Transactional(rollbackFor = Exception.class)
    public void deletarMarca(Long id) {

        Modelo modelo = modeloRepository.;
        Optional<Marca> marcaBD = marcaRepository.findById(id);
        Assert.isTrue(!modeloRepository.findModeloByMarca(marcaBD.get().getId()).equals(marcaBD.get().getNome()),"Marca asociada a um modelo, não pode ser excluida");
        Assert.isTrue(!marcaBD.isEmpty(),"Marca não encontrada para ser excluida");




        marcaRepository.deleteById(id);

    }



}
