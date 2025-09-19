package com.example.sixthproject.controller;

import com.example.sixthproject.model.UserModel;
import com.example.sixthproject.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class WebController {

    @Autowired
    private AuthService authService;

    @GetMapping("/")
    public String home() {
        return "redirect:/catalog";
    }

    @GetMapping("/login")
    public String loginPage(Model model, HttpServletRequest request) {
        String error = (String) request.getAttribute("error");
        if (error != null) {
            model.addAttribute("error", "Неверное имя пользователя или пароль");
        }
        return "login";
    }

    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("user", new UserModel());
        return "register";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("user") UserModel user,
                           BindingResult bindingResult,
                           RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "register";
        }
        try {
            authService.register(user);
            redirectAttributes.addFlashAttribute("success", "Регистрация прошла успешно!");
            return "redirect:/login";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/register";
        }
    }


    @GetMapping("/dashboard")
    public String dashboard(Model model, Authentication authentication) {
        return "redirect:/catalog";
    }

    @GetMapping("/catalog")
    public String catalog() {
        return "catalog";
    }

    @GetMapping("/cart-page")
    public String cartPage() {
        return "cart";
    }

    @GetMapping("/profile")
    public String profile() {
        return "profile";
    }

    @GetMapping("/order")
    public String orderPage() {
        return "order";
    }

    @GetMapping("/product-edit")
    public String createProductPage() {
        return "product-edit";
    }

    @GetMapping("/product-edit/{id}")
    public String editProductPage() {
        return "product-edit";
    }

    @GetMapping("/admin/brands")
    public String adminBrands() { return "admin-brands"; }

    @GetMapping("/admin/categories")
    public String adminCategories() { return "admin-categories"; }

    @GetMapping("/admin/orders")
    public String adminOrders() { return "admin-orders"; }

    @GetMapping("/admin/addresses")
    public String adminAddresses() { return "admin-addresses"; }

    @GetMapping("/admin/users")
    public String adminUsers() { return "admin-users"; }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        SecurityContextHolder.clearContext();
        return "redirect:/login?logout=true";
    }
}
