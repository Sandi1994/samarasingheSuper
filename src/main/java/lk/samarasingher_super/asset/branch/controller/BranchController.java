package lk.samarasingher_super.asset.branch.controller;


import lk.samarasingher_super.asset.branch.entity.Branch;
import lk.samarasingher_super.asset.branch.service.BranchService;
import lk.samarasingher_super.asset.common_asset.model.enums.LiveOrDead;
import lk.samarasingher_super.util.interfaces.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/branch")
public class BranchController implements AbstractController<Branch, Integer> {
    private final BranchService branchService;

    @Autowired
    public BranchController(BranchService branchService) {
        this.branchService = branchService;
    }

    private String commonThings(Model model, Branch branch, Boolean addState) {
        model.addAttribute("branch", branch);
        model.addAttribute("addStatus", addState);
        return "branch/addBranch";
    }

    @GetMapping
    public String findAll(Model model) {
        model.addAttribute("branchs", branchService.findAll().stream()
            .filter(x-> LiveOrDead.ACTIVE.equals(x.getLiveOrDead()))
            .collect(Collectors.toList()));
        return "branch/branch";
    }

    @Override
    public String findById(Integer id, Model model) {
        return null;
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        return commonThings(model, new Branch(), true);
    }

    @PostMapping( value = {"/save", "/update"} )
    public String persist(@Valid @ModelAttribute Branch branch, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
        if ( bindingResult.hasErrors() ) {
            return commonThings(model, branch, true);
        }
        redirectAttributes.addFlashAttribute("branchDetail", branchService.persist(branch));
       // branchService.persist(branch);
        return "redirect:/branch";
    }

    @GetMapping( "/edit/{id}" )
    public String edit(@PathVariable Integer id, Model model) {
        return commonThings(model, branchService.findById(id), false);
    }

    @GetMapping( "/delete/{id}" )
    public String delete(@PathVariable Integer id, Model model) {
        branchService.delete(id);
        return "redirect:/branch";
    }

    @GetMapping( "/{id}" )
    public String view(@PathVariable Integer id, Model model) {
        model.addAttribute("branchDetail", branchService.findById(id));
        return "branch/branch-detail";
    }
}
