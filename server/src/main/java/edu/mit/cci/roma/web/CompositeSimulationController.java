package edu.mit.cci.roma.web;


import edu.mit.cci.roma.model.CompositeServerSimulation;
import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@RooWebScaffold(path = "compositesimulations", formBackingObject = CompositeServerSimulation.class)
@RequestMapping("/compositesimulations")
@Controller
public class CompositeSimulationController {

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, headers = "accept=text/xml")
    @ResponseBody
    public CompositeServerSimulation showXml(@PathVariable("id") Long id, Model model) {
        return CompositeServerSimulation.findCompositeSimulation(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, headers = "accept=text/html")
    public String show(@PathVariable("id") Long id, Model model) {
        addDateTimeFormatPatterns(model);
        model.addAttribute("compositesimulation", CompositeServerSimulation.findCompositeSimulation(id));
        model.addAttribute("itemId", id);
        return "compositesimulations/show";
    }

}