package com.seguros.demo.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import com.seguros.demo.model.Aplicacion;
import com.seguros.demo.model.UserAuth;
import com.seguros.demo.service.ApiService;
import com.seguros.demo.service.MainService;

import lombok.Getter;
import lombok.Setter;

@Controller
@Scope("view")
public class MainController {		
	
	@Getter @Setter List<Aplicacion> aplicaciones;	
	
    private final ApiService apiService;
    
    @Getter @Setter private BarChartModel barModel;
	@Getter @Setter private Boolean commandAvailable = false;
	
	@Autowired
	public MainController(ApiService apiService) {
		this.apiService = apiService;
	}
	
	@PostConstruct
	public void init() {
		createBarModel();
	} 
	
	private UserAuth getPrincipal() {
		return (UserAuth)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}
	
	public void executeDirCommand() {
		this.commandAvailable = this.apiService.executeCommand("dir", getPrincipal());
	}
	
	public void executeLsCommand() {
		this.commandAvailable = this.apiService.executeCommand("ls", getPrincipal());
	}
	
	public void executeHelpCommand() {
		this.commandAvailable = this.apiService.executeCommand("hel", getPrincipal());
	}
	
	private void createBarModel() {
        barModel = initBarModel();
 
        barModel.setTitle("Bar Chart");
        barModel.setLegendPosition("ne");
 
        Axis xAxis = barModel.getAxis(AxisType.X);
        xAxis.setLabel("Gender");
 
        Axis yAxis = barModel.getAxis(AxisType.Y);
        yAxis.setLabel("Births");
        yAxis.setMin(0);
        yAxis.setMax(200);
    }
	
	 private BarChartModel initBarModel() {
	        BarChartModel model = new BarChartModel();
	 
	        ChartSeries boys = new ChartSeries();
	        boys.setLabel("Boys");
	        boys.set("2004", 120);
	        boys.set("2005", 100);
	        boys.set("2006", 44);
	        boys.set("2007", 150);
	        boys.set("2008", 25);
	 
	        ChartSeries girls = new ChartSeries();
	        girls.setLabel("Girls");
	        girls.set("2004", 52);
	        girls.set("2005", 60);
	        girls.set("2006", 110);
	        girls.set("2007", 135);
	        girls.set("2008", 120);
	 
	        model.addSeries(boys);
	        model.addSeries(girls);
	 
	        return model;
	    }
}
