package com.example.AZplastering.controller;

import com.example.AZplastering.ResponseMessage;
import com.example.AZplastering.dto.ChantierRepository;
import com.example.AZplastering.model.Chantier;
import com.example.AZplastering.service.ChantierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Controller
public class ChantierController {

    @Autowired
    private ChantierService chantierService;

    @Autowired
    private ChantierRepository chantierRepo;

    @GetMapping("/listChantier.html")
    public String showExampleView(Model model)
    {
        List<Chantier> chantiers = chantierService.getAllChantiers();
        model.addAttribute("chantiers", chantiers);
        return "/listChantier.html";
    }

    @GetMapping("/listChantie")
     public ResponseEntity getListFiles(Model model)
    {
        List<Chantier> chantiers = chantierService.getAllChantiers();
        model.addAttribute("chantiers", chantiers);
        return ResponseEntity.status(HttpStatus.OK).body(chantiers);
    }
    @GetMapping("/listChantie/{filePath}")
    public ResponseEntity downloadFile(@PathVariable String filePath) {
        Chantier ch = chantierRepo.findByName(filePath) ;
        return ResponseEntity.status(HttpStatus.OK).body(ch);
    }
    @GetMapping("/listChantie/{id}")
    public ResponseEntity<Optional<Chantier>> getChantierById(@PathVariable("id") long id) {
        Optional<Chantier> ch = chantierRepo.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(ch);
    }
    @GetMapping("/listChantie/{name}")
    public ResponseEntity getChantierByName(@PathVariable("name") String name) {
        Chantier chantierDat = chantierRepo.findByName(name);
        return ResponseEntity.status(HttpStatus.OK).body(chantierDat);
    }

    @GetMapping("/")
    public String showAddProduct()
    {

        return "/addChantier";
    }

    @PostMapping("/addCh")
    public ResponseEntity<ResponseMessage> saveProduct(@RequestParam("file") MultipartFile file,
                                                       @RequestParam("file2") MultipartFile file2,
                                                       @RequestParam("file3") MultipartFile file3,
                                                       @RequestParam("file4") MultipartFile file4,
                                                       @RequestParam("file5") MultipartFile file5,
                                                       @RequestParam("pname") String name,
                                                       @RequestParam("client") String client,
                                                       @RequestParam("region") String region,
                                                       @RequestParam("type_travaux") String type_travaux,
                                                       @RequestParam("type_projet") String type_projet)
    {
        String message = "";
        try {
            chantierService.saveProductToDB(file,file2,file3,file4,file5, name, client, region,type_travaux,type_projet);
            message = "Chantier crée successfully";
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        } catch (Exception e) {
            message = "Could not upload the file: " + file.getOriginalFilename() + "!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }

    @GetMapping("/deleteProd/{id}")
    public String deleteProduct(@PathVariable("id") Long id)
    {
        chantierService.deleteChantierById(id);
        return "redirect:/listChantiers";
    }

    @PostMapping("/changeName")
    public String changePname(@RequestParam("id") Long id,
                              @RequestParam("newPname") String name)
    {
        chantierService.chageChantierName(id, name);
        return "redirect:/listChantiers";
    }
    @PostMapping("/changeClient")
    public String changeClient(@RequestParam("id") Long id ,
                                    @RequestParam("newClient") String client)
    {
        chantierService.changeChantierClient(id, client);
        return "redirect:/listChantiers";
    }

    @PostMapping("/changeRegion")
    public String changeRegion(@RequestParam("id") Long id ,
                              @RequestParam("newRegion") String region)
    {
        chantierService.changeChantierRégion(id, region);
        return "redirect:/listChantiers";
    }

    @PostMapping("/changeType")
    public String changeType(@RequestParam("id") Long id ,
                               @RequestParam("newType") String type_projet)
    {
        chantierService.changeChantierType(id, type_projet);
        return "redirect:/listChantiers";
    }

    @PostMapping("/changeTypeTravaux")
    public String changeTypeTravx(@RequestParam("id") Long id ,
                             @RequestParam("newTypeTravx") String type_travaux)
    {
        chantierService.changeChantierTypeTravx(id, type_travaux);
        return "redirect:/listChantiers";
    }


}
