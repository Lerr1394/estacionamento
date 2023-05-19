package br.com.uniamerica.estacionamento.Service;

import br.com.uniamerica.estacionamento.Repository.MarcaRepository;
import br.com.uniamerica.estacionamento.Entity.Marca;
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

    public List<Marca> findAllMarcas(){

        return marcaRepository.findAll();
    }


    public Marca findMarcaById(Long id){
        Optional<Marca> marcaBD = marcaRepository.findById(id);

        //Assert.isTrue(!marcaBD.isEmpty(), "Marca não encontrado");

        return marcaBD.get();

    }



    @Transactional(rollbackFor = Exception.class)
    public Marca cadastroMarca(Marca marca){


        return marcaRepository.save(marca);
    }




    @Transactional(rollbackFor = Exception.class)
    public Marca modificarMarca(Marca marca, Long id){
        marca.setId(id);
        /*Optional<Marca> marcaBD = marcaRepository.findById(marca.getId());
        Assert.isTrue(!marcaBD.isEmpty(),"Marca não existe");
        Assert.isTrue(marcaBD.get().getId() != marca.getId(),"Id da Marca já existe");
        Assert.isTrue(marcaBD.get().getNome() != marca.getNome(),"Marca já cadastrado");
*/
       return  marcaRepository.save(marca);
    }


    @Transactional(rollbackFor = Exception.class)
    public void deletarMarca(Long id) {
        Optional<Marca> marcaBD = this.marcaRepository.findById(id);
        Assert.isTrue(!marcaBD.isEmpty(),"Marca não existe");


        marcaRepository.deleteById(id);
      // this.marcaRepository.delete(marcaBD.get());


    }



}
