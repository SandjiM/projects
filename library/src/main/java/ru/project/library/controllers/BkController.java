package ru.project.library.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.project.library.dao.BookDAO;
import ru.project.library.dao.PersonDAO;
import ru.project.library.models.Book;
import ru.project.library.models.Person;

import javax.validation.Valid;

@Controller
@RequestMapping("/bk")
public class BkController {
    private final BookDAO bookDAO;
    private final PersonDAO personDAO;

    @Autowired
    public BkController(BookDAO bookDAO, PersonDAO personDAO, PersonDAO personDAO1) {
        this.bookDAO = bookDAO;
        this.personDAO = personDAO1;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("bks", bookDAO.index());
        return "bk/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("bk", bookDAO.show(id));

        // Получаем ID пользователя, который взял книгу
        Integer personId = bookDAO.getPersonIdByBookId(id);

        // Если ID пользователя найден, получаем объект Person
        if (personId != null) {
            Person person = personDAO.show(personId);
            model.addAttribute("person", person.getFullName());
        } else {
            model.addAttribute("person", null);
        }

        return "bk/show";
    }

    @GetMapping("/new")
    public String newPerson (@ModelAttribute("bk") Book book) {
        return "bk/new";
    }

    @PostMapping("/new")
    public String create(@ModelAttribute("bk") @Valid Book book, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "bk/new";

        bookDAO.save(book);
        return "redirect:/bk";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable int id) {
        model.addAttribute("bk", bookDAO.show(id));
        return "bk/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("person") Book book, @PathVariable("id") int id) {
        bookDAO.update(id, book);
        return "redirect:/bk";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        bookDAO.delete(id);
        return "redirect:/bk";
    }
}
