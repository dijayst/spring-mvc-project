package com.springmvcproject.concertio.controllers;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.springmvcproject.concertio.formbeans.AccountCreationForm;

@Controller
@RequestMapping("/account")
public class AccountController {
	
	private static final String VN_REG_FORM = "accountRegistration";
	private static final String VN_REG_OK = "redirect:registration_ok";
	
	/**
	 * defines the field that HTTP parameters will be bound
	 * to and ignore unwanted form parameters.
	 * @param binder
	 */
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.setAllowedFields(
			"firstName", "middleName", "lastName", "email", "password", 
			"confirmPassword", "acceptTerms");
	}

	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String getRegistrationForm(Model model) {
		model.addAttribute("account", new AccountCreationForm());
		return VN_REG_FORM;
	}

	@RequestMapping(value = "", method = RequestMethod.POST)
	public String postRegistrationForm(
			@ModelAttribute("account") @Valid AccountCreationForm form,
			BindingResult result) {
		
		convertPasswordError(result);
		return (result.hasErrors() ? VN_REG_FORM : VN_REG_OK);
	}
	
	private static void convertPasswordError(BindingResult result) {
		for(ObjectError error : result.getGlobalErrors()) {
			String message = error.getDefaultMessage();
			if("account.password.mismatch.message".equals(message)) {
				if(!result.hasFieldErrors("password")) {
					result.rejectValue("password", "error.mismatch");
				}	
			}
		}
	}
}
