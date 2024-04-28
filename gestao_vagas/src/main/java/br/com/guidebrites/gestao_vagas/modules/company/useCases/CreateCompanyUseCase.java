package br.com.guidebrites.gestao_vagas.modules.company.useCases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.guidebrites.gestao_vagas.exceptions.UserFoundException;
import br.com.guidebrites.gestao_vagas.modules.company.entities.CompanyEntity;
import br.com.guidebrites.gestao_vagas.modules.company.repositories.CompanyRepository;

@Service
public class CreateCompanyUseCase {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    public CompanyEntity execute(CompanyEntity companyEntity){
        this.companyRepository
        .findByUsernameOrPassword(companyEntity.getUsername(), companyEntity.getPassword())
        .ifPresent((user) -> {
            throw new UserFoundException();
        });

        var password = passwordEncoder.encode(companyEntity.getPassword());
        companyEntity.setPassword(password);
        return this.companyRepository.save(companyEntity);
    }
}
