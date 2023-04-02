package com.gym.controller;

import com.gym.controller.main.Attributes;
import com.gym.model.Trainers;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Objects;
import java.util.UUID;

@Controller
@RequestMapping("/trainers")
public class TrainersCont extends Attributes {
    @GetMapping("/add")
    public String TrainerAdd(Model model) {
        AddAttributes(model);
        return "addTrainer";
    }

    @GetMapping("/delete/{id}")
    public String TrainerDelete(Model model, @PathVariable Long id) {
        trainersRepo.deleteById(id);
        return "redirect:/";
    }

    @PostMapping("/add")
    public String TrainerAddNew(Model model, @RequestParam String name, @RequestParam int price, @RequestParam String ach, @RequestParam String tel, @RequestParam byte exp, @RequestParam String spec, @RequestParam MultipartFile file) {
        String res = "";
        if (file != null && !Objects.requireNonNull(file.getOriginalFilename()).isEmpty()) {
            String uuidFile = UUID.randomUUID().toString();
            boolean createDir = true;
            try {
                File uploadDir = new File(uploadImg);
                if (!uploadDir.exists()) createDir = uploadDir.mkdir();
                if (createDir) {
                    res = "trainers/" + uuidFile + "_" + file.getOriginalFilename();
                    file.transferTo(new File(uploadImg + "/" + res));
                }
            } catch (Exception e) {
                model.addAttribute("message", "Некорректный данные!");
                AddAttributes(model);
                return "addTrainer";
            }
        }

        Trainers trainer = new Trainers(name, res, tel, ach, spec, price, exp);
        trainersRepo.save(trainer);

        return "redirect:/trainers/add";
    }

    @GetMapping("/edit/{id}")
    public String TrainerEdit(Model model, @PathVariable Long id) {
        AddAttributesTrainerEdit(model, id);
        return "editTrainer";
    }

    @PostMapping("/edit/{id}")
    public String TrainerEditOld(Model model, @RequestParam String name, @RequestParam int price, @RequestParam String ach, @RequestParam String tel, @RequestParam byte exp, @RequestParam String spec, @RequestParam MultipartFile file, @PathVariable Long id) {
        Trainers trainer = trainersRepo.getReferenceById(id);

        trainer.setSpec(spec);
        trainer.setName(name);
        trainer.setAch(ach);
        trainer.setExp(exp);
        trainer.setPrice(price);
        trainer.setTel(tel);

        if (file != null && !Objects.requireNonNull(file.getOriginalFilename()).isEmpty()) {
            String res = "";
            String uuidFile = UUID.randomUUID().toString();
            boolean createDir = true;
            try {
                File uploadDir = new File(uploadImg);
                if (!uploadDir.exists()) createDir = uploadDir.mkdir();
                if (createDir) {
                    res = "trainers/" + uuidFile + "_" + file.getOriginalFilename();
                    file.transferTo(new File(uploadImg + "/" + res));
                }
            } catch (Exception e) {
                AddAttributesTrainerEdit(model, id);
                model.addAttribute("message", "Некорректный данные!");
                return "editTrainer";
            }
            trainer.setFile(res);
        }

        trainersRepo.save(trainer);
        return "redirect:/";
    }
}
