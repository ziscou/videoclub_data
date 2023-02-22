package org.iesvdm.videoclub.service;

import org.iesvdm.videoclub.domain.Categoria;
import org.iesvdm.videoclub.exception.CategoriaNotFoundException;
import org.iesvdm.videoclub.repository.CategoriaRepository;
import org.iesvdm.videoclub.repository.CustomCategoriaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


import java.util.*;

@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;
    private final CustomCategoriaRepository customRepository;

    public CategoriaService(CategoriaRepository categoriaRepository, CustomCategoriaRepository customRepository) {
        this.categoriaRepository = categoriaRepository;
        this.customRepository = customRepository;
    }

    public List<Categoria> all() {
        return this.categoriaRepository.findAll();
    }

    public Map<String, Object> all(int pagina, int tamanio) {
        Pageable pageable = PageRequest.of(pagina, tamanio, Sort.by("id").ascending());

        Page<Categoria> pageAll = this.categoriaRepository.findAll(pageable);

        Map<String, Object> response = new HashMap<>();

        response.put("categorias", pageAll.getContent());
        response.put("currentPage", pageAll.getNumber());
        response.put("totalItems", pageAll.getNumberOfElements());
        response.put("totalPage", pageAll.getTotalPages());

        return response;


    }
    public List<Categoria> allFilter(Optional<String> busOpt , Optional<String> ordOpt) {
        return this.customRepository.queryCustomCategoria(busOpt, ordOpt);
    }

    public Categoria save(Categoria categoria) {
        return this.categoriaRepository.save(categoria);
    }

    public Categoria one(Long id) {
        return this.categoriaRepository.findById(id)
                .orElseThrow(() -> new CategoriaNotFoundException(id));
    }

    public Categoria replace(Long id, Categoria categoria) {

        return this.categoriaRepository.findById(id).map( p -> (id.equals(categoria.getId())  ?
                                                            this.categoriaRepository.save(categoria) : null))
                .orElseThrow(() -> new CategoriaNotFoundException(id));

    }

    public void delete(Long id) {
        this.categoriaRepository.findById(id).map(p -> {this.categoriaRepository.delete(p);
                                                        return p;})
                .orElseThrow(() -> new CategoriaNotFoundException(id));
    }

}
