package com.projetopratico.api.controller;

import com.projetopratico.api.business.UsuarioService;
import com.projetopratico.api.business.dto.EnderecoDTO;
import com.projetopratico.api.business.dto.TelefoneDTO;
import com.projetopratico.api.business.dto.UsuarioDTO;
import com.projetopratico.api.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuario")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @PostMapping
    public ResponseEntity<UsuarioDTO> salvaUsuario(@RequestBody UsuarioDTO usuarioDTO) {
        return ResponseEntity.ok(usuarioService.salvaUsuario(usuarioDTO));
    }

    @PostMapping("/login")
    public String login(@RequestBody UsuarioDTO usuarioDTO){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(usuarioDTO.getEmail(),
                        usuarioDTO.getSenha())
        );
        return "Bearer " + jwtUtil.generateToken(authentication.getName());
    }

    @GetMapping
    public ResponseEntity<UsuarioDTO> buscaUsuarioPorEmail(@RequestParam("email") String email){
        return ResponseEntity.ok(usuarioService.buscarUsuarioPorEmail(email));
    }

    @DeleteMapping
    public ResponseEntity<Void> deletaUsuarioPorEmail(@RequestParam("email") String email){
        usuarioService.deletaUsuarioPorEmail(email);
        return ResponseEntity.ok().build();
    }

    // Atualiza dados do usuário (mantido em PUT /usuario)
    @PutMapping
    public ResponseEntity<UsuarioDTO> atualizaDadosUsuario(@RequestHeader("Authorization") String token,
                                                           @RequestBody UsuarioDTO dto) {
        return ResponseEntity.ok(usuarioService.atualizaDadosUsuario(token, dto));
    }

    // Atualiza endereço -> PUT /usuario/endereco?id=...
    @PutMapping("/endereco")
    public ResponseEntity<EnderecoDTO> atualizaEndereco(@RequestParam("id") Long id,
                                                        @RequestBody EnderecoDTO dto) {
        return ResponseEntity.ok(usuarioService.atualizaEndereco(id, dto));
    }

    // Atualiza telefone -> PUT /usuario/telefone?id=...
    @PutMapping("/telefone")
    public ResponseEntity<TelefoneDTO> atualizaTelefone(@RequestParam("id") Long id,
                                                        @RequestBody TelefoneDTO dto) {
        return ResponseEntity.ok(usuarioService.atualizaTelefone(id, dto));
    }

}