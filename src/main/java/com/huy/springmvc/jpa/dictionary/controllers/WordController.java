package com.huy.springmvc.jpa.dictionary.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.huy.springmvc.jpa.dictionary.beans.Word;
import com.huy.springmvc.jpa.dictionary.services.WordService;

@Controller
public class WordController {

    @Autowired
    private WordService wordService;

    @RequestMapping("/test")
    public String getFirstPage() {

        return "login";
    }

    @RequestMapping(value = { "/search", "/search/{offset}", "/wordlist/search","/search/search", "/search/search/{offset}",
            "wordlist/search/{offset}" }, method = RequestMethod.GET)
    public String searchWord(@RequestParam(value = "word", defaultValue = "") String name, Model model,
            @RequestParam(value = "type", required = false) String type, HttpServletRequest request,
            @PathVariable(value = "offset", required = false) Integer offset, Integer maxResult) {
        HttpSession session = request.getSession();
        Object attribute = session.getAttribute("isAdmin");
        if (attribute != null) {
            model.addAttribute("wordPara", name);
            model.addAttribute("typePara", type);
            model.addAttribute("search", true);
            model.addAttribute("count", wordService.countSearch(name, type));
            model.addAttribute("offset", offset);
            model.addAttribute("listWord", wordService.findByWordByPage(name, type, offset, maxResult));
            return "lookup";
        }
        return "redirect:/login";
    }

    @RequestMapping(value = { "/wordlist", "/wordlist/{offset}" }, method = RequestMethod.GET)
    public String listWord(Model model, HttpServletRequest request,
            @PathVariable(value = "offset", required = false) Integer offset, Integer maxResult) {
        HttpSession session = request.getSession();
        Object attribute = session.getAttribute("isAdmin");
        if (attribute != null) {
            model.addAttribute("search", null);
            model.addAttribute("count", wordService.count());
            model.addAttribute("listWord", wordService.findAll(offset, maxResult));
            model.addAttribute("offset", offset);
            return "lookup";
        }
        return "redirect:/login";
    }

    @RequestMapping("/edit/{id}")
    public String editWord(@PathVariable int id, Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Object attribute = session.getAttribute("isAdmin");
        if (attribute != null) {
            Word word = wordService.findById(id);
            model.addAttribute("word", word);
            return "edit";
        }
        return "redirect:/login";
    }

    @RequestMapping("/editWord")
    public String doUpdateCustomer(@ModelAttribute("Word") Word word, Model model, Integer offset, Integer maxResult) {
        wordService.update(word);
        model.addAttribute("listWord", wordService.findAll(offset, maxResult));
        return "redirect:/wordlist";
    }

    @RequestMapping("/delete/{id}")
    public String doDeleteCustomer(@PathVariable int id, Model model, Integer offset, Integer maxResult) {
        wordService.delete(id);
        model.addAttribute("listWord", wordService.findAll(offset, maxResult));
        return "redirect:/wordlist";
    }

    @RequestMapping("/add-word")
    public String insertWord(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Object attribute = session.getAttribute("isAdmin");
        if (attribute != null) {
            model.addAttribute("word", new Word());
            return "add-word";
        }
        return "redirect:/login";

    }

    @RequestMapping("/addWord")
    public String doInsertWord(@ModelAttribute("Word") Word word, Model model, Integer offset, Integer maxResult) {
        List<Word> words = wordService.findByWord(word.getWord(), word.getWordtype());
        if (words.isEmpty()) {
            wordService.save(word);

            return "redirect:/wordlist";
        } else {
            model.addAttribute("message", "Word already exists");
            return "redirect:/add-word";
        }

    }
}
