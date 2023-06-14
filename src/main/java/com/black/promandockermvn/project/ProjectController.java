package com.black.promandockermvn.project;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ProjectController {

    @Autowired private ProjectService projectService;

    @GetMapping("/projects")
    public String showProjectList(Model model) {
        List<Project> projects = projectService.listAll();
        model.addAttribute("projects", projects);
        return "projects";
        
    }

    @GetMapping("/projects/create")
    public String showAddProject(Model model) {
        
        model.addAttribute("pageTitle", "New Project");
        model.addAttribute("project", new Project());
        return "create";
        
    }
    
    @PostMapping("/projects/save")
    public String saveProject(Project project, RedirectAttributes ra) {
        
        projectService.save(project);
        ra.addFlashAttribute("message", "The Project is Saved Successfully ");
        return "redirect:/projects";
    }

    @GetMapping("/projects/edit/{id}")
    public String showEditFrom(@PathVariable("id") Integer id, Model model, RedirectAttributes ra) {
        
            Project project;
            try {
                project = projectService.get(id);
                model.addAttribute("project", project);
                model.addAttribute("pageTitle", "Edit Project ( ID: "+id+" )");

                return "create";

            } catch (ProjectNotFoundException e) {
                
                ra.addFlashAttribute("message", "The Project Does not Exist");
                return "redirect:/projects";
            }
           
        
    }

    @GetMapping("/projects/delete/{id}")
    public String delete(@PathVariable("id") Integer id, RedirectAttributes ra) {
        
            try {
                projectService.delete(id);

                ra.addFlashAttribute("message", "The Project is Successfully Deleted!");
            } catch (ProjectNotFoundException e) {
                
                ra.addFlashAttribute("message", e.getMessage());
                
            }
            return "redirect:/projects";
           
        
    }
}