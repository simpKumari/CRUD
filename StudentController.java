package com.example.springBootApp.controller;

import com.example.springBootApp.entity.Student;
import com.example.springBootApp.repository.StudentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentRepo studentRepo;

    @GetMapping("/findAll")
    public Flux<Student> GetAllStudent(){
        return studentRepo.findAll();
    }
    @GetMapping("/student/{id}")
    public Mono<Student> getStudentById(@PathVariable int id){
        return studentRepo.findById(id);
    }
    @PostMapping("/insert")
    public Mono<Student> insertStudent(@RequestBody Student student){
        return studentRepo.save(student);
    }
    @PutMapping("/update/{id}")
    public Mono<Student> updateStudentById(@RequestBody Student student, @PathVariable int id){
        return studentRepo.findById(id)
                .map(
                        (c)->{
                            c.setId(student.getId());
                            c.setName(student.getName());
                            c.setAdd(student.getAdd());
                            return c;
                        }
                ).flatMap(c->studentRepo.save((c)));
    }
    @DeleteMapping("/delete/{id}")
    public Mono<Void> deleteById(@PathVariable int id){
       return studentRepo.deleteById(id);
    }
}
