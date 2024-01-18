package com.example.lr15.controllers;

import com.example.lr15.entities.Hotel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.ui.Model;
import com.example.lr15.services.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Controller
public class HotelController {
    private HotelService hotelService;
    private UserDetailsService userDetailsService;

    @Autowired
    public void setHotelService(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    @GetMapping("")
    public String showHotelList(Model model, @RequestParam(defaultValue = "0") int page) {
        Pageable pageable = PageRequest.of(page, 5);
        Page<Hotel> hotelPage = hotelService.getAllHotels(pageable);
        model.addAttribute("hotels", hotelPage.getContent());
        model.addAttribute("hotel", new Hotel());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", hotelPage.getTotalPages());
        List<Hotel> tophotels = hotelService.getTopHotels();
        model.addAttribute("tophotels", tophotels);

        return "hotels";

    }

    @PostMapping("/hotels/addOrUpdate/add")
    public String addHotel(@ModelAttribute(value = "Hotel") Hotel hotel) {
        hotelService.add(hotel);
        return "redirect:/";
    }

    @GetMapping("/hotels/addOrUpdate/add")
    public String test(Model model,
                       @RequestHeader(value = "Referer") String referer) {
        Page<Hotel> flowerPage = hotelService.getAllHotels(PageRequest.of(0, 5));
        model.addAttribute("hotels", flowerPage.getContent());
        model.addAttribute("hotel", new Hotel());
        model.addAttribute("referer", referer);
        return "addOrUpdate";
    }

    @GetMapping("/hotels/addOrUpdate/edit/{id}")
    public String editHotel(Model model, @PathVariable(value = "id") Integer id,
                             @RequestHeader(value = "Referer") String referer) {
        Hotel hotel = hotelService.getById(id);
        hotelService.incViews(hotel);
        model.addAttribute("hotel", hotel);
        model.addAttribute("referer", referer);
        return "addOrUpdate";
    }

    @PostMapping("/hotels/addOrUpdate/edit")
    public String updateHotel(@ModelAttribute(value = "Hotel") Hotel updatedHotel) {
        Hotel hotel = hotelService.getById(updatedHotel.getId());
        hotelService.update(hotel, updatedHotel);

        return "redirect:/";
    }

    @GetMapping("/hotels/show/{id}")
    public String showOneHotel(Model model, @PathVariable(value = "id") Integer id,
                               @RequestHeader(value = "Referer") String referer) {
        Hotel hotel = hotelService.getById(id);
        hotelService.incViews(hotel);
        model.addAttribute("hotel", hotel);
        model.addAttribute("referer", referer);
        List<Hotel> tophotels = hotelService.getTopHotels();
        model.addAttribute("tophotels", tophotels);

        return "hotel-info";
    }

    @GetMapping("/hotels/delete/{id}")
    public String deleteHotel(@PathVariable(value = "id") Integer id) {
        Hotel hotel = hotelService.getById(id);
        hotelService.delete(hotel);
        return "redirect:/";
    }

    @GetMapping("/hotels/filter")
    public String filterHotels(Model model,
                               @RequestParam(value = "name", required = false) String name,
                               @RequestParam(value = "pricefrom", required = false) Double pricefrom,
                               @RequestParam(value = "priceto", required = false) Double priceto,
                               @RequestParam(value = "hotelr", required = false) String hotelr,
                               @RequestParam(value = "quantity", required = false) String quantity,
                               @RequestParam(value = "status", required = false) Integer status,
                               @RequestParam(defaultValue = "0") int page) {

        Pageable pageable = PageRequest.of(page, 5);
        Page<Hotel> hotelPage = hotelService.getAllHotel(name, pricefrom, quantity, hotelr, priceto, status, pageable);

        model.addAttribute("hotels", hotelPage.getContent());
        model.addAttribute("name", name);
        model.addAttribute("quantity", quantity);
        model.addAttribute("pricefrom", pricefrom);
        model.addAttribute("priceTo", priceto);
        model.addAttribute("hotelr", hotelr);
        model.addAttribute("status", status);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", hotelPage.getTotalPages());

        List<Hotel> tophotels = hotelService.getTopHotels();
        model.addAttribute("tophotels", tophotels);

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString("/hotels/filter");
        if (name != null && !name.isEmpty()) uriBuilder.queryParam("name", name);
        if (pricefrom != null) uriBuilder.queryParam("pricefrom", pricefrom);
        if (priceto != null) uriBuilder.queryParam("priceto", priceto);
        if (hotelr != null && !hotelr.isEmpty()) uriBuilder.queryParam("hotelr", hotelr);
        if (quantity != null) uriBuilder.queryParam("quantity", quantity);
        if (status != null) uriBuilder.queryParam("status", status);

        model.addAttribute("filterUrl", uriBuilder.build().toString());
        return "hotels";
    }


    @PostMapping("/authenticateTheUser")
    public String authenticateUser(@RequestParam("username") String username, @RequestParam("password") String password, Model model) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        if (userDetails != null) {
            String storedPassword = userDetails.getPassword();
            if (password.equals(storedPassword)) {
                model.addAttribute("username", username);
                return "redirect:/Hotels";
            }
        }

        return "hotels";
    }
}
