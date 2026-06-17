package com.example.connecthubapplication.controller;


import com.example.connecthubapplication.entity.Post;
import com.example.connecthubapplication.entity.User;
import com.example.connecthubapplication.repository.UserRepository;
import com.example.connecthubapplication.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public String feed(Model model) {

        model.addAttribute("posts",
                postService.getTodayPosts());

        return "feed";
    }

    @PostMapping("/create")
    @ResponseBody
    public Post createPost(@RequestParam String content,
                           Authentication authentication) {



        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        System.out.println("User: " + user.getFullName());

        Post post = new Post();
        post.setUserId(user.getId());
        post.setUsername(user.getFullName());
        post.setContent(content);

        return postService.savePost(post);
    }
    
    @GetMapping("/today")
    public List<Post> getTodayPosts() {

        return postService.getTodayPosts();
             
    }
}
