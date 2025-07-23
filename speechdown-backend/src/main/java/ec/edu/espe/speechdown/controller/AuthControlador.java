package ec.edu.espe.speechdown.controller;

import ec.edu.espe.speechdown.dto.AuthRespuesta;
import ec.edu.espe.speechdown.dto.LoginPeticion;
import ec.edu.espe.speechdown.dto.RegistroPeticion;
import ec.edu.espe.speechdown.model.Usuario;
import ec.edu.espe.speechdown.service.AuthServicio;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ec.edu.espe.speechdown.dto.Respuesta;


@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000") // Ajusta si tu React está en otro puerto
public class AuthControlador {

    private final AuthServicio authServicio;

    public AuthControlador(AuthServicio authServicio) {
        this.authServicio = authServicio;
    }

    @PostMapping("/registrar")
    public Respuesta registrar(@RequestBody RegistroPeticion peticion) {
        return authServicio.registrar(peticion);
    }

    @PostMapping("/login")
    public Respuesta login(@RequestBody LoginPeticion peticion) {
        return authServicio.login(peticion);
    }
}
