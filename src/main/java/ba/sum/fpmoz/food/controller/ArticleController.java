package ba.sum.fpmoz.food.controller;

import ba.sum.fpmoz.food.model.Article;
import ba.sum.fpmoz.food.repositories.ArticleRepository;
import ba.sum.fpmoz.food.model.UserDetails;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


@Controller
public class ArticleController {

    @Autowired
    ArticleRepository articleRepository;

    private static String UPLOADED_FOLDER = "./uploads/";


    @GetMapping("/articles")
    public String showArticles (Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails user = (UserDetails) auth.getPrincipal();
        model.addAttribute("user", user);
        model.addAttribute("article", new Article());
        model.addAttribute("articles", articleRepository.findAll());
        model.addAttribute("added", false);
        model.addAttribute("activeLink", "Proizvodi");
        return "articles";
    }

    @GetMapping("/article/delete/{id}")
    public String deleteArticle(@PathVariable("id") Long id, Model model) throws IOException {
        Article article = articleRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Pogrešan ID"));
        articleRepository.delete(article);
        Files.delete(Path.of(article.getImage()));
        return "redirect:/articles";
    }

    @GetMapping("/article/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails user = (UserDetails) auth.getPrincipal();
        model.addAttribute("user", user);
        Article article = articleRepository.findById(id).orElseThrow(() -> new IllegalArgumentException());
        model.addAttribute("article", article);
        model.addAttribute("activeLink", "Proizvodi");
        return "article_edit";
    }

    @PostMapping("/article/edit/{id}")
    public String editArticle (@PathVariable("id") Long id, @Valid Article article, BindingResult result, @RequestParam("file") MultipartFile file, Model model) {
        if (file.isEmpty()) {
            result.addError(new FieldError("article", "image", "Molimo odaberite sliku."));
        } else if (!file.getContentType().startsWith("image/")) {
            result.addError(new FieldError("article", "image", "Molimo slika nije ispravnog formata."));
        } else {
            try {
                byte[] bytes = file.getBytes();
                Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
                Files.write(path, bytes);
                article.setImage(path.toString());
            } catch (IOException e) {
                result.addError(new FieldError("article", "image", "Problem s učitavanjem slike na poslužitelj."));
            }
        }

        if (result.hasErrors()) {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            UserDetails user = (UserDetails) auth.getPrincipal();
            model.addAttribute("user", user);
            model.addAttribute("article", article);
            model.addAttribute("activeLink", "Proizvodi");
            return "article_edit";
        }
        articleRepository.save(article);
        return "redirect:/articles";
    }


    @PostMapping("/article/add")
    public String addArticle (@Valid Article article, BindingResult result, @RequestParam("file") MultipartFile file, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails user = (UserDetails) auth.getPrincipal();
        model.addAttribute("user", user);
        model.addAttribute("activeLink", "Proizvodi");

        if (file.isEmpty()) {
            result.addError(new FieldError("article", "image", "Molimo odaberite sliku."));
        } else if (!file.getContentType().startsWith("image/")) {
            result.addError(new FieldError("article", "image", "Molimo slika nije ispravnog formata."));
        } else {
            try {
                byte[] bytes = file.getBytes();
                Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
                Files.write(path, bytes);
                article.setImage(path.toString());
            } catch (IOException e) {
                result.addError(new FieldError("article", "image", "Problem s učitavanjem slike na poslužitelj."));
            }
        }

        if (result.hasErrors()) {
            model.addAttribute("article", article);
            model.addAttribute("added", true);
            model.addAttribute("articles", articleRepository.findAll());
            return "articles";
        }
        articleRepository.save(article);
        return "redirect:/articles";
    }
}