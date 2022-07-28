package br.com.lex4crypto.monolito.controllers;

import br.com.lex4crypto.monolito.service.CarteiraService;
import br.com.lex4crypto.monolito.dtos.CarteiraDto;
import br.com.lex4crypto.monolito.models.Carteira;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/carteiras")
public class CarteiraController {
    private final CarteiraService carteiraService;

    public CarteiraController(CarteiraService carteiraService) {
        this.carteiraService = carteiraService;
    }

    @PostMapping
    public ResponseEntity<Carteira> save(@RequestBody @Valid CarteiraDto carteiraDto){
        var carteira = new Carteira();
        BeanUtils.copyProperties(carteiraDto, carteira);
        return ResponseEntity.status(HttpStatus.CREATED).body(carteiraService.save(carteira));
    }

    @GetMapping
    public ResponseEntity<List<Carteira>> findAll(){
        return ResponseEntity.status(HttpStatus.OK).body(carteiraService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Carteira> findById(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(carteiraService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Carteira> update(@PathVariable Long id, @RequestBody @Valid CarteiraDto carteiraDto) {
        return ResponseEntity.status(HttpStatus.OK).body(carteiraService.update(id, carteiraDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        carteiraService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body("Carteira deletada com sucesso!");
    }


}
