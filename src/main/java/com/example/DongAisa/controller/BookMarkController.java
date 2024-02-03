
package com.example.DongAisa.controller;

import com.example.DongAisa.dto.BookMarkDto;
import com.example.DongAisa.service.BookMarkService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/bookmark")
public class BookMarkController {

    @Autowired
    BookMarkService bookMarkService;

    @PostMapping("/insert")
    public ResponseEntity<?> insert (@RequestBody @Valid BookMarkDto bookMarkDto){
        try {
            bookMarkService.insert(bookMarkDto);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred during bookmark insertion");
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> delete (@RequestBody @Valid BookMarkDto bookMarkDto){
        try {
            bookMarkService.delete(bookMarkDto);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred during bookmark deletion");
        }
    }

}
