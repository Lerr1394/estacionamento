package br.com.uniamerica.estacionamento.Service;

import br.com.uniamerica.estacionamento.Entity.Condutor;
import br.com.uniamerica.estacionamento.Entity.Marca;
import br.com.uniamerica.estacionamento.Repository.CondutorRepository;
import br.com.uniamerica.estacionamento.Repository.MovimentacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

@Service
public class CondutorService {

    @Autowired
    private CondutorRepository condutorRepository;
    @Autowired
    private MovimentacaoRepository movimentacaoRepository;



    public List<Condutor> findAllCondutores(){

        return condutorRepository.findAll();
    }


    public List<Condutor> findAllCondAtivos(){

        return  condutorRepository.findByAtivo(true);
    }


    public Condutor findCondById(Long id){
        Optional<Condutor> condutorBD = condutorRepository.findById(id);

        Assert.isTrue(!condutorBD.isEmpty(), "Condutor não encontrado");
        return condutorBD.get();

    }


    @Transactional(rollbackFor = Exception.class)
    public Condutor cadastrarCondutor(Condutor condutor){

        Optional<Condutor> condutorBD = condutorRepository.findById(condutor.getId());

        Assert.isTrue(condutorBD.isEmpty(),"Id do condutor já existe");
        Assert.isTrue(!condutorRepository.findByCpf(condutor.getCpf()).isPresent(),"CPF do condutor já cadastrado");
        Assert.isTrue(condutor.getCpf().matches("\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}"),"Formato de CPF invalido, deve ser: 000.000.000-00");
        Assert.isTrue(!condutorRepository.findBytelefone(condutor.getTelefone()).isPresent(),"El telefone do condutor já esta cadastrado");
        Assert.isTrue(condutor.getTelefone().matches("\\(\\d{2}\\)\\d{5}-\\d{4}"),"Formato de TELEFONE invalido, deve ser: (00)0000-0000");
        Assert.isTrue(condutor.getNome().matches("[a-zA-Z\\s]+"),"Somente é permitido letras no nome");

        return condutorRepository.save(condutor);

    }


    @Transactional(rollbackFor = Exception.class)
    public Condutor modificarCondutor(Condutor condutor, Long id){



        Optional<Condutor> condutorBD = condutorRepository.findById(id);
        Assert.isTrue(!condutorBD.isEmpty(),"Condutor não existe");
        Assert.isTrue(!condutorRepository.findByCpf(condutor.getCpf()).isPresent(),"CPF do condutor já cadastrado");
        Assert.isTrue(condutor.getCpf().matches("\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}"),"Formato de CPF invalido, deve ser: 000.000.000-00");
        Assert.isTrue(!condutorRepository.findBytelefone(condutor.getTelefone()).isPresent(),"El telefone do condutor já esta cadastrado");
        Assert.isTrue(condutor.getTelefone().matches("\\(\\d{2}\\)\\d{5}-\\d{4}"),"Formato de TELEFONE invalido, deve ser: (00)0000-0000");
        Assert.isTrue(condutor.getNome().matches("[a-zA-Z\\s]+"),"Somente é permitido letras no nome");



        return condutorRepository.save(condutor);
    }


    @Transactional(rollbackFor = Exception.class)
    public void deletarCondutor(Long id) {

        Optional<Condutor> marcaBD = this.condutorRepository.findById(id);
        Assert.isTrue(!marcaBD.isEmpty(),"Condutor não existe");


        condutorRepository.deleteById(id);




      /*  Optional<Condutor> condutorBD = this.condutorRepository.findById(id);
        Assert.isTrue(!condutorBD.isEmpty(),"Condutor não existe");
        Condutor condutor = condutorBD.get();
        Assert.isTrue(!this.movimentacaoRepository.existsByCondutor(condutor),"Condutor vinculado a movimentação não pode ser excluido");
        this.condutorRepository.delete(condutor);
        */

    }


}
