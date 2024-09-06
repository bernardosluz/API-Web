package com.example.springboot.controller;

import com.example.springboot.usuarios.Usuario;
import com.example.springboot.dtos.UsuarioRecordDto;
import com.example.springboot.repositorio.UsuarioRepositorio;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class UsuarioController {

    @Autowired
    UsuarioRepositorio usuarioRepositorio;

    @PostMapping("/usuarios")
    public ResponseEntity<Usuario> saveUsuario(@RequestBody @Valid UsuarioRecordDto usuarioRecordDto) {
        var usuario = new Usuario();
        BeanUtils.copyProperties(usuarioRecordDto, usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioRepositorio.save(usuario));
    }

    @GetMapping("/usuarios")
    public ResponseEntity<List<Usuario>> getAllUsuarios(){
        return ResponseEntity.status(HttpStatus.OK).body(usuarioRepositorio.findAll());
    }

    @GetMapping("/usuarios/{cpf}")
    public ResponseEntity<Object> getOneUsuario(@PathVariable(value="cpf") long cpf){
        Optional<Usuario> usuario1 = usuarioRepositorio.findByCpf(cpf);
        if(usuario1.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado");
        }
        return ResponseEntity.status(HttpStatus.OK).body(usuario1.get());
    }

    @PutMapping("/usuarios/{cpf}")
    public ResponseEntity<Object> updateUsuario(@PathVariable(value="cpf") long cpf,
                                                    @RequestBody @Valid UsuarioRecordDto usuarioRecordDto){
        Optional<Usuario> usuario1 = usuarioRepositorio.findByCpf(cpf);
        if(usuario1.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado");
        }
        var usuario = usuario1.get();
        BeanUtils.copyProperties(usuarioRecordDto, usuario);
        return ResponseEntity.status(HttpStatus.OK).body(usuarioRepositorio.save(usuario));
    }

    @DeleteMapping("/usuarios/{cpf}")
    public ResponseEntity<Object> deleteUsuario(@PathVariable(value="cpf") long cpf){
        Optional<Usuario> usuario1 = usuarioRepositorio.findByCpf(cpf);
        if(usuario1.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado");
        }
        usuarioRepositorio.delete(usuario1.get());
        return ResponseEntity.status(HttpStatus.OK).body("Usuário deletado com sucesso");
    }

}
