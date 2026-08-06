package com.projetopratico.api.business.converte;

import com.projetopratico.api.business.dto.EnderecoDTO;
import com.projetopratico.api.business.dto.TelefoneDTO;
import com.projetopratico.api.business.dto.UsuarioDTO;
import com.projetopratico.api.infrastructure.entity.Endereco;
import com.projetopratico.api.infrastructure.entity.Telefone;
import com.projetopratico.api.infrastructure.entity.Usuario;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UsuarioConverter {

    public Usuario paraUsuario(UsuarioDTO usuarioDTO) {
        return Usuario.builder()
                .nome(usuarioDTO.getNome())
                .email(usuarioDTO.getEmail())
                .senha(usuarioDTO.getSenha())
                .enderecos(paraListaEnderecos(usuario.getEndereco()))
                .telefones(paraListaTelefones(usuario.getTelefone()))
                .build();
    }

    public List<Endereco> paraListaEnderecos(List<EnderecoDTO> enderecoDTOS) {
        return enderecoDTOS.stream()
                .map(this::paraEndereco).toList();
    }

    public  Endereco paraEndereco(EnderecoDTO enderecoDTO) {
        return Endereco.builder()
                .rua(enderecoDTO.getRua())
                .numero(enderecoDTO.getNumero())
                .cidade(enderecoDTO.getCidade())
                .complemento(enderecoDTO.getComplemento())
                .cep(enderecoDTO.getCep())
                .estado(enderecoDTO.getEstado())
                .build();
    }

    public List<Telefone> paraListaTelefones(List<TelefoneDTO> telefoneDTOS ) {
        return telefoneDTOS.stream().map(this::paraTelefone).toList();
    }

    public Telefone paraTelefone(TelefoneDTO telefoneDTO) {
        return Telefone.builder()
                .numero(telefoneDTO.getNumero())
                .ddd(telefoneDTO.getDdd())
                .build();
    }

        public UsuarioDTO paraUsuarioDTO(Usuario usuarioDTO) {
            return UsuarioDTO.builder()
                    .nome(usuarioDTO.getNome())
                    .email(usuarioDTO.getEmail())
                    .senha(usuarioDTO.getSenha())
                    .enderecos(paraListaEnderecosDTO(usuarioDTO.getEndereco()))
                    .telefones(paraListaTelefonesDTO(usuarioDTO.getTelefone()))
                    .build();
        }

        public List<EnderecoDTO> paraListaEnderecosDTO(List<Endereco> enderecoDTOS) {
            List<EnderecoDTO> enderecos = new ArrayList<>();
            for (Endereco enderecoDTO : enderecoDTOS) {
                enderecos.add(paraEndereco(enderecoDTO));
            }
        }

        public  EnderecoDTO paraEndereco(Endereco enderecoDTO) {
            return Endereco.builder()
                    .rua(enderecoDTO.getRua())
                    .numero(enderecoDTO.getNumero())
                    .cidade(enderecoDTO.getCidade())
                    .complemento(enderecoDTO.getComplemento())
                    .cep(enderecoDTO.getCep())
                    .estado(enderecoDTO.getEstado())
                    .build();
        }

        public List<TelefoneDTO> paraListaTelefonesDTO(List<Telefone> telefoneDTOS ) {
            return telefoneDTOS.stream().map(this::paraTelefoneDTO).toList();
        }

        public TelefoneDTO paraTelefoneDTO(Telefone telefoneDTO) {
            return TelefoneDTO.builder()
                    .numero(telefoneDTO.getNumero())
                    .ddd(telefoneDTO.getDdd())
                    .build();
        }

}
