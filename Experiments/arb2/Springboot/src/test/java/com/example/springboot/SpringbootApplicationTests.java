package com.example.springboot;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest
class SpringbootApplicationTests {

    @Test
    public List<String> hello(){
        return List.of("Hello", "World");
    }

        public class Student {
        private long id;
        private String name;
        private String email;
        private Integer age;
        private LocalDate dob;

        public void Student(){

        }

        public Student(long id,
                       String name,
                       Integer age,
                       LocalDate dob) {
            this.id = id;
            this.name = name;
            this.age = age;
            this.dob = dob;
        }



        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }

        public LocalDate getDob() {
            return dob;
        }

        public void setDob(LocalDate dob) {
            this.dob = dob;
        }


        @Override
        public String toString() {
            return "Student{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", email='" + email + '\'' +
                    ", age=" + age +
                    ", dob=" + dob +
                    '}';
        }
    }



    public SpringbootApplicationTests() {
    }

    void contextLoads() {
    }

}
