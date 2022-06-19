package com.example.demo.controller;

import com.example.demo.dto.PatientDTO;
import com.example.demo.dto.ResultDto;
import com.example.demo.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
public class HomeController {
    @Autowired
    private PatientService patientService;

    @PostMapping("/")
    public ResponseEntity<ResultDto> predictResult (@RequestBody PatientDTO patientDTO) throws Exception {
        ResultDto newDTO = patientService.predict(patientDTO);
        return new ResponseEntity<>(newDTO, HttpStatus.CREATED);
    }

}
