package com.reabilitacao.reabilitacao.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class PageController {

    @GetMapping("/")
    public String index() {
        return "inicial"; 
    }

    @GetMapping("/{pagina}")
    public String carregarPagina(@PathVariable String pagina) {
        return pagina; 
    }
}
