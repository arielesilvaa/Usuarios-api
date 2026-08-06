package com.projetopratico.api.business;


import com.projetopratico.api.business.converte.UsuarioConverter;
import com.projetopratico.api.business.dto.UsuarioDTO;
import com.projetopratico.api.infrastructure.entity.Usuario;
import com.projetopratico.api.infrastructure.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioConverter usuarioConverter;

    public UsuarioDTO salvaUsuario(UsuarioDTO usuarioDTO) {
                Usuario usuario = usuarioConverter.paraUsuario(usuarioDTO);
                 return usuarioConverter.paraUsuarioDTO( usuarioRepository.save(usuario));
    }


}

