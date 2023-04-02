package com.gym.controller;

import com.gym.controller.main.Attributes;
import com.gym.model.Statics;
import com.gym.model.Subs;
import com.gym.model.Users;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Controller
@RequestMapping("/subs")
public class SubsCont extends Attributes {
    @GetMapping
    public String Subs(Model model) {
        AddAttributesSubs(model);
        return "subs";
    }

    @GetMapping("/my")
    public String SubsMy(Model model) {
        AddAttributesSubsMy(model);
        return "mySubs";
    }

    @GetMapping("/my/delete/{id}")
    public String SubMyDelete(@PathVariable Long id) {
        Users user = getUser();
        user.removeSub(subsRepo.getReferenceById(id));
        usersRepo.save(user);
        return "redirect:/subs/my";
    }

    @GetMapping("/buy/{id}")
    public String SubBuy(@PathVariable Long id) {
        Users users = getUser();
        Subs subs = subsRepo.getReferenceById(id);
        users.addSub(subs);
        subs.getStatics().setCount(subs.getStatics().getCount() + 1);
        usersRepo.save(users);
        return "redirect:/subs";
    }

    @GetMapping("/add")
    public String SubAdd(Model model) {
        AddAttributes(model);
        return "addSub";
    }

    @GetMapping("/edit/{id}")
    public String SubEdit(Model model, @PathVariable Long id) {
        AddAttributesSubEdit(model, id);
        return "editSub";
    }

    @GetMapping("/delete/{id}")
    public String SubDelete(@PathVariable Long id) {
        Subs subs = subsRepo.getReferenceById(id);
        Set<Users> usersList = subs.getUsers();
        for (Users i : usersList) {
            i.removeSub(subs);
        }
        subsRepo.delete(subs);
        return "redirect:/subs";
    }

    @PostMapping("/add")
    public String subsAddNew(Model model, @RequestParam String name, @RequestParam int price, @RequestParam byte term, @RequestParam byte pause, @RequestParam byte start_by, @RequestParam byte before, @RequestParam MultipartFile file, @RequestParam String description) {
        String res = "";
        if (file != null && !Objects.requireNonNull(file.getOriginalFilename()).isEmpty()) {
            String uuidFile = UUID.randomUUID().toString();
            boolean createDir = true;
            try {
                File uploadDir = new File(uploadImg);
                if (!uploadDir.exists()) createDir = uploadDir.mkdir();
                if (createDir) {
                    res = "subs/" + uuidFile + "_" + file.getOriginalFilename();
                    file.transferTo(new File(uploadImg + "/" + res));
                }
            } catch (Exception e) {
                model.addAttribute("message", "Некорректный данные!");
                AddAttributes(model);
                return "addSub";
            }
        }

        Subs sub = new Subs(name, price, term, pause, start_by, before, res, description);
        Statics statics = new Statics();
        sub.setStatics(statics);
        statics.setSubs(sub);
        subsRepo.save(sub);

        return "redirect:/subs/add";
    }

    @PostMapping("/edit/{id}")
    public String SubEditOld(Model model, @RequestParam String name, @RequestParam int price, @RequestParam byte term, @RequestParam byte pause, @RequestParam byte start_by, @RequestParam byte before, @RequestParam MultipartFile file, @RequestParam String description, @PathVariable Long id) {
        Subs sub = subsRepo.getReferenceById(id);

        sub.setName(name);
        sub.setPrice(price);
        sub.setTerm(term);
        sub.setPause(pause);
        sub.setStart_by(start_by);
        sub.setBefore(before);
        sub.setDescription(description);

        if (file != null && !Objects.requireNonNull(file.getOriginalFilename()).isEmpty()) {
            String res = "";
            String uuidFile = UUID.randomUUID().toString();
            boolean createDir = true;
            try {
                File uploadDir = new File(uploadImg);
                if (!uploadDir.exists()) createDir = uploadDir.mkdir();
                if (createDir) {
                    res = "subs/" + uuidFile + "_" + file.getOriginalFilename();
                    file.transferTo(new File(uploadImg + "/" + res));
                }
            } catch (Exception e) {
                model.addAttribute("message", "Некорректный данные!");
                AddAttributesSubEdit(model, id);
                return "editSub";
            }
            sub.setFile(res);
        }

        subsRepo.save(sub);

        return "redirect:/subs";
    }
}
