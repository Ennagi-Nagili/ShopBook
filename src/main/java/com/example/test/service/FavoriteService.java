package com.example.test.service;

import com.example.test.entity.CartEntity;
import com.example.test.entity.FavoriteEntity;
import com.example.test.mapper.CartMapper;
import com.example.test.mapper.FavoriteMapper;
import com.example.test.model.Cart;
import com.example.test.model.Favorite;
import com.example.test.repository.FavoriteRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FavoriteService {
    private FavoriteRepository favoriteRepository;

    public FavoriteService(FavoriteRepository favoriteRepository) {
        this.favoriteRepository = favoriteRepository;
    }

    public void add(FavoriteEntity favorite) {
        favoriteRepository.save(favorite);
    }

    public List<FavoriteEntity> getFavoriteList() {
        List<FavoriteEntity> favorite = favoriteRepository.findAll();
        List<FavoriteEntity> list = new ArrayList<>();
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String owner = ((User)principal).getUsername();

        for (int i = 0; i < favorite.size(); i++) {
            if (favorite.get(i).getOwner().equals(owner)) {
                list.add(favorite.get(i));
            }
        }

        return list;
    }

    public void deleteById(Integer id) {
        favoriteRepository.deleteById(id);
    }

    public void clear() {
        favoriteRepository.deleteAll();
    }
}
