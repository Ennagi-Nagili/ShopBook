package com.example.test.controller;

import com.example.test.entity.*;
import com.example.test.model.*;
import com.example.test.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/web")
public class WebController {
    private final BookService bookService;
    private final CartService cartService;
    private final FavoriteService favoriteService;
    private final OtherService otherService;

    public WebController(BookService bookService, CartService cartService, FavoriteService favoriteService, OtherService otherService) {
        this.bookService = bookService;
        this.cartService = cartService;
        this.favoriteService = favoriteService;
        this.otherService = otherService;
    }

    @GetMapping("/home")
    public String home(Model model) {
        List<BookEntity> bookList = bookService.getBookList();
        List<NewEntity> newList = otherService.getNewList();
        List<BestEntity> bestList = otherService.getBestList();
        List<BlogEntity> blogList = otherService.getBlogList();
        List<BestEntity> bestList1 = new ArrayList<>();
        List<BestEntity> bestList2 = new ArrayList<>();
        List<BestEntity> bestList3 = new ArrayList<>();

        for (BestEntity bestEntity : bestList) {switch (bestEntity.getType()) {
                case "Top Rated" -> bestList1.add(bestEntity);
                case "Best Selling" -> bestList2.add(bestEntity);
                case "On Sale" -> bestList3.add(bestEntity);
            }
        }

        model.addAttribute("book", bookList);
        model.addAttribute("special", newList);
        model.addAttribute("best1", bestList1);
        model.addAttribute("best2", bestList2);
        model.addAttribute("best3", bestList3);
        model.addAttribute("blog", blogList);

        return "Home";
    }

    @GetMapping("/login")
    public String login() {
        return "Login";
    }

    @GetMapping("/register")
    public String register() {
        return "Register";
    }

    @GetMapping("/details/{id}")
    public String details(@PathVariable Integer id, Model model) {
        Product product = bookService.getProduct(id);
        model.addAttribute("product", product);
        BookEntity bookEntity = bookService.getEntity(id);
        model.addAttribute("entity", bookEntity);

        return "Details";
    }

    @GetMapping("/details1/{id}")
    public String details1(@PathVariable Integer id, Model model) {
        Product product = bookService.getProduct(id);
        model.addAttribute("product", product);
        BookEntity bookEntity = bookService.getEntity(id);
        model.addAttribute("entity", bookEntity);
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("cart", cartService.getCartList().size());
        model.addAttribute("fav", favoriteService.getFavoriteList().size());
        model.addAttribute("name", ((User)principal).getUsername());

        return "Details1";
    }

    @GetMapping("/save/username/{username}/authority/{authority}")
    public String save(@PathVariable String username, @PathVariable String authority) {
        Authority authority2 = new Authority();
        authority2.setUsername(username);
        authority2.setAuthority(authority);

        bookService.save(authority2);

        return "Login";
    }

    @GetMapping("/book/name/{name}/price/{price}/details/{details}/image/{image} ")
    public String book(@PathVariable String name,
                       @PathVariable String price,
                       @PathVariable String details,
                       @PathVariable String image) {
        Product product = new Product();
        product.setName(name);
        product.setPrice(price);
        product.setDetails(details);
        product.setImage(image);

        bookService.book(product);

        return "Home";
    }

    @GetMapping("/cart")
    public String cart(Model model) {
        List<CartEntity> cartList = cartService.getCartList();
        String total = cartService.getTotal();
        List<FavoriteEntity> favoriteList = favoriteService.getFavoriteList();

        model.addAttribute("size", cartList.size());
        model.addAttribute("favorite", favoriteList.size());
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("name", ((User)principal).getUsername());

        model.addAttribute("cart", cartList);
        model.addAttribute("total", total);

        return "Cart";
    }

    @GetMapping("/favorites")
    public String favorite(Model model) {
        List<FavoriteEntity> favoriteList = favoriteService.getFavoriteList();
        List<CartEntity> cartList = cartService.getCartList();

        model.addAttribute("size", favoriteList.size());
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        model.addAttribute("name", ((User)principal).getUsername());
        model.addAttribute("cart", cartList.size());
        model.addAttribute("favorite", favoriteList);

        return "Favorite";
    }

    @GetMapping("/search/{name}")
    public String search(@PathVariable String name, Model model) {
        model.addAttribute("books", bookService.getBook(name));

        return "Search";
    }

    @GetMapping("/search1/{name}")
    public String search1(@PathVariable String name, Model model) {
        model.addAttribute("books", bookService.getBook(name));
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("cart", cartService.getCartList().size());
        model.addAttribute("fav", favoriteService.getFavoriteList().size());
        model.addAttribute("name", ((User)principal).getUsername());

        return "Search1";
    }

    @GetMapping("/home1")
    public String home1(Model model) {
        List<BookEntity> bookList = bookService.getBookList();
        List<CartEntity> cartList = cartService.getCartList();
        List<FavoriteEntity> favoriteList = favoriteService.getFavoriteList();
        List<BlogEntity> blogList = otherService.getBlogList();

        model.addAttribute("book", bookList);
        model.addAttribute("cart", cartList.size());
        model.addAttribute("favorite", favoriteList.size());
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("name", ((User)principal).getUsername());
        model.addAttribute("blog", blogList);

        List<NewEntity> newList = otherService.getNewList();
        List<BestEntity> bestList = otherService.getBestList();
        List<BestEntity> bestList1 = new ArrayList<>();
        List<BestEntity> bestList2 = new ArrayList<>();
        List<BestEntity> bestList3 = new ArrayList<>();

        for (BestEntity bestEntity : bestList) {
            switch (bestEntity.getType()) {
                case "Top Rated" -> bestList1.add(bestEntity);
                case "Best Selling" -> bestList2.add(bestEntity);
                case "On Sale" -> bestList3.add(bestEntity);
            }
        }

        model.addAttribute("special", newList);
        model.addAttribute("best1", bestList1);
        model.addAttribute("best2", bestList2);
        model.addAttribute("best3", bestList3);

        return "Home1";
    }

    @GetMapping("/problem")
    public String error() {
        return "Problem";
    }

    @GetMapping("/admin")
    public String admin(Model model) {
        List<BookEntity> bookList = bookService.getBookList();
        model.addAttribute("product", bookList);

        return "Admin";
    }

    @GetMapping("/admin/edit/{id}")
    public String adminEdit(@PathVariable Integer id, Model model) {
        model.addAttribute("product", bookService.getEntity(id));
        model.addAttribute("image", bookService.getEntity(id).getImage().replace('/', '-'));
        model.addAttribute("type", "main");
        return "Edit";
    }

    @GetMapping("/best/edit/{name}")
    public String bestEdit(@PathVariable String name, Model model) {
        model.addAttribute("product", otherService.getBest(name));
        model.addAttribute("image", otherService.getBest(name).getImage().replace('/', '-'));
        model.addAttribute("type", "best");
        return "Edit";
    }

    @GetMapping("/blog/edit/{name}")
    public String blogEdit(@PathVariable String name, Model model) {
        model.addAttribute("product", otherService.getBlog(name));
        model.addAttribute("image", otherService.getBlog(name).getImage().replace('/', '-'));
        return "Edit1";
    }

    @GetMapping("/new/edit/{name}")
    public String newEdit(@PathVariable String name, Model model) {
        model.addAttribute("product", otherService.getNew(name));
        model.addAttribute("image", otherService.getNew(name).getImage().replace('/', '-'));
        model.addAttribute("type", "new");
        return "Edit";
    }

    @GetMapping("/admin/add")
    public String adminAdd(Model model) {
        model.addAttribute("type", "main");
        return "Add";
    }

    @GetMapping("/admin/add1")
    public String adminAdd1(Model model) {
        model.addAttribute("type", "best");
        return "Add";
    }

    @GetMapping("/admin/add2")
    public String adminAdd2(Model model) {
        model.addAttribute("type", "new");
        return "Add";
    }

    @GetMapping("/admin/add3")
    public String adminAdd3() {
        return "Add1";
    }

    @GetMapping("/best")
    public String best(Model model) {
        List<BestEntity> bestList = otherService.getBestList();
        model.addAttribute("product", bestList);

        return "Best";
    }

    @GetMapping("/new")
    public String New(Model model) {
        List<NewEntity> newList = otherService.getNewList();
        model.addAttribute("product", newList);

        return "New";
    }

    @GetMapping("/blog")
    public String blog(Model model) {
        List<BlogEntity> blogList = otherService.getBlogList();
        model.addAttribute("product", blogList);

        return "Blog";
    }

    @GetMapping("/details/name/{name}")
    public String details1(@PathVariable String name, Model model) {
        BestEntity bestEntity = otherService.getBest(name);
        model.addAttribute("entity", bestEntity);

        return "Details";
    }

    @GetMapping("/details/name1/{name}")
    public String details2(@PathVariable String name, Model model) {
        NewEntity newEntity = otherService.getNew(name);
        model.addAttribute("entity", newEntity);

        return "Details";
    }

    @GetMapping("/admin/register")
    public String adminRegister() {
        return "Register1";
    }
}
