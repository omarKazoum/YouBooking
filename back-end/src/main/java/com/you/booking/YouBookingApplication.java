package com.you.booking;

import com.you.booking.entity.City;
import com.you.booking.entity.Owner;
import com.you.booking.entity.RoleEnum;
import com.you.booking.repository.CityRepository;
import com.you.booking.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class YouBookingApplication implements CommandLineRunner {
    @Autowired
    CityRepository cityRepository;
    @Autowired
    UserRepository userRepository;
    public static void main(String[] args) {
        SpringApplication.run(YouBookingApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        /*Owner o=new Owner();
        o.setBanned(false);
        o.setEmail("owner1@gmail.com");
        o.setFirstName("owner ");
        o.setLastName("owner last name");
        o.setPassword(new BCryptPasswordEncoder().encode("passpass"));
        userRepository.save(o);

        cityRepository.save(new City(1l,"taroudant"));
        cityRepository.save(new City(1l,"agadir"));
        cityRepository.save(new City(1l,"marrakech"));
*/
    }
}
