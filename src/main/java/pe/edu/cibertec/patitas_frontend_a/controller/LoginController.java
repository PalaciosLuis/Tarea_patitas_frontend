package pe.edu.cibertec.patitas_frontend_a.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pe.edu.cibertec.patitas_frontend_a.dto.LoginRequestDTO;
import pe.edu.cibertec.patitas_frontend_a.dto.LoginResponseDTO;
import pe.edu.cibertec.patitas_frontend_a.viewmodel.LoginModel;
import org.springframework.web.client.RestTemplate;

@Controller
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/inicio")
    public String inicio(Model model) {
        LoginModel loginModel = new LoginModel("00", "", "");
        model.addAttribute("loginModel", loginModel);
        return "inicio";
    }

    @PostMapping("/autenticar")
    public String autenticar(@RequestParam("tipoDocumento") String tipoDocumento,
                             @RequestParam("numeroDocumento") String numeroDocumento,
                             @RequestParam("password") String password,
                             Model model) {

        //Validar campos de entrada
        if (tipoDocumento == null || tipoDocumento.trim().length() == 0 ||
                numeroDocumento == null || numeroDocumento.trim().length() == 0 ||
                password == null || password.trim().length() == 0) {

            LoginModel loginModel = new LoginModel("01", "Error: Debe completar correctamente sus credenciales", "");
            model.addAttribute("loginModel", loginModel);
            return "inicio";
        }

        //Invocar servicio de autenticacion
        //Objeto para la solicitud
        LoginRequestDTO loginRequest = new LoginRequestDTO(tipoDocumento, numeroDocumento, password);

        //Consumir API
        LoginResponseDTO response = restTemplate.postForObject("http://localhost:8081/autenticacion/login", loginRequest, LoginResponseDTO.class);

        if (response != null && "00".equals(response.codigo())) {
            LoginModel loginModel = new LoginModel("00", "", response.nombreUsuario());
            model.addAttribute("loginModel", loginModel);
            return "principal";
        } else {
            LoginModel loginModel = new LoginModel("01", response.mensaje(), "");
            model.addAttribute("loginModel", loginModel);
            return "inicio";
        }
    }
}