package com.avenuecode;

import com.avenuecode.repository.RouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    @Autowired
    RouteRepository routeRepository;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    /*@Override
    public void run(String...args) throws Exception {
        System.out.println("*************************************************************");
        Town t = townRepository.findById(1);
        System.out.println("Town -> " + t);
        System.out.println("*************************************************************");
        List<Town> list = townRepository.getAll();
        System.out.println("Town size -> " + list.size());
        for(Town to : list) {
            System.out.println("Town t -> " + to);
        }
        System.out.println("*************************************************************");
    }*/
}
