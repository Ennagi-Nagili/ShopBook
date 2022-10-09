package com.example.test.controller;

import com.example.test.entity.*;
import com.example.test.model.*;
import com.example.test.service.BookService;
import com.example.test.service.CartService;
import com.example.test.service.FavoriteService;
import com.example.test.service.OtherService;
import org.postgresql.shaded.com.ongres.scram.common.bouncycastle.base64.Base64Encoder;
import org.postgresql.util.PSQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequestMapping("/main")
public class Controller {
    private final BookService bookService;
    private final CartService cartService;
    private final FavoriteService favoriteService;
    private final OtherService otherService;

    public Controller(BookService bookService, CartService cartService, FavoriteService favoriteService, OtherService otherService, PasswordEncoder passwordEncoder) {
        this.bookService = bookService;
        this.cartService = cartService;
        this.favoriteService = favoriteService;
        this.otherService = otherService;
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/add/id/{id}/count/{count}")
    public RedirectView add(@PathVariable Integer id, @PathVariable Integer count) {
        CartEntity cart = new CartEntity();
        Product product = bookService.getProduct(id);

        cart.setName(product.getName());

        String price = "";
        for (int i = 0; i < product.getPrice().length(); i++) {
            if (i > 1) {
                price += product.getPrice().charAt(i);
            }
        }

        cart.setPrice(Double.valueOf(price));
        cart.setImage(product.getImage());
        cart.setCount(count);

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        cart.setOwner(((org.springframework.security.core.userdetails.User)principal).getUsername());

        cartService.add(cart);

        return new RedirectView("/web/cart");
    }

    @GetMapping("/registered/username/{username}/password/{password}/enabled/{enabled}/authority/{authority1}")
    public RedirectView registered(@PathVariable String username,
                             @PathVariable String password,
                             @PathVariable Boolean enabled,
                             @PathVariable String authority1,
                             Model model) {
        String redirect = "/web/login";

        try{
            User user = new User();
            user.setUsername(username);

            String pass = passwordEncoder.encode(password);
            user.setPassword(pass);

            user.setEnabled(enabled);

            Authority authority = new Authority();
            authority.setUsername(username);
            authority.setAuthority(authority1);

            bookService.register(user, authority);
        }

        catch (Exception exception) {
            redirect = "/web/problem";
        }

        System.out.println(redirect);

        return new RedirectView(redirect);
    }

    @GetMapping("/fav/{id}")
    public RedirectView fav(@PathVariable Integer id) {
        FavoriteEntity favorite = new FavoriteEntity();
        Product product = bookService.getProduct(id);

        favorite.setName(product.getName());

        String price = "";
        for (int i = 0; i < product.getPrice().length(); i++) {
            if (i > 1) {
                price += product.getPrice().charAt(i);
            }
        }

        favorite.setPrice(Double.valueOf(price));
        favorite.setImage(product.getImage());
        favorite.setCount(1);

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        favorite.setOwner(((org.springframework.security.core.userdetails.User)principal).getUsername());

        favoriteService.add(favorite);

        return new RedirectView("/web/favorites");
    }

    @GetMapping("/cart/remove/{id}")
    public RedirectView rmvCart(@PathVariable Integer id) {
        cartService.deleteById(id);
        return new RedirectView("/web/cart");
    }

    @GetMapping("/fav/remove/{id}")
    public RedirectView rmvFav(@PathVariable Integer id) {
        favoriteService.deleteById(id);
        return new RedirectView("/web/favorites");
    }

    @GetMapping("/cart/clear")
    public RedirectView clrCart() {
        cartService.clear();
        return new RedirectView("/web/cart");
    }

    @GetMapping("/fav/clear")
    public RedirectView clrFav() {
        favoriteService.clear();
        return new RedirectView("/web/favorites");
    }

    @GetMapping("/admin/delete/{id}")
    public RedirectView adminDelete(@PathVariable Integer id) {
        bookService.deleteById(id);
        return new RedirectView("/web/admin");
    }

    @GetMapping("/best/delete/{id}")
    public RedirectView bestDelete(@PathVariable Integer id) {
        otherService.deleteBest(id);
        return new RedirectView("/web/best");
    }

    @GetMapping("/new/delete/{id}")
    public RedirectView newDelete(@PathVariable Integer id) {
        otherService.deleteNew(id);
        return new RedirectView("/web/new");
    }

    @GetMapping("/blog/delete/{id}")
    public RedirectView blogDelete(@PathVariable Integer id) {
        otherService.deleteBlog(id);
        return new RedirectView("/web/blog");
    }

    @GetMapping("/admin/edit/id/{id}/name/{name}/price/{price}/image/{image}/details/{details}")
    private RedirectView edit(@PathVariable Integer id, @PathVariable String name, @PathVariable String price, @PathVariable String image,
                              @PathVariable String details) {
        BookEntity bookEntity = new BookEntity();
        bookEntity.setName(name);
        bookEntity.setPrice(price);
        bookEntity.setImage(image.replace('-', '/'));
        bookEntity.setDetails(details);
        bookService.edit(id, bookEntity);

        return new RedirectView("/web/admin");
    }

    @GetMapping("/best/edit/id/{id}/name/{name}/price/{price}/image/{image}/details/{details}/type/{type}")
    private RedirectView bestEdit(@PathVariable Integer id, @PathVariable String name, @PathVariable String price, @PathVariable String image,
                              @PathVariable String details, @PathVariable String type) {
        BestEntity bestEntity = new BestEntity();
        bestEntity.setName(name);
        bestEntity.setPrice(price);
        bestEntity.setImage(image.replace('-', '/'));
        bestEntity.setDetails(details);
        bestEntity.setType(type);
        otherService.bestEdit(id, bestEntity);

        return new RedirectView("/web/best");
    }

    @GetMapping("/new/edit/id/{id}/name/{name}/price/{price}/image/{image}/details/{details}")
    private RedirectView newEdit(@PathVariable Integer id, @PathVariable String name, @PathVariable String price, @PathVariable String image,
                              @PathVariable String details) {
        NewEntity newEntity = new NewEntity();
        newEntity.setName(name);
        newEntity.setPrice(price);
        newEntity.setImage(image.replace('-', '/'));
        newEntity.setDetails(details);
        otherService.newEdit(id, newEntity);

        return new RedirectView("/web/new");
    }

    @GetMapping("/blog/edit/id/{id}/name/{name}/price/{price}/image/{image}/details/{details}")
    private RedirectView blogEdit(@PathVariable Integer id, @PathVariable String name, @PathVariable String price, @PathVariable String image,
                                 @PathVariable String details) {
        BlogEntity blogEntity = new BlogEntity();
        blogEntity.setHead(name);
        blogEntity.setAuthor(price);
        blogEntity.setImage(image.replace('-', '/'));
        blogEntity.setText(details);
        otherService.blogEdit(id, blogEntity);

        return new RedirectView("/web/blog");
    }

    @GetMapping("/admin/add/name/{name}/price/{price}/image/{image}/details/{details}")
    private RedirectView adminAdd(@PathVariable String name, @PathVariable String price, @PathVariable String image,
                              @PathVariable String details) {
        Product bookEntity = new Product();
        bookEntity.setName(name);
        bookEntity.setPrice(price);

        String edited = image.replace('-', '/');
        edited = edited.replace('!', ';');
        bookEntity.setImage(edited);


        bookEntity.setDetails(details);
        bookService.book(bookEntity);

        return new RedirectView("/web/admin");
    }

    @GetMapping("/best/add/name/{name}/price/{price}/image/{image}/details/{details}")
    private RedirectView bestAdd(@PathVariable String name, @PathVariable String price, @PathVariable String image,
                                  @PathVariable String details) {
        BestEntity bestEntity = new BestEntity();
        bestEntity.setName(name);
        bestEntity.setPrice(price);
        bestEntity.setImage(image.replace('-', '/'));
        details = details.replace('[', '"');
        bestEntity.setDetails(details.replace(']', '"'));
        otherService.bestAdd(bestEntity);

        return new RedirectView("/web/best");
    }

    @GetMapping("/new/add/name/{name}/price/{price}/image/{image}/details/{details}")
    private RedirectView newAdd(@PathVariable String name, @PathVariable String price, @PathVariable String image,
                                  @PathVariable String details) {
        NewEntity newEntity = new NewEntity();
        newEntity.setName(name);
        newEntity.setPrice(price);
        newEntity.setImage(image.replace('-', '/'));
        newEntity.setDetails(details);
        otherService.newAdd(newEntity);

        return new RedirectView("/web/new");
    }

    @GetMapping("/blog/add/name/{name}/author/{author}/image/{image}/text/{text}")
    private RedirectView blogAdd(@PathVariable String name, @PathVariable String author, @PathVariable String image,
                                @PathVariable String text) {
        BlogEntity blogEntity = new BlogEntity();
        blogEntity.setHead(name);
        blogEntity.setAuthor(author);
        blogEntity.setImage(image.replace('-', '/'));
        blogEntity.setText(text);
        otherService.blogAdd(blogEntity);

        return new RedirectView("/web/blog");
    }

    @GetMapping("/admin/registered/username/{username}/password/{password}/enabled/{enabled}/authority/{authority1}")
    public RedirectView AdminRegistered(@PathVariable String username,
                                   @PathVariable String password,
                                   @PathVariable Boolean enabled,
                                   @PathVariable String authority1,
                                   Model model) {
        String redirect = "/web/login";

        try{
            User user = new User();
            user.setUsername(username);

            String pass = passwordEncoder.encode(password);
            user.setPassword(pass);

            user.setEnabled(enabled);

            Authority authority = new Authority();
            authority.setUsername(username);
            authority.setAuthority(authority1);

            bookService.register(user, authority);
        }

        catch (Exception exception) {
            redirect = "/web/problem";
        }

        System.out.println(redirect);

        return new RedirectView(redirect);
    }

    @PostMapping("/create")
    public void addCart(@RequestBody Cart cart) {
        cartService.add1(cart);
    }

    @GetMapping("/addHome/{id}")
    public RedirectView addHome(@PathVariable Integer id) {
        CartEntity cart = new CartEntity();
        Product product = bookService.getProduct(id);

        cart.setName(product.getName());

        String price = "";
        for (int i = 0; i < product.getPrice().length(); i++) {
            if (i > 1) {
                price += product.getPrice().charAt(i);
            }
        }

        cart.setPrice(Double.valueOf(price));
        cart.setImage(product.getImage());
        cart.setCount(1);

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        cart.setOwner(((org.springframework.security.core.userdetails.User)principal).getUsername());

        cartService.add(cart);

        return new RedirectView("/web/home1");
    }
}
