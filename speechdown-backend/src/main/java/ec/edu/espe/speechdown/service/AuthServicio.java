package ec.edu.espe.speechdown.service;

import ec.edu.espe.speechdown.dto.LoginPeticion;
import ec.edu.espe.speechdown.dto.RegistroPeticion;
import ec.edu.espe.speechdown.model.Usuario;
import ec.edu.espe.speechdown.repository.UsuarioRepositorio;
import org.springframework.stereotype.Service;
import ec.edu.espe.speechdown.dto.Respuesta;
import java.util.Optional;


@Service
public class AuthServicio {

    private final UsuarioRepositorio usuarioRepositorio;

    public AuthServicio(UsuarioRepositorio usuarioRepositorio) {
        this.usuarioRepositorio = usuarioRepositorio;
    }

    public Respuesta registrar(RegistroPeticion peticion) {
        if (usuarioRepositorio.existsByEmail(peticion.getEmail())) {
            return new Respuesta("El email ya está registrado", null);
        }
        Usuario usuario = new Usuario();
        usuario.setEmail(peticion.getEmail());
        usuario.setContrasena(peticion.getContrasena()); // sin encriptar (ejemplo)
        usuario.setRol(peticion.getRol());
        usuarioRepositorio.save(usuario);
        return new Respuesta("Usuario registrado exitosamente", usuario.getRol());
    }

    public Respuesta login(LoginPeticion peticion) {
        Optional<Usuario> opt = usuarioRepositorio.findByEmail(peticion.getEmail());
        if (opt.isEmpty()) {
            return new Respuesta("Usuario no encontrado", null);
        }
        Usuario usuario = opt.get();
        if (!usuario.getContrasena().equals(peticion.getContrasena())) {
            return new Respuesta("Contraseña incorrecta", null);
        }
        return new Respuesta("Login exitoso", usuario.getRol());
    }
}
