package com.personalprojects.bmi_calculator.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.personalprojects.bmi_calculator.bean.User;
import com.personalprojects.bmi_calculator.service.BMIService;

@Controller
public class BMIController {
	
	@Autowired
	private BMIService bmiService;

	@RequestMapping(value = "/bmiForm", method = RequestMethod.GET)
	public String showBMIForm(@ModelAttribute("user") User user) {
		return "bmiCalculatorForm";
	}

	@RequestMapping(value = "/getBMI", method = RequestMethod.GET)
	public String getBMIStatus(@ModelAttribute("user") @Valid User user, BindingResult result, ModelMap map) {

		// Redirecting to the same form page if invalid
		if (result.hasErrors()) {
			return "bmiCalculatorForm";
		}

		User validUser = bmiService.addUserDetails(user); // Adding user to userList
		Double bmi = bmiService.calculateBMI(validUser); // Calculating BMI
		String BMIstatus = bmiService.getBMIStatus(bmi); // Getting BMI Status

		// Adding all the above objects to the ModelMap to make them accessible in the
		// view
		map.addAttribute("validUser", validUser);
		map.addAttribute("bmi", bmi);
		map.addAttribute("BMIstatus", BMIstatus);

		return "bmiStatus";
	}

	@ModelAttribute("genderList")
	public List<String> populateGenderList() {
		return BMIService.genderType;
	}

}
