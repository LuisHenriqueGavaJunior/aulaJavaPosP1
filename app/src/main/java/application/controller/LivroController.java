package application.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;

import application.model.Livro;
import application.model.Genero;
import application.model.Editora;
import application.repository.EditoraRepository;
import application.repository.GeneroRepository;
import application.repository.LivroRepository;

@Controller
@RequestMapping("/livros")
public class LivroController {
    @Autowired
    private LivroRepository livroRepo;
    @Autowired
    private GeneroRepository generoRepo;
    @Autowired
    private EditoraRepository editoraRepo;

    @RequestMapping("/insert")
    public String insert(Model ui) {
        ui.addAttribute("generos", generoRepo.findAll());
        ui.addAttribute("editoras", editoraRepo.findAll());
        return "livros/insert";
    }

    @PostMapping("/insert")
    public String insert(
        @RequestParam("titulo") String titulo,
        @RequestParam("genero") long idGenero,
        @RequestParam("editora") long idEditora
    ) {
        Genero genero = generoRepo.findById(idGenero).orElse(null);
        Editora editora = editoraRepo.findById(idEditora).orElse(null);
        
        if (genero != null && editora != null) {
            Livro livro = new Livro();
            livro.setTitulo(titulo);
            livro.setGenero(genero);
            livro.setEditora(editora);
            
            livroRepo.save(livro);
        }

        return "redirect:/livros/list";
    }

    @RequestMapping("/list")
    public String getAll(Model ui) {
        ui.addAttribute("livros", livroRepo.findAll());
        return "/livros/list";
    }
}
