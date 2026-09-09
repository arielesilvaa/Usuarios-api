package com.projetopratico.api.business;

import com.projetopratico.api.business.converte.UsuarioConverter;
import com.projetopratico.api.business.dto.EnderecoDTO;
import com.projetopratico.api.business.dto.TelefoneDTO;
import com.projetopratico.api.business.dto.UsuarioDTO;
import com.projetopratico.api.infrastructure.entity.Endereco;
import com.projetopratico.api.infrastructure.entity.Telefone;
import com.projetopratico.api.infrastructure.entity.Usuario;
import com.projetopratico.api.infrastructure.exceptions.ConflictException;
import com.projetopratico.api.infrastructure.exceptions.ResourceNotFoundException;
import com.projetopratico.api.infrastructure.repository.EnderecoRepository;
import com.projetopratico.api.infrastructure.repository.TelefoneRepository;
import com.projetopratico.api.infrastructure.repository.UsuarioRepository;
import com.projetopratico.api.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioConverter usuarioConverter;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final EnderecoRepository enderecoRepository;
    private final TelefoneRepository telefoneRepository;


    public UsuarioDTO salvaUsuario(UsuarioDTO usuarioDTO) {
        emailExiste(usuarioDTO.getEmail());
        usuarioDTO.setSenha(passwordEncoder.encode(usuarioDTO.getSenha()));
        Usuario usuario = usuarioConverter.paraUsuario(usuarioDTO);
        return usuarioConverter.paraUsuarioDTO(usuarioRepository.save(usuario));
    }

    public void emailExiste(String email) {
        if (verificaEmailExistente(email)) {
            throw new ConflictException("Email já cadastrado " + email);
        }
    }

    public boolean verificaEmailExistente(String email) {
        return usuarioRepository.existsByEmail(email);
    }

    public UsuarioDTO buscarUsuarioPorEmail(String email) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Email não encontrado " + email));
        return usuarioConverter.paraUsuarioDTO(usuario);
    }
    public void deletaUsuarioPorEmail(String email){
        usuarioRepository.deleteByEmail(email);
    }

    public UsuarioDTO atualizaDadosUsuario(String token, UsuarioDTO dto) {
        String email = jwtUtil.extractUsername(token.substring(7));

        dto.setSenha(dto.getSenha()  != null ?  passwordEncoder.encode(dto.getSenha())  :  null);

        Usuario usuarioEntity = usuarioRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("Email não encontrado " + email));

        Usuario usuario = usuarioConverter.updateUsuario(dto, usuarioEntity);
        usuario.setSenha(passwordEncoder.encode(usuario.getPassword()));

        return usuarioConverter.paraUsuarioDTO(usuarioRepository.save(usuario));
    }

    public EnderecoDTO atualizaEndereco(Long idEndereco, EnderecoDTO enderecoDTO) {

        Endereco entity = enderecoRepository.findById(idEndereco).orElseThrow(() ->
                new ResourceNotFoundException("Endereço não encontrado " + idEndereco));

        Endereco endereco = usuarioConverter.updateEndereco(enderecoDTO, entity);

        return usuarioConverter.paraEnderecoDTO(enderecoRepository.save(endereco));
    }

    public TelefoneDTO atualizaTelefone(Long idTelefone, TelefoneDTO dto) {

       Telefone entity = telefoneRepository.findById(idTelefone).orElseThrow(() ->
                new ResourceNotFoundException("Telefone não encontrado " + idTelefone));

        Telefone telefone = usuarioConverter.updateTelefone(dto, entity);

        return usuarioConverter.paraTelefoneDTO(telefoneRepository.save(telefone));
    }

}