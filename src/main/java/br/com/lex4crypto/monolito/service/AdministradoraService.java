package br.com.lex4crypto.monolito.service;

import br.com.lex4crypto.monolito.exception.AdministradoraNotFoundException;
import br.com.lex4crypto.monolito.models.Administradora;
import br.com.lex4crypto.monolito.repositories.AdminsitradoraRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class AdministradoraService {

    private final AdminsitradoraRepository adminsitradoraRepository;
    private final OrdemService ordemService;

    public AdministradoraService(AdminsitradoraRepository adminsitradoraRepository, OrdemService ordemService) {
        this.adminsitradoraRepository = adminsitradoraRepository;
        this.ordemService = ordemService;
    }

    public Administradora atualizarLucroCorretagem(){
        double lucroTotalCorretagem = ordemService.calcularCorretagem();
        Administradora administradora = adminsitradoraRepository
                .findAll()
                .stream()
                .findFirst()
                .orElseThrow(() -> new AdministradoraNotFoundException("Necessario cadastrar uma administradora"));
        administradora.setLucroCorretagem(lucroTotalCorretagem);
        return adminsitradoraRepository.save(administradora);
    }
}
